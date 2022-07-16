package com.macromod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.macromod.structures.LayerManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        // Register ourselves for server and other game events we are interested i
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

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("Common Setup Called");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Client Setup called");
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        //InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        //LOGGER.info("Got IMC {}", event.getIMCStream().
           //     map(m->m.getMessageSupplier().get()).
             //   collect(Collectors.toList()));
    }


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    /*
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
     */
}
