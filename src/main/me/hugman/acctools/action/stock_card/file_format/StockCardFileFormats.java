package me.hugman.acctools.action.stock_card.file_format;

import me.hugman.acctools.action.ActionManager;
import me.hugman.acctools.action.stock_card.StockCard;

import java.util.ArrayList;
import java.util.List;

public class StockCardFileFormats
{
	public static final List<StockCardFileFormat> FORMATS = new ArrayList<>();
	public static final StockCardFileFormat CSV = register("CSV", card -> {
		StringBuilder sb = new StringBuilder();
		sb.append("Fiche de stocks%%%%%%%%%\n");
		sb.append("Date%Entrées%%%Sorties%%%Stocks%%\n");
		sb.append("%Quantités%Prix Uni%Montant%Quantités%Prix Uni%Montant%Quantités%Prix Uni%Montant\n");
		for(StockCard.Line line : card.lines()) {
			if(line.date() != null) sb.append(line.date().getDate()).append("/").append(line.date().getMonth() + 1).append("/").append(line.date().getYear() + 1900);
			for(StockCard.Cell cell : new StockCard.Cell[]{line.input(), line.output(), line.stocks()}) {
				sb.append(cell == null ? "%%%" : "%" + cell.quantity() + "%" + cell.unitPrice() + " €%" + cell.amount());
			}
			sb.append("\n");
		}
		return sb.toString().replace("%", ActionManager.PREFERENCES.get(ActionManager.DELIMITER_PREFERENCE, ";"));
	}, "csv");

	public static StockCardFileFormat register(String name, StockCardFileFormat.Formatter executor, String... extensions) {
		StockCardFileFormat format = new StockCardFileFormat(name, executor, List.of(extensions));
		FORMATS.add(format);
		return format;
	}
}
