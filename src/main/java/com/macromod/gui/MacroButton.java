package com.macromod.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class MacroButton extends Button{

    private int keyCode;
    public MacroButton(int keycode,int p_93721_, int p_93722_, int p_93723_, int p_93724_, Component p_93725_, OnPress p_93726_) {
        super(p_93721_, p_93722_, p_93723_, p_93724_, p_93725_, p_93726_);
        this.keyCode=keycode;
    }

    public int getKeyCode(){
        return keyCode;
    }
}
