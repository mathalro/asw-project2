package sonc.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sonc.battle.Ship;
import sonc.battle.World;
import sonc.shared.Movie;
import sonc.shared.SoncException;

/**
 * An instance of this class is responsible for managing a community of players
 * with several games taking place simultaneously. The methods of this class are
 * those needed by web client thus it follows the Façade design pattern. It also
 * follows the singleton design pattern to provide a single instance of this
 * class to the application
 * 
 * @author Ricardo Giovani
 * @version 1.0
 */
public class Manager implements Serializable {
	private static final long serialVersionUID = 1L;
	private static File playersFile;
	private static Manager instance;
	Players allPlayers;

	public static File getPlayersFile() {
		return Manager.playersFile;
	}

	public static void setPlayersFile(File managerFile) {
		Manager.playersFile = managerFile;
	}

	private Manager() {
		this.allPlayers = new Players();
	}

	/**
	 * Returns the single instance of this class as proposed in the singleton design
	 * pattern. If a backup of this class is available then the manager is recreated
	 * from that data
	 * 
	 * @return instance of this class
	 * @throws SoncException
	 *             - if I/O error occurs reading serialization
	 */
	public static Manager getInstance() throws SoncException {
		if (Manager.instance != null)
			return Manager.instance;
		else {
			if ((Manager.playersFile == null) || (!Manager.playersFile.exists()))
				return Manager.instance = new Manager();
			else {
				try {
					Manager.instance = restore();
					return Manager.instance;
				} catch (IOException | ClassNotFoundException e) {
					throw new SoncException(e.getMessage());
				}
			}
		}
	}

	/**
	 * Register a player with given nick and password. Changes are stored in
	 * serialization file
	 * 
	 * @param userId
	 *            - of user
	 * @param password
	 *            - of user
	 * @return true if registered and false otherwise
	 * @throws SoncException
	 *             - if I/O error occurs when serializing data
	 */
	public boolean register(String userId, String password) throws SoncException {
		try {
			boolean registered = this.allPlayers.register(userId, password);
			if (registered)
				backup(this);
			return registered;
		} catch (IOException e) {
			throw new SoncException(e.getMessage());
		}
	}

	/**
	 * Change password if old password matches the current one
	 * 
	 * @param nick
	 *            - of player
	 * @param oldPassword
	 *            - for authentication before update
	 * @param newPassword
	 *            - after update
	 * @return true if password changed and false otherwise
	 * @throws SoncException
	 *             - if I/O error occurs when serializing data
	 */
	public boolean updatePassword(String nick, String oldPassword, String newPassword) throws SoncException {
		try {
			boolean updated = this.allPlayers.updatePassword(nick, oldPassword, newPassword);
			if (updated)
				backup(this);
			return updated;
		} catch (IOException e) {
			throw new SoncException(e.getMessage());
		}
	}

	/**
	 * Authenticate user given id and password
	 * 
	 * @param nick
	 *            - of user to authenticate
	 * @param password
	 *            - of user to authenticate
	 * @return true if authenticated and false otherwise
	 * 
	 */
	public boolean authenticate(String nick, String password) {
		return this.allPlayers.getPlayer(nick).authenticate(password);
	}

	/**
	 * Return last submitted code by the authenticated used
	 * 
	 * @param nick
	 *            - of player
	 * @param password
	 *            - of player
	 * @return code of player's ship
	 * @throws SoncException
	 *             - if nick is unknown or password invalid
	 */
	public String getCurrentCode(String nick, String password) throws SoncException {
		Player player = this.allPlayers.getPlayer(nick);
		if ((player == null) || (!player.authenticate(password)))
			throw new SoncException("Nick is unknown or password is invalid.");
		return player.getCode();
	}

	/**
	 * Set ship's code and try to instance it, for given user and from given code.
	 * 
	 * @param nick
	 *            - of the player
	 * @param password
	 *            - of the player
	 * @param code
	 *            - the compile and instance
	 * @throws SoncException
	 *             - if nick is unknown, password is invalid, code has errors or an
	 *             I/O error occurred
	 */
	public void buildShip(String nick, String password, String code) throws SoncException {
		Player player = this.allPlayers.getPlayer(nick);
		if ((player == null) || (!player.authenticate(password)))
			throw new SoncException("Nick is unknown or password is invalid.");
		else {
			player.setCode(code);
			if (player.instanceShip() == null)
				throw new SoncException("The code has errors or an I/O error occurred.");
		}
	}

	/**
	 * Returns a sorted list of all registered players' nicks with ships These nicks
	 * can be used in a simulation
	 * 
	 * @return list of strings
	 */
	List<String> getPlayersNamesWithShips() {
		return this.allPlayers.getPlayersNamesWithShips();
	}

	/**
	 * Simulate a battle with ships of given players. Ships are shuffled in random
	 * order (using the java.util.Collections.shuffle() method)
	 * 
	 * @param nicks
	 *            - of players in this game
	 * @return movie with game
	 */
	public Movie battle(List<String> nicks) {
		World world = new World();
		List<Player> players = new ArrayList<>();
		List<Ship> ships = new ArrayList<>();
		for (String nick : nicks)
			players.add(this.allPlayers.getPlayer(nick));
		for (Player player : players)
			ships.add(player.instanceShip());
		Collections.shuffle(ships);
		return world.battle(ships);
	}

	/**
	 * Resets players for debugging purposes.
	 */
	void reset() {
		try {
			this.allPlayers = new Players();
			backup(this);
		} catch (Exception e) {
		}
	}

	private static Manager restore() throws IOException, ClassNotFoundException {
		try (FileInputStream stream = new FileInputStream(playersFile);
				ObjectInputStream deserializer = new ObjectInputStream(stream)) {
			return (Manager) deserializer.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw e;
		}
	}

	private static void backup(Manager manager) throws IOException {
		try (FileOutputStream stream = new FileOutputStream(playersFile);
				ObjectOutputStream serializer = new ObjectOutputStream(stream)) {
			serializer.writeObject(manager);
		} catch (IOException e) {
			throw e;
		}
	}
}