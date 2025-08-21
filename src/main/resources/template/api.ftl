package ${tableNameHump}

import (
	"github.com/gin-gonic/gin"
	"${projectName}/response"
	<#if moduleName?? >
	. "${projectName}/${moduleName}/service"
	. "${projectName}/${moduleName}/struct"
	</#if>
	"strconv"
)

// ${tableComent!""} api接口
// author ${authorName!""}
// date ${nowDate!""}
// ${genMark!""}
var service${tableNameHump1} = new(Service${tableNameHump1})

<#if swagger >
// Save${tableNameHump1} @Summary 保存 ${tableNameHump}
// @Tags ${tableNameHump}
// @Param ${tableNameHump} body ${tableNameHump}.${tableNameHump1} true "${tableComent!""}"
// @Success 200 object response.Response 成功后返回值
// @Router /${tableNameHump}/save [POST]
</#if>
//json参数
func Save${tableNameHump1}(c *gin.Context) {
	var ${tableNameHump}  ${tableNameHump1}
	err := c.ShouldBindJSON(&${tableNameHump})
	if err != nil {
		response.FailWithMessage("参数错误", c)
		return
	}
	if id, err := service${tableNameHump1}.Save${tableNameHump1}(&${tableNameHump}); err == nil {
		response.OkWithDetailed(id, "保存成功", c)
	} else {
		response.FailWithMessage("保存失败", c)
	}
}

<#if swagger >
// Delete${tableNameHump1} @Summary 删除 ${tableNameHump}
// @Tags ${tableNameHump}
// @Param id formData int true "id"
// @Success 200 object response.Response 成功后返回值
// @Router /${tableNameHump}/delete [POST]
</#if>
//formData参数
func Delete${tableNameHump1}(c *gin.Context) {
	id := c.PostForm("id")
	if id == "" {
		response.FailWithMessage("参数错误", c)
	return
	}
	intId, _ := strconv.Atoi(id)
	if err := service${tableNameHump1}.Delete${tableNameHump1}(intId); err != nil {
		response.FailWithMessage("删除失败", c)
	} else {
		response.OkWithDetailed("", "删除成功", c)
	}
}

<#if swagger >
// Update${tableNameHump1} @Summary 更新 ${tableNameHump}
// @Tags ${tableNameHump}
// @Param ${tableNameHump} body ${tableNameHump}.${tableNameHump1} true "${tableComent!""}"
// @Success 200 object response.Response 成功后返回值
// @Router /${tableNameHump}/update [POST]
</#if>
//json参数
func Update${tableNameHump1}(c *gin.Context) {
	var ${tableNameHump} ${tableNameHump1}
	err := c.ShouldBindJSON(&${tableNameHump})
	if err != nil {
		response.FailWithMessage("参数错误", c)
		return
	}
	if u, err := service${tableNameHump1}.Update${tableNameHump1}(&${tableNameHump}); err != nil {
		response.FailWithDetailed(err.Error(), "更新失败", c)
	} else {
		response.OkWithDetailed(u, "更新成功", c)
	}
}

<#if swagger >
// Get${tableNameHump1} @Summary 查询 ${tableNameHump}
// @Tags ${tableNameHump}
// @Param id query int true "id"
// @Success 200 object response.Response 成功后返回值
// @Router /${tableNameHump}/getOne [GET]
</#if>
//url参数
func Get${tableNameHump1}(c *gin.Context) {
	id := c.Query("id")
	if id == "" {
		response.FailWithMessage("参数错误", c)
	return
	}
	intId, _ := strconv.Atoi(id)
	if ${tableNameHump}, err := service${tableNameHump1}.Get${tableNameHump1}(intId); err != nil {
		response.FailWithMessage("获取失败", c)
	} else {
		response.OkWithDetailed(${tableNameHump}, "获取成功", c)
	}
}

<#if swagger >
	// Query${tableNameHump1}List @Summary 高级查询 ${tableNameHump}
	// @Tags ${tableNameHump}
	// @Param page formData int true "page"
	// @Param pagesize formData int true "pagesize"
	// @Param orderBy formData string false "orderBy"
	// @Success 200 object response.Response 成功后返回值
	// @Router /${tableNameHump}/getList [POST]
</#if>
//formData参数
func Query${tableNameHump1}List(c *gin.Context) {
	page, _ := strconv.Atoi(c.PostForm("page"))
	pagesize, _ := strconv.Atoi(c.PostForm("pagesize"))
	if ${tableNameHump}s, err := service${tableNameHump1}.Query${tableNameHump1}List(c); err != nil {
		response.FailWithMessage("获取失败", c)
	} else {
		response.OkWithDetailed(response.PageResult{
		List:     ${tableNameHump}s,
		Total:    len(${tableNameHump}s),
		Page:     page,
		PageSize: pagesize,
		}, "获取成功", c)
	}
}