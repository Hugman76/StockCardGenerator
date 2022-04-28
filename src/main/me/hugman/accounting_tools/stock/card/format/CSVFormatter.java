package me.hugman.accounting_tools.stock.card.format;

import me.hugman.accounting_tools.stock.card.StockCard;

public record CSVFormatter(char delimiter) implements Formatter
{
	public String format(StockCard card) {
		StringBuilder sb = new StringBuilder();
		sb.append("Fiche de stocks%%%%%%%%%%\n");
		sb.append("Date%Entrées%%%Sorties%%%Stocks%%%\n");
		sb.append("%Quantités%Prix Uni%Montant%Quantités%Prix Uni%Montant%Quantités%Prix Uni%Montant%\n");
		for(StockCard.Line line : card.lines()) {
			sb.append(line.date().getDate()).append("/").append(line.date().getMonth() + 1).append("/").append(line.date().getYear() + 1900).append("%");
			for(StockCard.Cell cell : new StockCard.Cell[]{line.input(), line.output(), line.stocks()}) {
				sb.append(cell == null ? "%%%" : cell.quantity() + "%" + cell.unitPrice() + " €%" + cell.amount() + " €%");
			}
			sb.append("\n");
		}
		return sb.toString().replace('%', this.delimiter);
	}
}
