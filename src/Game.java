/**
 * Class Game - the main class of the "Zork" game.
 *
 * Authors:  Michael Kolling, Travis Langston
 * Version: 1.2.1
 * Date:    October 2018
 * 
 *  This class is the main class of the "Zork" application. Zork is a very
 *  simple, text based adventure game.  Users can explore the world, 
 *  collect items such as food and weapons, fight creatures they encounter, 
 *  and try to reach the tower to defeat the dragon at the end of the game.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  routine.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates the
 *  commands that the parser returns, and controls the combat system.
 */
import static java.lang.System.*;
import java.util.*;
import java.io.*;
import javax.sound.sampled.*;
class Game 
{
    private Parser      parser;
    private Room        currentRoom;
    private Creature    p1;    
    private boolean     inCombat;
    private Scanner     rdIn   = new Scanner(System.in);
    private Queue<File> battle = new LinkedList<File>();

    /**
     * Create the game and initialize its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        out.print("\nEnter your name: ");
        String name = rdIn.nextLine();
        out.print("\nChoose which class you want to be: archer, warrior, or wizard. ");
        String type = rdIn.nextLine();
        while ( !type.equals("archer") && !type.equals("warrior") && !type.equals("wizard") )
        {
        	out.println("Invalid type. Please try again: ");
        	out.print("\nChoose which class you want to be: archer, warrior, or wizard. ");
            type = rdIn.nextLine();
        }
        p1 = new Creature(name, type, 5);
        createRooms();
        battle.offer(new File("src/sounds/katana-clash1.wav"));
        battle.offer(new File("src/sounds/katana-clash2.wav"));
        battle.offer(new File("src/sounds/katana-clash3.wav"));
        battle.offer(new File("src/sounds/katana-continuity1.wav"));
        battle.offer(new File("src/sounds/katana-gesture1.wav"));
        battle.offer(new File("src/sounds/katana-gesture2.wav"));
        battle.offer(new File("src/sounds/katana-slash5.wav"));
        battle.offer(new File("src/sounds/monster-footstep1.wav"));
        battle.offer(new File("src/sounds/monster-growl1.wav"));
        battle.offer(new File("src/sounds/sword-battle1.wav"));
        battle.offer(new File("src/sounds/sword-clash1.wav"));
        battle.offer(new File("src/sounds/sword-clash2.wav"));
        battle.offer(new File("src/sounds/sword-clash3.wav"));
        battle.offer(new File("src/sounds/sword-gesture1.wav"));
        battle.offer(new File("src/sounds/sword-gesture2.wav"));
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {    	
    	Room jail, courtyard, tower, storeRoom, armory, outsideCastle, riverBank, forestEntrance, forest1, forest2, forest3, forest4, 
    	clearing1, clearing2, mysteriousTree, cabinEntrance, livingRoom, kitchen, forestRiver, bridge, 
    	mineEntrance, tunnel1, tunnel2, tunnel3, tunnel4, tunnel5, supplyRoom, trail1, trail2, trail3, 
    	cavernEntrance, cave1, cave2, cave3, cave4, cave5, cave6, ominousTower, stair1, stair2, stair3, stair4, stair5, topOfTower, endGame;
    	
    	// create the rooms
    	jail 	  	   = new Room("jail", "a cell in the castle jail. The gate lies open", false, null, false, null);
    	courtyard 	   = new Room("courtyard", "courtyard in the center of the castle. An eerie silence lies over the area. A broken gate lies to the north", false, null, false, null);
    	tower	  	   = new Room("tower", "a tower which overlooks the land nearby the castle. A forest lies to the east, mountains to the west, and a river to the north", false, null, false, null);
    	storeRoom      = new Room("storeRoom", "a mostly empty storeroom. A chest with some old food lies nearby", false, null, false, null);
    	armory 	       = new Room("armory", "a ransacked armory. A damaged " + p1.getPrimary() + " and " + p1.getSecondary() + " lie in the corner", false, null, false, null);
    	outsideCastle  = new Room("outsideCastle", "a small field outside the gate to the castle", false, null, false, null);
    	riverBank      = new Room("riverBank", "the bank of a raging river. There is no way across", false, null, false, null);
    	forestEntrance = new Room("forestEntrance", "the entrance to a mysterious forest", true, new Creature("orc", "warrior", 1), false, null);
    	forest1        = new Room("forest1", "a forest", false, null, false, null);
    	forest2        = new Room("forest2", "a forest", false, null, false, null);
    	forest3        = new Room("forest3", "a forest. There is an apple in a tree nearby", false, null, false, null);
    	forest4        = new Room("forest4", "a forest. There is an orange in a tree nearby", false, null, false, null);    
    	clearing1      = new Room("clearing1", "a clearing in the forest", true, new Creature("orc", "warrior", 2), false, null);
    	clearing2      = new Room("clearing2", "a clearing in the forest", true, new Creature("orc", "warrior", 2), false, null);
    	mysteriousTree = new Room("mysteriousTree", "a tall and mysterious tree in the forest. A corpse lies nearby with a " + p1.getPrimary() + " and a " + p1.getSecondary(), false, null, false, null);
    	cabinEntrance  = new Room("cabinEntrance", "entrance to an old, abandoned cabin in the forest", false, null, false, null);
    	livingRoom	   = new Room("livingRoom", "living room in the abandoned cabin. A " + p1.getPrimary() + " and a " + p1.getSecondary() + " hang on the wall", false, null, false, null);
    	kitchen		   = new Room("kitchen", "kitchen in the abandoned cabin. A pot of stew sits on the stove, and there is some cooked fish on the table", false, null, false, null);
    	forestRiver    = new Room("forestRiver", "bank of a raging river in the forest. There is a bridge to the north", true, new Creature("troll", "warrior", 3), false, null);
    	bridge         = new Room("bridge", "bridge across the raging river", false, null, false, null);
    	mineEntrance   = new Room("mineEntrance", "entrance to an old, abandoned cabin mine dug into the mountainside", false, null, false, null);
    	tunnel1   	   = new Room("tunnel1", "tunnel in the mine", true, new Creature("bat", "archer", 1), false, null);
    	tunnel2   	   = new Room("tunnel2", "tunnel in the mine. A torch is attached to the wall", true, new Creature("bat", "archer", 2), false, null);
    	tunnel3   	   = new Room("tunnel3", "tunnel in the mine. An old corpse lies on the ground", true, new Creature("bat", "archer", 2), false, null);
    	tunnel4   	   = new Room("tunnel4", "tunnel in the mine. Some dead goblins lie nearby", true, new Creature("bat", "archer", 2), false, null);
    	tunnel5   	   = new Room("tunnel5", "tunnel in the mine. A mysterious light comes from a passage to the north", false, null, false, null);
    	supplyRoom     = new Room("supplyRoom", "storeroom for the mine. A chest sits in the corner", false, null, false, null);
    	trail1   	   = new Room("trail1", "a trail up the side of a mountain", false, null, false, null);
    	trail2   	   = new Room("trail2", "a trail up the side of a mountain", false, null, false, null);
    	trail3   	   = new Room("trail3", "a trail up the side of a mountain. A cave entrance lies to the south, and an ominous tower to the north", false, null, false, null);
    	cavernEntrance = new Room("cavernEntrance", "Entrance to caverns on the mountain", false, null, true, new Closing("collapsed entrance", "explosives", true));
    	cave1   	   = new Room("cave1", "dark cave", true, new Creature("goblin", "archer", 1), false, null);
    	cave2   	   = new Room("cave2", "dark cave. Some raw flesh lies nearby", true, new Creature("goblin", "archer", 2), false, null);
    	cave3   	   = new Room("cave3", "dark cave. Water drips from the stalactites above", true, new Creature("goblin", "archer", 2), false, null);
    	cave4   	   = new Room("cave4", "dark cave. Some old bones lie in the corner", true, new Creature("goblin", "archer", 3), false, null);
    	cave5   	   = new Room("cave5", "dark cave. There is a goblin camp nearby", true, new Creature("goblin", "archer", 4), false, null);
    	cave6   	   = new Room("cave6", "end of the cave system. A chest sits in the corner", false, null, false, null);
    	ominousTower   = new Room("ominousTower", "Entrance to ominous tower.", false, null, true, new Closing("locked door", "key", true));
    	stair1   	   = new Room("stair1", "staircase in the tower", true, new Creature("knight", "warrior", 1), false, null);
    	stair2   	   = new Room("stair2", "staircase in the tower", true, new Creature("knight", "warrior", 2), false, null);
    	stair3   	   = new Room("stair3", "staircase in the tower", true, new Creature("knight", "warrior", 3), false, null);
    	stair4   	   = new Room("stair4", "staircase in the tower", true, new Creature("knight", "warrior", 4), false, null);
    	stair5   	   = new Room("stair5", "staircase in the tower. A door lies right above. ", true, new Creature("knight", "warrior", 5), false, null);
    	topOfTower 	   = new Room("topOfTower", "top of the tower. A storm rages all around", true, new Creature("dragon", "warrior", 5), false, null);
    	endGame        = new Room("endGame", "end game", false, null, false, null);
    	
    	// initialize room exits
    	jail.setExits(null, courtyard, null, null);
    	courtyard.setExits(outsideCastle, storeRoom, tower, jail);
    	tower.setExits(courtyard, null, null, null);
    	storeRoom.setExits(null, armory, null, courtyard);
    	armory.setExits(null, null, null, storeRoom);
    	outsideCastle.setExits(riverBank, forestEntrance, courtyard, trail1);
    	riverBank.setExits(null, null, outsideCastle, null);
    	forestEntrance.setExits(null, forest1, null, outsideCastle);
    	forest1.setExits(null, forest2, null, forestEntrance);
    	forest2.setExits(null, clearing1, mysteriousTree, forest1);
    	forest3.setExits(clearing1, null, null, mysteriousTree);
    	forest4.setExits(null, clearing2, null, clearing1);
        clearing1.setExits(forestRiver, forest4, forest3, forest2);
        clearing2.setExits(null, cabinEntrance, null, forest4);
        mysteriousTree.setExits(forest2, forest3, null, null);
    	cabinEntrance.setExits(null, livingRoom, null, clearing2);
    	livingRoom.setExits(null, kitchen, null, cabinEntrance);
    	kitchen.setExits(null, null, null, livingRoom);
    	forestRiver.setExits(bridge, null, clearing1, null);
    	bridge.setExits(mineEntrance, null, forestRiver, null);
    	mineEntrance.setExits(tunnel1, null, bridge, null);
    	tunnel1.setExits(tunnel2, tunnel2, mineEntrance, tunnel3);
    	tunnel2.setExits(tunnel1, tunnel1, tunnel1, tunnel3);
    	tunnel3.setExits(tunnel2, tunnel2, tunnel1, tunnel4);
    	tunnel4.setExits(tunnel3, tunnel5, tunnel3, tunnel3);
    	tunnel5.setExits(mineEntrance, supplyRoom, mineEntrance, tunnel4);
    	supplyRoom.setExits(null, null, null, tunnel5);
    	trail1.setExits(null, outsideCastle, null, trail2);
    	trail2.setExits(null, trail1, null, trail3);
    	trail3.setExits(ominousTower, trail2, cavernEntrance, null);
    	cavernEntrance.setExits(trail3, null, cave1, null);
    	cave1.setExits(cavernEntrance, cave2, cave2, cave2);
    	cave2.setExits(cave1, cave1, cave3, cave1);
    	cave3.setExits(cave2, cave4, cave1, cave1);
    	cave4.setExits(cave1, cave3, cave1, cave5);
    	cave5.setExits(cave6, cave4, cave1, cave1);
    	cave6.setExits(cave5, null, null, null);
    	ominousTower.setExits(null, null, trail3, null);
    	stair1.setExits(stair2, null, ominousTower, null);
    	stair2.setExits(null, stair3, stair1, null);
    	stair3.setExits(null, stair4, null, stair2);
    	stair4.setExits(null, null, stair5, stair3);
    	stair5.setExits(stair4, null, topOfTower, null);
    	topOfTower.setExits(stair5, null, endGame, null);
    	
    	// initialize items in the rooms.
    	forest3.addItems("apple", new Food("apple", 30, 20));
    	forest4.addItems("orange", new Food("orange", 30, 20));
    	kitchen.addItems("stew", new Food("stew", 20, 30));
    	kitchen.addItems("fish", new Food("fish", 20, 30));
    	cave2.addItems("raw flesh", new Food("raw flesh", 40, 50));
    	
    	//initializes container objects in rooms
    	Container supplyChest = new Container("chest", 10);
    	supplyChest.addItems(new Food("apple", 30, 10));
    	supplyChest.addItems(new Food("orange", 30, 10));
    	supplyChest.addItems(new Food("bacon", 0, 30));
    	supplyChest.addItems(new Food("steak", 0, 40));
    	storeRoom.addItems(supplyChest.getName(), supplyChest);
    	Container supplyChest1 = new Container("chest", 10);
    	supplyChest1.addItems(new Food("biscuit", 30, 40));
    	supplyChest1.addItems(new Food("bagel", 30, 40));
    	supplyChest1.addItems(new Food("soup", 40, 30));
    	supplyChest1.addItems(new Food("chicken", 40, 30));
    	supplyChest1.addItems(new Weapon(p1.getPrimary(), 4));
    	supplyChest1.addItems(new Weapon(p1.getSecondary(), 4));
    	supplyChest1.addItems(new Trigger("explosives"));
    	supplyRoom.addItems(supplyChest1.getName(), supplyChest1);
    	Container supplyChest2 = new Container("chest", 10);
    	supplyChest2.addItems(new Food("raw flesh", 40, 50));
    	supplyChest2.addItems(new Food("rotten flesh", 40, 50));
    	supplyChest2.addItems(new Food("troll meat", 50, 40));
    	supplyChest2.addItems(new Food("orc flesh", 50, 40));
    	supplyChest2.addItems(new Weapon(p1.getPrimary(), 5));
    	supplyChest2.addItems(new Weapon(p1.getSecondary(), 5));
    	supplyChest2.addItems(new Trigger("key"));
    	supplyRoom.addItems(supplyChest1.getName(), supplyChest1);
    	
    	//initializes weapon objects in rooms
    	armory.addItems(p1.getPrimary(), new Weapon(p1.getPrimary(), 1));
    	armory.addItems(p1.getSecondary(), new Weapon(p1.getSecondary(), 1));
    	mysteriousTree.addItems(p1.getPrimary(), new Weapon(p1.getPrimary(), 2));
    	mysteriousTree.addItems(p1.getSecondary(), new Weapon(p1.getSecondary(), 2));
    	livingRoom.addItems(p1.getPrimary(), new Weapon(p1.getPrimary(), 3));
    	livingRoom.addItems(p1.getSecondary(), new Weapon(p1.getSecondary(), 3));
    	
    	// Add the weapons to the creatures.
    	Creature guard1 = forestEntrance.getGuardian();
    	guard1.addInventory("sword", new Weapon("sword", (int) guard1.getLevel()));
    	guard1.addInventory("shield", new Weapon("shield", (int) guard1.getLevel()));
    	Creature guard2 = clearing1.getGuardian();
    	guard2.addInventory("sword", new Weapon("sword", (int) guard2.getLevel()));
    	guard2.addInventory("shield", new Weapon("shield", (int) guard2.getLevel()));
    	Creature guard3 = clearing2.getGuardian();
    	guard3.addInventory("sword", new Weapon("sword", (int) guard3.getLevel()));
    	guard3.addInventory("shield", new Weapon("shield", (int) guard3.getLevel()));
    	Creature guard4 = forestRiver.getGuardian();
    	guard4.addInventory("sword", new Weapon("sword", (int) guard4.getLevel()));
    	guard4.addInventory("shield", new Weapon("shield", (int) guard4.getLevel()));
    	Creature guard5 = tunnel1.getGuardian();
    	guard5.addInventory("bow", new Weapon("bow", (int) guard5.getLevel()));
    	guard5.addInventory("knife", new Weapon("knife", (int) guard5.getLevel()));
    	Creature guard6 = tunnel2.getGuardian();
    	guard6.addInventory("bow", new Weapon("bow", (int) guard6.getLevel()));
    	guard6.addInventory("knife", new Weapon("knife", (int) guard6.getLevel()));
    	Creature guard7 = tunnel3.getGuardian();
    	guard7.addInventory("bow", new Weapon("bow", (int) guard7.getLevel()));
    	guard7.addInventory("knife", new Weapon("knife", (int) guard7.getLevel()));
    	Creature guard8 = tunnel4.getGuardian();
    	guard8.addInventory("bow", new Weapon("bow", (int) guard8.getLevel()));
    	guard8.addInventory("knife", new Weapon("knife", (int) guard8.getLevel()));
    	Creature guard9 = cave1.getGuardian();
    	guard9.addInventory("bow", new Weapon("bow", (int) guard9.getLevel()));
    	guard9.addInventory("knife", new Weapon("knife", (int) guard9.getLevel()));
    	Creature guard10 = cave2.getGuardian();
    	guard10.addInventory("bow", new Weapon("bow", (int) guard10.getLevel()));
    	guard10.addInventory("knife", new Weapon("knife", (int) guard10.getLevel()));
    	Creature guard11 = cave3.getGuardian();
    	guard11.addInventory("bow", new Weapon("bow", (int) guard11.getLevel()));
    	guard11.addInventory("knife", new Weapon("knife", (int) guard11.getLevel()));
    	Creature guard12 = cave4.getGuardian();
    	guard12.addInventory("bow", new Weapon("bow", (int) guard12.getLevel()));
    	guard12.addInventory("knife", new Weapon("knife", (int) guard12.getLevel()));
    	Creature guard13 = cave5.getGuardian();
    	guard13.addInventory("bow", new Weapon("bow", (int) guard13.getLevel()));
    	guard13.addInventory("knife", new Weapon("knife", (int) guard13.getLevel()));
    	Creature guard14 = stair1.getGuardian();
    	guard14.addInventory("sword", new Weapon("sword", (int) guard14.getLevel()));
    	guard14.addInventory("shield", new Weapon("shield", (int) guard14.getLevel()));
    	Creature guard15 = stair2.getGuardian();
    	guard15.addInventory("sword", new Weapon("sword", (int) guard15.getLevel()));
    	guard15.addInventory("shield", new Weapon("shield", (int) guard15.getLevel()));
    	Creature guard16 = stair3.getGuardian();
    	guard16.addInventory("sword", new Weapon("sword", (int) guard16.getLevel()));
    	guard16.addInventory("shield", new Weapon("shield", (int) guard16.getLevel()));
    	Creature guard17 = stair4.getGuardian();
    	guard17.addInventory("sword", new Weapon("sword", (int) guard17.getLevel()));
    	guard17.addInventory("shield", new Weapon("shield", (int) guard17.getLevel()));
    	Creature guard18 = stair5.getGuardian();
    	guard18.addInventory("sword", new Weapon("sword", (int) guard18.getLevel()));
    	guard18.addInventory("shield", new Weapon("shield", (int) guard18.getLevel()));
    	Creature guard19 = topOfTower.getGuardian();
    	guard19.addInventory("sword", new Weapon("sword", (int) guard19.getLevel()));
    	guard19.addInventory("shield", new Weapon("shield", (int) guard19.getLevel()));
    	
    	
    	currentRoom = jail; // start game in the jail cell.
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished)
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        // System.out.println("Thank you for playing.  Good bye.");
        System.out.println("Shia Lebeouf murders you with an axe. Have a nice day!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Zork!");
        System.out.println("Zork is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.longDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        if(command.isUnknown())
        {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if ( commandWord.equals("take"))
        {
        	chooseTake(command);
        }
        else if ( commandWord.equals("eat") )
        {
        	chooseEat(command);
        }
        else if ( commandWord.equals("open") )
        {
        	open(command);
        }
        else if ( commandWord.equals("inventory") )
        {
        	out.println("Inventory: ");
        	p1.printInventory();
        	out.println("Food: ");
        	p1.printFood();
        }
        else if (commandWord.equals("quit"))
        {
            if(command.hasSecondWord())
                System.out.println("Quit what?");
            else
                return true;  // signal that we want to quit
        }
        return false;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        // System.out.println("You are lost. You are alone. You wander");
        // System.out.println("around at Monash Uni, Peninsula Campus.");
    	System.out.println("You're walking in the woods.");
    	System.out.println("It's cold outside and your phone is dead. ");
    	System.out.println("Out of the corner of your eye you see him.");
    	System.out.println("Shia Lebeouf");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */ 
    private void goRoom(Command command) 
    {
        if ( !command.hasSecondWord() )
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        
        // Try to leave current room.
        Room nextRoom = currentRoom.nextRoom(direction);

        if (nextRoom == null)
        {
            System.out.println("There is no door!");
        }
        else if ( nextRoom.blockedByDoor() )
        {
        	boolean keepLooping = true;
        	while ( keepLooping )
        	{
        		out.print("Enter the name of the item to unlock the door, or quit.");
            	String temp = rdIn.nextLine();
            	if ( temp.equals("quit") )
            	{
            		keepLooping = false;
            	}
            	else
            	{
            		Map<String, Item> temp1 = p1.getInventory();
            		if ( temp1.containsKey(temp) )
            		{
            			Trigger temp2 = (Trigger) temp1.get(temp);
            			keepLooping = nextRoom.unlockDoor(temp2);
            		}
            		else
            		{
            			out.println("You don't have a " + temp);
            		}
            	}
        	}
        }
        else 
        {
        	if ( nextRoom.getName().equals("endGame") )
            {
            	endGame();
            }
            else if ( nextRoom.getName().equals("topOfTower") )
            {
            	playSound(new File("src/thunderstorm1.wav"));
            }
            else if ( nextRoom.getName().equals("forestRiver") || nextRoom.getName().equals("riverBank") )
            {
            	playSound(new File("src/creek1.wav"));
            }
            currentRoom = nextRoom;
            System.out.println(currentRoom.longDescription());
            inCombat = currentRoom.blockedByCreature();
            if ( inCombat )
            {
            	combat(currentRoom.getGuardian());
            }
        }
    }
    
