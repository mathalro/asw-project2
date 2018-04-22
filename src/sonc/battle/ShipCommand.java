package sonc.battle;

/**
 * This is the abstract part of the Command design pattern. It defines a command
 * executed by the ship and is used to delays command execution until the
 * {@link Ship#move()} command is completed. Ship commands are used to ensure
 * that ships execute at most one command per turn.
 * 
 * @author Ricardo Giovani
 * @version 1.0
 */
public interface ShipCommand {
	void execute();
}