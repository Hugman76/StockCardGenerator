package me.hugman.accounting_tools.stock.card.format;

public class Formatters
{
	public static final Formatter CSV = new CSVFormatter(',');

	public static Formatter csv(char delimiter) {
		return new CSVFormatter(delimiter);
	}
}
