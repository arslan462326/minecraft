package ru.yourname.c4fix;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Регистрация событий, чтобы сервер "слышал" этот код
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onC4Explode(EntityExplodeEvent event) {
        // Проверяем, что взорвался именно динамит
        if (!(event.getEntity() instanceof TNTPrimed)) return;
        
        TNTPrimed tnt = (TNTPrimed) event.getEntity();
        String name = tnt.getCustomName();

        // Ищем С4 в названии (код цвета §d)
        if (name != null && (name.contains("С4") || name.contains("C4"))) {
            int radius = 4; // Радиус разрушения обсидиана вокруг С4
            
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block b = tnt.getLocation().clone().add(x, y, z).getBlock();
                        
                        // ПРИНУДИТЕЛЬНО ЛОМАЕМ ОБСИДИАН
                        if (b.getType() == Material.OBSIDIAN) {
                            b.breakNaturally(); // Ломает блок и дропает предмет
                        }
                    }
                }
            }
        }
    }
}
