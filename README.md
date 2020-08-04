​		

## Keyboard

源码		ctrl + /

目录		[toc]

标题		ctrl + 数字

表格		ctrl+t 

标签		`code`

代码块		ctrl + shift + k

水平线		*** 

超链接		ctrl+k [百度][www.badu.com] <www.baidu.com>

下划线		ctrl+u

表情		😄 

高亮		==高亮==

加粗   		 **内容**	Ctrl+B 

倾斜		*内容*  	Ctrl+I 

删除线		~~内容~~ 	 Alt+Shift+5 

插入图片		ctrl+shift+i

有序列表		数字+英文小数点(.)+空格 

无序列表		+、- 、* + 空格创建



#IDEA快捷键



Ctrl + R, 可以替换	

Ctrl + H,  显示类结构图

Ctrl＋N，可以快速打开类

Ctrl＋E，可以显示最近编辑的文件列表

Ctrl＋F12，可以显示当前文件的结构

Ctrl＋Alt＋L 整理代码

Alt+ left/right 切换代码视图







## 数据库

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
WHERE b.user_nick_name = '采先生i'


-- 联表删除
DELETE a
FROM `ana` a
INNER JOIN `user` b ON a.user_id = b.id
WHERE b.user_nick_name = '采先生i'

-- 联表插入
INSERT INTO ana_new(id,title)
SELECT id,title
FROM ana

-- 复制表数据创建新表
CREATE TABLE ana_back 
SELECT * FROM ana
```



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



## Linux



### CentOS 7安装

参考：[Centos7安装](https://www.cnblogs.com/set-config/p/9040407.html)  [网络配置以及安装图形化界面](https://www.cnblogs.com/zqyw/p/11202560.html)  [没有网络](https://www.cnblogs.com/Vincent-yuan/p/10802023.html)



### 配置防火墙端口

```linux
#CentOS 6
vi /etc/sysconfig/iptables         //防火墙配置

-A INPUT -m state --state NEW -m tcp -p tcp --dport 22 -j ACCEPT //允许22端口通过
-A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT //允许3306端口通过

service iptables restart        //重启防火墙


#CentOS 7

# 查询端口是否开放
firewall-cmd --query-port=8080/tcp
# 开放80端口
firewall-cmd --permanent --add-port=80/tcp
# 移除端口
firewall-cmd --permanent --remove-port=8080/tcp

#查看firewall服务状态
systemctl status firewalld
#查看防火墙状态
firewall-cmd --state 

# 开启
service firewalld start
# 关闭
service firewalld stop
# 重启
service firewalld restart
```



### NAT和桥接模式

参考：[NAT和桥接模式](https://www.cnblogs.com/huhuxixi/p/11527837.html )



### Linux常用命令

```linux
chmod 777 xx #授权
tar -zxvf 文件名 #解压
tar -cvf 123.tar file1 file2 #压缩
ps -ef | grep tomcat
ps -ef | grep java
lsof -i :8080 #查看某个端口
lsof -c java #列出某个程序所打开的文件信息
kill -9 进程号
vi	编辑(dd删除文本当前行)
df -h #查询磁盘的空间使用情况

#查看内存使用情况
free -m 

#显示进程信息(包括CPU、内存使用等信息)
top

#显示磁盘空间使用情况
df --block-size=M



#动态查询
tail -99f text.txt

#用以显示符合条件的进程情况
lsof -i

#是一个列出当前系统打开文件的工具，查看端口占用
lsof -i:端口号

#一个监控TCP/IP网络的非常有用的工具，它可以显示路由表、实际的网络连接以及每个网络接口的状态信息
netstat

#显示tcp，udp的端口和进程等相关情况
netstat -tunplp
netstat -tunplp | grep 端口号

#也可以显示系统端口使用情况
netstat -anp
```



### Linux后台运行项目

```linux
#如果让程序始终在后台执行，即使关闭当前的终端也执行（之前的&做不到），这时候需要nohup。该命令可以在你退出帐户/关闭终端之后继续运行相应的进程。关闭中断后，在另一个终端jobs已经无法看到后台跑得程序了，此时利用ps（进程查看命令）
nohup ./startup.sh &
nohup java -jar weChat.jar &

ps -aux | grep "test.sh"  
#a:显示所有程序 u:以用户为主的格式来显示 x:显示所有程序，不以终端机来区分
```



### Linux配置JDK

/etc/profile 文件

```Linux
#JAVA的JDK配置
export JAVA_HOME=/develeop/jdk1.8.0_251
export PATH=$PATH:$JAVA_HOME/bin
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

#刷新环境变量文件： 刷新环境变量命令
source /etc/profile
```



### Linux安装Maven

参考：[Linux安装Maven](https://blog.csdn.net/qq_38270106/article/details/97764483)



### Linux安装Git

参考：[Linux安装Git](https://blog.csdn.net/xiaoye319/article/details/89642875)



### Linux安装MySQL

参考：[Linux安装MySQL](https://www.jianshu.com/p/276d59cbc529)  [MySQL无法远程连接](https://www.cnblogs.com/zzqit/p/10095597.html)



### Linux安装Redis

参考：[Linux安装Redis](https://www.cnblogs.com/limit1/p/9045183.html)  [MySQL忘记密码](https://www.cnblogs.com/black-fact/p/11613361.html)



### MySQL数据总显示 '' ? ''

1. 编辑my.cnf文件 默认路径都在 `vi /etc/my.cnf`

2. 添加配置

   ```Linux
   [mysqld]
   #中文无法插入数据
   character-set-server=utf8
   #改配置可以忽略大小写
   lower_case_table_names=1  
   
   [client]
   #中文无法插入数据
   default-character-set=utf8
   ```

   ```
   #设置编码格式
   mysql> set character_set_database=utf8;
    
   mysql> set character_set_server=utf8;
   
   # vi /etc/my.cnf;
   [mysqld]
   character_set_server = utf8
    
   [mysql]
   default-character-set=utf8
    
   [client]
   default-character-set=utf8
   ```

   

3. 运行代码，重启MySQL服务

   ```Linux
   service mysqld/mysql restart
   ```



### MySQL主从复制

> 主机配置(host79)

```
修改配置文件：vim /etc/my.cnf
#主服务器唯一ID
server-id=1
#启用二进制日志
log-bin=mysql-bin
# 设置不要复制的数据库(可设置多个)
binlog-ignore-db=mysql
binlog-ignore-db=information_schema
#设置需要复制的数据库
binlog-do-db=需要复制的主数据库名字
#设置logbin格式
binlog_format=STATEMENT
```



> 从机配置(host80)

```
修改配置文件：vim /etc/my.cnf
#从服务器唯一ID
server-id=2
#启用中继日志
relay-log=mysql-relay
```



> 主机、从机重启 MySQL 服务

> 主机从机都关闭防火墙

> 在主机上建立帐户并授权 slave

```
#在主机MySQL里执行授权命令
GRANT REPLICATION SLAVE ON *.* TO 'slave'@'%' IDENTIFIED BY '123123';
#查询master的状态
show master status;
#记录下File和Position的值
#执行完此步骤后不要再操作主服务器MySQL，防止主服务器状态值变化
```



> 在从机上配置需要复制的主机

```
#如果之前配置过主从需要停止原有的
stop slave;
reset master;

#复制主机的命令
CHANGE MASTER TO MASTER_HOST='主机的IP地址',
MASTER_USER='slave',
MASTER_PASSWORD='123123',
MASTER_LOG_FILE='mysql-bin.具体数字',MASTER_LOG_POS=具体值; #启动从服务器复制功能
start slave;

#查看从服务器状态
show slave status\G;

#下面两个参数都是Yes，则说明主从配置成功！
# Slave_IO_Running: Yes
# Slave_SQL_Running: Yes
```



## Windows



### Git

参考：[Git](https://blog.csdn.net/hellow__world/article/details/72529022)



### IDEA修改java文件后 不用重启Tomcat服务便可自动更新

参考：[IDEA修改java文件后 不用重启Tomcat服务便可自动更新](https://blog.csdn.net/u010865136/article/details/80392212)



### CRM与ERP

参考：[CRM与ERP](http://baijiahao.baidu.com/s?id=1653409772927548267&wfr=spider&for=pc)



### VO、DTO、DO、PO的概念

参考：[VO、DTO、DO、PO的概念](https://blog.51cto.com/14442094/2432008?source=dra)

```
entity是实体类  vo展示类  to入参类  
```



### JAVA 版本

> JAVA SE 标准版开发 主要用于桌面程序,控制台开发(JFC)

> JAVA EE 企业级开发，主要用于web端(JSP,EJB)       

> JAVA ME嵌入式开发(手机,小家电)



### JVM & GC

参考：[一篇文章彻底搞定所有GC面试问题](https://blog.csdn.net/liewen_/article/details/83151227)

![JVM结构](./image/JVM.png)

**JVM运行时数据区**

```
JVM运行时数据区主要分为：栈、堆、本地方法栈、程序计数器、方法区（jdk8无）、其中栈、本地方法栈、程序计数器，3个区域随线程生灭(因为是线程私有)，不共享数据。而Java堆和方法区则不一样

Java堆：Java虚拟机管理的内存中最大的一块，所有线程共享，几乎所有的对象实例和数组都在这里分配内存。GC主要就是在Java堆中进行的。JVM根据对象存活周期不同，同时也为了提高对象内存分配和垃圾回收的效率，把堆内存划分为几块。
	1.新生代（新生代又分为Eden80%，SurvivorFrom10%，SurvivorTo10%）
	2.老年代经过了多次GC依然存活，不会频繁做GC

流程：
-----------------------------------------------------------------------------------------
1.新生代有一个Eden区和两个survivor区，首先将对象放入Eden区，如果空间不足就向其中的一个survivor区上放，如果仍然放不下就会引发一次发生在新生代的Minor GC，将存活的对象放入另一个survivor区中，然后清空Eden和之前的那个survivor区的内存。在某次GC过程中，如果发现仍然又放不下的对象，就将这些对象放入老年代内存里去。

2.大对象以及长期存活的对象直接进入老年代。

3.触发Minor GC之前，会检查晋升到老年代的对象大小，是否大于老年代剩余空间，如果大于，则直接触发Full GC
-----------------------------------------------------------------------------------------

GC一共分三种：MinorGC,Major GC 和Full GC。Full GC是清理整个堆空间—包括年轻代和永久代。

方法区：对于JVM的方法区，可能听得最多的是另外一个说法——永久代（Permanent Generation），呼应堆的新生代和老年代。在永久代移除后，字符串常量池也不再放在永久代了，但是也没有放到新的方法区---元空间里，而是留在了堆里（为了方便回收？）。运行时常量池当然是随着搬家到了元空间里，毕竟它是装静态变量、字节码等信息的，有它的地方才称得上方法区。

元空间是方法区的在HotSpot jvm 中的实现，方法区主要用于存储类的信息、常量池、方法数据、方法代码等。方法区逻辑上属于堆的一部分，但是为了与堆进行区分，通常又叫“非堆”。元空间的本质和永久代类似，都是对JVM规范中方法区的实现。不过元空间与永久代之间最大的区别在于：元空间并不在虚拟机中，而是使用本地内存。
```



### JVM中堆和栈的区别

[JVM中堆和栈的区别](https://www.cnblogs.com/benon94/p/10626798.html)

[JVM中堆和栈到底存放了什么](https://www.cnblogs.com/toSeeMyDream/p/5251918.html)



### JAVA8 的Stream API使用

参考：[JAVA8 的Stream API使用](https://www.cnblogs.com/jimoer/p/10995574.html)



### JAVA反射机制

参考：[JAVA反射机制](https://www.cnblogs.com/hechenhao/p/8039639.html)  [JAVA反射机制](https://blog.csdn.net/liujiahan629629/article/details/18013523?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task)  

```java
private static object getFieldValueByName(String fieldName, object o) throws Exception {
	String firstLetter = fieldName.substring(0,1).toUpperCase() ;
	String getter = "get" + firstLetter + fieldName.substring(1);
	Method method = o.getClass().getMethod(getter, new Class[] {});
	Object value = method.invoke(o, new Object[] {});
    System.out.println("fieldName>>>>>>"+filedName+" val: "+String.valueOf(value));
	return value;
}
```



### JAVA多线程

参考：[JAVA多线程](https://blog.csdn.net/zl1zl2zl3/article/details/81868173)  [对象锁、锁池、等待池](https://blog.csdn.net/u014561933/article/details/58639411)



### JAVA IO流

参考：[JAVA IO流](https://blog.csdn.net/qq_37875585/article/details/89385688)





### JAVA注解与元注解

参考：[JAVA注解与元注解](https://blog.csdn.net/pengjunlee/article/details/79683621)  [JAVA注解+反射机制](https://baijiahao.baidu.com/s?id=1612408653409570352&wfr=spider&for=pc)  [JAVA框架常用注解](https://www.jianshu.com/p/a4db04398df6)



### 单例模式

> 懒汉

```java
public class SingleTon{
   private static SingleTon  INSTANCE = null;
   private SingleTon(){}
   public static SingleTon getInstance() {  
   if(INSTANCE == null){
      INSTANCE = new SingleTon(); 
    } 
    return INSTANCE；
  }
}
//懒汉模式在方法被调用后才创建对象，以时间换空间，在多线程环境下存在风险。
```



> 饿汉

```java
public class SingleTon{
	private static SingleTon INSTANCE = new SingleTon();
	private SingleTon(){
	
	}
	public static SingleTon getInstance(){ 
		return INSTANCE; 
	}
}
//饿汉模式在类被初始化时就已经在内存中创建了对象，以空间换时间，故不存在线程安全问题。
```



> 静态内部类

```java
public class SingleTon{
  private SingleTon(){}
 
  private static class SingleTonHoler{
     private static SingleTon INSTANCE = new SingleTon();
 }
 
  public static SingleTon getInstance(){
    return SingleTonHoler.INSTANCE;
  }
}
//静态内部类的优点是：外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化INSTANCE，故而不占内存。即当SingleTon第一次被加载时，并不需要去加载SingleTonHoler，只有当getInstance()方法第一次被调用时，才会去初始化INSTANCE,第一次调用getInstance()方法会导致虚拟机加载SingleTonHoler类，这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。
```



### 工厂模式

参考：[工厂模式](https://www.runoob.com/design-pattern/factory-pattern.html)



### 策略模式

参考：[策略模式](https://www.runoob.com/design-pattern/strategy-pattern.html)



### 工厂模式与策略模式

```
工厂模式中只管生产实例，具体怎么使用工厂实例由调用方决定，策略模式是将生成实例的使用策略放在策略类中配置后才提供调用方使用。 
工厂模式调用方可以直接调用工厂实例的方法属性等，策略模式不能直接调用实例的方法属性，需要在策略类中封装策略后调用。
```



### 观察者与监听器

参考：[java监听器实现与原理](https://www.cnblogs.com/againn/p/9512013.html)  [观察者和监听器的区别](https://blog.csdn.net/lovexiaotaozi/article/details/102579880)

```
重点：理解事件与事件源的关系
```



### Executors线程池

```
1. newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
2. newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
3. newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
4. newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
```



### 多线程保证集合安全

```java
//Collections由很多工具类：synchronizedSet、synchronizedList
Set<Object> set = Collections.synchronizedSet(new HashSet<Object>());
for (int i = 1; i <= 5; i++) {
    new Thread(() -> {
        set.add(UUID.randomUUID().toString().substring(0, 8));
        System.err.println(set.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }, String.valueOf(i)).start();
}
```



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



### JDBC面试题

参考：[JDBC面试题](https://www.cnblogs.com/kevinf/p/3705148.html)



### MyBatis面试题

参考：[MyBatis面试题](https://blog.csdn.net/a745233700/article/details/80977133)



### Spring面试题

参考：[Spring面试题](https://blog.csdn.net/a745233700/article/details/80959716)  [Spring Bean作用域](https://blog.csdn.net/qq_41083009/article/details/90743719)

>IOC基本原理

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



> IOC 操作 Bean 管理（bean 生命周期）

```xml
1、生命周期
	1).从对象创建到对象销毁的过程
	
2、bean 生命周期
    1).通过构造器创建bean实例（无参数构造）
    2).为bean的属性设置值和对其他bean引用（调用set方法）
    3).把bean实例传递给bean 后置处理器方法 -> postProcessBeforeInitialization
    4).调用bean的初始化的方法（需要进行配置初始化的方法）
    5).把bean实例传递给bean 后置处理器方法 -> postProcessAfterInitialization
    6).bean可以使用了（对象获取到了）
    7).当容器关闭时候，调用bean的销毁的方法（需要进行配置销毁的方法）

<bean id="myUser" 
      class="cn.cps.User" 
      init-method="myInitMethod"
      destroy-method="myDestroyMethod"
 ></bean>


后置处理器方法需要实现BeanPostProcess接口,也需要把后置处理器注入容器中
重写两个方法postProcessBeforeInitialization、postProcessAfterInitialization
```



### SpringMVC与Struts2对比

参考：[SpringMVC与Struts2区别与比较总结](https://blog.csdn.net/jishuizhipan/article/details/79385190)



### SpringMVC面试题

参考：[ SpringMVC面试题](https://blog.csdn.net/a745233700/article/details/80963758?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task)



### SpringBoot源码解析

参考：[SpringBoot面试题](https://blog.csdn.net/yuzongtao/article/details/84295732)  [SpringBoot注解解析](https://www.cnblogs.com/123-shen/p/SpringBoot.html)  [SpringBoot源码解析](https://blog.csdn.net/woshilijiuyi/article/details/82219585)

```
三大核心：快速整合第三方框架、无xml注解化配置、使用java语言内嵌tomcat
```

> 自定义starter

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



> SpringBoot启动流程

```
核心流程
1.创建SpringApplication对象
2.调用SpringApplication run 实现启动同时返回当前的容器上下文

