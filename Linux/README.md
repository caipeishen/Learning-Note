## Linux



### CentOS 7安装

参考：[Centos7安装](https://www.cnblogs.com/set-config/p/9040407.html)  [网络配置以及安装图形化界面](https://www.cnblogs.com/zqyw/p/11202560.html)  [没有网络](https://www.cnblogs.com/Vincent-yuan/p/10802023.html)  [网络配置](https://blog.csdn.net/lyf_ldh/article/details/78695357) [虚拟机ping主机vmnet8](https://blog.csdn.net/niuwei22007/article/details/50486872/)



### NAT和桥接模式

参考：[NAT和桥接模式](https://www.cnblogs.com/huhuxixi/p/11527837.html )



### Linux内存解释

参考：[Linux内存解释](https://blog.csdn.net/lengyue1084/article/details/51488188)



### 配置防火墙端口

```sh
#CentOS 6
vi /etc/sysconfig/iptables         //防火墙配置

-A INPUT -m state --state NEW -m tcp -p tcp --dport 22 -j ACCEPT //允许22端口通过
-A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT //允许3306端口通过

service iptables restart        //重启防火墙

#windows检查端口命令
telnet IP PORT


#CentOS 7

# 查询端口是否开放
firewall-cmd --query-port=8080/tcp
# 开放80端口
firewall-cmd --permanent --add-port=80/tcp
# 移除端口
firewall-cmd --permanent --remove-port=8080/tcp

#查看firewalld服务状态
systemctl status firewalld
#关闭firewalld
systemctl stop firewalld
#禁止firewall开机启动
systemctl disable firewalld 
#设置firewall开机启动
systemctl enable firewalld

# 开启
service firewalld start
# 关闭
service firewalld stop
# 重启
service firewalld restart

#创建软链接
ln -s 源文件 目标文件
```



### Linux常用命令

```sh
whoami #那个用户登录
date cla #日期

chkconfig mysql on #mysql自启动
chkconfig --list | grep mysql #查看mysql自启动

rpm -ivh xxx.rpm #安装rpm安装包

crontab -e|-l|-r #定时任务

mkdir -p /ferris/2020-12-18 #创建多级目录

chmod 777 file #文件授权 r=4,w=2,x=1 进入文件需要x权限
chown newowner file #改变文件所有者
chgrp newgroup file #改变文件所在组
usermod -g 组名 用户名 #更改用户所在组名

#使用gzip对文件进行压缩后，不会保留原来的文件
gzip 文件 #压缩
gunzip 文件 #解压

tar -zcvf 123.tar file1 file2 #压缩
tar -zxvf 文件名 #解压

ps -aux | grep file #查看进程信息
ps -ef | grep tomcat #同时查看父进程
kill -9 #关闭进程号

#正在进程信息(包括CPU、内存使用、多少人登录等信息)
top

vi 文件名	#编辑(dd删除文本当前行)
whereis 文件名 #查找文件安装

#系统中有哪些服务
ll /etc/init.d

#查看文件 空格翻页 enter翻一行
cat -n 文件 | less 

#查看磁盘占用情况
df -h

#查看内存使用
free -m

#查看磁盘占用情况
du -ach --max-depth=1 /opt

#查看目录下有多少文件
ls -l | grep "^-" | wc -l

vim 文件名 #编辑文件
	dd #删除当前行
	yy #赋值当前行 p粘贴复制的行
	/字符 #高亮显示字符，按n则查看下一个，:noh 取消高亮
	:set nu #查看行号

#动态查询
tail -99f text.txt

#查看所有的网络服务
netstat -anp | more
#window查看端口
netstat -ano | findstr 9000
#window查看进程
tasklist | findstr 19796

#网络服务tcp，udp的端口和进程等相关情况
netstat -tunplp
netstat -tunplp | grep 端口号

#查看某个端口
lsof -i :8080 
#列出某个程序所打开的文件信息
lsof -c java 

#如果让程序始终在后台执行，即使关闭当前的终端也执行（之前的&做不到），这时候需要nohup。该命令可以在你退出帐户/关闭终端之后继续运行相应的进程。关闭中断后，在另一个终端jobs已经无法看到后台跑得程序了，此时利用ps（进程查看命令）
nohup ./startup.sh &
nohup java -jar weChat.jar &
```





### Linux配置JDK

/etc/profile 文件

```sh
#编辑etc/profile文件
---------------------------------
#JAVA的JDK配置
export JAVA_HOME=/usr/local/java/jdk1.8.0_251
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
#将JDK配置到环境变量中，如果还有其他变量 在其后面使用:隔开
export PATH=$PATH:$JAVA_HOME/bin
---------------------------------

#刷新环境变量文件： 刷新环境变量命令
source /etc/profile
```



### Linux安装Maven

参考：[Linux安装Maven](https://blog.csdn.net/qq_38270106/article/details/97764483)



### Linux安装Git

参考：[Linux安装Git](https://blog.csdn.net/xiaoye319/article/details/89642875)



### Linux安装MySQL

参考：[Linux安装MySQL](https://www.jianshu.com/p/4587e9429702)  [MySQL无法远程连接](https://www.cnblogs.com/zzqit/p/10095597.html)  [MySQL忘记密码](https://www.cnblogs.com/black-fact/p/11613361.html)   [Windows安装MySQL8](https://www.cnblogs.com/tangyb/p/8971658.html)

```
2.2修改MySQL8.0的密码的编码方式
由于MySQL8.0修改了他的编码方式,而Navicat没有这个编码方式的密码,因此,就只能将密码的编码方式修改为原来的编码方式了,不然就登录不了。
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'Abcd@1234';
FLUSH PRIVILEGES;
```



### Linux安装Redis

参考：[Linux安装Redis](https://www.cnblogs.com/limit1/p/9045183.html)  

> 报错gcc无效命令

```
yum install -y gcc-c++
```

> 安装Redis

```sh
1.获取redis资源

　　wget http://download.redis.io/releases/redis-4.0.8.tar.gz

2.解压

　　tar xzvf redis-4.0.8.tar.gz

3.安装

　　cd redis-4.0.8

　　make

　　cd src

　　make install PREFIX=/usr/local/redis

4.移动配置文件到安装目录下

　　cd ../

　　mkdir /usr/local/redis/etc

　　mv redis.conf /usr/local/redis/etc

 5.配置redis为后台启动

　　vi /usr/local/redis/etc/redis.conf //将daemonize no 改成daemonize yes

6.将redis加入到开机启动

　　vi /etc/rc.local 
　　//在里面添加内容：/usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf 
　　(意思就是开机调用这段开启redis的命令)

7.开启redis

　　/usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf 

 

常用命令　　

　　redis-server /usr/local/redis/etc/redis.conf //启动redis

　　pkill redis  //停止redis

　　卸载redis：

　　　　rm -rf /usr/local/redis //删除安装目录

　　　　rm -rf /usr/bin/redis-* //删除所有redis相关命令脚本

　　　　rm -rf /root/download/redis-4.0.4 //删除redis解压文件夹
```



> io.lettuce.core.RedisConnectionException 

```
1).注释掉 #bin 127.0.0.1 (原因：bind 127.0.0.1生效，只能本机访问redis)
2).将 protected-mode yes 改为：protected-mode no （原因：把yes改成no，允许外网访问）
```



### MySQL数据总显示 '' ? ''

1. 编辑my.cnf文件 默认路径都在 `vi /etc/my.cnf`

2. 添加配置

   ```shell
   [mysqld]
   #中文无法插入数据
   character-set-server=utf8
   #改配置可以忽略大小写
   lower_case_table_names=1  
   
   [client]
   #中文无法插入数据
   default-character-set=utf8
   ```

   ```sh
   #设置编码格式
   mysql> set character_set_database=utf8;
    
   mysql> set character_set_server=utf8;
   
   # vi /etc/my.cnf;
   [mysqld]
   character_set_server=utf8
    
   [mysql]
   default-character-set=utf8
    
   [client]
   default-character-set=utf8
   ```

   

3. 运行代码，重启MySQL服务

   ```sh
   service mysqld/mysql restart
   ```



### MySQL主从复制

> 主机配置(host79)

```sh
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

```sh
修改配置文件：vim /etc/my.cnf
#从服务器唯一ID
server-id=2
#启用中继日志
relay-log=mysql-relay
```



> 主机、从机重启 MySQL 服务

> 主机从机都关闭防火墙

> 在主机上建立帐户并授权 slave

```sh
#在主机MySQL里执行授权命令
GRANT REPLICATION SLAVE ON *.* TO 'slave'@'%' IDENTIFIED BY '123123';
#查询master的状态
show master status;
#记录下File和Position的值
#执行完此步骤后不要再操作主服务器MySQL，防止主服务器状态值变化
```



> 在从机上配置需要复制的主机

```sh
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





### Shell备份数据库

> 编写备份脚本

```sh
#!/bin/bash

#备份路径
backDirectory="/data/back-up-db/"

#备份时间
backTime=$(date "+%Y_%m_%d_%H%M%S")


echo "==========开始备份数据库=========="
echo "备份路径：${backDirectory}"
echo "备份时间：${backTime}"

#数据库IP
host=114.116.190.45

#数据库名
database=cloud_wall

#数据库账号
user=root

#数据库密码
password=a9530.A.

echo "数据库IP：${host}"
echo "数据库名：${database}"
echo "数据库账号：${user}"
echo "数据库密码：${password}"

#创建文件夹
if [ ! -d "${backDirectory}/${backTime}" ]
then
	mkdir -p ${backDirectory}/${database}
fi


#备份数据库
mysqldump -u${user} -p${password} --host=${host} ${database} | gzip > ${backDirectory}/${database}/${database}_${backTime}.sql.gz

#进入备份目录
cd ${backDirectory}/${database}

#打包成tar
tar -zcvf ${database}_${backTime}.sql.tar.gz ${database}_${backTime}.sql.gz

#删除原有的gz包
rm -rf ${database}_${backTime}.sql.gz

#结束备份
echo "=================================="
```



> 编写定时任务

```sh
#启动编写定时任务
crontab -e

#编写定时任务
0 2 * * * /usr/sbin/my-shell/back-up-db.sh
```

