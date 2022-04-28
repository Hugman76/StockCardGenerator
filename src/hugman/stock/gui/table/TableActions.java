package hugman.stock.gui.table;

import hugman.stock.action.Action;

import javax.swing.*;

public class TableActions extends JTable
{
	public static final String[] CHOIX_TYPE = { Action.TYPES[Action.ENTREE], Action.TYPES[Action.SORTIE] };

	public TableActions(GrilleActionsModel model)
	{
		super(model);

		JComboBox<String> comboChoixType  = new JComboBox<>(CHOIX_TYPE);
		DefaultCellEditor editorChoixType = new DefaultCellEditor(comboChoixType);
		this.getColumnModel().getColumn(1).setCellEditor(editorChoixType);

		this.clearSelection();
	}

	@Override
	public GrilleActionsModel getModel() {
		return (GrilleActionsModel)super.getModel();
	}
}
