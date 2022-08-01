package com.macromod.gui;

import com.macromod.MacroMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.ChatFormatting;

import java.util.*;
import java.util.List;

public final class MacroScreen extends Screen {

    //private String currentKey;
    private List<MacroButton> macroButtons;
    private EditBox text;
    private Dictionary keys;
    private MacroMod mod;
    private int lastMacro = -1;
    private Button layerButton;

    public MacroScreen(TextComponent textComponent, MacroMod mod) {
        super(textComponent);
        this.mod = mod;
        macroButtons = new ArrayList<>();
        keys = new Hashtable();
    }

    @Override
    protected void init(){
        super.init();
        Setup();
    }

    public void Setup(){
        this.addRenderableWidget(new Button((this.width / 2), 8, 124, 20, new TextComponent("Remove Layer"), (button) ->{
            mod.layerManager.deleteLayer();
            mod.Save();
            ShowMain();
        }));

        this.addRenderableWidget(new Button((this.width / 2) + 140, 8, 60, 20, new TextComponent("Done"), (button) ->{
            this.onClose();
        }));

        layerButton = new Button((this.width / 4) - 92, 8, 124, 20, new TextComponent("Layer: " + mod.layerManager.getCurrentLayer().getLayerName()), (button) ->{
            mod.layerManager.nextLayer();
            layerButton.setMessage(new TextComponent("Layer: " + mod.layerManager.getCurrentLayer().getLayerName()));
            ShowMain();
        });
        this.addRenderableWidget(layerButton);

        this.addRenderableWidget(new Button((this.width / 4) + 37, 8, 62, 20, new TextComponent("New Layer"), (button) ->{
            Minecraft.getInstance().setScreen(new NewLayerScreen(new TextComponent("Macros"), mod));
        }));

        // FIRST ROW

        MacroButton b1 = new MacroButton(96,(this.width / 16) - 16, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "`"), (button) ->{
            createMacro(96, false);
        });
        this.addRenderableWidget(b1);

        MacroButton b2 = new MacroButton(49,(this.width * 2 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "1"), (button) ->{
            createMacro(49, false);
        });
        this.addRenderableWidget(b2);

        MacroButton b3 = new MacroButton(50,(this.width * 3 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "2"), (button) ->{
            createMacro(50, false);
        });
        this.addRenderableWidget(b3);

        MacroButton b4 = new MacroButton(51,(this.width * 4 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "3"), (button) ->{
            createMacro(51, false);
        });
        this.addRenderableWidget(b4);

        MacroButton b5 = new MacroButton(52,(this.width * 5 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "4"), (button) ->{
            createMacro(52, false);
        });
        this.addRenderableWidget(b5);

        MacroButton b6 = new MacroButton(53,(this.width * 6 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "5"), (button) ->{
            createMacro(53, false);
        });
        this.addRenderableWidget(b6);

        MacroButton b7 = new MacroButton(54,(this.width * 7 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "6"), (button) ->{
            createMacro(54, false);
        });
        this.addRenderableWidget(b7);

        MacroButton b8 = new MacroButton(55,(this.width * 8 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "7"), (button) ->{
            createMacro(55, false);
        });
        this.addRenderableWidget(b8);

        MacroButton b9 = new MacroButton(56,(this.width * 9 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "8"), (button) ->{
            createMacro(56, false);
        });
        this.addRenderableWidget(b9);

        MacroButton b10 = new MacroButton(57,(this.width * 10 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "9"), (button) ->{
            createMacro(57, false);
        });
        this.addRenderableWidget(b10);

        MacroButton b11 = new MacroButton(48,(this.width * 11 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "0"), (button) ->{
            createMacro(48, false);
        });
        this.addRenderableWidget(b11);

        MacroButton b12 = new MacroButton(45,(this.width  * 12 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "-"), (button) ->{
            createMacro(45, false);
        });
        this.addRenderableWidget(b12);

