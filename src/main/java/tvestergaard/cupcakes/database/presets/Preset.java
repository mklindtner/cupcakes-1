package tvestergaard.cupcakes.database.presets;

import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;

public class Preset
{

	private final int     id;
	private final String  name;
	private final String  description;
	private final Bottom  bottom;
	private final Topping topping;
	private final int     price;

	public Preset(int id, String name, String description, Bottom bottom, Topping topping, int price)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.bottom = bottom;
		this.topping = topping;
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

	public Bottom getBottom()
	{
		return this.bottom;
	}

	public Topping getTopping()
	{
		return this.topping;
	}

	public int getPrice()
	{
		return this.price;
	}
}
