package fruitsBasket;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Basket {
	public String owner;
	public HashMap<Integer, Fruit> items;
	//public HashMap<String, String[]> validTypes;
	public int cost;
	
	private int nextId;
	
	public Basket(String owner) {
		this.owner = owner;
		this.items = new HashMap<Integer, Fruit>();
	}
	
	private String getLine(String prompt) {
		// TODO: make sure they enter something
		//       (check for `\n`s etc?)
		boolean badInput = false;
		String line;
		
		do {
			badInput = false;
			
			System.out.print(prompt);
			line = new Scanner(System.in).nextLine().toLowerCase();		
			
			if (line.isEmpty()) {
				System.out.println("ERROR: nothing entered");
				badInput = true;
			}
		} while (badInput);
		
		return line;
	}
	
	private int getYesNo(String prompt) {
		boolean badInput = false;
		String line;
		int ret = 1;
		
		do {
			badInput = false;
			line = this.getLine(prompt);
			
			if (line.equals("y") || line.equals("yes")) {
				ret = 0;
			} else if (line.equals("n") || line.equals("no")) {
				ret = 1;
			} else {
				System.out.println("ERROR: input not yes/y or no/n");
				badInput = true;
			}
		} while (badInput);
		
		return ret;
	}
	
	private int getPositiveInt() {
		// Try to get a positive integer from stdin.
		//
		// If an integer is not entered, return -2.
		// If it is not positive, return -1.
		//
		
		int num;
		
		try {
			num = new Scanner(System.in).nextInt();
		} catch (InputMismatchException e) {
			return -2;
		}
		
		if (num < 0) { return -1; } else { return num; }
	}
	
	private int promptPositiveInt(String prompt) {
		// Force a positive integer to be entered.
		//
		// Prompts using string `prompt`.
		// (keeps prompting until we get valid input)
		//
		
		boolean badInput;
		int num;
		
		do {
			badInput = false;
			
			System.out.print(prompt);
			num = this.getPositiveInt();
			
			if (num == -2) {
				System.out.println("ERROR: not an integer");
				badInput = true;
			} else if (num == -1) {
				System.out.println("ERROR: not a positive integer");
				badInput = true;
			}
		} while (badInput);
		
		return num;
	}
	
	/*
	private String[] infoOfType(String type) {
		// Defines & returns attributes for a valid fruit types.
		String[] attr = new String[3];
		
		switch (type) {
		case "apple":
			attr[0] = "apple";
			attr[1] = "crunchy & sweet";
			attr[2] = "shiny & red";
			attr[3] = "true";
			
		}
		
		this.validFruits.put(0, value)
	}
	*/
	
	private Integer[] getAllIds() {
		return this.items.keySet().toArray(new Integer[this.items.size()]);
	}
	
	/*
	private void refreshBasket() {
		//checks for off foodstuffs & removes them?? or tells you to
	}
	*/
	
	private int addItem(String type, String taste, String colour, int price) {
		int id = this.nextId;
		this.items.put(this.nextId, new Fruit(type, taste, colour, price, true));
		this.nextId++;
		return id;
	}
	
	private void removeItem(int id) {
		this.items.remove(id);
	}
	
	private int totalCost() {
		int price = 0;
		for (int id : this.items.keySet()) {
			price += this.items.get(id).price;
		}
		
		return price;
	}
	
	private void setPriceOfItem(int id, int newPrice) {
		this.items.get(id).price = newPrice;
	}
	
	public void newItems(int num) {
		// Add `num` items, prompting for info about them.
		
		for (int i = 0; i < num; i++) {
			System.out.println("Item " + (i + 1) + "/" + num);
			String type = this.getLine("Type: ");
			String taste = this.getLine("Taste: ");
			String colour = this.getLine("Colour: ");
			int price = this.promptPositiveInt("Price: ");
			
			this.addItem(type, taste, colour, price);
			
			System.out.println("Item #" + this.items.size() + " added");
			System.out.println();
		}
		
		System.out.println("Current cost: " + this.totalCost() + "p");
		System.out.println();
	}
	
	public void returnItem(int id) {
		String type = this.items.get(id).type();
		
		if (this.items.get(id).isEdible()) {
			System.out.println("You decide you don't want the " + type + ".");
		} else {
			System.out.println("You sneakily return the inedible " + type + ".");
		}
		
		this.removeItem(id);
	}
	
	public void dropItem(int id) {
		// TODO: only work on edible items
		String type = this.items.get(id).type();
		int currentPrice = this.items.get(id).price;
		this.items.get(id).becomeInedible();
		this.setPriceOfItem(id, currentPrice / 2);
		System.out.println("Oh no! You dropped a " + type + "!");
		System.out.println("The supermarket doesn't want it any more. The " + type + "'s price has been halved, but it's inedible.");
		System.out.println();
	}
	
	public void printCurrentBasket() {
		//this.refreshBasket();
		// doesn't return edible info
		int itemCount = 0;
		for (Integer id : this.getAllIds()) {
			// we don't actually print the ID, because that would be
			// user-unfriendly in this case
			// insteaad, track the item count
			itemCount++;
			
			String type = this.items.get(id).type();
			String taste = this.items.get(id).taste;
			String colour = this.items.get(id).colour;
			int price = this.items.get(id).price;
			
			if (this.items.get(id).isEdible()) {
				System.out.println("Item " + itemCount + " (" + price + "p): a " + taste + " " + colour + " " + type);
			} else {
				System.out.println("Item " + itemCount + " (" + price + "p): a " + taste + " " + colour + " " + type + " (inedible)");
			}
		}
		System.out.println("Total: " + itemCount + " items");
		System.out.println("Cost: " + this.totalCost() + "p");
		System.out.println();
	}
	
	private void printOptions() {
		System.out.println("Available commands:");
		System.out.println("    n) new item(s)");
		System.out.println("    r) return item");
		System.out.println("    d) drop item (on the floor)");
		System.out.println("    p) display basket");
		System.out.println("    q) pay and leave");
	}
	
	public void goShopping() {
		boolean stillShopping;
		String option;
		
		do {
			stillShopping = true;
			
			this.printOptions();
			option = this.getLine("Enter a command: ");
			
			switch (option) {
			case "n": {
				this.newItems(this.promptPositiveInt("Add how many items? "));
				break;
			}
			
			case "r": {
				this.returnItem(this.promptPositiveInt("Item ID: "));
				break;
			}
			
			case "d": {
				this.dropItem(this.promptPositiveInt("Item ID: "));
				break;
			}
			
			case "p": {
				this.printCurrentBasket();
				break;
			}
			
			case "q": {
				System.out.println("You pay " + this.totalCost() + "p for your " + this.items.size() + " items.");
				return;
			}
			
			default: {
				System.out.println("ERROR: input not one of the specified options");
			}
			}
			
			int ret = this.getYesNo("Do you want to continue shopping? (Y/N) ");
			if (ret == 1) {
				stillShopping = false;
			}
		} while (stillShopping);
	}
	
	public static void main(String[] args) {
		Basket basket = new Basket("Raehik");
		/*
		basket.newItems(2);
		basket.printCurrentBasket();
		basket.dropItem(1);
		basket.printCurrentBasket();
		basket.returnItem(1);
		basket.newItems(2);
		basket.printCurrentBasket();
		*/
		basket.goShopping();
	}

}
