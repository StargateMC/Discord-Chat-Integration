package de.erdbeerbaerlp.dcintegration;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;


/**
 * Class containing all config entries
 */
@Config(modid = DiscordIntegration.MODID, name = "Discord-Integration")
public class Configuration {

    @Name("General Config")
    @Comment("General bot Configuration")
    public static category_general GENERAL = new category_general();
    @Name("Advanced")
    @Comment("Configure Advanced features like moving specific message types to different channels")
    public static category_advanced ADVANCED = new category_advanced();
    @Name("Linking")
    public static category_linking LINKING = new category_linking();
    @Name("Webhook")
    @Comment("Webhook configuration")
    public static category_webhook WEBHOOK = new category_webhook();
    @Name("Messages")
    @Comment("Customize messages of this mod")
    public static category_messages MESSAGES = new category_messages();
    @Comment("Configuration for built-in discord commands")
    public static category_commands COMMANDS = new category_commands();
    @Comment("Configurate the /discord command useable ingame")
    public static Configuration.discord_command DISCORD_COMMAND = new Configuration.discord_command();
    @Name("FTB Utilities")
    @Comment("These config values will only be used when FTB Utilities is installed!")
    public static category_ftbutilities FTB_UTILITIES = new category_ftbutilities();
    @Name("Votifier")
    @Comment("Configure votifier integration here")
    public static votifier VOTIFIER = new votifier();

    
    
    public static class category_general {

        @Comment({"Insert your Bot Token here!", "DO NOT SHARE IT WITH ANYONE!"})
        public String BOT_TOKEN = "INSERT TOKEN HERE!";
        public Discord.GameTypes BOT_GAME_TYPE = Discord.GameTypes.PLAYING;
        @Comment({"The Name of the Game", "", "PLACEHOLDERS:", "%online% - Online Players", "%max% - Maximum Player Amount"})
        public String BOT_GAME_NAME = "Minecraft with %online% players";
        @Comment("The channel ID where the bot will be working in")
        public String CHANNEL_ID = "000000000";
        @Comment("Wether or not the Bot should modify the channel description")
        public boolean MODIFY_CHANNEL_DESCRIPTRION = true;
        @Comment("If you think the update check is annoying disable this")
        public boolean UPDATE_CHECK = true;
        @Comment({"When description updates are enabled, how long should it wait before updating? (in milliseconds)", "Setting this too low can cause RateLimits from discord"})
        public int DESCRIPTION_UPDATE_DELAY = 500;
    }

    public static class category_linking {
        @Comment({"Role ID players receive when they are discord linked", "Leave empty to not provide a role"})
        public String DISCORD_LINKED_ROLE_ID = "";
        @Comment({"Is linking enabled?", "Set to false if not!"})
        public boolean DISCORD_LINKING_ENABLED = false;
    }
    
    public static class category_advanced {
        @Comment({"Custom channel ID for server specific messages (like Join/leave)", "Leave empty to use default channel"})
        public String SERVER_CHANNEL_ID = "";

        @Comment({"Custom channel ID for death messages", "Leave empty to use default channel"})
        public String DEATH_CHANNEL_ID = "";

        @Comment({"Custom channel ID for FTB Utilities messages", "Leave empty to use default channel"})
        public String FTB_UTILITIES_CHANNEL_ID = "";

        @Comment({"Custom channel ID for Votifier messages", "Leave empty to use default channel"})
        public String VOTIFIER_CHANNEL_ID = "";

        @Comment({"Custom channel where messages get sent to minecraft", "Leave empty to use default channel"})
        public String CHAT_INPUT_ID = "";

        @Comment({"Custom channel for ingame messages", "Leave empty to use default channel"})
        public String CHAT_OUTPUT_ID = "";

        @Comment({"Custom channel for description", "Leave empty to use default channel"})
        public String CHANNEL_DESCRIPTION_ID = "";
    }

    public static class category_webhook {

        @Comment("Wether or not the bot should use a webhook (it will create one)")
        public boolean BOT_WEBHOOK = false;
        @Comment("The avatar to be used for server messages")
        public String SERVER_AVATAR = "https://raw.githubusercontent.com/ErdbeerbaerLP/Discord-Chat-Integration/master/images/srv.png";
        @Comment("The username of the server")
        public String SERVER_NAME = "Server";
    }

