package com.reflexian.example;

import com.reflexian.rapi.RAPI;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class ExampleMain extends JavaPlugin {

    @Getter
    private RAPI rapi;

    @Override
    public void onEnable() {
        rapi = new RAPI(this);
        rapi.init();
    }

    @Override
    public void onDisable() {

    }
}
