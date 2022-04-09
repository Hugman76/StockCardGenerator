package hugman.stock.gui;

import hugman.stock.ControlleurActions;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class FenetreActions extends JFrame
{
	private TableActions table;

	public FenetreActions(ControlleurActions ctrl) {
		this.table = new TableActions(new GrilleActionsModel(ctrl));
		this.table.setFillsViewportHeight(true);

		JPanel  panelBoutons = new JPanel();
		JButton btnAjouter   = new JButton("Ajouter");
		JButton btnSupprimer = new JButton("Supprimer");
		btnAjouter  .addActionListener(e -> this.table.getModel().addRow());
		btnSupprimer.addActionListener(e -> {
			int[] rows = this.table.getSelectedRows().clone();
			Arrays.sort(rows);
			for(int i = rows.length - 1; i >= 0; i--) {
				this.table.getModel().removeRow(rows[i]);
			}
			this.table.clearSelection();
		});

		panelBoutons.setLayout(new FlowLayout());
		panelBoutons.add(btnAjouter);
		panelBoutons.add(btnSupprimer);

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(this.table), BorderLayout.CENTER);
		this.add(panelBoutons, BorderLayout.SOUTH);
	}
}
