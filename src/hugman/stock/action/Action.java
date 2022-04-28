package hugman.stock.action;

import java.util.Date;

public class Action
{
	public static final String[] TYPES = { "Stock initial", "Entrée", "Sortie" };

	public static final int STOCK_INITIAL = 0;
	public static final int ENTREE        = 1;
	public static final int SORTIE        = 2;

	private int   type;
	private Date  date;
	private int   quantite;
	private float prixUnitaire;

	private Action(int type, Date date, int quantite, float prixUnitaire) {
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

	public static Action creerStockInitial(Date date, int quantite, float prixUnitaire) {
		return new Action(Action.STOCK_INITIAL, date, quantite, prixUnitaire);
	}

	public static Action creerEntree(Date date, int quantite, float prixUnitaire) {
		return new Action(Action.ENTREE, date, quantite, prixUnitaire);
	}

	public static Action creerSortie(Date date, int quantite) {
		return new Action(Action.SORTIE, date, quantite, 0);
	}

	public int getType() {
		return this.type;
	}

	public boolean setType(String type) {
		for(int i = 0; i < TYPES.length; i++) {
			if(TYPES[i].equals(type)) {
				this.type = i;
				return true;
			}
		}
		return false;
	}

	public boolean setType(int type) {
		this.type = type;
		return (type >= 0 && type < TYPES.length);
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
		return this.prixUnitaire >= 0.0F;
	}

	public double getMontant() {
		return this.quantite * this.prixUnitaire;
	}

	/**
	 * Vérifie si l'action est valide.
	 * @param quantiteTotale la quantite totale d'unités dans l'inventaire. Permet de vérifier si on ne dépasse pas la limite lorsque l'action est une sortie.
	 */
	public boolean estValide(int quantiteTotale) {
		return (this.type != SORTIE || this.quantite <= quantiteTotale);
	}

	@Override
	public String toString() {
		return "Action{" +
				"type=" + TYPES[type] +
				", date=" + date +
				", quantite=" + quantite +
				(type == SORTIE ? "" : (", prixUnitaire=" + prixUnitaire)) +
				'}';
	}
}
