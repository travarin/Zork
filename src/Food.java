/**
 * Class Food - Part of the "Zork" game.
 *
 * Author:  Travis Langston
 * Version: 1.1
 * Date:    October 21 2016
 *  
 *  This class provides the blueprint for creating 
 *  food objects to be eaten by the player in order
 *  to restore their health. The food also has a chance
 *  of being poisoned, which would instead hurt the player.
 */
public class Food extends Item {
	
	private String  name;
	private int     health;
	private int     power;
	
	/*
	 * Constructor to initialize fields and determine if the food is poisoned. 
	 */
	public Food(String nm, int x, int y)
	{
		name 	   = nm;
		health     = x;
		power      = y;
	}
	
	/*
	 * Accessor method to get the type of food the object is. 
	 */
	public String getName()
	{
		return name;
	}
	
	/*
	 * Used to restore the health of the player when they eat the food. 
	 */
	public int restoreHealth()
	{
		return health;
	}
	
	/*
	 * Used to restore the power of the player when they eat the food. 
	 */
	public int restorePower()
	{
		return power;
	}
	
	/*
	 * Means the player or creatures can use this item and store
	 * it in their inventories. 
	 */
	public boolean usable()
	{
		return true;
	}
	
	/*
	 * Checks to see if the item is food, to determine where the 
	 * player stores it. 
	 */
	public boolean isFood()
	{
		return true;
	}
}
