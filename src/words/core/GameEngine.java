package words.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.*;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GameEngine.java
 *
 * Created on Nov 1, 2009, 6:49:08 PM
 */

/**
 *
 * @author Satyajeet
 */
public class GameEngine extends javax.swing.JFrame {
	
	static Connection conn = null;
	private void initDB(String filename)
	{
		if(conn != null)
			return;
        try
        {
            String url = "jdbc:sqlite:"+filename;
            Class.forName ("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection (url);
            System.out.println ("Database connection established");
        }
        catch (Exception e)
        {
            System.err.println ("Cannot connect to database server");
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

    public GameEngine(String string) {
    	if(!string.equals("text"))
    	{
	        initComponents();
	        myInit();
    	}
    }
    static int n_cached = 0;
    private static class GameRunner implements Runnable
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
				gamecontroller.bannedPlayers = new HashSet<Player>();
                while(play == true)
                {
                    score = gamecontroller.GamePlay(gameconfig).retValue;
                    
                    	thisGameEngine.updateUI();
                if(score ==-1)
                {
                    // Game must have ended, since you have a score now
                   play = false;
                   String gameover = "Game Over.";
                   for(Player p : gamecontroller.bannedPlayers)
           		{
           			System.err.println("Kicked out: " +p.getClass().getName());
           		}
                   if(conn == null)
                	   JOptionPane.showMessageDialog(GameScreen, gameover);
                }

                // Update user interface!
                Thread.sleep(gameconfig.gameDelay);

                }
            } catch (InterruptedException ex) {
                System.out.println("Game runner interrupted");
            }
        }


    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ScreenTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        PlayerListbox = new javax.swing.JList();
        input_combo = new javax.swing.JComboBox();
        AddPlayerButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        secretBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        GameDelaySlider = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        RoundsBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        NewGameButton = new javax.swing.JButton();
        StepButton = new javax.swing.JButton();
        PlayButton = new javax.swing.JButton();
        PauseButton = new javax.swing.JButton();
        TournamentButton = new javax.swing.JButton();
        LetterLabel = new javax.swing.JLabel();
        RoundsLabel = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMemUsage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Seven Letter Word - Game Simulator");

        ScreenTable.setFont(new java.awt.Font("Courier", 0, 18)); // NOI18N
        ScreenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Player Name", "Score", "Secret Letters", "Open Letters", "Bid", "Word Returned", "Points", "Letters used"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ScreenTable.setRowHeight(30);
        ScreenTable.setRowMargin(5);
        int[] widths = {100, 85, 300, 310, 65, 200, 65, 150};
        TableColumnModel columnModel = ScreenTable.getColumnModel();
        for (int i = 0; i < widths.length; i++) {
            if (i < columnModel.getColumnCount()) {
                columnModel.getColumn(i).setPreferredWidth(widths[i]);
//                columnModel.getColumn(i).
            }
            else break;
        }
        jScrollPane1.setViewportView(ScreenTable);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane2.setViewportView(PlayerListbox);

        input_combo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        AddPlayerButton.setText("Add Player");
        AddPlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPlayerButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Remove");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Hidden Letters: ");

        secretBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));

        jLabel2.setText("Game Delay (0-1000ms):");

        GameDelaySlider.setMaximum(1000);
        GameDelaySlider.setValue(800);
        GameDelaySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                GameDelaySliderStateChanged(evt);
            }
        });

        jLabel3.setText("Number of Rounds:");

        String[] labels = new String[50];
        for (int i = 0; i < labels.length; i++) {
            if (i < 20) {
                labels[i] = String.valueOf(i+1);
            } else {
                labels[i] = String.valueOf(20 + (i - 20) * 20);
            }
        }

        RoundsBox.setModel(new javax.swing.DefaultComboBoxModel<>(labels));

        jLabel4.setText("Program Parameters:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                    .addComponent(input_combo, 0, 223, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(AddPlayerButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                    .addComponent(GameDelaySlider, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(secretBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(RoundsBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel4))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddPlayerButton)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(secretBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(RoundsBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GameDelaySlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        NewGameButton.setText("Begin New Game");
        NewGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGameButtonActionPerformed(evt);
            }
        });

        StepButton.setText("Step");
        StepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StepButtonActionPerformed(evt);
            }
        });

        PlayButton.setText("Play");
        PlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayButtonActionPerformed(evt);
            }
        });

        PauseButton.setText("Pause");
        PauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PauseButtonActionPerformed(evt);
            }
        });

        TournamentButton.setText("Play Tournament");
        TournamentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TournamentButtonActionPerformed(evt);
            }
        });

        LetterLabel.setBackground(new java.awt.Color(255, 102, 102));
        LetterLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        LetterLabel.setText("Current Letter:");

        RoundsLabel.setBackground(new java.awt.Color(255, 102, 102));
        RoundsLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        RoundsLabel.setText("Current Round:");
        RoundsLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NewGameButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StepButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PlayButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PauseButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TournamentButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LetterLabel)
                .addGap(18, 18, 18)
                .addComponent(RoundsLabel)
                .addContainerGap(247, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewGameButton, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(StepButton, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(PlayButton, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(PauseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(TournamentButton, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(LetterLabel)
                    .addComponent(RoundsLabel))
                .addContainerGap())
        );

        StatusLabel.setBackground(new java.awt.Color(153, 153, 255));
        StatusLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        StatusLabel.setText("Status:");

        jLabel5.setText("Program Controls:");

        txtMemUsage.setText("MEM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(StatusLabel)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 878, Short.MAX_VALUE)
                        .addComponent(txtMemUsage)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtMemUsage)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(StatusLabel))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddPlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddPlayerButtonActionPerformed
        // TODO add your handling code here:
        String player = (String)input_combo.getSelectedItem();
        //PlayerListbox.setModel(new DefaultListModel());
        DefaultListModel dlm = (DefaultListModel) PlayerListbox.getModel();
        dlm.add(dlm.getSize(),(Object)player);


    }//GEN-LAST:event_AddPlayerButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         DefaultListModel dlm = (DefaultListModel) PlayerListbox.getModel();
         int index;
         do{
             index = PlayerListbox.getSelectedIndex();
             try{
             dlm.remove(index);
             } catch(Exception e)
             {
                 // Do nothing!
             }

         }while(PlayerListbox.getSelectedIndices().length != 0);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void NewGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameButtonActionPerformed
        // TODO add your handling code here:

        gamecontroller = new GameController();
        // Lets create the playerstate object
        DefaultListModel dlm = (DefaultListModel) PlayerListbox.getModel();
        ArrayList<String> plist = new ArrayList<String>();
        for(int loop=0;loop<dlm.getSize();loop++)
        {
            plist.add((String)dlm.get(loop));
        }
        int numR = Integer.parseInt((String)RoundsBox.getSelectedItem());
        int secret_objs = Integer.parseInt((String)secretBox.getSelectedItem());


        iocontroller = new IOController();
        gameconfig = new GameConfig(plist, secret_objs, iocontroller,numR);
        thisGameEngine.updateUI();
    }//GEN-LAST:event_NewGameButtonActionPerformed

    private void GameDelaySliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_GameDelaySliderStateChanged
        // TODO add your handling code here:
        if(gameconfig != null)
        {
            gameconfig.gameDelay = GameDelaySlider.getValue();
        }
    }//GEN-LAST:event_GameDelaySliderStateChanged

    private void PlayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayButtonActionPerformed
        // TODO add your handling code here:

        grunner = new Thread(new GameRunner());
        grunner.start();

    }//GEN-LAST:event_PlayButtonActionPerformed

    private void PauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PauseButtonActionPerformed
        // TODO add your handling code here:
        if(grunner != null)
        {
            grunner.interrupt();
        }

    }//GEN-LAST:event_PauseButtonActionPerformed

    private void StepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StepButtonActionPerformed
        // TODO add your handling code here:
        if(gameconfig != null)
        {
            int score = gamecontroller.GamePlay(gameconfig).retValue;
            updateUI();
            if(score == -1)
            {
                String gameover = "Game Over";
                JOptionPane.showMessageDialog(GameScreen, gameover);
            }
            else
            {

                // Update user interface
            }
        }


    }//GEN-LAST:event_StepButtonActionPerformed

    private void TournamentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TournamentButtonActionPerformed
       
    }//GEN-LAST:event_TournamentButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
    	System.setOut(
    		    new PrintStream(new OutputStream() {
					@Override
					public void write(int b) throws IOException {						
					}
				}));
    	if(args.length == 0 || args.length == 1 && args[0] == "gui")
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new GameEngine("gui").setVisible(true);
                }
            });
    	else if(args.length == 5)
    		new GameEngine("text").runText(args);
    	else if(args.length == 1 && args[0].equals("tournament"))
    		new GameEngine("text").runTourn();
    	else
    		System.err.println("Usage: java GameEngine \"<player class list with spaces between each>\" num_secret num_rounds tourn_desc out_file_name");
    }
    private void runTourn() {
		// TODO Auto-generated method stub
    	initDB("results.db");
		try {
			Statement s = GameEngine.conn.createStatement();
			int n = s.executeQuery("SELECT COUNT(*) FROM jobs where started is null").getInt(1);
			while(n > 0)
			{
				ResultSet rs = s.executeQuery("SELECT job,id from jobs where started is null limit 1");
				if(rs.next())
				{
					int nz = rs.getInt(2);
					String job = (rs.getString(1));
					Statement s2 = GameEngine.conn.createStatement();
					s2.executeUpdate("UPDATE jobs set started=1 where id="+rs.getInt(2));
					rs.close();
					String[] args2 = job.split(";");
					System.err.println("Runnign text");
					runText(args2);
					s2.executeUpdate("UPDATE jobs set started=1 where id="+nz);
				}
				n = s.executeQuery("SELECT COUNT(*) FROM jobs where started is null").getInt(1);
//					n--;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static int tournament_id = 0;
    private void runText(String[] args) {
    	args[0] = "seven.f10.g5.Ninja seven.f10.g5.Ninja seven.f10.g6.InitialPlayer seven.f10.g6.InitialPlayer seven.f10.g4.G4Player seven.f10.g4.G4Player seven.g1.G1Player seven.g1.G1Player";
    	args[1] = "5";
    	args[2] = "100";
    	args[3] = "asdf";
//		System.exit(-1);
    	String players[] = args[0].split(" ");
    	int numSecret = Integer.valueOf(args[1]);
    	int numRounds = Integer.valueOf(args[2]);
    	iocontroller = new IOController();
    	String filename = args[4];
    	String desc = args[3];
    	java.net.InetAddress localMachine = null;
		try {
			localMachine = java.net.InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	String hostname =  localMachine.getHostName();
    	initDB(filename);
		try {
			Statement s2 = conn.createStatement();
			s2.execute("INSERT INTO tournament (num_hidden,num_rounds,start,end,source,desc) VALUES ("+numSecret+","+numRounds+",\""+DateFormat.getDateTimeInstance().format(new Date())+"\"," +
					"null,\""+hostname+"\", \""+desc+"\");");
			tournament_id = s2.getGeneratedKeys().getInt(1);
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

        grunner = new Thread(new GameRunner());
        grunner.start();
        
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddPlayerButton;
    private javax.swing.JSlider GameDelaySlider;
    private javax.swing.JLabel LetterLabel;
    private javax.swing.JButton NewGameButton;
    private javax.swing.JButton PauseButton;
    private javax.swing.JButton PlayButton;
    private javax.swing.JList PlayerListbox;
    private javax.swing.JComboBox RoundsBox;
    private javax.swing.JLabel RoundsLabel;
    private javax.swing.JTable ScreenTable;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JButton StepButton;
    private javax.swing.JButton TournamentButton;
    private javax.swing.JComboBox input_combo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox secretBox;
    private javax.swing.JLabel txtMemUsage;
    // End of variables declaration//GEN-END:variables


    public void myInit()
    {
        iocontroller = new IOController();
        populatePlayerList();
        PlayerListbox.setModel(new DefaultListModel());
        GameScreen = ScreenTable;
        thisGameEngine = this;
        Runtime r = Runtime.getRuntime();
		long free = r.freeMemory();
		txtMemUsage.setText(((r.totalMemory() - free)/1024/1024) + "/"+(r.maxMemory()/1024/1024)+"MB");
        memUpdate= new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Runtime r = Runtime.getRuntime();
				long free = r.freeMemory();
//				logger.debug("Currently using " +
//						(r.totalMemory() - free) + " bytes(?) -- free " + free
//				);
				txtMemUsage.setText(((r.totalMemory() - free)/1024/1024) + "/"+(r.maxMemory()/1024/1024)+"MB");

			}
		});
        memUpdate.start();
        this.validate();
    }

    public void populatePlayerList()
    {
        input_combo.removeAllItems();
        ArrayList<String> playernames = iocontroller.getPlayerList();
        input_combo.setModel(new DefaultComboBoxModel());
        DefaultComboBoxModel dcm = (DefaultComboBoxModel)input_combo.getModel();
        for(int loop=0;loop<playernames.size();loop++)
        {
            dcm.addElement((Object)playernames.get(loop));
        }
    }

    public void updateUI()
    {
        // First remove the current rows


        String bidletter = "";
        DefaultTableModel dtm = (DefaultTableModel)ScreenTable.getModel();

        while(dtm.getRowCount() != 0)
        {
            dtm.removeRow(0);

        }
        // Now populate the table room by room:
        for(int loop=0;loop<gameconfig.PlayerList.size();loop++)
        {
            String playername = gameconfig.PlayerList.get(loop);
            int score = gameconfig.secretstateList.get(loop).score;
            String secretLetters = "";
            for(int letterloop=0;letterloop<gameconfig.secretstateList.get(loop).secretLetters.size();letterloop++)
            {
                secretLetters += " " + gameconfig.secretstateList.get(loop).secretLetters.get(letterloop).character;
            }
            String openLetters = "";
            for(int letterloop=0;letterloop<gameconfig.openstateList.get(loop).openLetters.size();letterloop++)
            {
                openLetters += " " + gameconfig.openstateList.get(loop).openLetters.get(letterloop).character;
            }
            int lastbidIndex = gameconfig.BidList.size() - 1;
            int bidvalue = 0;
            try{
            if(gameconfig.BidList.get(lastbidIndex) != null)
            {
                bidvalue = gameconfig.BidList.get(lastbidIndex).bidValues.get(loop);
                bidletter = gameconfig.BidList.get(lastbidIndex).TargetLetter.character.toString();
                LetterLabel.setText("Current Letter: " + bidletter);
                PlayerBids l = gameconfig.BidList.get(lastbidIndex);
                String formatString = "<html>Status: Letter won by player %d [%s]<br>"
							+ "Winning bid: %d (Actually paid: %d)</html>";
                int winner_id = l.getWinnerID();
				Object formatargs[] = {
						winner_id, l.getWonBy(), l.getBidValues().get(winner_id),l.getWinAmount()
				};
                StatusLabel.setText(String.format(formatString, formatargs));
            }
            }catch(Exception ex)
            {
                // Do nothing
            }
            String wordReturned = "";
            try{
            if(gameconfig.PlayerWords.get(loop) != null)
            {
                wordReturned = gameconfig.PlayerWords.get(loop);
                if(wordReturned.length() == 7)
                	wordReturned = "<html><b>"+wordReturned+"</b></html>";
                else if(wordReturned.length() == 0)
                	wordReturned = " ";
            }
            } catch(Exception ex)
            {
                // Do nothing
            }
            String wbstring = "";
            String points = "";
            if(gameconfig.wordbag.size() !=0)
            {
                wbstring = gameconfig.wordbag.get(loop);

            }

            if(gameconfig.lasPoints.size() != 0)
            {
                points = gameconfig.lasPoints.get(loop).toString();

            }
            Object[] UIData = new Object[]{playername.substring(playername.indexOf(".")+1),score + "   ",secretLetters,openLetters,bidvalue+ "   ",wordReturned,points,wbstring};
            dtm.addRow(UIData);
            RoundsLabel.setText("Current round: " + (gameconfig.current_round + 1));

        }

    }

}
