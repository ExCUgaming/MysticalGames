package com.mysticalsurvival.commands.parkour;

import com.mysticalsurvival.core.parkour.Checkpoint;
import com.mysticalsurvival.core.parkour.Difficulty;
import com.mysticalsurvival.core.parkour.Parkour;
import com.mysticalsurvival.core.parkour.ParkourMap;
import com.mysticalsurvival.util.MessagesConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ParkourAdminCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("ms.admin") || sender.hasPermission("ms.parkouradmin")) {
            if (sender instanceof Player) {

                Player player = (Player) sender;
                if (args.length >= 1) {

                    if (args.length >= 2) {


                        //createparkour
                        if (args.length == 3) {
                            if (args[0].equalsIgnoreCase("create")) {
                                if (args[2].equalsIgnoreCase("easy")) {
                                    new ParkourMap(args[1], Difficulty.EASY);
                                } else if (args[2].equalsIgnoreCase("normal")) {
                                    new ParkourMap(args[1], Difficulty.NORMAL);
                                } else if (args[2].equalsIgnoreCase("hard")) {
                                    new ParkourMap(args[1], Difficulty.HARD);
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-create.invalid-difficulty")))
                                            .replaceAll("<player>", sender.getName())
                                            .replaceAll("<playerdisplay>", player.getDisplayName())
                                            .replaceAll("<map>", args[1]));
                                    return true;
                                }


                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-create.success")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            }
                        }


                        //deleteparkour
                        if (args[0].equalsIgnoreCase("delete")) {
                            if (Parkour.getParkourMapInstance(args[1]) != null) {
                                Parkour.getParkourMapInstance(args[1]).delete();
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-delete.success")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-delete.not-found")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            }
                        }

                        //setstart
                        if (args[0].equalsIgnoreCase("setstart")) {
                            if (Parkour.getParkourMapInstance(args[1]) != null) {
                                Parkour.getParkourMapInstance(args[1]).setStartLocation(((Player) sender).getLocation().getBlock().getLocation());
                                ((Player) sender).getLocation().getBlock().setType(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setstart.success")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setstart.not-found")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            }
                        }

                        //setend
                        if (args[0].equalsIgnoreCase("setend")) {
                            if (Parkour.getParkourMapInstance(args[1]) != null) {
                                Parkour.getParkourMapInstance(args[1]).setEndLocation(((Player) sender).getLocation().getBlock().getLocation());
                                ((Player) sender).getLocation().getBlock().setType(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setend.success")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setend.not-found")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            }
                        }

                        //setspawn
                        if (args[0].equalsIgnoreCase("setspawn")) {
                            if (Parkour.getParkourMapInstance(args[1]) != null) {
                                Parkour.getParkourMapInstance(args[1]).setSpawnLocation(((Player) sender).getLocation());
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setspawn.success")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setstart.not-found")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            }
                        }

                        //setdeathy
                        if (args.length == 3) {
                            if (args[0].equalsIgnoreCase("setdeathy")) {
                                if (Parkour.getParkourMapInstance(args[1]) != null) {
                                    try {
                                        Parkour.getParkourMapInstance(args[1]).setDeathY(Short.parseShort(args[2]));
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setdeathy.success")))
                                                .replaceAll("<player>", sender.getName())
                                                .replaceAll("<playerdisplay>", player.getDisplayName())
                                                .replaceAll("<map>", args[1]));
                                    } catch (Exception ignored) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setdeathy.parse-fail")))
                                                .replaceAll("<player>", sender.getName())
                                                .replaceAll("<playerdisplay>", player.getDisplayName())
                                                .replaceAll("<map>", args[1]));
                                    }
                                    return true;
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setdeathy.not-found")))
                                            .replaceAll("<player>", sender.getName())
                                            .replaceAll("<playerdisplay>", player.getDisplayName())
                                            .replaceAll("<map>", args[1]));
                                    return true;
                                }
                            }
                        }

                        //goto command
                        if (args[0].equalsIgnoreCase("goto")) {
                            if (Parkour.getParkourMapInstance(args[1]) != null) {
                                if (Parkour.getParkourMapInstance(args[1]).getSpawnLocation() != null) {
                                    ((Player) sender).teleport(Parkour.getParkourMapInstance(args[1]).getSpawnLocation());
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-goto.success")))
                                            .replaceAll("<player>", sender.getName())
                                            .replaceAll("<playerdisplay>", player.getDisplayName())
                                            .replaceAll("<map>", args[1]));
                                    return true;
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-goto.no-spawn")))
                                            .replaceAll("<player>", sender.getName())
                                            .replaceAll("<playerdisplay>", player.getDisplayName())
                                            .replaceAll("<map>", args[1]));
                                    return true;
                                }
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-goto.not-found")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            }
                        }

                        //addcheckpoint command
                        if (args.length == 3) {
                            if (args[0].equalsIgnoreCase("addcheckpoint")) {
                                if (Parkour.getParkourMapInstance(args[1]) != null) {
                                    try {
                                        if (Parkour.getParkourMapInstance(args[1]).createCheckpoint(Short.parseShort(args[2]), ((Player) sender).getLocation().getBlock().getLocation(), ((Player) sender).getLocation())) {
                                            ((Player) sender).getLocation().getBlock().setType(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-addcheckpoint.success")))
                                                    .replaceAll("<player>", sender.getName())
                                                    .replaceAll("<playerdisplay>", player.getDisplayName())
                                                    .replaceAll("<map>", args[1])
                                                    .replaceAll("<ckid>", args[2]));
                                            return true;
                                        } else {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-addcheckpoint.creation-fail")))
                                                    .replaceAll("<player>", sender.getName())
                                                    .replaceAll("<playerdisplay>", player.getDisplayName())
                                                    .replaceAll("<map>", args[1])
                                                    .replaceAll("<ckid>", args[2]));
                                            return true;
                                        }
                                    } catch (Exception ignored) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-addcheckpoint.parse-fail")))
                                                .replaceAll("<player>", sender.getName())
                                                .replaceAll("<playerdisplay>", player.getDisplayName())
                                                .replaceAll("<map>", args[1])
                                                .replaceAll("<ckid>", args[2]));
                                        return true;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-addcheckpoint.not-found")))
                                            .replaceAll("<player>", sender.getName())
                                            .replaceAll("<playerdisplay>", player.getDisplayName())
                                            .replaceAll("<map>", args[1])
                                            .replaceAll("<ckid>", args[2]));
                                    return true;
                                }
                            }
                        }

                        //deletecheckpoint command
                        if (args.length == 3) {
                            if (args[0].equalsIgnoreCase("deletecheckpoint")) {
                                if (Parkour.getParkourMapInstance(args[1]) != null) {
                                    try {
                                        if (Parkour.getParkourMapInstance(args[1]).deleteCheckpoint(Short.parseShort(args[2]))) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-deletecheckpoint.success")))
                                                    .replaceAll("<player>", sender.getName())
                                                    .replaceAll("<playerdisplay>", player.getDisplayName())
                                                    .replaceAll("<map>", args[1])
                                                    .replaceAll("<ckid>", args[2]));
                                            return true;
                                        } else {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-deletecheckpoint.deletion-fail")))
                                                    .replaceAll("<player>", sender.getName())
                                                    .replaceAll("<playerdisplay>", player.getDisplayName())
                                                    .replaceAll("<map>", args[1])
                                                    .replaceAll("<ckid>", args[2]));
                                            return true;
                                        }
                                    } catch (Exception ignored) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-deletecheckpoint.parse-fail")))
                                                .replaceAll("<player>", sender.getName())
                                                .replaceAll("<playerdisplay>", player.getDisplayName())
                                                .replaceAll("<map>", args[1])
                                                .replaceAll("<ckid>", args[2]));
                                        return true;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-deletecheckpoint.not-found")))
                                            .replaceAll("<player>", sender.getName())
                                            .replaceAll("<playerdisplay>", player.getDisplayName())
                                            .replaceAll("<map>", args[1])
                                            .replaceAll("<ckid>", args[2]));
                                    return true;
                                }
                            }
                        }

                        //setcheckpointtplocation command
                        if (args.length == 3) {
                            if (args[0].equalsIgnoreCase("setcheckpointtplocation")) {
                                if (Parkour.getParkourMapInstance(args[1]) != null) {
                                    try {
                                        Parkour.getParkourMapInstance(args[1]).getCheckpoint(Short.parseShort(args[2])).setTpLocation(((Player) sender).getLocation());
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setcheckpointtplocation.success")))
                                                .replaceAll("<player>", sender.getName())
                                                .replaceAll("<playerdisplay>", player.getDisplayName())
                                                .replaceAll("<map>", args[1])
                                                .replaceAll("<ckid>", args[2]));
                                        return true;
                                    } catch (Exception ignored) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setcheckpointtplocation.parse-fail")))
                                                .replaceAll("<player>", sender.getName())
                                                .replaceAll("<playerdisplay>", player.getDisplayName())
                                                .replaceAll("<map>", args[1])
                                                .replaceAll("<ckid>", args[2]));
                                        return true;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setcheckpointtplocation.not-found")))
                                            .replaceAll("<player>", sender.getName())
                                            .replaceAll("<playerdisplay>", player.getDisplayName())
                                            .replaceAll("<map>", args[1])
                                            .replaceAll("<ckid>", args[2]));
                                    return true;
                                }
                            }
                        }

                        //listcheckpoints command
                        if (args[0].equalsIgnoreCase("listcheckpoints")) {
                            if (Parkour.getParkourMapInstance(args[1]) != null) {
                                ArrayList<String> list = new ArrayList<>();
                                for (Checkpoint cp : Parkour.getParkourMapInstance(args[1]).getCheckpoints()) {
                                    list.add(Short.toString(cp.getId()));
                                }
                                sender.sendMessage(ChatColor.GREEN + list.toString());
                                return true;
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-listcheckpoints.not-found")))
                                        .replaceAll("<player>", sender.getName())
                                        .replaceAll("<playerdisplay>", player.getDisplayName())
                                        .replaceAll("<map>", args[1]));
                                return true;
                            }
                        }

                    }

                    //listmaps
                    if (args[0].equalsIgnoreCase("listmaps")) {
                        sender.sendMessage(ChatColor.GREEN + Parkour.getAllParkourMapInstances().keySet().toString());
                        return true;
                    }

                    //setlobby
                    if (args[0].equalsIgnoreCase("setlobby")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-setlobby.success")))
                                .replaceAll("<player>", sender.getName())
                                .replaceAll("<playerdisplay>", player.getDisplayName()));
                        Parkour.setParkourLobbyLoc(((Player) sender).getLocation());
                        return true;
                    }

                }

                for (String msg : MessagesConfig.getList("games.parkour.admin.command-usage")) {
                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', msg)
                                    .replaceAll("<player>", sender.getName())
                                    .replaceAll("<playerdisplay>", player.getDisplayName())
                                    .replaceAll("<label>", label)
                    );
                }

            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-player")))
                        .replaceAll("<player>", sender.getName()));
                return true;
            }

        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.admin.command-permission.no-permission")))
                    .replaceAll("<player>", sender.getName()));
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("ms.admin") || sender.hasPermission("ms.parkouradmin")) {

            if (args.length == 1) {
                return StringUtil.copyPartialMatches(args[0], Arrays.asList("create", "listmaps", "delete", "setstart", "setend", "setspawn", "setdeathy", "goto", "setlobby", "addcheckpoint", "deletecheckpoint", "listcheckpoints", "setcheckpointtplocation"), new ArrayList<>());
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList(Parkour.getAllParkourMapInstances().keySet().toArray(new String[0])), new ArrayList<>());
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("setstart")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList(Parkour.getAllParkourMapInstances().keySet().toArray(new String[0])), new ArrayList<>());
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("setend")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList(Parkour.getAllParkourMapInstances().keySet().toArray(new String[0])), new ArrayList<>());
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("setspawn")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList(Parkour.getAllParkourMapInstances().keySet().toArray(new String[0])), new ArrayList<>());
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("setdeathy")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList(Parkour.getAllParkourMapInstances().keySet().toArray(new String[0])), new ArrayList<>());
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("goto")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList(Parkour.getAllParkourMapInstances().keySet().toArray(new String[0])), new ArrayList<>());
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("addcheckpoint")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList(Parkour.getAllParkourMapInstances().keySet().toArray(new String[0])), new ArrayList<>());
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("deletecheckpoint")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList(Parkour.getAllParkourMapInstances().keySet().toArray(new String[0])), new ArrayList<>());
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("listcheckpoints")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList(Parkour.getAllParkourMapInstances().keySet().toArray(new String[0])), new ArrayList<>());
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("setcheckpointtplocation")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList(Parkour.getAllParkourMapInstances().keySet().toArray(new String[0])), new ArrayList<>());
            }

            if (args.length == 3 && args[0].equalsIgnoreCase("deletecheckpoint")) {
                try {
                    ArrayList<String> list = new ArrayList<>();
                    for (Checkpoint cp : Parkour.getParkourMapInstance(args[1]).getCheckpoints()) {
                        list.add(Short.toString(cp.getId()));
                    }
                    return StringUtil.copyPartialMatches(args[2], list, new ArrayList<>());
                } catch (Exception ignored) {
                }
            }

            if (args.length == 3 && args[0].equalsIgnoreCase("setcheckpointtplocation")) {
                try {
                    ArrayList<String> list = new ArrayList<>();
                    for (Checkpoint cp : Parkour.getParkourMapInstance(args[1]).getCheckpoints()) {
                        list.add(Short.toString(cp.getId()));
                    }
                    return StringUtil.copyPartialMatches(args[2], list, new ArrayList<>());
                } catch (Exception ignored) {
                }
            }
        }
        return new ArrayList<>();
    }
}