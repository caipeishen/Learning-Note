## DataBase

### 事务特性和隔离级别

参考：[事务特性和隔离级别](https://www.cnblogs.com/lztkdr/p/Transaction.html)  [资料补充](https://blog.csdn.net/qq_33290787/article/details/51924963)  [如何理解一致性 推到出如何理解隔离性](https://blog.csdn.net/qq_37997523/article/details/83188003)



### SQL	ACID

```
A	原子性
C 	一致性
I 	隔离性
D 	持久性

原子性关注状态，要么全部成功，要么全部失败，不存在部分成功的状态。
而一致性关注数据的可见性，中间状态的数据对外部不可见，只有最初状态和最终状态的数据对外可见
```



### NoSQL	CAP + BASE

```
C	强一致性
A	高可用性
P	分区容错性
BASE	基本可用 软状态 最终一致

它的思想是通过系统放松某一时刻数据一致性的要求，来换取系统整体伸缩性和性能上改观
```



### CAP

参考：[CAP 定理的含义](http://www.ruanyifeng.com/blog/2018/07/cap.html)

```
C:
一致性，原文翻译过来是说，对于任何从客户端发达到分布式系统的数据读取请求，要么读到最新的数据要么失败。换句话说，一致性是站在分布式系统的角度，对访问本系统的客户端的一种承诺：要么我给您返回一个错误，要么我给你返回绝对一致的最新数据，不难看出，其强调的是数据正确。


A:
对于任何请求从客户端发达到分布式系统的数据读取请求，都一定会收到数据，不会收到错误，但不保证客户端收到的数据一定是最新的数据。换句话说，可用性是站在分布式系统的角度，对访问本系统的客户的另一种承诺：我一定会给您返回数据，不会给你返回错误，但不保证数据最新，强调的是不出错。


P:
分布式系统应该一直持续运行，即使在不同节点间同步数据的时候，出现了大量的数据丢失或者数据同步延迟。分区容忍性是站在分布式系统的角度，对访问本系统的客户端的再一种承诺：我会一直运行，不管我的内部出现何种数据同步问题，强调的是不挂掉。

对于一个分布式系统而言，P是前提，必须保证，因为只要有网络交互就一定会有延迟和数据丢失，这种状况我们必须接受，必须保证系统不能挂掉。试想一下，如果稍微出现点数据丢包，我们的整个系统就挂掉的话，我们为什么还要做分布式呢？所以，按照CAP理论三者中最多只能同时保证两者的论断，对于任何分布式系统，设计时架构师能够选择的只有C或者A，要么保证数据一致性（保证数据绝对正确），要么保证可用性（保证系统不出错）。


当为了满足分区容忍（多数据节点之间备份）；要么保证强一致性，要么保证可用性；保证强一致性时，则在数据同步给其它节点前，是不可用的；而保证可用性，在数据同步给其它节点前，也是可以读取到的，但是可能数据不是最新的而已。如果既保证一致性又保证可用性，也就是放弃分区容忍，那么就是数据只存在一个节点上，那么当这个数据节点与网络中其它服务出现通信故障，也就是出现网络分区时，那么其它服务就读取不到数据节点了，那么此时可用性也无法保证了，因为不可用了
```



16个库

select 0-15 	切换

DBSIZE		 查看当前库有多少key

plushDB	清除当前库的key

plushALL	 清除所有库的key



### 索引面试题

参考: [索引面试题](https://www.cnblogs.com/Brambling/p/6754993.html)



### SQL Server 常用SQL

```sql
-- 联表更新
update u set u.money= u.money+10
from user u
inner join dept d on d.id = u.deptId 
where d.deptName = '开发'

-- 联表删除
delete u 
from user u
inner join dept d on d.id = u.deptId
where d.deptName = '开发'

-- 联表插入
insert into user_new(id,name)
select id,name 
from user

-- 复制表数据创建新表
select id,name 
into user_new 
from user
```



### ROW_NUMBER() OVER()

参考：[ROW_NUMBER() OVER()函数用法详解](https://blog.csdn.net/qq_25221835/article/details/82762416)

```sql
-- 根据dept_id分组
select 
    name,
    dept_id,
    salary,
    row_number() over (partition by dept_id order by salary desc) rank
from user t
```



### SQL Server 知识点

```sql
三元运算

	iif(布尔,值1,值2)		

截取
	substring(值，开始位置，长度)
	select substring('123456',2,2)

```



### SQL Server JSON转表

```sql
declare @users varchar(max) = '[{"id":1,"name":"Cps"},{"id":2,"name":"Xy"}]'		

SELECT *  INTO #users
FROM OPENJSON(@users)
WITH (
    id  INT,
    name varchar(200)
)
```


### SQL Server 表拼接SQL

```sql
-- 需要修改的排班（那些存在的数据）
DECLARE @update_update VARCHAR(MAX)=''
SELECT @update_update+='UPDATE kt_paiba SET '+col_name+'='''+isnull(value,'')+''',gly_no = '''+@operator+''' WHERE rq='''+year_month+''' AND user_serial='+CAST(user_serial AS VARCHAR(10))+';'
FROM #result_update a

-- 执行拼接的SQL
EXEC(@update_update)	
```



### SQL Server 行专列

参考：[SQL Server 行专列](https://www.cnblogs.com/Rawls/p/11027413.html)



### SQL Server 字段行转列

```sql
-- 拆分数据，行转列
-- APPLY有两种形式，一个是OUTER APPLY，一个是CROSS APPLY，区别在于指定OUTER，意味着结果集中将包含使右表表达式为空的左表表达式中的行，而指定CROSS，则相反，结果集中不包含使右表表达式为空的左表表达式中的行。

SELECT
	v.value
FROM zt_glbc t
CROSS APPLY STRING_SPLIT(CAST(t.Valuse AS VARCHAR(MAX)), ',') v
WHERE bh = 13
```



### SQL Server 判断表或存储过程

```sql
--判断表
if   object_id('tb_table') is not null  
	print 'exist' 
else 
	print'not exist' 

--判断存储过程
if exists (select 1
          from sysobjects
          where  id = object_id('bdSettlementDept')
          and type in ('P','PC'))
   drop procedure bdSettlementDept
go
```



### SQL Server存储过程

```sql
USE scm_main;
-- 需要先创建拆分字符串存储过程（split_str）
if exists (select 1
          from sysobjects
          where  id = object_id('bd_proc_schedule_gui_lv')
          and type in ('P','PC'))
DROP PROCEDURE bd_proc_schedule_gui_lv
GO
CREATE PROCEDURE bd_proc_schedule_gui_lv
	@user_serial_str varchar(max),
	@begin_date date,
	@end_date date,
	@ban_ci int,
	@ip varchar(max),
	@operator varchar(max)
as
BEGIN

-- 关闭打印受影响行数
SET NOCOUNT ON;


-- 定义返回受影响行数结果
	declare @result int = 0;


	--  异常扑捉机制
		BEGIN TRY

			CREATE TABLE #ban_ci(
				id INT IDENTITY,
				name VARCHAR(20)
			)

			CREATE TABLE #user_serial(
				id INT IDENTITY,
				user_serial VARCHAR(20)
			)

			CREATE TABLE #result(
			  user_serial  INT,
			  col_name VARCHAR(20),
			  value VARCHAR(100),
			  date DATE,
			  year_month VARCHAR(10)
			)

			CREATE TABLE #result_insert(
			  user_serial  INT,
			  col_name VARCHAR(20),
			  value VARCHAR(100),
			  date DATE,
			  year_month VARCHAR(10)
			)

			CREATE TABLE #result_update(
			  user_serial  INT,
			  col_name VARCHAR(20),
			  value VARCHAR(100),
			  date DATE,
			  year_month VARCHAR(10)
			)


			CREATE TABLE #kt_paiba (
			  user_serial bigint NOT NULL,
			  rq nvarchar(10) NOT NULL,
			  d1 varchar(100) NULL,
			  d2 varchar(100) NULL,
			  d3 varchar(100) NULL,
			  d4 varchar(100) NULL,
			  d5 varchar(100) NULL,
			  d6 varchar(100) NULL,
			  d7 varchar(100) NULL,
			  d8 varchar(100) NULL,
			  d9 varchar(100) NULL,
			  d10 varchar(100) NULL,
			  d11 varchar(100) NULL,
			  d12 varchar(100) NULL,
			  d13 varchar(100) NULL,
			  d14 varchar(100) NULL,
			  d15 varchar(100) NULL,
			  d16 varchar(100) NULL,
			  d17 varchar(100) NULL,
			  d18 varchar(100) NULL,
			  d19 varchar(100) NULL,
			  d20 varchar(100) NULL,
			  d21 varchar(100) NULL,
			  d22 varchar(100) NULL,
			  d23 varchar(100) NULL,
			  d24 varchar(100) NULL,
			  d25 varchar(100) NULL,
			  d26 varchar(100) NULL,
			  d27 varchar(100) NULL,
			  d28 varchar(100) NULL,
			  d29 varchar(100) NULL,
			  d30 varchar(100) NULL,
			  d31 varchar(100) NULL,
			  gly_no nvarchar(10) NULL,
			  year int ,
			  month int ,
			)


		-- 开启事务
			BEGIN TRAN


				-- 拆分用户编号保存临时表中
				insert into #user_serial
				select * from string_split(@user_serial_str,',');

				-- 先清除表中存在的数据
				--delete a
				--from kt_paiba a
				--inner join #user_serial b on a.user_serial = b.user_serial
				--where a.rq = CONVERT(varchar(7),@begin_date,120) or a.rq = CONVERT(varchar(7),@end_date,120)


				-- 拆分班次保存临时表中
				INSERT INTO #ban_ci
				SELECT v.value FROM zt_glbc t CROSS APPLY STRING_SPLIT(CAST(t.Valuse AS VARCHAR(MAX)), ',') v WHERE bh = @ban_ci

				declare @lx int;
				select @lx = lx from zt_glbc where bh = @ban_ci;

				-- 规律班次类型：周
				IF @lx = 1 BEGIN
					INSERT INTO #result (user_serial, col_name, value, date, year_month)
					select t1.user_serial,t2.* from  (select * from #user_serial) t1,
					(
						SELECT 'd'+CAST(d.date_day AS VARCHAR(2)) colName, b.name,d.date,CONVERT(VARCHAR(7),date,120) date_month
						FROM bd_sys_dim_date d
						LEFT JOIN #ban_ci b ON b.id=d.date_week
						WHERE date >= @begin_date AND date <= @end_date
					) t2
				END
				-- 按月或者日
				ELSE BEGIN
					INSERT INTO #result (user_serial, col_name, value, date, year_month)
					select t1.user_serial,t2.* from  (select * from #user_serial) t1,
					(
						SELECT 'd'+CAST(d.date_day AS VARCHAR(2)) colName, b.name,d.date,CONVERT(VARCHAR(7),date,120) date_month
						FROM bd_sys_dim_date d
						LEFT JOIN #ban_ci b ON b.id=d.date_day
						WHERE date >= @begin_date AND date <= @end_date
					) t2
				END


				-- select * from A,B 不指定条件那么会产生A的所有B数据，刚好满足需求
				--INSERT INTO #result (user_serial, col_name, value, date, year_month)
				--select t1.user_serial,t2.* from  (select * from #user_serial) t1,
				--(
				--	SELECT 'd'+CAST(d.date_day AS VARCHAR(2)) colName, b.name,d.date,CONVERT(VARCHAR(7),date,120) date_month
				--	FROM bd_sys_dim_date d
				--	LEFT JOIN #ban_ci b ON b.id=d.date_week
				--	WHERE date >= @begin_date AND date <= @end_date
				--) t2


				-- 需要新增的数据 #result_insert
				insert into #result_insert(user_serial,col_name,value,date,year_month)
				select a.user_serial,a.col_name,a.value,a.date,a.year_month
				from #result a
				inner join (
					select user_serial,convert(varchar(7),date,120) year_month from #result
					except
					select user_serial,rq as year_month from kt_paiba
				) b on a.user_serial = b.user_serial and a.year_month = b.year_month


				-- 需要修改的数据 #result_update
				insert into #result_update(user_serial,col_name,value,date,year_month)
				select a.user_serial,a.col_name,a.value,a.date,a.year_month
				from #result a
				inner join (
					select user_serial,convert(varchar(7),date,120) year_month from #result
					Intersect
					select user_serial,rq as year_month from kt_paiba
				) b on a.user_serial = b.user_serial and a.year_month = b.year_month


				-- select * from #result_insert
				-- select * from #result_update
				-- select * from #result

				-- 将插入的数据存入到临时排班表中
				-- 下面两段代码将竖着的数据转为了横着的数据
				INSERT INTO #kt_paiba (user_serial, rq, gly_no, year, month)
				SELECT distinct a.user_serial,CONVERT(VARCHAR(7),date,120),@operator,YEAR(date),MONTH(date)
				FROM #result_insert a


				-- 循环拼接需要更新临时表中的字段
				-- 查询（循环）拼接数据（竖着循环）
				DECLARE @update_insert VARCHAR(MAX)=''
				SELECT @update_insert+='UPDATE #kt_paiba SET '+col_name+'='''+value+''',gly_no = '''+@operator+''' WHERE rq='''+year_month+''' AND user_serial='+CAST(a.user_serial AS VARCHAR(10))+';'
				FROM #result_insert a


				-- 执行拼接的语句
				EXEC(@update_insert)


				-- 从临时表插入到真实表中
				insert into kt_paiba
				select * from #kt_paiba


				-- 需要修改的排班（那些存在的数据）
				DECLARE @update_update VARCHAR(MAX)=''
				SELECT @update_update+='UPDATE kt_paiba SET '+col_name+'='''+value+''',gly_no = '''+@operator+''' WHERE rq='''+year_month+''' AND user_serial='+CAST(user_serial AS VARCHAR(10))+';'
				FROM #result_update a


				-- 执行拼接的SQL
				EXEC(@update_update)


				-- 新增的生成日志记录
				insert into wt_log(log_fun,log_type,log_detail,gly_no,log_time,log_computer,log_ip,regserial)
				select distinct 13,1,isnull(@operator,'')+'录入'+isnull(b.user_lname,'')+isnull(a.year_month,'')+'排班',isnull(@operator,''),getdate(),'',isnull(@ip,''),''
				from #result_insert a
				inner join dt_user b on a.user_serial = b.user_serial

				set @result = @result + @@ROWCOUNT;


				--更新的生成日志记录
				insert into wt_log(log_fun,log_type,log_detail,gly_no,log_time,log_computer,log_ip,regserial)
				select distinct 13,1,isnull(@operator,'')+'更新'+isnull(b.user_lname,'')+isnull(a.year_month,'')+'排班',isnull(@operator,''),getdate(),'',isnull(@ip,''),''
				from #result_update a
				inner join dt_user b on a.user_serial = b.user_serial


				set @result = @result + @@ROWCOUNT;


		--  提交事务
			COMMIT TRAN

	--  结束异常捕捉
		END TRY

	--  异常处理
		BEGIN CATCH
			DECLARE @errStr VARCHAR(MAX) = ERROR_MESSAGE();
			IF XACT_STATE() <> 0
			BEGIN
			  ROLLBACK TRANSACTION;
			  RAISERROR (@errStr, 16, 1);
			END;
			ROLLBACK TRAN;
		END CATCH



--  清除临时表
	select @result as result
	DROP TABLE #ban_ci
	DROP TABLE #user_serial
	DROP TABLE #kt_paiba

-- 打开打印受影响行数
SET NOCOUNT OFF;
END;

```



### SQL Server 留下的坑

```
循环查询数据赋给变量时，一定要加上 isnull(字段,'') ，这样即使当前没有查到，也不会影响下次遍历的数据
```



### SQL Server Merge用法

参考：[Merge](https://www.cnblogs.com/Vincent-yuan/p/11521229.html)

```sql
MERGE target_table USING source_table
ON merge_condition
WHEN MATCHED
    THEN update_statement
WHEN NOT MATCHED
    THEN insert_statement
WHEN NOT MATCHED BY SOURCE
    THEN DELETE;
------------------------------------------------
MERGE sales.category t 
    USING sales.category_staging s
ON (s.category_id = t.category_id)
WHEN MATCHED
    THEN UPDATE SET 
        t.category_name = s.category_name,
        t.amount = s.amount
WHEN NOT MATCHED BY TARGET 
    THEN INSERT (category_id, category_name, amount)
         VALUES (s.category_id, s.category_name, s.amount)
WHEN NOT MATCHED BY SOURCE 
    THEN DELETE;
    
```



### SQL Server、My SQL、Oracle 数据库分页

取出sql表中第31到40的记录（以自动增长ID为主键）

> MySQL

```sql
select * from t order by id limit 0,10
```



> Oracle

```sql
select * from (select rownum r,* from t where r<=40) where r>30
```

​	

> SQL Server

```sql
select top 10 * from t where id not in (select top 30 id from t order by id ) orde by id

select top 10 * from t where id in (select top 40 id from t order by id) order by id desc


select * from t order by id
offset (4-1) * 10 rows
fetch next @pageSize rows only;
```



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