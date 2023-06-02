package events;

import alexpetmecky.explosivearrows.Explosivearrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.Random;

public class ArrowEvents implements Listener {
    Explosivearrows plugin;
    ArrayList<Arrow> explosive = new ArrayList<>();
    ArrayList<Arrow> cluster= new ArrayList<>();

    ArrayList<Arrow> area = new ArrayList<>();



    public ArrowEvents(Explosivearrows plugin){
        this.plugin=plugin;
        //this.w = plugin.getServer().getWorld();
    }

    @EventHandler
    public void launchEvent(ProjectileLaunchEvent event){
        Projectile proj = event.getEntity();
        //System.out.println("LAUNCH NAME: "+proj.getName());

    }

    @EventHandler
    public void entityShoot(EntityShootBowEvent event){
        //System.out.println("ENTITY LAUNCH: "+event.getConsumable().getItemMeta().getDisplayName());

        if( event.getConsumable().getItemMeta().getLore()==null ||event.getConsumable().getItemMeta().getDisplayName() == null ){
            System.out.println("EXITING");
            return;
        }

        String displayName = event.getConsumable().getItemMeta().getDisplayName();
        String lore = event.getConsumable().getItemMeta().getLore().get(0);

        if(displayName.equals("Explosive Arrow") && lore.equals("Explodes on impact")){
            explosive.add((Arrow) event.getProjectile());
        }else if(displayName.equals("Cluster Arrow") && lore.equals("Explodes on impact with secondary explosions")){
            cluster.add((Arrow) event.getProjectile());

        }else if(displayName.equals("Area Denial Arrow") && lore.equals("Blocks an area")){
            area.add((Arrow) event.getProjectile());
        }



    }

    @EventHandler
    public void arrowHit(ProjectileHitEvent event){
        Projectile proj=event.getEntity();
        //event.get
        //System.out.println("NAME: "+proj.getName());

        if(proj instanceof Arrow){
            //System.out.println("ARROW HIT");
            if(explosive.contains(proj) ){
                explosive.remove(proj);
                event.getEntity().getWorld().createExplosion(proj.getLocation(),100,true);
                proj.remove();
            }

            //event.setCancelled(true);



        }

    }

    @EventHandler
    public void cluster(ProjectileHitEvent event) {
        Projectile proj = event.getEntity();
        if(proj instanceof Arrow){
            if(cluster.contains(proj)){
                cluster.remove(proj);

                Location loc=proj.getLocation();

                event.getEntity().getWorld().createExplosion(loc,5,true);
                //System.out.println("LOCATION BEFORE: "+loc);



                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        event.getEntity().getWorld().createExplosion(loc.add(10,0,0),10,true);
                        event.getEntity().getWorld().createExplosion(loc.subtract(20,0,0),10,true);
                        event.getEntity().getWorld().createExplosion(loc.add(10,0,10),10,true);
                        event.getEntity().getWorld().createExplosion(loc.subtract(0,0,20),10,true);
                        //System.out.println("LOCATION After: "+loc);
                    }
                }, 20L);



                proj.remove();
            }
        }


    }
    @EventHandler
    public void area(ProjectileHitEvent event){
        Projectile proj = event.getEntity();

        if(proj instanceof Arrow){
            if(area.contains(proj)){
                area.remove(proj);

                Location loc = proj.getLocation();
                //System.out.println("Starting Location: "+loc);

                //Location boundingTopLeft = loc.add();


                BoundingBox areaBox = BoundingBox.of(loc,5,5,5);
                //System.out.println("X MAX: "+areaBox.getMaxX());
                //System.out.println("X MAX: "+areaBox.getMinX());


                Random r = new Random();
                double minX = areaBox.getMinX();
                double maxX = areaBox.getMaxX();

                double minZ = areaBox.getMinZ();
                double maxZ = areaBox.getMaxZ();

                double y = loc.getY();




                new BukkitRunnable(){
                    int count=0;
                    public void run(){
                        if(count==10){
                            System.out.println("Cancelling");
                            this.cancel();
                        }

                        double randomX=r.nextInt()%(maxX-minX)+minX;

                        double randomZ=r.nextInt()%(maxZ-minZ)+minZ;

                        //System.out.println("Coords= "+randomX+" "+y+" "+randomZ);

                        event.getEntity().getWorld().createExplosion(randomX,y,randomZ,10,true);

                        count+=1;

                    }
                }.runTaskTimer(plugin, 20, 20);



                proj.remove();
                return;
            }

        }


    }



}