具体分析
1.创建SpringApplication对象SpringBoot容器初始化操作。
	1.1.获取当前应用启动类型，ClassUtils.isPresent("类名")判断当前classpath是否有加载所需要的类
		webApplicationType 分为三种类型：
            a.NONE：不会嵌入web容器启动(普通项目)
            b.SERVLET：使用java代码创建Web容器启动
            c.REACTIVE：响应式启动(Spring5新特性)
	1.2.setInitializers
		META-INF/spring.factories获取对应的ApplicationContextInitializer并创建对象装配到集合中
		ApplicationContextInitializer是spring组件spring-context组件中的一个接口，主要是spring 		   ioc容器刷新之前的一个回调接口，用于处于自定义逻辑。
	1.3.setListeners
		META-INF/spring.factroies获取对应的ApplicationListener并创建实例对象装配到集合中
		监听器会贯穿springBoot整个生命周期

2.调用SpringApplication run 方法实现启动
	2.1.StopWatch stopWatch = new StopWatch();
		记录我们SpringBoot启动时间
	2.2.getRunListeners(args);读取我们的META-INF/spring.factories得到监听器
		通过JAVA反射，使用构造方法生成实例，同时也会将监听器存入SimpleApplicationEventMulticaster
	2.3.listeners.starting();
		循环调用监听starting方法启用
	2.4.ConfigurableEnvironment 
					environment = prepareEnvironment(listeners,applicationAArguments);
		将系统变量和环境变量，加入到其父类定义的对象MutablePropertySources中，接下来解析配置文件就行
		1.this.getOrCreateEnvironment();获取相应的环境对象
			environment已经被设置了servlet类型，所以创建的是环境对象是StandardServletEnvironment
		2.listeners.environmentPrepared(environment);把得到的配置文件读取并加载到SpringBoot容器
            ConfigFileApplicationListener类该监听器非常核心，主要用来处理项目配置
            默认获取classpath:/,classpath:/config,file:./file:./conifg项目中的为application的
            properties、xml、yml、yaml文件. 举例：location:classpath:/appcalition.properties
        至此，项目的变量配置已全部加载完毕
	2.6.Banner printedBanner = printBanner(environment);
		打印SpringBootBanner图
	2.7.context = this.createApplicationContext();
		根据webApplicationType进行判断，创建SpringBoot上下文对象
	2.8.prepareContext(context, environment, listeners, applicationArguments,
					printedBanner);
        主要是在容器刷新之前的准备动作。将启动类注入容器，为后续开启自动化配置奠定基础
        1.load(context, sources.toArray(new Object[0]));
        加载启动指定类（重点）启动类加载spring容器beanDefinitionMap中
        为后续springBoot 自动化配置奠定基础，springBoot为我们提供的各种注解配置也与此有关
    2.9.refreshContext(context);刷新上下文
    	-> refresh -> onRefresh -> createWebServer -> getWebServer -> Tomcat -> SpringMVC
    	ServletwebServerFactoryAutoConfiguration 创建Tomcat
    	DispatcherservletAutoConfiguration 创建SpringMVC
    2.10.afterRefresh定义一个空的模板给其他子类重写
    2.11.listeners.started(context);使用广播和回调机制通知监听器SpringBoot容器已经启动成功
    2.12.listeners.running(context);使用广播和回调机制通知监听器SpringBoot容器启动成功
```



> 配置文件值注入

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



### SpringBoot+SpringCloud

参考：[SpringBoot+SpringCloud面试题整理](https://blog.csdn.net/qq_40117549/article/details/84944840?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task ) 



### Servlet、Filter、Listener、Interceptor的区别与联系？

参考:  [Servlet、Filter、Listener、Interceptor的区别与联系?](https://blog.csdn.net/qq_40117549/article/details/84944840?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task ) 



### Shiro

参考：[Shiro面试知识点](https://www.jianshu.com/p/e6ca8cd7d823)

```
Apache Shiro是Java的一个安全(权限)框架

Shiro可以非常容易的开发出足够好的应用,其不仅可以用在JavaSE环境,也可以用在JavaEE环境

Shiro可以完成:认证、授权、加密、会话管理、与Web集成、缓存等
```



使用MD5盐值加密：

```java
1.在doGetAuthenticationInfo 方法返回创建SimpleAuthenticationInfo对象的时候，需要使用SimpleAuthenticionInfo("认证实体信息","密码","盐值","Realm")构造器，realName → getName()
2.使用ByteSource.Util,byte("盐值") 计算盐值（一般使用ID，唯一标识）
3.使用New SimpleHash("加密算法","密码","盐值","加密次数");计算盐值加密后的值
```



认证流程

```java
1.首先收集信息，创建UsernamePasswordToken对象，再由SecurityUtils得到Subject对象，调用它的login方法，同时传参UsernamePasswordToken对象，其会自动委托给SecurityManager
2.SecurityManager 负责真正的身份验证逻辑，它会委托给 Authenticator 进行身份验证； 
（Authenticator 才是真正的身份验证者，它会调用认证策略对Realm进行身份验证，同时把token传入Realm，从Realm获取分身验证信息，返回SimpleaAuthenticaionInfo对象，进行逻辑判断）
3.Authenticator 才是真正的身份验证者，Shiro API 中核心的身份认证入口点，此处可以自定义插入自己的实现；
4.Authenticator 可能会委托给相应的 AuthenticationStrategy 进行多 Realm 身份验证，默认 ModularRealmAuthenticator 会调用 AuthenticationStrategy 进行多 Realm 身份验证； 
5.Authenticator 会把相应的 token 传入 Realm，从 Realm 获取 身份验证信息，如果没有返回/抛出异常表示身份验证失败了。此处 可以配置多个Realm，将按照相应的顺序及策略进行访问。
```



授权流程

```java
1.首先调用 Subject.isPermitted*/hasRole* 接口，其会委托给 SecurityManager，而 SecurityManager 接着会委托给 Authorizer
2.Authorizer是真正的授权者，如果调用如 isPermitted(“user:view”)，其首先会通过PermissionResolver 把字符串转换成相应的 Permission 实例； 
3.在进行授权之前，其会调用相应的 Realm 获取 Subject 相应的角色/权限用于匹配传入的角色/权限；（授权）
（返回SimpleAuthorizationInfo对象，Authorizer 会判断Realm的角色/权限是否和传入的匹配，）
4.Authorizer 会判断Realm的角色/权限是否和传入的匹配，如果有多个Realm，会委托给 ModularRealmAuthorizer进行循环判断，如果匹配如isPermitted*/hasRole*会返回true，否则返回false表示 授权失败。
```



### 前端知识点

> js解决乱码问题

```js
window.open('revoke/export?'+'&userName='+encodeURIComponent(this.userName));
```



> 对象使用key

```
p{
	//不换行
	white-space: nowrap
}

var user = {};
user["id"] = 1;
console.log(user); //{id:1}

//另外多提一个知识点
return、break不能终止forEach()循环
```



> 引用JS静态数据

```js
/* js数据 */
let list_data = [
  { id: '1', name: '甲', age: '18' },
  { id: '2', name: '乙', age: '14' },
  { id: '3', name: '丙', age: '22' },
  { id: '4', name: '丙', age: '17' }
];
module.exports = {list_data};

/* 使用数据 */
let datas = require('../../datas/listData');
console.log(typeof datas, datas);

--------------------------------------------------------------------------------

export default function request(){
    console.log("request");
}

//引用
let request = require('../../request');
//使用
request();


```



### 静态资源文件无法访问

```xml
<build>
	<resources>
		<resource>
			<directory>src/main/resources</directory>
		</resource>
	</resources>
</build>
```



### 过滤器和拦截器区别

参考：[过滤器和拦截器区别](https://www.jianshu.com/p/7bd0cad17f23)



### 序列化与反序列化

参考：[序列化与反序列化](https://blog.csdn.net/tree_ifconfig/article/details/82766587 )



### SOAP

参考：[SOAP](https://www.runoob.com/soap/soap-tutorial.html)

```
SOAP 是基于 XML 的简易协议，可使应用程序在 HTTP 之上进行信息交换。
或者更简单地说：SOAP 是用于访问网络服务的协议。
```



> 为什么使用 SOAP？
```
对于应用程序开发来说，使程序之间进行因特网通信是很重要的。

目前的应用程序通过使用远程过程调用（RPC）在诸如 DCOM 与 CORBA 等对象之间进行通信，但是 HTTP 不是为此设计的。RPC 会产生兼容性以及安全问题；防火墙和代理服务器通常会阻止此类流量。

通过 HTTP 在应用程序间通信是更好的方法，因为 HTTP 得到了所有的因特网浏览器及服务器的支持。SOAP就是被创造出来完成这个任务的。

SOAP提供了一种标准的方法，使得运行在不同的操作系统并使用不同的技术和编程语言的应用程序可以互相进行通信。
```



> SOAP语法

```xml
一条 SOAP 消息就是一个普通的 XML 文档，包含下列元素：
    必需的 Envelope 元素，可把此 XML 文档标识为一条 SOAP 消息
    可选的 Header 元素，包含头部信息
    必需的 Body 元素，包含所有的调用和响应信息
    可选的 Fault 元素，提供有关在处理此消息所发生错误的信息
    
<?xml version="1.0"?>
<soap:Envelope
xmlns:soap="http://www.w3.org/2001/12/soap-envelope"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Header>
    ...
    </soap:Header>

    <soap:Body>
    ...
      <soap:Fault>
      ...
      </soap:Fault>
    </soap:Body>
</soap:Envelope>
```



### TCP/IP协议族

```
计算机设备与网络需要通信，双方就必须基于相同的方法。
比如 何如胎侧到通信目标，由哪一边先发起通信，使用哪种语言进行通信，怎样结束通讯等规则都需要事先确定。不同的硬件、操作系统之间的通讯，所有这一切都需要一种规则，而我们把这种规则成为协议。
例如：TCP、IP、FCP、DNC、HTTP等。像这样把与互联网相关联的协议集合起来称为TPC/IP。
```



### TCP/IP分层管理

```
TCP/IP协议族中最重要的一点就是分层。按层次分别分为：应用层、传输层、网络层、数据链路层。
把TCP/IPf分层化是有好处的，比如，如果互联网只由一个协议统筹，某个地方需要修改设计时，就必须把所有部分整体替换掉。而分层之后只需把变动的层替换掉即可。

应用层：应用层决定了向用户提供应用服务时通信的活动
传输层：传输层对上层应用层，提供处于网络连接中的两台计算机之间的数据传输。
网络层：网络层用来处理在网络上流动数据包，数据包时网络传输的最小数据单位
数据链路层(网络接口层)：用来链接网络硬件部分
```



### TCP/IP通信传输流

```
			  客户端			服务端
			  
应用层		  HTTTP客户端		HTTP服务器
				↕				↕
传输层			 TCP			 TCP
				↕				↕
网络层			  IP			  IP
				↕				↕
数据链路层	    网络			   网络
				↓				↑
				→→→→→→→→→→→→→→→→→
利用TCP/IP协议族进行网络通讯时，会通过分层顺序与对方进行通信，发送端从应用层往下走，接收端从链路层往上走	
				
```



### TCP的三次握手

参考：[TCP的三次握手与四次挥手理解及面试题](https://blog.csdn.net/qq_38950316/article/details/81087809)   [TCP三次握手详细过程](https://blog.csdn.net/huaishu/article/details/93739446)

```
为了准确无误得将数据送达目标处，TCP协议采用了三次握手策略。用TCP协议把数据包送出去后，TCP不会对传送后的情况置之不理，它一定会向对方确认是否成功送达。握手过程中使用了TCP的标志(flag)——SYN(synchronize)和ACK(acknowledgement)

发送端首先发送一个带SYN标志的数据包给对方。接收端收到后，回传一个带有SYN/ACK标志的数据包以示传达确认信息。最后，发送端回传一个带ACK标志的数据包，代表'握手'/结束。

若在握手过程中某个阶段莫名中断，TCP协议会再次以相同的顺序发送相同的数据包。
```



### RPC面试题

参考：[RPC面试题](https://www.cnblogs.com/feifeicui/p/10431529.html )



### WebService面试题

参考：[WebService面试题](https://blog.csdn.net/c99463904/article/details/76018436?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1) 



### 负载均衡

[负载均衡分类](https://www.jianshu.com/p/c48af7936329)

[介绍负载均衡的面试话术](https://blog.csdn.net/yuanaili/article/details/81191408)



### Nginx

参考：[Nginx面试](<https://blog.csdn.net/a303549861/article/details/88672901>)

参考：[Nginx是什么 ? 能干嘛 ？](<https://blog.csdn.net/forezp/article/details/87887507>)

参考： [Windows 下Nginx重启项目不重新加载](https://www.cnblogs.com/zjfblog/p/11854946.html) 

```
Nignx是一个开源的、高性能的轻量级的HTTP服务器和反向代理服务器；
Nginx可以作为一个HTTP服务器进行网站的发布处理，另外Nginx可以作为反向代理进行负载均衡的实现。

正向代理、反向代理、负载均衡
```





### List Map 

参考：[JAVA中的集合](https://blog.csdn.net/weixin_36027342/article/details/79972399)



### HashMap红黑树

参考：[HashMap红黑树](https://www.jianshu.com/p/2c7a4a4e1f53)   [HashMap理解](https://blog.csdn.net/wenyiqingnianiii/article/details/52204136)



### 集合对象去重

```java
List<String> dataList = list.stream().distinct().collect(Collectors.toList());

//根据id去重
personList=
    personList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                // 利用 TreeSet 的排序去重构造函数来达到去重元素的目的
                // 根据firstName去重
                () -> new TreeSet<>(Comparator.comparing(Person::getName))), ArrayList::new));
```



### Tomcat优化

参考：[Tomcat优化](https://www.cnblogs.com/xuwc/p/8523681.html)

```
tomcat设置https端口时,8443和443区别:

1. 8443端口在访问时需要加端口号,相当于http的8080,不可通过域名直接访问,需要加上端口号;https://yuming.com:8443。
2. 443端口在访问时不需要加端口号,相当于http的80,可通过域名直接访问;例:https://yuming.com。

https使用域名访问网站,而不显示端口号?

将端口号设置为443,即可通过域名直接访问网站
```



### Tomcat做成系统服务

参考：[Tomcat做成系统服务](<https://jingyan.baidu.com/article/597a0643680371312b52431a.html>)

一：下载tomcat

二：配置tomcat

1.解压tomcat至自定义目录下。



2.如果电脑上面有多个tomcat的话，请修改一下tomcat服务端口，否则tomcat启动将因为端口冲突会失败。

主要对解压目录下conf/server.xml文件进行修改

```xml
<Server port="9001" shutdown="SHUTDOWN">  
<Connector port="9090" protocol="HTTP/1.1"  connectionTimeout="20000" redirectPort="9061" />  
<Connector port="9081" protocol="AJP/1.3" redirectPort="9061" />
```



3.修改tomcat中bin/目录下的service.bat文件

1) 在文件开头部分添加以下内容

```xml
SET JAVA_HOME=C:\Program Files\Java\jdk1.7.0_67  
SET CATALINA_HOME=D:\server\tomcat  
SET PR_DISPLAYNAME=项目名或其他自定义名称 
```

2) 在文件中找到rem Set default Service name  部分并将内容修改为：

```xml
set SERVICE_NAME=myporject(自定义名称)
```



4.修改bin目录下shutdown.bat和startup.bat文件，在文件开头添加内容：

```xml
SET JAVA_HOME=C:\Program Files\Java\jdk1.7.0_67  
SET CATALINA_HOME=D:\server\tomcat
```



5.添加服务

1)在DOS界面下,进入Tomcat解压目录的bin目录,输入命令:

```windows
service.bat remove tomcat6

service.bat install
```

如果安装成功,会提示:The service 'Tomcat6（或者你修改一后的SERVICE_NAME）' has
been installed



### Maven配置文件激活Spring Boot配置文件

参考：[Maven配置文件激活Spring Boot配置文件](http://dolszewski.com/spring/spring-boot-properties-per-maven-profile/)



### Maven跳过测试环节打包

```cmd
mvn clean package -Dmaven.test.skip=true

# mvn clean package -Dmaven.test.skip=true -P prod
```



### Maven定义规范

```
	GroupId和ArtifactId被统称为“坐标”是为了保证项目唯一性而提出的，如果你要把你项目弄到maven本地仓库去，你想要找到你的项目就必须根据这两个id去查找。

　　GroupId一般分为多个段，这里我只说两段，第一段为域，第二段为公司名称。域又分为org、com、cn等等许多，其中org为非营利组织，com为商业组织。举个apache公司的tomcat项目例子：这个项目的GroupId是org.apache，它的域是org（因为tomcat是非营利项目），公司名称是apache，ArtifactId是tomcat。
　　
　　比如我创建一个项目，我一般会将GroupId设置为cn.mht，cn表示域为中国，mht是我个人姓名缩写，ArtifactId设置为testProj，表示你这个项目的名称是testProj，依照这个设置，在你创建Maven工程后，新建包的时候，包结构最好是cn.zr.testProj打头的，如果有个StudentDao[Dao层的]，它的全路径就是cn.zr.testProj.dao.StudentDao
```



### SpringBoot打war包部署

1. pom.xml配置修改

```pom.xml
<packaging>war</packaging>
```

2. 排除spring boot中内嵌的tomcat依赖包：

```pom.xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-tomcat</artifactId>
   <scope>provided</scope><!-- provided打包时不加载此包 -->
