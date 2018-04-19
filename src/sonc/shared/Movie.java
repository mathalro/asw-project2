package sonc.shared;

import java.util.ArrayList;
import java.util.List;

public class Movie
{
	//Attributes
	private List<Frame> frames;
	
	//Getters and setters
	public List<Frame> getFrames()
	{
		return this.frames;
	}
	
	//Constructors
	public Movie()
	{
		this.frames = new ArrayList<>();
	}
	
	//Methods
	public void newFrame()
	{
		this.frames.add(new Frame());
	}
	
	public void addOblong(int x, int y, float heading, int size, String color) throws IllegalStateException
	{
		if (this.frames.size() == 0)
			throw new IllegalStateException("It's necessary to instantiate a frame before insert an oblong object.");
		else
			this.frames.get(this.frames.size() - 1).getOblongs().add(new Oblong(x, y, heading, size, color));
	}
	
	public void addScore(String name, int points, int status) throws IllegalStateException
	{
		if (this.frames.size() == 0)
			throw new IllegalStateException("It's necessary to instantiate a frame before insert a score object.");
		else			
			this.frames.get(this.frames.size() - 1).getScores().add(new Score(name, points, status));
	}
	
	//Inner classes
	public static class Frame
	{
		//Attributes
		private List<Oblong> oblongs;
		private List<Score> scores;
		
		//Getters and setters
		public List<Oblong> getOblongs()
		{
			return this.oblongs;
		}
		
		public List<Score> getScores()
		{
			return this.scores;
		}
		
		//Constructors
		public Frame()
		{
			this.oblongs = new ArrayList<>();
			this.scores = new ArrayList<>();
		}		
	}
	
	public static class Oblong
	{
		//Attributes
		int x;
		int y;
		float heading;
		int size;
		String color;
		
		//Getters and setters
		public int getX()
		{
			return this.x;
		}		
		public int getY()
		{
			return this.y;
		}
		public float getHeading()
		{
			return this.heading;
		}		
		public int getSize()
		{
			return this.size;
		}		
		public String getColor()
		{
			return this.color;
		}
		
		//Constructors
		public Oblong(int x, int y, float heading, int size, String color) 
		{
			this.x = x;
			this.y = y;
			this.heading = heading;
			this.size = size;
			this.color = color;
		}
	}
	
	public static class Score
	{
		//Attributes
		private String name;
		private int points;
		private int status;
		
		//Getters and setters
		public String getName()
		{
			return this.name;			
		}		
		public int getPoints()
		{
			return this.points;
		}		
		public int getStatus()
		{
			return this.status;
		}
		
		//Constructors
		public Score(String name, int points, int status)
		{
			this.name = name;
			this.points = points;
			this.status = status;
		}		
	}	
}