package hugman.stock.gui;

import hugman.stock.action.Action;

import javax.swing.*;

public class TableActions extends JTable
{
	public static final String[] TYPES = { Action.ENTREE, Action.SORTIE };

	public TableActions(GrilleActionsModel model)
	{
		super(model);

		// Create the combo box editor
		JComboBox<String> comboBox = new JComboBox<>(TYPES);
		DefaultCellEditor editor = new DefaultCellEditor(comboBox);

		// Assign the editor to the second column
		this.getColumnModel().getColumn(1).setCellEditor(editor);
		this.clearSelection();
	}

	@Override
	public GrilleActionsModel getModel() {
		return (GrilleActionsModel)super.getModel();
	}
}
