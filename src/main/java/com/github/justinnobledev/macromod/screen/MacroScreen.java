package com.github.justinnobledev.macromod.screen;

import com.github.justinnobledev.macromod.MacroMod;
import com.github.justinnobledev.macromod.macros.Macro;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.*;

public final class MacroScreen extends Screen {

    private final List<MacroButton> macroButtons;
//    private final Hashtable keys;
    private ButtonWidget layerButton;

    public MacroScreen(Text textComponent) {
        super(textComponent);
        macroButtons = new ArrayList<>();
//        keys = new Hashtable();
    }

    @Override
    protected void init(){
        super.init();
        Setup();
    }

    public void Setup(){

        ButtonWidget removeLayer = new ButtonWidget((this.width / 2), 8, 124, 20, Text.of("Remove Layer"), (button) ->{
            MacroMod.getInstance().getLayerManager().deleteLayer();
            layerButton.onPress();
            MacroMod.getInstance().Save();
            if(MacroMod.getInstance().getLayerManager().getTotalLayers() == 1){
                button.active = false;
                layerButton.active = false;
            }
            ShowMain();
        });
        if(MacroMod.getInstance().getLayerManager().getTotalLayers() == 1)
            removeLayer.active = false;

        this.addDrawableChild(removeLayer);

        this.addDrawableChild(new ButtonWidget((this.width / 2) + 140, 8, 60, 20, Text.of("Done"), (button) -> this.close()));

        layerButton = new ButtonWidget((this.width / 4) - 92, 8, 124, 20, Text.of("Layer: " + MacroMod.getInstance().getLayerManager().getCurrentLayer().getName()),
            (button) -> {
                MacroMod.getInstance().getLayerManager().nextLayer();
                layerButton.setMessage(Text.of("Layer: " + MacroMod.getInstance().getLayerManager().getCurrentLayer().getName()));
                ShowMain();
        });
        if(MacroMod.getInstance().getLayerManager().getTotalLayers() == 1)
            layerButton.active = false;
        this.addDrawableChild(layerButton);

        this.addDrawableChild(new ButtonWidget((this.width / 4) + 37, 8, 62, 20, Text.of("New Layer"), (button) -> MinecraftClient.getInstance().setScreen(new NewLayerScreen(Text.of("Macros")))));

        // FIRST ROW

        MacroButton b1 = new MacroButton(96,(this.width / 16) - 16, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "`"), (button) -> createMacro(96, false));
        this.addDrawableChild(b1);

        MacroButton b2 = new MacroButton(49,(this.width * 2 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "1"), (button) -> createMacro(49, false));
        this.addDrawableChild(b2);

        MacroButton b3 = new MacroButton(50,(this.width * 3 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "2"), (button) -> createMacro(50, false));
        this.addDrawableChild(b3);

        MacroButton b4 = new MacroButton(51,(this.width * 4 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "3"), (button) -> createMacro(51, false));
        this.addDrawableChild(b4);

        MacroButton b5 = new MacroButton(52,(this.width * 5 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "4"), (button) -> createMacro(52, false));
        this.addDrawableChild(b5);

        MacroButton b6 = new MacroButton(53,(this.width * 6 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "5"), (button) -> createMacro(53, false));
        this.addDrawableChild(b6);

        MacroButton b7 = new MacroButton(54,(this.width * 7 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "6"), (button) -> createMacro(54, false));
        this.addDrawableChild(b7);

        MacroButton b8 = new MacroButton(55,(this.width * 8 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "7"), (button) -> createMacro(55, false));
        this.addDrawableChild(b8);

        MacroButton b9 = new MacroButton(56,(this.width * 9 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "8"), (button) -> createMacro(56, false));
        this.addDrawableChild(b9);

        MacroButton b10 = new MacroButton(57,(this.width * 10 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "9"), (button) -> createMacro(57, false));
        this.addDrawableChild(b10);

        MacroButton b11 = new MacroButton(48,(this.width * 11 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "0"), (button) -> createMacro(48, false));
        this.addDrawableChild(b11);

        MacroButton b12 = new MacroButton(45,(this.width  * 12 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "-"), (button) -> createMacro(45, false));
        this.addDrawableChild(b12);

