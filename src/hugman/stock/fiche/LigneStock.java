package hugman.stock.fiche;

import java.util.Date;

public class LigneStock
{
	private final Date date;
	private final CelluleStock entree;
	private final CelluleStock sortie;
	private final CelluleStock stocks;

	public LigneStock(Date date, CelluleStock entree, CelluleStock sortie, CelluleStock stocks) {
		this.date = date;
		this.entree = entree;
		this.sortie = sortie;
		this.stocks = stocks;
	}

	public Date getDate() {
		return date;
	}

	public CelluleStock getEntree() {
		return entree;
	}

	public CelluleStock getSortie() {
		return sortie;
	}

	public CelluleStock getStocks() {
		return stocks;
	}
}
