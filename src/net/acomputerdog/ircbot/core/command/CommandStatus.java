package net.acomputerdog.ircbot.core.command;

import com.sorcix.sirc.structure.Channel;
import com.sorcix.sirc.structure.User;
import com.sorcix.sirc.util.Chattable;
import net.acomputerdog.ircbot.command.Command;
import net.acomputerdog.ircbot.command.util.CommandLine;
import net.acomputerdog.ircbot.main.IrcBot;
import net.acomputerdog.ircbot.plugin.IrcPlugin;
import net.acomputerdog.ircbot.plugin.PluginList;

public class CommandStatus extends Command {
    public CommandStatus(IrcBot bot) {
        super(bot, "Status", "status", "stats");
    }

    @Override
    public String getDescription() {
        return "Gets the internal status of AcomputerBot.";
    }

    @Override
    public boolean processCommand(Channel channel, User sender, Chattable target, CommandLine command) {
        target.send(bot.getVersionString() + " status:");
        target.send("  " + bot.getCommandManager().getCommandNameMap().size() + " loaded commands with " + bot.getCommandManager().getCommandMap().size() + " registered aliases.");
        target.send("  Memory: " + (Runtime.getRuntime().freeMemory() / 1000000) + "mb used / " + (Runtime.getRuntime().totalMemory() / 1000000) + "mb allocated / " + (Runtime.getRuntime().maxMemory() / 1000000) + "mb available.");
        PluginList plugins = bot.getPluginList();
        StringBuilder builder = new StringBuilder(plugins.size() * 2);
        int count = 0;
        for (IrcPlugin plugin : plugins.getPlugins()) {
            if (count > 0) {
                builder.append(", ");
            }
            builder.append(plugin.getID());
            count++;
        }
        target.send("  Plugins (" + plugins.size()  + "): " + builder.toString());
        return true;
    }
}