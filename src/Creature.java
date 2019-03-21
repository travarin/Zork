/**
 * Class Creature - Part of the "Zork" game.
 *
 * Author:  Travis Langston
 * Version: 1.2
 * Date:    October 18 2016
 *  
 *  This class provides the blueprint for the creature object
 *  and contains fields for health, a collection
 *  for the items in the creature's inventory, and
 *  presets for the different class. Objects of this are 
 *  created for the player and the different creatures in 
 *  the game.  
 */
import java.util.*;
import static java.lang.System.*;
public class Creature {
	
	private String 			  name;
	private String            preset;
	private int 			  health;
	private int               maxHealth;
	private int               power;
	private int               maxPower;
	private double            level;
	private String            primary;
	private String    		  secondary;
	private Map<String, Item> inventory;
	private ArrayList<Food>	  food;

	
	/*
	 * Initializes the default values for health and inventory.
	 */
	public Creature(String nm, String preset, double level)
	{
		name        = nm;
		inventory   = new HashMap<String, Item>();
		food 		= new ArrayList<Food>();
		this.preset = preset;
		power       = 300;
		maxPower    = 300;
		this.level  = level;
		if ( preset.equals("archer") )
		{
			health     = 150;
			maxHealth  = 150;
			primary    = "bow";
			secondary  = "knife";
		}
		else if ( preset.equals("warrior") )
		{
			health      = 250;
			maxHealth   = 250;
			primary     = "sword";
			secondary   = "shield";
		}
		else if ( preset.equals("wizard") )
		{
			health     = 100;
			maxHealth  = 100;
			primary    = "staff";
			secondary  = "knife";
		}
		health    = (int) (health    * (level / 5));
		maxHealth = (int) (maxHealth * (level / 5));
		power     = (int) (power     * (level / 5));
		maxPower  = (int) (maxPower  * (level / 5));
	}
	
	/*
	 * Accessor method to return the name of the creature. 
	 */
	public String getName()
	{
		return name;
	}
	
	/*
	 * Accessor method to return health.
	 */
	public int getHealth()
	{
		return health;
	}
	
	/*
	 * Accessor method to return the power the player has. 
	 */
	public int getPower()
	{
		return power;
	}
	
	/*
	 * Accessor method to return the inventory.
	 */
	public Map<String, Item> getInventory()
	{
		return inventory;
	}
	
	/*
	 * Accessor method to return the arraylist with all the food the player has. 
	 */
	public ArrayList<Food> getFoodList()
	{
		return food;
	}
	
	/*
	 * Accessor method to return which preset class the creature is.
	 */
	public String getPreset()
	{
		return preset;
	}
	
	/*
	 * Accessor method to return an item from the inventory.
	 */
	public Item getItem(String key)
	{
		return inventory.get(key);
	}
	
	/*
	 * Accessor method to return a food item from the inventory.
	 */
	public Food getFood(String key)
	{
		
		for ( Food temp : food )
		{
			if ( temp.getName().equals(key) )
			{
				return temp;
			}
		}
		return null;
	}
	
	/*
	 * Accessor method to get the level of the creature.
	 */
	public double getLevel()
	{
		return level;
	}
	
	/*
	 * Accessor method to view all of the contents of the inventory.
	 */
	public void printInventory()
	{
		out.println(inventory);
	}
	
	/*
	 * Prints out all of the food the player has.
	 */
	public void printFood()
	{
		out.println(food);
	}
	
	public boolean hasFood(String key)
	{
		for ( Food temp : food )
		{
			if ( temp.getName().equals(key) )
			{
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Mutator method to add an item to the inventory. 
	 */
	public void addInventory(String key, Item value)
	{
		inventory.put(key, value);
	}
	
	/*
	 * Mutator method to add an item to the food list. 
	 */
	public void addFood(Food temp)
	{
		food.add(temp);
	}
	
	/*
	 * Mutator method to remove an item from the inventory. 
	 */
	public void removeInventory(String key)
	{
		inventory.remove(key);
	}
	
	public void removeFood(String key)
	{
		int indx = 0;
		while ( !food.get(indx).getName().equals(key))
		{
			indx++;
		}
		food.remove(indx);
	}
	
	/*
	 * Mutator method to change the health of the player. 
	 */
	public void setHealth(int change)
	{
		if ( health + change <= maxHealth )
		{
			health += change;
		}
		else
		{
			health = maxHealth;
		}
	}
	
	/*
	 * Mutator method to change the power of the player. 
	 */
	public void setPower(int change)
	{
		if ( power + change <= maxPower )
		{
			power += change;
		}
		else
		{
			power = maxPower;
		}
	}
	
	/*
	 * Used for determining the type of the weapons which appear at 
	 * certain places on the map.
	 */
	public String getPrimary()
	{
		return primary;
	}
	
	/*
	 * Used for determining the type of the weapons which appear at 
	 * certain places on the map.
	 */
	public String getSecondary()
	{
		return secondary;
	}
	
	/*
	 * Used to print out the player's stats. 
	 */
	public String toString()
	{
		return "Name: " + name + " Class: " + preset + " Health: " + health + " Power: " + power;
	}
}
