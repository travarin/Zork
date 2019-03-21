/*
 * Class Room - a room in an adventure game.
 *
 * Author:  Michael Kolling
 * Version: 1.2
 * Date:    August 2000
 * 
 * This class is part of Zork. Zork is a simple, text based adventure game.
 *
 * "Room" represents one location in the scenery of the game.  It is 
 * connected to at most four other rooms via exits.  The exits are labeled
 * north, east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 */

import java.util.*;
import static java.lang.System.*;

class Room 
{
	private String 				  name;
    private String                description;
    private HashMap               exits;        // stores exits of this room.
    private HashMap<String, Item> items;
    private Creature              guardian;
    private Closing 			  door;
    private boolean               hasCreature;
    private boolean 			  hasDoor;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     */
    public Room(String nm, String description, boolean hasGuardian, Creature guard, boolean hasClosing, Closing close) 
    {
    	name             = nm;
        this.description = description;
        exits 			 = new HashMap();
        items 			 = new HashMap<String, Item>();
        hasCreature 	 = hasGuardian;
        guardian 		 = guard;
        hasDoor 		 = hasClosing;
        door 			 = close;
    }

    /**
     * Define the exits of this room.  Every direction either leads to
     * another room or is null (no exit there).
     */
    public void setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null)
            exits.put("north", north);
        if(east != null)
            exits.put("east", east);
        if(south != null)
            exits.put("south", south);
        if(west != null)
            exits.put("west", west);
    }
    
    /*
     * Accessor method to get the name of the room.
     */
    public String getName()
    {
    	return name;
    }
    
    /*
     * Returns whether or not the room has a guardian that is preventing you from leaving. 
     */
    public boolean blockedByCreature()
    {
    	if ( hasCreature  )
    	{
       		out.println("A " + guardian.getName() + " blocks your path.");
        	return true;
    	}
    	return false;
    }
    
    public boolean blockedByDoor()
    {
    	if ( hasDoor )
    	{
    		if ( door.isLocked() ) 
    		{
				out.println("A " + door.getName() + " blocks your path. You need a " + door.getCondition() + " to get by. ");
				return true;
			}
    	}
    	return false;
    }
    
    /*
     * Accessor method used to allow other classes to set up combat between the player
     * and the creature. 
     */
    public Creature getGuardian()
    {
    	return guardian;
    }
    
    /*
     * Gets rid of the creature after it's health runs out. 
     */
    public void killCreature()
    {
    	guardian = null;
    	hasCreature = false;
    }
    
    /*
     * Used to verify if the player has the trigger object needed to unlock the door. 
     * If so, then the door is unlocked and the player can pass. 
     */
    public boolean unlockDoor(Trigger con)
    {
    	if ( con.getName().equals(door.getCondition() ))
    	{
    		out.println("You used the " + con.getName() + " to open the " + door.getName() + "!");
    		door.lock(false);
    		return false;
    	}
    	else
    	{
    		out.println("The " + con.getName() + " didn't work!");
    		return true;
    	}
    }
    
    /*
     * Adds an item to the room. Will describe it in the description. 
     */
    public void addItems(String name, Item item)
    {
    	items.put(name, item);
    }
    
    /*
     * Check to see if the inventory of the room contains an item. 
     */
    public boolean hasItem(String name)
    {
    	return items.containsKey(name);
    }
    
    /*
     * Checks to see if that item in the inventory is usable. 
     */
    public boolean isUsable(String name)
    {
    	return items.get(name).usable();
    }
    
    /*
     * Takes an item from the room, and returns it to add it to 
     * the player's inventory. 
     */
    public Item takeItem(String name)
    {
    	return items.remove(name);
    }
    
    /*
     * Allows other classes to access this item. 
     */
    public Item getItem(String name)
    {
    	return items.get(name);
    }
    
    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String shortDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, on the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String longDescription()
    {
        return "You are in " + description + ".\n" + exitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west ".
     */
    private String exitString()
    {
        String returnString = "Exits:";
		Set keys = exits.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room nextRoom(String direction) 
    {
        return (Room)exits.get(direction);
    }
}
