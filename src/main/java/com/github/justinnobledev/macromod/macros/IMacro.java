package com.github.justinnobledev.macromod.macros;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;

public interface IMacro {

    void onTick(long hndl, ClientPlayerEntity player, long currentTime);
    void execute(ClientPlayerEntity player);
    boolean isKeyPressed(long hndl, int key);
    String getCommand();
    int getKey();
    int getModifier();

    InputUtil.Type getType();

    void setCommand(String cmd);
}
