package me.hugman.acctools.action.stock_card.file_format;

import me.hugman.acctools.action.stock_card.StockCard;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List;

public record StockCardFileFormat(String name, Formatter formatter, List<String> extensions)
{
	public interface Formatter
	{
		String format(StockCard card);
	}

	public static StockCardFileFormat getFromFilter(FileNameExtensionFilter fileNameExtensionFilter) {
		for(StockCardFileFormat format : StockCardFileFormats.FORMATS) {
			if(format.extensions.contains(fileNameExtensionFilter.getExtensions()[0])) {
				return format;
			}
		}
		return null;
	}
}
