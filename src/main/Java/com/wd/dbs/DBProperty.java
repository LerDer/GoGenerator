package com.wd.dbs;

import lombok.Data;

/**
 * 数据库连接属性对象
 *
 * @author lww
 */
@Data
public class DBProperty {

    private String host;
    private String port;
    private String dbName;
    private String username;
    private String password;

    public DBProperty(String host, String port, String dbName, String username, String password) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

}
