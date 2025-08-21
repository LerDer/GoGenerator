package main

import (
	"github.com/gin-gonic/gin"
	"${projectName}/${tableNameHump}"
	"${projectName}/configs"
	//"${projectName}/corn"
	"${projectName}/middleware"

	<#if swagger >
	swaggerfiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
	_ "${projectName}/docs" // swag init 命令自动生成该包
	</#if>
)

// author ${authorName!""}
// date ${nowDate!""}
// ${genMark!""}
<#if swagger >
// @title ${projectName} swagger
// @version 1.0
// @description ${projectName} swagger 项目
</#if>
func main() {
	configs.BeforeRun()
	r := gin.Default()

	//如需跨域可以打开
	//r.Use(middleware.Cors())
	//打开就能玩https了
	//r.Use(middleware.LoadTls())
	r.Use(middleware.Logger())

<#if swagger >
	//第一种方式 Swagger
	r.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerfiles.Handler))

	//第二种方式 Swagger
	//ginSwagger.WrapHandler(swaggerFiles.Handler,
	//ginSwagger.URL("http://localhost:8080/swagger/doc.json"),
	//ginSwagger.DefaultModelsExpandDepth(-1))
</#if>
	//定时任务
	//corn.CornDemo()
	//发送邮件
	//if err1 := configs.Send("1111111@qq.com", "测试", "第一封简单邮件"); err1 != nil {
	//	configs.Log.Error(err1)
	//}

    //绑定路由
	new(${tableNameHump}.${tableNameHump1}Router).Init${tableNameHump1}Router(r)

	configs.InitRedis()
	//默认8080 listen and serve on 0.0.0.0:8080
	err := r.Run(":" + configs.CONFIG.Port)
	if err != nil {
		configs.Log.Error(err.Error())
	}
	//检查配置是否启用
	//Go ---> Go Modules ---> Enable Go Modules integration
	//执行 go mod tidy 或 go mod download 下载依赖
	//执行 swag init 命令自动生成swagger文档
	//启动 访问 http://localhost:8080/swagger/index.html
}
