package words.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


public class GenerateTournamentScript {
	static class Pair
	{
		String p1;
		String p2;
		public Pair(String p1, String p2)
		{
			this.p1 = p1;
			this.p2 = p2;
		}
		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return p1.hashCode()+p2.hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return (p1.equals(((Pair) obj).p1) && p2.equals(((Pair) obj).p2) ) || (p1.equals(((Pair) obj).p2) && p2.equals(((Pair) obj).p1) );
		}
	}
	
	static String tpl = "PLAYERS;NUMSECRET;NUMROUNDS;DESC;results.db";
	static void generate8p()
	{
		String[] players = {"seven.f10.g1.players.BetterPlusPlusPlayer",
				"seven.f10.g3.OurPlayer",
				"seven.f10.g4.G4Player",
				"seven.f10.g5.Ninja",
				"seven.f10.g6.InitialPlayer",
				"seven.f10.g7.ClassyPlayer",
				"seven.f10.g9.BiddingPlayer",
				"seven.g1.G1Player"};
		String z = "";
		for(String s : players)
			z+= s + " ";
		z = z.substring(0,z.length()-1);
		ArrayList<String> s = new ArrayList<String>();
		s.add(z);
		printVersions(s, "All 8 players");
	}
	static void generate9p()
	{
		String[] players = {"seven.f10.g1.players.BetterPlusPlusPlayer",
				"seven.f10.g3.OurPlayer",
				"seven.f10.g4.G4Player",
				"seven.f10.g5.Ninja",
				"seven.f10.g6.InitialPlayer",
				"seven.f10.g7.ClassyPlayer",
				"seven.f10.g9.BiddingPlayer",
				"seven.g1.G1Player",
				"seven.g0.LessStingyPlayer"};
		String z = "";
		for(String s : players)
			z+= s + " ";
		z = z.substring(0,z.length()-1);
		ArrayList<String> s = new ArrayList<String>();
		s.add(z);
		printVersions(s, "All 8 players and LessStingy");
	}
	static void generate2v2s()
	{
		String[] players = {"seven.f10.g1.players.BetterPlusPlusPlayer",
				"seven.f10.g3.OurPlayer",
				"seven.f10.g4.G4Player",
				"seven.f10.g5.Ninja",
				"seven.f10.g6.InitialPlayer",
				"seven.f10.g7.ClassyPlayer",
				"seven.f10.g9.BiddingPlayer",
				"seven.g1.G1Player"};
//		String[] players = {"p1","p2","p3"};
		HashSet<Pair> pairsMade = new HashSet<Pair>();
		for(String p : players)
			for(String p2 : players)
			{
				if(!p.equals(p2))
				{
					Pair z = new Pair(p,p2);
					pairsMade.add(z);
				}
			}
		ArrayList<String> playerCombos = new ArrayList<String>();
		for(Pair p : pairsMade)
		{
			String z = p.p1 + " " + p.p1+" " + p.p2 + " " + p.p2 ;
			playerCombos.add(z);
		}
		printVersions(playerCombos,"2v2");
	}
	static void generate1v1s()
	{
		String[] players = {"seven.f10.g1.players.BetterPlusPlusPlayer",
				"seven.f10.g3.OurPlayer",
				"seven.f10.g4.G4Player",
				"seven.f10.g5.Ninja",
				"seven.f10.g6.InitialPlayer",
				"seven.f10.g7.ClassyPlayer",
				"seven.f10.g9.BiddingPlayer",
				"seven.g1.G1Player"};
//		String[] players = {"p1","p2","p3"};
		HashSet<Pair> pairsMade = new HashSet<Pair>();
		for(String p : players)
			for(String p2 : players)
			{
				if(p.equals(p2))
				{
					Pair z = new Pair(p,p2);
					pairsMade.add(z);
				}
			}
		ArrayList<String> playerCombos = new ArrayList<String>();
		for(Pair p : pairsMade)
		{
			String z = p.p1 + " " + p.p2 ;
			playerCombos.add(z);
		}
		printVersions(playerCombos,"1v1");
	}
	static void generate5v5s()
	{
		String[] players = {"seven.f10.g1.players.BetterPlusPlusPlayer",
				"seven.f10.g3.OurPlayer",
				"seven.f10.g4.G4Player",
				"seven.f10.g5.Ninja",
				"seven.f10.g6.InitialPlayer",
				"seven.f10.g7.ClassyPlayer",
				"seven.f10.g9.BiddingPlayer",
				"seven.g1.G1Player"};
//		String[] players = {"p1","p2","p3"};
		HashSet<Pair> pairsMade = new HashSet<Pair>();
		for(String p : players)
			for(String p2 : players)
			{
				if(!p.equals(p2))
				{
					Pair z = new Pair(p,p2);
					pairsMade.add(z);
				}
			}
		ArrayList<String> playerCombos = new ArrayList<String>();
		for(Pair p : pairsMade)
		{
			String z = p.p1 + " " + p.p1 + " " + p.p1 + " " + p.p1 + " " + p.p1 +" " + p.p2 + " " + p.p2 + " " + p.p2 + " " + p.p2 + " " + p.p2 ;
			playerCombos.add(z);
		}
		printVersions(playerCombos,"2v2");
	}
	static void generate2v2v2v2()
	{
		String[] players = {"seven.f10.g1.players.BetterPlusPlusPlayer",
				"seven.f10.g3.OurPlayer",
				"seven.f10.g4.G4Player",
				"seven.f10.g5.Ninja",
				"seven.g1.G1Player",
				"seven.f10.g6.InitialPlayer",
				"seven.f10.g7.ClassyPlayer",
				"seven.f10.g9.BiddingPlayer"
				};
		HashSet<HashSet<String>> pairsMade = new HashSet<HashSet<String>>();
		for(String p : players)
			for(String p2 : players)
				for(String p3: players)
					for(String p4 : players)
						if(!p.equals(p2) && 
						!p.equals(p3) &&
						!p.equals(p4) && 
						!p2.equals(p3) && 
						!p2.equals(p4) &&
						!p3.equals(p4))
						{
							HashSet<String> pair = new HashSet<String>();
							pair.add(p);
							pair.add(p2);
							pair.add(p3);
							pair.add(p4);
							pairsMade.add(pair);
						}
			
		ArrayList<String> playerCombos = new ArrayList<String>();
		for(HashSet<String> p : pairsMade)
		{
			String z = "";
			for(String pp : p)
				z += pp + " " + pp+ " ";
			z = z.substring(0,z.length()-1);
			playerCombos.add(z);
		}
		
		printVersions(playerCombos,"2v2v2v2 proper");
	}
	static void generateSingles()
	{
		String[] players = {"seven.f10.g1.players.BetterPlusPlusPlayer",
				"seven.f10.g3.OurPlayer",
				"seven.f10.g4.G4Player",
				"seven.f10.g5.Ninja",
				"seven.g1.G1Player",
				"seven.f10.g6.InitialPlayer",
				"seven.f10.g7.ClassyPlayer",
				"seven.f10.g9.BiddingPlayer",
				"seven.g0.LessStingyPlayer"
				};
		ArrayList<String> playerCombos = new ArrayList<String>();
		for(String p : players)
		{
			String z = p + " " + p + " "+p + " " + p;
			playerCombos.add(z);
			z = z + " " + z;
			playerCombos.add(z);
			z = z + " " + p + " " + p + " "+p + " " + p;
			playerCombos.add(z);
		}
		printVersions(playerCombos,"Singles");
	}
	static void printVersions(Collection<String> playercombos,String desc)
	{
		int j = 0;
		for(String p : playercombos)
		{
			for(int i =0;i<8;i++)
			{
				if(i == 7)
					j = 200;
				else
					j=100;
				Statement s;
				try {
					s = conn.createStatement();
					s.execute("INSERT INTO jobs (job) VALUES (\""+(tpl.replace("PLAYERS", p).replace("NUMSECRET", ""+i).replace("NUMROUNDS", ""+j).replace("DESC", desc))+"\")");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
            System.err.println ("Cannot connect to database server");
        }

	}
	public static void main(String[] args) {
		initDB("results.db");
		generate1v1s();
//			generate2v2s();
//			enerate5v5s();
//			generate8p();
//			generate9p();
//			generate2v2v2v2();
//			generateSingles();
		
	}
}
