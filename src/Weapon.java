/**
 * Class Weapon - Part of the "Zork" game.
 *
 * Author:  Travis Langston
 * Version: 1.1
 * Date:    October 20 2016
 *  
 *  This class provides the blueprint for creating 
 *  weapon objects like swords or spears, which
 *  are used to block exits in Zork. The class 
 *  contains several presets for each type of 
 *  weapon, which have a set of skills they can use. 
 *  It also contains an attack method. 
 */
import static java.lang.System.*;
import java.util.*;
public class Weapon extends Item {
	
	private int                    level;
	private double                 multiplier;
	private String  			   type;
	private boolean 			   canBlock;
	private HashMap<String, Skill> skills;
	
	/*
	 * Constructor to initialize the fields
	 * according to the preset for the class
	 * the player chooses. 
	 */
	public Weapon(String type, int lvl)
	{
		level      = lvl;
		multiplier = ((double) level) / 5;
		this.type  = type;
		canBlock   = false;
		skills     = new HashMap<String, Skill>();
		if ( type.equals("sword") )
		{
			Skill slash  = new Skill((int) (multiplier * 10),(int) (multiplier * 25),(int) (multiplier * 10), 0, 0.1, "Slash");
		    Skill stab   = new Skill((int) (multiplier * 20),(int) (multiplier * 35),(int) (multiplier * 20), 0, 0.1, "Stab");
		    Skill sweep  = new Skill((int) (multiplier * 20),(int) (multiplier * 35),(int) (multiplier * 30), 0, 0.0, "Sweeping Cut");
		    Skill swing  = new Skill((int) (multiplier * 30),(int) (multiplier * 45),(int) (multiplier * 30), 0, 0.2, "Swing");
		    Skill rend   = new Skill((int) (multiplier * 40),(int) (multiplier * 55),(int) (multiplier * 40), 0, 0.3, "Rend");
		    Skill brutal = new Skill((int) (multiplier * 40),(int) (multiplier * 55),(int) (multiplier * 50), 0, 0.2, "Brutal Assault");
		    skills.put("Slash", slash);
		    skills.put("Stab", stab);
		    skills.put("Sweeping Cut", sweep);
		    skills.put("Swing", swing);
		    skills.put("Rend", rend);
		    skills.put("Brutal Assault", brutal);
			canBlock = true;
		}
		else if ( type.equals("bow") )
		{
			Skill quick     = new Skill((int) (multiplier * 20),(int) (multiplier * 35),(int) (multiplier * 10), 0, 0.3, "Quick Shot");
			Skill fly       = new Skill((int) (multiplier * 30),(int) (multiplier * 45),(int) (multiplier * 20), 0, 0.2, "Let Fly");
			Skill explosive = new Skill((int) (multiplier * 40),(int) (multiplier * 55),(int) (multiplier * 30), 0, 0.3, "Explosive Arrow");
			Skill accurate  = new Skill((int) (multiplier * 50),(int) (multiplier * 65),(int) (multiplier * 40), 0, 0.05, "Accurate Shot");
			Skill barrage   = new Skill((int) (multiplier * 80),(int) (multiplier * 95),(int) (multiplier * 50), 0, 0.4, "Barrage");
			skills.put("Quick Shot", quick);
			skills.put("Let Fly",fly);
			skills.put("Explosive", explosive);
			skills.put("Accurate Shot", accurate);
			skills.put("Barrage", barrage);
		}
		else if ( type.equals("shield") )
		{
			Skill swipe = new Skill((int) (multiplier * 20),(int) (multiplier *  35),(int) (multiplier * 30), 0, 0.0, "Shield Swipe");
			Skill blow  = new Skill((int) (multiplier * 30),(int) (multiplier *  45),(int) (multiplier * 40), 0, 0.0, "Shield Blow");
			Skill bash  = new Skill((int) (multiplier * 40),(int) (multiplier *  55),(int) (multiplier * 60), 0, 0.0, "Bash");
			skills.put("Shield Swipe", swipe);
			skills.put("Shield Blow", blow);
			skills.put("Bash", bash);
			canBlock = true;
		}
		else if ( type.equals("staff") )
		{
			Skill strike    = new Skill((int) (multiplier * 15),(int) (multiplier * 30),(int) (multiplier * 20), 0, 0.05, "Staff Strike");
			Skill wind      = new Skill((int) (multiplier * 20),(int) (multiplier * 35),(int) (multiplier * 25), 0, 0.1, "Wind");
			Skill fire      = new Skill((int) (multiplier * 30),(int) (multiplier * 45),(int) (multiplier * 35), 0, 0.1, "Fire");
			Skill blizzard  = new Skill((int) (multiplier * 40),(int) (multiplier * 55),(int) (multiplier * 55), 0, 0.05, "Blizzard");
			Skill lightning = new Skill((int) (multiplier * 60),(int) (multiplier * 75),(int) (multiplier * 65), 0, 0.2, "Lightning");
			Skill heal      = new Skill(0, 0, 30, 50, 0.0, "Heal");
			skills.put("Staff Strike", strike);
			skills.put("Wind", wind);
			skills.put("Fire", fire);
			skills.put("Blizzard", blizzard);
			skills.put("Lightning", lightning);
			skills.put("Heal", heal);
		}
		else if ( type.equals("knife") )
		{
			Skill stab  = new Skill((int) (multiplier * 10),(int) (multiplier * 20),(int) (multiplier * 10), 0, 0.1, "Stab");
			Skill blind = new Skill((int) (multiplier * 20),(int) (multiplier * 30),(int) (multiplier * 15), 0, 0.1, "Blindside");
			Skill slash = new Skill((int) (multiplier * 30),(int) (multiplier * 50),(int) (multiplier * 20), 0, 0.1, "Throat Slash");
			skills.put("Stab", stab);
			skills.put("Blindside", blind);
			skills.put("Throat Slash", slash);
		}
		
	}
	
	/*
	 * Accessor method to return the type of weapon.
	 */
	public String getName()
	{
		return type;
	}

	/*
	 * Used to return the map of skills the weapon has, 
	 * to be called by the player in combat. 
	 */
	public Map<String, Skill> getSkills()
	{
		return skills;
	}
	
	/*
	 * Used to check if the skill the player wants to use exists. 
	 */
	public boolean hasSkill(String x)
	{
		return skills.containsKey(x);
	}
	
	/*
	 * Used to access an individual skill, to be used in combat. 
	 */
	public Skill getSkill(String x)
	{
		return skills.get(x);
	}
	
	/*
	 * Used to print out all of the skills the weapon has along
	 * with a description of each one. 
	 */
	public void printSkills()
	{
		for ( String key : skills.keySet() )
		{
			out.println(skills.get(key));
		}
	}
	
	/*
	 * Used to block attacks. Gives the player
	 * a 50% chance of blocking if they have 
	 * a shield, and a 25% chance to parry
	 * with a sword.
	 */
	public int block(int damageDone)
	{
		if ( canBlock )
		{
			int x = 0;
			if ( type.equals("shield") )
			{
				x = (Math.random() <= 0.5 ) ? 1 : 2;
			}
			else
			{
				x = ( Math.random() <= 0.25 ) ? 1 : 2;
			}
			if ( x == 1 )
			{
				out.println("The attack was blocked!");
				return 0;
			}
		}
		return damageDone;
	}

	/*
	 * Means the player or creatures can take this item
	 * and store it in their inventories for later use. 
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
