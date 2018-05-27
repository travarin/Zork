/**
 * Class Container - Part of the "Zork" game.
 *
 * Author:  Travis Langston
 * Version: 1.0
 * Date:    October 14 2016
 *  
 *  This class provides the blueprint for creating 
 *  container objects like chests or bottles, which
 *  are used to hold other items in Zork. It has a 
 *  limit on how many items an object can store. 
 */
import java.util.*;
import static java.lang.System.*;
public class Container extends Item {
	
	private String	    name;
	private int    		capacity;
	private Stack<Item> inside;
	
	/*
	 * Initializes the name, size, and items inside the container. 
	 */
	public Container(String nm, int max)
	{
		name = nm;
		capacity = max;
		inside = new Stack<Item>();
	}
	
	/*
	 * Accessor method to return the name of the container. 
	 */
	public String getName()
	{
		return name;
	}
	
	/*
	 * Mutator method to put an item inside the container. Does not work if the container is full.
	 */
	public void addItems(Item x)
	{
		if ( inside.size()+1 > capacity )
		{
			out.println("The " + name + " is already full!");
		}
		else
		{
			inside.push(x);
		}
	}
	
	/*
	 * Used to see which item is at the top of the container. 
	 */
	public void viewInside()
	{
		
		if ( !inside.isEmpty() ) 
		{
			out.println("There is a " + inside.peek().getName() + " inside the " + name
					+ ". Any other items are buried beneath it. ");
		}
		else
		{
			out.println("There's nothing here!");
		}
	}
	
	/*
	 * Used to take an item off the top of the stack in the container. 
	 */
	public Item take()
	{
		if ( !inside.isEmpty() )
		{
			return inside.pop();
		}
		else
		{
			out.println("There's nothing here!");
			return null;
		}
	}
	
	/*
	 * Checks to see if the container is empty. 
	 */
	public boolean hasItem()
	{
		if ( inside.size() <= 0 )
		{
			return false;
		}
		return true;
	}
	
	/*
	 * Means the player or other creatures cannot pick up this item
	 * and store it in their inventory. 
	 */
	public boolean usable()
	{
		return false;
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
