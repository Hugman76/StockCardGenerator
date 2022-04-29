package me.hugman.acctools;

import me.hugman.acctools.action.ActionManager;
import me.hugman.acctools.action.Action;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Main
{
	public static final String APP_NAME = "Outils de comptabilité";
	public static final JFrame MAIN_MENU = Main.createMainMenu();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		MAIN_MENU.setTitle(APP_NAME);
		MAIN_MENU.setVisible(true);
		MAIN_MENU.setMinimumSize(new Dimension(500, 500));
		MAIN_MENU.setLocationRelativeTo(null);
		MAIN_MENU.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		MAIN_MENU.setVisible(true);
	}

	public static JFrame createMainMenu() {
		JFrame frame = new JFrame();
		JPanel pnlAction = new JPanel();
		JPanel pnlActionBtns = new JPanel();
		JLabel lblActionName = new JLabel("Actions", JLabel.CENTER);
		JButton btnNewFile = new JButton("Nouveau fichier");
		JButton btnExample1 = new JButton("Exemple 1");
		JButton btnExample2 = new JButton("Exemple 2");

		frame.add(pnlAction);

		pnlAction.setLayout(new GridLayout(2, 1));
		pnlAction.add(lblActionName);
		pnlAction.add(pnlActionBtns);

		GridLayout lytBtns = new GridLayout(3, 1);
		lytBtns.setHgap(10);
		lytBtns.setVgap(10);
		pnlActionBtns.setLayout(lytBtns);
		pnlActionBtns.add(btnNewFile);
		pnlActionBtns.add(btnExample1);
		pnlActionBtns.add(btnExample2);

		btnNewFile.addActionListener(e -> {
			ArrayList<Action> actions = new ArrayList<>();
			actions.add(Action.createInitialStock(null, 1, 5.00F));
			openActionMenu(actions);
		});

		btnExample1.addActionListener(e -> {
			ArrayList<Action> actions = new ArrayList<>();
			actions.add(Action.createInitialStock(new Date(2022 - 1900, Calendar.APRIL, 1), 30, 50.00F));
			actions.add(Action.createOutput(new Date(2022 - 1900, Calendar.APRIL, 2), 10));
			actions.add(Action.createInput(new Date(2022 - 1900, Calendar.APRIL, 7), 20, 60.00F));
			actions.add(Action.createOutput(new Date(2022 - 1900, Calendar.APRIL, 14), 35));
			openActionMenu(actions);
		});

		btnExample2.addActionListener(e -> {
			ArrayList<Action> actions = new ArrayList<>();
			actions.add(Action.createInitialStock(new Date(2022 - 1900, Calendar.MAY, 4), 25, 900.00F));
			actions.add(Action.createOutput(new Date(2022 - 1900, Calendar.MAY, 9), 15));
			actions.add(Action.createInput(new Date(2022 - 1900, Calendar.MAY, 18), 70, 975.00F));
			actions.add(Action.createOutput(new Date(2022 - 1900, Calendar.MAY, 24), 20));
			actions.add(Action.createOutput(new Date(2022 - 1900, Calendar.MAY, 30), 30));
			actions.add(Action.createInput(new Date(2022 - 1900, Calendar.MAY, 30), 15, 930.00F));
			openActionMenu(actions);
		});

		return frame;
	}

	public static void openActionMenu(ArrayList<Action> actions) {
		new ActionManager(actions).openWindow();
		MAIN_MENU.setVisible(false);
	}
}
