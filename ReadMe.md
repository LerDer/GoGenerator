#   GoGenerator代码生成器

golang代码生成器，go有很多代码生成器，不过都是命令行操作，不太直观。

所以开发了一个代码生成器插件，这个代码生成器采用插件的方式，用起来更加简单，已经发布到jetbrains插件市场。

插件地址：https://plugins.jetbrains.com/plugin/26613-gogenerator

## 使用方法

### 1、先创建一个空项目

<img src="/images/image-20250221121658132.png" alt="image-20250221121658132" style="zoom:50%;" />

### 2、配置数据库连接，目前只支持MySQL数据库

<img src="/images/image-20250221121825144.png" alt="image-20250221121825144" style="zoom:50%;" />
<br/>
<img src="/images/image-20250221121850663.png" alt="image-20250221121850663" style="zoom:50%;" />

点击插件，显示如下窗口

<img src="/images/image-20250221122010216.png" alt="image-20250221122010216" style="zoom:50%;" />

配置数据库，测试连接，成功后会展示数据库表

<img src="/images/image-20250221122159751.png" alt="image-20250221122159751" style="zoom:50%;" />
<br/>
<img src="/images/image-20250221122238810.png" alt="image-20250221122238810" style="zoom:50%;" />
<br/>
<img src="/images/image-20250221122302934.png" alt="image-20250221122302934" style="zoom:50%;" />

选择项目路径，填写作者，项目名称等信息

<img src="/images/image-20250221122540861.png" alt="image-20250221122540861" style="zoom:50%;" />

### 3、选择需要生成种类，第一次需要选中初始化文件，点击OK，生成成功

<img src="/images/image-20250221122709388.png" alt="image-20250221122709388" style="zoom:50%;" />
<br/>
<img src="/images/image-20250221122801898.png" alt="image-20250221122801898" style="zoom:50%;" />

### 4、检查配置

idea配置（idea要开发go需要下载Go插件）

<img src="/images/image-20250221123118386.png" alt="image-20250221123118386" style="zoom:50%;" />
<br/>
<img src="/images/image-20250221122916003.png" alt="image-20250221122916003" style="zoom:50%;" />

GoLand配置

<img src="/images/image-20250221123009209.png" alt="image-20250221123009209" style="zoom:50%;" />

在main.go中也有说明

<img src="/images/image-20250221123228467.png" alt="image-20250221123228467" style="zoom:50%;" />

### 5、执行 go mod tidy 或 go mod download 下载依赖

<img src="/images/image-20250221123306395.png" alt="image-20250221123306395" style="zoom:50%;" />

### 6、执行 swag init 命令自动生成swagger文档

要先安装swag命令
```
go install github.com/swaggo/swag/cmd/swag@latest
```

<img src="/images/image-20250221123511636.png" alt="image-20250221123511636" style="zoom:50%;" />

如果表没有Comment会导致swagger生成报错

<img src="/images/image-20250221123936002.png" alt="image-20250221123936002" style="zoom:50%;" />

### 7、启动，访问 http://localhost:8080/swagger/index.html

<img src="/images/image-20250221124018535.png" alt="image-20250221124018535" style="zoom:50%;" />
<br/>
<img src="/images/image-20250221124105770.png" alt="image-20250221124105770" style="zoom:50%;" />

### 8、测试一下接口

<img src="/images/image-20250221124138638.png" alt="image-20250221124138638" style="zoom:50%;" />

### 9、日志打印（速度确实很快）

<img src="/images/image-20250221124251937.png" alt="image-20250221124251937" style="zoom:50%;" />

日志文件

<img src="/images/image-20250221124336601.png" alt="image-20250221124336601" style="zoom:50%;" />

### 10、第二次生成

很多配置已经保存了，不需要再次输入

<img src="/images/image-20250221124658061.png" alt="image-20250221124658061" style="zoom:50%;" />

先点击测试连接按钮，获取表，不需要选中初始化文件，

不点击测试连接按钮也可以，可以直接输入表的名称

<img src="/images/image-20250221124831162.png" alt="image-20250221124831162" style="zoom:50%;" />

### 11、打开 user_like_router.go ，把路由信息复制到main.go中

<img src="/images/image-20250221124942513.png" alt="image-20250221124942513" style="zoom:50%;" />
<br/>
<img src="/images/image-20250221125059360.png" alt="image-20250221125059360" style="zoom:50%;" />

### 12、再次执行swag init 命令自动生成swagger文档

<img src="/images/image-20250221125151801.png" alt="image-20250221125151801" style="zoom:50%;" />

### 13、启动，访问 http://localhost:8080/swagger/index.html

<img src="/images/image-20250221125243830.png" alt="image-20250221125243830" style="zoom:50%;" />

新的接口已经展示出来了。测试一下

<img src="/images/image-20250221125355594.png" alt="image-20250221125355594" style="zoom:50%;" />



### 14、其他

支持跨域，https，在main.go中配置

<img src="/images/image-20250221125600111.png" alt="image-20250221125600111" style="zoom:50%;" />

还有发送邮件和定时任务，redis

<img src="/images/image-20250221125736240.png" alt="image-20250221125736240" style="zoom:50%;" />

试一下定时任务，每分钟打印一个hello world

<img src="/images/image-20250221125842258.png" alt="image-20250221125842258" style="zoom:50%;" />
<br/>
<img src="/images/image-20250221130021692.png" alt="image-20250221130021692" style="zoom:50%;" />

### 15、最后

struct

<img src="/images/image-20250221130109276.png" alt="image-20250221130109276" style="zoom:50%;" />

Api

<img src="/images/image-20250221130145508.png" alt="image-20250221130145508" style="zoom:50%;" />

router

<img src="/images/image-20250221130207031.png" alt="image-20250221130207031" style="zoom:50%;" />

service (支持事务)

<img src="/images/image-20250221130244469.png" alt="image-20250221130244469" style="zoom:50%;" />

Api里面只提供了最基本的业务操作，具体业务还需要根据需求修改。

项目结构是一个表对应一个文件夹，可以根据自己的需求改变目录。

<img src="/images/image-20250221130501426.png" alt="image-20250221130501426" style="zoom:50%;" />

对于想体验一下go，却又不知如何下手的朋友们，快来试试吧。