package com.wd.vo;

import java.util.List;
import lombok.Data;

@Data
public class TableVO {

    /**
     * 原表名
     */
    private String tableName;

    /**
     * 驼峰命名
     */
    private String tableNameHump;

    /**
     * 大写开头驼峰命名
     */
    private String tableNameHump1;

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
}
