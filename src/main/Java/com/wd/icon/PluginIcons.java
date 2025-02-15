package com.wd.icon;

import com.intellij.openapi.util.IconLoader;
import javax.swing.Icon;

/**
 * @author Memory
 * @since 2024/6/20
 */
public class PluginIcons {

	public static final Icon lightning = load("/icons/lightning.svg");
	public static final Icon lightning_dark = load("/icons/lightning_dark.svg");
	
	public static final Icon plugin = load("/icons/plugin.svg");
	public static final Icon plugin_dark = load("/icons/plugin_dark.svg");
	
	public static final Icon projectStructure = load("/icons/projectStructure.svg");
	public static final Icon projectStructure_dark = load("/icons/projectStructure_dark.svg");
	
	public static final Icon success = load("/icons/success.svg");
	public static final Icon success_dark = load("/icons/success_dark.svg");
	
	public static final Icon testCustom = load("/icons/testCustom.svg");
	public static final Icon testCustom_dark = load("/icons/testCustom_dark.svg");
	
	public static final Icon testFailed = load("/icons/testFailed.svg");
	public static final Icon testFailed_dark = load("/icons/testFailed_dark.svg");
	
	public static Icon load(String iconPath) {
		return IconLoader.getIcon(iconPath, PluginIcons.class);
	}

}
