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

public class Manager implements Serializable
{
	//Attributes
	private static final long serialVersionUID = 1L;
	private static File playersFile;
	private static Manager instance;
	Players allPlayers;
	
	//Getters and setters
	public static File getPlayersFile()
	{
		return Manager.playersFile;
	}	
	public static void setPlayersFile(File managerFile)
	{
		Manager.playersFile = managerFile;
	}
	
	//Constructors
	private Manager()
	{
		this.allPlayers = new Players();
	}	
	
	//Methods
	public static Manager getInstance() throws SoncException
	{		
		if (Manager.instance != null)
			return Manager.instance;
		else
		{		
			if ((Manager.playersFile == null) || (!Manager.playersFile.exists()))			
				return Manager.instance = new Manager();
			else
			{		
				try (FileInputStream stream = new FileInputStream(playersFile);
					 ObjectInputStream deserializer = new ObjectInputStream(stream))
				{
					Manager.instance = (Manager) deserializer.readObject();
					return Manager.instance;				
				} catch (IOException | ClassNotFoundException e)
				{
					throw new SoncException(e.getMessage());
				}
			}
		}
	}
	
	public boolean register(String userId, String password) throws SoncException
	{
		try (FileOutputStream stream = new FileOutputStream(playersFile);
			 ObjectOutputStream serializer = new ObjectOutputStream(stream))
		{
			boolean registered = this.allPlayers.register(userId, password);
			if (registered)
				serializer.writeObject(this);
			return registered;
		} catch (IOException e)
		{
			throw new SoncException(e.getMessage());
		}
	}
	
	public boolean updatePassword(String nick, String oldPassword, String newPassword) throws SoncException
	{
		try (FileOutputStream stream = new FileOutputStream(playersFile);
		     ObjectOutputStream serializer = new ObjectOutputStream(stream))
		{
			boolean updated = this.allPlayers.updatePassword(nick, oldPassword, newPassword);
			if (updated)
				serializer.writeObject(this);
			return updated;
		} catch (IOException e)
		{
			throw new SoncException(e.getMessage());
		}
	}
	
	public boolean authenticate(String nick, String password)
	{
		return this.allPlayers.getPlayer(nick).authenticate(password);
	}
	
	public String getCurrentCode(String nick, String password) throws SoncException
	{
		Player player = this.allPlayers.getPlayer(nick);
		if ((player == null) || (!player.authenticate(password)))
			throw new SoncException("Nick is unknown or password is invalid.");
		return player.getCode();
	}
	
	public void buildShip(String nick, String password, String code) throws SoncException
	{
		Player player = this.allPlayers.getPlayer(nick);
		if ((player == null) || (!player.authenticate(password)))
			throw new SoncException("Nick is unknown or password is invalid.");
		else
		{
			player.setCode(code);
			if (player.instanceShip() == null)
				throw new SoncException("The code has errors or an I/O error occurred.");
		}
	}
	
	List<String> getPlayersNamesWithShips()
	{
		return this.allPlayers.getPlayersNamesWithShips();
	}
	
	public Movie battle(List<String> nicks)
	{
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
	
	void reset()
	{		
		try (FileOutputStream stream = new FileOutputStream(playersFile);
			 ObjectOutputStream serializer = new ObjectOutputStream(stream))
		{		
			this.allPlayers = new Players();
			serializer.writeObject(this);			
		} catch (Exception e) {}
	}
}