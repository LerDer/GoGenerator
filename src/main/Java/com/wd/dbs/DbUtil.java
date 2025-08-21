package com.wd.dbs;

import com.wd.util.CommonUtil;
import com.wd.vo.ColumnVO;
import com.wd.vo.TableVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据库工具
 *
 * @author lww
 */
public class DbUtil {

	public static List<TableVO> getAllTables(DbType type, String dbName, String keyword) throws SQLException {
		Connection con = type.getConnection();
		List<TableVO> tables = new ArrayList<>();
		TableVO tableVO;
		try {
			PreparedStatement ps;
			if (StringUtils.isBlank(keyword)) {
				String sql = type.getAllTable();
				ps = con.prepareStatement(sql);
				ps.setString(1, dbName);
			} else {
				String sql = type.getAllTableLike();
				ps = con.prepareStatement(sql);
				ps.setString(1, dbName);
				ps.setString(2, keyword);
			}
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				tableVO = new TableVO();
				tableVO.setTableName(resultSet.getString("TABLE_NAME"));
				//tableVO.setTableNameHump(CommonUtil.underlineToHump(resultSet.getString("TABLE_NAME")));
				//tableVO.setTableNameHump1(CommonUtil.underlineToHump1(resultSet.getString("TABLE_NAME")));
				tableVO.setTableComent(resultSet.getString("TABLE_COMMENT"));
				tables.add(tableVO);
			}
			resultSet.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}

	public static List<ColumnVO> getColumns(DbType type, String tableName) throws SQLException {
		Connection con = type.getConnection();
		List<ColumnVO> columns = new ArrayList<>();
		ColumnVO columnVO;
		try {
			String sql = type.getColumns();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, type.getDbProperty().getDbName());
			ps.setString(2, tableName);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				columnVO = new ColumnVO();
				columnVO.setColumnName(resultSet.getString("COLUMN_NAME"));
				columnVO.setColumnNameHump(CommonUtil.underlineToHump(resultSet.getString("COLUMN_NAME")));
				columnVO.setColumnNameHump1(CommonUtil.underlineToHump1(resultSet.getString("COLUMN_NAME")));
				columnVO.setColumnComent(resultSet.getString("COLUMN_COMMENT"));
				columnVO.setDType(resultSet.getString("DATA_TYPE"));
				columnVO.setDType1(type.getTypeMap().get(resultSet.getString("DATA_TYPE").toUpperCase()));
				columnVO.setColumnType(resultSet.getString("COLUMN_TYPE"));
				columnVO.setIsPrimary(StringUtils.isNotBlank(resultSet.getString("COLUMN_KEY")) && "PRI".equalsIgnoreCase(resultSet.getString("COLUMN_KEY")));
				columnVO.setIsNullAble(
						StringUtils.isNotBlank(resultSet.getString("IS_NULLABLE")) && "YES".equalsIgnoreCase(resultSet.getString("COLUMN_KEY")) ? null : "not null;");
				columns.add(columnVO);
			}
			resultSet.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columns;
	}

	public static TableVO getTableInfo(DbType type, String tableName) throws SQLException {
		Connection con = type.getConnection();
		TableVO tableVO = null;
		try {
			String sql = type.getTableEqual();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, type.getDbProperty().getDbName());
			ps.setString(2, tableName);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				tableVO = new TableVO();
				tableVO.setTableName(resultSet.getString("TABLE_NAME"));
				tableVO.setTableFirstWords(CommonUtil.getFirstWords1(resultSet.getString("TABLE_NAME")));
				//tableVO.setTableNameHump(CommonUtil.underlineToHump(resultSet.getString("TABLE_NAME")));
				//tableVO.setTableNameHump1(CommonUtil.underlineToHump1(resultSet.getString("TABLE_NAME")));
				tableVO.setTableComent(resultSet.getString("TABLE_COMMENT"));
			}
			if (tableVO == null) {
				throw new RuntimeException("MySQLDBUtil_getTable_resultSet:{}查询表不存在 " + "resultSet");
			}
			List<ColumnVO> columns = getColumns(type, tableName);
			tableVO.setColumnVOS(columns);
			List<ColumnVO> collect = columns.stream().filter(ColumnVO::getIsPrimary).collect(Collectors.toList());
			if (collect.size() > 1) {
				throw new RuntimeException("MySQLDBUtil_getTable_collect:{} 表错误,主键数量大于1 " + collect.size());
			}

			if (collect.size() == 1) {
				ColumnVO columnVO = collect.get(0);
				tableVO.setPriKey(columnVO.getColumnName());
				tableVO.setPriKeyHump(columnVO.getColumnNameHump());
				tableVO.setPriKeyHump1(columnVO.getColumnNameHump1());
			}
			resultSet.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableVO;
	}
}
