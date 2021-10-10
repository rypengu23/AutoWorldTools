package com.github.rypengu23.autoworldtools.model;

public class ResetWorldModel {

    private String worldName;
    private long seed = -1;

    public ResetWorldModel() {
    }

    public ResetWorldModel(String worldName, Long seed) {
        setWorldName(worldName);
        setSeed(seed);
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public boolean useSeed(){
        if(this.seed == -1){
            return false;
        }
        return true;
    }
}
