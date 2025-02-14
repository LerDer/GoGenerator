package com.wd.util;

import com.intellij.openapi.project.Project;
import com.wd.storage.GenerateConfig;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * 通用工具
 *
 * @author lww
 */
public class CommonUtil {

    /**
     * 驼峰转下划线
     */
    public static String humpToUnderline(String str) {
        String regex = "([A-Z])";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group();
            str = str.replaceAll(target, "_" + target.toLowerCase());
        }
        return str;
    }

    /**
     * 下划线转驼峰
     */
    public static String underlineToHump(String str) {
        String regex = "_(.)";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group(1);
            str = str.replaceAll("_" + target, target.toUpperCase());
        }
        return str;
    }

    /**
     * 下划线转驼峰,首字母大写
     */
    public static String underlineToHump1(String str) {
        String regex = "_(.)";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group(1);
            str = str.replaceAll("_" + target, target.toUpperCase());
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 驼峰取首字母
     */
    public static String getFirstWords(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.charAt(0));
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt)) {
                sb.append(charAt);
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 下划线取首字母
     */
    public static String getFirstWords1(String str) {
        StringBuilder sb = new StringBuilder();
        String[] split = str.split("_");
        for (String string : split) {
            sb.append(string, 0, 1);
        }
        return sb.toString().toLowerCase();
    }

    public static Boolean check(JPanel contentPane, String host, String dbName, String username, String password) {
        if (host.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "数据库host不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dbName.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "数据库名称不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "用户名不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static GenerateConfig initGenerateConfig(Project project, JComboBox dbSelected, JTextField hostField, JTextField portField,
            JTextField dbField, JTextField username, JPasswordField passwordField, JTextField projectPath, JTextField author, JRadioButton structRadioButton,
            JRadioButton dbRadioButton, JRadioButton apiRadioButton, JRadioButton routerRadioButton, JRadioButton generateRadioButton, JRadioButton swaggerRadioButton, JRadioButton txRradioButton, String projectName) {
        GenerateConfig instance = GenerateConfig.getInstance(project);
        instance.setDbType(dbSelected.getSelectedItem() == null ? "" : dbSelected.getSelectedItem().toString());
        instance.setDbHost(hostField.getText());
        instance.setDbPort(portField.getText());
        instance.setDbName(dbField.getText());
        instance.setUsername(username.getText());
        instance.setPassword(passwordField.getText());
        instance.setPath(projectPath.getText());
        instance.setAuthorName(author.getText());
        instance.setStructSelected(structRadioButton.isSelected());
        instance.setDbSelected(dbRadioButton.isSelected());
        instance.setApiSelected(apiRadioButton.isSelected());
        instance.setRouterSelected(routerRadioButton.isSelected());
        instance.setGenerateSelected(generateRadioButton.isSelected());
        instance.setSwaggerSelected(swaggerRadioButton.isSelected());
        instance.setTxSelected(txRradioButton.isSelected());
        instance.setProjectName(projectName);
        return instance;
    }

    public static void isTrue(boolean b, String s) {
        if (!b) {
            throw new RuntimeException(s);
        }
    }

    public static Boolean checkAll(JPanel contentPane, String host, String dbName, String username, String password, String path, String authorName, String tableName, String projectName) {
        check(contentPane, host, dbName, username, password);
        if (path.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "路径不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (authorName.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "作者不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (tableName.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "表名不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (projectName.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "项目名称不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static String getNowDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
