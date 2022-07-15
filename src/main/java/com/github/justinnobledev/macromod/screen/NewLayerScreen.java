package com.github.justinnobledev.macromod.screen;

import com.github.justinnobledev.macromod.MacroMod;
import com.github.justinnobledev.macromod.layers.Layer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class NewLayerScreen extends Screen {

    private TextFieldWidget text;

    protected NewLayerScreen(Text textComponent) {
        super(textComponent);
    }

    @Override
    protected void init() {
        super.init();
        text = new TextFieldWidget(textRenderer,this.width / 2 - 62, this.height / 2 - 10, 124, 20, Text.of("Text"));
        text.setMaxLength(100);
        text.setEditable(true);
        text.changeFocus(true);
        text.setTextFieldFocused(true);
        text.active = true;
        text.visible = true;
        addDrawableChild(text);


        ButtonWidget newLayerCancelButton = new ButtonWidget((this.width / 2) - 62, this.height / 2 + 15, 58, 20, Text.of("Cancel"), (button) -> {
            MinecraftClient.getInstance().setScreen(new MacroScreen(Text.of("Macros")));
        });

        ButtonWidget newLayerDoneButton = new ButtonWidget((this.width / 2) + 4, this.height / 2 + 15, 62, 20, Text.of("Save Layer"), (button) -> {
            MacroMod.getInstance().getLayerManager().addLayer(new Layer(text.getText()));
            MacroMod.getInstance().Save();
            MacroMod.getInstance().getLayerManager().nextLayer();
            MinecraftClient.getInstance().setScreen(new MacroScreen(Text.of("Macros")));
        });
        this.addDrawableChild(newLayerDoneButton);
        this.addDrawableChild(newLayerCancelButton);
    }
    @Override
    public void render(MatrixStack s, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(s);
        String text = "New Layer Name:";
        drawTextWithShadow(s, textRenderer, Text.of(text), (this.width / 2) - (textRenderer.getWidth(text) / 2) , this.height / 2 - 25, 0xFFFFFF);
        super.render(s,mouseX,mouseY,partialTicks);
    }
}
