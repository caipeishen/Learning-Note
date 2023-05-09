# 一、安装准备

## 1、前提条件

- Docker可以运行在Windows、Mac、CentOS、Ubuntu等操作系统上

- Docker支持以下的CentOS版本：

- - CentOS 7 (64-bit)
  - CentOS 6.5 (64-bit) 或更高的版本 

- 目前，CentOS 仅发行版本中的内核支持 Docker

- - Docker 运行在 CentOS 7 上，要求系统为64位、系统内核版本为 3.10 以上。
  - Docker 运行在 CentOS-6.5 或更高的版本的 CentOS 上，要求系统为64位、系统内核版本为 2.6.32-431 或者更高版本。

## 2、查看系统内核

uname命令用于打印当前系统相关信息（内核版本号、硬件架构、主机名称和操作系统类型等）。

```shell
uname -r
```

## 3、查看已安装的CentOS版本信息

```shell
cat /etc/redhat-release
```



# 二、CentOS7安装docker

官网：http://www.docker.com

安装手册：https://docs.docker.com/install/linux/docker-ce/centos（CE-社区版）

## 1、安装需要的软件包

yy -utils提供了yy-config-manager相关功能，device-mapper-persistent-data和lvm2是设备映射器驱动程序所需要的。

```shell
yum install -y yum-utils \
               device-mapper-persistent-data \
               lvm2
```

## 2、设置docker下载镜像

推荐阿里云下载地址

```shell
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

## 3、更新yum软件包索引

我们在更新或配置yum源之后，通常都会使用yum makecache 生成缓存，这个命令是将软件包信息提前在本地缓存一份，用来提高搜索安装软件的速度

```shell
yum makecache fast
```

## 4、安装docker ce

```shell
yum install -y docker-ce
```

## 5、启动docker

```shell
systemctl start docker
```

## 6、版本验证

```shell
docker version
```



## 7、设置开机启动

```shell
#查看服务是否自动启动（是：enabled | 否：disabled）
systemctl list-unit-files|grep docker.service 

#设置开机启动：如不是enabled可以运行如下命令设置自启动
systemctl enable docker
#重新加载服务配置
systemctl daemon-reload 

#如果希望不进行自启动，运行如下命令设置
systemctl disable docker
#重新加载服务配置
systemctl daemon-reload 
```



# 三、卸载

```
systemctl stop docker 
yum remove -y docker-ce
rm -rf /var/lib/docker
```

