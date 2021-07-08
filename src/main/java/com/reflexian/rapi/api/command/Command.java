package com.reflexian.rapi.api.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.List;

public abstract class Command extends org.bukkit.command.Command {

    public Command(){
        super("");
    }
    private Command(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    public abstract boolean execute(CommandSender sender, String[] args);

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return execute(sender,args);
    }
}
