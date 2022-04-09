package hugman.stock;

import hugman.stock.action.Action;
import hugman.stock.fiche.FicheStock;
import hugman.stock.fiche.LigneStock;
import hugman.stock.gui.FenetreActions;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ControlleurActions
{
	private final List<Action> actions;
	private static final String NOM_APPLICATION = "Inventaire Stockeur 3000";

	public static void main(String[] args) {
		ControlleurActions ctrl = new ControlleurActions();
	}

	public ControlleurActions() {
		this.actions = new ArrayList<>();
		this.getActions().add(new Action(Action.STOCK_INITIAL, null, 1, 10));

		FenetreActions fenetreActions = new FenetreActions(this);
		fenetreActions.setTitle(NOM_APPLICATION);
		fenetreActions.setVisible(true);
		fenetreActions.pack();
		fenetreActions.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public List<Action> getActions() {
		return actions;
	}

	public FicheStock creerFicheStockFifo() {
		ArrayList<LigneStock> lignes = new ArrayList<>();
		return new FicheStock(lignes);
	}
}
