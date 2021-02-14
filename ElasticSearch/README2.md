## 谷粒商城

### 1.ElasticSearch安装

> 下载ealastic search和kibana 

```bash
docker pull elasticsearch:7.6.2
docker pull kibana:7.6.2
```



> 配置 

```bash
mkdir -p /mydata/elasticsearch/config
mkdir -p /mydata/elasticsearch/data
echo "http.host: 0.0.0.0" >/mydata/elasticsearch/config/elasticsearch.yml
chmod -R 777 /mydata/elasticsearch/
```



> 启动Elastic search 

```bash
docker run --name elasticsearch -p 9200:9200 -p 9300:9300 \
-e  "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms64m -Xmx512m" \
-v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /mydata/elasticsearch/data:/usr/share/elasticsearch/data \
-v  /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-d elasticsearch:7.6.2
```



> 设置开机启动elasticsearch 

```bash
docker update elasticsearch --restart=always
```



> 启动kibana： 

```bash
# 注意更换自己的IP
docker run --name kibana -e ELASTICSEARCH_HOSTS=http://192.168.181.130:9200 -p 5601:5601 -d kibana:7.6.2
```



> 设置开机启动kibana 

```
docker update kibana  --restart=always
```



> 测试 查看elasticsearch版本信息： http://192.168.137.14:9200/

```json
{
    "name": "0adeb7852e00",
    "cluster_name": "elasticsearch",
    "cluster_uuid": "9gglpP0HTfyOTRAaSe2rIg",
    "version": {
        "number": "7.6.2",
        "build_flavor": "default",
        "build_type": "docker",
        "build_hash": "ef48eb35cf30adf4db14086e8aabd07ef6fb113f",
        "build_date": "2020-03-26T06:34:37.794943Z",
        "build_snapshot": false,
        "lucene_version": "8.4.0",
        "minimum_wire_compatibility_version": "6.8.0",
        "minimum_index_compatibility_version": "6.0.0-beta1"
    },
    "tagline": "You Know, for Search"
}
```



### 2、初步检索

#### 1）_CAT

