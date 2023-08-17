package xyz.mintydev.duels.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import xyz.mintydev.duels.MINTDuels;
import xyz.mintydev.duels.core.DuelPlayer;
import xyz.mintydev.duels.managers.database.DatabaseManager;
import xyz.mintydev.duels.managers.database.SQLQuery;

public class PlayerManager {

	private final MINTDuels main;
	
	private Set<DuelPlayer> players = new HashSet<>();
	private List<String> cached = new ArrayList<>();
	
	public PlayerManager(MINTDuels main) {
		this.main = main;
	}
	
	public DuelPlayer getPlayer(UUID uuid) {
		if(cached.contains(uuid.toString())){
			for(DuelPlayer dPlayer : players) {
				if(dPlayer.getUuid().equals(uuid)) return dPlayer;
			}
		}
		return null;
	}
	
	public DuelPlayer getPlayer(Player player) {
		if(!isCached(player)) {
				
			try(ResultSet rs = main.getDatabaseManager().executeResultStatement(SQLQuery.SELECT_PROFILE, player.getUniqueId().toString())) {
				
				if(rs == null) {
					return null;
				}
				
				while(rs.next()) {
					final DuelPlayer dPlayer = DatabaseManager.get().getProfileFromResultSet(rs);
					dPlayer.setName(player.getName());
					players.add(dPlayer);
					addToCache(player);
					return dPlayer;
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		} else {
			return getPlayer(player.getUniqueId());
		}
		
		main.getDatabaseManager().executeStatement(SQLQuery.INSERT_PROFILE, player.getUniqueId().toString(), player.getName(), 0, 0, 0, 0, 0);
		DuelPlayer res = new DuelPlayer(player.getUniqueId(), player.getName(),  0, 0, 0, 0, 0);
		players.add(res);
		addToCache(player);
		return res;
	}
	
	public void shutdown() {
		savePlayers();
	}
	
	private void savePlayers() {
		for(String uuid : cached) {
			savePlayer(UUID.fromString(uuid));
		}
	}
	
	public void savePlayer(UUID uuid) {
		if(!isCached(uuid)) return;
		
		final DuelPlayer dPlayer = getPlayer(uuid);
		
		DatabaseManager.get().executeStatement(SQLQuery.UPDATE_PROFILE,
				dPlayer.getName(),
				dPlayer.getWins(),
				dPlayer.getLoss(),
				dPlayer.getKills(),
				dPlayer.getDeaths(),
				dPlayer.getStreak(),
				uuid.toString());
	}
	
	public void addToCache(Player player) {
		cached.add(player.getUniqueId().toString());
	}
	
	public boolean isCached(UUID uuid) {
		return cached.contains(uuid.toString());
	}
	
	public boolean isCached(Player player) {
		return isCached(player.getUniqueId());
	}
	
	public List<String> getCached() {
		return cached;
	}

	public Set<DuelPlayer> getPlayers() {
		return players;
	}
	
}
