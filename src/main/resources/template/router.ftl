package router

import (
	"github.com/gin-gonic/gin"
	<#if moduleName?? >
	"${projectName}/middleware/authorize"
	. "${projectName}/${moduleName}/api"
	</#if>
)

// ${tableNameHump1}Router ${tableComent!""} 路由组件
// author ${authorName!""}
// date ${nowDate!""}
// ${genMark!""}
type ${tableNameHump1}Router struct {
}
//todo 把下面这一行复制到main.go中
//new(router.${tableNameHump1}Router).Init${tableNameHump1}Router(r)
func (s *${tableNameHump1}Router) Init${tableNameHump1}Router(e *gin.Engine) {
	//完全放行
	//public := e.Group("${tableNameHump}")
	// JWT 认证
	protected := e.Group("${tableNameHump}")
	protected.Use(jwt.JWTAuthMiddleware())

	// JWT 认证 + 权限校验
	private := e.Group("${tableNameHump}")
	private.Use(jwt.JWTAuthMiddleware())

	${tableNameHump}Router := e.Group("${tableNameHump}")
	${tableNameHump}Router.POST("", authorize.RequirePermission("${tableNameHump}:save"), Save${tableNameHump1})        // 保存
	${tableNameHump}Router.DELETE("", authorize.RequirePermission("${tableNameHump}:delete"), Delete${tableNameHump1})    // 删除
	${tableNameHump}Router.PUT("", authorize.RequirePermission("${tableNameHump}:update"), Update${tableNameHump1})    // 更新
	${tableNameHump}Router.GET("", authorize.RequirePermission("${tableNameHump}:get"), Get${tableNameHump1})        // 查询一个
	${tableNameHump}Router.GET("/list", authorize.RequirePermission("${tableNameHump}:list"), Query${tableNameHump1}List) // 查询多个
}

