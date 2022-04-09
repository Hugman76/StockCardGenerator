package hugman.stock.action;

import java.util.Date;
import java.util.Objects;

public class Action
{
	public static final String[] TYPES = { "Stock initial", "Entrée", "Sortie" };

	public static final String STOCK_INITIAL = TYPES[0];
	public static final String ENTREE        = TYPES[1];
	public static final String SORTIE        = TYPES[2];

	private String type;
	private Date   date;
	private int    quantite;
	private float  prixUnitaire;

	public Action(String type, Date date, int quantite, float prixUnitaire) {
		this.type = type;
		this.date = date;
		this.quantite = quantite;
		this.prixUnitaire = prixUnitaire;
	}

	public Action(Action action) {
		this(action.getType(), action.getDate(), action.getQuantite(), action.getPrixUnitaire());
	}

	public static Action creerDefaut() {
		return new Action(Action.ENTREE, null, 1, 10);
	}

	public String getType() {
		return this.type;
	}

	public boolean setType(String type) {
		this.type = type;
		return (Objects.equals(type, STOCK_INITIAL) || Objects.equals(type, ENTREE) || Objects.equals(type, SORTIE));
	}

	public Date getDate() {
		return this.date;
	}

	public boolean setDate(Date date) {
		this.date = date;
		return true;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public boolean setQuantite(int quantite) {
		this.quantite = quantite;
		return quantite > 0;
	}

	public float getPrixUnitaire() {
		return this.prixUnitaire;
	}

	public boolean setPrixUnitaire(float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
		return this.prixUnitaire >= 0;
	}

	public double getMontant() {
		return this.quantite * this.prixUnitaire;
	}

	/**
	 * Vérifie si l'action est valide.
	 * @param quantiteTotale la quantite totale d'unités dans l'inventaire. Permet de vérifier si on ne dépasse pas la limite lorsque l'action est une sortie.
	 */
	public boolean estValide(int quantiteTotale) {
		return (!Objects.equals(this.type, SORTIE) || this.quantite <= quantiteTotale);
	}
}
