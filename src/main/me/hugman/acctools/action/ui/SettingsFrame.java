package me.hugman.acctools.action.ui;

import me.hugman.acctools.action.ActionManager;
import me.hugman.acctools.action.stock_card.StockCardMethods;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JFrame
{
	public SettingsFrame(JFrame parentFrame)
	{
		this.setTitle("Paramètres");
		this.pack();
		this.setLocationRelativeTo(parentFrame);
		this.setMinimumSize(new Dimension(300, 300));
		this.setVisible(true);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Export", createExportSettingsPanel());
		this.add(tabbedPane, BorderLayout.CENTER);
	}


	public static JPanel createExportSettingsPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));

		panel.add(new JLabel("Méthode de fiche de stocks"));
		JComboBox<String> stockCardMethodCB = new JComboBox<>(StockCardMethods.NAMES);
		stockCardMethodCB.setSelectedIndex(ActionManager.PREFERENCES.getInt(ActionManager.CARD_METHOD_PREFERENCE, 0));
		stockCardMethodCB.addActionListener(e -> {
			for(int i = 0; i < StockCardMethods.NAMES.length; i++)
				if(i == stockCardMethodCB.getSelectedIndex())
					ActionManager.PREFERENCES.putInt(ActionManager.CARD_METHOD_PREFERENCE, i);
		});
		panel.add(stockCardMethodCB);

		panel.add(new JLabel("Délimiteur CSV"));
		JTextField textField = new JTextField(ActionManager.PREFERENCES.get(ActionManager.DELIMITER_PREFERENCE, ";"), 3);
		textField.addActionListener(e -> {
			ActionManager.PREFERENCES.put(ActionManager.DELIMITER_PREFERENCE, textField.getText());
		});
		panel.add(textField);
		return panel;
	}
}