    public static class category_messages {
        @Comment("Set this to true if you don't want to have Join / Leave messages being sent")
        public boolean DISABLE_JOIN_LEAVE_MESSAGES = false;
        @Comment({"The format of the uptime command and %uptime% placeholder", "For more help with the formatting visit https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html"})
        public String UPTIME_FORMAT = "dd 'days' HH 'hours' mm 'minutes' ss 'seconds'";
        @Comment("Disable removal of color codes from chat to discord?")
        public boolean DISCORD_COLOR_CODES = false;
        @Comment("Enable removal of color codes from discord to chat?")
        public boolean PREVENT_MC_COLOR_CODES = false;
        @Comment("Should tamed entity death be visible in discord?")
        public boolean TAMED_DEATH_ENABLED = false;
        @Comment("This message will edited in / sent when the server finished starting")
        public String SERVER_STARTED_MSG = "Server Started!";
        @Comment({"Message to show while the server is starting", "This will be edited to SERVER_STARTED_MSG when webhook is false"})
        public String SERVER_STARTING_MSG = "Server Starting...";
        @Comment("This message will be sent when the server was stopped")
        public String SERVER_STOPPED_MSG = "Server Stopped!";
        @Comment({"PLACEHOLDERS:", "%player% - The player\u00B4s name"})
        public String PLAYER_JOINED_MSG = "%player% joined";
        @Comment({"PLACEHOLDERS:", "%player% - The player\u00B4s name"})
        public String PLAYER_LEFT_MSG = "%player% left";
        @Comment({"PLACEHOLDERS:", "%player% - The player\u00B4s name", "%msg% - The death message"})
        public String PLAYER_DEATH_MSG = "%player% %msg%";
        @Comment({"Message shown if a player  attempts to link but is already"})
        public String LINK_ALREADY_LINKED = "You are already linked to %player%!";
        @Comment({"Message shown if a player attempts to link and fails"})
        public String LINK_FAILED = "Your code was not correct!";
        @Comment({"Message shown if a player  attempts to link and suceeds"})
        public String LINK_SUCCESS = "You are now linked to minecraft!";
        @Comment({"Message shown if a player  attempts to link and its invalid"})
        public String LINK_INVALID = "Your discord linking code is invalid!";
        @Comment({"Message shown if a player  attempts to link and encounters an error"})
        public String LINK_ERROR = "An error occured linking you. Please report the time this occured to the server administrator.";
        @Comment({"Message shown if a player  attempts to run a discord command and fails because they arent linked"})
        public String LINK_NOT_LINKED = "You're not linked to your minecraft account.\n%method%";
        @Comment({"Message shown if a player tries to update their settings and fails"})
        public String SETTINGS_UPDATE_FAILED = "You're not able to update your settings.";
        @Comment({"Message shown if a player tries to update their settings and succeeds"})
        public String SETTINGS_UPDATE_SUCCESS = "Settings updated successfully!";
        @Comment({"Message shown if a player tries to update their settings and its invalid"})
        public String SETTINGS_INVALID = "Setting %key% is invalid!";
        @Comment({"Message shown if a player tries to update their settings and its invalid"})
        public String SETTINGS_GET = "%bool%";
        
