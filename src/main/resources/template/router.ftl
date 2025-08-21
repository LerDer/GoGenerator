package router

import (
	"github.com/gin-gonic/gin"
	<#if moduleName?? >
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
//new(${tableNameHump}.${tableNameHump1}Router).Init${tableNameHump1}Router(r)
func (s *${tableNameHump1}Router) Init${tableNameHump1}Router(e *gin.Engine) {
	${tableNameHump}Router := e.Group("${tableNameHump}")
	${tableNameHump}Router.POST("save", Save${tableNameHump1})        // 保存
	${tableNameHump}Router.POST("delete", Delete${tableNameHump1})    // 删除
	${tableNameHump}Router.POST("update", Update${tableNameHump1})    // 更新
	${tableNameHump}Router.GET("getOne", Get${tableNameHump1})        // 查询一个
	${tableNameHump}Router.POST("getList", Query${tableNameHump1}List) // 查询多个
}

