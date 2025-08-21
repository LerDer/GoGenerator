package com.wd.storage;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * 缓存功能
 *
 * @author lww
 * @date 2020-05-15 14:24
 */
@Data
@State(name = "GoGenerateConfig", storages = {@Storage("goGenerateConfig.xml")})
public class GenerateConfig implements PersistentStateComponent<GenerateConfig> {

    private String dbType;
    private String dbHost;
    private String dbPort;
    private String dbName;
    private String username;
    private String password;
    private String path;
    private String authorName;
    private boolean structSelected;
    private boolean dbSelected;
    private boolean apiSelected;
    private boolean routerSelected;
    private boolean generateSelected;
    private boolean swaggerSelected;
    private boolean txSelected;
    private String projectName;
    private String dbUrl;

    private String moduleName;
    private String modulePath;

    public static GenerateConfig getInstance(Project project) {
        GenerateConfig config = project.getService(GenerateConfig.class);
        if (config == null) {
            config = new GenerateConfig();
        }
        return config;
    }

    @Override
    public GenerateConfig getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull GenerateConfig state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
