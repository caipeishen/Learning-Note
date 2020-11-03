### SpringBoot源码解析

参考：[SpringBoot面试题](https://blog.csdn.net/yuzongtao/article/details/84295732) 

```
三大核心：快速整合第三方框架、无xml注解化配置、使用java语言内嵌tomcat
```



#### 配置文件值注入

```java
/**
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
 *      prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 * 只有这个组件是容器中的组件，才能容器提供的@ConfigurationProperties功能；
 *  @ConfigurationProperties(prefix = "person")默认从全局配置文件中获取值；
 *
 * @ImportResource：导入Spring的配置文件，让配置文件里面的内容生效；
 * Spring Boot里面没有Spring的配置文件，我们自己编写的配置文件，也不能自动识别；
 * 想让Spring的配置文件生效，加载进来；@ImportResource标注在一个配置类上
 *
 **/
@Component
@ImportResource(locations = {"classpath:beans.xml"})
@PropertySource(value = {"classpath:person.properties"})
@ConfigurationProperties(prefix = "person")
```



#### 自定义starter

```java
//SpringBoot启动类
@SpringBootApplication

//SpringBoot的配置类
@SpringBootConfiguration

//启动自动配置
@EnableAutoConfiguration

//导入配置类
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
public class TokenAutoConfiguration(){

}

//获取配置文件值(yml和properties)
@ConfigurationProperties(prefix = "mayikt")
public class TokenProperties(){
	//mayikt.tokenRedisHost
	private String tokenRedisHost;
	//mayikt.tokenRedisPwd
	private String tokenRedisPwd;
}

//程序加载配置类入口
/MATA-INFO/spring.factories
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.mayikt.config.TokenAutoConfiguration
```



#### SpringBoot自动装配

参考： [SpringBoot自动装配原理](https://www.cnblogs.com/hello-shf/p/11057861.html) 

```java
// AutoConfigurationImportSelector类
//自动装配
@Override
public String[] selectImports(AnnotationMetadata annotationMetadata) {
    if (!isEnabled(annotationMetadata)) {
        return NO_IMPORTS;
    }
    AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader.loadMetadata(this.beanClassLoader);
    AnnotationAttributes attributes = getAttributes(annotationMetadata);
    
    //1.获取所有的自动配置类（META-INF/spring.factories中配置的key为org.springframework.boot.autoconfigure.EnableAutoConfiguration的类）
    List<String> configurations = getCandidateConfigurations(annotationMetadata,attributes);
    configurations = removeDuplicates(configurations);
    
    //2.需要排除的自动装配类（springboot的主类上 @SpringBootApplication(exclude = {com.demo.starter.config.DemoConfig.class})指定的排除的自动装配类）
    Set<String> exclusions = getExclusions(annotationMetadata, attributes);
    checkExcludedClasses(configurations, exclusions);
    
    //3.将需要排除的类从 configurations remove掉
    configurations.removeAll(exclusions);
    configurations = filter(configurations, autoConfigurationMetadata);
    fireAutoConfigurationImportEvents(configurations, exclusions);
    return StringUtils.toStringArray(configurations);
}
```







#### SpringBoot启动流程

参考： [SpringBoot源码解析](https://www.cnblogs.com/hello-shf/category/1456313.html)  [Spring观察者模式](https://www.cnblogs.com/dubhlinn/p/10725636.html)  [SpringApplicationRunListener](https://www.cnblogs.com/duanxz/p/11243271.html)



> 入口

```java
SpringApplication.run(SpringbootApplication.class, args);
			点进去 点进去
				 ↓
(new SpringApplication(primarySources)).run(args)
```



##### 1、new SpringApplication() 对象

```
1.new SpringApplication()对象

   第一步：获取当前应用启动类型
   		this.webApplicationType = deduceWebApplicationType();
   		
   第二步：初始化classpath下 META-INF/spring.factories中已配置的ApplicationContextInitializer
   		setInitializers();
   		
   第三步：初始化classpath下所有已配置的 ApplicationListener
   		setListeners();
   		
   第四步：推断主入口类
        deduceMainApplicationClass()
        
        
	1.获取当前应用启动类型
		this.webApplicationType = deduceWebApplicationType();
            ClassUtils.isPresent("类名") 判断classpath是否有加载所需要的类
                webApplicationType 分为三种类型：
                    a.NONE：不会嵌入web容器启动(普通项目)
                    b.SERVLET：使用java代码创建Web容器启动
                    c.REACTIVE：响应式启动(Spring5新特性)
	
	2.初始化classpath下 META-INF/spring.factories中已配置的ApplicationContextInitializer
        setInitializers()
            初始化ApplicationContextInitializer
            通过指定的classLoader从classpath下的META-INF/spring.factories的资源文件中获取接口实例名,然后循环通过反射机制创建实例
            ApplicationContextInitializer是Spring框架的类, 这个类的主要目的就是在ConfigurableApplicationContext调用refresh()方法之前，回调这个类的initialize方法。通过ConfigurableApplicationContext 的实例获取容器的环境Environment，从而实现对配置文件的修改完善等工作
            剪短说就是在容器刷新之前调用该类的 initialize 方法。并将 ConfigurableApplicationContext 类的实例传递给该方法
            通常用于需要对应用程序上下文进行编程初始化的web应用程序中。例如，根据上下文环境注册属性源或激活配置文件等
	
	3.初始化classpath下所有已配置的 ApplicationListener
        setListeners()
            初始化classpath下所有已配置的 ApplicationListener
            加载过程和Initializer差不多
            ApplicationListener是spring的事件监听器，典型的观察者模式
            通过 ApplicationEvent 类和 ApplicationListener 接口，可以实现对spring容器全生命周期的监听，当然也可以自定义监听事件

	4.推断主入口类
        deduceMainApplicationClass()
            根据调用栈，循环判断方法名是否等于main，推断出main方法的类名
            
```



##### 2、run() 方法

```
调用SpringApplication.run()方法 实现启动同时返回当前的容器上下文

    第一步：获取启动监听器,并启动
        SpringApplicationRunListeners listeners = getRunListeners(args);
        listeners.starting();
        
    第二步：构造应用上下文环境
    	ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
    	
    第三步：初始化应用上下文
		context = createApplicationContext();
    	
    第四步：刷新应用上下文前的准备阶段
		prepareContext(context, environment, listeners, applicationArguments, printedBanner);
    	 
    第五步：刷新应用上下文
    	refreshContext(context);


	1.获取启动监听器,并启动
		getRunListeners(args)
			读取META-INF/spring.factories的SpringApplicationRunListener反射机制得到发布启动监听器，
			SpringApplicationRunListener -> EventPublishingRunListener(是Spring容器的启动监听器)
			它其实是用来在整个启动流程中接收不同执行点事件通知的监听者，SpringApplicationRunListener接口规定了SpringBoot的生命周期，在各个生命周期广播相应的事件，调用实际的ApplicationListener类
		listeners.starting()
			循环调用监听starting方法()
			
			
	2.构造应用上下文环境
		prepareEnvironment()
    		像计算机的环境、Java环境、Spring的运行环境(--spring.profiles.active=prod)，以及Spring项目的配置(在SpringBoot中就是那个熟悉的application.properties/yml)等等
                1.getOrCreateEnvironment获取相应的环境对象
                    根据webApplicationType创建环境对象
                2.configureEnvironment
                    将args封装成SimpleCommandLinePropertySource并加入到了environment中并激活相应的配置文件
                2.listeners.environmentPrepared(environment);
                    启动相应的监听器，其中重要的ConfigFileApplicationListener就是加载项目配置文件的监听器。
                    默认获取classpath:/,classpath:/config,file:./file:./conifg项目中的为application的
                    properties、xml、yml、yaml文件. 举例：location:classpath:/appcalition.properties
                至此，项目的变量配置已全部加载完毕,查看environment属性，配置文件的配置信息已经可以看到


	3.初始化应用上下文
		this.createApplicationContext();
		根据webApplicationType进行判断，创建SpringBoot上下文对象ConfigurableApplicationContext
	
	
	4.刷新应用上下文前的准备阶段
		prepareContext(context, environment, listeners, applicationArguments,printedBanner);
            设置容器环境 	
            执行容器后置处理
            执行容器中的 ApplicationContextInitializer 回调
            向各个监听器发送容器已经准备好的事件
            加载我们的启动类，将启动类注入容器（load()方法，重要）
            	注解形式的Bean定义读取器 比如：@Configuration @Bean @Component @Controller @Service等等
            	XML形式的Bean定义读取器
            发布容器已加载事件
            
            
	5.refreshContext(context);
		刷新上下文环境
		准备bean工厂
		设置 beanFactory 的后置处理
		据IoC容器的初始化步骤和IoC依赖注入
	
        refresh()方法中所作的工作也挺多，没办法面面俱到，主要是IoC容器的初始化步骤和tomcat/jetty容器创建

        IoC容器初始化步骤(invokeBeanFactoryPostProcessors()方法中)
            第一步：Resource定位 -> 找到需要spring管理的类
                在SpringBoot中，我们都知道他的包扫描是从主类所在的包开始扫描的，prepareContext()方法中，
                会先将主类解析成BeanDefinition，然后解析主类的BeanDefinition获取basePackage的路径，这样就完成了定位的过程
                常规的在SpringBoot中有三种实现定位，
                第一个是主类所在包的，
                第二个是SPI扩展机制实现的自动装配（比如各种starter），
                第三种就是@Import注解指定的类。各种@EnableXXX注解，很大一部分都是对@Import的二次封装（其实也是为了解耦，比如当@Import导入的类发生变化时，我们的业务系统也不需要改任何代码）

            第二步：BeanDefinition的载入 -> 加载这些类
                所谓的载入就是通过上面的定位得到的basePackage，
                SpringBoot会将该路径拼接成：classpath*:org/springframework/boot/demo/**/*.class这样的形式，
                然后一个类会将该路径下所有的.class文件都加载进来，然后遍历判断是不是有@Component注解，
                如果有的话，就是我们要装载的BeanDefinition
                TIPS：@Configuration，@Controller，@Service等注解底层都是@Component注解，只不过包装了一层罢了。

            第三个：注册BeanDefinition -> 将这些类的BeanDefinition注册到ioc
                这个过程通过调用上文提到的BeanDefinitionRegister接口的实现来完成。
                这个注册过程把载入过程中解析得到的BeanDefinition向IoC容器进行注册。
                通过上文的分析，我们可以看到，在IoC容器中将BeanDefinition注入到一个ConcurrentHashMap中，
                IoC容器就是通过这个HashMap来持有这些BeanDefinition数据的。比如DefaultListableBeanFactory 中的beanDefinitionMap属性。
		
		tomcat/jetty容器创建
			refresh()中的onRefresh()方法，也就说这里要回到子类：ServletWebServerApplicationContext
			根据servletWebServerFactory对象调用getWebServer()方法
			可以根据不同的配置创建不同的容器，例如：TOmcat、Jetty就是在这里面创建的
			
```



#### Spring Boot 自定义注解，AOP 切面统一打印出入参请求日志

参考：[Spring Boot 自定义注解，AOP 切面统一打印出入参请求日志](https://www.exception.site/springboot/spring-boot-aop-web-request)





### SpringBoot打war包部署

1. pom.xml配置修改

```pom.xml
<packaging>war</packaging>
```

2. 排除spring boot中内嵌的tomcat依赖包：

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-tomcat</artifactId>
   <scope>provided</scope><!-- provided打包时不加载此包 -->
</dependency>
```

3. 修改maven打war包插件

```xml
<build>
    <finalName>war包名</finalName>
    <plugins>
        <plugin>
            <artifactId>maven-war-plugin</artifactId>
            <version>3.0.0</version>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.3</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

4. 如果是发布jar包，程序的入口时main函数所在的类，使用@SpringBootApplication注解；如果是war包发布，需要增加SpringBootServletInitializer子类，并重写其configure方法，或者将main函数所在的类继承SpringBootServletInitializer子类，并重写configure方法，当时打包为war时上传到tomcat服务器中访问项目始终报404错就是忽略了这个步骤！！！

```java
//继承SpringBootServletInitializer子类
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

    //重写configure方法
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

