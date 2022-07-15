package com.github.justinnobledev.macromod.mixin;

import com.github.justinnobledev.macromod.MacroMod;
import com.github.justinnobledev.macromod.screen.MacroScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class PauseScreen {

    @Inject(at = @At("HEAD"), method = "initWidgets()V")
    public void initWidgets(CallbackInfo ci){
        ButtonWidget layerButton = new ButtonWidget(5, 18, 124, 20, Text.of("Layer: " + MacroMod.getInstance().getLayerManager().getCurrentLayer().getName()), (button) -> {
            MacroMod.getInstance().getLayerManager().nextLayer();
            button.setMessage(Text.of("Layer: " + MacroMod.getInstance().getLayerManager().getCurrentLayer().getName()));
        });
        if(MacroMod.getInstance().getLayerManager().getTotalLayers() == 1)
            layerButton.active = false;
        ((GameMenuScreen)(Object)this).addDrawableChild(layerButton);

        ((GameMenuScreen)(Object)this).addDrawableChild(new ButtonWidget(8 + layerButton.getWidth(), 18, 124, 20, new TranslatableText("macromod.config.title"),
                (button) -> {
                    MinecraftClient.getInstance().setScreen(new MacroScreen(Text.of("Macros")));
                })
        );
    }


}
