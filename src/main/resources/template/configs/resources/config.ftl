port: 8080

#为 true 时打印 sql
debug: true

mysql:
 host: ${host}
 port: ${port}
 user: ${username}
 password: ${password}
 dbname: ${dbName}

logger:
 name: ${projectName}
 level: info
 director:
 log-in-console: true
 showline: true

redis:
 addr: 127.0.0.1:6379
 password: admin
 db: 0

#email:
# to:
# port: 465
# from: 1111111@qq.com
# host: smtp.qq.com
# is-ssl: true
# secret: *********
# nickname: Demo