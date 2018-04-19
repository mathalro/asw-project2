package sonc.game;

import java.io.IOException;
import java.io.Serializable;
import javax.naming.NameNotFoundException;
import sonc.battle.Ship;
import sonc.shared.SoncException;
import sonc.utils.AgentBuilder;

public class Player implements Serializable
{
	//Attributes
	private static final long serialVersionUID = 1L;
	private String nick;
	private String password;
	private String code;
	//The ship really need to be stored? Doesn't have any
	//mention of a "getShip" or "setShip" in the API,
	//but it says that we need to store the instance
	//of the ship for some reason that I don't know. Well,
	//the "instanceShip" method create an instance of a ship,
	//returning it and storing in this attribute as well. I don't
	//know how will we use it, but I doing what the specification says
	//just to follow a development pattern.
	private Ship ship;

	//Getters and setters	
	String getNick()
	{
		return this.nick;
	}
	void setNick(String nick)
	{
		this.nick = nick;
	}
	String getPassword()
	{
		return this.password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	String getCode()
	{
		return this.code;
	}
	void setCode(String code)
	{
		this.code = code;
	}
	
	//Constructors
	Player(String nick, String password)
	{
		this.nick = nick;
		this.password = password;
	}	
	
	//Methods
	void checkCode() throws SoncException
	{
		try
		{
			AgentBuilder agentBuilder = new AgentBuilder();
			agentBuilder.getInstance(Ship.class, this.code, this.nick);
		} catch (IOException | InstantiationException | IllegalAccessException | NameNotFoundException e)
		{
			throw new SoncException(e.getMessage());
		}
	}
	
	Ship instanceShip()
	{
		try
		{
			this.checkCode();
			AgentBuilder agentBuilder = new AgentBuilder();
			return ship = agentBuilder.getInstance(Ship.class, this.code, this.nick);
		} catch (Exception e)
		{
			return ship = null;
		}
	}
	
	boolean authenticate(String password)
	{
		return this.password.equals(password);
	}
}