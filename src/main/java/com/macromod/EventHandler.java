package com.macromod;

import com.macromod.gui.MacroScreen;
import com.macromod.structures.Layer;
import com.macromod.structures.LayerManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent.InitScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.network.chat.*;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;

@Mod.EventBusSubscriber(modid = "macromod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EventHandler {

    private MacroMod mod;
    private Button layerButton, openMacrosButton;
    private static final DecimalFormat df = new DecimalFormat("0.0");
    private LayerManager layerManager;
    private boolean inGame;

    public EventHandler(MacroMod mod, LayerManager layerManager){
        this.mod = mod;
        this.layerManager = layerManager;

        layerButton = new Button(5, 18, 124, 20, new TextComponent("Layer: " + layerManager.getCurrentLayer().getLayerName()), (p_213124_1_) -> {
            layerManager.nextLayer();
            layerButton.setMessage(new TextComponent("Layer: " + mod.layerManager.getCurrentLayer().getLayerName()));
        });

        openMacrosButton = new Button(8 + layerButton.getWidth(), 18, 124, 20, new TextComponent("Macro Menu"), (p_213124_1_) -> {
            Minecraft.getInstance().setScreen(new MacroScreen(new TextComponent("Macros"), mod));
        });
    }
/*
    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent e){
        final LocalPlayer player = Minecraft.getInstance().player;
        // check if we are in-game
        Minecraft.getInstance().
        if (player == null) {
            inGame=false;
            return;
        }
        inGame=true;
    }
 */
    @SubscribeEvent
    public void addMacroButton(final InitScreenEvent event)
    {
        if(!(event.getScreen() instanceof PauseScreen)){
            return;
        }
        layerButton.setMessage(new TextComponent("Layer: " + mod.layerManager.getCurrentLayer().getLayerName()));

        event.addListener(layerButton);
        event.addListener(openMacrosButton);
    }


    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent e){
        if(e.getAction() != GLFW.GLFW_PRESS){
            return;
        }
        Screen screen = Minecraft.getInstance().screen;
        if(screen != null){
            return;
        }
        Layer layer = layerManager.getCurrentLayer();
        String command;
        if((command=layer.getCommand(e.getKey())) == ""){
            return;
        }
        Send(command);
    }


    @SubscribeEvent
    public void mouseClick(InputEvent.MouseInputEvent e){
        if(e.getAction() != GLFW.GLFW_PRESS){
            return;
        }
        Screen screen = Minecraft.getInstance().screen;
        if(screen != null){
            return;
        }
        Layer layer = layerManager.getCurrentLayer();
        String command;
        if((command=layer.getCommand(e.getButton())) == ""){
            return;
        }
        Send(command);
    }

    public void Send(String message){
        message = message.replace("%POSX%", df.format(Minecraft.getInstance().player.xo) + "");
        message = message.replace("%POSY%", df.format(Minecraft.getInstance().player.yo) + "");
        message = message.replace("%POSZ%", df.format(Minecraft.getInstance().player.zo) + "");
        message = message.replace("%HP%", df.format(Minecraft.getInstance().player.getHealth() * 100 / Minecraft.getInstance().player.getMaxHealth()) + "%");
        if(message.contains("|")){
            String[] messages = message.split("\\|");
            for(String str : messages){
                Minecraft.getInstance().player.chat(str);
            }
            return;
        }
        Minecraft.getInstance().player.chat(message);
    }
}
