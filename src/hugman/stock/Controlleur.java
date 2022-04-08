package hugman.stock;

import hugman.stock.action.Action;
import hugman.stock.gui.FenetreActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Controlleur
{
	private final List<Action> actions;
	private static final String NOM_APPLICATION = "Inventaire Stockeur 3000";

	public static void main(String[] args) {
		Controlleur ctrl = new Controlleur();
	}

	public Controlleur() {
		this.actions = new ArrayList<>();
		this.getActions().add(new Action(Action.STOCK_INITIAL, null, 1, 10));

		FenetreActions fenetreActions = new FenetreActions(this);
		fenetreActions.setName(NOM_APPLICATION);
		fenetreActions.setVisible(true);
		fenetreActions.pack();
	}

	public List<Action> getActions() {
		return actions;
	}
}
