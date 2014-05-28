package Entity;

public class PlayerSave {
	private static int lives=0;
	private static int health=16;
	private static long startTime=0,endTime=0;
	private static int score=0;
	
	
	void init(){
		//lives=3;
		//score=0;
		//health=16;

	}
	
	public static int getLives(){return lives;}
	public static void setLives(int i){lives=i;}
	
	public static int getHealth(){return health;}
	public static void setHealth(int i){health=i;}
	
	public static long getTime(){return endTime-startTime;}

	public static long getStartTime(){return startTime;}
	public static void setStartTime(long i){startTime=i;}
	
	public static long getEndTime(){return endTime;}
	public static void setEndTime(long i){endTime=i;}
	
	public static int getScore(){return score;}
	public static void setScore(int i){score=i;}
	
	public static void nullPlayer()
	{
		health=0;
		lives=0;
		endTime=0;
		startTime=0;
		score=0;
	}
	
}
