package com.github.rypengu23.autoworldtools.util;

import com.github.rypengu23.autoworldtools.AutoWorldTools;
import com.github.rypengu23.autoworldtools.config.ConfigLoader;
import com.github.rypengu23.autoworldtools.config.MainConfig;
import com.github.rypengu23.autoworldtools.config.MessageConfig;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;

public class DiscordUtil {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public DiscordUtil(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    public void sendMessageMainChannel(String message){
        TextChannel mainChannel = AutoWorldTools.discordSRV.getMainTextChannel();
        github.scarsz.discordsrv.util.DiscordUtil.sendMessage(mainChannel, message);
    }


}
