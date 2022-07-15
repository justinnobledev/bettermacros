package com.github.justinnobledev.macromod.layers;

import java.util.ArrayList;
import java.util.List;

public class LayerManager {
    private int currentLayer = 0;
    private List<Layer> layers;
    public LayerManager(){
        this.layers = new ArrayList<>();
    }

    public Layer getCurrentLayer(){
        return this.layers.get(this.currentLayer);
    }

    public void addLayer(Layer layer){
        this.layers.add(layer);
    }

    public int getTotalLayers(){return this.layers.size();}

    public void nextLayer(){
        if(this.currentLayer + 1 >= layers.size())
            this.currentLayer = 0;
        else
            this.currentLayer++;
    }

    public boolean deleteLayer(){
        return this.layers.remove(this.currentLayer) != null;
    }

    public boolean canDeleteLayer(){
        return this.layers.size() > 1;
    }
}
