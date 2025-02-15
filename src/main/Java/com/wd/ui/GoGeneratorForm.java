package com.wd.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.wd.icon.PluginIcons;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.jetbrains.annotations.Nullable;

/**
 * @author lww
 * @date 2025-02-14 16:07
 */
public class GoGeneratorForm extends DialogWrapper {

	private JComboBox dbSelected;
	private JTextField dbUrl;
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

	public GoGeneratorForm(@Nullable Project project) {
		super(project);
		init();
		setTitle("Go Generator");

		testConnectButton.setIcon(PluginIcons.testCustom);
		selectPathButton.setIcon(PluginIcons.projectStructure);
	}

	@Nullable
	@Override
	protected JComponent createCenterPanel() {
		return contentPanel;
	}

	//public static void init(AnActionEvent e) {
	//	GoGenForm dialog = new GoGenForm(e.getProject());
	//	dialog.pack();
	//	//设置窗口居中
	//	dialog.setLocationRelativeTo(null);
	//	dialog.setVisible(true);
	//}

}
