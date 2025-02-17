package com.wd.i18;

import com.intellij.AbstractBundle;
import com.intellij.openapi.diagnostic.Logger;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/**
 * @author lww
 * @date 2025-02-17 11:52
 */
public class GoGeneratorBoundle extends AbstractBundle {

	private static final Logger LOG = Logger.getInstance(GoGeneratorBoundle.class);
	private static final String BUNDLE = "messages.GoGeneratorBoundle";
	private final String pathToBundle;
	private final AbstractBundle adaptedBundle;
	private final ResourceBundle.Control adaptedControl = ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES);
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE, Objects.requireNonNull(getDynamicLocale()));
	private static final GoGeneratorBoundle INSTANCE = new GoGeneratorBoundle(BUNDLE);

	protected GoGeneratorBoundle(@NotNull String pathToBundle) {
		super(pathToBundle);
		this.pathToBundle = pathToBundle;
		this.adaptedBundle = createAdaptedBundle();
	}

	/**
	 * 基于当前IDE所安装的语言包来进行本地化翻译
	 *
	 * @param key    键
	 * @param params 参数
	 * @return 翻译结果
	 */
	@NotNull
	public static @Nls String message(@NotNull String key, Object... params) {
		return INSTANCE.getAdaptedMessage(key, params);
	}

	/**
	 * 基于当前系统语言来进行本地化翻译
	 *
	 * @param key 键
	 * @return 翻译结果
	 */
	@NotNull
	public static String messageOnSystem(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
		String result = RESOURCE_BUNDLE.getString(key);
		return MessageFormat.format(result, params);
	}

	private String getAdaptedMessage(@PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
		if (adaptedBundle != null) {
			return adaptedBundle.getMessage(key, params);
		}
		return getMessage(key, params);
	}

	private AbstractBundle createAdaptedBundle() {
		Locale dynamicLocale = getDynamicLocale();
		if (dynamicLocale == null) {
			return null;
		}

		if (dynamicLocale.toLanguageTag().equals(Locale.ENGLISH.toLanguageTag())) {
			//return new AbstractBundle(pathToBundle) {
			//
			//
			//	@Override
			//	protected @NotNull ResourceBundle findBundle(@NotNull String pathToBundle, @NotNull ClassLoader loader, ResourceBundle.@NotNull Control control) {
			//		ResourceBundle dynamicBundle = ResourceBundle.getBundle(pathToBundle, dynamicLocale, loader, adaptedControl);
			//		return (dynamicBundle != null) ? dynamicBundle : super.findBundle(pathToBundle, loader, control);
			//	}
			//};
		}

		return null;
	}

	private static Locale getDynamicLocale() {
		try {
			//return DynamicBundle.getLocale();
			return Locale.getDefault();
		} catch (Exception e) {
			LOG.debug(e.getClass().getSimpleName() + ": DynamicBundle.getLocale()");
			return null;
		}
	}

}
