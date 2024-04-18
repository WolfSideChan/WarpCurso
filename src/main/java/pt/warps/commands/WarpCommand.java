package pt.warps.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.warps.Warps;
import pt.warps.managers.WarpManager;
import pt.warps.objets.Warp;

public class WarpCommand implements CommandExecutor {

    private static final Warps INSTANCE = Warps.getInstance();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("warp")) {

            if (!(s instanceof Player)) {
                Bukkit.getConsoleSender().sendMessage("§cApenas jogadores podem executar este comando.");
                return false;
            }

            WarpManager warpManager = INSTANCE.getWarpManager();
            Player p = (Player) s;

            if (args.length == 0) {
                p.sendMessage("§cComando incorreto. Use:");
                p.sendMessage("");
                p.sendMessage("§c/warp list");
                p.sendMessage("§c/warp ir <nome>");
                p.sendMessage("§c/warp remove <nome>");
                p.sendMessage("§c/warp set <nome>");
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    if (!(p.hasPermission("warp.list"))) {
                        p.sendMessage(INSTANCE.getConfig().getString("Mensagens.Sem_perm").replace("&", "§"));
                        return false;
                    }
                    p.sendMessage(warpManager.getWarpList());
                }
            }

            if (args.length == 2) {

                String warpName = args[1];

                if (args[0].equalsIgnoreCase("ir")) {
                    Warp warp = warpManager.get(warpName);
                    if (warp != null) {
                        warpManager.go(p, warp);
                        p.sendMessage("§aVocê teleportou-se para a warp §f" + warpName + "§a.");
                    } else {
                        p.sendMessage("§cA warp §f"+ warpName + " §cnão existe.");
                        return false;
                    }

                }
                if (args[0].equalsIgnoreCase("remove")) {

                    if (!(p.hasPermission("warp.remove"))) {
                        p.sendMessage(INSTANCE.getConfig().getString("Mensagens.Sem_perm").replace("&", "§"));
                        return false;
                    }


                    if (warpManager.delete(warpName)) {
                        p.sendMessage("§cVocê deletou a warp §f" + warpName + " §ccom sucesso.");
                        return true;
                    }

                    p.sendMessage("§cA warp §f" + warpName + " §cnão existe.");

                }
                if (args[0].equalsIgnoreCase("set")) {

                    if (!(p.hasPermission("warp.set"))) {
                        p.sendMessage(INSTANCE.getConfig().getString("Mensagens.Sem_perm").replace("&", "§"));
                        return false;
                    }

                    warpManager.insert(new Warp(warpName, p.getLocation()));
                    p.sendMessage("§aVocê setou a warp §f" + warpName + " §acom sucesso.");
                }
            }

        }

        return false;
    }
}
