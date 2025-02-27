package com.wd.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.wd.dbs.DBProperty;
import com.wd.dbs.DbType;
import com.wd.dbs.DbUtil;
import com.wd.i18.GoGeneratorBoundle;
import com.wd.icon.PluginIcons;
import com.wd.storage.GenerateConfig;
import com.wd.util.CommonUtil;
import com.wd.util.FileChooseUi;
import com.wd.util.FreeMarkerUtil;
import com.wd.vo.TableVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

/**
 * @author lww
 * @date 2025-02-14 16:07
 */
public class GoGeneratorForm extends DialogWrapper {

	private JComboBox dbSelected;
	private JTextField dbUrlField;
	private JTextField usernameField;
	private JButton testConnectButton;
	private JTextField projectPath;
	private JButton selectPathButton;
	private JTextField authorField;
	private JTextField projectName;
	private JComboBox tableNames;
	private JRadioButton structRadioButton;
	private JRadioButton dbRadioButton;
	private JRadioButton apiRadioButton;
	private JRadioButton routerRadioButton;
	private JRadioButton txRradioButton;
	private JRadioButton swaggerRadioButton;
	private JRadioButton generateMarkRadioButton;
	private JRadioButton initRadioButton;
	private JPanel contentPanel;
	private JPasswordField passwordField;
	private JTextField dbNameField;
	private JTextField hostField;
	private JTextField portField;
	private JLabel dbTypeLabel;
	private JLabel dbNameLabel;
	private JLabel dbHostLabel;
	private JLabel dbPortLabel;
	private JLabel dbUrlLabel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel projectPathLabel;
	private JLabel authorLabel;
	private JLabel projectNameLabel;
	private JLabel tableNamesLabel;
	private JLabel optionsLabel;

	private Project project;

	public GoGeneratorForm(@Nullable Project project) {
		super(project);
		this.project = project;
		init();
		setTitle("Go Generator");
		createUIComponents();
		dbTypeLabel.setText(GoGeneratorBoundle.messageOnSystem("DataBase.Type"));
		dbNameLabel.setText(GoGeneratorBoundle.messageOnSystem("DataBase.Name"));
		dbHostLabel.setText(GoGeneratorBoundle.messageOnSystem("DataBase.Host"));
		dbPortLabel.setText(GoGeneratorBoundle.messageOnSystem("DataBase.Port"));
		dbUrlLabel.setText(GoGeneratorBoundle.messageOnSystem("DataBase.URL"));
		usernameLabel.setText(GoGeneratorBoundle.messageOnSystem("DataBase.UserName"));
		passwordLabel.setText(GoGeneratorBoundle.messageOnSystem("DataBase.Password"));
		projectPathLabel.setText(GoGeneratorBoundle.messageOnSystem("Project.Path"));
		authorLabel.setText(GoGeneratorBoundle.messageOnSystem("GENERATE.Author"));
		projectNameLabel.setText(GoGeneratorBoundle.messageOnSystem("Project.Name"));
		tableNamesLabel.setText(GoGeneratorBoundle.messageOnSystem("Table.Names"));
		optionsLabel.setText(GoGeneratorBoundle.messageOnSystem("GENERATE.Options"));
		testConnectButton.setText(GoGeneratorBoundle.messageOnSystem("Test.Connection"));
		selectPathButton.setText(GoGeneratorBoundle.messageOnSystem("Select.Path"));

		structRadioButton.setText(GoGeneratorBoundle.messageOnSystem("GENERATE.Struct"));
		dbRadioButton.setText(GoGeneratorBoundle.messageOnSystem("GENERATE.DB"));
		apiRadioButton.setText(GoGeneratorBoundle.messageOnSystem("GENERATE.Api"));
		routerRadioButton.setText(GoGeneratorBoundle.messageOnSystem("GENERATE.Router"));
		txRradioButton.setText(GoGeneratorBoundle.messageOnSystem("GENERATE.Transaction"));
		swaggerRadioButton.setText(GoGeneratorBoundle.messageOnSystem("GENERATE.Swagger"));
		generateMarkRadioButton.setText(GoGeneratorBoundle.messageOnSystem("GENERATE.Mark"));
		initRadioButton.setText(GoGeneratorBoundle.messageOnSystem("GENERATE.Init.File"));

		testConnectButton.setIcon(PluginIcons.testCustom);
		selectPathButton.setIcon(PluginIcons.projectStructure);
		dbSelected.addItemListener(e -> onDbSelect());
		testConnectButton.addActionListener(e -> onTest());
		selectPathButton.addActionListener(e -> onSelect());

		//初始化 回显
		GenerateConfig config = GenerateConfig.getInstance(this.project);
		if (config.getDbHost() != null) {
			dbSelected.setSelectedItem(config.getDbType());
			hostField.setText(config.getDbHost());
			portField.setText(config.getDbPort());
			dbNameField.setText(config.getDbName());
			usernameField.setText(config.getUsername());
			passwordField.setText(config.getPassword());
			projectPath.setText(config.getPath());
			authorField.setText(config.getAuthorName());
			structRadioButton.setSelected(config.isStructSelected());
			dbRadioButton.setSelected(config.isDbSelected());
			apiRadioButton.setSelected(config.isApiSelected());
			routerRadioButton.setSelected(config.isRouterSelected());
			generateMarkRadioButton.setSelected(config.isGenerateSelected());
			swaggerRadioButton.setSelected(config.isSwaggerSelected());
			txRradioButton.setSelected(config.isTxSelected());
			projectName.setText(config.getProjectName());
			dbUrlField.setText(config.getDbUrl());
		} else {
			dbSelected.setSelectedItem(DbType.MySQL.name());
			hostField.setText(DbType.MySQL.getDefaultHost());
			portField.setText(DbType.MySQL.getDefaultPort());
			usernameField.setText(DbType.MySQL.getDefaultUserName());
		}

	}

