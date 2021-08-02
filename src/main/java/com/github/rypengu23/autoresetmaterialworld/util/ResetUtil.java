package com.github.rypengu23.autoresetmaterialworld.util;

import com.github.rypengu23.autoresetmaterialworld.AutoResetMaterialWorld;
import com.github.rypengu23.autoresetmaterialworld.Config;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

public class ResetUtil {

    private Config config;
    private final Plugin plugin;

    public ResetUtil(Plugin plugin){
        this.plugin = plugin;
    }

    /**
     * 指定されたタイプの素材世界を再生成
     * 0:ノーマル 1:ネザー 2:ジエンド
     * @param worldType
     */
    public void regenerateWorld(int worldType){
        config = new Config(plugin);
        try {
            MVWorldManager worldManager = AutoResetMaterialWorld.worldManager;

            //ワールド名リストの取得
            ArrayList<String> worldNameList = new ArrayList<>();
            if (worldType == 0) {
                worldNameList = new ArrayList<String>(Arrays.asList(config.getMaterialWorldNameOfNormal()));
            } else if (worldType == 1) {
                worldNameList = new ArrayList<String>(Arrays.asList(config.getMaterialWorldNameOfNether()));
            } else {
                worldNameList = new ArrayList<String>(Arrays.asList(config.getMaterialWorldNameOfEnd()));
            }

            //ワールド再生成
            for (String worldName : worldNameList) {
                Bukkit.getLogger().info("[ARW] ワールド名："+ worldName +"をリセットします。");
                if(worldManager.regenWorld(worldName, true, true, "")){
                    Bukkit.getLogger().info("[ARW] ワールド名："+ worldName +"をリセットしました。");


                    //ワールドボーダーをセット
                    int worldSize = 0;
                    if(worldType == 0){
                        worldSize = config.getMaterialWorldOfNormalSize();
                    }else if(worldType == 1){
                        worldSize = config.getMaterialWorldOfNetherSize();
                    }else{
                        worldSize = config.getMaterialWorldOfEndSize();
                    }
                    WorldBorder worldBorder = Bukkit.getWorld(worldName).getWorldBorder();
                    worldBorder.setCenter(0.0, 0.0);
                    worldBorder.setSize(worldSize);

                }else{
                    Bukkit.getLogger().warning("[ARW] ワールド名："+ worldName +"のリセットに失敗しました。");
                }
            }

        }catch (NoClassDefFoundError e){
            Bukkit.getLogger().warning("[ARW] 素材世界のリセットに失敗しました。");
            Bukkit.getLogger().warning("[ARW] 原因：Multiverse-Coreが導入されていない");
        }
        //作り直すパターン(廃止)
        //素材世界を削除
        //worldManager.deleteWorld(worldName);

        /*
        //素材世界を生成
        // 0:ノーマル 1:ネザー 2:ジエンド
        if(worldType == 0) {
            worldManager.addWorld(
                    worldName, //ワールド名
                    World.Environment.NORMAL, //ワールドタイプ
                    null, //シード値（nullでランダム)
                    WorldType.NORMAL, //生成オプション
                    true, //村生成するかどうか
                    null //カスタムジェネレーター
            );
            //スポーン地点変更
            Bukkit.getWorld(worldName).setSpawnLocation(config.getMaterialWorldOfNormalPortalX(),config.getMaterialWorldOfNormalPortalY(),config.getMaterialWorldOfNormalPortalZ());
            //ワールドボーダーをセット
            System.out.println(config.getMaterialWorldOfNormalSize());
            WorldBorder worldBorder = Bukkit.getWorld(worldName).getWorldBorder();
            worldBorder.setCenter(0.0, 0.0);
            worldBorder.setSize(config.getMaterialWorldOfNormalSize());
        }else if(worldType == 1){
            worldManager.addWorld(
                    worldName, //ワールド名
                    World.Environment.NETHER, //ワールドタイプ
                    null, //シード値（nullでランダム)
                    WorldType.NORMAL, //生成オプション
                    true, //村生成するかどうか
                    null //カスタムジェネレーター
            );
            //スポーン地点変更
            Bukkit.getWorld(worldName).setSpawnLocation(config.getMaterialWorldOfNetherPortalX(),config.getMaterialWorldOfNetherPortalY(),config.getMaterialWorldOfNetherPortalZ());
            //ワールドボーダーをセット
            WorldBorder worldBorder = Bukkit.getWorld(worldName).getWorldBorder();
            worldBorder.setCenter(0.0, 0.0);
            worldBorder.setSize(config.getMaterialWorldOfNetherSize());
        }else{
            worldManager.addWorld(
                    worldName, //ワールド名
                    World.Environment.THE_END, //ワールドタイプ
                    null, //シード値（nullでランダム)
                    WorldType.NORMAL, //生成オプション
                    true, //村生成するかどうか
                    null //カスタムジェネレーター
            );
            //スポーン地点変更
            Bukkit.getWorld(worldName).setSpawnLocation(config.getMaterialWorldOfEndPortalX(),config.getMaterialWorldOfEndPortalY(),config.getMaterialWorldOfEndPortalZ());
            //ワールドボーダーをセット
            WorldBorder worldBorder = Bukkit.getWorld(worldName).getWorldBorder();
            worldBorder.setCenter(0.0, 0.0);
            worldBorder.setSize(config.getMaterialWorldOfEndSize());
        }
        */

    }
}
