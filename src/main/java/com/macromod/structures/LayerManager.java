package com.macromod.structures;

import com.macromod.MacroMod;

import java.util.ArrayList;
import java.util.List;

public class LayerManager {

    private ArrayList<Layer> layers;
    //private Layer currentLayer;
    private int currentLayerIndex;
    private transient MacroMod mod;

    public LayerManager(){
        layers=new ArrayList<>();
        Layer layer=new Layer("Default");
        layers.add(layer);
        currentLayerIndex=0;
    }

    public int getCurrentLayerIndex(){
        return currentLayerIndex;
    }

    public void nextLayer(){
        if(layers.size() <= currentLayerIndex +1){
            currentLayerIndex=0;
            //currentLayer=layers.get(0);
        }else {
            currentLayerIndex++;
            //currentLayer = layers.get(currentLayerIndex);
        }
        mod.Save();
    }

    public void setMod(MacroMod mod){
        this.mod=mod;
    }

    public boolean deleteLayer(){
        if(layers.size()<=1){
            return false;
        }
        layers.remove(currentLayerIndex);
        nextLayer();
        return true;
    }

    public void setCurrentLayerIndex(int currentLayerIndex){
        this.currentLayerIndex=currentLayerIndex;
    }

    public List<Layer> getLayers(){
        return layers;
    }

    public Layer getCurrentLayer(){
        return layers.get(currentLayerIndex);
    }

    public void addLayer(Layer layer){
        layers.add(layer);
    }
}
