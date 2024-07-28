package words.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.util.logging.Logger;

public class TournamentEngine {
	static int n_cached= 0;
	static class GameRunner
    {
    	class PlayerWithScore implements Comparable<PlayerWithScore>
    	{
    		int score;
    		int playerID;
    		@Override
    		public int compareTo(PlayerWithScore o) {
    			return (score == o.score ? 1 : score > o.score ? -1 : 1);
    		}
    		
    	}
        public void run() {
            try {
            	int this_round = 0;
                Boolean play = true;
                int score;
//                String header = gameconfig.getPlayersPlaying()+","+gameconfig.number_of_secret_objects+","+gameconfig.number_of_rounds+",";
                PreparedStatement prep = null;
                try {
					 prep = conn.prepareStatement(
					"insert into tournament_rounds (tournament_id,player,player_id,round,points_spent,points_gained,score,rank) values ("+tournament_id+",?,?,?,?,?,?,?);");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gamecontroller.bannedPlayers = new HashSet<Player>();
                while(play == true)
                {
                    score = gamecontroller.GamePlay(gameconfig).retValue;
                    if(conn != null)
                    {
                    	if(gameconfig.current_round != this_round)
                    	{
            			//format "PlayerList,NumSecret,NumRounds,round_number,player,word,points_won,points_spent,score"
                    		this_round = gameconfig.current_round;
                    		HashMap<Integer, Integer> playerToScore = new HashMap<Integer, Integer>();
                    		TreeSet<PlayerWithScore> sortedSet = new TreeSet<PlayerWithScore>();
	                    	for(int i =0;i<gameconfig.PlayerList.size();i++)
	                		{
	                    		PlayerWithScore t = new PlayerWithScore();
	                    		t.score = gameconfig.secretstateList.get(i).score;
	                    		t.playerID= i;
	                    		sortedSet.add(t);
	                		}
	                    	Iterator<PlayerWithScore> it = sortedSet.iterator();
	                    	int rank = 1;
	                    	while(it.hasNext())
	                    	{
	                    		PlayerWithScore t = it.next();
	                    		playerToScore.put(t.playerID, rank);
	                    		rank++;
	                    	}
                    		for(int i =0;i<gameconfig.PlayerList.size();i++)
                    		{
                    			prep.setString(1, gameconfig.PlayerList.get(i));
                    			prep.setInt(2, i);
                    			prep.setInt(3, gameconfig.current_round);
                    			prep.setInt(4, gameconfig.lasPointsSpent.get(i));
                    			prep.setInt(5, gameconfig.lasPoints.get(i));
                    			prep.setInt(6, gameconfig.secretstateList.get(i).score);
                    			if(playerToScore.get(i) == null)
                    				System.err.println("No rank for player " + i + ", score was " + gameconfig.secretstateList.get(i).score);
                    			prep.setInt(7, playerToScore.get(i));
//                    			prep.setInt(7,0);
                    			prep.addBatch();
                    			conn.setAutoCommit(false);
	                    		prep.executeBatch();
	                    		conn.commit();
	                    		prep.clearBatch();
                    			n_cached++;
                    		}
//                    		n_cached = 210;
//                    		System.err.println("" + Math.round(((double) (gameconfig.current_round*100*100))/gameconfig.number_of_rounds)/100+"%");
//							gameconfig.outFile.flush();
                    		if(n_cached > 20)
                    		{
	                    		conn.setAutoCommit(false);
	                    		prep.executeBatch();
	                    		conn.commit();
	                    		prep.clearBatch();
	                    		n_cached = 0;
                    		}
                    	}
                    }
                    else
                    	thisGameEngine.updateUI();
                if(score ==-1)
                {
                	try {
    					 prep = conn.prepareStatement(
    		     		"insert into output (tournament_id,player) values ("+tournament_id+",?);");
                		for(Player p : gamecontroller.bannedPlayers)
                		{
                			prep.setString(1, p.getClass().getName());
                			prep.execute();
                		}

     				} catch (SQLException e1) {
     					// TODO Auto-generated catch block
     					e1.printStackTrace();
     				}
                    // Game must have ended, since you have a score now
                   play = false;
                   String gameover = "Game Over.";
                   if(conn == null)
                	   JOptionPane.showMessageDialog(GameScreen, gameover);
                }

                }
                if(conn != null)
                {
                	try {
                		conn.setAutoCommit(false);
                		prep.executeBatch();
                		conn.commit();
                		Statement s2 = conn.createStatement();
            			s2.execute("UPDATE tournament SET end = NOW() WHERE id="+tournament_id+";");
                		conn.commit();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }


    }

	static Connection conn = null;
	static void initDB(String filename)
	{
        try
        {
//            String url = "jdbc:sqlite:"+filename;
//            Class.forName ("org.sqlite.JDBC").newInstance ();
//            conn = DriverManager.getConnection (url);
        	String userName = "ppsf09";
            String password = "ppsf09";
            String url = "jdbc:mysql://projects.seas.columbia.edu/4444p1";
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            conn = DriverManager.getConnection (url, userName, password);
            System.out.println ("Database connection established");
        }
        catch (Exception e)
        {
            System.err.println ("Cannot connect to database server>>>");
            e.printStackTrace();
        }

	}
	
    public static IOController iocontroller;
    public static GameConfig gameconfig;
    public static GameController gamecontroller;
    public static JTable GameScreen;
    static Thread grunner;
    static Boolean play;
    public static GameEngine thisGameEngine;
    /** Creates new form GameEngine */
    public static javax.swing.Timer memUpdate;
    private Logger logger = Logger.getLogger(GameEngine.class.getName());

	public static void main(String[] args) {
		initDB("results.db");
		System.setOut(
    		    new PrintStream(new OutputStream() {
					@Override
					public void write(int b) throws IOException {						
					}
				}));
		try {
			Statement s = conn.createStatement();
			ResultSet rs1 =s.executeQuery("SELECT COUNT(*) FROM jobs where started is null");
			rs1.next();
			int n = rs1.getInt(1);
			while(n > 0)
			{
				ResultSet rs = s.executeQuery("SELECT job,id from jobs where started is null limit 1");
				if(rs.next())
				{
					int nz = rs.getInt(2);
					String job = (rs.getString(1));
					Statement s2 = conn.createStatement();
					int stillHave = s2.executeUpdate("UPDATE jobs set started=NOW() where started is null and id="+rs.getInt(2));
					if(stillHave == 1)
					{
						String[] args2 = job.split(";");						
						runText(args2);
						s2 = conn.createStatement();
						s2.executeUpdate("UPDATE jobs set ended=NOW() where id="+nz);
					}
				}
				rs1= s.executeQuery("SELECT COUNT(*) FROM jobs where started is null");
				rs1.next();
				n = rs1.getInt(1);
//					n--;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		runText(args);
	}
	static int tournament_id = 0;
    static void runText(String[] args) {
    	String players[] = args[0].split(" ");
    	int numSecret = Integer.valueOf(args[1]);
    	int numRounds = Integer.valueOf(args[2]);
    	IOController iocontroller = new IOController();
    	String desc = args[3];
    	java.net.InetAddress localMachine = null;
		try {
			localMachine = java.net.InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	String hostname =  localMachine.getHostName();
		try {
			Statement s2 = conn.createStatement();
			s2.execute("INSERT INTO tournament (num_hidden,num_rounds,`start`,`source`,`desc`) VALUES ("+numSecret+","+numRounds+",NOW()," +
					"\""+hostname+"\", \""+desc+"\");",Statement.RETURN_GENERATED_KEYS);
			
			ResultSet t = s2.getGeneratedKeys();
			t.next();
			tournament_id = t.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gamecontroller = new GameController();
        // Lets create the playerstate object
        ArrayList<String> plist = new ArrayList<String>();
        for(String s : players)
    	{
        	plist.add(s);
    	}
        gameconfig = new GameConfig(plist, numSecret, iocontroller,numRounds);
        gameconfig.gameDelay = 0;

        GameRunner runner = new GameRunner();
        runner.run();
	}
}