	@Nullable
	@Override
	protected JComponent createCenterPanel() {
		return contentPanel;
	}

	private void onSelect() {
		FileChooseUi uiComponentFacade = FileChooseUi.getInstance(project);
		VirtualFile baseDir = null;
		if (project != null) {
			baseDir = project.getBaseDir();
		}
		final VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("Select Project Path", baseDir, baseDir);
		if (null != vf) {
			this.projectPath.setText(vf.getPath());
		}
	}

	private void onTest() {
		tableNames.removeAllItems();
		String host = hostField.getText();
		String port = portField.getText();
		String dbName = dbNameField.getText();
		String username = usernameField.getText();
		String password = passwordField.getText();
		String projectName1 = projectName.getText();

		Boolean check = CommonUtil.check(contentPanel, host, dbName, username, password);
		if (!check) {
			return;
		}
		Connection con = null;
		Object item = dbSelected.getSelectedItem();
		CommonUtil.isTrue(item != null, "Get DataBase Type Fail！");
		try {
			DbType dbType = DbType.getByName(item.toString(), new DBProperty(host, port, dbName, username, password));
			Class.forName(dbType.getDriverClass());
			dbUrlField.setText(String.format(dbType.getConnectionUrlPattern(), host, port, dbName));

			CommonUtil.initGenerateConfig(project, dbSelected, hostField, portField,
					dbNameField, usernameField, passwordField, projectPath,
					authorField, structRadioButton, dbRadioButton, apiRadioButton,
					routerRadioButton, generateMarkRadioButton, swaggerRadioButton,
					txRradioButton, projectName1, dbUrlField.getText());

			con = dbType.getConnection();
			if (con != null) {
				JOptionPane.showMessageDialog(contentPanel, "数据库连接成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
				addItem(dbName, dbType, null);
			} else {
				JOptionPane.showMessageDialog(contentPanel, "数据库连接失败！", "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(contentPanel, "数据库连接失败！", "错误", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void addItem(String dbName, DbType type, String keyword) throws SQLException {
		List<TableVO> allTables = DbUtil.getAllTables(type, dbName, keyword);
		List<String> tables = allTables.stream().map(TableVO::getTableName).collect(Collectors.toList());
		for (String table : tables) {
			tableNames.addItem(table);
		}
	}

	private void onDbSelect() {
		if (StringUtils.isBlank(dbNameField.getText())) {
			Object item = dbSelected.getSelectedItem();
			assert item != null;

			//只是获取url, 属性可以置空
			DbType dbType = DbType.getByName(item.toString(), null);

			hostField.setText(dbType.getDefaultHost());
			portField.setText(dbType.getDefaultPort());
			usernameField.setText(dbType.getDefaultUserName());
			dbUrlField.setText(String.format(dbType.getConnectionUrlPattern(), dbType.getDefaultHost(), dbType.getDefaultPort(), ""));
		}
	}

	private void createUIComponents() {
		String[] listData = new String[]{DbType.MySQL.name()/*, DbType.PostgreSQL.name(), DbType.Oracle.name()*/};
		for (String listDatum : listData) {
			dbSelected.addItem(listDatum);
		}
	}

	@SneakyThrows
	@Override
	protected void doOKAction() {
		String host = hostField.getText();
		String port = portField.getText();
		String dbName = dbNameField.getText();
		String username = usernameField.getText();
		String password = passwordField.getText();
		String path = projectPath.getText();
		String authorName = authorField.getText();
		String tableName = tableNames.getSelectedItem().toString();
		String projectName1 = projectName.getText();
		if (StringUtils.isBlank(tableName)) {
			JOptionPane.showMessageDialog(contentPanel, "Please Select Table", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}

		CommonUtil.initGenerateConfig(project, dbSelected, hostField, portField,
				dbNameField, usernameField, passwordField, projectPath,
				authorField, structRadioButton, dbRadioButton, apiRadioButton,
				routerRadioButton, generateMarkRadioButton, swaggerRadioButton, txRradioButton,
				projectName1, "");
		Boolean check = CommonUtil.checkAll(contentPanel, host, dbName, username, password, path, authorName, tableName, projectName1);
		if (!check) {
			return;
		}
		Object item = dbSelected.getSelectedItem();
		CommonUtil.isTrue(item != null, "数据库类型获取失败！");
		DbType dbType = DbType.getByName(item.toString(), new DBProperty(host, port, dbName, username, password));
		TableVO infoVO = DbUtil.getTableInfo(dbType, tableName);
		infoVO.setAuthorName(authorName);
		infoVO.setNowDate(CommonUtil.getNowDate());
		infoVO.setProjectName(projectName1);
		infoVO.setSwagger(swaggerRadioButton.isSelected());
		infoVO.setTx(txRradioButton.isSelected());
		infoVO.setHost(hostField.getText());
		infoVO.setPort(portField.getText());
		infoVO.setUsername(usernameField.getText());
		infoVO.setPassword(passwordField.getText());
		infoVO.setDbName(dbNameField.getText());

		if (generateMarkRadioButton.isSelected()) {
			infoVO.setGenMark("Generate By GoGenerator");
		}
		Boolean res = true;
		if (structRadioButton.isSelected()) {
			res &= FreeMarkerUtil.genStruct(infoVO, path);
		}
		if (dbRadioButton.isSelected()) {
			res &= FreeMarkerUtil.genDB(infoVO, path);
		}
		if (apiRadioButton.isSelected()) {
			res &= FreeMarkerUtil.genApi(infoVO, path);
		}
		if (routerRadioButton.isSelected()) {
			res &= FreeMarkerUtil.genRouter(infoVO, path);
		}
		if (initRadioButton.isSelected()) {
			res &= FreeMarkerUtil.genInit(infoVO, path);
		}
		if (res) {
			JOptionPane.showMessageDialog(contentPanel, "生成成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(contentPanel, "生成失败！", "错误", JOptionPane.ERROR_MESSAGE);
		}
		//dispose();
	}
}
