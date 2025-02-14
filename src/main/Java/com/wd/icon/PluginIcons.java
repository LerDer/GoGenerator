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
	public static final Icon abstractException = load("/icons/abstractException.svg");
	public static final Icon abstractException_dark = load("/icons/abstractException_dark.svg");

	public static final Icon cwmVerified = load("/icons/cwmVerified.svg");

	public static Icon load(String iconPath) {
		return IconLoader.getIcon(iconPath, PluginIcons.class);
	}

}
