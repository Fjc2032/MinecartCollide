package dev.Fjc.minecartCollide;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecartCollide extends JavaPlugin implements Listener {
    private double damageAmt;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getLogger().info("Plugin is starting!");
        this.saveDefaultConfig();

        damageAmt = getConfig().getDouble("collision-damage", 45.0);
        //Creates a setting to change the damage amount. Defaults to 45.

    }

    @EventHandler
    public void onEntityCollision(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Minecart) {
            Minecart minecart = (Minecart) event.getDamager();
            Entity entity = event.getEntity();
            if (minecart.getVelocity().length() > 0) {
                //This checks if the minecart velocity is greater than 0, indicating that it's moving
                LivingEntity livingEntity = (LivingEntity) entity;
                entity = event.getEntity();

                if (!(entity instanceof org.bukkit.entity.Player)) {
                    livingEntity.setHealth(Math.max(0, livingEntity.getHealth() - damageAmt));
                    //Checks if the entity is not a player, then sets the health using math
            } else {
                    if (entity instanceof Player) {
                        livingEntity.setHealth(Math.max(0, livingEntity.getHealth() - damageAmt - 15));
                        //Deals additional damage to players using math.
                    }
                }
        }

        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("minecartcollidereload")) {
            this.reloadConfig();
            damageAmt = getConfig().getDouble("collision-damage", 45.0);
            sender.sendMessage("Reload successful. Awesome!!!");
            return true;
            //Reloads the configuration file.
        }
        sender.sendMessage("Oop. Something went wrong? Check your syntax.");
        return false;
        //If the command is invalid (somehow), sends an error message, returns false, exits.
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getLogger().info("Shutdown commencing.");
    }
}
