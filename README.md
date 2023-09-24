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

## 创建第一个管理员账号
仅当系统中还没有管理员账号的时候访问该地址可以创建一个管理员账号

post: /api/auth/addFirstAdmin

默认账号 wcf 默认密码 wcf6888888