package com.wd.dbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lww
 */
@Getter
public enum DbType implements DBInterface {
	MySQL("com.mysql.jdbc.Driver", "jdbc:mysql://%s:%s/%s?serverTimezone=Asia/Shanghai", "mysql-connector-java-5.1.38.jar") {
		@Override
		public Map<String, String> getTypeMap() {
			Map<String, String> typeMap = new HashMap<>(16);
			typeMap.put("TINYINT", "int");
			typeMap.put("SMALLINT", "int");
			typeMap.put("MEDIUMINT", "int");
			typeMap.put("INT", "int");
			typeMap.put("BIGINT", "int");

			typeMap.put("BOOL", "bool");
			typeMap.put("BOOLEAN", "bool");

			typeMap.put("CHAR", "string");
			typeMap.put("VARCHAR", "string");
			typeMap.put("TINYTEXT", "string");
			typeMap.put("TEXT", "string");
			typeMap.put("MEDIUMTEXT", "string");
			typeMap.put("LONGTEXT", "string");

			typeMap.put("DATE", "utils.Date");
			typeMap.put("DATETIME", "utils.Time");
			typeMap.put("TIMESTAMP", "utils.Time");
			typeMap.put("TIME", "utils.Time");

			typeMap.put("NUMERIC", "float");
			typeMap.put("FLOAT", "float");
			typeMap.put("DOUBLE", "float");
			typeMap.put("DECIMAL", "float");

			typeMap.put("JSON", "string");
			typeMap.put("ENUM", "string");
			typeMap.put("POINT", "string");
			typeMap.put("GEOMETRY", "string");
			return typeMap;
		}

		@Override
		public String getDefaultPort() {
			return "3306";
		}

	},
	MySQL_8("com.mysql.cj.jdbc.Driver", "jdbc:mysql://%s:%s/%s", "mysql-connector-java-8.0.11.jar") {
		@Override
		public Map<String, String> getTypeMap() {
			Map<String, String> typeMap = new HashMap<>(16);
			typeMap.put("TINYINT", "int");
			typeMap.put("SMALLINT", "int");
			typeMap.put("MEDIUMINT", "int");
			typeMap.put("INT", "int");
			typeMap.put("BIGINT", "int");

			typeMap.put("BOOL", "bool");
			typeMap.put("BOOLEAN", "bool");

			typeMap.put("CHAR", "string");
			typeMap.put("VARCHAR", "string");
			typeMap.put("TINYTEXT", "string");
			typeMap.put("TEXT", "string");
			typeMap.put("MEDIUMTEXT", "string");
			typeMap.put("LONGTEXT", "string");

			typeMap.put("DATE", "utils.Date");
			typeMap.put("DATETIME", "utils.Time");
			typeMap.put("TIMESTAMP", "utils.Time");
			typeMap.put("TIME", "utils.Time");

			typeMap.put("NUMERIC", "float");
			typeMap.put("FLOAT", "float");
			typeMap.put("DOUBLE", "float");
			typeMap.put("DECIMAL", "float");

			typeMap.put("JSON", "string");
			typeMap.put("ENUM", "string");
			typeMap.put("POINT", "string");
			typeMap.put("GEOMETRY", "string");
			return typeMap;
		}

		@Override
		public String getDefaultPort() {
			return "3306";
		}
	},
	Oracle("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@%s:%s:%s", "ojdbc14.jar") {
		@Override
		public Map<String, String> getTypeMap() {
			Map<String, String> typeMap = new HashMap<>(16);

			return typeMap;
		}

		@Override
		public String getDefaultPort() {
			return "1521";
		}
	},
	PostgreSQL("org.postgresql.Driver", "jdbc:postgresql://%s:%s/%s", "postgresql-9.4.1209.jar") {
		@Override
		public Map<String, String> getTypeMap() {
			Map<String, String> typeMap = new HashMap<>(16);

			return typeMap;
		}

		@Override
		public String getDefaultPort() {
			return "5432";
		}
	},
	SqlServer("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://%s:%s;databaseName=%s", "sqljdbc4-4.0.jar") {
		@Override
		public Map<String, String> getTypeMap() {
			Map<String, String> typeMap = new HashMap<>(16);

			return typeMap;
		}

		@Override
		public String getDefaultPort() {
			return null;
		}
	},
	//Sqlite("org.sqlite.JDBC", "jdbc:sqlite:%s", "sqlite-jdbc-3.19.3.jar") {},
	MariaDB("org.mariadb.jdbc.Driver", "jdbc:mariadb://%s:%s/%s", "mariadb-java-client-2.3.0.jar") {
		@Override
		public Map<String, String> getTypeMap() {
			Map<String, String> typeMap = new HashMap<>(16);

			return typeMap;
		}

		@Override
		public String getDefaultPort() {
			return null;
		}
	},
	;

	private String driverClass;
	private String connectionUrlPattern;
	private String connectorJarFile;
	private DBProperty property;

	DbType(String driverClass, String connectionUrlPattern, String connectorJarFile) {
		this.driverClass = driverClass;
		this.connectionUrlPattern = connectionUrlPattern;
		this.connectorJarFile = connectorJarFile;
	}

	public static DbType getByName(String name, DBProperty property) {
		for (DbType dbtype : DbType.values()) {
			if (dbtype.name().equalsIgnoreCase(name)) {
				dbtype.setDbProperty(property);
				return dbtype;
			}
		}
		throw new IllegalArgumentException("不存在对应的枚举类");
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (StringUtils.isBlank(this.property.getHost())) {
			throw new IllegalArgumentException("DBProperty未设置");
		}
		return DriverManager.getConnection(String.format(this.getConnectionUrlPattern(), this.property.getHost(),
				this.property.getPort(), this.property.getDbName(), "UTF-8"), this.property.getUsername(), this.property.getPassword());
	}

	@Override
	public String getDefaultHost() {
		return "localhost";
	}

	@Override
	public String getDefaultUserName() {
		return "root";
	}

	@Override
	public DBProperty getDbProperty() {
		return this.property;
	}

	@Override
	public void setDbProperty(DBProperty property) {
		this.property = property;
	}

	/**
	 * 不同数据库,只需要在不同枚举中重写该方法即可
	 */
	@Override
	public String getAllTable() {
		return "SELECT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.`TABLES` WHERE TABLE_SCHEMA = ?";
	}

	@Override
	public String getAllTableLike() {
		return "SELECT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.`TABLES` WHERE TABLE_SCHEMA = ? AND TABLE_NAME LIKE CONCAT('%',?,'%')";
	}

	@Override
	public String getColumns() {
		return "SELECT * FROM information_schema.`COLUMNS` WHERE TABLE_SCHEMA = ? AND table_name = ? ORDER BY ORDINAL_POSITION";
	}

	@Override
	public String getTableEqual() {
		return "SELECT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.`TABLES` WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
	}
}