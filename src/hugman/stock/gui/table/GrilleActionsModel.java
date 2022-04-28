package hugman.stock.gui.table;

import hugman.stock.ControlleurActions;
import hugman.stock.action.Action;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class GrilleActionsModel extends AbstractTableModel
{
	private static final String[] COLONNES = {"Date", "Type d'action", "Quantité", "Prix unitaire"};

	private ControlleurActions ctrl;
	private Object[][] tabDonnees;

	public GrilleActionsModel(ControlleurActions ctrl) {
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
		// Bloquer le type du stock initial
		if(lig == 0 && col == 1) return false;

		// Bloquer le prix unitaire des sorties
		if(this.tabDonnees[lig][1].equals(Action.TYPES[Action.SORTIE]) && col == 3) return false;

		return true;
	}

	public void addAction() {
		this.ctrl.getActions().add(Action.creerDefaut());
		this.refreshAllRows();
	}

	public void removeAction(int i) {
		if(i > 0) {
			this.ctrl.getActions().remove(i);
			this.refreshAllRows();
		}
	}

	public boolean moveAction(int i, int offset) {
		int j = i + offset;
		if(i <= 0 || j <= 0 || i >= this.ctrl.getActions().size() || j >= this.ctrl.getActions().size()) return false;
		Action action = this.ctrl.getActions().get(i);
		this.ctrl.getActions().remove(i);
		this.ctrl.getActions().add(j, action);
		return true;
	}

	public void refreshAllRows() {
		List<Action> lstClients = ctrl.getActions();
		this.tabDonnees = new Object[lstClients.size()][4];

		for(int lig = 0; lig < lstClients.size(); lig++) {
			Action action = lstClients.get(lig);

			this.tabDonnees[lig][0] = action.getDate();
			this.tabDonnees[lig][1] = Action.TYPES[action.getType()];
			this.tabDonnees[lig][2] = action.getQuantite();

			if(action.getType() == Action.SORTIE) this.tabDonnees[lig][3] = null;
			else                                  this.tabDonnees[lig][3] = action.getPrixUnitaire();
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
			if(Action.TYPES[Action.SORTIE].equals(value)) {
				// Cacher le prix unitaire si la ligne devient une sortie
				this.tabDonnees[lig][3] = null;
				this.fireTableCellUpdated(lig, 3);
			}
			if(Action.TYPES[Action.ENTREE].equals(value)) {
				// Refaire apparaître le prix unitaire si la ligne devient une entree
				this.tabDonnees[lig][3] = action.getPrixUnitaire();
				this.fireTableCellUpdated(lig, 3);
			}
		}
	}
}
