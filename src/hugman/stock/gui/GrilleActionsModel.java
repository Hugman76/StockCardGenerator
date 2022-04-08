package hugman.stock.gui;

import hugman.stock.Controlleur;
import hugman.stock.action.Action;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class GrilleActionsModel extends AbstractTableModel
{
	private static final String[] COLONNES = {"Date", "Type d'action", "Quantité", "Prix unitaire"};

	private Controlleur ctrl;
	private Object[][] tabDonnees;

	public GrilleActionsModel(Controlleur ctrl) {
		this.ctrl = ctrl;
		this.refreshAllRows();
	}

	@Override
	public int getColumnCount() {
		return COLONNES.length;
	}

	@Override
	public int getRowCount() {
		return this.tabDonnees.length;
	}

	@Override
	public String getColumnName(int col) {
		return COLONNES[col];
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return switch(col) {
			case 0 ->  Date.class;
			default -> String.class;
			case 2 ->  Integer.class;
			case 3 ->  Float.class;
		};
	}

	@Override
	public Object getValueAt(int lig, int col) {
		return this.tabDonnees[lig][col];
	}

	@Override
	public boolean isCellEditable(int lig, int col) {
		return lig != 0 || col != 1;
	}

	public void addRow() {
		this.ctrl.getActions().add(new Action(Action.ENTREE, null, 1, 10));
		this.refreshAllRows();
	}

	public void removeRow(int i) {
		if(i > 0) {
			this.ctrl.getActions().remove(i);
			this.refreshAllRows();
		}
	}

	public void removeRow() {
		this.removeRow(this.ctrl.getActions().size() - 1);
	}

	private void refreshAllRows() {
		List<Action> lstClients = ctrl.getActions();
		this.tabDonnees = new Object[lstClients.size()][4];

		for(int lig = 0; lig < lstClients.size(); lig++) {
			Action action = lstClients.get(lig);

			this.tabDonnees[lig][0] = action.getDate();
			this.tabDonnees[lig][1] = action.getType();
			this.tabDonnees[lig][2] = action.getQuantite();
			this.tabDonnees[lig][3] = action.getPrixUnitaire();
		}
		this.fireTableDataChanged();
	}

	@Override
	public void setValueAt(Object value, int lig, int col) {
		Action action = new Action(this.ctrl.getActions().get(lig));
		boolean valide = switch(col) {
			case 0 -> action.setDate((Date) value);
			case 1 -> action.setType((String) value);
			case 2 -> action.setQuantite((int) value);
			case 3 -> action.setPrixUnitaire((float) value);
			default -> throw new IllegalStateException("Unexpected value: " + col);
		};
		if(valide) {
			this.ctrl.getActions().set(lig, action);
			this.tabDonnees[lig][col] = value;
			this.fireTableCellUpdated(lig, col);
		}
	}
}
