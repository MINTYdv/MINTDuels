package xyz.mintydev.duels.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import xyz.mintydev.duels.MINTDuels;

public class LangManager extends ConfigFileHandler {

	private final MINTDuels main;
	
	public LangManager(MINTDuels main)
	{
		super(main, "messages.yml");
		this.main = main;
	}

	public static String getMessage(final String KEY) {
		return getDataFile().getString(KEY).replaceAll("&", "§");
	}
	
	public static String getMessageListSplitted(final String KEY)
	{
		String result = "";
		
		List<String> preResult = getMessageList(KEY);
		for(String str : preResult) {
			result += str + "\n";
		}
		return result;
	}
	
	public static List<String> getMessageList(final String KEY)
	{
		List<String> result = new ArrayList<>();
		for(String key : getDataFile().getStringList(KEY)) {
			result.add(key.replaceAll("&", "§"));
		}
		return result;
	}
	
    /* Getters & Setters */
}
