package me.hugman.acctools.action.ui.table;

import me.hugman.acctools.action.ActionManager;
import me.hugman.acctools.action.Action;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class ActionTableModel extends AbstractTableModel
{
	private static final String[] COLUMNS = {"Date", "Type d'action", "Quantité", "Prix unitaire"};

	private final ActionManager manager;
	private Object[][] dataTable;

	public ActionTableModel(ActionManager manager) {
		this.manager = manager;
		this.refreshAllRows();
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public int getRowCount() {
		return this.dataTable.length;
	}

	@Override
	public String getColumnName(int col) {
		return COLUMNS[col];
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return switch(col) {
			case 0 -> Date.class;
			default -> String.class;
			case 2 -> Integer.class;
			case 3 -> Float.class;
		};
	}

	@Override
	public Object getValueAt(int lig, int col) {
		return this.dataTable[lig][col];
	}

	@Override
	public boolean isCellEditable(int lig, int col) {
		// Lock the type for the initial stock row
		if(lig == 0 && col == 1) return false;

		// Lock the unit price of output rows
		return !this.dataTable[lig][1].equals(Action.TYPES[Action.OUTPUT]) || col != 3;
	}

	public void addAction() {
		this.manager.getActions().add(Action.createDefault());
		this.refreshAllRows();
	}

	public void removeAction(int i) {
		if(i > 0) {
			this.manager.getActions().remove(i);
			this.refreshAllRows();
		}
	}

	public boolean moveAction(int i, int offset) {
		int j = i + offset;
		if(i <= 0 || j <= 0 || i >= this.manager.getActions().size() || j >= this.manager.getActions().size()) return false;
		Action action = this.manager.getActions().get(i);
		this.manager.getActions().remove(i);
		this.manager.getActions().add(j, action);
		return true;
	}

	public void refreshAllRows() {
		List<Action> actions = manager.getActions();
		this.dataTable = new Object[actions.size()][4];

		for(int lig = 0; lig < actions.size(); lig++) {
			Action action = actions.get(lig);

			this.dataTable[lig][0] = action.getDate();
			this.dataTable[lig][1] = Action.TYPES[action.getType()];
			this.dataTable[lig][2] = action.getQuantity();

			if(action.getType() == Action.OUTPUT) this.dataTable[lig][3] = null;
			else this.dataTable[lig][3] = action.getUnitPrice();
		}
		this.fireTableDataChanged();
	}

	@Override
	public void setValueAt(Object value, int lig, int col) {
		Action action = new Action(this.manager.getActions().get(lig));
		boolean valid = switch(col) {
			case 0 -> action.setDate((Date) value);
			case 1 -> action.setType((String) value);
			case 2 -> action.setQuantity((int) value);
			case 3 -> action.setUnitPrice((float) value);
			default -> throw new IllegalStateException("Unexpected value: " + col);
		};
		if(valid) {
			this.manager.getActions().set(lig, action);
			this.dataTable[lig][col] = value;
			this.fireTableCellUpdated(lig, col);
			if(Action.TYPES[Action.OUTPUT].equals(value)) {
				// Hide the unit price if the row is converting to an output
				this.dataTable[lig][3] = null;
				this.fireTableCellUpdated(lig, 3);
			}
			if(Action.TYPES[Action.INPUT].equals(value)) {
				// Show up the unit price if the row is converting to an input
				this.dataTable[lig][3] = action.getUnitPrice();
				this.fireTableCellUpdated(lig, 3);
			}
		}
	}
}
