package com.github.justinnobledev.macromod.layers;

import com.github.justinnobledev.macromod.macros.IMacro;
import com.github.justinnobledev.macromod.macros.Macro;
import net.minecraft.client.util.InputUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Layer {
    private List<Macro> macros;
    private String name;
    public Layer(String name){
        this.name = name;
        this.macros = new ArrayList<>();
    }

    public String getName(){return this.name;}

    public boolean addMacro(String cmd, int key, InputUtil.Type type, int mod){
        return this.macros.add(new Macro(cmd, key, type, mod));
    }

    public boolean addMacro(String cmd, int key, InputUtil.Type type){
        Optional<Macro> macro = getMacro(key);
        if(macro.isPresent()) {
            macro.get().setCommand(cmd);
            return true;
        }else return this.macros.add(new Macro(cmd, key, type));
    }

    public Optional<Macro> getMacro(int key){
        return macros.stream().filter(macro -> macro.getKey() == key).findFirst();
    }

    public boolean removeMacro(int index){
        if(index >= this.macros.size() || index < 0)
            return false;
        return this.macros.remove(index) != null;
    }

    public List<Macro> getMacros(){return this.macros;}

    public boolean removeMacro(Macro macro){
        return this.macros.remove(macro);
    }

    public boolean removeMacro(String cmd){
        boolean didRemove = false;
        for(IMacro macro : this.macros){
            if(macro.getCommand().equalsIgnoreCase(cmd)) {
                return this.macros.remove(macro);
            }
        }
        return false;
    }

    public boolean removeMacro(int key, InputUtil.Type type){
        for(IMacro macro : this.macros){
            if(macro.getType() != type)
                continue;
            if(macro.getKey() == key && macro.getModifier() == -1) {
                return this.macros.remove(macro);
            }
        }
        return false;
    }

    public boolean removeMacro(int key, int mod, InputUtil.Type type){
        for(IMacro macro : this.macros){
            if(macro.getType() != type)
                continue;
            if(macro.getKey() == key && macro.getModifier() == mod) {
                return this.macros.remove(macro);
            }
        }
        return false;
    }
}
