package com.github.justinnobledev.macromod.screen;

import com.github.justinnobledev.macromod.MacroMod;
import com.github.justinnobledev.macromod.macros.IMacro;
import com.github.justinnobledev.macromod.macros.Macro;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Optional;
import java.util.stream.Collectors;

public class NewMacroScreen extends Screen {

    private TextFieldWidget text;
    private int currentKey;
    private String command;
    private boolean mouse;

    protected NewMacroScreen(Text textComponent, int currentKey, boolean mouse) {
        super(textComponent);
        this.currentKey= currentKey;
        this.mouse = mouse;
    }

    protected NewMacroScreen(Text textComponent, int currentKey) {
        super(textComponent);
        this.currentKey= currentKey;
        this.mouse = false;
    }

    @Override
    protected void init() {
        super.init();
        text = new TextFieldWidget(textRenderer,this.width / 2 - 62, this.height / 2 - 10, 124, 20, Text.of("Text"));
        text.setMaxLength(100);
        text.setEditable(true);
        text.setVisible(true);
        text.active = true;
        text.setTextFieldFocused(true);

        Optional<Macro> macro = MacroMod.getInstance().getLayerManager().getCurrentLayer().getMacro(currentKey);
        text.setText(macro.isPresent() ? macro.get().getCommand() : "");

        addDrawableChild(text);

        ButtonWidget newMacroCancelButton = new ButtonWidget((this.width / 2) - 62, this.height / 2 + 15, 58, 20, Text.of("Back"),(button) -> {
            MinecraftClient.getInstance().setScreen(new MacroScreen(Text.of("Macros")));
        });

        ButtonWidget newMacroDoneButton = new ButtonWidget((this.width / 2) + 4, this.height / 2 + 15, 62, 20, Text.of("Save Macro"), (button) -> {
            int keyCode = currentKey;
            String command = text.getText();
            if(macro.isPresent() && command.length() == 0) {
                MacroMod.getInstance().getLayerManager().getCurrentLayer().removeMacro(macro.get());
            }
            else if(command.length() > 0) {
                MacroMod.getInstance().getLayerManager().getCurrentLayer().addMacro(command, keyCode, mouse ? InputUtil.Type.MOUSE : InputUtil.Type.KEYSYM);
            }
            MacroMod.getInstance().Save();
            MinecraftClient.getInstance().setScreen(new MacroScreen(Text.of("Macros")));
        });
        this.addDrawableChild(newMacroCancelButton);
        this.addDrawableChild(newMacroDoneButton);
    }
    @Override
    public void render(MatrixStack s, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(s);
        String text = "Command:";
//        drawString(s, font, ChatFormatting.WHITE + text, (this.width / 2) - (font.width(text) / 2) , this.height / 2 - 25, 30);
        drawTextWithShadow(s, textRenderer, Text.of("Command: "), (this.width / 2) - (textRenderer.getWidth(text) / 2) , this.height / 2 - 25, 0xFFFFFF);

        super.render(s,mouseX,mouseY,partialTicks);
    }
}
