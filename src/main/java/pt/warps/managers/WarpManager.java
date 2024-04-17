package pt.warps.managers;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import pt.warps.LocationSerielizer;
import pt.warps.Warps;
import pt.warps.objets.Warp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WarpManager {

    private static final Warps INSTANCE = Warps.getInstance();
    private final ConfigurationSection config = INSTANCE.getConfig().getConfigurationSection("Warps");
    ArrayList<Warp> warpList;

    public WarpManager() {
        warpList = new ArrayList<>();
        load();
    }

    public Warp get(String warpName) {
        return warpList.stream().filter(w -> w.getName().equalsIgnoreCase(warpName)).findFirst().orElse(null);
    }

    public void go(Player player, Warp warp) {
        player.teleport(warp.getLocation());
    }

    public void insert(Warp warp){
        warpList.add(warp);
        save(warp);
    }

    public boolean delete(String warpName){
        Warp warp = get(warpName);
        if (warp != null) {
            warpList.remove(warp);
            config.set(warpName, null);
            INSTANCE.saveConfig();
            return true;
        }
        return false;
    }

    public String getWarpList() {

        List<String> warplist = warpList.stream().map(Warp::getName).collect(Collectors.toList());

        if (warplist.size() != 0) {
            String list = String.join(", ",warplist);
            return "§aLista de Warps: " + list;
        }
        return "§cNão existem warps disponíveis.";
    }

    private void save(Warp warp){
        config.set(warp.getName(), LocationSerielizer.serialize(warp.getLocation()));
        INSTANCE.saveConfig();
    }

    private void load(){
        for (String key : config.getKeys(false)) {
            Location loc = LocationSerielizer.unserielize(config.getString(key));
            Warp warp = new Warp(key, loc);
            warpList.add(warp);
        }
        System.out.println("[Warp] " + warpList.size() + " warps foram carregadas.");
    }
}
