package me.hugman.acctools.action;

import me.hugman.acctools.Main;
import me.hugman.acctools.action.ui.ActionFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class ActionManager
{
	public static final Preferences PREFERENCES = Preferences.userNodeForPackage(ActionManager.class);
	public static final String CARD_METHOD_PREFERENCE = "cardMtd";
	public static final String DELIMITER_PREFERENCE = "dlmt";
	private final ArrayList<Action> actions;

	public ActionManager(ArrayList<Action> actions) {
		this.actions = actions;
		if(this.actions.isEmpty()) resetActions();
	}

	public void openWindow() {
		ActionFrame actionFrame = new ActionFrame(this);
		actionFrame.setTitle(Main.APP_NAME + "- Générateur de fiche de stocks");
		actionFrame.pack();
		actionFrame.setLocationRelativeTo(Main.MAIN_MENU);
		actionFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		actionFrame.setVisible(true);
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void resetActions() {
		actions.clear();
		actions.add(Action.createInitialStock(null, 1, 5.00F));
	}
}
