package me.hugman.acctools.action.stock_card;

import java.util.ArrayList;
import java.util.Date;

public record StockCard(ArrayList<Line> lines)
{
	public record Line(Date date, Cell input, Cell output, Cell stocks) {}

	public record Cell(int quantity, float unitPrice)
	{
		public float amount() {
			return this.quantity * this.unitPrice;
		}
	}
}
