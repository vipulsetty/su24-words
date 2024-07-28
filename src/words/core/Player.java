package words.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Player {
    // array of all possible Words for the game
    protected Word[] wordlist;

    // for writing to the screen
    protected Logger logger = Logger.getLogger(this.getClass().getName());

    // the player's ID, so that you can get this player's info from the game
    protected int myID;

    // the current letters held by this player in the current round
    protected List<Character> myLetters = new ArrayList<>();

    // this indicates the letters held by all players during a round
    // position #0 is a List of letters held by player #0 and so on
    protected List<List<Character>> playerLetters = new ArrayList<>();

    // this keeps track of the number of players in the game
    protected int numPlayers;

    // keeps track of all players' scores in the current game
    protected List<Integer> scores;


    /**
     * This method is called at the start of a new game.
     * This is where you'll learn your playerID—save this!
     * @param playerID the ID (0 through numPlayers - 1) of your player this game.
     * @param numPlayers the number of players in this game
     */
    public void startNewGame(int playerID, int numPlayers) {
        myID = playerID; // store my ID

        initializeWordlist(); // read the file containing all the words

        this.numPlayers = numPlayers; // so we know how many players are in the game
    }

    /**
     * This is a helper method that reads the list of words and stores it in a List
     * so that the returnWord() method can choose a word from it at the end of a round.
     */
    protected void initializeWordlist() {
        String line = null;
        ArrayList<Word> wtmp = new ArrayList<>(55000);
        try {
            BufferedReader r = new BufferedReader(new FileReader("files/wordlist.txt"));
            while (null != (line = r.readLine())) {
                wtmp.add(new Word(line.trim()));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred.", e);
        }
        wordlist = wtmp.toArray(new Word[0]);
    }

    /**
     * This method is called at the start of a new round in the game.
     * The player can some initialization if it so chooses.
     * @param secretstate This player's secret state, including its secret letters
     */
    public void startNewRound(SecretState secretstate){
        myLetters.clear(); // clear the letters that I have
        // this puts the secret letters into the currentLetters List
        myLetters.addAll(secretstate.getSecretLetters().stream().map(Letter::getCharacter).toList());

        playerLetters.clear(); // clear the letters that all the players have
        for (int i = 0; i < numPlayers; i++) {
            playerLetters.add(new LinkedList<Character>()); // initialize each player's list of letters
        }
        /*
        Note that although the letters that I have will be in the playerLetters List, the playerLetters
        List doesn't include my secret letters.
         */
    }

    /**
     * Method is called when it is this player's turn to submit a bid. They should guarantee that
     * the value they return is between 0 and secretstate.getScore(). The highest bid wins, but
     * the winner only pays the price of the second-highest bid.
     *
     * Subclasses of BasicPlayer must implement this according to their strategy.
     *
     * @param bidLetter the Letter currently up for bidding on
     * @param playerBidList an unmodifiable list of previous bids from this game
     * @param totalRounds the total number of rounds in the game, which is different from the current round
     * @param playerList list of all player names
     * @param secretstate set of data that is stored unique to each player (their score and secret letters)
     * @param playerID the ID of the player being asked to bid (can ignore)
     * @return the amount to bid for the letter
     */
    public abstract int bid(Letter bidLetter, List<PlayerBids> playerBidList,
                   int totalRounds, ArrayList<String> playerList,
                   SecretState secretstate, int playerID) ;





    /**
     * This method is called after each player makes their bid and the letter
     * is awarded to one of the players.
     * @param currentBids a PlayerBids object indicating the result of the bidding
     */
    public void bidResult(PlayerBids currentBids){
        // update the List of which player has which letters
        playerLetters.get(currentBids.getWinnerID()).add(currentBids.getTargetLetter().getCharacter());

        // see if I won the bid and add it to my letters if so
        if (myID == currentBids.getWinnerID()) {
            myLetters.add(currentBids.getTargetLetter().getCharacter());
        }
    }



    /**
     * This method is called at the end of each round. Given the
     * total set of letters (public and private), assemble a word
     * and return it (as a String, not a Word). The game will
     * reject your word if you shouldn't be able to build it
     * given your letters.
     * @return the word you create using your letters
     */
    public String returnWord() {


        // verbose way of building a String from a bunch of characters.
        char c[] = new char[myLetters.size()];
        for (int i = 0; i < c.length; i++) {
            c[i] = myLetters.get(i);
        }
        String s = new String(c);

        // iterate through our word list. If we find one we can build,
        // check to see if it's an improvement on the best one we've seen.
        Word ourletters = new Word(s);
        Word bestword = new Word("");
        for (Word w : wordlist) {
            if (ourletters.contains(w)) {
                if (w.score > bestword.score) {
                    bestword = w;
                }

            }
        }

        return bestword.word;
    }


    /**
     * At the end of each round, the GameController
     * passes an ArrayList of scores to each player.
     * @param scores list mapping player id to scores
     */
    public void updateScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

}
