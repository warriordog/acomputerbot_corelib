package net.acomputerdog.ircbot.core.main;

import net.acomputerdog.ircbot.command.CommandManager;
import net.acomputerdog.ircbot.core.command.*;
import net.acomputerdog.ircbot.main.IrcBot;
import net.acomputerdog.ircbot.plugin.IrcPlugin;

public class IrcPluginCoreLib implements IrcPlugin {

    private IrcBot bot;

    @Override
    public IrcBot getIrcBot() {
        return bot;
    }

    @Override
    public String getID() {
        return "corelib";
    }

    @Override
    public String getName() {
        return "Core Lib";
    }

    @Override
    public void onLoad(IrcBot ircBot) {
        bot = ircBot;
        try {
            CommandManager manager = bot.getCommandManager();
            manager.registerCommand(new CommandHelp(bot));
            manager.registerCommand(new CommandInfo(bot));
            manager.registerCommand(new CommandStop(bot));
            manager.registerCommand(new CommandJoin(bot));
            manager.registerCommand(new CommandLeave(bot));
            manager.registerCommand(new CommandStatus(bot));
            manager.registerCommand(new CommandChannels(bot));
            manager.registerCommand(new CommandLogin(bot));
            manager.registerCommand(new CommandLogout(bot));
            manager.registerCommand(new CommandAdmins(bot));
            manager.registerCommand(new CommandAliases(bot));
            manager.registerCommand(new CommandWhitelist(bot));
            manager.registerCommand(new CommandBlacklist(bot));
            manager.registerCommand(new CommandToggleWhitelist(bot));
            manager.registerCommand(new CommandToggleBlacklist(bot));
            manager.registerCommand(new CommandListWhitelist(bot));
            manager.registerCommand(new CommandListBlacklist(bot));
            manager.registerCommand(new CommandReload(bot));
            manager.registerCommand(new CommandPlugins(bot));
        } catch (Exception e) {
            getLogger().logError("Exception registering core commands!", e);
        }
        getLogger().logInfo("Loaded core commands.");
    }

    @Override
    public void onUnload() {
        getLogger().logInfo("Unloaded core commands.");
    }
}
