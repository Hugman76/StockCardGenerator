package hugman.stock.fiche;

public class CelluleStock
{
	private final int quantite;
	private final float prixUnitaire;

	public CelluleStock(int quantite, float prixUnitaire) {
		this.quantite = quantite;
		this.prixUnitaire = prixUnitaire;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public float getPrixUnitaire() {
		return this.prixUnitaire;
	}

	public float getMontant() {
		return this.quantite * this.prixUnitaire;
	}
}