</dependency>
```

3. 修改maven打war包插件

```
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



### 日期格式问题

1. 后台接受前台日期问题

   一、@DateTimeFormat(pattern = "yyyy-MM-dd")

   二、创建配置类注入Spring中

   ```java
   @Configuration
   public class DateConverterConfig implements Converter<String, Date> {
   
           private static final List<String> formarts = new ArrayList<>(4);
   
           static {
               formarts.add("yyyy-MM");
               formarts.add("yyyy-MM-dd");
               formarts.add("yyyy-MM-dd hh:mm");
               formarts.add("yyyy-MM-dd hh:mm:ss");
           }
   
           @Override
           public Date convert(String source) {
               String value = source.trim();
               if ("".equals(value)) {
                   return null;
               }
               if (source.matches("^\\d{4}-\\d{1,2}$")) {
                   return parseDate(source, formarts.get(0));
               } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                   return parseDate(source, formarts.get(1));
               } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
                   return parseDate(source, formarts.get(2));
               } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
                   return parseDate(source, formarts.get(3));
               } else {
                   throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
               }
           }
   
           /**
            * 格式化日期
            *
            * @param dateStr String 字符型日期
            * @param format  String 格式
            * @return Date 日期
            */
           public Date parseDate(String dateStr, String format) {
               Date date = null;
               try {
                   DateFormat dateFormat = new SimpleDateFormat(format);
                   date = dateFormat.parse(dateStr);
               } catch (Exception e) {
   
               }
               return date;
           }
   
       }
   
   ```

2. JSON格式字符串日期问题

   一、@JSONField(format = "yyyy-MM-dd HH:mm:ss")

   二、@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")

   三、JSONArray.toJSONStringWithDateFormat(this,"yyyy-MM-dd HH:mm:ss");



### MyBatis date类型引发问题

date类型在判断非空时，这种写法会引发异常：invalid comparison: java.util.Date and java.lang.String

```xml
<if test="createDate != null and createDate !='' " >  
  date(createDate) = #{createDate}  
</if>
```

正确写法应为：

```xml
<if test="createDate != null" >  
  date(createDate) = date#{createDate}
</if> 
```



### Ajax传输JSON数据

```js
$.ajax({
    url:"/user/export",
    data:JSON.stringify(data),
    type:"post",
    contentType:"application/json",//重点
    success:function(fileName){
        console.log(fileName);
        window.open("../user/downLoad?fileName=" + fileName);
    },error:function(){
    	console.log("失败！");
    }
})
```

```java
@ResponseBody
@RequestMapping(value = "/export")
public void export(@RequestBody JSONObject data){
   	 //将字符串解析成JSONObject对象
}
```



### Jackson FastJson

