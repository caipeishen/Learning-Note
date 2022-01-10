







## ES

```json

PUT /ana
{
  "mappings": {
    "properties": {
      "id": {
        "type": "keyword"
      },
      "user_id": {
        "type": "keyword"
      },
      "user_name": {
        "type": "text"
      },
      "ana_type_id": {
        "type": "keyword"
      },
      "ana_type_name": {
        "type": "text"
      },
      "ana_title": {
        "type": "text"
      },
      "ana_content": {
        "type": "text"
      },
      "comment_num": {
        "type": "long"
      },
      "prize_num": {
        "type": "long"
      },
      "create_date": {
        "type": "date",
        "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
      },
      "update_date": {
        "type": "date",
        "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
      }
    }
  }
}


POST /ana/_search
{
  "query": {
    "match_all": {}
  }
}
```





### Load canal adapter: es7 failed，cannot be cast to com.alibaba.druid.pool.DruidDataSource



> 需要修改源码解决,打开刚刚下载的源码，修改canal-canal-1.1.5\client-adapter\es7x\pom.xml 

```xml
<dependency>
    <groupId>com.alibaba.otter</groupId>
    <artifactId>client-adapter.common</artifactId>
    <version>${project.version}</version>
    <!--增加下面这段-->
    <scope>provided</scope>
</dependency>
```



> 然后修改canal-canal-1.1.5\pom.xml，主要是打包跳过测试，避免报错 

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>${maven-surefire.version}</version>
    <configuration>
        <useSystemClassLoader>true</useSystemClassLoader>
        <forkMode>once</forkMode>
        <argLine>${argline} ${jacocoArgLine}</argLine>
        <systemProperties>
            <!-- common shared -->
        </systemProperties>
        <skipTests>true</skipTests>    <!--默认关掉单元测试 -->
    </configuration>
</plugin>
```



> 之后maven插件执行下面 

```
canal module for otter 1.1.5 下重新打包
```



>执行完后用canal-canal-1.1.5\client-adapter\es7x\target\client-adapter.es7x-1.1.5-jar-with-dependencies.jar下面包替换conf/plugins/client-adapter.es7x-1.1.5-jar-with-dependencies.jar



> 重启



> 全量数据迁移

```sh
curl http://127.0.0.1:8081/etl/exampleKey/es7/mytest_user.yml -X POST
```

