package hugman.stock;

import hugman.stock.action.Action;
import hugman.stock.fiche.Fiche;
import hugman.stock.gui.FenetreActions;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.List;

public class ControlleurActions
{
	private final List<Action> actions;
	private static final String NOM_APPLICATION = "Inventaire Stockeur 3000";

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		ControlleurActions ctrl = new ControlleurActions();
		ctrl.openWindow();
	}

	public ControlleurActions() {
		this.actions = new ArrayList<>();
		//this.getActions().add(new Action(Action.STOCK_INITIAL, null, 1, 10));

		// tests
		this.getActions().add(Action.creerStockInitial(new Date(2022 - 1900, Calendar.APRIL, 1), 30, 50.00F));
		this.getActions().add(Action.creerSortie(new Date(2022 - 1900, Calendar.APRIL, 2), 10));
		this.getActions().add(Action.creerEntree(new Date(2022 - 1900, Calendar.APRIL, 7), 20, 60.00F));
		this.getActions().add(Action.creerSortie(new Date(2022 - 1900, Calendar.APRIL, 14), 35));
	}

	public List<Action> getActions() {
		return actions;
	}

	public void openWindow() {
		FenetreActions fenetreActions = new FenetreActions(this);
		fenetreActions.setTitle(NOM_APPLICATION);
		fenetreActions.pack();
		fenetreActions.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		fenetreActions.setVisible(true);
	}

	public Fiche creerFicheStockPEPS() throws IllegalStateException {
		ArrayList<Fiche.Ligne> lignes = new ArrayList<>();
		Deque<Fiche.Cellule> stocks = new ArrayDeque<>();
		for(Action action : this.getActions()) {
			if(action.getType() == Action.STOCK_INITIAL) {
				Fiche.Cellule celStock = new Fiche.Cellule(action.getQuantite(), action.getPrixUnitaire());
				lignes.add(new Fiche.Ligne(action.getDate(), null, null, celStock));
				stocks.add(celStock);
			}
			else if(action.getType() == Action.ENTREE) {
				Fiche.Cellule cel = new Fiche.Cellule(action.getQuantite(), action.getPrixUnitaire());
				stocks.add(cel);

				for(Fiche.Cellule stock : stocks) {
					if(stock == stocks.peek()) // si ce stock est le premier
						lignes.add(new Fiche.Ligne(action.getDate(), cel, null, stock));
					else
						lignes.add(new Fiche.Ligne(action.getDate(), null, null, stock));
				}
			}
			else if(action.getType() == Action.SORTIE) {
				int aSortir = action.getQuantite(); // quantité à sortir des stocks
				while (aSortir > 0) {
					if(stocks.isEmpty()) throw new IllegalStateException("Une sortie a été tentée alors qu'il ne reste plus assez de stock !");

					Fiche.Cellule stock = stocks.poll(); // on prend le premier stock
					int nvQuantite = stock.quantite() - aSortir; // nouvelle quantité du stock après la sortie

					if(nvQuantite <= 0) { //si le stock pris a été vidé
						lignes.add(new Fiche.Ligne(action.getDate(), null, new Fiche.Cellule(stock.quantite(), stock.prixUnitaire()), null));
					}
					else { // s'il y a toujours quelque chose dans le stock
						Fiche.Cellule celStock = new Fiche.Cellule(nvQuantite, stock.prixUnitaire());
						stocks.add(celStock);
						lignes.add(new Fiche.Ligne(action.getDate(), null, new Fiche.Cellule(aSortir, stock.prixUnitaire()), celStock));
						// on ajoute le stock avec sa nouvelle quantité et la sortie
					}
					aSortir = aSortir - stock.quantite();
				}
			}
		}
		return new Fiche(lignes);
	}
}
