/**
 * Class Item - Part of the "Zork" game.
 *
 * Author:  Travis Langston
 * Version: 1.0
 * Date:    October 13 2016
 *  
 *  This class provides the basic methods which will
 *  be inherited by the subclasses for the different
 *  types of items used in the game.
 */
public abstract class Item {
	
	public abstract String getName();
	
	public abstract boolean usable();
	
	public abstract boolean isFood();
}
