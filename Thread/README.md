# 多线程

参考：[JAVA多线程](https://blog.csdn.net/zl1zl2zl3/article/details/81868173)  [对象锁、锁池、等待池](https://blog.csdn.net/u014561933/article/details/58639411)  [死锁](https://blog.csdn.net/hd12370/article/details/82814348)  [千锋教育多线程](https://www.bilibili.com/video/BV1Sp4y1U7tQ?p=44)

```java
public void test() {
    log.info(Thread.currentThread().getName()+"\t"+"...testB");
	try { 
        TimeUnit.SECONDS.sleep(1); 
    } catch (InterruptedException e) { 
        e.printStackTrace(); 
    }
}
```



## 线程概括



### 进程与进程

> 进程

> **进程是系统进行资源分配的基本单位**，**也是独立运行的基本单位** 多个进程可以同时存在于内存中，能在一段时间内同时运行，在windows操作中，可以打开任务管理器看到各种各样的进程和对应的PID，并且都占用了一定的系统资源。单核CPU在同一个时刻，只能运行一个进程。所谓同时运行是宏观上的概念，微观上进程之间是在不停地快速切换



> 线程

> 线程又称**轻量级进程**（Light Weight Process）,它是进程内一个相对独立的、可调度的执行单元，也是CPU的基本调度单位。一个进程由一个或多个线程组成，彼此间完成不同的工作，同时执行，称为多线程，此处的同时执行也是宏观上的。在windows操作系统中，可以打开任务管理器，找到性能分页下的资源管理器，可以查看每个进程所拥有的线程数



###  进程和线程的区别

> 1. 进程是操作系统资源分配的基本单位，而线程是CPU的基本调度单位。
> 2. 一个程序运行后之后有一个进程。
> 3. 一个进程可以包含多个线程，但是至少需要有一个线程，否则这个线程是没有意义的。
> 4. 进程间不能共享数据段地址，但同进程的线程之间可以。



### 线程的创建

#### 继承Thread类，重写run方法。

```java
/**
 * 线程类
 */
public class MyThread extends Thread{
	@Override
	public void run() {
		for(int i=0;i<10;i++) {
			System.out.println(i);
		}
	}
}
```

```java
public class testMyThread {
	public static void main(String[] args) {
		//创建线程对象
		MyThread myThread=new MyThread();
		//启动子线程
		myThread.start();
		for(int i=0;i<10;i++) {
			System.out.println("----"+i);
		}
	}
}
```



#### 实现Runnable接口

```java
/**
 * 实现Runnable接口
 */
public class MyRunnable implements Runnable{
	@Override
	public void run() {
		for(int i=0;i<10;i++) {
			System.out.println(Thread.currentThread().getName()+"："+i);
		}
	}
}
```

```java
public class testRunnable {
	public static void main(String[] args) {
        //创建MyThread对象，实现run功能
        MyRunnable myRunnable=new MyRunnable();
        //创建线程类
        Thread thread=new Thread(myRunnable, "子线程");
        //启动线程
        thread.start();
        for(int i=0;i<10;i++) {
            System.out.println("主线程："+i);
        }
    }
}
```



#### 实现Callable接口

>**与Runnable接口的区别**：
>
>1. Callable接口中call方法有返回值，Runnable接口中run方法没有返回值。
>2. Callable接口中call方法有声明异常，Runnable接口中run方法没有异常。

```java
//Callable具有泛型返回值、可以声明异常。
public interface Callable<V>{
    public V call() throws Exception;
}
```

```java
/**
 * 演示Callable接口的使用
 * 功能需求：使用Callable实现1-100的和。
 */
public class Demo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//1.创建Callable对象
		Callable<Integer> callable=new Callable<Integer>() {
			private int sum=0;
			@Override
			public Integer call() throws Exception {
				for(int i=1;i<=100;i++) {
					sum+=i;
				}
				return sum;
			}
		};
		//2.Thread的构造方法中没有带Callable的构造方法
        //需要把Callable对象转成可执行任务，FutureTask表示将要执行的任务
		//该类实现了RunnableFuture<V>接口，而该接口又继承了Runnable类
		FutureTask<Integer> task=new FutureTask<Integer>(callable);
		
		//3.创建线程对象
		Thread thread=new Thread(task);
		//4.启动线程
		thread.start();
		//5.获取结果（等待call方法执行完毕，才会返回）
		Integer sum=task.get();
		System.out.println("结果是"+sum);
	}
}
```



#### Callable结合线程池使用

```java
/**
 * 使用线程池计算1-100的和
 */
public class Demo2 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//1.创建线程池
		ExecutorService executorService=Executors.newFixedThreadPool(1);
		//2.提交任务,Future表示将要执行任务的结果；
        //submit可以传入一个Callable<T>对象
        //如果是多个线程，需要循环提交submit方法
		Future<Integer> future=executorService.submit(new Callable<Integer>() {
			private int sum=0;
			@Override
			public Integer call() throws Exception {
				System.out.println(Thread.currentThread().getName()+"开始计算。。");
				for(int i=1;i<=100;i++) {
					sum+=i;
					Thread.sleep(10);
				}
				return sum;
			}
		});
		//3.获取任务的结果（等待任务完成才会返回）
		System.out.println(future.get());
        //4.关闭线程池
		executorService.shutdown();
	}
}
```



#### Future接口

> Future**：**表示将要完成任务的结果。

```java
/**
 * 演示Future接口的使用
 */
public class Demo3 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//1.创建线程池
		ExecutorService executorService=Executors.newFixedThreadPool(2);
		//2.提交任务
		Future<Integer> future1=executorService.submit(new Callable<Integer>() {
			int sum=0;
			@Override
			//计算1-50的和
			public Integer call() throws Exception {
				for(int i=1;i<=50;i++) {
					sum+=i;
				}
				System.out.println("1-50的和计算完毕。");
				return sum;
			}
		});
		Future<Integer> future2=executorService.submit(new Callable<Integer>() {
			int sum=0;
			@Override
			//计算51-100的和
			public Integer call() throws Exception {
				for(int i=51;i<=100;i++) {
					sum+=i;
				}
				System.out.println("51-100的和计算完毕。");
				return sum;
			}
		});
		//3.获取结果
		System.out.println(future1.get()+future2.get());
		//4.关系线程池
		executorService.shutdown();
	}
}
```





### 线程池

Executors工厂类：创建线程池的工具类

> 1. 创建固定线程个数的线程池。
> 2. 创建缓存线程池，由任务的多少决定。
> 3. 创建单线程池。
> 4. 创建调度线程池。调度：周期、定时执行。





### Lock接口

>+ JDK5加入，与synchronized比较，显示定义，结构更灵活
>+ 提供更多实用性方法，功能更强大、性能更优越
>+ 常用方法:
>  + void lock() //获取锁，如锁被占用，则等待
>  + boolean tryLock() //尝试获取锁（成功返回true。失败返回false，不阻塞)
>  + void unlock() //释放锁



### 重入锁

> ReentrantLock： Lock接口的**实现类**，与synchronized一样具有互斥锁功能。
>
> 所谓重入锁，**是指一个线程拿到该锁后**，**还可以再次成功获取**，而不会因为该锁已经被持有（尽管是自己所持有）而陷入等待状态（死锁）。**之前说过的synchronized也是可重入锁**。
>
> 可重复可递归调用的锁，在外层使用锁之后，在内层仍然可以使用，并且不发生死锁（前提得是同一个对象或者class），这样的锁就叫做可重入锁 

```java
//重入锁的使用
public class Ticket implements Runnable{
    int ticket=100;
    //创建重入锁对象
    Lock lock=new ReentrantLock();
    @Override
    public void run() {	
        while(true) {	
            //上锁		
            lock.lock();
            try {					
                if(ticket>0)
                    System.out.println(Thread.currentThread().getName()+"卖出了一张票，还剩"+(--ticket)+"张。");
                else break;
            } finally {
                //解锁
                lock.unlock();
            }	
        }
    }
}
```

```java
public class testTicket {
	public static void main(String[] args) {
		Ticket ticket=new Ticket();
		//1.创建线程池
		ExecutorService eService=Executors.newFixedThreadPool(4);
		//2.提交四次，让四个线程来运行
		for(int i=0;i<4;i++) {
			eService.submit(new Ticket());
		}
		eService.shutdown();
	}
}
```



### 读写锁

> ReentrantReadWriteLock
>
> + 一种支持一写多读的同步锁，读写分离，可分别分配读锁、写锁。
>
> + 支持多次分配读锁，使多个读操作可以并发执行
> + 在读操作远远高于写操作的环境中，可在保障线程安全的情况下，提高运行效率



>互斥规则：
>
>- 写—-写：互斥，一个线程在写的同时其他线程会被阻塞
>- 读—-写：互斥，读的时候不能写，写的时候不能读
>- 读—-读：不互斥、不阻塞
>- 在读操作远远高于写操作的环境中，可在保证线程安全的情况下，提高运行效率



```java
//演示读写锁的使用
public class ReadWriteLock {
		//创建读写锁对象
		ReentrantReadWriteLock rrlLock=new ReentrantReadWriteLock();
		ReadLock readLock=rrlLock.readLock();//获得读锁
		WriteLock writeLock=rrlLock.writeLock();//获得写锁
		private int value=999;
		//读方法
		public int getValue() {
			readLock.lock();//开启读锁
			try {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return this.value;
			} finally {
				readLock.unlock();//释放读锁
			}			
		}
		//写方法
		public void setValue(int value) {
			writeLock.lock();//开启写锁
			try {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.value=value;
			} finally {
				writeLock.unlock();//释放写锁
			}	
		}
}
```

```java
public class testReadWriteLock {
	public static void main(String[] args) {
		ExecutorService eService=Executors.newFixedThreadPool(20);
		ReadWriteLock rwlLock=new ReadWriteLock();
		Runnable read=new Runnable() {		
			@Override
			public void run() {
				System.out.println(rwlLock.getValue());
			}
		};
		Runnable write=new Runnable() {		
			@Override
			public void run() {
				rwlLock.setValue(666);
				System.out.println("改写为666");
			}
		};
		//写2次
		for(int i=0;i<2;i++) {
			eService.submit(write);
		}
		//读18次
		for(int i=0;i<18;i++) {
			eService.submit(read);
		}	
		eService.shutdown();
	}
}
```



### 线程安全集合

> 下图中蓝色的表示线程安全的集合，绿色表示现代开发中已经很少使用的线程安全的集合 



#### Collection集合

<img src="/images/Collection集合.png"/>

#### Map集合

![](/images/Map安全集合.png)



#### Collections中工具方法

> Collections工具类提供线程安全集合的方法
>
> JDK1.5之前接口统一、维护性高，但性能没有提升，均以synchonized实现

![](/images/Collections中工具方法.png)



#### CopyOnWriteArrayList

> - 线程安全的ArrayList，加强版的读写分离
> - 写有锁，读无锁，读写之间不堵塞，优于读写锁
> - 写入时，先copy一个容器副本、再添加新元素，最后替换引用。所以说它是用空间换安全的一种方式
> - 使用ArrayList无异

```java
/**
 * 演示CopyOnWriteArrayList的使用
 */
public class Demo2 {
    public static void main(String[] args) {
        //1.创建集合
        CopyOnWriteArrayList<String> list=new CopyOnWriteArrayList<String>();
        //2.使用多线程操作
        ExecutorService eService=Executors.newFixedThreadPool(5);
        //3.提交任务
        for(int i=0;i<5;i++) {
            eService.submit(new Runnable() {			
                @Override
                public void run() {
                    for(int j=0;j<10;j++) {
                        list.add(Thread.currentThread().getName()+"..."+new Random().nextInt(1000));
                    }
                }
            });			
        }
        //4.关闭线程池
        eService.shutdown();
        //等所有线程都执行完毕 -> 空旋
        while(!eService.isTerminated());
        //5.打印结果
        System.out.println("元素个数："+list.size());
        for (String string : list) {
            System.out.println(string);
        }
    }
}
```



#### CopyOnWriteArraySet

> - 线程安全的Set，底层使用CopyOnWriteArrayList实现，所以会有顺序
> - 唯一不同在于，使用`addIfAbsent()`添加元素，会遍历数组，如果已有元素（比较依据是equals），则不添加（扔掉副本）

```java
//演示CopyOnWriteArraySet的使用
public class Demo3 {
	public static void main(String[] args) {
		CopyOnWriteArraySet<String> set=new CopyOnWriteArraySet<String>();
		set.add("tang");
		set.add("he");
		set.add("yu");
		set.add("wang");
		set.add("tang");//重复元素，添加失败
		System.out.println(set.size());
		System.out.println(set.toString());
	}
}
```



### Queue接口（队列）

> Collection的子接口，表示队列FIFO（First In First Out），先进先出。 

>  常用方法：
>
> - 抛出异常：
>
>   - `boolean add(E e)`
>
>     顺序添加一个元素（到达上限后，再添加则会抛出异常）。
>
>   - `E remove()`
>
>     获得第一个元素并移除（如果队列没有元素时，则抛出异常）。
>
>   - `E element()`
>
>     获得第一个元素但不移除（如果队列没有元素时，则抛异常）。
>
> - 返回特殊值：（**建议使用以下方法**）
>
>   - `boolean offer(E e)`
>
>     顺序添加一个元素（到达上限后，再添加则会返回false）。
>
>   - `E poll()`
>
>     获得第一个元素并移除（如果队列没有元素时，则返回null）。
>
>   - `E peek()`
>
>     获得第一个元素但不移除（如果队列没有元素时，则返回null）。

```java
//演示Queue实现类的使用
public class Demo4 {
	public static void main(String[] args) {
		//创建队列
		Queue<String> queue=new LinkedList<String>();
		//入队
		queue.offer("tang");
		queue.offer("he");
		queue.offer("yu");
		queue.offer("wang");
		queue.offer("fan");
		System.out.println("队首元素："+queue.peek());
		System.out.println("元素个数："+queue.size());
		//出队
		int size=queue.size();
		for(int i=0;i<size;i++) {
			System.out.println(queue.poll());
		}
		System.out.println("出队完毕："+queue.size());
	}
}
```





#### ConcurrentLinkedQueue类

> - Queue接口的实现类。线程安全、可高效读写的队列，高并发下性能最好的队列。
>
> - 无锁、CAS（Compare and Swap）比较交换算法，修改的方法包含三个核心参数（V,E,N）。
>
> - V：要更新的变量；E：预期值；N：新值。
>
> - 只有当V==E，V=N；否则表示V已被更新过，则取消当前操作。
>
>   也就是说假如当前值V是80，要将其改成100，先将V读取出来，读取的V就是预期值；如果预期值E和V相等，就把V的值更新成新值100；如果不等，说明中间有其他线程更新了V，就取消当前操作。

```java
//演示线程安全的队列
public class Demo5 {
	public static void main(String[] args) throws InterruptedException {
		//创建安全队列
		ConcurrentLinkedQueue<Integer> queue=new ConcurrentLinkedQueue<Integer>();
		//两个线程执行入队操作
		Thread t1=new Thread(new Runnable() {		
			@Override
			public void run() {
				for(int i=1;i<=5;i++) {
					queue.offer(i);
				}
			}
		});
		Thread t2=new Thread(new Runnable() {		
			@Override
			public void run() {
				for(int i=6;i<=10;i++) {
					queue.offer(i);
				}
			}
		});
		//启动线程
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		for(int i=1;i<=10;i++) {
			System.out.println(queue.poll());
		}
	}
}
```





#### BlockingQueue接口（阻塞队列）

> - **Queue的子接口**，阻塞的队列，增加了两个线程状态为无限期等待的方法。
>
> - 方法
>
>   - `void put(E e)`
>
>     将指定元素插入此队列中，如果没有可用空间，则等待。
>
>   - `E take()`
>
>     获取并移除此队列头部元素，如果没有可用元素，则等待。
>
> - **可用于解决生产者**、**消费者问题**。



### 

### ExecutorService中对异常的处理

参考：[ExecutorService中对异常的处理](https://blog.csdn.net/huangyaa729/article/details/89474292)





### 多线程使用

```java
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    Integer numThread = 200;//每个线程携带200条数据
    Integer count = list.size() % numThread == 0 ? list.size()/numThread : (list.size()/numThread + 1);
    //记录线程个数
    CountDownLatch latch = new CountDownLatch(count);
    for (int i = 0; i < count; i++) {
        int index = i;
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if(index == count-1){//最后一次
                        List<ImportAttendance2> subList = list.subList(index * numThread,list.size());
                        importAttendanceMapper.importAttendanceData(subList);
                    }else{//除了最后一次
                        List<ImportAttendance2> subList = list.subList(index * numThread,index * numThread + numThread);
                        importAttendanceMapper.importAttendanceData(subList);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    latch.countDown();
                }
            }
        });
    }
    //等待所有线程执行完
    latch.await();
    //异步执行同步存储过程
    cachedThreadPool.execute(new Runnable() {
        @Override
        public void run() {
            importAttendanceMapper.execAttendanceProc();
        }
    });

```

