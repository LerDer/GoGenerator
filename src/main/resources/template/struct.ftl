package ${tableNameHump}

import (
	"${projectName}/utils"
)

// ${tableNameHump1} ${tableComent!""}
// author ${authorName!""}
// date ${nowDate!""}
// ${genMark!""}
type ${tableNameHump1} struct {

<#if columnVOS?? >
<#list columnVOS as value>
	${value.getColumnNameHump1()} ${value.getDType1()} `gorm:"<#if value.getIsPrimary()>primary_key;</#if>type:${value.getColumnType()};${value.getIsNullAble()}" json:"${value.getColumnNameHump()}"`  <#if value.getColumnComent()?? >// ${value.getColumnComent()!""}</#if>
</#list>
</#if>
}

// TableName 解决gorm表明映射
func (${tableNameHump1}) TableName() string {
	return "${tableName}"
}
