package hugman.stock.fiche;

import java.util.ArrayList;
import java.util.Date;

public record Fiche(ArrayList<Ligne> lignes)
{
	public record Ligne(Date date, Cellule entree, Cellule sortie, Cellule stocks) {}
	public record Cellule(int quantite, float prixUnitaire)
	{
		public float montant() {
			return this.quantite * this.prixUnitaire;
		}
	}
}
