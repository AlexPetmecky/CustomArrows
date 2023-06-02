package alexpetmecky.explosivearrows;

import Recipes.recipes;
import events.ArrowEvents;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Explosivearrows extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new ArrowEvents(this),this);
        //recipes.createExplosiveArrow(this);
        recipes recipe = new recipes(this);
        recipe.createExplosiveArrow();
        recipe.createCluster();
        recipe.createArea();



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
