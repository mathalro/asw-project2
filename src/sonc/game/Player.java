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
		} catch (IOException | InstantiationException | IllegalAccessException | NameNotFoundException | NullPointerException e)
		{
			throw new SoncException("An error occurred on checking player's code.", e);
		}
	}
	
	Ship instanceShip()
	{
		try
		{
			this.checkCode();
			AgentBuilder agentBuilder = new AgentBuilder();
			return agentBuilder.getInstance(Ship.class, this.code, this.nick);
		} catch (Exception e)
		{
			return null;
		}
	}
	
	boolean authenticate(String password)
	{
		return this.password.equals(password);
	}
}