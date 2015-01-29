package net.acomputerdog.ircbot.core.command;

import com.sorcix.sirc.structure.Channel;
import com.sorcix.sirc.structure.User;
import com.sorcix.sirc.util.Chattable;
import net.acomputerdog.ircbot.command.Command;
import net.acomputerdog.ircbot.command.util.CommandLine;
import net.acomputerdog.ircbot.main.IrcBot;
import net.acomputerdog.ircbot.plugin.IrcPlugin;
import net.acomputerdog.ircbot.plugin.PluginList;

public class CommandPlugins extends Command {

    public CommandPlugins(IrcBot bot) {
        super(bot, "plugins", "plugins", "plugin");
    }

    @Override
    public String getDescription() {
        return "Lists loaded plugins.";
    }

    @Override
    public boolean processCommand(Channel channel, User user, Chattable chattable, CommandLine commandLine) {
        PluginList plugins = bot.getPluginList();
        chattable.send(plugins.size() + " loaded plugins:");
        for (IrcPlugin plugin : plugins.getPlugins()) {
            chattable.send(plugin.getName() + ' ' + plugin.getVersion() + " (" + plugin.getID() + " / " + plugin.getClass().getName() + ')');
        }
        return true;
    }
}
