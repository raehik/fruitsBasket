package fruitsBasket;

public class Fruit {
	// type should be final
	// others could change when they go mouldy etc.
	private String type;
	public String taste, colour;
	public int price;
	private boolean isEdible;
	
	public Fruit(String type, String taste, String colour, int price, boolean isEdible) {
		this.type = type;
		this.taste = taste;
		this.colour = colour;
		this.price = price;
		this.isEdible = isEdible;
	}
	
	public String type() {
		return this.type;
	}
	
	public boolean isEdible() {
		return this.isEdible;
	}
	
	public void becomeInedible() {
		this.isEdible = false;
		this.taste = "yucky";
		this.colour = "unappealing";
	}

}
