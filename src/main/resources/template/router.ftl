package ${tableNameHump}

import (
	"github.com/gin-gonic/gin"
)

/**
* ${tableComent!""} 路由组件
*
* @author ${authorName!""}
* @date ${nowDate!""}
* ${genMark!""}
*/

type Router${tableNameHump1} struct {
}
//todo 把下面这一行复制到main.go中
//new(${tableNameHump}.Router${tableNameHump1}).InitRouter${tableNameHump1}(r)
func (s *Router${tableNameHump1}) InitRouter${tableNameHump1}(e *gin.Engine) {
	${tableNameHump}Router := e.Group("${tableNameHump}")
	${tableNameHump}Router.POST("save", Save${tableNameHump1})        // 保存
	${tableNameHump}Router.POST("delete", Delete${tableNameHump1})    // 删除
	${tableNameHump}Router.POST("update", Update${tableNameHump1})    // 更新
	${tableNameHump}Router.GET("getOne", Get${tableNameHump1})        // 查询一个
	${tableNameHump}Router.POST("getList", Query${tableNameHump1}List) // 查询多个
}

