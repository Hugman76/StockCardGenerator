package hugman.stock.gui;

import hugman.stock.ControlleurActions;
import hugman.stock.fiche.Fiche;
import hugman.stock.fiche.file.FicheStockFileChooser;
import hugman.stock.gui.table.GrilleActionsModel;
import hugman.stock.gui.table.TableActions;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class FenetreActions extends JFrame
{
	private TableActions table;
	private ControlleurActions ctrl;
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

	public FenetreActions(ControlleurActions ctrl) {
		this.ctrl = ctrl;
		this.table = new TableActions(new GrilleActionsModel(ctrl));
		this.table.setFillsViewportHeight(true);

		this.setJMenuBar(this.creerBarreMenu());
		this.setLayout(new BorderLayout());

		JButton btnAdd    = new JButton("Ajouter");
		JButton btnRemove = new JButton("Supprimer");
		btnAdd   .addActionListener(this.addListener);
		btnRemove.addActionListener(this.removeListener);

		JButton btnMoveUp   = new JButton("↑");
		JButton btnMoveDown = new JButton("↓");
		btnMoveUp  .addActionListener(this.moveUpListener);
		btnMoveDown.addActionListener(this.moveDownListener);

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
		JMenu menuFichier = new JMenu("Fichier");
		JMenu menuLignes  = new JMenu("Lignes");

		JMenuItem itmExporter = new JMenuItem("Exporter la fiche de stocks", KeyEvent.VK_E);
		itmExporter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		itmExporter.addActionListener(e -> {
			Fiche fiche = ctrl.creerFicheStockPEPS();

			FicheStockFileChooser fc = new FicheStockFileChooser(fiche);
			fc.setDialogTitle("Exporter la fiche de stock sous...");
			fc.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));
			fc.showDialogAndSave(this);
		});

		JMenuItem itmAdd      = new JMenuItem("Ajouter",               KeyEvent.VK_A);
		JMenuItem itmRemove   = new JMenuItem("Supprimer",             KeyEvent.VK_S);
		JMenuItem itmMoveUp   = new JMenuItem("Déplacer vers le haut", KeyEvent.VK_H);
		JMenuItem itmMoveDown = new JMenuItem("Déplacer vers le bas",  KeyEvent.VK_B);
		itmAdd     .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.SHIFT_MASK));
		itmRemove  .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
		itmMoveUp  .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
		itmMoveDown.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.SHIFT_MASK));

		itmAdd     .addActionListener(this.addListener);
		itmRemove  .addActionListener(this.removeListener);
		itmMoveUp  .addActionListener(this.moveUpListener);
		itmMoveDown.addActionListener(this.moveDownListener);

		barre.add(menuFichier);
		barre.add(menuLignes);
		menuFichier.add(itmExporter);
		menuLignes.add(itmAdd);
		menuLignes.add(itmRemove);
		menuLignes.add(itmMoveUp);
		menuLignes.add(itmMoveDown);

		return barre;
	}
}
