package router

import (
	"${projectName}/middleware/jwt"
	"${projectName}/middleware/router"
	"${projectName}/module_config"
	"${projectName}/${moduleName}/api"
)

func init() {
	${tableFirstWords} := router.New(module_config.CONFIG.Env, "/${tableNameHump}", jwt.JWTAuthMiddleware())
	${tableFirstWords}.POST("", "system:${tableNameHump}:save", api.Save${tableNameHump1})          // 保存
	${tableFirstWords}.DELETE("/:${priKeyHump}", "system:${tableNameHump}:delete", api.Delete${tableNameHump1})    // 删除
	${tableFirstWords}.PUT("", "system:${tableNameHump}:update", api.Update${tableNameHump1})       // 更新
	${tableFirstWords}.GET("/:${priKeyHump}", "system:${tableNameHump}:get", api.Get${tableNameHump1})             // 查询一个
	${tableFirstWords}.GET("/list", "system:${tableNameHump}:list", api.Query${tableNameHump1}List) // 查询多个
}

