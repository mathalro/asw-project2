package sonc.game;

import java.io.IOException;
import java.io.Serializable;
import javax.naming.NameNotFoundException;
import sonc.battle.Ship;
import sonc.shared.SoncException;
import sonc.utils.AgentBuilder;

/**
 * A player of the SonC game. An instance of this class records the player's
 * authentication and the last code submitted.
 * 
 * @author Ricardo Giovani
 * @version 1.0
 *
 */
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nick;
	private String password;
	private String code;

	String getNick() {
		return this.nick;
	}

	void setNick(String nick) {
		this.nick = nick;
	}

	String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	String getCode() {
		return this.code;
	}

	void setCode(String code) {
		this.code = code;
	}

	Player(String nick, String password) {
		this.nick = nick;
		this.password = password;
	}

	/**
	 * Try to compile and instance the submitted code and report errors. It uses the
	 * AgentBuilder class.
	 * 
	 * SoncException - on errors in compiling or instancing the code
	 */
	void checkCode() throws SoncException {
		try {
			AgentBuilder agentBuilder = new AgentBuilder();
			agentBuilder.getInstance(Ship.class, this.code, this.nick);
		} catch (IOException | InstantiationException | IllegalAccessException | NameNotFoundException
				| NullPointerException e) {
			throw new SoncException("An error occurred on checking player's code.", e);
		}
	}

	/**
	 * Make an instance of {@link Ship} after compiling and instancing the submitted
	 * code. This instance is stored in this class
	 * 
	 * @return instance ship or null if exceptions occurred when compiling the code
	 *         or instancing the class
	 */
	Ship instanceShip() {
		try {
			this.checkCode();
			AgentBuilder agentBuilder = new AgentBuilder();
			return agentBuilder.getInstance(Ship.class, this.code, this.nick);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Check the authentication of this player
	 * 
	 * @param password
	 *            - for checking
	 * @return true password is the expected, false otherwise
	 */
	boolean authenticate(String password) {
		return this.password.equals(password);
	}
}