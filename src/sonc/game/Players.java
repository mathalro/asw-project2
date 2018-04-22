package sonc.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A collection of players, persisted on file. Contains methods for
 * registration, authentication and retrieving players and their names.
 * 
 * @author Ricardo Giovani
 * @version 1.0
 *
 */
public class Players implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<String, Player> players;

	public Players() {
		this.players = new HashMap<>();
	}

	/**
	 * Register a player with given nick and password
	 * 
	 * @param nick
	 *            - of user
	 * @param password
	 *            - id user
	 * @return true if registered and false otherwise
	 */
	boolean register(String nick, String password) {
		if ((isValid(nick)) && (!password.equals("")) && (this.players.get(nick) == null)) {
			nick = nick.trim();
			this.players.put(nick, new Player(nick, password));
			return true;
		}
		return false;
	}

	/**
	 * Change password if old password matches current one
	 * 
	 * @param nick - of player
	 * @param oldPassword - for authentication before update
	 * @param newPassword - after update
	 * @return true if password changed and false otherwise
	 */
	boolean updatePassword(String nick, String oldPassword, String newPassword) {
		if (!newPassword.equals("")) {
			Player player = this.players.get(nick);
			if ((player != null) && (player.authenticate(oldPassword))) {
				player.setPassword(newPassword);
				return true;
			}
		}
		return false;
	}

	/**
	 * Authenticate user given id and password
	 * 
	 * @param nick - of user to authenticate
	 * @param password - of user to authenticate
	 * @return true if authenticated and false otherwise
	 */
	boolean authenticate(String nick, String password) {
		Player player = this.players.get(nick);
		return ((player != null) && (player.authenticate(password)));
	}

	/**
	 * Get the player with given name
	 * 
	 * @param name - of player
	 * @return player instance
	 */
	Player getPlayer(String name) {
		return this.players.get(name);
	}

	/**
	 * Produces a sorted list of players' names that have an instantiable ship.
	 * 
	 * @return list of names as strings
	 */
	List<String> getPlayersNamesWithShips() {
		List<String> playersNamesWithShips = new ArrayList<>();
		for (String key : this.players.keySet())
			if (this.players.get(key).instanceShip() != null)
				playersNamesWithShips.add(key);
		Collections.sort(playersNamesWithShips);
		return playersNamesWithShips;
	}

	private boolean isValid(String nick) {
		return !nick.trim().contains(" ");
	}
}