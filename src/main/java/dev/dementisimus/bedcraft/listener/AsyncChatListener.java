/*
 | Copyright 2025 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.bedcraft.listener;

import dev.dementisimus.bedcraft.BedCraftPlugin;
import dev.dementisimus.bedcraft.permission.BedCraftPermissions;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class AsyncChatListener implements Listener {

    private final BedCraftPlugin plugin;

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        if(this.plugin.getDisplayProvider() == null) return;

        Player player = event.getPlayer();
        Component message = this.plugin.getDisplayProvider().getChatSuffix(player).append(Component.text(player.getName())).append(Component.space()).append(Component.text("-").color(NamedTextColor.GRAY)).append(Component.space());

        event.setCancelled(true);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(onlinePlayer.getWorld())) {
                if (player.hasPermission(BedCraftPermissions.COLORED_MESSAGES)) {
                    message = message.append(LegacyComponentSerializer.legacy('&').deserialize(LegacyComponentSerializer.legacyAmpersand().serialize(event.message())));
                } else {
                    message = message.append(event.message());
                }

                onlinePlayer.sendMessage(message);
            }
        }
    }
}
