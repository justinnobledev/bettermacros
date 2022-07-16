package com.macromod.gui;

import com.macromod.MacroMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;

public class NewMacroScreen extends Screen {

    private MacroMod mod;
    private EditBox text;
    private int currentKey;
    private String command;

    protected NewMacroScreen(TextComponent textComponent, MacroMod mod, int currentKey) {
        super(textComponent);
        this.mod=mod;
        this.currentKey= currentKey;
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
        text.setValue(mod.layerManager.getCurrentLayer().getCommand(currentKey));

        addRenderableWidget(text);

        Button newMacroCancelButton = new Button((this.width / 2) - 62, this.height / 2 + 15, 58, 20, CommonComponents.GUI_CANCEL, (button) -> {
            Minecraft.getInstance().setScreen(new MacroScreen(new TextComponent("Macros"), mod));
        });

        Button newMacroDoneButton = new Button((this.width / 2) + 4, this.height / 2 + 15, 62, 20, new TextComponent("Save Macro"), (button) -> {
            int keyCode = currentKey;
            String command = text.getValue();
            mod.layerManager.getCurrentLayer().addMacro(keyCode,command);
            mod.Save();
            Minecraft.getInstance().setScreen(new MacroScreen(new TextComponent("Macros"), mod));
        });
        this.addRenderableWidget(newMacroCancelButton);
        this.addRenderableWidget(newMacroDoneButton);
    }
    @Override
    public void render(PoseStack s, int mouseX, int mouseY, float partialTicks){
        this.renderDirtBackground(0);
        String text = "Command:";
        drawString(s, font, ChatFormatting.WHITE + text, (this.width / 2) - (font.width(text) / 2) , this.height / 2 - 25, 30);
        super.render(s,mouseX,mouseY,partialTicks);
    }
}
