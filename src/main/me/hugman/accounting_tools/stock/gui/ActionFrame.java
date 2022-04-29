package me.hugman.accounting_tools.stock.gui;

import me.hugman.accounting_tools.stock.ActionManager;
import me.hugman.accounting_tools.stock.card.StockCard;
import me.hugman.accounting_tools.stock.card.StockCardGenerator;
import me.hugman.accounting_tools.stock.card.file_format.StockCardFileFormat;
import me.hugman.accounting_tools.stock.card.file_format.StockCardFileFormats;
import me.hugman.accounting_tools.stock.gui.table.ActionTable;
import me.hugman.accounting_tools.stock.gui.table.ActionTableModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class ActionFrame extends JFrame
{
	private ActionManager manager;
	private ActionTable table;
	private final ActionListener addListener = e -> this.table.getModel().addAction();
	private final ActionListener removeListener = e -> {
		int[] rows = this.table.getSelectedRows().clone();
		Arrays.sort(rows);
		for(int i = rows.length - 1; i >= 0; i--) {
			this.table.getModel().removeAction(rows[i]);
		}
		this.table.getModel().refreshAllRows();
		this.table.clearSelection();
	};
	private final ActionListener moveUpListener = e -> {
		int[] rows = this.table.getSelectedRows().clone();
		ArrayList<Integer> newSelectionRows = new ArrayList<>();
		Arrays.sort(rows);
		boolean changed = false;
		System.out.println(Arrays.toString(rows));
		for(int row : rows) {
			if(this.table.getModel().moveAction(row, -1)) {
				changed = true;
				newSelectionRows.add(row - 1);
			}
			else {
				newSelectionRows.add(row);
			}
		}
		if(changed) {
			this.table.getModel().refreshAllRows();
			for(int row : newSelectionRows) {
				this.table.addRowSelectionInterval(row, row);
			}
		}
	};
	private final ActionListener moveDownListener = e -> {
		int[] rows = this.table.getSelectedRows().clone();
		ArrayList<Integer> newSelectionRows = new ArrayList<>();
		Arrays.sort(rows);
		boolean changed = false;
		System.out.println(Arrays.toString(rows));
		for(int row : rows) {
			if(this.table.getModel().moveAction(row, 1)) {
				changed = true;
				newSelectionRows.add(row + 1);
			}
			else {
				newSelectionRows.add(row);
			}
		}
		if(changed) {
			this.table.getModel().refreshAllRows();
			for(int row : newSelectionRows) {
				this.table.addRowSelectionInterval(row, row);
			}
		}
	};
	private final ActionListener exportListener = e -> {
		StockCard stockCard = StockCardGenerator.createACM(this.manager.getActions());

		StockCardFileChooser fc = new StockCardFileChooser(stockCard);
		fc.setDialogTitle("Exporter la fiche de stock sous...");
		fc.showDialogAndSave(this);
	};

	public ActionFrame(ActionManager manager) {
		this.manager = manager;
		this.table = new ActionTable(new ActionTableModel(manager));
		this.table.setFillsViewportHeight(true);

		this.setJMenuBar(this.creerBarreMenu());
		this.setLayout(new BorderLayout());

		JButton btnAdd = new JButton("Ajouter");
		JButton btnRemove = new JButton("Supprimer");
		btnAdd.addActionListener(this.addListener);
		btnRemove.addActionListener(this.removeListener);

		JButton btnMoveUp = new JButton("↑");
		JButton btnMoveDown = new JButton("↓");
		btnMoveUp.addActionListener(this.moveUpListener);
		btnMoveDown.addActionListener(this.exportListener);

		JPanel panDown = new JPanel();
		panDown.add(btnAdd);
		panDown.add(btnRemove);

		JPanel panRight = new JPanel();
		panRight.add(btnMoveUp);
		panRight.add(btnMoveDown);

		this.add(new JScrollPane(this.table), BorderLayout.CENTER);
		this.add(panDown, BorderLayout.SOUTH);
		this.add(panRight, BorderLayout.EAST);
	}

	public JMenuBar creerBarreMenu() {
		JMenuBar barre = new JMenuBar();

		JMenu menuFile = new JMenu("Fichier");
		JMenu menuEdit = new JMenu("Édition");
		JMenu menuTools = new JMenu("Outils");
		barre.add(menuFile);
		barre.add(menuEdit);
		barre.add(menuTools);

		JMenuItem itmNew = new JMenuItem("Nouveau", KeyEvent.VK_N);
		JMenuItem itmLoad = new JMenuItem("Ouvrir", KeyEvent.VK_O);
		JMenuItem itmSave = new JMenuItem("Enregistrer", KeyEvent.VK_S);
		menuFile.add(itmNew);
		menuFile.add(itmLoad);
		menuFile.add(itmSave);

		JMenuItem itmAdd = new JMenuItem("Ajouter", KeyEvent.VK_A);
		JMenuItem itmRemove = new JMenuItem("Supprimer", KeyEvent.VK_S);
		JMenuItem itmMoveUp = new JMenuItem("Déplacer vers le haut", KeyEvent.VK_H);
		JMenuItem itmMoveDown = new JMenuItem("Déplacer vers le bas", KeyEvent.VK_B);
		itmAdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.SHIFT_MASK));
		itmRemove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
		itmMoveUp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
		itmMoveDown.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.SHIFT_MASK));
		itmAdd.addActionListener(this.addListener);
		itmRemove.addActionListener(this.removeListener);
		itmMoveUp.addActionListener(this.moveUpListener);
		itmMoveDown.addActionListener(this.moveDownListener);
		menuEdit.add(itmAdd);
		menuEdit.add(itmRemove);
		menuEdit.add(itmMoveUp);
		menuEdit.add(itmMoveDown);

		JMenuItem itmExport = new JMenuItem("Exporter la fiche de stocks", KeyEvent.VK_E);
		itmExport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		itmExport.addActionListener(this.exportListener);
		menuTools.add(itmExport);

		return barre;
	}
}
