package com.github.justinnobledev.macromod.macros;

import com.github.justinnobledev.macromod.MacroMod;
import com.github.justinnobledev.macromod.placeholder.Placeholder;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Macro implements IMacro {
    private int key, mod;
    private String cmd;
    private InputUtil.Type type;
    private transient boolean isPressed = false;
    public Macro(String cmd, int key, InputUtil.Type type){
        this.cmd = cmd;
        this.key = key;
        this.type = type;
        this.mod = -1;
        MacroMod.LOGGER.info(String.format("Registering macro with key %d and command %s", key, cmd));
    }
    public Macro(String cmd, int key, InputUtil.Type type, int mod){
        this(cmd, key, type);
        this.mod = mod;
    }

    @Override
    public void onTick(long hndl, ClientPlayerEntity player, long currentTime) {
        if(this.isKeyPressed(hndl, this.key) && (this.mod == -1 || this.isKeyPressed(hndl, this.mod))){
            if(this.isPressed)
                return;
            this.execute(player);
            this.isPressed = true;
        }else
            this.isPressed = false;
    }

    @Override
    public void execute(ClientPlayerEntity player) {
        for(String command : this.cmd.split("\\|"))
            player.sendChatMessage(Placeholder.parsePlaceholders(command.trim()));
    }

    @Override
    public boolean isKeyPressed(long hndl, int key) {
        MacroMod.LOGGER.info(String.format("Looking up key %d with type %s", key, type.toString()));
        if(type == InputUtil.Type.MOUSE){
            return GLFW.glfwGetMouseButton(hndl, key) == 1;
        }else {
            return GLFW.glfwGetKey(hndl, key) == 1;
        }
    }

    @Override
    public String getCommand() {
        return this.cmd;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public int getModifier() {
        return this.mod;
    }

    @Override
    public InputUtil.Type getType() {
        return this.type;
    }

    @Override
    public void setCommand(String cmd) {
        this.cmd = cmd;
    }
}
