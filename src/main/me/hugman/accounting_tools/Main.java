package me.hugman.accounting_tools;

import me.hugman.accounting_tools.stock.ActionManager;

import javax.swing.*;

public class Main
{
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		ActionManager manager = new ActionManager();
		manager.openWindow();
	}
}
