package words.g0;

import java.util.ArrayList;
import java.util.List;

import words.core.Letter;
import words.core.Player;
import words.core.PlayerBids;
import words.core.SecretState;

public class LetterValuePlayer extends Player {

    /**
     * Method is called when it is this player's turn to submit a bid. They should guarantee that
     * the value they return is between 0 and secretstate.getScore(). The highest bid wins, but
     * the winner only pays the price of the second-highest bid.
     *
     * This implementation just bids the value of the letter
     *
     * @param bidLetter the Letter currently up for bidding on
     * @param playerBidList an unmodifiable list of previous bids from this game
     * @param totalRounds the total number of rounds in the game, which is different from the current round
     * @param playerList list of all player names
     * @param secretstate set of data that is stored unique to each player (their score and secret letters)
     * @param playerID the ID of the player being asked to bid (can ignore)
     * @return the amount to bid for the letter
     */
    @Override
    public int bid(Letter bidLetter, List<PlayerBids> playerBidList,
                   int totalRounds, ArrayList<String> playerList,
                   SecretState secretstate, int playerID) {
        // Always bid the letter value of the letter on auction.
        int bidLetterValue = bidLetter.getValue();
        int myBid = Math.min(secretstate.getScore(), bidLetterValue);
        //logger.info(String.format("Bidding %d for letter %c", bidLetterValue, bidLetter.getAlphabet()));
        return myBid;
    }



}
