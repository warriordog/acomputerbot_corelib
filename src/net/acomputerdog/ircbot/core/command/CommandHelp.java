package net.acomputerdog.ircbot.core.command;

import com.sorcix.sirc.structure.Channel;
import com.sorcix.sirc.structure.User;
import com.sorcix.sirc.util.Chattable;
import net.acomputerdog.ircbot.command.Command;
import net.acomputerdog.ircbot.command.util.CommandLine;
import net.acomputerdog.ircbot.config.Config;
import net.acomputerdog.ircbot.main.IrcBot;

public class CommandHelp extends Command {
    public CommandHelp(IrcBot bot) {
        super(bot, "Help", "help", "hlp", "?");
    }

    @Override
    public int getMaxArgs() {
        return 1;
    }

    @Override
    public String getHelpString() {
        return Config.COMMAND_PREFIX + "help [command]";
    }

    @Override
    public String getDescription() {
        return "Gets help on AcomputerBot commands.";
    }

    @Override
    public boolean processCommand(Channel channel, User sender, Chattable target, CommandLine command) {
        if (command.hasArgs()) {
            Command cmd = bot.getCommandManager().getCommandMap().get(command.args.toLowerCase());
            if (cmd != null) {
                target.send(colorGreen("Found command: " + cmd.getName()));
                target.send(colorGreen("  Usage: \"" + cmd.getHelpString() + "\""));
                target.send(colorGreen("  Aliases: " + getAliases(cmd)));
                target.send(colorGreen("  Description: " + cmd.getDescription()));
                target.send(colorGreen("  Arguments: " + getArguments(cmd)));
                target.send(colorGreen("  Permissions: " + getPermissions(cmd, channel, sender)));
                return true;
            } else {
                target.send(colorRed("Could not find command: \"" + command.args + "\""));
                return false;
            }
        } else {
            target.send(colorGreen("Registered commands: (use \"" + getHelpString() + "\" to view details)"));
            StringBuilder builder = new StringBuilder(16);
            int count = 0;
            for (String cmd : bot.getCommandManager().getCommandNameMap().keySet()) {
                Command cmmd = bot.getCommandManager().getCommandNameMap().get(cmd);
                if ((!cmmd.requiresAdmin() || (bot.getAuth().isAuthenticated(sender) || (cmmd.canOpOverride() && sender.hasOperator()))) &&
                        ((channel != null && cmmd.allowedInChannel(channel, sender)) || (channel == null && cmmd.allowedInPM(sender)))) {
                    if (count > 0) {
                        builder.append(", ");
                    } else {
                        builder.append("  ");
                    }
                    builder.append(cmd);
                    count++;
                    if (count >= 8) {
                        count = 0;
                        target.send(colorGreen(builder.toString()));
                        builder = new StringBuilder(16);
                    }
                }
            }
            String lastLine = builder.toString();
            if (!lastLine.isEmpty()) {
                target.send(colorGreen(lastLine));
            }

            return true;
        }
    }

    private String getAliases(Command cmd) {
        String[] cmds = cmd.getCommands();
        if (cmds.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(cmds.length);
        for (int index = 0; index < cmds.length; index++) {
            builder.append(cmds[index]);
            if (index < cmds.length - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private String getArguments(Command cmd) {
        int min = cmd.getMinArgs();
        int max = cmd.getMaxArgs();
        if (min == max) {
            return String.valueOf(min);
        }
        return min + " - " + max;
    }

    private String getPermissions(Command cmd, Channel channel, User sender) {
        String perms;
        if (cmd.requiresAdmin()) {
            if (cmd.canOpOverride()) {
                perms = "admin/op ";
            } else {
                perms = "admin ";
            }
        } else {
            perms = "none ";
        }
        if (channel == null) {
            perms += cmd.allowedInPM(sender) ? "(allowed here)" : "(blocked here)";
        } else {
            perms += cmd.allowedInChannel(channel, sender) ? "(allowed here)" : "(blocked here)";
        }
        return perms;
    }
}
