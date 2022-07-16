package com.macromod.structures;

import java.util.HashMap;

public class Layer {

    private String layerName;
    private HashMap<Integer, String> macros;

    public Layer(String layerName){
        macros = new HashMap<>();
        this.layerName=layerName;
    }

    public void addMacro(int key, String message){
        if(message.length() == 0){
            macros.remove(key);
            return;
        }
        if(macros.get(key) != null){
            macros.replace(key,message);
        }else {
            macros.put(key, message);
        }
    }

    public String getLayerName(){
        return layerName;
    }

    public String getCommand(int keycode){
        return macros.getOrDefault(keycode,"");
    }

    /*public JSONObject layerToJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("layer",layerName);
        JSONArray jsonArray = new JSONArray();

        for(Map.Entry<Integer,String> entry: macros.entrySet()){
            int keycode = entry.getKey();
            String command = entry.getValue();
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("keyCode",keycode);
            jsonObject2.put("cmd",command);
            jsonArray.put(jsonObject2);
        }
        jsonObject.put("macros",jsonArray);
        return jsonObject;
    }*/
}
