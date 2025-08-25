package service

import (
	"errors"
	"strconv"

	. "${projectName}/${moduleName}/entity"
	"${projectName}/module_config/mysql"
	"github.com/gin-gonic/gin"
)

// ${tableNameHump1}Service ${tableComent!""} 数据库操作文件
// author ${authorName!""}
// date ${nowDate!""}
// ${genMark!""}
type ${tableNameHump1}Service struct {
}

func (s *${tableNameHump1}Service) Save${tableNameHump1}(${tableFirstWords} *${tableNameHump1}) (id int, err error) {
	<#if tx >
	db := mysql.Db.Begin()
	<#else>
	db := mysql.Db
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

func (s *${tableNameHump1}Service) Delete${tableNameHump1}(id int) error {
	<#if tx >
		db := mysql.Db.Begin()
	<#else>
		db := mysql.Db
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

func (s *${tableNameHump1}Service) Update${tableNameHump1}(${tableFirstWords} *${tableNameHump1}) (*${tableNameHump1}, error) {
	<#if tx >
		db := mysql.Db.Begin()
	<#else>
		db := mysql.Db
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

func (s *${tableNameHump1}Service) Get${tableNameHump1}(id int) (*${tableNameHump1}, error) {
	<#if tx >
		db := mysql.Db.Begin()
	<#else>
		db := mysql.Db
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

func (s *${tableNameHump1}Service) Query${tableNameHump1}List(c *gin.Context) (${tableFirstWords}s []${tableNameHump1}, err error) {
	<#if tx >
		db := mysql.Db.Begin()
	<#else>
		db := mysql.Db
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
