package com.reflexian.rapi.api.command;

import com.reflexian.rapi.api.annotation.SubCommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command extends org.bukkit.command.Command {

    final List<SubCommand> subCommands = new ArrayList<>();

    public Command() {
        super("");
    }

    private Command(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    public void setSubCommands(SubCommand... subCommandInfos) {
        this.subCommands.clear();
        this.subCommands.addAll(Arrays.asList(subCommandInfos));
    }

    public void addSubCommands(SubCommand... subCommandInfos) {
        this.subCommands.addAll(Arrays.asList(subCommandInfos));
    }

    public abstract boolean execute(CommandSender sender, String[] args);

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length > 0) {
            for (SubCommand subCommand : subCommands) {
                SubCommandInfo info = subCommand.getClass().getDeclaredAnnotation(SubCommandInfo.class);
                if (info.name().equalsIgnoreCase(args[0]) || Arrays.asList(info.aliases()).contains(args[0].toLowerCase())) {
                    subCommand.execute(sender, args);
                    return true;
                }
            }
        }
        return execute(sender, args);
    }

}