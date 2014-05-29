package com.RPGMakerDev.RPGMaker.Commands;

import com.RPGMakerDev.RPGMaker.EntityData.CrippledZombie1;
import com.RPGMakerDev.RPGMaker.EntityData.CustomGuard;
import com.RPGMakerDev.RPGMaker.EntityData.EliteZombieLv3;
import com.RPGMakerDev.RPGMaker.EntityData.HorseMount;
import com.RPGMakerDev.RPGMaker.EntityData.Villager1;
import net.minecraft.server.v1_7_R3.World;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.entity.Player;

public class spawnmob implements CommandExecutor {

    private World world;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            world = ((CraftWorld) p.getWorld()).getHandle();
            Location loc = p.getLocation();
            if (args.length != 0) {
                Integer parsed = Integer.parseInt(args[1]);
                switch (args[0]) {
                    case "ironguard":
                        for (int i = 0; i < parsed; i++) {
                            CustomGuard guard = new CustomGuard(world); //Generally you design your custom entity to accept an nms world to call the superconstructor.
                            guard.setPosition(loc.getX(), loc.getY(), loc.getZ());
                            guard.setCustomName("Iron Guard " + ChatColor.DARK_RED + "Lv55");
                            guard.setCustomNameVisible(true);
                            world.addEntity(guard);
                        }
                        break;

                    case "zombie":
                        for (int i = 0; i < parsed; i++) {
                            CrippledZombie1 zombie1 = new CrippledZombie1(world); //Generally you design your custom entity to accept an nms world to call the superconstructor.
                            zombie1.setPosition(loc.getX(), loc.getY(), loc.getZ());
                            zombie1.setCustomName(ChatColor.DARK_RED + "Zombie " + ChatColor.GREEN + "Lv1");
                            zombie1.setCustomNameVisible(true);
                            world.addEntity(zombie1);
                        }
                        break;

                    case "Shopkeeper":
                        for (int i = 0; i < parsed; i++) {
                            Villager1 villager1 = new Villager1(world); //Generally you design your custom entity to accept an nms world to call the superconstructor.
                            villager1.setPosition(loc.getX(), loc.getY(), loc.getZ());
                            villager1.setCustomName(ChatColor.GREEN + "" + ChatColor.BOLD + "Shopkeeper");
                            villager1.setCustomNameVisible(true);
                            world.addEntity(villager1);
                        }
                        break;
                    case "mount":
                        for (int i = 0; i < parsed; i++) {
                            HorseMount mount = new HorseMount(world); //Generally you design your custom entity to accept an nms world to call the superconstructor.
                            mount.setPosition(loc.getX(), loc.getY(), loc.getZ());
                            mount.setCustomName(ChatColor.GREEN + p.getName() + "'s Mount ");
                            mount.setCustomNameVisible(true);
                            world.addEntity(mount);
                        }
                        break;
                    case "elitezombielv3":
                        for (int i = 0; i < parsed; i++) {
                            EliteZombieLv3 elitezombielv3 = new EliteZombieLv3(world); //Generally you design your custom entity to accept an nms world to call the superconstructor.
                            elitezombielv3.setPosition(loc.getX(), loc.getY(), loc.getZ());
                            elitezombielv3.setCustomName(ChatColor.DARK_RED + "Zombie" + ChatColor.GREEN + " Lv3");
                            elitezombielv3.setCustomNameVisible(true);
                            world.addEntity(elitezombielv3);
                        }
                        break;
                }
            }
        } else {
            sender.sendMessage("This command can only be ran by a player");
        }
        return true;
    }

}
