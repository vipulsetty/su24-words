/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package words.core;

/**
 *
 * Represents a letter in the game, including the character and its value.
 */
public class Letter implements Cloneable{

	protected Object clone(){
		Letter l = new Letter(character,value);
		return l;
	}
    Character character;
    int value;
    public Letter(Character c, int s)
    {
        character = c;
        value = s;
    }
	/**
	 * @return the character
	 */
	public Character getCharacter() {
		return character;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