> （1）GET/*cat/nodes：查看所有节点*
>
> 如：http://192.168.137.14:9200/_cat/nodes	注：*表示集群中的主节点

```
127.0.0.1 61 91 11 0.08 0.49 0.87 dilm * 0adeb7852e00
```



> （2）GET/*cat/health：查看es健康状况*
>
> 如： http://192.168.137.14:9200/_cat/health	注：green表示健康值正常

```
1588332616 11:30:16 elasticsearch green 1 1 3 3 0 0 0 0 - 100.0%
```



>（3）GET/*cat/master：查看主节点*
>
>如： http://192.168.137.14:9200/_cat/master

```
vfpgxbusTC6-W3C2Np31EQ 127.0.0.1 127.0.0.1 0adeb7852e00
```



>（4）GET/_cat/indicies：查看所有索引 ，等价于mysql数据库的show databases;
>
>如： http://192.168.137.14:9200/_cat/indices

```json
green open .kibana_task_manager_1   KWLtjcKRRuaV9so_v15WYg 1 0 2 0 39.8kb 39.8kb
green open .apm-agent-configuration cuwCpJ5ER0OYsSgAJ7bVYA 1 0 0 0   283b   283b
green open .kibana_1                PqK_LdUYRpWMy4fK0tMSPw 1 0 7 0 31.2kb 31.2kb
```



#### 2）索引一个文档

>保存一个数据，保存在哪个索引的哪个类型下，指定用那个唯一标识
>PUT customer/external/1	在customer索引下的external类型下保存1号数据为

```json
PUT customer/external/1
{
 "name":"John Doe"
}
```

>PUT和POST都可以
>POST新增。如果不指定id，会自动生成id。指定id就会修改这个数据，并新增版本号；
>PUT可以新增、修改。PUT必须指定id；由于PUT需要指定id，我们一般用来做修改操作，不指定id会报错。



#### 3）查看文档

> GET /customer/external/1
>
> GET /customer/external/1&if_seq_no=1&if_primary_term=1
> 通过“if_seq_no=1&if_primary_term=1 ”，当序列号匹配的时候，才进行修改，否则不修改。

```json
{
    "_index": "customer",//在哪个索引
    "_type": "external",//在哪个类型
    "_id": "1",//记录id
    "_version": 3,//版本号
    "_seq_no": 6,//并发控制字段，每次更新都会+1，用来做乐观锁
    "_primary_term": 1,//同上，主分片重新分配，如重启，就会变化
    "found": true,
    "_source": {
        "name": "John Doe"
    }
}
```



#### 4）更新文档

> （1）POST更新文档，带有_update
>
> ```
> POST /customer/external/1/_update
> ```
>
> 如果再次执行更新，则不执行任何操作，序列号也不发生变化 
>
> POST更新方式，会对比原来的数据，和原来的相同，则不执行任何操作（version和_seq_no）都不变。



> （2）POST更新文档，不带_update
>
> ```
> POST /customer/external/1/
> ```
>
>  在更新过程中，重复执行更新操作，数据也能够更新成功，不会和原来的数据进行对比。 



#### 5）删除文档或索引

> 注：elasticsearch并没有提供删除类型的操作，只提供了删除索引和文档的操作。

```
DELETE customer/external/1
DELETE customer
```



#### 6）eleasticsearch的批量操作 bulk

> 语法格式： 
>
> 这里的批量操作，当发生某一条执行发生失败时，其他的数据仍然能够接着执行，彼此之间是独立的

```
{action:{metadata}}\n
{request body  }\n

{action:{metadata}}\n
{request body  }\n
```



> 实例1: 执行多条数据 

```json
POST customer/external/_bulk
{"index":{"_id":"1"}}
{"name":"John Doe"}
{"index":{"_id":"2"}}
{"name":"John Doe"}
```

>  执行结果 

```json
#! Deprecation: [types removal] Specifying types in bulk requests is deprecated.
{
  "took" : 491,
  "errors" : false,
  "items" : [
    {
      "index" : {
        "_index" : "customer",
        "_type" : "external",
        "_id" : "1",
        "_version" : 1,
        "result" : "created",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 0,
        "_primary_term" : 1,
        "status" : 201
      }
    },
    {
      "index" : {
        "_index" : "customer",
        "_type" : "external",
        "_id" : "2",
        "_version" : 1,
        "result" : "created",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 1,
        "_primary_term" : 1,
        "status" : 201
      }
    }
  ]
}
```



> 实例2：对于整个索引执行批量操作 

```json
POST /_bulk
{"delete":{"_index":"website","_type":"blog","_id":"123"}}
{"create":{"_index":"website","_type":"blog","_id":"123"}}
{"title":"my first blog post"}
{"index":{"_index":"website","_type":"blog"}}
{"title":"my second blog post"}
{"update":{"_index":"website","_type":"blog","_id":"123"}}
{"doc":{"title":"my updated blog post"}}
```

> 运行结果： 

```json
#! Deprecation: [types removal] Specifying types in bulk requests is deprecated.
{
  "took" : 608,
  "errors" : false,
  "items" : [
    {
      "delete" : {
        "_index" : "website",
        "_type" : "blog",
        "_id" : "123",
        "_version" : 1,
        "result" : "not_found",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 0,
        "_primary_term" : 1,
        "status" : 404
      }
    },
    {
      "create" : {
        "_index" : "website",
        "_type" : "blog",
        "_id" : "123",
        "_version" : 2,
        "result" : "created",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 1,
        "_primary_term" : 1,
        "status" : 201
      }
    },
    {
      "index" : {
        "_index" : "website",
        "_type" : "blog",
        "_id" : "MCOs0HEBHYK_MJXUyYIz",
        "_version" : 1,
        "result" : "created",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 2,
        "_primary_term" : 1,
        "status" : 201
      }
    },
    {
      "update" : {
        "_index" : "website",
        "_type" : "blog",
        "_id" : "123",
        "_version" : 3,
        "result" : "updated",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 3,
        "_primary_term" : 1,
        "status" : 200
      }
    }
  ]
}
```



#### 7）样本测试数据

> POST bank/account/_bulk
>
> https://github.com/elastic/elasticsearch/blob/master/docs/src/test/resources/accounts.json 测试数据





















