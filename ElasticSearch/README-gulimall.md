## 谷粒商城

### ElasticSearch安装

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



> 进入es容器内部plugin目录或者外部映射的linux数据卷

```bash
cd /mydata/elasticsearch/plugins

wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.6.2/elasticsearch-analysis-ik-7.6.2.zip

unzip elasticsearch-analysis-ik-7.6.2.zip -d ik

rm -rf elasticsearch-analysis-ik-7.6.2.zip

# 进入es的docker的elasticsearch的bin 有ik 表示成功
elasticsearch-plugin list

# 重启elasticsearch
docker restart 7f66

# 默认分词器
POST _analyze 
{
	"analyzer": "standard",
	"text": "我是中国人" 
}

# ik分词器
POST _analyze 
{
	"analyzer": "ik_smart", # ik_smart、ik_max_word
	"text": "我是中国人" 
}
```

