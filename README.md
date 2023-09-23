## 安装
修改配置文件
```shell
cp src/main/resources/application.demo.yaml src/main/resources/application.yaml
vim src/main/resources/application.yaml
```

```shell
docker build -t wcf .
```

```shell
docker run -d \
  --name=wcf \
  -p 8080:8080 \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Asia/Shanghai \
  -v /etc/localtime:/etc/localtime \
  --restart unless-stopped \
  wcf
```