    /*
     * Plays the wav files
     */
    private void playSound(File sound)
    {
    	try {
    	    
    	    AudioInputStream stream;
    	    AudioFormat format;
    	    DataLine.Info info;
    	    Clip clip;

    	    stream = AudioSystem.getAudioInputStream(sound);
    	    format = stream.getFormat();
    	    info = new DataLine.Info(Clip.class, format);
    	    clip = (Clip) AudioSystem.getLine(info);
    	    clip.open(stream);
    	    clip.start();
    	}
    	catch (Exception e) {
    	    out.println("AAAAAAAAAH. SOUND FILE PLAYBACK FAILED!");
    	}

    }
    /*
     * Takes an item the player chooses from the room they're in, 
     * otherwise prints an error message. 
     */
    private void chooseTake(Command command)
    {
    	if ( !command.hasSecondWord() )
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
            return;
        }
    	
    	String item = command.getSecondWord();
    	
    	if ( currentRoom.hasItem(item) && currentRoom.isUsable(item) )
    	{
    		Item temp = currentRoom.takeItem(item);
    		takeItem(temp, item);
    	}
    	else
    	{
    		out.println("No such item exists.");
    	}
    }
    
    private void takeItem(Item temp, String item)
    {
		if ( temp.isFood() )
		{
			p1.addFood((Food) temp);
		}
		else
		{
    		p1.addInventory(item, temp);
		}
		out.println("taken");
    }
    
    /*
     * Figures out what item the player wants to eat, then passes a string 
     * to the eatItem method.
     * otherwise prints an error message. 
     *
     */
    private void chooseEat(Command command)
    {
    	if ( !command.hasSecondWord() )
        {
            // if there is no second word, we don't know what to eat...
            System.out.println("Eat what?");
            return;
        }
    	
    	String item = command.getSecondWord();
    	eatItem(item);
    }
    
    /*
     * Takes an item from the players list of food, and
     * then restores the players health and power
     * based upon the item chosen.
     */
    private boolean eatItem(String eat)
    {
    	
    	if ( !p1.hasFood(eat) )
		{
			out.println("You don't have a " + eat);
			return false;
		}
		else
		{
			Food eaten = p1.getFood(eat);
			p1.removeFood(eat);
			p1.setHealth(eaten.restoreHealth());
			p1.setPower(eaten.restorePower());
			out.println("The " + eat + " restored " + eaten.restoreHealth() + " health and " + eaten.restorePower() + " power.");
			return true;
		}
    }
    

    /*
     * Opens a container in the room, then views inside it. 
     * Allows players to take items out of the container. 
     */
    private void open(Command command)
    {
    	if ( !command.hasSecondWord() )
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Open what?");
            return;
        }
    	
    	String item = command.getSecondWord();
    	
    	if ( currentRoom.hasItem(item)  )
    	{
    		Container temp 		  = (Container) currentRoom.getItem(item);
    		boolean   keepLooping = true;
    		while ( keepLooping ) 
    		{
				temp.viewInside();
				out.println("Enter take to take the item on top, or quit.");
				String rd = rdIn.nextLine();
				if ( rd.equals("quit") )
				{
					keepLooping = false;
				}
				else if ( temp.hasItem() )
				{
					Item temp1 = temp.take();
					takeItem(temp1, temp1.getName());
				}
				else
				{
					out.println("The " + temp.getName() + " was empty!");
					keepLooping = false;
				}
			}
    	}
    	else
    	{
    		out.println("No such item exists.");
    	}
    }
    
    /*
     * Used for combat between the player and the creature. 
     */
    private void combat(Creature guardian)
    {
    	out.println("Entering combat. ");
    	Weapon             guardWeapon = (Weapon) guardian.getItem(guardian.getPrimary());
    	Weapon             primary     = null;
    	Weapon             secondary   = null;
    	Scanner            rdIn        = new Scanner(System.in);
    	Map<String, Item>  inventory   = p1.getInventory();
    	Map<String, Skill> guardSkills = guardWeapon.getSkills();
    	boolean            keepLooping = true;
    	while ( keepLooping )
    	{
    		out.print("\nEnter a weapon to attack with: ");
    		String x = rdIn.nextLine();
    		if ( !inventory.containsKey(x) )
    		{
    			out.println("You don't have a " + x);
    		}
    		else
    		{
    			primary = (Weapon) inventory.get(x);
    			keepLooping = false;
    		}
    	}
    	keepLooping = true;
    	while ( keepLooping )
    	{
    		out.print("\nEnter a secondary weapon: ");
    		String x = rdIn.nextLine();
    		if ( !inventory.containsKey(x) )
    		{
    			out.println("You don't have a " + x);

    		}
    		else
    		{
    			secondary = (Weapon) inventory.get(x);
    			keepLooping = false;
    		}		
    	}
    	keepLooping = true;
    	while ( keepLooping )
    	{
    		out.println("\nYours stats: \n" + p1 );
        	out.println("\nOpponents stats: \n" + guardian );
        	out.println("\nAvailable Skills: \n");
        	primary.printSkills();
        	secondary.printSkills();
        	boolean chooseSkill = true;
        	String chosen = "";
        	Skill choose = null;
        	while (chooseSkill)
        	{
				out.println("\n\nEnter the name of a skill to use. ");
				out.print("Or enter 'eat' to eat food from your inventory to restore health and power: ");
				chosen = rdIn.nextLine();
				if ( chosen.equals("eat") )
				{
					boolean chooseFood = true;
					out.println("You have: ");
					p1.printFood();
					while (chooseFood) 
					{
						out.print("Enter name of food to eat, or quit to not eat anything: ");
						String eat = rdIn.nextLine();
						if ( eat.equals("quit") )
						{
							chooseFood = false;
						}
						else 
						{
							chooseFood = !eatItem(eat);
						}
						
					}
				}
				else if ( primary.hasSkill(chosen) )
				{
					if ( p1.getPower() - primary.getSkill(chosen).losePower() < 0 )
					{
						out.println("You don't have enough power for that skill!");
					}
					else
					{
						chooseSkill = false;
						choose = primary.getSkill(chosen);
					}
				}
				else if ( secondary.hasSkill(chosen) )
				{
					if ( p1.getPower() - secondary.getSkill(chosen).losePower() < 0 )
					{
						out.println("You don't have enough power for that skill!");
					}
					else
					{
						chooseSkill = false;
						choose = secondary.getSkill(chosen);
					}
				}
				else
				{
					out.println("Skill does not exist.");
				}
			}
        	if ( choose.heal() == 0 )
        	{
        		File temp = battle.poll();
        		playSound(temp);
        		battle.offer(temp);
        		int    damage = choose.getDamage();
        		int    power  = choose.losePower();
        		double miss   = choose.getMiss();
        		if ( Math.random() >= miss )
        		{
        			out.println("You attacked for " + damage + " damage and consumed " + power + " power.");
            		guardian.setHealth(-damage);
        		}
        		else
        		{
        			out.println("The attack missed! It consumed " + power + " power.");
        		}
        		p1.setPower(-power);
        	}
        	else
        	{
        		int heal  = choose.heal();
        		int power = choose.losePower();
        		out.println("You healed yourself for " + heal + " health and consumed " + power + " power.");
        		p1.setHealth(heal);
        		p1.setPower(-power);
        	}
        	if ( guardian.getHealth() <= 0 )
        	{
        		out.println("\nYou killed the " + guardian.getName() + "!");
        		guardian    = null;
        		keepLooping = false;
        		currentRoom.killCreature();
        	}
        	else
        	{
        		File temp = battle.poll();
        		playSound(temp);
        		battle.offer(temp);
        		Skill attack = null;
        		List<String> keysAsArray = new ArrayList<String>(guardSkills.keySet());
        		Random r = new Random();
        		attack = guardSkills.get(keysAsArray.get(r.nextInt(keysAsArray.size())));
        		if ( attack.heal() == 0 )
            	{
            		int    damage = attack.getDamage();
            		int    power  = attack.losePower();
            		double miss   = attack.getMiss();
            		if ( Math.random() >= miss && p1.getPower() + power >= 0 )
            		{
            			out.println("The " + guardian.getName() + " attacked for " + damage + " damage and consumed " + power + " power.");
            		
            			String block = "";
                		if ( primary.getName().equals("shield") )
                		{
                			block = "primary";
                		}
                		else if ( secondary.getName().equals("shield") )
                		{
                			block = "secondary";
                		}
                		else if ( primary.getName().equals("sword") )
                		{
                			block = "primary";
                		}
                		else if ( secondary.getName().equals("sword") )
                		{
                			block = "secondary";
                		}
                		
                		if ( block.equals("primary") )
                		{
                			p1.setHealth(-primary.block(damage));
                		}
                		else if ( block.equals("secondary") )
                		{
                			p1.setHealth(-secondary.block(damage));
                		}
                		else
                		{
                			p1.setHealth(-damage);
                		}
            		}
            		else 
            		{
            			out.println("\n\nThe " + guardian.getName() + "'s attack missed! It consumed " + power + " power.");
            		}
            		guardian.setPower(-power);
            	}
            	else
            	{
            		int heal  = choose.heal();
            		int power = choose.losePower();
            		out.println("\n\nThe " + guardian.getName() + " healed itself for " + heal + " health and consumed " + power + " power.");
            		guardian.setHealth(heal);
            		guardian.setPower(-power);
            	}
        	}
        	if ( p1.getHealth() < 0 )
        	{
        		out.println("\nThe " + guardian.getName() + " has killed you. GAME OVER.");
        		System.exit(0);
        	}
    	}
    }
    
    private void endGame()
    {
    	out.println("You have defeated the dragon and taken over the kingdom of Zork! ");
    	out.println("However, you have also killed every creature in the kingdom.");
    	out.println("Have fun living out the rest of your very lonely life!");
    	System.exit(0);
    }
}
