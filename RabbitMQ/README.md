### RabbitMQ

参考：[RabbitMQ]( https://www.bilibili.com/video/BV1Qv411B7WS?p=1)  [RabbitMQ之消息持久化](https://blog.csdn.net/u013256816/article/details/60875666/)  [死信队列](https://blog.csdn.net/qq_37960603/article/details/104295562) 

```
市面上比较火爆的几款MQ:
ActiveMQ，RocketMQ，Kafka，RabbitMQ

语言的支持:ActiveMQ，RocketMQ只支持Java语言，Kafka可以支持多们语言，RabbitMQ支持多种语言。
效率方面:ActiveMQ，RocketMQ，Kalka效率都是毫秒级别，RabbitMQ是微秒级别的。
消息丢失，消息重复问题:RabbitMQ针对消息的持久化，和重复问题都有比较成熟的解决方案。

学习成本:RabbitMQ非常简单。
RabbitMQ是由Rabbit公司去研发和维护的，最终是在Pivotal。
RabbitMQ严格的遵循AMQP协议，高级消息队列协议，帮助我们在进程之间传递异步消息。

纠正一个错误千锋教育视频中说exchange没有持久化，是有的并且要设置，看RabbitMQ之消息持久化
```

![](images/RabbitMQ完整架构图.png)



#### Exchange类型

```
Direct Exchange 路由模式：默认类型，根据路由键（Routing Key）将消息投递给对应队列。
Topic Exchange 通配符模式：通过对消息的路由键（Routing Key）和绑定到交换机的队列，将消息路由给队列。符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词
Fanout Exchange 发布/订阅：将消息路由给绑定到它身上的所有队列，而不理会绑定的路由键（Routing Key）。
Headers Exchange 直连交换机：发送消息时匹配 Header 而非 Routing Key，性能很差，几乎不用。
```



#### 消息可靠性

```
1.消费者在消费消息时，如果执行一般，消费者宕机了怎么办?手动ACK。
2.如果消息已经到达了RabbitMQ，但是RabbitMQ宕机了，消息是不是就丢了?Exchange、消息、Queue有持久化机制。
3.生产者发送消息时，由于网络问题，导致消息没发送到RabbitMQ? RabbitMQ提供了事务操作，和Confirm(生产者发送消息到exchange)
4.exchange→queue Return机制(捕捉丢失的消息)
```



##### Ack

```
只需要在消费者端，添加Qos能力以及更改为手动ack即可让消费者，
根据自己的能力去消费指定的消息，而不是默认情况下由RabbitMQ平均分配了
生产者不变，正常发布消息到默认的exchange，并指定routing
```

> 消费者指定Qos和手动ack

```java
//指定当前消费者，一次消费多少个消息
channel.basicQos(1);
//开启监听Queue
DefaultConsumer consume = new DefaultConsumer(channel){
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        Jedis jedis = new Jedis("192.168.199.109",6379);
        String messageId = properties.getMessageId();
        //1. setnx到Redis中，默认指定value-0
        String result = jedis.set(messageId, "0", "NX", "EX", 10);
        if(result != null && result.equalsIgnoreCase("OK")) {
            System.out.println("接收到消息：" + new String(body, "UTF-8"));
            //2. 消费成功，set messageId 1
            jedis.set(messageId,"1");
            channel.basicAck(envelope.getDeliveryTag(),false);
        }else {
            //3. 如果1中的setnx失败，获取key对应的value，如果是0，return，如果是1
            String s = jedis.get(messageId);
            if("1".equalsIgnoreCase(s)){
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        }
    }
};
//手动ack
//参数1：queue - 指定消费哪个队列
//参数2：autoAck - 指定是否自动ACK （true，接收到消息后，会立即告诉RabbitMQ）
//参数3：consumer - 指定消费回调
channel.basicConsume("HelloWorld",false,consume);
```



>SpringBoot手动Ack

```yml
//配置文件
spring:
  rabbitmq:
    listener:
    simple:
      acknowledge-mode: manual
```

```java
//手动ack
@RabbitListener(queues = "boot-queue")
public void getMessage(String msg, Channel channel, Message message) throws IOException {
    System.out.println("接收到消息：" + msg);
    //手动ack
    channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
}
```



##### Confirm 

```
解决生产者到RabbitMQ(Exchange)消息丢失

RabbitMQ的事务:事务可以保证消息100%传递，可以通过事务的回漆去记录日志，后面定时再次发送当前消息。
事务的操作，效率太低，加了事务操作后，比平时的操作效率至少要慢100倍。
RabbitMQ除了事务，还提供了Confirm的确认机制，这个效率比事务高很多。
```



> 1.普通confirm

```java
//1.开启confirm
channel.confirmseleCt();
//2.判断消息发送是否成功
if(channel.waitForConfirms(）{
	System.out.println("消息发送成功"):
}else{
	System.out.println("发送消息失败"）;
}
```



> 2.批量confirm

```java
//1.开启confirm
channel.confirmSelect();
//2.批量发送消息
for (int i = a; i<1000; i++）{
	String msg ="Hello-World!" + i;
	channel.basicPublish("","HelloWorld" ,null,msg.getBytes());
}
//3.确定批量操作是否成功
channel.waitForConfirmsOrDie(); //当你发送的全部消息，有一个失败的时候，就直接全部失败抛出异常IOException
```



> 3.异步confirm

```java
//1. 发布消息到exchange
channel.confirmSelect();
//2.批量发送消息
for (int i = a; i<1000; i++）{
	String msg ="Hello-World!" + i;
	channe1 .basicPublish("","HelloWorld" ,null,msg.getBytes());
}
//3.开启异步回调
channel.addConfirmListener(new ConfirmListener() {
    @Override
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("消息发送成功，标识：" + deliveryTag + ",是否是批量" + multiple);
    }

    @Override
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("消息发送失败，标识：" + deliveryTag + ",是否是批量" + multiple);
    }
});
```





##### Return 机制

```
解决Exchange到Queue消息丢失

Confirm只能保证消息到达cxchange，无法保证消息可以被exchange分发到指定qucue.
而且exchange是不能持久化消息的，queue是可以持久化消息。
采用Return机制来监听消息是否从exchange送到了指定的queue中
```



> 开启Return机制，并在发送消息时，指定mandatory为true

```java
// 开启return机制
channel.addReturnListener(new ReturnListener() {
    @Override
    public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
       // 当消息没有送达到queue时，才会执行。
       System.out.println(new String(body,"UTF-8") + "没有送达到Queue中！！");
    }
});
// 在发送消息时，指定mandatory参数为true
channel.basicPublish("","HelloWorld",true,properties,msg.getBytes());
```



> SpringBoot实现Return机制

```yml
spring:
  rabbitmq:
    publisher-confirm-type: simple
    publisher-returns: true
```

```java
//开启Confirm和Return

@Component
public class PublisherConfirmAndReturnConfig implements RabbitTemplate.ConfirmCallback ,RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct  // init-method
    public void initMethod(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            System.out.println("消息已经送达到Exchange");
        }else{
            System.out.println("消息没有送达到Exchange");
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息没有送达到Queue");
    }
}

```



#### 重复消费

```
为了解决消息重复消费的问题，可以采用Redis，在消费者消费消息之前，现将消息的id放到Redis中,
id-0(正在执行业务)
id-1（执行业务成功)
如果ack失败，在RabbitMQ将消息交给其他的消费者时，
先获取key,存在，如果为0，则什么都不做，为1，直接ack；不存在设置为0，执行业务，再设置为1，最后ack

极端情况:第一个消费者在执行业务时，出现了死锁，在设置redis时，再给key设置一个生存时间。
```



> 生产者，发送消息时，指定messageld

```java
//生产者，发送消息时，指定messageld
AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
    .deliveryMode(1)     //指定消息书否需要持久化 1 - 需要持久化  2 - 不需要持久化
    .messageId(UUID.randomUUID().toString())
    .build();
String msg = "Hello-World！";
channel.basicPublish("","HelloWorld",true,properties,msg.getBytes());
```

> 消费者，在消费消息时，根据具体业务逻辑去操作redis

```java
//4. 开启监听Queue
DefaultConsumer consume = new DefaultConsumer(channel){
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        Jedis jedis = new Jedis("192.168.199.109",6379);
        String messageId = properties.getMessageId();
        //1. setnx到Redis中，默认指定value-0
        String result = jedis.set(messageId, "0", "NX", "EX", 10);
        if(result != null && result.equalsIgnoreCase("OK")) {
            System.out.println("接收到消息：" + new String(body, "UTF-8"));
            //2. 消费成功，set messageId 1
            jedis.set(messageId,"1");
            channel.basicAck(envelope.getDeliveryTag(),false);
        }else {
            //3. 如果1中的setnx失败，获取key对应的value，如果是0，return，如果是1
            String s = jedis.get(messageId);
            if("1".equalsIgnoreCase(s)){
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        }
    }
};
channel.basicConsume("HelloWorld",false,consume);
```



> SpringBoot实现

```java
//生产者
@Test
void contextLoads() throws IOException {
    CorrelationData messageId = new CorrelationData(UUID.randomUUID().toString());
    rabbitTemplate.convertAndSend("boot-topic-exchange","slow.red.dog","红色大狼狗！！",messageId);
    System.in.read();
}
```

```java
//消费者
@RabbitListener(queues = "boot-queue")
public void getMessage(String msg, Channel channel, Message message) throws IOException {
    //0. 获取MessageId
    String messageId = message.getMessageProperties().getHeader("spring_returned_message_correlation");
    //1. 设置key到Redis
    if(redisTemplate.opsForValue().setIfAbsent(messageId,"0",10, TimeUnit.SECONDS)) {
        //2. 消费消息
        System.out.println("接收到消息：" + msg);

        //3. 设置key的value为1
        redisTemplate.opsForValue().set(messageId,"1",10,TimeUnit.SECONDS);
        //4.  手动ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }else {
        //5. 获取Redis中的value即可 如果是1，手动ack
        if("1".equalsIgnoreCase(redisTemplate.opsForValue().get(messageId))){
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }
    }
}
```



### 