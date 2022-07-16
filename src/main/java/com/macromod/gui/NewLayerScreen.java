package com.macromod.gui;

import com.macromod.MacroMod;
import com.macromod.structures.Layer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;

public class NewLayerScreen extends Screen {

    private MacroMod mod;
    private EditBox text;

    protected NewLayerScreen(TextComponent textComponent, MacroMod mod) {
        super(textComponent);
        this.mod=mod;
    }

    @Override
    protected void init() {
        super.init();
        text = new EditBox(font,this.width / 2 - 62, this.height / 2 - 10, 124, 20, new TextComponent("Text"));
        text.setMaxLength(100);
        text.setEditable(true);
        text.setFocus(true);
        text.active = true;
        text.visible = true;
        addRenderableWidget(text);


        Button newLayerCancelButton = new Button((this.width / 2) - 62, this.height / 2 + 15, 58, 20, new TextComponent("Cancel"), (button) -> {
            Minecraft.getInstance().setScreen(new MacroScreen(new TextComponent("Macros"), mod));
        });

        Button newLayerDoneButton = new Button((this.width / 2) + 4, this.height / 2 + 15, 62, 20, new TextComponent("Save Layer"), (button) -> {
            mod.layerManager.addLayer(new Layer(text.getValue()));
            mod.Save();
            mod.layerManager.nextLayer();
            Minecraft.getInstance().setScreen(new MacroScreen(new TextComponent("Macros"), mod));
        });
        this.addRenderableWidget(newLayerDoneButton);
        this.addRenderableWidget(newLayerCancelButton);
    }
    @Override
    public void render(PoseStack s, int mouseX, int mouseY, float partialTicks){
        this.renderDirtBackground(0);
        String text = "New Layer Name:";
        drawString(s, font, ChatFormatting.WHITE + text, (this.width / 2) - (font.width(text) / 2) , this.height / 2 - 25, 30);
        super.render(s,mouseX,mouseY,partialTicks);
    }
}
