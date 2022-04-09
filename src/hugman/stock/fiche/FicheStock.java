package hugman.stock.fiche;

import java.util.ArrayList;

public class FicheStock
{
	public final ArrayList<LigneStock> lignes;

	public FicheStock(ArrayList<LigneStock> lignes) {
		this.lignes = lignes;
	}

	public ArrayList<LigneStock> getLignes() {
		return this.lignes;
	}
}
