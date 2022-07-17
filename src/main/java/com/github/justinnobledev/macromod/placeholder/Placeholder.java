package com.github.justinnobledev.macromod.placeholder;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class Placeholder {
    private static List<Placeholder> placeholders = new ArrayList<>(){
        {
            add(new Placeholder("HP", () ->{
                return Integer.toString(((int) MinecraftClient.getInstance().player.getHealth()));
            }));
            add(new Placeholder("POS", () ->{
                ClientPlayerEntity player = MinecraftClient.getInstance().player;;
                int x = (int)player.getX();
                int y = (int)player.getY();
                int z = (int)player.getZ();
                return String.format("%d %d %d", x, y, z);
            }));
            add(new Placeholder("POSX", () ->{
                return Integer.toString(((int) MinecraftClient.getInstance().player.getX()));
            }));
            add(new Placeholder("POSY", () ->{
                return Integer.toString(((int) MinecraftClient.getInstance().player.getY()));
            }));
            add(new Placeholder("POSZ", () ->{
                return Integer.toString(((int) MinecraftClient.getInstance().player.getZ()));
            }));
            add(new Placeholder("NAME", () ->{
                return MinecraftClient.getInstance().player.getName().getString();
            }));
            add(new Placeholder("COOL_PEOPLE", () ->{
                return "Retro & Nimmy";
            }));
            add(new Placeholder("DUMB_PEOPLE", () ->{
                return "Vinny & Bleua";
            }));
        }
    };

    public static String parsePlaceholders(String message){
        for(Placeholder placeholder : placeholders){
            message = placeholder.parse(message);
        }
        return message;
    }

    private String placeholder;
    private Converter converter;
    public Placeholder(String placeholder, Converter converter){
        this.placeholder = placeholder;
        this.converter = converter;
    }

    public String parse(String message){
        return message.replaceAll("%"+this.placeholder+"%", this.converter.convert());
    }

    public interface Converter{
        String convert();
    }

}
