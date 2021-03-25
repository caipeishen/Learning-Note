#### 事务传播行为

> 多事务方法直接进行调用，这个过程中事务是如何进行管理的

> + REQUIRED：
>
>   如果add方法本身有事务，调用update方法之后，update使用当前add方法中的事务。
>
>   如果add方法本身没有事务，调用update方法之后，创建新事务。
>
> + REQUIRED_NEW：
>
>   如果add方法本身有事务，调用update方法之后，开启新的事务。
>
>   如果add方法本身没有事务，调用update方法之后，开启新的事务。
>



```java
// 同一个对象内事务方法互调默认失效，原因绕过了代理对象
// *事务使用代理对象来控制的
@Transactional(timeout = 30) //a事务的所有设置就传播到了和他公用一个事务的方法
public void a() {
	// b，c做任何设置都没用。都是和a公用一个事务(要使用代理对象才会有事务的传播性)
    this.b();
	this.c();
    
    // 引入了aspectj 这样调用本类的service方法事务隔离传播性才生效
    OrderServiceImpl orderService = (OrderServiceImpl)AopContext.currentProxy();
	orderService.b();
	orderService.c();
    
	// bService.b(); // a事务
	// cService.c();//新事务(不回滚)int i = 10/0;
}
```



##### 事务失效问题

>本地事务失效间题：同一个对象内事务方法互调默认失效，原因绕过了代理对象，事务使用代理对象来控制
>
>解决:使用代理对象来调用事务方法
>
>+ 引入aop-starter;spring-boot-starter-aop;引入了aspectj
>
>+ @EnableAspectJAutoProxy;开启aspectj动态代理功能。以后所有的动态代理都是aspectj创建的(即使没有接口也可以动态代理)。
>
>+ ```java
>  // 启动类启用aop代理
>  @EnableAspectAutoProxy(exposeProxy = true)
>  
>  // 引入了aspectj 这样调用本类的service方法事务隔离传播性才生效
>  OrderServiceImpl orderService = (OrderServiceImpl)AopContext.currentProxy();
>  orderService.b();
>  orderService.c();
>  ```



#### 事务隔离级别

>事务有特性成为隔离性，多事务操作之间不会产生影响。不考虑隔离性产生很多问题

> + 脏读：一个未提交事务读取到另一个未提交事务的数据
> + 不可重复读：一个未提交事务读取到另一提交事务修改数据
> + 幻读：一个未提交事务读取到另一提交事务添加数据

> 隔离级别

> + READ_UNCOMMITTED（读未提交）
>
> 该隔离级别的事务会读到其它未提交事务的数据，此现象也称之为脏读。
>
> + READ_COMMITTED（读提交）
>
> 一个事务可以读取另一个已提交的事务，多次读取会造成不一样的结果，此现象称为不可重复读问题，Oracle和SQLServer的默认隔离级别。
>
> + REPEATABLE_READ（可重复读）
>
> 该隔离级别是MySQL默认的隔离级别，在同一个事务里，select的结果是事务开始时时间点的状态，因此，同样的select操作读到的结果会是一致的，但是，会有幻读现象。MySQL的InnoDB引擎可以通过next-keylocks机制（参考下文"行锁的算法"一节）来避免幻读。
>
> + SERIALIZABLE（序列化）
>
> 在该隔离级别下事务都是串行顺序执行的，MySQL数据库的InnoDB引擎会给读操作隐式加一把读共享锁，从而避免了脏读、不可重读复读和幻读问题。



##### 事务隔离级别图解

![](C:/Users/peish/Desktop/Learning-Note/Spring/images/隔离级别.png)



