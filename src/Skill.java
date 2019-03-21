/**
 * Class Skill - Part of the "Zork" game.
 *
 * Author:  Travis Langston
 * Version: 1.0
 * Date:    October 28 2016
 *  
 *  This class provides the blueprint for creating 
 *  skill objects to be used by weapons in Zork. 
 *  The player will choose which skill they want 
 *  to use during combat. Each weapon will have 
 *  a map of the skills that can be used by it. 
 *  Each skill does a different amount of damage, 
 *  has a different chance of missing, or uses
 *  a different amount of power. 
 */
import java.util.*;
public class Skill {
	
	private int     lowDamage;
	private int	    highDamage;
	private int     power;
	private int     heal;
	private double  miss;
	private String  name;
	private Random  rand;
	
	/*
	 * Initializes the values for an object of skill, including how much damage it does, 
	 * how much power it consumes, how much it heals, the chance of missing, and 
	 * the name of the skill. 
	 */
	public Skill(int x, int y, int z, int a, double b, String c)
	{
		lowDamage  = x;
		highDamage = y;
		power      = z;
		heal       = a;
		miss 	   = b;
		name 	   = c;
		rand       = new Random();
	}
	
	/*
	 * Returns a random damage done within the range of the min and max values for damage. 
	 */
	public int getDamage()
	{
		int x = rand.nextInt(highDamage - lowDamage) + lowDamage; 
		return x;
	}
	
	/*
	 * Accessor method to get the power this skill consumes. 
	 */
	public int losePower()
	{
		return power;
	}
	
	/*
	 * Returns how much health this skill will heal. 
	 */
	public int heal()
	{
		return heal;
	}
	
	/*
	 * Returns the chance that an attack will miss. 
	 */
	public double getMiss()
	{
		return miss;
	}
	
	/*
	 * Returns the name of this skill. 
	 */
	public String getName()
	{
		return name;
	}

	/*
	 * Returns a description of this skill, so the player can decide
	 * whether or not they want to use it. 
	 */
	public String toString()
	{
		String tab = "\t";
		if ( name.length() < 8 )
		{
			tab += "\t";
		}
		if ( heal == 0 )
		{
			return name + tab + " Damage range: " + lowDamage + " - " + highDamage + "\t Power Consumed: " + power + "\t Chance of missing: " + (miss * 100) + "%"; 
		}
		else
		{
			return name + tab + " Heals: " + heal + "\t\t Power Consumed: " + power; 
		}
	}
	
	
}
