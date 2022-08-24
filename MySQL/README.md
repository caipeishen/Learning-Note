## MySQL

> 推荐黑马的MySQL：https://www.bilibili.com/video/BV1Kr4y1i7ru



> 资料：

+ MySQL-基础篇
+ MySQL-进阶篇
+ MySQL-运维篇
+ 尚硅谷-高级



> 看黑马视频，看尚硅谷的资料(尚硅谷视频时间太长了)



### 幻读的理解

参考：[幻读理解防链接失效](https://www.cnblogs.com/shujiying/p/13100324.html)   [幻读理解原文](https://segmentfault.com/a/1190000016566788?utm_source=tag-newest)

+ 我理解的幻读是：明明查询不到(普通SELECT也叫快照读)，但不让插入(其他事务中已经插入提交了)，说明读的有问题，所以称为了幻读。
+ MySQL为幻读提供了解决方案是`间隙锁`和`MCVV`解决。这是方案，而不是可重复读隔离级别里面的东西，所以单单的可重复读还是没办法解决幻读
  + 查询的时候就不能是快照读了，应该是当前读（读最新的数据：`select ··· lock in share mode`、`select ··· for update`），具体锁的范围，要看你的查询条件 `where`，例如，查询一个不存在的主键，行锁没办法给该索引加锁，为了解决这种情况，发明了一种间隙锁，锁范围
  + MCVV类似乐观锁的机制，查询undo log版本链。



### 并发问题的解决方案*

> 怎么解决 脏读 、 不可重复读 、 幻读 这些问题呢？其实有两种可选的解决方案： 

+ 方案一：读操作利用多版本并发控制（ **MVCC** ），写操作进行 **加锁** 。 

  普通的SELECT语句在`READ COMMITTED`和`REPEATABLE READ`隔离级别下会使用到MVCC读取记录。 

  + 在 `READ COMMITTED` 隔离级别下，一个事务在执行过程中 **每次执行SELECT操作** 时都会生成一 个ReadView，ReadView的存在本身就保证了 **事务不可以读取到未提交的事务所做的更改** ，也就是避免了脏读现象； 
  + 在 `REPEATABLE READ` 隔离级别下，一个事务在执行过程中只有 **第一次执行SELECT操作** 才会 生成一个ReadView，之后的SELECT操作都 **复用** 这个ReadView，这样也就避免了不可重复读 和幻读的问题。 

+ 方案二：读、写操作都采用 **加锁** 的方式。

  + 采用 **MVCC** 方式的话， **读-写** 操作彼此并不冲突， **性能更高** 。 
  + 采用 **加锁** 方式的话， **读-写** 操作彼此需要 **排队执行** ，影响性能。 



### 前缀索引

参考：https://blog.csdn.net/wdjnb/article/details/122880079



### B+Tree

在线演示：https://www.cs.usfca.edu/~galles/visualization/BPlusTree.html

+ 树左侧：小于等于
+ 树右侧：大于
+ 下方：形成链表，索引叶子结点上数据之间是有单向链表维系的，从左向右，所以接着第一步查找的结果x，继续向后读取下一条记录，然后重复 2、3、4 步，直到在 user_uuid_index 上取到的值不为x 输入的值时，循环结束(如果唯一索引就只会找一次，不会重复；**非唯一索引，索引列值相同时按照主键排序**)。



### 为什么MySQL默认可重复读*

> 为什么MySQL默认可重复读，而大多数数据库(Oracle、SQLServer)是读已提交？

参考： [【原创】互联网项目中mysql应该选什么事务隔离级别](https://www.cnblogs.com/rjzheng/p/10510174.html)

+ RR才会有间隙锁，RC没有



### 分页数据量大时，分页如何优化？

+ 自己理解的事，先缩小范围，再进行limit，例如【limit 200000,10】，MySQL需要排序前2000010记录，仅仅返回2000000-2000010条数据，查询排序的代价太大
+ 官方：通过覆盖索引+子查询，其实就是先通过覆盖索引查询，这样是使用索引的，查询快，再使用子查询查询唯一id，这时数据量就很小了


```sql
优化前：
	select * from tb_sku limit 9000000, 10
	
优化后：

	select * from tb_sku 
	where id in (
		select id from (
			select id from tb_sku order by id limit 9000000, 10
		) a
	)
```



### ACID一致性与CAP一致性浅谈

参考：https://blog.csdn.net/qq_34924156/article/details/112525065



### SQL执行流程

参考：./尚硅谷高级/第04章_逻辑架构.pdf（了解流程和原理即可）



### MVCC多版本并发控制细节

参考：./尚硅谷高级/第16章_多版本并发控制.pdf

+ ReadView判断方式：

  对于使用READ UNCOMMITTED隔离级别的事务来说，直接读取记录的最新版本就好了对于使用SERIALIZABLE隔离级别的事务来说，使用加锁的方式来访问记录。对于使用READ COMMITTED和REPEATABLE READ隔离级别的事务来说，就需要用到我们上边所说的版本链了，核心问题就是：需要判断一下版本链中的哪个版本是当前事务可见的。所以设计InnoDB的大叔提出了一个ReadView的概念，**这个ReadView中主要包含当前系统中还有哪些活跃的读写事务，把它们的事务id放到一个列表中，我们把这个列表命名为为m_ids。**这样在访问某条记录时，只需要按照下边的步骤判断记录的某个版本是否可见：

  + 如果被访问版本的trx_id属性值等于creator_trx_id，表明数据就是当前这个事务该的，所以该版本可以被当前事务访问。
  + 如果被访问版本的trx_id属性值小于m_ids列表中最小的事务id，表明生成该版本的事务在生成ReadView前已经提交，所以该版本可以被当前事务访问。
  + 如果被访问版本的trx_id属性值大于m_ids列表中最大的事务id，表明生成该版本的事务在生成ReadView后才生成，所以该版本不可以被当前事务访问。
  + 如果被访问版本的trx_id属性值在m_ids列表中最大的事务id和最小事务id之间，那就需要判断一下trx_id属性值是不是在m_ids列表中，如果在，说明创建ReadView时生成该版本的事务还是活跃的，该版本不可以被访问；如果不在，说明创建ReadView时生成该版本的事务已经被提交，该版本可以被访问。



### 什么情况会出现死锁

> 死锁是指两个或多个事务在同一资源上相互占用，并请求锁定对方占用的资源，从而导致恶性循环。

参考：./尚硅谷高级/第15章_锁.pdf/3.6 其它锁之：死锁



### 锁细节

参考：./尚硅谷高级/第15章_锁.pdf/附录（了解流程和原理即可）

+ 记录锁（`Record Locks`）

  + 记录锁(行锁)也就是仅仅把一条记录锁上，官方的类型名称为： `LOCK_REC_NOT_GAP` ，对周围的数据没有影响。 

+ 间隙锁（`Gap Locks`）

  gap锁的提出仅仅是为了防止插入幻影记录而提出的。

  + **MySQL** 在 **REPEATABLE READ** 隔离级别下是可以解决幻读问题的，解决方案有两种，可以使用 **MVCC** 方案解决，也可以采用 **加锁** 方案解决。但是在使用加锁方案解决时有个大问题，就是事务在第一次执行读 取操作时，那些幻影记录尚不存在，我们无法给这些 **幻影记录** 加上 **记录锁** 。InnoDB提出了一种称之为 **Gap Locks** 的锁，官方的类型名称为： `LOCK_GAP` ，我们可以简称为 gap锁 。

+ 临键锁（`Next-Key Locks`）

  + 有时候我们既想 **锁住某条记录** ，又想 **阻止** 其他事务在该记录前边的 **间隙插入新记录** ，所以InnoDB就提 出了一种称之为 **Next-Key Locks** 的锁，官方的类型名称为： `LOCK_ORDINARY` ，我们也可以简称为 next-key锁 。Next-Key Locks是在存储引擎 **innodb** 、事务级别在 **可重复读** 的情况下使用的数据库锁， innodb默认的锁就是Next-Key locks。





### -----



### MySQL 常用SQL

```sql
-- 联表更新
UPDATE `ana` a 
INNER JOIN `user` b ON a.user_id = b.id
SET a.comment_num = 66
WHERE b.user_nick_name = 'Cai Peishen'


-- 联表删除
DELETE a
FROM `ana` a
INNER JOIN `user` b ON a.user_id = b.id
WHERE b.user_nick_name = 'Cai Peishen'

-- 联表插入
INSERT INTO ana_new(id,title)
SELECT id,title
FROM ana

-- 复制表数据创建新表
CREATE TABLE ana_back 
SELECT * FROM ana

-- 查询字段(strlist)中是否包含(str)的结果，返回结果为null或记录的位置
SELECT FIND_IN_SET('b', 'a,b,c,d');
```



>Mysql GROUP_CONCAT获取分组的前几名

参考：[mysql GROUP_CONCAT获取分组的前几名](https://blog.csdn.net/qq_34471305/article/details/83347994)



### MySQL分组取每组前几条记录(排序)

参考：[分组取每组前几条记录](https://www.cnblogs.com/duhuo/p/4385642.html)   [每组的前几条记录的方法和理解](https://blog.csdn.net/junzi528/article/details/84404412)



> 初始化数据

```sql
CREATE TABLE `mygoods` (  
  `goods_id` int(11) unsigned NOT NULL AUTO_INCREMENT,  
  `cat_id` int(11) NOT NULL DEFAULT '0',  
  `price` tinyint(3) NOT NULL DEFAULT '0',  
  `status` tinyint(3) DEFAULT '1',  
  PRIMARY KEY (`goods_id`),  
  KEY `icatid` (`cat_id`)  
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;  
  
INSERT INTO `mygoods` VALUES (1, 101, 90, 0);  
INSERT INTO `mygoods` VALUES (2, 101, 99, 1);  
INSERT INTO `mygoods` VALUES (3, 102, 98, 0);  
INSERT INTO `mygoods` VALUES (4, 103, 96, 0);  
INSERT INTO `mygoods` VALUES (5, 102, 95, 0);  
INSERT INTO `mygoods` VALUES (6, 102, 94, 1);  
INSERT INTO `mygoods` VALUES (7, 102, 93, 1);  
INSERT INTO `mygoods` VALUES (8, 103, 99, 1);  
INSERT INTO `mygoods` VALUES (9, 103, 98, 1);  
INSERT INTO `mygoods` VALUES (10, 103, 97, 1);  
INSERT INTO `mygoods` VALUES (11, 104, 96, 1);  
INSERT INTO `mygoods` VALUES (12, 104, 95, 1);  
INSERT INTO `mygoods` VALUES (13, 104, 94, 1);  
INSERT INTO `mygoods` VALUES (15, 101, 92, 1);  
INSERT INTO `mygoods` VALUES (16, 101, 93, 1);  
INSERT INTO `mygoods` VALUES (17, 101, 94, 0);  
INSERT INTO `mygoods` VALUES (18, 102, 99, 1);  
INSERT INTO `mygoods` VALUES (19, 105, 85, 1);  
INSERT INTO `mygoods` VALUES (20, 105, 89, 0);  
INSERT INTO `mygoods` VALUES (21, 105, 99, 1);
```



> 每个分类找出价格最高的有效的两个商品 

```sql
select a.* 
from mygoods a 
where (
	select count(1) from mygoods 
	where cat_id = a.cat_id and price > a.price and status=1  
	) < 2 
and status=1 
order by a.cat_id,a.price desc ;
```



> 每个分类找出价格最高的有效的两个商品 

```sql
select a.* 
from mygoods a 
left join mygoods b 
on a.cat_id = b.cat_id and a.price < b.price and b.status=1
where a.status=1
group by a.goods_id,a.cat_id,a.price
having count(b.goods_id) < 2
order by a.cat_id,a.price desc;
```



### MySQL内查询构造动态日期表

```sql
#		startDate	  endDate
# 有条件：day1 		- 	day2
# 无条件：now()-7 	- 	now()
# java进行处理过后，向dao层串，startDate, endDate
 
-- SELECT
-- 	DATE( date ) signtime 
-- FROM
-- 	(#           构造当前日期之前31天的日期表
-- 	SELECT @cdate := date_add(@cdate, INTERVAL - 1 DAY) date
--                  FROM (SELECT @cdate := date_add(CURRENT_DATE, INTERVAL 1 DAY) FROM ana LIMIT datediff('2018-06-26','2018-06-25')) a
-- 	) t_date 
 
SELECT
	DATE( date ) signtime 
FROM
	(#           构造动态日期表
	SELECT @cdate := date_add(@cdate, INTERVAL - 1 DAY) date
                 FROM (SELECT @cdate := date_add(endDate, INTERVAL 1 DAY) FROM ana LIMIT datediff(startDate,endDate)) a
	) t_date

```



### MySQL动态数字生成

参考：[MySQL动态数字生成](https://blog.csdn.net/qiuli_liu/article/details/81707562)

> 分析：from后边的 (SELECT @xi:=0) xc0 是定义@xi的初始值为0，另外的两个[union](https://so.csdn.net/so/search?q=union&spm=1001.2101.3001.7020)是为了计算多少个数据 

```sql
SELECT @xi:=@xi+1 as xc from 
(SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) xc1, 
(SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) xc2,  
(SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) xc3,  
(SELECT @xi:=0) xc0 
```

