package com.github.justinnobledev.macromod;

import com.github.justinnobledev.macromod.layers.Layer;
import com.github.justinnobledev.macromod.layers.LayerManager;
import com.github.justinnobledev.macromod.macros.IMacro;
import com.github.justinnobledev.macromod.macros.Macro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class MacroMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("macromod");
	private LayerManager layermanager;
	private static MacroMod mod;

	public static MacroMod getInstance(){return mod;}

	public LayerManager getLayerManager(){
		return this.layermanager;
	}

	public void Save(){
		if(!CheckFile()){
			return;
		}
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(layermanager);
			FileWriter fileWriter = new FileWriter("config/bettermacros.json");
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
			BufferedReader bufferedReader = new BufferedReader(new FileReader("config/bettermacros.json"));
			Gson gson = new Gson();
			LayerManager obj = gson.fromJson(bufferedReader, LayerManager.class);
			if (obj == null) {
				obj = new LayerManager();
				obj.addLayer(new Layer("Default"));
			}
			this.layermanager= obj;
			LOGGER.info("Config File Loaded");
			Save();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public boolean CheckFile(){
		try {
			File jsonFile = new File("config/bettermacros.json");
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

	@Override
	public void onInitialize() {
		mod = this;
		LOGGER.info("Hello Fabric world!");
		this.Load();
//		this.layermanager = new LayerManager();
//		this.layermanager.getCurrentLayer().addMacro("test", InputUtil.GLFW_KEY_W, InputUtil.Type.KEYSYM);
		ClientTickEvents.START_WORLD_TICK.register(listener -> {
			if(MinecraftClient.getInstance().currentScreen != null)
				return;

			long hndl = MinecraftClient.getInstance().getWindow().getHandle();
			long curTime = System.currentTimeMillis();
			ClientPlayerEntity player = MinecraftClient.getInstance().player;

			for(IMacro macro : this.layermanager.getCurrentLayer().getMacros()){
				macro.onTick(hndl, player, curTime);
			}

		});
	}
}
