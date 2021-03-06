package net.acomputerdog.ircbot.core.command;

import com.sorcix.sirc.structure.Channel;
import com.sorcix.sirc.structure.User;
import com.sorcix.sirc.util.Chattable;
import net.acomputerdog.ircbot.command.Command;
import net.acomputerdog.ircbot.command.util.CommandLine;
import net.acomputerdog.ircbot.config.Config;
import net.acomputerdog.ircbot.main.IrcBot;

public class CommandBlacklist extends Command {
    public CommandBlacklist(IrcBot bot) {
        super(bot, "Blacklist", "blacklist", "addblacklist", "add_blacklist", "add-blacklist");
    }

    @Override
    public String getHelpString() {
        return Config.COMMAND_PREFIX + "blacklist <user>";
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public boolean requiresAdmin() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Changes a player's blacklist status.";
    }

    @Override
    public boolean processCommand(Channel channel, User sender, Chattable target, CommandLine command) {
        User user = bot.getConnection().createUser(command.args);
        if (bot.getBlacklist().isBlacklisted(user)) {
            bot.getBlacklist().removeBlacklisted(user);
            target.send("User " + user.getNick() + " has been un-blacklisted.");
        } else {
            bot.getBlacklist().addBlacklisted(user);
            target.send("User " + user.getNick() + " has been blacklisted.");
        }
        return true;
    }
}
