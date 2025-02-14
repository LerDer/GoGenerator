package com.wd.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.wd.ui.GoGeneratorForm;

public class GoGeneratorAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent e) {
		// 创建并显示表单对话框
		GoGeneratorForm form = new GoGeneratorForm(e.getProject());
		form.show();
	}
}
