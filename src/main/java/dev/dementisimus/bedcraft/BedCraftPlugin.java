/*
 | Copyright 2025 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.bedcraft;

import de.cytooxien.realms.api.DisplayProvider;
import dev.dementisimus.autumn.api.Autumn;
import dev.dementisimus.autumn.api.npc.pool.AutumnNPCPool;
import dev.dementisimus.autumn.plugin.initializer.CustomAutumnInitializer;
import dev.dementisimus.bedcraft.listener.AsyncChatListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class BedCraftPlugin extends JavaPlugin {

    private Autumn autumn;
    private AutumnNPCPool npcPool;
    private DisplayProvider displayProvider;

    @Override
    public void onEnable() {
        CustomAutumnInitializer.of(this).enableStorageSetupStates().initialize(autumn -> {
            this.autumn = autumn;
            this.npcPool = this.autumn.getNpcPool();

            this.autumn.registerListener(new AsyncChatListener(this));

            try {
                this.displayProvider = Bukkit.getServicesManager().load(DisplayProvider.class);
            } catch (NoClassDefFoundError ignored) {
            }
        });
    }
}
