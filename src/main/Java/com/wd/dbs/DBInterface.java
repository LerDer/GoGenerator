package com.wd.dbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author lww
 */
public interface DBInterface {

    Connection getConnection() throws SQLException;

    Map<String, String> getTypeMap();

    String getDefaultHost();

    String getDefaultPort();

    String getDefaultUserName();

    DBProperty getDbProperty();

    void setDbProperty(DBProperty property);

    String getAllTable();

    String getAllTableLike();

    String getColumns();

    String getTableEqual();
}
