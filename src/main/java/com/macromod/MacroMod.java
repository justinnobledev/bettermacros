package com.macromod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macromod.structures.LayerManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MacroMod.MOD_ID)
public class MacroMod
{

    public static final String MOD_ID = "macromod";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    private EventHandler eventHandler;
    public LayerManager layerManager;

    public MacroMod() {
        Load();

        MinecraftForge.EVENT_BUS.register(this);
        eventHandler = new EventHandler(this,layerManager);
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }


    public void Save(){
        if(!CheckFile()){
            return;
        }
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(layerManager);
            FileWriter fileWriter = new FileWriter("config/BetterMacros.json");
            fileWriter.write(json);
            fileWriter.close();
            LOGGER.info("Saved Config File");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void Load(){
        if(!CheckFile()){
            return;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("config/BetterMacros.json"));
            Gson gson = new Gson();
            LayerManager obj = gson.fromJson(bufferedReader, LayerManager.class);
            if (obj == null) {//file is empty
                obj = new LayerManager();
            }
            obj.setMod(this);
            this.layerManager= obj;
            LOGGER.info("Config File Loaded");
            Save();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean CheckFile(){
        try {
            File jsonFile = new File("config/BetterMacros.json");
            if (jsonFile.createNewFile()) {
                LOGGER.info("Config File Created");
            }
            if (jsonFile.exists()) {
                return true;
            }
            return false;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
