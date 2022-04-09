package hugman.stock.gui;

import hugman.stock.ControlleurActions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class FenetreActions extends JFrame
{
	private TableActions table;
	private ControlleurActions ctrl;

	public FenetreActions(ControlleurActions ctrl) {
		this.ctrl = ctrl;
		this.table = new TableActions(new GrilleActionsModel(ctrl));
		this.table.setFillsViewportHeight(true);

		this.setJMenuBar(this.creerBarreMenu());
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(this.table), BorderLayout.CENTER);
	}

	public JMenuBar creerBarreMenu() {
		JMenuBar barre = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		JMenu menuLignes  = new JMenu("Lignes");

		JMenuItem itmExporter = new JMenuItem("Exporter", KeyEvent.VK_E);
		itmExporter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		itmExporter.addActionListener(e -> ctrl.creerFicheStockFifo());

		JMenuItem itmAjouter      = new JMenuItem("Ajouter",   KeyEvent.VK_A);
		JMenuItem itmSupprimer    = new JMenuItem("Supprimer", KeyEvent.VK_S);
		JMenuItem itmDeplacerHaut = new JMenuItem("Déplacer vers le haut", KeyEvent.VK_H);
		JMenuItem itmDeplacerBas  = new JMenuItem("Déplacer vers le bas",  KeyEvent.VK_B);
		itmAjouter  .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.SHIFT_MASK));
		itmSupprimer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
		itmDeplacerHaut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
		itmDeplacerBas .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.SHIFT_MASK));

		itmAjouter  .addActionListener(e -> this.table.getModel().addRow());
		itmSupprimer.addActionListener(e -> {
			int[] rows = this.table.getSelectedRows().clone();
			Arrays.sort(rows);
			for(int i = rows.length - 1; i >= 0; i--) {
				this.table.getModel().removeRow(rows[i]);
			}
			this.table.clearSelection();
		});
		itmDeplacerHaut.addActionListener(e -> {
			// TODO
		});
		itmDeplacerBas .addActionListener(e -> {
			// TODO
		});

		barre.add(menuFichier);
		barre.add(menuLignes);
		menuFichier.add(itmExporter);
		menuLignes.add(itmAjouter);
		menuLignes.add(itmSupprimer);
		menuLignes.add(itmDeplacerHaut);
		menuLignes.add(itmDeplacerBas);

		return barre;
	}
}
