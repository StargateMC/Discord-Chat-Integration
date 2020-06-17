package java.de.erdbeerbaerlp.dcintegration.linkhandlers;

import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import de.erdbeerbaerlp.dcintegration.Configuration;
import de.erdbeerbaerlp.dcintegration.DiscordIntegration;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.*;
import java.util.UUID;

import static de.erdbeerbaerlp.dcintegration.DiscordIntegration.discord_instance;
import static de.erdbeerbaerlp.dcintegration.Configuration.*;

public class PlayerLinkController {
    private static final File playerLinkedFile = new File("./linkedPlayers.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final JsonParser parser = new JsonParser();

    public static boolean isPlayerLinked(UUID player) throws IllegalStateException {
        if (!DimensionManager.getWorld(0).getMinecraftServer().isServerInOnlineMode()) return false;
        try {
            for (JsonElement e : getJson()) {
                final PlayerLink o = gson.fromJson(e, PlayerLink.class);
                if (!o.mcPlayerUUID.isEmpty() && o.mcPlayerUUID.equals(player.toString())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isDiscordLinked(String discordID) {
        if (!DimensionManager.getWorld(0).getMinecraftServer().isServerInOnlineMode()) return false;
        try {
            for (JsonElement e : getJson()) {
                final PlayerLink o = gson.fromJson(e, PlayerLink.class);
                if (!o.discordID.isEmpty() && o.discordID.equals(discordID)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Nullable
    public static UUID getPlayerFromDiscord(String discordID) {
        if (!DimensionManager.getWorld(0).getMinecraftServer().isServerInOnlineMode()) return null;
        try {
            for (JsonElement e : getJson()) {
                final PlayerLink o = gson.fromJson(e, PlayerLink.class);
                if (!o.discordID.isEmpty() && !o.mcPlayerUUID.isEmpty() && o.discordID.equals(discordID)) {
                    return UUID.fromString(o.mcPlayerUUID);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String getDiscordFromPlayer(UUID player) {
        if (!DimensionManager.getWorld(0).getMinecraftServer().isServerInOnlineMode()) return null;
        try {
            for (JsonElement e : getJson()) {
                final PlayerLink o = gson.fromJson(e, PlayerLink.class);
                if (!o.discordID.isEmpty() && !o.mcPlayerUUID.isEmpty() && o.mcPlayerUUID.equals(player.toString())) {
                    return o.discordID;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PlayerSettings getSettings(String discordID, UUID player) {
        if (!DimensionManager.getWorld(0).getMinecraftServer().isServerInOnlineMode()) return null;
        if (player == null && discordID == null) throw new NullPointerException();
        else if (discordID == null) discordID = getDiscordFromPlayer(player);
        else if (player == null) player = getPlayerFromDiscord(discordID);
        if (player == null || discordID == null) throw new NullPointerException();
        try {
            for (JsonElement e : getJson()) {
                final PlayerLink o = gson.fromJson(e, PlayerLink.class);
                if (!o.discordID.isEmpty() && !o.mcPlayerUUID.isEmpty() && o.discordID.equals(discordID) && o.mcPlayerUUID.equals(player.toString())) {
                    return o.settings;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean linkPlayer(String discordID, UUID player) throws KeyAlreadyExistsException {
        if (!DimensionManager.getWorld(0).getMinecraftServer().isServerInOnlineMode()) return false;
        if (isDiscordLinked(discordID) || isPlayerLinked(player)) throw new KeyAlreadyExistsException();
        try {
            if (PlayerLinkController.getNameFromUUID(player) == null) return false;
            final JsonArray a = getJson();
            final PlayerLink link = new PlayerLink();
            link.discordID = discordID;
            link.mcPlayerUUID = player.toString();
            final boolean ignoringMessages = discord_instance.ignoringPlayers.contains(player.toString());
            link.settings.ignoreDiscordChatIngame = ignoringMessages;
            if (ignoringMessages) discord_instance.ignoringPlayers.remove(player.toString());
            a.add(gson.toJsonTree(link));
            saveJSON(a);
            //onPlayerLink is here
            final Guild guild = discord_instance.getChannel().getGuild();
            final Role linkedRole = guild.getRoleById(Configuration.LINKING.DISCORD_LINKED_ROLE_ID);
            final Member member = guild.getMember(discord_instance.jda.getUserById(PlayerLinkController.getDiscordFromPlayer(UUID.fromString(link.mcPlayerUUID))));
            if (!member.getRoles().contains(linkedRole))
                guild.addRoleToMember(member, linkedRole).queue();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePlayerSettings(String discordID, UUID player, PlayerSettings s) {
        if (!DimensionManager.getWorld(0).getMinecraftServer().isServerInOnlineMode()) return false;
        if (player == null && discordID == null) throw new NullPointerException();
        else if (discordID == null) discordID = getDiscordFromPlayer(player);
        else if (player == null) player = getPlayerFromDiscord(discordID);
        if (player == null || discordID == null) throw new NullPointerException();
        if (isDiscordLinked(discordID) && isPlayerLinked(player))
            try {
                final JsonArray a = getJson();
                final PlayerLink link = getUser(discordID, player);
                for (JsonElement e : a) {
                    final PlayerLink l = gson.fromJson(e, PlayerLink.class);
                    if (l.equals(link)) {
                        a.remove(e);
                        break;
                    }
                }
                if (link == null) return false;
                link.settings = s;
                a.add(gson.toJsonTree(link));
                saveJSON(a);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        return false;
    }

    private static void saveJSON(JsonArray a) throws IOException {
        try (Writer writer = new FileWriter(playerLinkedFile)) {
            gson.toJson(a, writer);
        }
    }

    public static boolean unlinkPlayer(String discordID, UUID player) {
        if (!DimensionManager.getWorld(0).getMinecraftServer().isServerInOnlineMode()) return false;
        if (!isDiscordLinked(discordID) && !isPlayerLinked(player)) return false;
        try {
            for (JsonElement e : getJson()) {
                final PlayerLink o = gson.fromJson(e, PlayerLink.class);
                if (o.discordID != null && o.discordID.equals(discordID)) {
                    final JsonArray json = getJson();
                    json.remove(e);
                    try (Writer writer = new FileWriter(playerLinkedFile)) {
                        gson.toJson(json, writer);
                    }
                    final Guild guild = discord_instance.getChannel().getGuild();
                    final Role linkedRole = guild.getRoleById(Configuration.LINKING.DISCORD_LINKED_ROLE_ID);
                    final Member member = guild.getMember(discord_instance.jda.getUserById(PlayerLinkController.getDiscordFromPlayer(UUID.fromString(o.mcPlayerUUID))));
                    if (member.getRoles().contains(linkedRole))
                        guild.removeRoleFromMember(member, linkedRole).queue();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static PlayerLink getUser(String discordID, UUID player) throws IOException {
        if (!DimensionManager.getWorld(0).getMinecraftServer().isServerInOnlineMode()) return null;
        final JsonArray a = getJson();
        for (JsonElement e : a) {
            final PlayerLink l = gson.fromJson(e, PlayerLink.class);
            if (l.discordID.equals(discordID) && l.mcPlayerUUID.equals(player.toString()))
                return l;
        }
        return null;
    }

    private static JsonArray getJson() throws IOException, IllegalStateException {
        if (!playerLinkedFile.exists()) {
            playerLinkedFile.createNewFile();
            try (Writer writer = new FileWriter(playerLinkedFile)) {
                gson.toJson(new JsonArray(), writer);
            }
            return new JsonArray();
        }
        final FileReader is = new FileReader(playerLinkedFile);
        final JsonArray a = parser.parse(is).getAsJsonArray();
        is.close();
        return a;
    }

    /**
     * Unused for now, might be needed in the future
     *
     * @return A all Player links as array or an empty array if parsing the json fails
     */
    public static PlayerLink[] getAllLinks() {
        try {
            return gson.fromJson(getJson(), PlayerLink[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PlayerLink[0];
    }

    @Nullable
    public static String getNameFromUUID(UUID uuid) {
        final String name = DimensionManager.getWorld(0).getMinecraftServer().getMinecraftSessionService().fillProfileProperties(new GameProfile(uuid, ""), false).getName();
        return name.isEmpty() ? null : name;
    }

}
