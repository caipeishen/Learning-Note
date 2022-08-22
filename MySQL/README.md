## MySQL

> 推荐黑马的MySQL：https://www.bilibili.com/video/BV1Kr4y1i7ru



> 资料：

+ MySQL-基础篇
+ MySQL-进阶篇
+ MySQL-运维篇





### 前缀索引

参考：https://blog.csdn.net/wdjnb/article/details/122880079

+ 5.索引叶子结点上数据之间是有单向链表维系的，所以接着第一步查找的结果，继续向后读取下一条记录，然后重复 2、3、4 步，直到在 user_uuid_index 上取到的值不为 39352f81-1 时，循环结束。
+ 如果我们建立了前缀索引并且前缀索引的选择性为 1，那么就不需要第 5 步了，如果前缀索引选择性小于 1，就需要第五步。



### B+Tree

+ 树左侧：小于等于
+ 树右侧：大于
+ 下方：形成链表，索引叶子结点上数据之间是有单向链表维系的，所以接着第一步查找的结果x，继续向后读取下一条记录，然后重复 2、3、4 步，直到在 user_uuid_index 上取到的值不为x 输入的值时，循环结束。



### 为什么MySQL默认可重复读

> 为什么MySQL默认可重复读，而大多数数据库(Oracle、SQLServer)是读已提交？

参考：https://blog.51cto.com/u_15485936/5202149

+ 我们都知道事务的几种性质 :原子性、一致性、隔离性和持久性 (ACID)
  为了维持一致性和隔离性,一般使用加锁这种方式来处理,但是加锁相对带来的是并发处理能力的降低
+ 可重复读(Repeated Read)：可重复读。基于锁机制并发控制的DBMS需要对选定对象的读锁(read locks)和写锁(write locks)一直保持到事务结束，但不要求“范围锁(range-locks)”，因此可能会发生“幻影读(phantom reads)” 在该事务级别下，保证同一个事务从开始到结束获取到的数据一致。是Mysql的默认事务级别。
+ 读已提交，binlog如果是statement模式，就可能会出现从库同步问题(需要加入间隙锁)，或者5.1之后使用row模式同步



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



### 啥时候用表锁，啥时候用行锁？

参考：https://www.cnblogs.com/chanshuyi/p/mysql-table-lock-and-row-lock.html



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



### Oracle 使用SQL备份与恢复

```sql
1.1 完全备份
exp demo/demo@orcl buffer=1024 file=d：\back.dmp full=y

1.2 完全还原
imp demo/demo@orcl file=d:\back.dmp full=y ignore=y log=D:\implog.txt

2.1 导出指定表
exp demo/demo@orcl file=d:\backup2.dmp tables=(teachers,students)

2.2 导入指定表
imp demo/demo@orcl file=d:\backup2.dmp tables=(teachers,students)
```

详情：https://blog.csdn.net/yztezhl/article/details/80451046



## 



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

