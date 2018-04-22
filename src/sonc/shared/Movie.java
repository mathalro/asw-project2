package sonc.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Data for an animation of the game that can be presented to the player. It is
 * composed of a sequence frames, each with a sequence of oblong objects and
 * another of player's scores. Oblong objects of different sizes and colors
 * represent ships and munitions.
 * 
 * @author Ricardo Giovani
 * @version 1.0
 *
 */
public class Movie {
	private List<Frame> frames;

	public List<Frame> getFrames() {
		return this.frames;
	}

	public Movie() {
		this.frames = new ArrayList<>();
	}

	public void newFrame() {
		this.frames.add(new Frame());
	}

	/**
	 * Add a object to current frame
	 * 
	 * @param x
	 *            - coordinate of object
	 * @param y
	 *            - coordinate of object
	 * @param heading
	 *            - of object (angle in radians)
	 * @param size
	 *            - of the oblong object
	 * @param color
	 *            - String with its name (e.g. "red") or HTML/CSS format (e.g.
	 *            "#FF0000")
	 * @throws java.lang.IllegalStateException
	 *             - if no frame was created before executing this method
	 */
	public void addOblong(int x, int y, float heading, int size, String color) throws IllegalStateException {
		if (this.frames.size() == 0)
			throw new IllegalStateException("It's necessary to instantiate a frame before insert an oblong object.");
		else
			this.frames.get(this.frames.size() - 1).getOblongs().add(new Oblong(x, y, heading, size, color));
	}

	/**
	 * Add a score to current frame
	 * 
	 * @param name
	 *            - of player
	 * @param points
	 *            - of player
	 * @param status
	 *            - of player
	 * @throws java.lang.IllegalStateException
	 *             - if no frame was created before executing this method
	 */
	public void addScore(String name, int points, int status) throws IllegalStateException {
		if (this.frames.size() == 0)
			throw new IllegalStateException("It's necessary to instantiate a frame before insert a score object.");
		else
			this.frames.get(this.frames.size() - 1).getScores().add(new Score(name, points, status));
	}

	/**
	 * A frame in a movie with a list of oblong object and scores of players
	 * 
	 * @author Ricardo Giovani
	 * @version 1.0
	 *
	 */
	public static class Frame {
		private List<Oblong> oblongs;
		private List<Score> scores;

		public List<Oblong> getOblongs() {
			return this.oblongs;
		}

		public List<Score> getScores() {
			return this.scores;
		}

		public Frame() {
			this.oblongs = new ArrayList<>();
			this.scores = new ArrayList<>();
		}
	}

	/**
	 * Immutable representing oblong object with a center and a direction (heading).
	 * It also has a size and color that are useful for visualization.
	 * 
	 * @author Ricardo Giovani
	 * @version 1.0
	 *
	 */
	public static class Oblong {
		int x;
		int y;
		float heading;
		int size;
		String color;

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}

		public float getHeading() {
			return this.heading;
		}

		public int getSize() {
			return this.size;
		}

		public String getColor() {
			return this.color;
		}

		public Oblong(int x, int y, float heading, int size, String color) {
			this.x = x;
			this.y = y;
			this.heading = heading;
			this.size = size;
			this.color = color;
		}
	}

	/**
	 * Immutable with player's score presented in a frame, with points and status
	 * 
	 * @author Ricardo Giovani
	 * @version 1.0
	 *
	 */
	public static class Score {
		private String name;
		private int points;
		private int status;

		public String getName() {
			return this.name;
		}

		public int getPoints() {
			return this.points;
		}

		public int getStatus() {
			return this.status;
		}

		public Score(String name, int points, int status) {
			this.name = name;
			this.points = points;
			this.status = status;
		}
	}
}