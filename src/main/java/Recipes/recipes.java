package Recipes;

import alexpetmecky.explosivearrows.Explosivearrows;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class recipes{
    Explosivearrows plugin;

    public recipes(Explosivearrows plugin) {
        this.plugin=plugin;
    }

    public void createExplosiveArrow(){
        ItemStack explosiveArrow= new ItemStack(Material.ARROW);
        ItemMeta explosiveMeta = explosiveArrow.getItemMeta();
        explosiveMeta.setDisplayName("Explosive Arrow");
        List<String> lore = new ArrayList<>();
        lore.add("Explodes on impact");
        explosiveMeta.setLore(lore);

        explosiveArrow.setItemMeta(explosiveMeta);


        ShapedRecipe recipe= new ShapedRecipe(new NamespacedKey(plugin,"explosive_arrow"),explosiveArrow);

        recipe.shape(" TA","   ","   ");
        recipe.setIngredient('T',Material.TNT);
        recipe.setIngredient('A',Material.ARROW);

        Bukkit.addRecipe(recipe);


    }

    public void createCluster(){
        ItemStack clusterArrow= new ItemStack(Material.ARROW);
        ItemMeta explosiveMeta = clusterArrow.getItemMeta();
        explosiveMeta.setDisplayName("Cluster Arrow");
        List<String> lore = new ArrayList<>();
        lore.add("Explodes on impact with secondary explosions");
        explosiveMeta.setLore(lore);

        clusterArrow.setItemMeta(explosiveMeta);

        ShapedRecipe recipe= new ShapedRecipe(new NamespacedKey(plugin,"cluster_arrow"),clusterArrow);

        recipe.shape("TAT","   ","   ");
        recipe.setIngredient('T',Material.TNT);
        recipe.setIngredient('A',Material.ARROW);

        Bukkit.addRecipe(recipe);
    }

    public void createArea(){
        ItemStack areaArrow= new ItemStack(Material.ARROW);
        ItemMeta explosiveMeta = areaArrow.getItemMeta();
        explosiveMeta.setDisplayName("Area Denial Arrow");
        List<String> lore = new ArrayList<>();
        lore.add("Blocks an area");
        explosiveMeta.setLore(lore);

        areaArrow.setItemMeta(explosiveMeta);

        ShapedRecipe recipe= new ShapedRecipe(new NamespacedKey(plugin,"area_arrow"),areaArrow);

        recipe.shape("TTT","TAT","TTT");
        recipe.setIngredient('T',Material.TNT);
        recipe.setIngredient('A',Material.ARROW);

        Bukkit.addRecipe(recipe);



    }

}
