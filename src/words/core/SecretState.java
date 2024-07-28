/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package words.core;

import java.util.ArrayList;

/**
 * Represents the secret state for an individual player.
 */
public class SecretState {
    ArrayList<Letter> secretLetters;

    int score;
    int total_letters;
    public SecretState(int scoreL)
    {

        secretLetters = new ArrayList<Letter>();
        score = scoreL;
    }
	/**
	 * @return the secretLetters
	 */
	public ArrayList<Letter> getSecretLetters() {
		return secretLetters;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @return the total_letters
	 */
	public int getTotalLetters() {
		return total_letters;
	}

    
}
