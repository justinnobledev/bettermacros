package com.github.justinnobledev.macromod.screen;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class MacroButton extends ButtonWidget {

    private int keyCode;
    public MacroButton(int keycode, int x, int y, int width, int height, Text text, ButtonWidget.PressAction press) {
        super(x, y, width, height, text, press);
        this.keyCode=keycode;
    }

    public int getKeyCode(){
        return keyCode;
    }
}