参考：[性能差异](https://blog.csdn.net/u013433821/article/details/82905222)

```
Jackson相比json-lib框架，Jackson所依赖的jar包较少，简单易用并且性能也要相对高些。而且Jackson社区相对比较活跃，更新速度也比较快。SpringBoot默认的json解析

Fastjson是一个Java语言编写的高性能的JSON处理器,由阿里巴巴公司开发。无依赖，不需要例外额外的jar，能够直接跑在JDK上。FastJson采用独创的算法，将parse的速度提升到极致，超过所有json库。
FastJson在复杂类型的Bean转换Json上会出现一些问题，可能会出现引用的类型，导致Json转换出错，需要制定引用。有的版本存在高危漏洞，不建议生产使用
```



### POI报表引入pom出现jar包冲突

```xml
<!--POI报表-->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>3.6</version>
    <exclusions>
        <exclusion>
            <artifactId>jsp-api</artifactId>
            <groupId>javax.servlet.jsp</groupId>
        </exclusion>
        <exclusion>
            <artifactId>servlet-api</artifactId>
            <groupId>javax.servlet</groupId>
        </exclusion>
    </exclusions>
</dependency>
```

### Excel导出导出 - 阿里巴巴

参考：[Excel导入导出](http://www.pianshen.com/article/4672412475/)



### 前后端分离 - JWT用户认证

参考：[JWT用户认证](https://www.cnblogs.com/wenqiangit/p/9592132.html)



**传统方式**

```
前后端分离通过Restful API进行数据交互时，如何验证用户的登录信息及权限。在原来的项目中，使用的是最传统也是最简单的方式，前端登录，后端根据用户信息生成一个token，并保存这个 token 和对应的用户id到数据库或Session中，接着把 token 传给用户，存入浏览器 cookie，之后浏览器请求带上这个cookie，后端根据这个cookie值来查询用户，验证是否过期。

但这样做问题就很多，如果我们的页面出现了 XSS 漏洞，由于 cookie 可以被 JavaScript 读取，XSS 漏洞会导致用户 token 泄露，而作为后端识别用户的标识，cookie 的泄露意味着用户信息不再安全。尽管我们通过转义输出内容，使用 CDN 等可以尽量避免 XSS 注入，但谁也不能保证在大型的项目中不会出现这个问题。
```





### Restful风格

```
// 需求:
//   1. 查询
//        * URI: emps
//        * 请求方式: GET
//   2. 添加所有员工信息
//        2.1 显示添加页面:
//                * URI: emp
//                * 请求方式: GET
//        2.2 添加员工
//                * URI: emp
//                * 请求方式: POST
//                * 添加完成后,重定向到 list 页面
//   3. 删除
//        * URI: emp/{id}
//        * 请求方式: DELETE
//   4. 修改操作 (其中 lastName 不可修改!!)
//       4.1 显示修改页面
//              * URI: emp/{id}
//              * 请求方式: GET
//       4.2 修改员工信息
//              * URI: emp
//              * 请求方式: PUT
//              * 完成修改,重定向到 list 页面
```



### 树形结构

```sql
-- Oracle

-- 语法
select * from table 
start ··· with ···
Connent By ···

-- 示例
Select * From DEMO
Start With ID = '00001'
Connect By Prior ID = PID
```



```java
//递归查找所有菜单的子菜单
private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){
    
    List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
    	return categoryEntity.getParentCid() == root.getCatId();
    }).map(categoryEntity ->
        categoryEntity.setChildren(getChildrens(categoryEntity,all));
        return categoryEntity;
    }).sorted((menu1,menu2)->{
    	return menu1.getSort() - menu2.getSort();
    }).collect(Collectors.tolist());
    
    return children;
}
```



```java
 	/**
     * 获取该节点的子节点
     * @param nodeId
     * @param data
     * @return
     */
    public List<JSONObject> getChildren(String nodeId,List<JSONObject> data){
        List<JSONObject> child = new ArrayList<JSONObject>();
        for(JSONObject object : data){
            if(nodeId.equals(object.getString("depParent"))){
                child.add(object);
            }
        }
        return child;
    }

    /**
     * 部门树
     * 递归处理   数据库树结构数据->树形json
     * @param nodeId
     * @param nodes
     * @return
     */
    public JSONArray getNodeJson(String nodeId, List<JSONObject> nodes){

        //当前层级当前点下的所有子节点（实战中不要慢慢去查,一次加载到集合然后慢慢处理）
        List<JSONObject> childList = getChildren(nodeId,nodes);
        JSONArray childTree = new JSONArray();
        for (JSONObject node : childList) {
            JSONObject o = new JSONObject();
            o.put("key",node.getString("id"));
            o.put("title",node.getString("depName"));
            JSONArray child = getNodeJson(node.getString("id"),nodes);  //递归调用该方法
            if(!child.isEmpty()) {
                o.put("children",child);
            }
            childTree.fluentAdd(o);
        }
        return childTree;
    }

    /**
     * 部门树
     * @param
     * @return
     */
    public JSONArray findDeptRoleTree(){
        List<JSONObject> data = this.iDeptDao.findDeptRoleTree();
        JSONArray treeData =  getNodeJson("0",data);
        return treeData;
    }

	/*
		select depSerial, depNo, depName, depParent from dt_dep
    */
```



### Git

参考：[如何理解集中式与分布式](https://blog.csdn.net/weixin_42476601/article/details/82290902)   [Git vs SVN 与Git命令](https://www.cnblogs.com/qcloud1001/archive/2018/10/31/9884576.html)

.git目录中的config文件

```
[core]
	repositoryformatversion = 0
	filemode = false
	bare = false
	logallrefupdates = true
	symlinks = false
	ignorecase = true
[remote "origin"]
	url = http://guoyongchao@192.168.2.211:1010/r/dormitory-management-web.git
	fetch = +refs/heads/*:refs/remotes/origin/*
[branch "master"]
	remote = origin
	merge = refs/heads/master

```



### 时间转换多少分钟前、几天前

```js
var minute = 1000 * 60;
var hour = minute * 60;
var day = hour * 24;
var month = day * 30;

//获取个性化时间差 
export function getDateDiff(dateStr){
  let dateTimeStamp = dateStr;// Date.parse(dateStr.replace(/-/gi,"/")); //字符串转换为时间戳
  var now = new Date().getTime();
  var diffValue = now - dateTimeStamp;
  if(diffValue < 0){
      //若日期不符则弹出窗口告之
      console.log("结束日期不能小于开始日期！");
  }
  var yearC = diffValue/(12*month);
  var monthC = diffValue/month;
  var weekC = diffValue/(7*day);
  var dayC = diffValue/day;
  var hourC = diffValue/hour;
  var minC = diffValue/minute;
  let result = null;
  if(yearC >= 1){
      result = parseInt(yearC) + "年前";
  }
  else if(monthC >= 1){
      result = parseInt(monthC) + "个月前";
  }
  else if(weekC>=1){
      result = parseInt(weekC) + "周前";
  }
  else if(dayC>=1){
      result = parseInt(dayC) +"天前";
  }
  else if(hourC>=1){
      result = parseInt(hourC) +"个小时前";
  }
  else if(minC>=1){
      result = parseInt(minC) +"分钟前";
  }else{
      result="刚刚";
  }
  return result;
}
```



### 跨域问题

> **跨域不是请求发不出去，而是服务端正常返回结果后被浏览器拦截返回结果**
> **(浏览器为了防止非同源的请求 拿到服务器的返回数据结果)**

```js
1.JSONP	-- 原理就是利用了script标签，添加了一个script标签，利用标签特性达到跨域加载资源的效果。
    JSONP由两部分组成，回调函数和数据
    优点：
    （1）兼容性好，在多古老的浏览器都能运行。
    （2）能直接访问响应文本，支持在浏览器与服务器之间双向通信。
    缺点：
    （1）只支持GET请求，不支持POST请求；
    （2）不够安全。因为JSONP是从其他域中加载代码执行，如果其他域不安全，可能会在响应中带有恶意代码。
    （3）不容易确认请求是否失败。
    
    //jsonp请求，默认携带callback参数，方法回调success上面
    //http://localhost:9090/student?callback=jQuery172022456231109176& =1483893661922
     $.ajax({
         url: "http://localhost:9090/student",
         type: "GET",
         dataType: "jsonp", //指定服务器返回的数据类型
         success: function (data) {
             var result = JSON.stringify(data); //json对象转成字符串
         }
     });
    
2、CORS -- 跨站资源共享，它是跨域的官方解决方案，升级版的JSONP。
	原理是使用自定义的HTTP头部让浏览器与服务器进行沟通，从而决定请求或响应是应该成功还是失败。请求和响应都	不包含cookie信息。
	CORS需要浏览器和后院同时支持，浏览器会自动进行CORS通信，实现CORS通信的关键是后端，只要后端实现了		CORS，就实现了跨域，服务端设置Access-Control-Allow-Origin 就可以开启CORS，该属性表示哪些域名可	以访问资源，如果设置通配符则表示所有网站都可以访问资源。
```



### RabbitMQ

参考：[RabbitMQ](https://blog.csdn.net/hellozpc/article/details/81436980#8SpringbootRabbitMQ_1273) [Rabbit面试](https://blog.csdn.net/weixin_43496689/article/details/103159268)  [Rabbit详解](https://www.cnblogs.com/williamjie/p/9481774.html)  [springboot + rabbitmq发送邮件案例](https://www.jianshu.com/p/dca01aad6bc8)  [RabbitMQ专业](http://www.iocoder.cn/Spring-Boot/RabbitMQ/)



![](image/Rabbit内部结构图.jpg)



> RabbitMQ有四种Exchange类型，分别是Direct 、Topic、Fanout 、Headers

```
Direct Exchange 路由模式：默认类型，根据路由键（Routing Key）将消息投递给对应队列。
Topic Exchange 通配符模式：通过对消息的路由键（Routing Key）和绑定到交换机的队列，将消息路由给队列。符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词
Fanout Exchange 发布/订阅：将消息路由给绑定到它身上的所有队列，而不理会绑定的路由键（Routing Key）。
Headers Exchange 直连交换机：发送消息时匹配 Header 而非 Routing Key，性能很差，几乎不用。
```



>prefetch与消息投递

```
prefetch与消息投递
prefetch允许为每个consumer指定最大的unacked messages数目。简单来说就是用来指定一个consumer一次可以从Rabbit中获取多少条message并缓存在client中(RabbitMQ提供的各种语言的client library)。一旦缓冲区满了，Rabbit将会停止投递新的message到该consumer中直到它发出ack。

假设prefetch值设为10，共有两个consumer。意味着每个consumer每次会从queue中预抓取 10 条消息到本地缓存着等待消费。同时该channel的unacked数变为20。而Rabbit投递的顺序是，先为consumer1投递满10个message，再往consumer2投递10个message。如果这时有新message需要投递，先判断channel的unacked数是否等于20，如果是则不会将消息投递到consumer中，message继续呆在queue中。之后其中consumer对一条消息进行ack，unacked此时等于19，Rabbit就判断哪个consumer的unacked少于10，就投递到哪个consumer中。

总的来说，consumer负责不断处理消息，不断ack，然后只要unacked数少于prefetch * consumer数目，broker就不断将消息投递过去。

channel = connection.createChannel();
channel.basicQos(prefetch);
```



### Redis常见面试题

参考：[几率大的Redis面试题](https://blog.csdn.net/Butterfly_resting/article/details/89668661)  [Redis常见面试题](https://www.cnblogs.com/jasontec/p/9699242.html) [哨兵模式](https://www.jianshu.com/p/06ab9daf921d)



### Redis

> redis.conf

```
Linux中
daemonize no → daemonize yes

redis.conf配置文件中daemonize守护线程，默认是NO

daemonize:yes:redis采用的是单进程多线程的模式。当redis.conf中选项daemonize设置成yes时，代表开启守护进程模式。在该模式下，redis会在后台运行，并将进程pid号写入至redis.conf选项pidfile设置的文件中，此时redis将一直运行，除非手动kill该进程。

daemonize:no: 当daemonize选项设置成no时，当前界面将进入redis的命令行界面，exit强制退出或者关闭连接工具(putty,xshell等)都会导致redis进程退出。
```



> redis密码

```
redis-server
redis-cli -p 6379

ping #出现 PONG

config get requirepass

config set requirepass "123456"

ping #出现 NOAUTH Authentication required

auth 123456

ping #出现 PONG
```



> RDB(Redis DataBase )

```
Redis会单独创建(fork) 一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。整个过程中，主进程是不进行任何IO操作的，这就确保了极高的性能
如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那RDB方式要比AOF方式更加的高效。RDB的缺点是最后一次持久化后的数据可能丢失。

flushall和shutdown 都会触发rdb备份，如果执行该命令前使用了flushall那么数据备份的将是空数据库

dump.rdb

# 默认备份配置
save 60  10000 	# 1分钟内改了1万次
save 300 10		# 5分钟内改了10次
save 900 1		# 15分钟内改了1次
save ""			# 禁用

save命令可以立即备份

优势：适合大规模数据恢复、对数据的完整性和一致性要求不高
劣势：在一定时间做一次备份，所以如果redis意外down掉的话，就会丢失最后一次快照的所有修改、Fork的时候，复制进程，内存相当于扩大了一倍，需要考虑服务器的压力
```



> AOF(Append Only File)

```
以日志的形式来记录每个写操作，将Redis执行过的所有写指令记录下来(读操作不记录)，只许追加文件但不可以改写文件，redis启动之初会读取该文件重新构建数据
换言之，redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作

appendonly.aof

#打开apf备份
appendonly yes

#格式化AOF文件(去除杂乱代码)
redis-check-aof --fix appendonly.aof
#格式化RDB文件
redis-check-dump --fix dump.rdb

Appendsync：
	Always：同步持久化 每次发生数据变更会被立即记录到磁盘  性能较差但数据完整性比较好
	Everysec：出场默认推荐，异步操作，每秒记录，如果一秒内宕机，有数据丢失
	No：从不同步

重写
AOF采用文件追加方式，文件会越来越大为避免出现此种情况，新增了重写机制,当AOF文件的大小超过所设定的阈值时，Redis就会启动AOF文件的内容压缩，只保留可以恢复数据的最小指令集.可以使用命令bgrewriteaof

重写原理
AOF文件持续增长而过大时，会fork出一条新进程来将文件重写(也是先写临时文件最后再rename)，
遍历新进程的内存中数据，每条记录有一条的Set语句。重写aof文件的操作，并没有读取旧的aof文件，
而是将整个内存中的数据库内容用命令的方式重写了一个新的aof文件，这点和快照有点类似

触发机制
Redis会记录上次重写时的AOF大小，默认配置是当AOF文件大小是上次rewrite后大小的一倍且文件大于64M时触发
```



> 主从复制

```
行话：也就是我们所说的主从复制，主机数据更新后根据配置和策略，
自动同步到备机的master/slaver机制，Master以写为主，Slave以读为主，实现读写分离、容灾恢复

info replication #查看redis信息状态

怎么用
	配从不配主
	从库配置：SLAVEOF 主库IP 主库端口
	修改配置文件细节操作
	常用三招：
        一主二仆（主机挂掉，从机还是从机；从机挂掉，直接变成新主机。中心化严重）
        薪火相传
        反客为主(SLAVEOF no one使当前数据库停止与其他数据库的同步，转成主数据库)

复制原理
	slave启动成功连接到master后会发送一个sync命令
    Master接到命令启动后台的存盘进程，同时收集所有接收到的用于修改数据集命令，
    在后台进程执行完毕之后，master将传送整个数据文件到slave,以完成一次完全同步
    全量复制：而slave服务在接收到数据库文件数据后，将其存盘并加载到内存中。
    增量复制：Master继续将新的所有收集到的修改命令依次传给slave,完成同步
    但是只要是重新连接master,一次完全同步（全量复制)将被自动执行
```



> 哨兵模式(sentinel)

```
是什么
	反客为主的自动版，能够后台监控主机是否故障，如果故障了根据投票数自动将从库转换为主库

怎么玩
	自定义的/myredis目录下新建sentinel.conf文件，名字绝不能错
	sentinel.conf内容： sentinel monitor 被监控主库(自己起名字) 127.0.0.1 6379 1
	上面最后一个数字1，表示主机挂掉后salve投票看让谁接替成为主机，得票数多少后成为主机
	redis-sentinel /myredis/sentinel.conf 
```





### Redis缓存

SpringBoot与Redis整合

> pom.xml配置

```pom.xml
<!-- redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- spring2.X集成redis所需common-pool2-->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
    <version>2.6.0</version>
</dependency>
```



> application.properties配置

```properties
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.database=0
spring.redis.timeout=1800000

spring.redis.lettuce.pool.max-active=20
spring.redis.lettuce.pool.max-wait=-1
#最大阻塞等待时间(负数表示没限制)
spring.redis.lettuce.pool.max-idle=5
spring.redis.lettuce.pool.min-idle=0
```



> Redis缓存配置类

```java
@EnableCaching //开启缓存
@Configuration  //配置类
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setConnectionFactory(factory);
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //value hashmap序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置序列化（解决乱码的问题）,过期时间600秒
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(600))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }
}
```



> SpringBoot缓存注解

```
//key加上单引号
@Cacheable(key="'getUserList'",value="userList")
根据方法对其方法结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据返回；如果缓存不存在，则执行方法， 并将返回结果存入缓存中。一般用在查询方法上。

@CachePut
使用该注解标志的方法，每次都会执行并将结果存入指定的缓存中。其他方法可以直接从响应的缓存中读取缓存数据，
而不需要再去查询数据库。一般用在新增方法上。

@CacheEvict
使用该注解标志的方法，会清空指定的缓存，一般用在更新或者删除方法上。allEntries清空所有缓存属性
```



### Redis+RabbitMQ 实现订单秒杀

参考：[秒杀思路](https://blog.csdn.net/WayneLee0809/article/details/100930245)



### GET和POST两种基本请求方法的区别

参考：[GET和POST两种基本请求方法的区别](http://cnblogs.com/songanwei/p/9387815.html)

```
除了一些常见的区别，他们在请求速度上也会所区别，
GET产生一个TCP数据包；POST产生两个TCP数据包。
对于GET方式的请求，浏览器会把http header和data一并发送出去，服务器响应200（返回数据）；
而对于POST，浏览器先发送header，服务器响应100 continue，浏览器再发送data，服务器响应200 ok（返回数据）。

也就是说，GET只需要汽车跑一趟就把货送到了，而POST得跑两趟，第一趟，先去和服务器打个招呼“嗨，我等下要送一批货来，你们打开门迎接我”，然后再回头把货送过去。
因为POST需要两步，时间上消耗的要多一点，看起来GET比POST更有效。因此Yahoo团队有推荐用GET替换POST来优化网站性能。但这是一个坑！跳入需谨慎。为什么？
1. GET与POST都有自己的语义，不能随便混用。
2. 据研究，在网络环境好的情况下，发一次包的时间和发两次包的时间差别基本可以无视。而在网络环境差的情况下，两次包的TCP在验证数据包完整性上，有非常大的优点。
3. 并不是所有浏览器都会在POST中发送两次包，Firefox就只发送一次。
```





### 两个数组取差集

```js
let newUser = res.result;
newUser = newUser.filter(a => {
    return !this.lr.peopleData.find(b => {
     	return a.userSerial == b.userSerial   
    })
})
this.lr.peopleData.push(...newUser)
```



### 过滤器与拦截器的区别

参考：[过滤器与拦截器的区别](https://blog.csdn.net/zxd1435513775/article/details/80556034)



### 单点登录

参考：[单点登录](https://ke.qq.com/course/295318?taid=1976011373969814)

> 什么是单点登录?

```
单点登录全称single Sign On (以下简称SSO)，是指在多系统应用群中登录- -个系统，便可在其他所有系统中得到授权而无需再次登录，包括单点登录与单点注销两部分.
```



> 登录

```
相比于单系统登录，sso需要一个独立的认证中心，只有认证中心能接受用户的用户名密码等安全信息，其他系统不提供登录入口，只接受认证中心的间接授权。
间接授权通过令牌实现，sso认证中心验证用户的用户名密码没问题，创建授权令牌，在接下来的跳转过程中，授权令牌作为参数发送给各个子系统，子系统拿到令牌，即得到了授权，可以借此创建局部会话，局部会话登录方式与单系统的登录式相同。这个过程，也就是单点登录的原理，用下图说明
```



> 流程

```
1.访问A域名
2.验证没有登陆 重定向到统一登陆认证中心
3.验证是否有全局对话 ①没有则响应到同意登录页面 ②有 则相应到A域名主页
4.没有的话 则进行登录创建全局
```



### Https配置

> 生成密钥文件

```cmd
keytool -genkey -v -alias nianshaoyouwei -keyalg RSA -keystore C:/keys/nianshaoyouwei.keystore -validity 36500
```

> 生成证书

```cmd
keytool -export -alias nianshaoyouwei -storepass a9530.A. -file C:/keys/nianshaoyouwei.cer -keystore C:/keys/nianshaoyouwei.keystore
```

> 导入证书 需要先删除 jre\lib\security\cacerts 文件

```cmd
keytool -import -keystore "C:\Program Files\Java\jdk1.8.0_131\jre\lib\security\cacerts" -storepass changeit -keypass changeit -alias emailcert -file C:/keys/nianshaoyouwei.cer
```



### MyBatis-Plus

> Mybatis-Plus（简称MP）是一个 Mybatis 的增强工具，在 Mybatis 的基础上只做增强不做改变，为简化开发、提高效率而生 

```
<!--mybatis-plus-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.0.5</version>
</dependency>

<!--mysql-->
<!--mysql6以及以上是com.mysql.cj.jdbc.Driver 并且在url之后要指定时区-->
<!--如果你的配置数据库文件是 com.mysql.jdbc.Driver 这个jar包版本换成5.1.8的-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.8</version>
</dependency>

mapper 继承 BaseMapper<Entity>
```



> 主键生成策略

```java
/*
    AUTO、INPUT、NONE、
    mp自带策略：ID_WORKER(19位数字)、ID_WORKER_STR(19位字符)
*/
@TalbleId(type=IdType.AUTO)
private Loing id;

1.自动增长
	AUTO_INCREMENT
2.UUID
	每次生成随机唯一的值
3.Redis实现
    可以使用Redis集群来获职更高的贡吐里。假如一个集群中有5台Redis.
    可以初始化每台Redis的值分别是1,2,3,4,5,然后步长都是S。备个Redis生成的ID为:
        A: 1,6,11,16,21
        B: 2,7,12,17,22
        C: 1,3,8,13,18,23
        D: 4,9,14,19,24
        E: 5,10,15,20,25
4.mp自带策略
	snowflake算法
	核心思想是:
		使用41bit作为毫秒数，10bit作为机器的ID (5个bit是数据中心，5个bit的机器ID)
		12bit作为毫秒内的流水号意昧着每个节点在每毫秒可以产生4096个ID)
```



> 自动填充

```java
1.在实体类属性添加注解
	@TableField(fill=Field.INSERT)
	private Date createTime;
	@TableField(file=Field.INSERT_UPDATE)
	private Date updateTime;
2.创建类，实现MetaObjectHandle实现接口的方法
    @Component
    public class MyMetaObjectHandler implements MetaObjectHandler {
        //自动填充：执行插入操作
        @Override
        public void insertFill(MetaObject metaObject) {
            this.setFieldValByName("version",1,metaObject);
            this.setFieldValByName("createDateTime",new Date(),metaObject);
            this.setFieldValByName("updateDateTime",new Date(),metaObject);
        }

        //自动填充：执行更新操作
        @Override
        public void updateFill(MetaObject metaObject) {
            this.setFieldValByName("updateDateTime",new Date(),metaObject);
        }
    }
```



> 乐观锁

```
悲观锁 乐观锁是一种思想。

悲观锁这是一种对数据的修改抱有悲观态度的并发控制方式。我们一般认为数据被并发修改的概率比较大，所以需要在修改之前先加锁。但是在效率方面，处理加锁的机制会让数据库产生额外的开销，还有增加产生死锁的机会。使用的话，先将自动提交事务关闭，开启事务，select…for update会把数据给锁住，更新数据，关闭事务

乐观锁在对数据库进行处理的时候，乐观锁并不会使用数据库提供的锁机制。一般的实现乐观锁的方式就是记录数据版本。解决丢失更新，更新时带上版本号条件。使用的话update items set age = 2where id = 1 and version = 3;
```



> 乐观锁：MyBatisPlus实现

```java
1.再实体类属性添加注解
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
2.添加乐观锁插件
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
3.代码实现
    //测试乐观锁：先查询 再修改
    User user = userMapper.selectById(2);
    user.setUserName("二哥");
    int num = userMapper.updateById(user);
    System.out.println(num);
```



> 分页查询

```java
1.添加分页插件
    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
2.代码实现
    Page<User> userPage = new Page<>(1,3);
    userPage.setDesc("id");
    userMapper.selectPage(userPage,null);

    System.out.println(userPage.getCurrent());
    System.out.println(userPage.getSize());
    System.out.println(userPage.getTotal());
    System.out.println(userPage.getRecords().toString());
```



> 逻辑删除

```java
1.再实体类属性添加注解
    @TableLogic
	private Integer is_delete;
2.添加逻辑删除插件
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
3.实现代码
    //逻辑删除，底层执行的更新操作
    int num = userMapper.deleteById(2);
	//配置逻辑删除插件，查询时会带上is_delete条件，想查询删除的数据，只能通过.xml查询
    List<User> userList = userMapper.selectList(null);
```



> 性能分析

```java
1.添加性能分析插件
    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(500);//ms，超过此处设置的ms则sql不执行
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }
```



> 复杂查询

```java
//Wrapper实现类
QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//查询条件
userQueryWrapper.eq("user_name","采先生i");
//查询需要的字段
userQueryWrapper.select("id","user_name","create_date_time");
//执行查询
List<User> userList = userMapper.selectList(userQueryWrapper);
```



### ES6

> 解构

```
let user = {name:'张三',age:18}
let {name,age} = user;
```



> 箭头函数

```
var f1 = function(m){
	return m;
}
var f2 = m => m;
```



> 模板字符串

```0
let name = '张三';
let str = `我是${name}`;//我是张三

function f1(){
	return '李四'
}
let str = `我是${f1()}`;//我是李四
```



> 巧妙：

```js
var a = "";
console.log(a || '默认值');//当a为空的时候，会输出默认值

printNum(2);
function printNum(num='默认值'){
    console.log(num);//当不传参数时，会输出默认值
}
```



> sort

```js
let fPersons = persons.sort(function(x,y){
    return x-y;//升序
    //reutrn y-x;//降序
})
```



> filter

```js
let fPersons = persons.filter(p => p.name.indexOf())
```



> map 接收一个函数，将原数组中的所有元素用这个函数处理后放入新数组返回。

```js
let fPersons = persons.map(item => ({
	id:item.userId,
	name:item.userName
    //重新定义对象属性的key
}))
```



> reduce 为数组中的每一个元素依次执行回调函数，不包括数组中被删除或从未被赋值的元素

```
arr.reduce(callback,[initialValue])
1、previousValue (上一次调用回调返回的值，或者是提供的初始值( initialValue) )
2、currentValue (数组中当前被处理的元素)
3、index (当前元素在数组中的索引) 
4、array (调用reduce的数组) 

let arr = [2,-10,30,13];
let result = arr.reduce((a,b)=>{
	console.log("上次处理后:"+a);
	console.log("上次处理后:"+b);
	return a+b;
})
console.log(result);
```



> promise 封装异步操作

```js
function get(url,data){
	return new Promise(resolve,reject) => {
		$.ajax({
			url: url,
			data: data,
			success: function(data){
				resolve(data)''
			},
			error: function(err){
				reject(err);
			}
		})
	}
}

get("mock/user.json")
    .then((data) =>{
        console.log('用户查询成功:'+data);
        return get(`mock/user_score_${data.id}.json`);
    }).then((data)=>{
		console.log('课程查询成功:'+data);
    	return get(`mock/corse_score_${data.id}.json`);
    }).then((data)=>{
    	console.log('课程成绩查询成功:'+data);
	}).catch((err)=>{
    	console.log('出现异常:'+err);
	});
```



> 模块化

参考：[ES6模块花化](https://blog.csdn.net/qq_33295794/article/details/75338575)

```js
/*es5模块化*/

//创建模块化
const num1 = 1;
const num2 = 2;
module.export = {
	num1,num2
}
//引用模块化
const m = request('./文件名.js');
console.log(m.num1);



/*es6模块化：es6实现模块化操作，不能直接在node.js中直接运行，需要用babel转为ess5*/
//创建模块化
export function fun1(){
    console.log('fun1');
}
export function fun2(){
    console.log('fun2');
}
//引用模块化
import {fun1,fun2} from './文件名.js'
fun1();
fun2();
```



### 阿里云OSS

参考：[阿里云](https://www.aliyun.com/ )  [学习路径](https://help.aliyun.com/learn/learningpath/oss.html?spm=5176.7933691.1309819.8.7f392a66swxJkC&aly_as=3eLSnC9NS)

```java
//解决Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
```



### 阿里云短信验证

参考：[阿里云](https://www.aliyun.com/ )  



### 阿里云ICON

参考：[阿里云ICON](https://www.iconfont.cn/)



### OAuth2

参考：[如何理解OAuth2](http://www.ruanyifeng.com/blog/2019/04/oauth_design.html)  [OAuth 2.0 的四种方式](http://www.ruanyifeng.com/blog/2019/04/oauth-grant-types.html)

> OAuth2针对特定问题的一种解决方案(JWT是实现)，按照一定规则生成字符串，字符串包含用户信息

```
1.授权码
2.隐藏式
3.密码式
4.凭证式

主要解决两个问题
	1.开放系统间的授权	
	2.分布式访问问题
```



> 微信登陆

```
1.生成二维码
https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect


2.用户扫码会回调一个自定义函数，两个形参code、state
接着根据code获取临时票据,得到accsess_token 和 openid
https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code
                    
                    
3.拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s

appid: wxed9954c01bb89b47

# 微信开放平台 appsecret
appsecret: a7482517235173ddb4083788de60b90e

# 微信开放平台 重定向url（guli.shop需要在微信开放平台配置）
redirecturl: http://guli.shop/api/ucenter/wx/callback
```





### SSO单点登陆

参考：[springBoot+redis 实现session共享理解，应用场景单点登录](https://blog.csdn.net/qq_33251859/article/details/79972551 )

> 1.session广播

```
session复制
```



> 2.cookie+redis

```
1.在项目中任何一个模块进行登录，賽录之后，把数据放到两个地方
(1) redis,在key生成唯一随机值(ip、用户id等等)。在value获取用户数据
(2) cookie;把redis里面生成key值放到cookie里面
2、访问项目中其他模块，发送请求带着cookie进行发送。获取cookie值，拿着cookie做事情
(1)把cookie获取值，到redis进行查询，根据key进行查询，如果查询数据就是登录
```



> 3.使用token

```
token：按照一定规则生成字符串，字符串可以包含用户信息
1.在项目某个模块进行查录，查录之后，按照规则生成字符串，把登录之后用户包含到生成字符串里面，把字符串返回
(1)可以把字符串通过cookie返回
(2)把字符串通过地址栏返回
2、再去访问项目其他模块，每次访问在地址栏带着生成字符串，在访问模块里面获取地址栏字符串,根据字符串获取用户信息。如何可以获取到，就是登录
```



### JWT实现Token验证

参考：[JWT](https://www.jianshu.com/p/e88d3f8151db)   [JWT常见问题](https://blog.csdn.net/u013089490/article/details/84443667)  [JWT面试](https://blog.csdn.net/MINGJU2020/article/details/103039418?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1)  Token理解

```
1.调用登录接口返回token
2.前端把token放入cookie中(使用插件cnpm install js-cookie)
3.创建前端拦截器，判断cookie里面是否有token字符串，如果有把token字符串放入header(请求头中)
4.根据token值，调用接口根据token获取用户信息，为了首页显示，把返回用户信息放入cookie中
5.首页面显示用户信息，从cookie中获取

jwt生成的token每次请求要携带上,后台解析token就能获取到帐号信息
能很好解决分布式系统中常见的session不同步失效的问题,内容可以自己定义
我们用jwt里时存的是基本信息,JSON格式,只存了用户ID、用户名、昵称
```



> pom.xml

```xml
<!-- JWT-->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.7.0</version>
</dependency>
```



> JWTUtils

```java
package com.atguigu.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {
    //常量
    public static final long EXPIRE = 1000 * 60 * 60 * 24; //token过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //秘钥

    //生成token字符串的方法
    public static String getJwtToken(String id, String nickname){
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                .setSubject("guli-user")
            	//从什么时间计算
                .setIssuedAt(new Date())
            	//过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
				
            	//设置token主体部分 ，存储用户信息
                .claim("id", id)  
                .claim("nickname", nickname)

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token字符串获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
```



### Vue

框架：nuxt、element、ant、vant

> 过滤器：

```js
{{new Date() || formatDate('yyyy-MM-dd HH:mm:ss')}}

Vue.filter('formatDate'),function(value,format='yyyy-MM-dd'){
	//format='yyyy-MM-dd' es6写法，默认值 yyyy-MM-dd
    return '';
}
```



> 统计：

```js
let items = [1,2,3,4,5];
return this.items.reduce((preTotal,item) => preTotal+item ,0)
```



> 内置指令：

这些指令解析后，查看HTML就会消失，v-cloak就是利用这一点防止闪现

```txt
v:text		更新元素的testContent
v-html		更新元素的innerHTML
v-if		如果为true,当前标签才会输出到页面
v-else 		如果为false,当前标签才会输出到页面
v-show		通过控制display样式来控制显示/隐藏
v-for		遍历数组/对象
v-on		绑定那个事件,一般简写为@
v-bind		强制绑定解析表达式,一般简写为:
v-model		栓香港i昂数据绑定
ref			为某个元素注册一个唯一表示,vue独享通过$rels属性访问这个元素对象
v-cloak		使用它防止闪现表达式,与css配合:[v-cloak]{display:none}
```



> 定义指令：

全局指令

```js
<p v-upper-text="'Just do It'"></p>

Vue.directive('upper-text',function(el,binding){
    console.log(el,binding);
    el.textContent = binding.value.toUpperCase();
})
```

局部指令

```js
<p v-lower-text="msg">Just do It</p>

new Vue({
    el:"#app",
    data:{
       msg:"Just like this"
    },
    directives:{
    	'lower-text':function(el,binding){
        	el.textContent = binding.value.toLowerCase();
            //文本内容变成小写，仅仅是文本内容，msg变量内容不变
    	}
    }
})
```



> 绑定监听：

```js
<button @click="test1()>test1</button>
<button @click="test2('123')">test2</button>
<button @click="test2('123',$event)">test2</button>

new Vue({
    el:"#app",
    data:{
        test1(event){
            alert(event.target.innerHTML);//输出test1
        },
        test2(content){
            alert(content);//输出123
        },
        test1(content,event){
            alert(content);//输出123
            alert(event.target.innerHTML);//输出test
        }
    }
})
```



> 时间修饰符：

```html
<!-- 阻止单击事件冒泡 -->
<a @click.stop="doThis"></a>

<!-- 提交事件不再重载页面，阻止事件默认行为 -->
<form @submit.prevent="onSubmit"></form>

<!-- 修饰符可以串联  -->
<a @click.stop.prevent="doThat"></a>

<!-- 只当事件在该元素本身（而不是子元素）触发时触发回调 -->
<div @click.self="doThat">...</div>

<!-- click 事件只能点击一次，2.1.4版本新增 -->
<a @click.once="doThis"></a>
```



> 按键修饰符：

```html
<input @keyup.enter="submit">
<input @keyup.delete (捕获 "删除" 和 "退格" 键)="submit">
```



> 自定义事件：

```js
1.绑定事件
<TodoHeader @addTodo="addTodo" ref='header'/>
<!-- 声明ref，再js中能调用该组件，this.$refs.header -->

2.触发事件
const data {
    name:'_Cps',
    check:true
};
this.$emit('addTodo',data);
```



> 插件：

```js
/*********Begin-MyPlugin.js*********/
(function (window) {
      const MyPlugin = {}
      MyPlugin.install = function (Vue, options) {
        // 1. 添加全局方法或属性
        Vue.myGlobalMethod = function () {
          console.log('Vue函数对象的myGlobalMethod()')
        }

        // 2. 添加全局资源
        Vue.directive('my-directive',function (el, binding) {
          el.textContent = 'my-directive----'+binding.value
        })

        // 4. 添加实例方法
        Vue.prototype.$myMethod = function () {
          console.log('vm $myMethod()')
        }
      }
      //向外暴露
      window.MyPlugin = MyPlugin
})(window)
/*********End-MyPlugin.js*********/

<div id="test">
  <p v-my-directive="msg"></p>
</div>

<script type="text/javascript" src="../js/vue.js"></script>
<script type="text/javascript" src="vue-myPlugin.js"></script>
<script type="text/javascript">
  Vue.use(MyPlugin) // 声明使用插件,内部会调用插件对象的install()
  const vm = new Vue({
    el: '#test',
    data: {
      msg: 'HaHa'
    }
  });
  Vue.myGlobalMethod();
  vm.$myMethod();
</script>
```



生命周期：

```
1.beforeCreate
	数据模型未加载
	方法未加载
	html模板未加载

2.created
	数据模型已加载
    方法已加载
    html模板已加载(可以获取到)
    html模板未渲染(获取的是{{num}})

3.beforeMount
	html模板未渲染

4.mounted
	html已渲染

5.beforeUpdate
	数据模型已更新
	html模板未更新

6.update
	数据模型已更新
	html模板已更新
    	
```

![](image\Vue生命周期.png)



```js
new Vue({
    el:"#app",
    data:{
        isShow = true;
    },
    mounted(){//初始化显示之后立即调用
        this.intervalId = setInterval(
        	consolot.log('-------');
        	this.isShow = !this.isShow
        ),1000)
    },
    beforeDestory(){//死亡之前调用
        //清除定时器
        clearInterval(this.intervalId);//这里能调到intervarId，只因为初始化给了vue实例
    }
})
```



> Vue组件化编码

1. 拆分组件
2. 静态组件
3. 动态组件（初始化显示、交互）



消息订阅与发布

组件进行通信，没有任何位置的限制，就不用组件间传递数据了

```js
cnpm install  --save pubsub-js

import PubSub from pubsub-js

//订阅消息
PubSub.subscribe('deleteTodo',function(msg,data){
    //这里面的this就不是代表vue组件了可以这样写下面
})

//发布消息
PubSub.publish('deleteTodo',param)

---------------------------------------------------------------------------------------

//订阅消息
PubSub.subscribe('deleteTodo',(msg,data) => {
	//这里的this就可以代表vue组件，这个函数没有自己的this，就会使用外部的this
})

//发布消息
PubSub.publish('deleteTodo',(msg,data) => {
    //其他知识，如果是要返回对象的话,这样写 => ({
    //
	//})
})

```

> slot

此方式用于父组件向子组件传递`标签数据`

```js
//子组件
<template>
	<div>
        <slotname="xxx">不确定的标签结构 1</slot>
        <div>组件确定的标签结构</div>
        <slotname="yyy">不确定的标签结构 2</slot>
	</div>
</template>

//父组件
<div>
    <divslot="xxx">xxx 对应的标签结构</div>
    <divslot="yyy">yyyy 对应的标签结构</div>
</div>
```



> 路由参数

```js
1、手写完整的 path:
 
    this.$router.push({path: `/user/${userId}`});
 
    //获取参数：this.$route.params.userId
 
2、用 params 传递：
 
    this.$router.push({name:'user', params:{userId: '123'}});
 
    //获取参数：this.$route.params.userId
    //url 形式：url 不带参数，http:localhost:8080/#/user
 
3、用 query 传递：
 
    this.$router.push({path:'/user', query:{userId: '123'}});
 
    //获取参数：this.$route.query.userId
    //url 形式：url 带参数，http:localhost:8080/#/user?userId=123

/**
    this.$router.push({path:'/user', params:{userId}});  //->/user
    //这里的 params 不生效,如果提供了 path，则params 会被忽略
    
    query  相当于 get 请求，页面跳转的时候可以在地址栏看到请求参数，
    params 相当于 post 请求，参数不在地址栏中显示。
    要注意，以 / 开头的嵌套路径会被当作根路径。 这让你充分的使用嵌套组件而无须设置嵌套的路径。
*/
```



> 路由监听

```
//监听
watch:{
	//路由变化发生变化，则会执行该代码块
	$router(to,from){
		//同一个组件点击两次，不会再次执行created方法，所以有时候需求可能要进行一些处理。
	}
}
```



> Ajax请求

一、vue-resource模块

```js
//在项目中安装
cnpm install vue-resource --save

// 引入模块 
importVueResourcefrom'vue-resource' 
// 使用插件 
Vue.use(VueResource)
// 通过 vue/组件对象发送 ajax 请求 
this.$http.get('/someUrl').then((response)=>{ //successcallback 
    console.log(response.data)//返回结果数据
},(response)=>{ //errorcallback
    console.log(response.statusText)//错误信息
})
```

二、axios模块

```js
//在项目中安装
cnpm install axios --save

// 引入模块 
import axios from 'axios'

// 发送 ajax 请求 
axios.get(url) .then(response=>{ 
    console.log(response.data)// 得到返回结果数据 
}) .catch(error=>{ 
    console.log(error.message)//错误信息
})
```



> Vue + Axios 导入Excel

```js
import reqwest from 'reqwest'

//真正的导入
handleOkImport(e) {
    let _this = this;
    const { fileList } = this;
    const formData = new FormData();
    if(fileList.length==0){
        this.$message.warning('请选择文件!');
    }else{
        fileList.forEach((file) => {
            formData.append('file', file);
        });
        //console.log("fileList:"+fileList[0]);
        reqwest({
            url: _this.apiUrl+'importAttendance/importExcel',
            method: 'post',
            processData: false,
            data: formData,
            success: (result) => {
                if(result.code=='200'){
                    this.fileList = []
                    this.uploading = false
                    this.$message.success('导入成功!');
                    this.visibleImport = false;
                }else{
                    this.uploading = false
                    this.$message.error(result.message);
                }
                this.getLogList();
            },
            error: () => {
                this.uploading = false
                this.$message.error('导入失败!');
            },
        });
    }

},
```



> Vue + Ant 兼容IE浏览器

参考：[兼容性](https://blog.csdn.net/lydxwj/article/details/89912983)

babel.config.js`文件

```js
module.exports = {
  presets: [
    '@vue/app',
    // 兼容配置
    [
      '@babel/preset-env',
      {
        'useBuiltIns': 'entry'
      }
    ]
  ],
  // 按需加载配置
  plugins: [
    [
      'import',
      {
        libraryName: 'ant-design-vue',
        libraryDirectory: 'es',
        style: 'css'
      },
    ]
  ]
}
```



`main.js`文件（项目入口）

```js
// 引入@babel/polyfill处理兼容 
import '@babel/polyfill'

import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import store from './store/store'
import './plugins/antd.js'
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
```



防止数据读取缓存

参考：[防止数据读取缓存](https://blog.csdn.net/weixin_38659265/article/details/90265999)

![](image\解决IE总是读取缓存.png)

```js
//我们可以在封装axios时，默认给请求加上时间戳，下面是给get和post请求默认添加时间戳：
// 添加时间戳       
 if (config.method === 'post') {            
 	config.data = {                
 		...config.data,                
 		t: Date.parse(new Date()) / 1000           
 	}       
 } else if (config.method === 'get') {          
 	 config.params = {               
 	 	 t: Date.parse(new Date()) / 1000,               
 	 	...config.params            
 	}       
 }
```



IE路由页面路由 不刷新问题，在App.vue页面

```js
mounted() {
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
      window.addEventListener(
        "hashchange",
        () => {
          let currentPath = window.location.hash.slice(1);
          if (this.$route.path !== currentPath) {
            this.$router.push(currentPath);
          }
        },
        false
      );
    }
}
```





`router.js`，要考虑，IE路由问题，IE的URL如果加 ''#'' 号，页面则不会刷新，所以要想办法去掉路由中的 ''#'' 号

```js
export default new Router({
  mode : "history",	//该属性可以去掉 #
  routes: [ 
    {
      path: '/admin',
      component: admin
    },{
      path: '/home',
      component: home
    }
  ]
})
```



如果去掉了#号，Vue打包过后找不到页面解决办法

参考：[页面找不到](https://blog.csdn.net/IsITMan/article/details/85121729)

```

```



> Vue + Ant 中文国际化配置

```vue
<template>
  <a-locale-provider :locale="locale">
    <router-view/>
  </a-locale-provider>
</template>

<script>

import zhCN from "ant-design-vue/lib/locale-provider/zh_CN";
export default {
  data() {
    return {
      locale: zhCN
    };
  }
}
```



> Vue + Ant 表格分页

参考：[表格分页](https://www.cnblogs.com/zhaogaojian/p/11162280.html)

1、设置pagination

```html
 <a-table :columns="columns" :dataSource="data" :rowSelection="rowSelection" 	:pagination="pagination">
     <a slot="action" href="javascript:;">查看</a>
  </a-table>
```

2、自定义pagination，注意写成onChange,change不行，灰色部分请根据自己实际代码修改。

```js
data () {
    let _this = this;
    return {
      collapsed: false,
      data,
      sels,
      columns,
      rowSelection,
      pagination: {
        pageNo: 1,
        pageSize: 20, // 默认每页显示数量
        showSizeChanger: true, // 显示可改变每页数量
        pageSizeOptions: ['10', '20', '50', '100'], // 每页数量选项
        showTotal: total => `Total ${total} items`, // 显示总数
        onShowSizeChange: (pageNo, pageSize) => _this.changePage(1,pageSize),
        onChange:(pageNo,pageSize)=>_this.changePage(pageNo,pageSize),//点击页码事件
        total:0 //总条数
       }
    }
  },
```

3、Ajax读取数据列表时pagination.total赋总条数即可

```js
.then((response) => {
    that.data = response.data.items
    that.pagination.total=response.data.total
    console.log(response)
})
```

4、定义改变页码事件

```js
changePage(pageNo,pageSize){
    that.pagination.pageNo = pageNo;
    that.pagination.pageSize = pageSize;
    searchUser();//请求数据
}
```

5、读取数据时带上当前页、分页大小，过滤条件，后端代码可以简单使用通用分页方法返回Json数据即可，

```js
searchUser () {
    let param= {pageNo:this.pagination.pageNo,pageSize:this.pagination.pageSize};
    this.getUser(param);
},
```



> Vue 组件数据双向绑定

参考：[双向绑定](https://blog.csdn.net/w390058785/article/details/81076569)

```js
<body>
<script src="https://cdn.bootcss.com/vue/2.5.16/vue.js"></script>
<div id="box">
    <new-input v-bind:name.sync="name"></new-input>
    {{name}}
</div>
<script>
	Vue.component('new-input',{
        props: ['name'],
        data: function(){
            return {
                newName: this.name
            }	
        },
        template:'<label><input @keyup="changeName" v-model="newName" />你的名字</label>',
        methods: {
            changeName: function(){
                this.$emit('update:name',this.newName);
            }
        }	
    });
    new Vue({
        el:'#box',	
        data: {
        	name:'nick'		
        }
    });
</script>
</body>

/* ------------------------------------------------------------------------------- */
/*
	通过与方法一进行比较：会发现

    <new-input v-bind:name="name" v-on:update:name="name = $event"></new-input>

    被简化成了

    <new-input v-bind:name.sync="name"></new-input>

    而其他代码不变。

    所以我们在使用.sync修饰符的时候，只需要注意，v-bind:xx，v-on:update:xx，v-bind:xx.sync的差异就行了。

    而且注册事件的时候一定要用this.$emit( 'update:xx' );

*/

```



### 微信支付接口

通俗来讲，我们就是要和微信进行打交道，你调我API，我调你的API。这里大体思路是先 生成预订单，然后支付完成，但是生成订单需要一些准备条件（code、openId、sign等等）

1. 获取code
2. 通过code获取openId
3. 生成预订单，需要传很多参数（这里的获取sign稍微麻烦了一点），返回与订单信息，然后用户确认支付

```
weixin:
  pay:
    #关联的公众号appid
    appid: wx74862e0dfcf69954
    #商户号
    partner: 1558950191
    #商户key
    partnerkey: T6m9iK73b0kn9g5v426MKfHQH7X8rKwb
    #回调地址
    notifyurl: http://guli.shop/api/order/weixinPay/weixinNotify
    
    
    <dependency>
        <groupId>com.github.wxpay</groupId>
        <artifactId>wxpay-sdk</artifactId>
        <version>0.0.3</version>
    </dependency>
```



>JSAPI支付

参考：[JSAPI支付](https://www.cnblogs.com/wuer888/p/7839139.html)	 [JSAPI支付官网](https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_1)

```
1.统一下单，得到预支付id
其中会用到很多参数，appid mch_id openid sign这些，而openid和sign需要进行一些处理才能得到，
		按照规定将Map的参数转换成一个字符串的形式(字段名=字段值&字段名=字段值)并且进行字段名字典排序
		其实我们最终下单的参数是一个xml的String类型，所以我们还要把那些参数放入Map中转换成xml
```

```
2.组装调起支付的参数
这个步骤其实就是封装参数，预支付id、其他的配置信息sign签名生成请求数据
```

```
3.调起支付
使用jssdk或者h5接口调起支付进行支付，根据支付结果再进行相对应的操作
```





```js
//JS拉取支付
function onBridgeReady(){
   WeixinJSBridge.invoke(
      'getBrandWCPayRequest', {
         "appId":"wx2421b1c4370ec43b",     //公众号名称，由商户传入     
         "timeStamp":"1395712654",         //时间戳，自1970年以来的秒数     
         "nonceStr":"e61463f8efa94090b1f366cccfbbb444", //随机串     
         "package":"prepay_id=u802345jgfjsdfgsdg888",     
         "signType":"MD5",         //微信签名方式：     
         "paySign":"70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
      },
      function(res){
      if(res.err_msg == "get_brand_wcpay_request:ok" ){
      // 使用以上方式判断前端返回,微信团队郑重提示：
            //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
      } 
   }); 
}
if (typeof WeixinJSBridge == "undefined"){
   if( document.addEventListener ){
       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
   }else if (document.attachEvent){
       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
   }
}else{
   onBridgeReady();
}



//微信小程序
wx.chooseWXPay({
    timestamp: 0, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
    nonceStr: '', // 支付签名随机串，不长于 32 位
    package: '', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
    signType: '', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
    paySign: '', // 支付签名
    success: function (res) {
        // 支付成功后的回调函数
    }
});
```



> Native支付

参考：[Native支付](<https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1>)

1. 使用场景：用户扫描商户展示在各种场景的二维码进行支付。

2. Native支付流程：也称之为扫码支付，是支付指定的商品，所以不需要用户选择商品，客户端直接准备好所有的订单信息，直接调用微信下单API生成预订单，同时接受微信返回的code_url，生成二维码给用户。用户扫码链接，微信会验证该链接有效性，满足的话返回支付授权，也就是拉起支付，输入密码过后，提交授权直接到微信，微信会给予支付的结果，同时也会异步通知客户端。



```

//1 根据订单号查询订单信息
QueryWrapper<Order> wrapper = new QueryWrapper<>();
wrapper.eq("order_no",orderNo);
Order order = orderService.getOne(wrapper);

//2 使用map设置生成二维码需要参数
Map m = new HashMap();
m.put("appid","wx74862e0dfcf69954");
m.put("mch_id", "1558950191");
m.put("nonce_str", WXPayUtil.generateNonceStr());
m.put("body", order.getCourseTitle()); //课程标题
m.put("out_trade_no", orderNo); //订单号
m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
m.put("spbill_create_ip", "127.0.0.1");
m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
m.put("trade_type", "NATIVE");

//3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
//设置xml格式的参数
client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
client.setHttps(true);
//执行post请求发送
client.post();

//4 得到发送请求返回结果
//返回内容，是使用xml格式返回
String xml = client.getContent();

//把xml格式转换map集合，把map集合返回
Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

//最终返回数据 的封装
Map map = new HashMap();
map.put("out_trade_no", orderNo);
map.put("course_id", order.getCourseId());
map.put("total_fee", order.getTotalFee());
map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
map.put("code_url", resultMap.get("code_url"));        //二维码地址

```



### Docker

参考：[Docker安装](https://www.cnblogs.com/bigben0123/p/11350200.html)



#### Docker简介

> 三大要素：镜像 容器 仓库



> 镜像

```
Docker 镜像（Image）就是一个只读的模板。镜像可以用来创建 Docker 容器，一个镜像可以创建很多容器。
```



> 容器

```
 Docker 利用容器（Container）独立运行的一个或一组应用。容器是用镜像创建的运行实例。
 
它可以被启动、开始、停止、删除。每个容器都是相互隔离的、保证安全的平台。
 
可以把容器看做是一个简易版的 Linux 环境（包括root用户权限、进程空间、用户空间和网络空间等）和运行在其中的应用程序。


容器的定义和镜像几乎一模一样，也是一堆层的统一视角，唯一区别在于容器的最上面那一层是可读可写的。
```



> 仓库

```
 仓库（Repository）是集中存放镜像文件的场所。
仓库(Repository)和仓库注册服务器（Registry）是有区别的。仓库注册服务器上往往存放着多个仓库，每个仓库中又包含了多个镜像，每个镜像有不同的标签（tag）。
 
仓库分为公开仓库（Public）和私有仓库（Private）两种形式。
最大的公开仓库是 Docker Hub(https://hub.docker.com/)，
存放了数量庞大的镜像供用户下载。国内的公开仓库包括阿里云 、网易云 等
```



> 总结

```
需要正确的理解仓储/镜像/容器这几个概念:
 
Docker 本身是一个容器运行载体或称之为管理引擎。我们把应用程序和配置依赖打包好形成一个可交付的运行环境，这个打包好的运行环境就似乎 image镜像文件。只有通过这个镜像文件才能生成 Docker 容器。image 文件可以看作是容器的模板。Docker 根据 image 文件生成容器的实例。同一个 image 文件，可以生成多个同时运行的容器实例。
 
*  image 文件生成的容器实例，本身也是一个文件，称为镜像文件。
 
*  一个容器运行一种服务，当我们需要的时候，就可以通过docker客户端创建一个对应的运行实例，也就是我们的容器
 
* 至于仓储，就是放了一堆镜像的地方，我们可以把镜像发布到仓储中，需要的时候从仓储中拉下来就可以了。
```



> Docker与虚拟机比较

```
1.Docker有着比虚拟机更少的抽象层，由于Docker不需要Hypervisor实现硬件资源虚拟化，运行在Docker容器上的程序直接使用的都是实际物理机的硬件资源，因此在Cpu、内存利用率上Docker将会在效率上有明显优势。

2.Docker利用的是宿主机的内核，而不需要Guest OS(虚拟机安装的操作系统)，因此，当新建一个容器时，Docker不需要和虚拟机一样重新加载一个操作系统，避免了引导、加载操作系统内核这个比较费时费资源的过程，当新建一个虚拟机时，虚拟机软件需要加载Guest OS，这个新建过程是分钟级别的，而Docker由于直接利用宿主机的操作系统则省略了这个过程，因此新建一个Docker容器只需要几秒钟。
```

<img src="image\Docker与虚拟机比较.png" style="zoom: 67%;" />



#### Docker常用命令

> Docker 镜像命令

```
docker --hple	docker常用命令
docker ps		查看docker进程

docker images	列出本地主机上的镜像
	-a :列出本地所有的镜像（含中间映像层）
	-q :只显示镜像ID
	--digests :显示镜像的摘要信息
	--no-trunc :显示完整的镜像信息
	说明：REPOSITORY镜像名、TAG标签、IMAGE_ID唯一、CREATED创建时间、VIRTUAL SIZE镜像大小
	同一仓库源可以有多个TAG，代表这个仓库源的不同个版本，我们使用REPOSITORY:TAG来定义不同的镜像。如果你不指定一个镜像的版本标答，例如你只体用ubuntu.docker将默认体用ubuntu:latest镜像
	
	
docker search xxx 查询某个镜像
	-s : 列出收藏数不小于指定值的镜像
	--no-trunc :显示完整的镜像信息
	--automated : 只列出 automated build类型的镜像；

    # 星数超过30的tomcat
    docker search -s 30 tomcat
    
    
docker pull xxx[:TAG] 拉取镜像
	docker pull tomcat 等价于 docker pull tomcat:latest
	
	
docker rmi xxx 删除某个镜像/镜像ID
	-f 镜像ID :单个删除某个镜像
	-f 镜像名[:TAG] :单个删除某个镜像
	-f 镜像名[:TAG] 镜像名[:TAG] :多个删除镜像
	-f $(docker images -qa) 删除所有镜像
	docker rmi -f hello-world 强制删除某个镜像
	docker rmi hello-world 等价于 docker rmi hello-world:latest


docker commit -m=“提交的描述信息” -a=“作者” 容器ID 要创建的目标镜像名:[标签名]
```



> Docker 容器命令

```
有镜像才能创建容器


docker run [OPTIONS] xxx IMAGE [COMMAND] 启动交互式容器
	--name="容器新名字": 为容器指定一个名称；
    -d: 后台运行容器，并返回容器ID，也即启动守护式容器；
    -i：以交互模式运行容器，通常与 -t 同时使用；
    -t：为容器重新分配一个伪输入终端，通常与 -i 同时使用；
    -P: 随机端口映射；
    -p: 指定端口映射，有以下四种格式
            ip:hostPort:containerPort
            ip::containerPort
            hostPort:containerPort
            containerPort
     docker run -it centos	启动centos并分配终端      
     docker run -it -p 8080:8080 tomcat

docker ps 查看当前正在进行的docker进程
	-a :列出当前所有正在运行的容器+历史上运行过的
    -l :显示最近创建的容器。
    -n：显示最近n个创建的容器。
    -q :静默模式，只显示容器编号。
    --no-trunc :不截断输出。


docker restart 容器ID  返回容器信息以JSON字符串方式

在容器中
    exit 容器停止退出
    Ctrl+P+Q 容器不停止退出

docker start 容器ID

docker stop 容器ID

docker kill 容器ID 强制关闭容器

docker rm 容器ID 删除已停止的容器
	-f :停止并删除容器
	
	一次性删除多个容器
	docker rm -f $(docker ps -a -q)
	docker ps -a -q | xargs docker rm
	
	
	

docker run -d xxx 启动守护式容器	
    问题：然后docker ps -a 进行查看, 会发现容器已经退出
    很重要的要说明的一点: Docker容器后台运行,就必须有一个前台进程.
    容器运行的命令如果不是那些一直挂起的命令（比如运行top，tail），就是会自动退出的。

    这个是docker的机制问题,比如你的web容器,我们以nginx为例，正常情况下,我们配置启动服务只需要启动响应的service即可。例如
    service nginx start
    但是,这样做,nginx为后台进程模式运行,就导致docker前台没有运行的应用,
    这样的容器后台启动后,会立即自杀因为他觉得他没事可做了.
    所以，最佳的解决方案是,将你要运行的程序以前台进程的形式运行
	
	
docker logs -f -t --tail 容器ID  查看容器日志
	-t 是加入时间戳
	-f 跟随最新的日志打印
	--tail 数字 显示最后多少条


docker top 容器ID	查看容器内运行的进程

docker inspect 容器ID 查看容器内部细节


进入正在运行的容器并以命令行交互
	docker exec -it 容器ID bashShell 	在外部执行docker容器的命令 [/bin/bash运行docker终端]
			
	docker attach 容器ID	重新进入
    
	exec 是在容器中打开新的终端，并且可以启动新的进程
	
	attach 直接进入容器启动命令的终端，不会启动新的进程

docker cp 容器ID:容器内路径 目的主机路径
```



#### Docker镜像

> UnionFS(联合文件系统)

```
UnionFS（联合文件系统）：Union文件系统（UnionFS）是一种分层、轻量级并且高性能的文件系统，它支持对文件系统的修改作为一次提交来一层层的叠加，同时可以将不同目录挂载到同一个虚拟文件系统下(unite several directories into a single virtual filesystem)。Union 文件系统是 Docker 镜像的基础。镜像可以通过分层来进行继承，基于基础镜像（没有父镜像），可以制作各种具体的应用镜像。
 
特性：一次同时加载多个文件系统，但从外面看起来，只能看到一个文件系统，联合加载会把各层文件系统叠加起来，这样最终的文件系统会包含所有底层的文件和目录
```



> Docker镜像加载原理

```
 
 Docker镜像加载原理：

docker的镜像实际上由一层一层的文件系统组成，这种层级的文件系统UnionFS。
bootfs(boot file system)主要包含bootloader和kernel, bootloader主要是引导加载kernel, Linux刚启动时会加载bootfs文件系统，在Docker镜像的最底层是bootfs。这一层与我们典型的Linux/Unix系统是一样的，包含boot加载器和内核。当boot加载完成之后整个内核就都在内存中了，此时内存的使用权已由bootfs转交给内核，此时系统也会卸载bootfs。
 
rootfs (root file system) ，在bootfs之上。包含的就是典型 Linux 系统中的 /dev, /proc, /bin, /etc 等标准目录和文件。rootfs就是各种不同的操作系统发行版，比如Ubuntu，Centos等等。 
平时我们安装进虚拟机的CentOS都是好几个G，为什么docker这里才200M？？

对于一个精简的OS，rootfs可以很小，只需要包括最基本的命令、工具和程序库就可以了，因为底层直接用Host的kernel，自己只需要提供 rootfs 就行了。由此可见对于不同的linux发行版, bootfs基本是一致的, rootfs会有差别, 因此不同的发行版可以公用bootfs。

以我们的pull为例，在下载的过程中我们可以看到docker的镜像好像是在一层一层的在下载
```



> 为什么 Docker 镜像要采用这种分层结构呢

```
最大的一个好处就是 - 共享资源
 
比如：有多个镜像都从相同的 base 镜像构建而来，那么宿主机只需在磁盘上保存一份base镜像，
同时内存中也只需加载一份 base 镜像，就可以为所有容器服务了。而且镜像的每一层都可以被共享。

Docker镜像都是只读的
当容器启动时，一个新的可写层被加载到镜像的顶部(例如图下的tomcat)。
这一层通常被称作“容器层”，“容器层”之下的都叫“镜像层”。
```

![](image\镜像分层.png)



#### Docker容器数据券

```
容器的持久化

docker run -it -v /宿主机绝对路径目录:/容器内目录 镜像名 	容器宿主共享数据
docker run -it -v /宿主机绝对路径目录:/容器内目录:ro 镜像名	携带权限
docker run -it -v /cps_hose:/cps_contain centos:6.8

数据卷容器 容器间传递共享(--volumes-from 父容器ID)
命名的容器挂载数据卷，其它容器通过挂载这个(父容器)实现数据共享，挂载数据卷的容器，称之为数据卷容器
docker run -it --name dc02 --volumes-from dc01 zzyy/centos

容器之间配置信息的传递，数据卷的生命周期一直持续到没有容器使用它为止
```



#### DockerFile解析

> DockerFile构建过程解析

```
DockerFile是用来构建Docker镜像的构建文件，是由一系列命令和参数构成的脚本。

构建三步骤：DockerFile →(build)→ Docker镜像 →(run)→ Docker容器

从应用软件的角度来看，Dockerfile、Docker镜像与Docker容器分别代表软件的三个不同阶段，
*  Dockerfile是软件的原材料
*  Docker镜像是软件的交付品
*  Docker容器则可以认为是软件的运行态。
Dockerfile面向开发，Docker镜像成为交付标准，Docker容器则涉及部署与运维，三者缺一不可，合力充当Docker体系的基石。
```



> DockerFile体系结构(保留字指令)

```
FROM		基础镜像，当前新镜像是基于哪个镜像的
MAINTAINER	镜像维护者的姓名和邮箱地址
RUN			容器构建时需要运行的命令
EXPOSE		当前容器对外暴露出的端口
WORKDIR		指定在创建容器后，终端默认登陆的进来工作目录，一个落脚点
ENV			用来在构建镜像过程中设置环境变量
ADD			将宿主机目录下的文件拷贝进镜像且ADD命令会自动处理URL和解压tar压缩包
COPY		类似ADD，拷贝文件和目录到镜像中。 <源路径> 的文件/新一层的镜像内 <目标路径> 位置
VOLUME		容器数据卷，用于数据保存和持久化工作

CMD			指定一个容器启动时要运行的命令
			Dockerfile 中可以有多个 CMD 指令，但只有最后一个生效，CMD 会被docker run之后的参数替换

ENTRYPOINT 	指定一个容器启动时要运行的命令
			ENTRYPOINT 的目的和 CMD 一样，都是在指定容器启动程序及参数
			
ONBUILD		当构建一个被继承的Dockerfile时运行命令，父镜像在被子继承后父镜像的onbuild被触发
```



>自定义镜像mycentos

```
1.编写DockerFile文件
-------------------------------
FROM centosMAINTAINER zzyy<zzyy167@126.com>
ENV MYPATH /usr/localWORKDIR $MYPATH
RUN yum -y install vimRUN yum -y install net-tools
EXPOSE 80
CMD echo $MYPATHCMD echo "success--------------ok"CMD /bin/bash 
-------------------------------

2.构建出新镜像
docker build -f myDockerfile -t 新镜像名字:TAG .

3.运行
docker run -it 新镜像名字:TAG 

4.列出镜像变更历史
docker history 镜像名

# tomcat启动容器
docker run -d -p 8888:8080 --name my_tomcat 
-v /myDocker/tomcat/test:/usr/local/apache-tomcat-9.0.8/webapps/test 
-v /myDocker/tomcat/logs/:/usr/local/apache-tomcat-9.0.8/logs/ --privileged=true


docker run -p 12345:3306 
--name mysql 
-v /myDocker/mysql/conf:/etc/mysql/conf.d 
-v /myDocker/mysql/logs:/logs 
-v /myDocker/mysql/data:/var/lib/mysql 
-e MYSQL_ROOT_PASSWORD=123456 
-d mysql:5.7


-p 12345:3306：将主机的12345端口映射到docker容器的3306端口。
--name mysql：运行服务名字
-v /zzyyuse/mysql/conf:/etc/mysql/conf.d ：将主机/zzyyuse/mysql录下的conf/my.cnf 挂载到容器的 /etc/mysql/conf.d
-v /zzyyuse/mysql/logs:/logs：将主机/zzyyuse/mysql目录下的 logs 目录挂载到容器的 /logs。
-v /zzyyuse/mysql/data:/var/lib/mysql ：将主机/zzyyuse/mysql目录下的data目录挂载到容器的 /var/lib/mysql 
-e MYSQL_ROOT_PASSWORD=a9530.A.：初始化 root 用户的密码。
-d mysql:5.6 : 后台程序运行mysql5.6
 
 docker exec -it MySQL运行成功后的容器ID     /bin/bash
```



> MyTomcat的DockerFile

```
FROM         centos
MAINTAINER   Cai Peishen<peishen.cai@foxmail.com>

#把宿主机当前上下文的Cps.txt拷贝到容器/usr/local/路径下
COPY Cps.txt /usr/local/Cps.txt

#把java与tomcat添加到容器中
ADD jdk-8u171-linux-x64.tar.gz /usr/local/
ADD apache-tomcat-9.0.8.tar.gz /usr/local/

#安装vim编辑器
RUN yum -y install vim

#设置工作访问时候的WORKDIR路径，登录落脚点
ENV MYPATH /usr/local
WORKDIR $MYPATH

#配置java与tomcat环境变量
ENV JAVA_HOME /usr/local/jdk1.8.0_171
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV CATALINA_HOME /usr/local/apache-tomcat-9.0.8
ENV CATALINA_BASE /usr/local/apache-tomcat-9.0.8
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin

#容器运行时监听的端口
EXPOSE 8080

#启动时运行tomcat
# ENTRYPOINT ["/usr/local/apache-tomcat-9.0.8/bin/startup.sh"]
# CMD ["/usr/local/apache-tomcat-9.0.8/bin/catalina.sh","run"]
CMD /usr/local/apache-tomcat-9.0.8/bin/startup.sh && tail -F /usr/local/apache-tomcat-9.0.8/bin/logs/catalina.out
```





### Spring Security

参考：[认证流程](https://blog.csdn.net/yuanlaijike/article/details/84703690)  [配置方式](https://blog.csdn.net/houysx/article/details/80380831)  [配置方式]( https://blog.csdn.net/fellhair/article/details/91410281 )  [补充](https://www.cnblogs.com/yingbing/p/4552932.html    ) 

​	

Spring Security主要包含两个部分：用户认证和用户授权，本质上是Filter过滤器，对请求进行过滤

![](image\spring-security.png)

![](image\spring-security认证流程.png)

> 用户认证

如果访问的登录路径(/auth/login)，那么执行登录授权存储token操作，之后发放该请求；

如果访问不是则验证请求头中的token获取权限，之后发放该请求

```
如果系统的模块众多，每个模块都需要就行授权与认证，所以我们选择基于token的形式进行授权与认证，用户根据用户名密码认证成功，然后获取当前用户角色的一系列权限值，并以用户名为key;权限列表为value的形式存入redis缓存中
根据用户名相关信息生成token返回，浏览器将token记录到cookie中，每次调用api接口都默认将token携带到header请求头中，Spring-security解析header头获取token信息，解析token获取 当前用户名，根据用户名就可以从redis中获取权限列表，这样Spring -security就能够判断当前请求是否有权限访问
```



> 用户授权





### Spring Cloud

微服务：

```
但通常而言，微服务是一种架构模式或者说是一种架构风格, 它提倡将单一应用程序划分成一组小的服务，每个服务运行，在其独立的自己的进程中，服务之间互相协调、互相配合,为用户提供最终价值。

服务之间采用轻量级的通信机制互相沟通(通常是基于HTTP的RESTful API)。每个服务都围绕着具体业务进行构建,并粗能够被独立地部署到生产环境、类生产环境等。

另外,尽量避免统一的、集中式的服务管理机制，对具体的一个服务而言,应根据业务上下文，选择合适的语言、工具对其进行构建，可以有一个非常轻量级的集中式管理来协调这些服务，可以使用不同的语言来编写服务,也可以使用不同的数据存储。

-----------------------------------------------------------------------------------------

微服务化的核心就是将传统的一站式应用，根据业务拆分成一个一个的服务,彻底地去耦合每一个微服务提供单个业务功能的服务,一个服务做一件事,从技术角度看就是一种小而独立的处理过程，类似进程概念，能够自行单独启动或销毁，可以拥有自己独立的数据库。
```



#### Eureka服务注册与发现

> Eureka Server 提供服务注册和发现

> Service Provider服务提供方将自身服务注册到Eureka，从而使服务消费方能够找到

> Service Consumer服务消费方从Eureka获取注册服务列表，从而能够消费服务



```
Spring Cloud 封装了 Netflix 公司开发的 Eureka 模块来实现服务注册和发现(请对比Zookeeper)。
 
Eureka 采用了 C-S 的设计架构。Eureka Server 作为服务注册功能的服务器，它是服务注册中心。
 
而系统中的其他微服务，使用 Eureka 的客户端连接到 Eureka Server并维持心跳连接。这样系统的维护人员就可以通过 Eureka Server 来监控系统中各个微服务是否正常运行。SpringCloud 的一些其他模块（比如Zuul）就可以通过 Eureka Server 来发现系统中的其他微服务，并执行相关的逻辑。
请注意和Dubbo的架构对比

Eureka包含两个组件：Eureka Server和Eureka Client
Eureka Server提供服务注册服务
各个节点启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观的看到
 
 
EurekaClient是一个Java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询(round-robin)负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳(默认周期为30秒)。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除（默认90秒）
```



> Eureka保证AP

```
Eureka看明白了这一点, 因此在设计时就优先保证可用性。Eureka各个节点都是平等的,几个节点挂掉不会影响正常节点的工作，剩余的节点依然可以提供注册和查询服务。而Eureka的客户端在向某个Eureka注册或时如果发现连接失败,则会自动切换至其它节点，只要有一台Eureka还在,就能保证注册服务可用(保证可用性)，只不过查到的信息可能不是最新的(不保证强一致性)。

除此之外，Eureka还有一种自我保护机制， 如果在15分钟内超过85%的节点都没有正常的心跳，那么Eureka就认为客户端 与注册中心出现了网络故障，此时会出现以下几种情况:

1. Eureka不再从注册列表中移除因为长时间没收到心跳而应该过期的服务
2. Eureka仍然能够接受新服务的注册和查询请求,但是不会被同步到其它节点上(即保证当前节点依然可用)
3.当网络稳定时，当前实例新的注册信息会被同步到其它节点中
因此，Eureka可以很好的应对因网络故障导致部分节 点失去联系的情况，而不会像zookeeper那样使整 个注册服务瘫痪。
```



> Zookepper保证CP

```
当向注册中心查询服务列表时，我们可以容忍注册中心返回的是几分钟以前的注册信息，但不能接受服务直接down掉不可用。也就是说，服务注册功能对可用性的要求要高于一致性。

但是zk会出现这样一种情况， 当master节 点因为网络故障与其他节点失去联系时，剩余节点会重新进行leader选举。问题在于，选举leader的时间太长， 30 ~ 120s,且选举期间整个zk集群都是不可用的,这就导致在选举期间注册服务瘫痪。

在云部署的环境下，因网络问题使得zk集群失去master节点是较大概率会发生的事,虽然服务能够最终恢复，但是漫长的选举时间导致的注册长期不可用是不能容忍的。
```



> 自我保护机制

```
默认情况下，如果没有自我保护，EurekaServer在一定时间内没有接收到某个微服务实例的心跳，EurekaServer将会注销该实例(默认90秒)。但是当网络分区故障发生时，微服务与EurekaServer之间无法正常通信，以上行为可能变得非常危险了一因为微服务本身其实是健康的，此时本不应该注销这个微服务。

Eureka通过"自我保护模式"来解决这个问题一当EurekaServer节点在短时间内丢失过多客户端时(可能发生了网络分区故障)，那么这个节点就会进入自我保护模式。一旦进入该模式，EurekaServer就会保护服务注册表中的信息，不再删除服务注册表中的数据(也就是不会注销任何微服务)。当网络故障恢复后,该Eureka Server节点会自动退出自我保护模式。

-- 更容易理解的方式
在自我保护模式中，Eureka Server会保护服务注册表中的信息,不再注销任何服务实例。当它收到的心跳数重新恢复到阈值以上时，该Eureka Server节点就会自动退出自我保护模式。它的设计哲学就是宁可保留错误的服务注册信息，也不盲目注销任何可能健康的服务实例。一句话讲解:好死不如赖活着 AP(可用和容错)
```



> Bibbon负载均衡

Ribbon提供了多种负载均衡策略：比如轮询(RoundRobinRule)、随机(RandomRule)和撞南墙(RetyRule)、根据响应时间加权

```
Spring Cloud Ribbon是基于Netflix Ribbon实现的一套 客户端 负载均衡的工具。
 
简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供客户端的软件负载均衡算法，将Netflix的中间层服务连接在一起。Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。简单的说，就是在配置文件中列出Load Balancer（简称LB）后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器。我们也很容易使用Ribbon实现自定义的负载均衡算法。

LB，即负载均衡(Load Balance)，在微服务或分布式集群中经常用的一种应用。
负载均衡简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA。
常见的负载均衡有软件Nginx，LVS，硬件 F5等。
相应的在中间件，例如：dubbo和SpringCloud中均给我们提供了负载均衡，SpringCloud的负载均衡算法可以自定义。 
```



> Feign负载均衡

Feign是一个声明式的Web服务客户端， 使得编写Web服务客户端变得非常容易,
**只需要创建一个接口，然后在上面添加注解即可**。
参考官网: https://github.com/OpenFeign/feign



> Feign能干什么

```
Feign旨在使编写Java Http客户端变得更容易。
前面在使用Ribbon + RestTemplate时，利用RestTemplate对http请求的封装处理，形成了一套模版化的调用方法。但是在实际
开发中，由于对服务依赖的调用可能不止一处，往往一个接口会被多处调用，所以通常都会针对每个微服务自行封装一 些客户端
类来包装这些依赖服务的调用。所以Feign在此基础上做了进一 步封装，由他来帮助我们定义和实现依赖服务接口的定义。在
Feign的实现下，我们只需创建一个接并使用注解的方式来配置它(以前是Dao接口，上面标注Mapper注解现在是一 个微服务接口
上面标注一个Feign注解即可)， 即可完成对服务提供方的接口绑定,简化了使用Spring cloud Ribbon时，自动封装服务调用客户
端的开发量。
```



#### Ribbon与Feign

```
1.微服务名字获得调用地址
2.就是通过接口+注解，获得我们的调用服务。
```



#### Hystrix断路器

向调用方返回一个符合预期的、可处理的备选响应(FallBack) ,而不是长时间的等待或者抛出调用方无法处理的异常

```
复杂分布式体系结构中的应用程序有数十个依赖关系，每个依赖关系在某些时候将不可避免地失败。
Hystrix是一个用于处理分布式系统的延迟和容错的开源库,在分布式系统里,许多依赖不可避免的会调用失败,比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败,避免级联故障,以提高分布式系统的弹性。
"断路器”本身是一种开关装置,当某个服务单元发生故障之后,通过断路器的故障监控(类似熔断保险丝)，向调用方返回一个符合预期的、可处理的备选响应(FallBack) ,而不是长时间的等待或者抛出调用方无法处理的异常,这样就保证了服务调用方的
线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。
```



>服务雪崩

```
多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又调用其它的微服务,这就是所谓的“扇出”。
如果扇出的链路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源,进而引起系统崩溃,所谓的“雪崩效应”.

对于高流量的应用来说，单一的后端依赖可能会导致所有服务器上的所有资源都在几秒钟内饱和。比失败更糟糕的是,这些应用程
序还可能导致服务之间的延迟增加，备份队列，线程和其他系统资源紧张，导致整个系统发生更多的级联故障。这些都表示需要对
故障和延迟进行隔离和管理，以便单个依赖关系的失败，不能取消整个应用程序或系统。
```



> 服务熔断

一般是某个服务故障或者异常引起类似现实世界中的“保险丝“， 当某个异常条件被触发，直接熔断整个服务，而不是一直等到此服务超时导致雪崩。

```
熔断机制是应对雪崩效应的一种微服务链路保护机制。
当扇出链路的某个微服务不可用或者响应时间太长时，会进行服务的降级,进而熔断该节点微服务的调用,快速返回”错误”的响应信息。
当检测到该节点微服务调用响应正常后恢复调用链路。在SpringCloud框架里熔断机制通过Hystrix实现。
Hystrix会监控微服务间调用的状况，当失败的调用到一定阈值,缺省是5秒内20次调用失败就会启动熔断机制。熔断机制的注解是@HystrixCommand.
```



> 服务降级

```
所谓降级， 一般是从整体负荷考虑。就是当某个服务熔断之后，服务器将不再被调用
此时客户端可以自己准备一个本地的fallback回调， 返回一个缺省值。
这样做，虽然服务水平下降，但好歹可用，比直接挂掉要强。

整体资源快不够了，忍痛将某些服务先关掉，待渡过难关，再开启回来
服务降级处理是在客户端实现完成的，与服务端没有关系
让客户端在服务端不可用时也会获得提示信息而不会挂起耗死服务器
```



> 服务监控hystrixDashboard

```
除了隔离依赖服务的调用以外，Hystrix还提供了准实时的调用监控（Hystrix Dashboard），Hystrix会持续地记录所有通过Hystrix发起的请求的执行信息，并以统计报表和图形的形式展示给用户，包括每秒执行多少请求多少成功，多少失败等。Netflix通过hystrix-metrics-event-stream项目实现了对以上指标的监控。Spring Cloud也提供了Hystrix Dashboard的整合，对监控内容转化成可视化界面。
```



> 如何看监测数据：7色 1圈 1线

参考： [Hystrix仪表盘监控HystrixDashboard](https://www.cnblogs.com/coding-farmer/p/12032403.html)

```
7色：
	绿 成功色
	蓝 熔断数
	青 错误请求数
	黄 超时数
	紫 线程拒绝数
	红 失败/异常数
	灰 最近10s错误百分比

实心圆：共有两种含义。它通过颜色的变化代表了实例的健康程度，它的健康度从绿色<黄色<橙色<红色递减。
该实心圆除了颜色的变化之外，它的大小也会根据实例的请求流量发生变化，流量越大该实心圆就越大。所以通过该实心圆的展示，就可以在大量的实例中快速的发现 故障实例和高压力实例。

曲线：用来记录2分钟内流量的相对变化，可以通过它来观察到流量的上升和下降趋势。
```



#### Zuul路由网关

Zuul包含了对请求的路由和过滤两个最主要的功能：

```
其中路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础而过滤器功能则负责对请求的处理过程进行干预，是实现请求校验、服务聚合等功能的基础.

Zuul和Eureka进行整合，将Zuul自身注册为Eureka服务治理下的应用，同时从Eureka中获得其他微服务的消息，也即以后的访问微服务都是通过Zuul跳转后获得。

注意：Zuul服务最终还是会注册进Eureka

提供=代理+路由+过滤三大功能
```

 

 

#### Config分布式配置中心

> 是什么

```
SpringCloud Config为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为各个不同微服务应用的所有环境提供了一个中心化的外部配置。
```



> 怎么玩

```
SpringCloud Config分为服务端和客户端两部分。

服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配置服务器并为客户端提供获取配置信息,加密/解密信息等访问接口

客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容,并在启动的时候从配置中心获取和加载配置信息
配置服务器默认采用git来存储配置信息,这样就有助于对环境配置进行版本管理,并且可以通过git客户端工具来方便的管理和访问配置内容。
```



> 分布式面临的配置问题

```
微服务意味着要将单体应用中的业务拆分成一个个 子服务，每个服务的粒度相对较小，因此系统中会出现大量的服务。由于每个
服务都需要必要的配置信息才能运行，所以一集中式的、动态的配置管理设施是必不可少的。SpringCloud提供 了
ConfigServer来解决这个问题，我们每一个微服务自己带着一 个application.yml,. 上百个配置文件的管理...

Config查看配置信息：http://127.0.0.1:3344/application-dev.yml
```



> bootstrap.yml

```
applicaiton.yml是用户级的资源配置项
bootstrap.yml是系统级的，优先级更加高

Spring Cloud会创建一个Bootstrap Context',作为Spring应用的Application Context'的父上下文。
初始化的时候,Bootstrap Context负责从外部源加载配置属性并解析配置。这两个上下文共享一个从外部获取的“Environment'。
Bootstrap'属性有高优先级,默认情况下，它们不会被本地配置覆盖。'Bootstrap context'和Application Context'有着不同的约定,所以新增了一个bootstrap.yml文件, 保证Bootstrap Context'和Application Context'配置的分离。
```



### Spring Cloud Alibaba

```
SpringCloud的几大痛点
    SpringCloud部分组件停止维护更新，部分不再开源，给开发带来不便;
    SpringCloud部分环境搭建复杂，没有完善的可视化界面，我们需要大量的二次开发和定制
    SpringCloud配置复杂，难以上手，部分配置差别难以区分和合理应用

SpringCloud Alibaba的优势:
    阿里使用过的组件经历了考验，性能强悍，设计合理，现在开源出来大家用
    成套的产品搭配完善的可视化界面给开发运维带来极大的便利
    搭建简单，学习曲线低。
    
SpringCloud Alibaba 最终技术搭配方案
    SpringCloud Alibaba - Nacos:注册中心(服务发现/注册)
    SpringCloud Alibaba - Nacos:配置中心(动态配置管理)
    SpringCloud - Ribbon: 负载均衡
    SpringCloud - Feign: 声明式httP客户端(调用远程服务，feign闭源，使用的openFeign)
    SpringCloud Alibaba - Sentinel: 服务容错(限流、降级、熔断)
    SpringCloud - Gateway: API网关(webflux 编程模式)
    SpringCloud - Sleuth: 调用链监控
    SpringCloud Alibaba - Seata: 原Fescar, 即分布式事务解决方案

版本选择
    Spring Cloud Version		Spring Cloud Alibaba 	Version Spring Boot Version
    -----						-----					-----
    Spring Cloud Greenwich		2.1.x.RELEASE			2.1.x. RELEASE
    Spring Cloud Finchley		2.0.x.RELEASE			2.0.x.RELEASE
    Spring Cloud Edgware		1 5.x.RELEASE			1 5.x.RELEASE
    
    
依赖统一管理，然后在新创建dependencies标签中添加自己所需要的以来，无需写版本号
	<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.1.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```



#### Nacos

> Nacos注册配置中心

```
记录所有的服务信息，以Map<String,List<Object>>存储个服务信息，key为服务名，我们通过服务名就可以取。
自定义负载均衡，获取所有服务，它采用策略模式，就是声明接口，我们只需要去继承该接口，实现返回服务的方法就可以。
```



```
1.引入依赖
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    
2.在配置文件中，配置Nacos Server地址
	#一、
	spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
	spring.application.name=gulimall-coupon
	
	#二、
	#spring:
    #  cloud:
    #    nacos:
    #      discovery:
    #        server-addr: 127.0.0.1:8848
    #  application:
    #    name: gulimall-coupon

3.使用@EnableDiscoveryClient注解开启服务注册与发现功能
    @EnableDiscoveryClient
    @SpringBootApplication
    public class Application{
        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }

4.启动Nacos服务，启动微服务
```



> Nacos配置中心管理

```
本地应用读取我们云端分布式配置中心文件(第一次建立长连接)。
本地应用读取到配置文件之后，本地jvm和硬盘中都会缓存一份。
本地应用与分布式配置中心服务器端一直保持长连接.
当我们的配置文件发生变化(MD5|版本号)实现区分，将变化结果通知给我们的本地应用，及时的刷新我们的配置文件
```



```
1.引入依赖
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>

2.创建bootstrap.properties文件(优先级别高)
	# 默认根据服务名 获取Nacos中properties文件加载配置 如果没有 则加载程序的application.properties
    spring.application.name=gulimall-coupon
    spring.cloud.nacos.config.server-addr=127.0.0.1:8848

3.给在Nacos配置中心添加数据集(Data Id) gulimall-coupon.properties
	设置该服务的相关配置信息

4.动态刷新配置
    @RefreshScope: 动态获取并刷新配置，给类添加
    @Value("${配置项名}"): 获取配置
```



> Nacos分布式配置管理细节

```
1）、命名空间：配置隔离；
	默认：public(保留空间)；默认新增的所有配置都在public空间。
	1、开发，测试，生产：利用命名空间来做环境隔离。
		注意：在bootstrap.properties; 需要使用哪个命名空间下的配置
		spring.cloud.nacos.config.namespace=9de62e44-cd2a-4a82-bf5c-95878bd5e871
	2、每一个微服务之间互相隔离配置，每一个微服务都创建自己的命名空间，只加载自己命名空间下的所有配置

2）、配置集：所有的配置的集合

3）、配置集ID：类似文件名。
	 Data ID：类似文件名

4）、配置分组：默认所有的配置集都属于：DEFAULT_GROUP；
	1111，618，1212
	spring.cloud.nacos.config.group=prod
	
* 项目中的使用：每个微服务创建自己的命名空间，使用配置分组区分环境，dev，test，prod
    spring.application.name=gulimall-coupon
    spring.cloud.nacos.config.server-addr=127.0.0.1:8848
	spring.cloud.nacos.config.namespace=9de62e44-cd2a-4a82-bf5c-95878bd5e871
	spring.cloud.nacos.config.group=prod

* 同时加载多个配置集
 * 1)、微服务任何配置信息，任何配置文件都可以放在配置中心中
 * 2）、只需要在bootstrap.properties说明加载配置中心中哪些配置文件即可
 * 3）、@Value，@ConfigurationProperties。。。
 * 以前SpringBoot任何方法从配置文件中获取值，都能使用。
 * 配置中心有的优先使用配置中心中的
 
	spring.cloud.nacos.config.server-addr=127.0.0.1:8848
    spring.cloud.nacos.config.namespace=1986f4f3-69e0-43bb-859c-abe427b19f3a
    # 如果不指定分组，默认DEFAULT_GROUP，如果naocs没有，则加载程序的application.properties
    spring.cloud.nacos.config.group=prod 

    spring.cloud.nacos.config.ext-config[0].data-id=datasource.yml
    spring.cloud.nacos.config.ext-config[0].group=dev
    spring.cloud.nacos.config.ext-config[0].refresh=true

    spring.cloud.nacos.config.ext-config[1].data-id=mybatis.yml
    spring.cloud.nacos.config.ext-config[1].group=dev
    spring.cloud.nacos.config.ext-config[1].refresh=true

    spring.cloud.nacos.config.ext-config[2].data-id=other.yml
    spring.cloud.nacos.config.ext-config[2].group=dev
    spring.cloud.nacos.config.ext-config[2].refresh=true
```



> 数据持久化

参考：[Nacos]( https://nacos.io/zh-cn/docs/deployment.html)

```
默认的情况下，分布式配置中心的数据存放到本地data目录下，但是这种情况如果nacos集群的话无法保证数据的同步性。

在0.7版本之前，在单机模式时nacos使用嵌入式数据库实现数据的存储，不方便观察数据存储的基本情况。0.7版本增加了支持mysql数据源能力，具体的操作步骤：

1.安装数据库，版本要求：5.6.5+
2.初始化mysql数据库，数据库初始化文件：nacos-mysql.sql
3.修改conf/application.properties文件，增加支持mysql数据源配置（目前只支持mysql），添加mysql数据源的url、用户名和密码。

spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=root
db.password=root
```



> 集群配置

<img src="image\Nacos集群.png" style="zoom: 50%;" />

```
相关集群配置


创建cluster文件夹
---nacos-server-8848
---nacos-server-8849
---nacos-server-8850

cluster.conf
###ip和端口号 不要用127.0.0.1
192.168.1.22:8848
192.168.1.22:8849
192.168.1.22:8850


Nginx相关配置
轮询分发给三台,关于session共享Nacos自己解决了

客户端连接 
spring.cloud.nacos.config.server-addr=192.168.1.22:8848

注意： 
1.nacos在windows版本下运行默认是单机版本 需要指定startup.cmd -m cluster
2.nacos在linux版本下运行默认是集群版本 如果想连接单机版本 startup.cmd –m standalone

```



> Zookeeper Eureka Nacos区别

```
Zookeeper采用CP保证数据的一致性的问题，原理采用Zab原子广播协议，当我们的zk领导因为某种原因宕机的情况下，会自动出发重新选一个新的领导角色，整个选举的过程为了保证数据的一致性的问题，在选举的过程中整个zk环境是不可以使用，可以短暂可能无法使用到zk，以为者微服务采用该模式的情况下，可能无法实现通讯。(本地有缓存除外)。
注意：可运行节点必须满足过半机制，整个zk采用使用。
三台集群：两台宕机，一台正常，还是没法使用需要过半选举。
zad协议通过比较myid 谁最大谁为领导角色，只要满足过半机制就可以称为领导者。
如何办证数据一致性，所有写的请求都交给我们领导角色实现，领导写完，再将数据同步每个节点(也就是说,选举过程中不能注册服务)


Eureka采用ap的设计理念架构注册中心，完全去中心化思想，也就是没有主从之分。每个节点都是均等，采用相互注册原理，你中有我我中你，只要最后有一个eureka节点存在就可以保证整个微服务可以实现通讯。相互注册


Nacos与Eureka区别.

Nacos.从1.0版本支持CP和AP混合模式集群，默认是采用Ap保证服务可用性，CP的形式底层集群raft协议保证数据的一致性的问题。
如果我们采用Ap模式注册服务的实例仅支持临时注册形式，在网络分区产生抖动的情况正任然还可以继续注册我们的服务列表。
那么选择CP模式必须保证数据的强一致性的问题，如果网络分区产生抖动的情况下，是无法注册我们的服务列表。选择CP模式可以支持注册实例持久。

```



#### Feign

> Feign远程调用(openFeign)

```
1.引入依赖
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>

2.开启feign功能
	@EnableFeignClients(basePackages = "com.atguigu.gulimall.member.feign")
    @EnableDiscoveryClient
    @SpringBootApplication
    public class Application {
        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }
    
3.声明远程接口
    @FeignClient("gulimall-coupon")
    public interface CouponFeignService {
        @RequestMapping("/coupon/coupon/member/list")
        public R membercoupons();
    }
```



#### GateWay

> Spring Cloud GateWay

```
微服务
微服务网关是整个微服务API请求的入口，可以实现日志拦截、权限控制、解决跨域问题、限流、熔断、负载均衡、黑名单与白名单拦截、授权等
    
过滤器与网关的区别    
过滤器用于拦截单个服务
网关拦截整个的微服务

Zuul与Gateway有那些区别
Zuul网关属于netfix公司开源的产品属于第一代微服务网关
Gateway属于SpringCloud自研发的第二代微服务网关
相比来说SpringCloud Gateway性能比Zuul性能要好：
注意：Zuul基于Servlet实现的，阻塞式的Api，不支持长连接。
SpringCloudGateway基于Spring5构建，能够实现响应式非阻塞式的Api，支持长连接，能够更好的整合Spring体系的产品。

keep+keepAlived
Nignx Nignx Nignx
Gateway Gateway Gateway
订单服务 会员服务 积分服务 商品服务
```



```
构成
    1.route		路由
    2.predicate 断言
    3.Filter	过滤
    * 断言条件判断成功 路由再经过一些过滤，最终到相对应的请求
    
    
使用

	1.引入依赖
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        
	2.注册服务
		@EnableDiscoveryClient
        @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) #排除数据源
        public class Application {
            public static void main(String[] args) {
                SpringApplication.run(Application.class, args);
            }
        }
        
	3.添加bootstrap.properties配置
		spring.application.name=gulimall-coupon
        spring.cloud.nacos.config.server-addr=127.0.0.1:8848
        spring.cloud.nacos.config.namespace=b5d62415-0dea-4747-a65d-874cc6203bf2
        
	4.application.yml网关配置
		spring:
      cloud:
        gateway:
          routes:
    #        - id: test_route
    #          uri: https://www.baidu.com
    #          predicates:
    #            - Query=url,baidu
    #
    #        - id: qq_route
    #          uri: https://www.qq.com
    #          predicates:
    #            - Query=url,qq
	
```



#### Sentinel

> 服务限流/熔断

```
服务限流目的是为了更好的保护我们的服务，在高并发的情况下，如果客户端请求的数量达到一定极限（后台可以配置阈值），请求的数量超出了设置的阈值，开启自我的保护，直接调用我们的服务降级的方法，不会执行业务逻辑操作，直接走本地falback的方法，返回一个友好的提示。
```



> 服务降级

```
在高并发的情况下，防止用户一直等待，采用限流/熔断方法，使用服务降级的方式返回一个友好的提示给客户端，不会执行业务逻辑请求，直接走本地的falback的方法。
提示语：当前排队人数过多，稍后重试~
```



> 服务的雪崩效应

```
默认的情况下，Tomcat或者是Jetty服务器只有一个线程池去处理客户端的请求，
这样的话就是在高并发的情况下，如果客户端所有的请求都堆积到同一个服务接口上， 
那么就会产生tomcat服务器所有的线程都在处理该接口，可能会导致其他的接口无法访问。


假设我们的tomcat线程最大的线程数量是为20，这时候客户端如果同时发送100个请求会导致有80个请求暂时无法访问，就会转圈。
```



>服务的隔离的机制

```
服务的隔离机制分为信号量和线程池隔离模式
服务的线程池隔离机制：每个服务接口都有自己独立的线程池，互不影响，缺点就是占用cpu资源非常大。
服务的信号量隔离机制：最多只有一定的阈值线程数处理我们的请求，超过该阈值会拒绝请求。
```



### SOA

```
SOA只是一种架构设计模式，而SOAP、REST、RPC就是根据这种设计模式构建出来的规范，
其中SOAP通俗理解就是http+xml的形式，
REST就是http+json的形式，
RPC是基于socket的形式。
上文提到的CXF就是典型的SOAP/REST框架，dubbo就是典型的RPC框架，而SpringCloud就是遵守REST规范的生态系统。
```



### RPC

```
RPC【Remote Procedure Call】是指远程过程调用，是一种进程间通信方式，他是一种技术的思想，而不是规范。它允许程序调用另一个地址空间（通常是共享网络的另一台机器上）的过程或函数，而不用程序员显式编码这个远程调用的细节。即程序员无论是调用本地的还是远程的函数，本质上编写的调用代码基本相同。

RPC两个核心模块：通讯，序列化
```

![1588055070462](\image\RPC流程.png)

![1588055177701](\image\RPC核心流程.png)







### Dubbo + Zookeeper

```
Apache Dubbo是一款高性能、轻量级的开源Java RPC框架，它提供了三大核心能力：面向接口的远程方法调用，智能容错和负载均衡，以及服务自动注册和发现。
阿里2014年停更，后来与当当网的版本DubboX整合，在2018年开源给了apache

特性
1.面向接口代理的高性能RPC调用
2.服务自动注册与发现
3.智能负载均衡
4.高度可扩展
5.运行期间流量调度(多版本)
6.可视化的服务治理与运维
```

<img src="\image\Dubbo基本概念.png" alt=" " style="zoom:60%;" />

> 基本概念

```
服务提供者(Provider): 暴露服务的服务提供方，服务提供者在启动时，向注册中心注册自己提供的服务。

服务消费者(Consumer): 调用远程服务的服务消费方，服务消费者在启动时，向注册中心订阅自己所需的服务，服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。

注册中心(Registry): 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者

监控中心(Monitor): 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心
```



> 调用关系

```
1.服务容器负责启动，加载，运行服务提供者。
2.服务提供者在启动时，向注册中心注册自己提供的服务。
3.服务消费者在启动时，向注册中心订阅自己所需的服务。
4.注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
5.服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
6.服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。
```



> zookeeper宕机与dubbo直连

```
zookeeper注册中心宕机，还可以消费dubbo暴露的服务。

1.监控中心宕掉不影响使用，只是丢失部分采样数据
2.数据库宕掉后，注册中心仍能通过缓存提供服务列表查询，但不能注册新服务
3.注册中心对等集群，任意一台宕掉后，将自动切换到另一台
4.注册中心全部宕掉后，服务提供者和服务消费者仍能通过本地缓存通讯
5.服务提供者无状态，任意一台宕掉后，不影响使用
6.服务提供者全部宕掉后，服务消费者应用将无法使用，并无限次重连等待服务提供者恢复


没有注册中心的dubbo直连

@Reference(url="127.0.0.1:20882")
UserSerice userSerice;
```



> 集群下dubbo负载均衡配置

```
Random LoadBalance
随机，按权重设置随机概率。
在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。

RoundRobin LoadBalance
轮循，按公约后的权重设置轮循比率。
存在慢的提供者累积请求的问题，比如：第二台机器很慢，但没挂，当请求调到第二台时就卡在那，久而久之，所有请求都卡在调到第二台上。

LeastActive LoadBalance
最少活跃调用数，相同活跃数的随机，活跃数指调用前后计数差。
使慢的提供者收到更少请求，因为越慢的提供者的调用前后计数差会越大。

ConsistentHash LoadBalance
一致性 Hash，相同参数的请求总是发到同一提供者。
当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动。算法参见：http://en.wikipedia.org/wiki/Consistent_hashing
缺省只对第一个参数 Hash，如果要修改，请配置 <dubbo:parameter key="hash.arguments" value="0,1" />
缺省用 160 份虚拟节点，如果要修改，请配置 <dubbo:parameter key="hash.nodes" value="320" />
```



### WebSocket 

参考：[WebSocket](http://www.ruanyifeng.com/blog/2017/05/websocket.html) [WebSocket](https://www.runoob.com/html/html5-websocket.html)

```
HTTP 协议有一个缺陷：通信只能由客户端发起。

WebSocket它的最大特点就是，服务器可以主动向客户端推送信息，客户端也可以主动向服务器发送信息，是真正的双向平等对话，属于服务器推送技术的一种。
```





### Mycat分库分表

> 为什么使用Mycat  ->  流程图类似Nginx

```
1.Java与数据库紧耦合
2.高访问量高并发对数据库压力
3.读写请求数据不一致


1.读写分离
2.数据分片(数据库分布式)
	垂直拆分:分库
	水平拆分:分表
3.多数据源整合
```



> 原理

![](./image\mycat原理.png)

```
Mycat 的原理中最重要的一个动词是“拦截”，它拦截了用户发送过来的 SQL 语句
首先对SQL语句做了一些特定的分析：如分片分析、路由分析、读写分离分析、缓存分析等
然后将此 SQL发往后端的真实数据库，并将返回的结果做适当的处理，最终再返回给用户。

这种方式把数据库的分布式从代码中解耦出来，程序员察觉不出来后台使用Mycat还是MySQL
```


