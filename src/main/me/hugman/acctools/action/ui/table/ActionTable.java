package me.hugman.acctools.action.ui.table;

import me.hugman.acctools.action.Action;

import javax.swing.*;

public class ActionTable extends JTable
{
	public static final String[] TYPE_CHOICES = {Action.TYPES[Action.INPUT], Action.TYPES[Action.OUTPUT]};

	public ActionTable(ActionTableModel model) {
		super(model);

		JComboBox<String> typeChoicesCombo = new JComboBox<>(TYPE_CHOICES);
		DefaultCellEditor typeCellEditor = new DefaultCellEditor(typeChoicesCombo);
		this.getColumnModel().getColumn(1).setCellEditor(typeCellEditor);

		this.clearSelection();
	}

	@Override
	public ActionTableModel getModel() {
		return (ActionTableModel) super.getModel();
	}
}
