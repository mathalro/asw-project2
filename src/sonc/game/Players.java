package sonc.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Players implements Serializable
{
	//Attributes
	private static final long serialVersionUID = 1L;
	private Map<String,Player> players;	
	
	//Constructors
	public Players()
	{
		this.players = new HashMap<>();		
	}
	
	//Methods
	boolean register(String nick, String password)
	{
		return (this.players.put(nick, new Player(nick, password)) == null);
	}
	
	boolean updatePassword(String nick, String oldPassword, String newPassword)
	{
		Player player = this.players.get(nick);
		if ((player != null) && (player.authenticate(oldPassword)))
		{
			player.setPassword(newPassword);
			return true;
		}
		return false;
	}
	
	boolean authenticate(String nick, String password)
	{
		Player player = this.players.get(nick);
		return ((player != null) && (player.authenticate(password)));
	}
	
	Player getPlayer(String name)
	{
		return this.players.get(name);
	}
	
	List<String> getPlayersNamesWithShips()
	{
		List<String> playersNamesWithShips = new ArrayList<>();
		for (String key : this.players.keySet())
			if (this.players.get(key).instanceShip() != null)
				playersNamesWithShips.add(key);
		Collections.sort(playersNamesWithShips);
		return playersNamesWithShips;
	}
}