package com.wd.vo;

import com.wd.util.CommonUtil;
import java.util.List;
import lombok.Data;

@Data
public class TableVO {

	/**
	 * 原表名
	 */
	private String tableName;

	/**
	 * 表名去前缀
	 */
	private String tableNameNoPrefix;

	private String tableFirstWords;

	private String tableComent;

	private String priKey;

	private String priKeyHump;

	private String priKeyHump1;

	private List<ColumnVO> columnVOS;

	private String authorName;

	private String nowDate;

	private String genMark;

	private String projectName;

	private Boolean swagger;

	private Boolean tx;

	private String host;
	private String port;
	private String username;
	private String password;
	private String dbName;
	private String moduleName;

	public String getTableNameComment() {
		return tableName + " - " + tableComent;
	}

	/**
	 * 驼峰命名
	 */
	public String getTableNameHump() {
		return CommonUtil.underlineToHump(tableNameNoPrefix);
	}

	/**
	 * 大写开头驼峰命名
	 */
	public String getTableNameHump1() {
		return CommonUtil.underlineToHump1(tableNameNoPrefix);
	}
}
