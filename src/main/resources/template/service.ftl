package ${tableNameHump}

import (
	"errors"
	"github.com/gin-gonic/gin"
	"github.com/jinzhu/gorm"
	"${projectName}/configs"
	"strconv"
)

/**
* ${tableComent!""} 数据库操作文件
*
* @author ${authorName!""}
* @date ${nowDate!""}
* ${genMark!""}
*/

type Service${tableNameHump1} struct {
}

var db *gorm.DB

func (s *Service${tableNameHump1}) Save${tableNameHump1}(${tableFirstWords} *${tableNameHump1}) (id int, err error) {
	<#if tx >
	db = configs.GetDB().Begin()
	<#else>
	db = configs.GetDB()
	</#if>
	if err := db.Create(&${tableFirstWords}).Error; err != nil {
		<#if tx >
		db.Rollback()
		</#if>
		return -1, err
	} else {
		<#if tx >
		db.Commit()
		</#if>
		return ${tableFirstWords}.${priKeyHump1}, nil
	}
}

func (s *Service${tableNameHump1}) Delete${tableNameHump1}(id int) error {
	<#if tx >
		db = configs.GetDB().Begin()
	<#else>
		db = configs.GetDB()
	</#if>
	if err := db.Where(&${tableNameHump1}{${priKeyHump1}: id}).Delete(${tableNameHump1}{}).Error; err != nil {
		<#if tx >
		db.Rollback()
		</#if>
		return err
	} else {
		<#if tx >
		db.Commit()
		</#if>
		return nil
	}
}

func (s *Service${tableNameHump1}) Update${tableNameHump1}(${tableFirstWords} *${tableNameHump1}) (*${tableNameHump1}, error) {
	<#if tx >
		db = configs.GetDB().Begin()
	<#else>
		db = configs.GetDB()
	</#if>
	id := ${tableFirstWords}.${priKeyHump1}
	if id == 0 {
		return nil, errors.New("id为空")
	}
	if err := db.Model(&${tableNameHump1}{}).Where(&${tableNameHump1}{${priKeyHump1}: id}).Updates(${tableFirstWords}).Error; err != nil {
		<#if tx >
		db.Rollback()
		</#if>
		return nil, err
	} else {
		<#if tx >
		db.Commit()
		</#if>
		return ${tableFirstWords}, nil
	}
}

func (s *Service${tableNameHump1}) Get${tableNameHump1}(id int) (*${tableNameHump1}, error) {
	<#if tx >
		db = configs.GetDB().Begin()
	<#else>
		db = configs.GetDB()
	</#if>
	var ${tableFirstWords} ${tableNameHump1}
	if err := db.Model(&${tableNameHump1}{}).Where(&${tableNameHump1}{${priKeyHump1}: id}).Find(&${tableFirstWords}).Error; err != nil {
		<#if tx >
		db.Rollback()
		</#if>
		return nil, err
	} else {
		<#if tx >
		db.Commit()
		</#if>
		return &${tableFirstWords}, nil
	}
}

func (s *Service${tableNameHump1}) Query${tableNameHump1}List(c *gin.Context) (${tableFirstWords}s []${tableNameHump1}, err error) {
	<#if tx >
		db = configs.GetDB().Begin()
	<#else>
		db = configs.GetDB()
	</#if>
	tx := db.Model(&${tableNameHump1}{}).Where("1=1")

<#if columnVOS?? >
    <#list columnVOS as value>
        <#if value.getDType1() == "string">
			if ${value.columnNameHump}, exist := c.GetPostForm("${value.columnNameHump}"); exist {
			tx = tx.Where("${value.columnName} like concat('%',?,'%')", ${value.columnNameHump})
			}
        <#else >
			if ${value.columnNameHump}, exist := c.GetPostForm("${value.columnNameHump}"); exist {
			tx = tx.Where("${value.columnName} = ?", ${value.columnNameHump})
			}
        </#if>
    </#list>
</#if>
	//if name, exist := c.GetPostForm("name"); exist {
	//tx = tx.Where("name like concat('%',?,'%')", name)
	//}
	if age, exist := c.GetPostForm("age"); exist {
		tx = tx.Where("age > ?", age)
	}
	//if sex, exist := c.GetPostForm("sex"); exist {
	//tx = tx.Where("sex = ?", sex)
	//}
	page, _ := strconv.Atoi(c.PostForm("page"))
	pagesize, _ := strconv.Atoi(c.PostForm("pagesize"))
	order := c.PostForm("orderBy")
	orderType := c.PostForm("orderType")
	tx = tx.Limit(pagesize).Offset((page-1)*pagesize)
	if order != "" {
		tx = tx.Order(order+" "+orderType, true)
	}
	//处理一对多,外键关联
	//tx.Preload("${tableFirstWords}s")
	//tx.Find(&${tableFirstWords}s).RecordNotFound()
	err = tx.Find(&${tableFirstWords}s).Error
	<#if tx >
	if err != nil {
		tx.Rollback()
	} else {
		tx.Commit()
	}
	</#if>
	return
}
