### Spring面试题

参考：[Spring面试题](https://blog.csdn.net/a745233700/article/details/80959716)  [Spring Bean作用域](https://blog.csdn.net/qq_41083009/article/details/90743719)  [Spring源码分析](https://blog.csdn.net/nuomizhende45/article/details/81158383)  [Spring 中的观察者模式](https://www.cnblogs.com/dubhlinn/p/10725636.html))



参考： [IOC容器结构](https://www.bilibili.com/video/BV1EE411u7YV?p=26)  [IOC父子容器](https://blog.csdn.net/fhjdzkp/article/details/78687513)

```
例如：
我们事务注解支持(<tx:annotation-driven/>)放入了spring-context.xml，它的注解范围在service dao以及entity；
如果将事务注解支持放入spring-mvc.xml，事务注解(@Transaction)放在service上无效.

又或者，我们共有三个文件spring-context.xml、spring-security.xml、spring-mvc.xml，spring-context.xml引入了spring-security.xml
如果想启用security注解权限的配置，在controller生效，应放入spring-mvc.xml
如果想启用security注解权限的配置，在service、dao、entity生效，应放入spring-context.xml或者spring-security.xml
```

![](images/ServletContext容器.png)

#### IOC基本原理

```java
1、IOC 思想基于 IOC 容器完成，IOC 容器底层就是对象工厂
2、Spring 提供 IOC 容器实现两种方式：（两个接口）
	1).BeanFactory：IOC 容器基本实现，是 Spring 内部的使用接口，不提供开发人员进行使用
	* 加载配置文件时候不会创建对象，在获取对象（使用）才去创建对象
	
	2).ApplicationContext：BeanFactory 接口的子接口，提供更多更强大的功能，一般由开发人员进行使用
	* 加载配置文件时候就会把在配置文件对象进行创建
	
	虽然第一种方式比较好，但我们通常是Web项目，在项目启动时加载时全部加载，后期访问更快，费时费力放在启动
	
String classValue = class属性;//xml解析得到
Class class = Class.forName(className);//通过反射创建对象
User user = (User)class.newInstance();//创建实例
```



#### IOC 操作Bean管理

> 参考：[bean 生命周期](https://www.cnblogs.com/zrtqsk/p/3735273.html) [bean生命周期](https://www.jb51.net/article/154487.htm)

```xml
1、生命周期
	1).从对象创建到对象销毁的过程
	
2、bean 生命周期
    1).通过构造器创建bean实例（无参数构造）
    2).为bean的属性设置值和对其他bean引用（调用set方法）
    3).把bean实例传递给bean 后置处理器方法执行初始化之前调用方法 -> postProcessBeforeInitialization
    4).调用bean的初始化的方法（需要进行配置初始化的方法:init-method）
    5).把bean实例传递给bean 后置处理器方法执行初始化之后调用方法 -> postProcessAfterInitialization
    6).bean可以使用了（对象获取到了）
    7).当容器关闭时候，调用bean的销毁的方法（需要进行配置销毁的方法）

<bean id="myUser" 
      class="cn.cps.User" 
      init-method="myInitMethod"
      destroy-method="myDestroyMethod"
 ></bean>


后置处理器方法需要实现BeanPostProcess接口,也需要把后置处理器实现类注入容器中
重写两个方法postProcessBeforeInitialization、postProcessAfterInitialization
```



#### AOP基本原理

```java
1、AOP底层使用动态代理
    1).有两种情况动态代理，使用 JDK 动态代理，创建接口实现类代理对象，增强类的方法
    	//创建接口实现类代理对象
 		Class[] interfaces = {UserDao.class};
		UserDaoImpl userDao = new UserDaoImpl();
        UserDao dao = (UserDao)Proxy.newProxyInstance(
            JDKProxy.class.getClassLoader(),
            interfaces,
            new UserDaoProxy(userDao)
        );
		//创建代理对象代码
        class UserDaoProxy implements InvocationHandler {
         //1 把创建的是谁的代理对象，把谁传递过来
         //有参数构造传递
         private Object obj;
         public UserDaoProxy(Object obj) {
             this.obj = obj;
         }
         //增强的逻辑
         @Override
         public Object invoke(Object proxy, Method method, Object[] args){
             //方法之前
             System.out.println(
                 "方法之前执行"+method.getName()
                 +":传递的参数..."
                 + Arrays.toString(args)
             );
             //被增强的方法执行
             Object res = method.invoke(obj, args);
             //方法之后
             System.out.println("方法之后执行...."+obj);
             return res;
          }
    2).没有接口情况，使用 CGLIB 动态代理，创建子类的代理对象，增强类的方法
        自定义当前类的代理对象 继承被代理类，重写该的方法，在该方法中调用父类的方法，执行自己的增强逻辑
```



![](C:/Users/peish/Desktop/Knowledge/images/动态代理有接口情况原理.png)



![](C:/Users/peish/Desktop/Knowledge/images/动态代理没有接口情况原理.png)



#### Spring中的观察者模式

参考：[监听者模式和观察者模式的区别与联系](https://blog.csdn.net/lovexiaotaozi/article/details/102579360)  [Spring 中的观察者模式](https://www.cnblogs.com/jmcui/p/11054756.html)

```
律师是被观察者(Subject)，需要存储所有拍卖的人员，当价格改变，律师需要通知所有拍卖人员价格改变

张三和李四准备上演一场抢劫银行的行动,张三负责抢劫,李四负责放哨,
他们两约定,如果李四这边看到警察来就立即通知张三,张三收到通知后立马逃跑...
在上面的例子中,张三的角色其实就是观察者,李四是被观察者.
```



