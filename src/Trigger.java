/**
 * Class Closing - Part of the "Zork" game.
 *
 * Author:  Travis Langston
 * Version: 1.0
 * Date:    December 13 2016
 *  
 *  This class provides the blueprint for creating 
 *  trigger objects like keys or explosives, used to 
 *  open closing objects like doors.   
 */
public class Trigger extends Item {
	
	private String name;
	
	public Trigger(String nm)
	{
		name = nm;
	}
	
	/*
	 * Accessor method to return the name of the object. 
	 */
	public String getName()
	{
		return name;
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
		return false;
	}

}