        MacroButton b13 = new MacroButton(61,(this.width  * 13 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, Text.of("§l" + "="), (button) -> createMacro(61, false));
        this.addDrawableChild(b13);

        MacroButton b14 = new MacroButton(259,(this.width * 14 / 16) - 16, (this.height / 2) - 80, this.width / 12, 20, Text.of("§l" + "Back"), (button) -> createMacro(259, false));
        this.addDrawableChild(b14);

        // SECOND ROW

        MacroButton b15 = new MacroButton(258,(this.width / 16) - 16, (this.height / 2) - 55, this.width / 15, 20, Text.of("§l" + "Tab"), (button) -> createMacro(258, false));
        this.addDrawableChild(b15);

        MacroButton b16 = new MacroButton(81,(this.width * 2 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "Q"), (button) -> createMacro(81, false));
        this.addDrawableChild(b16);

        MacroButton b17 = new MacroButton(87,(this.width * 3 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "W"), (button) -> createMacro(87, false));
        this.addDrawableChild(b17);

        MacroButton b18 = new MacroButton(69,(this.width * 4 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "E"), (button) -> createMacro(69, false));
        this.addDrawableChild(b18);

        MacroButton b19 = new MacroButton(82,(this.width * 5 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "R"), (button) -> createMacro(82, false));
        this.addDrawableChild(b19);

        MacroButton b20 = new MacroButton(84,(this.width * 6 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "T"), (button) -> createMacro(84, false));
        this.addDrawableChild(b20);

        MacroButton b21 = new MacroButton(89,(this.width * 7 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "Y"), (button) -> createMacro(89, false));
        this.addDrawableChild(b21);

        MacroButton b22 = new MacroButton(85,(this.width * 8 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "U"), (button) -> createMacro(85, false));
        this.addDrawableChild(b22);

        MacroButton b23 = new MacroButton(73,(this.width * 9 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "I"), (button) -> createMacro(73, false));
        this.addDrawableChild(b23);

        MacroButton b24 = new MacroButton(79,(this.width * 10 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "O"), (button) -> createMacro(79, false));
        this.addDrawableChild(b24);

        MacroButton b25 = new MacroButton(80,(this.width * 11 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "P"), (button) -> createMacro(80, false));
        this.addDrawableChild(b25);

        MacroButton b26 = new MacroButton(91,(this.width  * 12 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "["), (button) -> createMacro(91, false));
        this.addDrawableChild(b26);

        MacroButton b27 = new MacroButton(93,(this.width * 13 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "]"), (button) -> createMacro(93, false));
        this.addDrawableChild(b27);

        MacroButton b28 = new MacroButton(92,(this.width  * 14 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, Text.of("§l" + "\\"), (button) -> createMacro(92, false));
        this.addDrawableChild(b28);

        // THIRD ROW

        MacroButton b29 = new MacroButton(280,(this.width / 16) - 16, (this.height / 2) - 30, this.width / 15, 20, Text.of("§l" + "Caps"), (button) -> createMacro(280, false));
        this.addDrawableChild(b29);

        MacroButton b30 = new MacroButton(65,(this.width * 2 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + "A"), (button) -> createMacro(65, false));
        this.addDrawableChild(b30);

        MacroButton b31 = new MacroButton(83,(this.width * 3 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + "S"), (button) -> createMacro(83, false));
        this.addDrawableChild(b31);

        MacroButton b32 = new MacroButton(68,(this.width * 4 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + "D"), (button) ->{
            createMacro(68, false);
        });
        this.addDrawableChild(b32);

        MacroButton b33 = new MacroButton(70,(this.width * 5 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + "F"), (button) -> createMacro(70, false));
        this.addDrawableChild(b33);

        MacroButton b34 = new MacroButton(71,(this.width * 6 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + "G"), (button) ->{
            createMacro(71, false);
        });
        this.addDrawableChild(b34);

        MacroButton b35 = new MacroButton(72,(this.width * 7 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + "H"), (button) ->{
            createMacro(72, false);
        });
        this.addDrawableChild(b35);

        MacroButton b36 = new MacroButton(74,(this.width * 8 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + "J"), (button) ->{
            createMacro(74, false);
        });
        this.addDrawableChild(b36);

        MacroButton b37 = new MacroButton(75,(this.width * 9 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + "K"), (button) ->{
            createMacro(75, false);
        });
        this.addDrawableChild(b37);

        MacroButton b38 = new MacroButton(76,(this.width * 10 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + "L"), (button) ->{
            createMacro(76, false);
        });
        this.addDrawableChild(b38);

        MacroButton b39 = new MacroButton(59,(this.width * 11 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + ";"), (button) ->{
            createMacro(59, false);
        });
        this.addDrawableChild(b39);

        MacroButton b40 = new MacroButton(39,(this.width  * 12 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, Text.of("§l" + "'"), (button) ->{
            createMacro(39, false);
        });
        this.addDrawableChild(b40);

        MacroButton b41 = new MacroButton(257,(this.width * 13 / 16) - 16, (this.height / 2) - 30, this.width / 12, 20, Text.of("§l" + "Enter"), (button) ->{
            createMacro(257, false);
        });
        this.addDrawableChild(b41);
        // FOURTH ROW

        MacroButton b42 = new MacroButton(340,(this.width / 16) - 16, (this.height / 2) - 5, this.width / 12, 20, Text.of("§l" + "LShift"), (button) ->{
            createMacro(340, false);
        });
        this.addDrawableChild(b42);

        MacroButton b43 = new MacroButton(90,(int) (this.width * 2.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, Text.of("§l" + "Z"), (button) ->{
            createMacro(90, false);
        });
        this.addDrawableChild(b43);

        MacroButton b44 = new MacroButton(88,(int) (this.width * 3.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, Text.of("§l" + "X"), (button) ->{
            createMacro(88, false);
        });
        this.addDrawableChild(b44);

        MacroButton b45 = new MacroButton(67,(int) (this.width * 4.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, Text.of("§l" + "C"), (button) ->{
            createMacro(67, false);
        });
        this.addDrawableChild(b45);

        MacroButton b46 = new MacroButton(86,(int) (this.width * 5.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, Text.of("§l" + "V"), (button) ->{
            createMacro(86, false);
        });
        this.addDrawableChild(b46);

        MacroButton b47 = new MacroButton(66,(int) (this.width * 6.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, Text.of("§l" +  "B"), (button) ->{
            createMacro(66, false);
        });
        this.addDrawableChild(b47);

        MacroButton b48 = new MacroButton(78,(int) (this.width * 7.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, Text.of("§l" + "N"), (button) ->{
            createMacro(78, false);
        });
        this.addDrawableChild(b48);

        MacroButton b49 = new MacroButton(77,(int) (this.width * 8.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, Text.of("§l" + "M"), (button) ->{
            createMacro(77, false);
        });
        this.addDrawableChild(b49);

        MacroButton b50 = new MacroButton(44,(int) (this.width * 9.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, Text.of("§l" + ","), (button) ->{
            createMacro(44, false);
        });
        this.addDrawableChild(b50);

        MacroButton b51 = new MacroButton(46,(int) (this.width * 10.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, Text.of("§l" + "."), (button) ->{
            createMacro(46, false);
        });
        this.addDrawableChild(b51);

        MacroButton b52 = new MacroButton(47,(int) (this.width * 11.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, Text.of("§l" + "/"), (button) ->{
            createMacro(47, false);
        });
        this.addDrawableChild(b52);

        MacroButton b53 = new MacroButton(344,(int) (this.width * 12.5 / 16) - 16, (this.height / 2) - 5, this.width / 12, 20, Text.of("§l" + "RShift"), (button) ->{
            createMacro(344, false);
        });
        this.addDrawableChild(b53);

        // FIFTH ROW

        MacroButton b54 = new MacroButton(341,(this.width / 16) - 16, (this.height / 2) + 20, this.width / 12, 20, Text.of("§l" + "LCtrl"), (button) ->{
            createMacro(341, false);
        });
        this.addDrawableChild(b54);

        MacroButton b56 = new MacroButton(342, (int)(this.width * 3.5 / 16) - 8, (this.height / 2) + 20, this.width / 15, 20, Text.of("§l" + "LAlt"), (button) ->{
            createMacro(342, false);
        });
        this.addDrawableChild(b56);

        MacroButton b57 = new MacroButton(32,(this.width * 5 / 16) - 8, (this.height / 2) + 20, (int) (this.width / 3.84), 20, Text.of("§l" + "Spacebar"), (button) ->{
            createMacro(32, false);
        });
        this.addDrawableChild(b57);

        MacroButton b58 = new MacroButton(346,(int) (this.width * 10.5 / 16) - 8, (this.height / 2) + 20, this.width / 12, 20, Text.of("§l" + "RAlt"), (button) ->{
            createMacro(346, false);
        });
        this.addDrawableChild(b58);

        MacroButton b59 = new MacroButton(344,(int) (this.width * 12.5 / 16) - 8, (this.height / 2) + 20, this.width / 12, 20, Text.of("§l" + "RCtrl"), (button) ->{
            createMacro(344, false);
        });
        this.addDrawableChild(b59);

        // Arrow keys

        MacroButton b60 = new MacroButton(265, (this.width  * 2/ 16) - 8, (this.height / 2) + 45, this.width / 24, 20, Text.of("§l" + "^"), (button) ->{
            createMacro(265, false);
        });
        this.addDrawableChild(b60);

        MacroButton b61 = new MacroButton(263,(this.width / 16) - 8, (this.height / 2) + 70, this.width / 24, 20, Text.of("§l" + "<-"), (button) ->{
            createMacro(263, false);
        });
        this.addDrawableChild(b61);

        MacroButton b62 = new MacroButton(264,(this.width * 2 / 16) - 8, (this.height / 2) + 70, this.width / 24, 20, Text.of("§l" + "v"), (button) ->{
            createMacro(264, false);
        });
        this.addDrawableChild(b62);

        MacroButton b63 = new MacroButton(262,(this.width * 3 / 16) - 8, (this.height / 2) + 70, this.width / 24, 20, Text.of("§l" + "->"), (button) ->{
            createMacro(262, false);
        });
        this.addDrawableChild(b63);

        // Insert - Pg dn

        // Upper 3

        MacroButton b64 = new MacroButton(260,(this.width * 5 / 16) - 16, (this.height / 2) + 45, this.width / 15, 20, Text.of("§l" + "Ins"), (button) ->{
            createMacro(260, false);
        });
        this.addDrawableChild(b64);

        MacroButton b65 = new MacroButton(268,(int) (this.width * 6.5 / 16) - 18, (this.height / 2) + 45, (int) (this.width / 13.333), 20, Text.of("§l" + "Home"), (button) ->{
            createMacro(268, false);
        });
        this.addDrawableChild(b65);

        MacroButton b66 = new MacroButton(266,(this.width * 8 / 16) - 18, (this.height / 2) + 45, (int) (this.width / 13.333), 20, Text.of("§l" + "PgUp"), (button) ->{
            createMacro(266, false);
        });
        this.addDrawableChild(b66);

        // Lower 3

        MacroButton b67 = new MacroButton(261,(this.width * 5 / 16) - 16, (this.height / 2) + 70, this.width / 15, 20, Text.of("§l" + "Del"), (button) ->{
            createMacro(261, false);
        });
        this.addDrawableChild(b67);

        MacroButton b68 = new MacroButton(269,(int) (this.width * 6.5 / 16) - 16, (this.height / 2) + 70, this.width / 15, 20, Text.of("§l" + "End"), (button) ->{
            createMacro(269, false);
        });
        this.addDrawableChild(b68);

        MacroButton b69 = new MacroButton(267,(this.width * 8 / 16) - 18, (this.height / 2) + 70, (int) (this.width / 13.333), 20, Text.of("§l" + "PgDn"), (button) ->{
            createMacro(267, false);
        });
        this.addDrawableChild(b69);

        // Mouse Buttons

        MacroButton b70 = new MacroButton(0,(int) (this.width * 10.5 / 16) - 25, (this.height / 2) + 45, (int) (this.width / 9.6), 20, Text.of("§l" + "Mouse1"), (button) ->{
            createMacro(0, true);
        });
        this.addDrawableChild(b70);

        MacroButton b71 = new MacroButton(1,(int) (this.width * 12.5 / 16) - 25, (this.height / 2) + 45, (int) (this.width / 9.6), 20, Text.of("§l" + "Mouse2"), (button) ->{
            createMacro(1, true);
        });
        this.addDrawableChild(b71);

        MacroButton b72 = new MacroButton(2,(int) (this.width * 14.5 / 16) - 29, (this.height / 2) + 45, (int) (this.width / 8.28), 20, Text.of("§l" + "MidMouse"), (button) ->{
            createMacro(2, true);
        });
        this.addDrawableChild(b72);

        MacroButton b73 = new MacroButton(3,(int) (this.width * 10.5 / 16) - 25, (this.height / 2) + 70, (int) (this.width / 9.6), 20, Text.of("§l" + "Mouse3"), (button) ->{
            createMacro(3, true);
        });
        this.addDrawableChild(b73);

        MacroButton b74 = new MacroButton(4,(int) (this.width * 12.5 / 16) - 25, (this.height / 2) + 70, (int) (this.width / 9.6), 20, Text.of("§l" + "Mouse4"), (button) ->{
            createMacro(4, true);
        });
        this.addDrawableChild(b74);

        macroButtons.add(b1);
        macroButtons.add(b2);
        macroButtons.add(b3);
        macroButtons.add(b4);
        macroButtons.add(b5);
        macroButtons.add(b6);
        macroButtons.add(b7);
        macroButtons.add(b8);
        macroButtons.add(b9);
        macroButtons.add(b10);
        macroButtons.add(b11);
        macroButtons.add(b12);
        macroButtons.add(b13);
        macroButtons.add(b14);
        macroButtons.add(b15);
        macroButtons.add(b16);
        macroButtons.add(b17);
        macroButtons.add(b18);
        macroButtons.add(b19);
        macroButtons.add(b20);
        macroButtons.add(b21);
        macroButtons.add(b22);
        macroButtons.add(b23);
        macroButtons.add(b24);
        macroButtons.add(b25);
        macroButtons.add(b26);
        macroButtons.add(b27);
        macroButtons.add(b28);
        macroButtons.add(b29);
        macroButtons.add(b30);
        macroButtons.add(b31);
        macroButtons.add(b32);
        macroButtons.add(b33);
        macroButtons.add(b34);
        macroButtons.add(b35);
        macroButtons.add(b36);
        macroButtons.add(b37);
        macroButtons.add(b38);
        macroButtons.add(b39);
        macroButtons.add(b40);
        macroButtons.add(b41);
        macroButtons.add(b42);
        macroButtons.add(b43);
        macroButtons.add(b44);
        macroButtons.add(b45);
        macroButtons.add(b46);
        macroButtons.add(b47);
        macroButtons.add(b48);
        macroButtons.add(b49);
        macroButtons.add(b50);
        macroButtons.add(b51);
        macroButtons.add(b52);
        macroButtons.add(b53);
        macroButtons.add(b54);
        macroButtons.add(b56);
        macroButtons.add(b57);
        macroButtons.add(b58);
        macroButtons.add(b59);
        macroButtons.add(b60);
        macroButtons.add(b61);
        macroButtons.add(b62);
        macroButtons.add(b63);
        macroButtons.add(b64);
        macroButtons.add(b65);
        macroButtons.add(b66);
        macroButtons.add(b67);
        macroButtons.add(b68);
        macroButtons.add(b69);
        macroButtons.add(b70);
        macroButtons.add(b71);
        macroButtons.add(b72);
        macroButtons.add(b73);
        macroButtons.add(b74);
        ShowMain();
    }

    public void createMacro(int key, boolean mouse){
        MinecraftClient.getInstance().setScreen(new NewMacroScreen(Text.of("Macros"), key, mouse));
    }

    @Override
    public void render(MatrixStack s, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(s);
        StringBuilder rule = new StringBuilder("-");
        int dashAmount = this.width / textRenderer.getWidth(rule.toString());
        rule.append("-".repeat(Math.max(0, dashAmount - 5)));
        drawTextWithShadow(s, textRenderer, Text.of(rule.toString()), (this.width / 2) - (textRenderer.getWidth(rule.toString()) / 2), 30, 0x808080);
        super.render(s, mouseX, mouseY, partialTicks);

        for (MacroButton b : macroButtons) {
            if (mouseX >= b.x && mouseX <= b.x + b.getWidth() && mouseY >= b.y && mouseY <= b.y + b.getHeight()) {
                Optional<Macro> macro = MacroMod.getInstance().getLayerManager().getCurrentLayer().getMacro(b.getKeyCode());
                String command = macro.isPresent() ? macro.get().getCommand() : "";
                if(!command.equalsIgnoreCase("")){
                    int length = textRenderer.getWidth("Current Command: ");
                    drawCenteredText(s, textRenderer, "Current Command: ", ((this.width / 2) - (textRenderer.getWidth(command) / 2)+20), this.height - (this.height / 15), 0xffd700);
                    drawCenteredText(s, textRenderer, command, ((this.width / 2) - (textRenderer.getWidth(command) / 2)+20) + length, this.height - (this.height / 15), 0xffd700);
                }
            }
        }
    }

    public void ShowMain(){
        layerButton.setMessage(Text.of("Layer: " + MacroMod.getInstance().getLayerManager().getCurrentLayer().getName()));
        for(MacroButton b : macroButtons){
            b.visible = true;
            String rawMessage = b.getMessage().getString();
            Optional<Macro> macro = MacroMod.getInstance().getLayerManager().getCurrentLayer().getMacro(b.getKeyCode());
            String command = macro.isPresent() ? macro.get().getCommand() : "";
            if(!command.equalsIgnoreCase("")){
                MutableText text = (MutableText) Text.of(rawMessage);
                Style style = Style.EMPTY.withColor(Formatting.GOLD);
                text.setStyle(style);
                b.setMessage(text);
            }else{
                b.setMessage(Text.of(rawMessage));
            }
        }
    }
}
