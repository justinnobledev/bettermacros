package com.macromod.commandutils;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class commandutils {

    public static String[] SplitByBar(String message){
        ArrayList<String> messages = new ArrayList<>();
        if(message.contains("|")){
            String[] messages = message.split("\\|");
            return messages;
        }
        return messages;
    }

    public static String ReplaceWithInfo(String message){
        message = message.replace("%POSX%", String.valueOf((int)Minecraft.getInstance().player.xo));
        message = message.replace("%POSY%", String.valueOf((int)Minecraft.getInstance().player.yo));
        message = message.replace("%POSZ%", String.valueOf((int)Minecraft.getInstance().player.zo));
        message = message.replace("%HP%", Minecraft.getInstance().player.getHealth() * 100 / Minecraft.getInstance().player.getMaxHealth() + "%");

        return message;
    }
}
