package tvestergaard.cupcakes.database.bottoms;

public final class Bottom
{

	private final int    id;
	private final String name;
	private final String description;
	private final int    price;

	public Bottom(int id, String name, String description, int price)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public String getDescription()
	{
		return this.description;
	}

	public int getPrice()
	{
		return this.price;
	}
}
