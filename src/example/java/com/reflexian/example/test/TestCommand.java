package com.reflexian.example.test;

import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.command.CommandSender;

@CommandInfo(name="test",description = "2")
public class TestCommand extends Command {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("pog!");
        return true;
    }
}