        @Comment({"The message to print to discord when it was possible to detect a server crash", "Will also be used in the channel description"})
        public String SERVER_CRASHED_MSG = "Server Crash Detected :thinking:";
        @Comment({"This is what will be displayed ingame when someone types into the bot\u00B4s channel", "PLACEHOLDERS:", "%user% - The username", "%id% - The user ID", "%msg% - The Message"})
        public String INGAME_DISCORD_MSG = "\u00A76[\u00A75DISCORD\u00A76]\u00A7r <%user%> %msg%";
        @Comment({"Supports MulitLined messages using \\n", "PLACEHOLDERS:", "%player% - The player\u00B4s name", "%name% - The advancement name", "%desc% - The advancement description"})
        public String PLAYER_ADVANCEMENT_MSG = "%player% just gained the advancement **%name%**\\n_%desc%_";
        @Comment({"Chat message when webhook is disabled", "PLACEHOLDERS:", "%player% - The player\u00B4s name", "%msg% - The chat message"})
        public String PLAYER_CHAT_MSG = "%player%: %msg%";
        @Comment(
                {"Channel description while the server is online", "PLACEHOLDERS:", "%online% - Online player amount", "%max% - Maximum player count", "%tps% - Server TPS", "%motd% - The server MOTD (from server.properties!)", "%uptime% - The uptime of the server", "%days% - The amount of days the server has been online", "%hours% - The amount of hours the server has been online", "%minutes% - The amount of minutes the server has been online", "%seconds% - The amount of seconds the server has been online"})
        public String CHANNEL_DESCRIPTION = "%motd% (%online%/%max%) | %tps% TPS | Uptime: %uptime%";
        @Comment("Channel description while the server is offline")
        public String CHANNEL_DESCRIPTION_OFFLINE = "Server is Offline!";
        @Comment("Channel description while the server is starting")
        public String CHANNEL_DESCRIPTION_STARTING = "Starting...";
        @Comment({"PLACEHOLDERS:", "%player% - The player\u00B4s name"})
        public String PLAYER_TIMEOUT_MSG = "%player% timed out!";
        @Comment("Should /say output be sent to discord?")
        public boolean ENABLE_SAY_OUTPUT = true;
        @Comment("Should /me output be sent to discord?")
        public boolean ENABLE_ME_OUTPUT = true;
        
    }
    
    public static class category_ftbutilities
    {
        @Comment("Print afk messages in discord")
        public boolean DISCORD_AFK_MSG_ENABLED = true;
        @Comment({"Format of the AFK message", "PLACEHOLDERS:", "%player% - The player\u00B4s name"})
        public String DISCORD_AFK_MSG = "%player% is now AFK";
        @Comment({"Format of the no longer AFK message", "PLACEHOLDERS:", "%player% - The player\u00B4s name"})
        public String DISCORD_AFK_MSG_END = "%player% is no longer AFK";
        @Comment("URL of the FTB Avatar icon")
        public String FTB_AVATAR_ICON = "https://raw.githubusercontent.com/ErdbeerbaerLP/Discord-Chat-Integration/master/images/ftb.png";
        @Comment({"Format of the shutdown message printed when the server will shutdown/restart in 10 seconds"})
        public String SHUTDOWN_MSG_10SECONDS = "Server stopping in 10 seconds!";
        @Comment({"Format of the shutdown message printed when the server will shutdown/restart in 2 minutes"})
        public String SHUTDOWN_MSG_2MINUTES = "Server stopping in 2 minutes!";
        @Comment("Format name like in chat?")
        public boolean CHAT_FORMATTING = true;
    }
    
