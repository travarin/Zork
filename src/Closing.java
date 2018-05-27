/**
 * Class Closing - Part of the "Zork" game.
 *
 * Author:  Travis Langston
 * Version: 1.0
 * Date:    October 18 2016
 *  
 *  This class provides the blueprint for creating 
 *  closing objects like doors or windows, which
 *  are used to block exits in Zork. It has the
 *  ability to be locked and need some sort
 *  of trigger object to open it, like a key 
 *  for a door, or a brick for a window.  
 */
public class Closing {
	
	private String  name;
	private String  lockCondition;
	private boolean locked;
	
	/*
	 * Initializes the type of object ( door or window )
	 * and whether or not that object is locked. 
	 */
	public Closing(String nm, String con, boolean lock)
	{
		name   		  = nm;
		lockCondition = con;
		locked 		  = lock;
	}
	
	/*
	 * Accessor method to return the name of the object. 
	 */
	public String getName()
	{
		return name;
	}
	
	/*
	 * Accessor method to return the condition needed to unlock the object. 
	 */
	public String getCondition()
	{
		return lockCondition;
	}
	
	/*
	 * Accessor method to check if the door is locked. 
	 */
	public boolean isLocked()
	{
		return locked;
	}
	
	/*
	 * Used to either lock or unlock the door. 
	 */
	public void lock(boolean lock)
	{
		locked = lock;
	}
}
