package net.acomputerdog.ircbot.core.command;

import com.sorcix.sirc.structure.Channel;
import com.sorcix.sirc.structure.User;
import com.sorcix.sirc.util.Chattable;
import net.acomputerdog.ircbot.command.Command;
import net.acomputerdog.ircbot.command.util.CommandLine;
import net.acomputerdog.ircbot.main.IrcBot;

public class CommandLogout extends Command {
    public CommandLogout(IrcBot bot) {
        super(bot, "Logout", "logout", "deauth", "de-auth", "de_auth");
    }

    @Override
    public String getDescription() {
        return "Log out as admin.";
    }

    @Override
    public boolean processCommand(Channel channel, User sender, Chattable target, CommandLine command) {
        if (bot.getAuth().deauthenticate(sender)) {
            target.send("You have been successfully logged out!");
            return true;
        } else {
            target.send(colorRed("You are not logged in!"));
            return false;
        }
    }
}