    public static class category_commands
    {
        @Comment("Set to false to completely disable the \"Unknown Command\" message")
        public boolean ENABLE_UNKNOWN_COMMAND_MESSAGE = true;
        @Comment("Set to true to enable the \"Unknown Command\" message in all channels")
        public boolean ENABLE_UNKNOWN_COMMAND_MESSAGE_EVERYWHERE = false;
        @Comment({"Add your Custom commands to this JSON", "You can copy-paste it to https://jsoneditoronline.org  Make sure when pasting here, that the json is NOT mulitlined.", "You can click on \"Compact JSON Data\" on the website", "NOTE: You MUST op the uuid set at SENDER_UUID in the ops.txt !!!", "", "mcCommand   -   The command to execute on the server", "adminOnly   -   True: Only allows users with the Admin role to use this command. " + "False: @everyone can use the command", "description -   Description shown in /help", "aliases     -   Aliases for the command in a string array", "useArgs     -   Shows argument text after the command", "argText     -   Defines custom arg text. Default is <args>", "channelIDs    -    Allows you to set specific text channels outside of the server channel to use this command (make it an string array), Set to [\"00\"] to allow from all channels"})
        public String JSON_COMMANDS = DiscordIntegration.defaultCommandJson;
        @Comment("The Role ID of your Admin Role")
        public String ADMIN_ROLE_ID = "0";
        @Comment({"The prefix of the commands like list"})
        public String CMD_PREFIX = "/";
        @Comment("The message for 'list' when no player is online")
        public String MSG_LIST_EMPTY = "There is no player online...";
        @Comment("The message for 'list' when one is online")
        public String MSG_LIST_ONE = "There is 1 player online:";
        @Comment({"The header for 'list'", "PLACEHOLDERS:", "%amount% - The amount of players online"})
        public String MSG_LIST_HEADER = "There are %amount% players online:";
        @Comment("Message sent when user does not have permission to run a command")
        public String MSG_NO_PERMISSION = "You don\u00B4t have permission to execute this command!";
        @Comment({"Message sent when an invalid command was typed", "PLACEHOLDERS:", "%prefix% - Command prefix"})
        public String MSG_UNKNOWN_COMMAND = "Unknown command, try `%prefix%help` for a list of commands";
        @Comment("You MUST op this UUID in the ops.txt or many commands won´t work!!")
        public String SENDER_UUID = "8d8982a5-8cf9-4604-8feb-3dd5ee1f83a3";
        @Comment({"Message if a player provides too many arguments", "PLACEHOLDERS:", "%player% - The player\u00B4s name"})
        public String MSG_PLAYER_NOT_FOUND = "Can not find player \"%player%\"";
        @Comment({"Enable the /help command in discord", "Disabling also removes response when you entered an invalid command", "Requires server restart"})
        public boolean ENABLE_HELP_COMMAND = true;
        @Comment("Custom Channel ID for the help command. Set to 00 to allow usage from everywhere and to 0 to allow usage from the bots default channel")
        public String HELP_CMD_CHANNEL_ID = "00";
        @Comment("Custom Channel ID for the list command. Set to 00 to allow usage from everywhere and to 0 to allow usage from the bots default channel")
        public String LIST_CMD_CHANNEL_ID = "0";
        @Comment("Custom Channel ID for the uptime command. Set to 00 to allow usage from everywhere and to 0 to allow usage from the bots default channel")
        public String UPTIME_CMD_CHANNEL_ID = "0";
        @Comment({"Enable the /list command in discord", "Requires server restart"})
        public boolean ENABLE_LIST_COMMAND = true;
        @Comment({"Enable the /uptime command in discord", "Requires server restart"})
        public boolean ENABLE_UPTIME_COMMAND = true;
        @Comment({"A list of blacklisted modids", "Adding one will prevent the mod to send messages to discord using forges IMC system"})
        public String[] IMC_MOD_ID_BLACKLIST = new String[]{"examplemodid"};
        @Comment("Header of the help command")
        public String HELP_HEADER = "Your available commands in this channel:";
    }
    
    public static class discord_command
    {
        @Comment("The message displayed when typing /discord in the server chat")
        public String MESSAGE = "Join our discord! http://discord.gg/myserver";
        @Comment("The message shown when hovering the /discord command message")
        public String HOVER = "Click to open the invite url";
        @Comment("The url to open when clicking the /discord command text")
        public String URL = "http://discord.gg/myserver";
        @Comment("Message sent when unignoring discord messages")
        public String IGNORECMD_UNIGNORE = "You are no longer ignoring Discord messages!";
        @Comment("Message sent when ignoring discord messages")
        public String IGNORECMD_IGNORE = "You are now ignoring Discord messages!";
    }
    
    public static class votifier
    {
        @Comment("Should votifier messages be sent to discord?")
        public boolean ENABLED = true;
        @Comment({"The message format of the votifier message", "", "PLACEHOLDERS:", "%player% - The player\u00B4s name", "%site% - The name of the vote site", "%addr% - (IP) Address of the site"})
        public String MESSAGE = "%player% voted on %site%";
        @Comment("Name of the webhook author")
        public String NAME = "Votifier";
        @Comment("URL of the webhook avatar image")
        public String AVATAR_URL = "https://media.forgecdn.net/avatars/158/149/636650534005921456.png";
    }
}
