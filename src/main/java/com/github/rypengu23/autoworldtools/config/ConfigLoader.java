package com.github.rypengu23.autoworldtools.config;

import com.github.rypengu23.autoworldtools.AutoWorldTools;
import jp.jyn.jbukkitlib.config.YamlLoader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigLoader {

    private final YamlLoader mainLoader;
    private MainConfig mainConfig;

    private YamlLoader messageLoader;
    private MessageConfig messageConfig;

    public ConfigLoader() {
        Plugin plugin = AutoWorldTools.getInstance();
        this.mainLoader = new YamlLoader(plugin, "config.yml");
        mainConfig = new MainConfig(mainLoader.getConfig());
        this.messageLoader = new YamlLoader(plugin, "message_"+ mainConfig.getLanguage() +".yml");
        messageConfig = new MessageConfig(messageLoader.getConfig());
    }

    public void reloadConfig() {
        Plugin plugin = AutoWorldTools.getInstance();

        //MainConfig
        mainLoader.saveDefaultConfig();
        if (mainConfig != null) {
            mainLoader.reloadConfig();
        }
        mainConfig = new MainConfig(mainLoader.getConfig());

        //MessageConfig
        this.messageLoader = new YamlLoader(plugin, "message_ja.yml");
        messageLoader.saveDefaultConfig();
        this.messageLoader = new YamlLoader(plugin, "message_en.yml");
        messageLoader.saveDefaultConfig();
        this.messageLoader = new YamlLoader(plugin, "message_"+ mainConfig.getLanguage() +".yml");
        if (messageConfig != null) {
            this.messageLoader = new YamlLoader(plugin, "message_"+ mainConfig.getLanguage() +".yml");
            messageLoader.reloadConfig();
        }
        messageConfig = new MessageConfig(messageLoader.getConfig());

        //CommandMessage
        CommandMessage commandMessage = new CommandMessage(mainConfig);
        commandMessage.changeLanguageCommandMessages();

        //ConsoleMessage
        ConsoleMessage consoleMessage = new ConsoleMessage(mainConfig);
        consoleMessage.changeLanguageConsoleMessages();
    }

    public void saveConfig(){
        mainLoader.saveConfig();
        messageLoader.saveConfig();
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public void setMainConfig(MainConfig mainConfig){
        this.mainConfig = mainConfig;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public void setMessageConfig(MessageConfig messageConfig){
        this.messageConfig = messageConfig;
    }

}
