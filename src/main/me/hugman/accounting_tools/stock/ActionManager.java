package me.hugman.accounting_tools.stock;

import me.hugman.accounting_tools.stock.action.Action;
import me.hugman.accounting_tools.stock.gui.ActionFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

public class ActionManager
{
	public static final Preferences PREFERENCES = Preferences.userNodeForPackage(ActionManager.class);
	private static final String APP_NAME = "Inventaire Stockeur 3000";
	private final List<Action> actions;

	public ActionManager() {
		this.actions = new ArrayList<>();
		//this.getActions().add(new Action(Action.STOCK_INITIAL, null, 1, 10));

		// tests
		this.getActions().add(Action.createInitialStock(new Date(2022 - 1900, Calendar.APRIL, 1), 30, 50.00F));
		this.getActions().add(Action.createOutput(new Date(2022 - 1900, Calendar.APRIL, 2), 10));
		this.getActions().add(Action.createInput(new Date(2022 - 1900, Calendar.APRIL, 7), 20, 60.00F));
		this.getActions().add(Action.createOutput(new Date(2022 - 1900, Calendar.APRIL, 14), 35));
	}

	public List<Action> getActions() {
		return actions;
	}

	public void openWindow() {
		ActionFrame actionFrame = new ActionFrame(this);
		actionFrame.setTitle(APP_NAME);
		actionFrame.pack();
		actionFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		actionFrame.setVisible(true);
	}
}