        MacroButton b13 = new MacroButton(61,(this.width  * 13 / 16) - 8, (this.height / 2) - 80, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "="), (button) ->{
            createMacro(61, false);
        });
        this.addRenderableWidget(b13);

        MacroButton b14 = new MacroButton(259,(this.width * 14 / 16) - 16, (this.height / 2) - 80, this.width / 12, 20, new TextComponent(ChatFormatting.BOLD + "Back"), (button) ->{
            createMacro(259, false);
        });
        this.addRenderableWidget(b14);

        // SECOND ROW

        MacroButton b15 = new MacroButton(258,(this.width / 16) - 16, (this.height / 2) - 55, this.width / 15, 20, new TextComponent(ChatFormatting.BOLD + "Tab"), (button) ->{
            createMacro(258, false);
        });
        this.addRenderableWidget(b15);

        MacroButton b16 = new MacroButton(81,(this.width * 2 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "Q"), (button) ->{
            createMacro(81, false);
        });
        this.addRenderableWidget(b16);

        MacroButton b17 = new MacroButton(87,(this.width * 3 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "W"), (button) ->{
            createMacro(87, false);
        });
        this.addRenderableWidget(b17);

        MacroButton b18 = new MacroButton(69,(this.width * 4 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "E"), (button) ->{
            createMacro(69, false);
        });
        this.addRenderableWidget(b18);

        MacroButton b19 = new MacroButton(82,(this.width * 5 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "R"), (button) ->{
            createMacro(82, false);
        });
        this.addRenderableWidget(b19);

        MacroButton b20 = new MacroButton(84,(this.width * 6 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "T"), (button) ->{
            createMacro(84, false);
        });
        this.addRenderableWidget(b20);

        MacroButton b21 = new MacroButton(89,(this.width * 7 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "Y"), (button) ->{
            createMacro(89, false);
        });
        this.addRenderableWidget(b21);

        MacroButton b22 = new MacroButton(85,(this.width * 8 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "U"), (button) ->{
            createMacro(85, false);
        });
        this.addRenderableWidget(b22);

        MacroButton b23 = new MacroButton(73,(this.width * 9 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "I"), (button) ->{
            createMacro(73, false);
        });
        this.addRenderableWidget(b23);

        MacroButton b24 = new MacroButton(79,(this.width * 10 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "O"), (button) ->{
            createMacro(79, false);
        });
        this.addRenderableWidget(b24);

        MacroButton b25 = new MacroButton(80,(this.width * 11 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "P"), (button) ->{
            createMacro(80, false);
        });
        this.addRenderableWidget(b25);

        MacroButton b26 = new MacroButton(91,(this.width  * 12 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "["), (button) ->{
            createMacro(91, false);
        });
        this.addRenderableWidget(b26);

        MacroButton b27 = new MacroButton(93,(this.width * 13 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "]"), (button) ->{
            createMacro(93, false);
        });
        this.addRenderableWidget(b27);

        MacroButton b28 = new MacroButton(92,(this.width  * 14 / 16) - 8, (this.height / 2) - 55, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "\\"), (button) ->{
            createMacro(92, false);
        });
        this.addRenderableWidget(b28);

        // THIRD ROW

        MacroButton b29 = new MacroButton(280,(this.width / 16) - 16, (this.height / 2) - 30, this.width / 15, 20, new TextComponent(ChatFormatting.BOLD + "Caps"), (button) ->{
            createMacro(280, false);
        });
        this.addRenderableWidget(b29);

        MacroButton b30 = new MacroButton(65,(this.width * 2 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "A"), (button) ->{
            createMacro(65, false);
        });
        this.addRenderableWidget(b30);

        MacroButton b31 = new MacroButton(83,(this.width * 3 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "S"), (button) ->{
            createMacro(83, false);
        });
        this.addRenderableWidget(b31);

        MacroButton b32 = new MacroButton(68,(this.width * 4 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "D"), (button) ->{
            createMacro(68, false);
        });
        this.addRenderableWidget(b32);

        MacroButton b33 = new MacroButton(70,(this.width * 5 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "F"), (button) ->{
            createMacro(70, false);
        });
        this.addRenderableWidget(b33);

        MacroButton b34 = new MacroButton(71,(this.width * 6 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "G"), (button) ->{
            createMacro(71, false);
        });
        this.addRenderableWidget(b34);

        MacroButton b35 = new MacroButton(72,(this.width * 7 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "H"), (button) ->{
            createMacro(72, false);
        });
        this.addRenderableWidget(b35);

        MacroButton b36 = new MacroButton(74,(this.width * 8 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "J"), (button) ->{
            createMacro(74, false);
        });
        this.addRenderableWidget(b36);

        MacroButton b37 = new MacroButton(75,(this.width * 9 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "K"), (button) ->{
            createMacro(75, false);
        });
        this.addRenderableWidget(b37);

        MacroButton b38 = new MacroButton(76,(this.width * 10 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "L"), (button) ->{
            createMacro(76, false);
        });
        this.addRenderableWidget(b38);

        MacroButton b39 = new MacroButton(59,(this.width * 11 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + ";"), (button) ->{
            createMacro(59, false);
        });
        this.addRenderableWidget(b39);

        MacroButton b40 = new MacroButton(39,(this.width  * 12 / 16) - 8, (this.height / 2) - 30, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "'"), (button) ->{
            createMacro(39, false);
        });
        this.addRenderableWidget(b40);

        MacroButton b41 = new MacroButton(257,(this.width * 13 / 16) - 16, (this.height / 2) - 30, this.width / 12, 20, new TextComponent(ChatFormatting.BOLD + "Enter"), (button) ->{
            createMacro(257, false);
        });
        this.addRenderableWidget(b41);
        // FOURTH ROW

        MacroButton b42 = new MacroButton(340,(this.width / 16) - 16, (this.height / 2) - 5, this.width / 12, 20, new TextComponent(ChatFormatting.BOLD + "LShift"), (button) ->{
            createMacro(340, false);
        });
        this.addRenderableWidget(b42);

        MacroButton b43 = new MacroButton(90,(int) (this.width * 2.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "Z"), (button) ->{
            createMacro(90, false);
        });
        this.addRenderableWidget(b43);

        MacroButton b44 = new MacroButton(88,(int) (this.width * 3.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "X"), (button) ->{
            createMacro(88, false);
        });
        this.addRenderableWidget(b44);

        MacroButton b45 = new MacroButton(67,(int) (this.width * 4.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "C"), (button) ->{
            createMacro(67, false);
        });
        this.addRenderableWidget(b45);

        MacroButton b46 = new MacroButton(86,(int) (this.width * 5.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "V"), (button) ->{
            createMacro(86, false);
        });
        this.addRenderableWidget(b46);

        MacroButton b47 = new MacroButton(66,(int) (this.width * 6.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD +  "B"), (button) ->{
            createMacro(66, false);
        });
        this.addRenderableWidget(b47);

        MacroButton b48 = new MacroButton(78,(int) (this.width * 7.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "N"), (button) ->{
            createMacro(78, false);
        });
        this.addRenderableWidget(b48);

        MacroButton b49 = new MacroButton(77,(int) (this.width * 8.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "M"), (button) ->{
            createMacro(77, false);
        });
        this.addRenderableWidget(b49);

        MacroButton b50 = new MacroButton(44,(int) (this.width * 9.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + ","), (button) ->{
            createMacro(44, false);
        });
        this.addRenderableWidget(b50);

        MacroButton b51 = new MacroButton(46,(int) (this.width * 10.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "."), (button) ->{
            createMacro(46, false);
        });
        this.addRenderableWidget(b51);

        MacroButton b52 = new MacroButton(47,(int) (this.width * 11.5 / 16) - 8, (this.height / 2) - 5, this.width / 30, 20, new TextComponent(ChatFormatting.BOLD + "/"), (button) ->{
            createMacro(47, false);
        });
        this.addRenderableWidget(b52);

        MacroButton b53 = new MacroButton(344,(int) (this.width * 12.5 / 16) - 16, (this.height / 2) - 5, this.width / 12, 20, new TextComponent(ChatFormatting.BOLD + "RShift"), (button) ->{
            createMacro(344, false);
        });
        this.addRenderableWidget(b53);

        // FIFTH ROW

        MacroButton b54 = new MacroButton(341,(this.width / 16) - 16, (this.height / 2) + 20, this.width / 12, 20, new TextComponent(ChatFormatting.BOLD + "LCtrl"), (button) ->{
            createMacro(341, false);
        });
        this.addRenderableWidget(b54);

        MacroButton b56 = new MacroButton(342,(int) (this.width * 3.5 / 16) - 8, (this.height / 2) + 20, this.width / 15, 20, new TextComponent(ChatFormatting.BOLD + "LAlt"), (button) ->{
            createMacro(342, false);
        });
        this.addRenderableWidget(b56);

        MacroButton b57 = new MacroButton(32,(int) (this.width * 5 / 16) - 8, (this.height / 2) + 20, (int) (this.width / 3.84), 20, new TextComponent(ChatFormatting.BOLD + "Spacebar"), (button) ->{
            createMacro(32, false);
        });
        this.addRenderableWidget(b57);

        MacroButton b58 = new MacroButton(346,(int) (this.width * 10.5 / 16) - 8, (this.height / 2) + 20, this.width / 12, 20, new TextComponent(ChatFormatting.BOLD + "RAlt"), (button) ->{
            createMacro(346, false);
        });
        this.addRenderableWidget(b58);

        MacroButton b59 = new MacroButton(344,(int) (this.width * 12.5 / 16) - 8, (this.height / 2) + 20, this.width / 12, 20, new TextComponent(ChatFormatting.BOLD + "RCtrl"), (button) ->{
            createMacro(344, false);
        });
        this.addRenderableWidget(b59);

        // Arrow keys

        MacroButton b60 = new MacroButton(265,(int) (this.width  * 2/ 16) - 8, (this.height / 2) + 45, this.width / 24, 20, new TextComponent(ChatFormatting.BOLD + "^"), (button) ->{
            createMacro(265, false);
        });
        this.addRenderableWidget(b60);

        MacroButton b61 = new MacroButton(263,(int) (this.width / 16) - 8, (this.height / 2) + 70, this.width / 24, 20, new TextComponent(ChatFormatting.BOLD + "<-"), (button) ->{
            createMacro(263, false);
        });
        this.addRenderableWidget(b61);

        MacroButton b62 = new MacroButton(264,(int) (this.width * 2 / 16) - 8, (this.height / 2) + 70, this.width / 24, 20, new TextComponent(ChatFormatting.BOLD + "v"), (button) ->{
            createMacro(264, false);
        });
        this.addRenderableWidget(b62);

        MacroButton b63 = new MacroButton(262,(int) (this.width * 3 / 16) - 8, (this.height / 2) + 70, this.width / 24, 20, new TextComponent(ChatFormatting.BOLD + "->"), (button) ->{
            createMacro(262, false);
        });
        this.addRenderableWidget(b63);

        // Insert - Pg dn

        // Upper 3

        MacroButton b64 = new MacroButton(260,(int) (this.width * 5 / 16) - 16, (this.height / 2) + 45, this.width / 15, 20, new TextComponent(ChatFormatting.BOLD + "Ins"), (button) ->{
            createMacro(260, false);
        });
        this.addRenderableWidget(b64);

        MacroButton b65 = new MacroButton(268,(int) (this.width * 6.5 / 16) - 18, (this.height / 2) + 45, (int) (this.width / 13.333), 20, new TextComponent(ChatFormatting.BOLD + "Home"), (button) ->{
            createMacro(268, false);
        });
        this.addRenderableWidget(b65);

        MacroButton b66 = new MacroButton(266,(int) (this.width * 8 / 16) - 18, (this.height / 2) + 45, (int) (this.width / 13.333), 20, new TextComponent(ChatFormatting.BOLD + "PgUp"), (button) ->{
            createMacro(266, false);
        });
        this.addRenderableWidget(b66);

        // Lower 3

        MacroButton b67 = new MacroButton(261,(int) (this.width * 5 / 16) - 16, (this.height / 2) + 70, this.width / 15, 20, new TextComponent(ChatFormatting.BOLD + "Del"), (button) ->{
            createMacro(261, false);
        });
        this.addRenderableWidget(b67);

        MacroButton b68 = new MacroButton(269,(int) (this.width * 6.5 / 16) - 16, (this.height / 2) + 70, this.width / 15, 20, new TextComponent(ChatFormatting.BOLD + "End"), (button) ->{
            createMacro(269, false);
        });
        this.addRenderableWidget(b68);

        MacroButton b69 = new MacroButton(267,(int) (this.width * 8 / 16) - 18, (this.height / 2) + 70, (int) (this.width / 13.333), 20, new TextComponent(ChatFormatting.BOLD + "PgDn"), (button) ->{
            createMacro(267, false);
        });
        this.addRenderableWidget(b69);

        // Mouse Buttons

        MacroButton b70 = new MacroButton(0,(int) (this.width * 10.5 / 16) - 25, (this.height / 2) + 45, (int) (this.width / 9.6), 20, new TextComponent(ChatFormatting.BOLD + "Mouse1"), (button) ->{
            createMacro(0, true);
        });
        this.addRenderableWidget(b70);

        MacroButton b71 = new MacroButton(1,(int) (this.width * 12.5 / 16) - 25, (this.height / 2) + 45, (int) (this.width / 9.6), 20, new TextComponent(ChatFormatting.BOLD + "Mouse2"), (button) ->{
            createMacro(1, true);
        });
        this.addRenderableWidget(b71);

        MacroButton b72 = new MacroButton(2,(int) (this.width * 14.5 / 16) - 29, (this.height / 2) + 45, (int) (this.width / 8.28), 20, new TextComponent(ChatFormatting.BOLD + "MidMouse"), (button) ->{
            createMacro(2, true);
        });
        this.addRenderableWidget(b72);

        MacroButton b73 = new MacroButton(3,(int) (this.width * 10.5 / 16) - 25, (this.height / 2) + 70, (int) (this.width / 9.6), 20, new TextComponent(ChatFormatting.BOLD + "Mouse3"), (button) ->{
            createMacro(3, true);
        });
        this.addRenderableWidget(b73);

        final String buttonType74 = "Mouse4";
        MacroButton b74 = new MacroButton(4,(int) (this.width * 12.5 / 16) - 25, (this.height / 2) + 70, (int) (this.width / 9.6), 20, new TextComponent(ChatFormatting.BOLD + "Mouse4"), (button) ->{
            createMacro(4, true);
        });
        this.addRenderableWidget(b74);

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
        Minecraft.getInstance().setScreen(new NewMacroScreen(new TextComponent("Macros"), mod, key));
    }

    @Override
    public void render(PoseStack s, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(0);
        String rule = "-";
        int dashAmount = this.width / font.width(rule);
        for (int i = 0; i < dashAmount - 5; i++) {
            rule += "-";
        }
        drawString(s, font, ChatFormatting.GRAY + rule, (this.width / 2) - (font.width(rule) / 2), 30, 30);
        super.render(s, mouseX, mouseY, partialTicks);

        for (MacroButton b : macroButtons) {
            if (mouseX >= b.x && mouseX <= b.x + b.getWidth() && mouseY >= b.y && mouseY <= b.y + b.getHeight()) {
                String command = mod.layerManager.getCurrentLayer().getCommand(b.getKeyCode());
                if(!command.equalsIgnoreCase("")){
                    drawCenteredString(s, font, ChatFormatting.WHITE + "Current Command: " +ChatFormatting.GOLD + "" + ChatFormatting.BOLD + "" + command, ((this.width / 2) - (font.width(command) / 2)+20), this.height - (this.height / 15), 15);
                }
            }
        }
    }

    public void ShowMain(){
        layerButton.setMessage(new TextComponent("Layer: " + mod.layerManager.getCurrentLayer().getLayerName()));
        for(MacroButton b : macroButtons){
            b.visible = true;
            String rawMessage = ChatFormatting.stripFormatting(b.getMessage().getContents());

            String command = mod.layerManager.getCurrentLayer().getCommand(b.getKeyCode());
            if(!command.equalsIgnoreCase("")){
                b.setMessage(new TextComponent(ChatFormatting.GOLD + rawMessage));
            }else{
                b.setMessage(new TextComponent(rawMessage));
            }
        }
    }
}
