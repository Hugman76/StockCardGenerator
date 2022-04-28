package me.hugman.accounting_tools.stock.action;

import java.util.Date;

public class Action
{
	public static final String[] TYPES = {"Stock initial", "Entrée", "Sortie"};

	public static final int INITIAL_STOCK = 0;
	public static final int INPUT = 1;
	public static final int OUTPUT = 2;

	private int type;
	private Date date;
	private int quantity;
	private float unitPrice;

	private Action(int type, Date date, int quantity, float unitPrice) {
		this.type = type;
		this.date = date;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public Action(Action action) {
		this(action.getType(), action.getDate(), action.getQuantity(), action.getUnitPrice());
	}

	public static Action createDefault() {
		return new Action(Action.INPUT, null, 1, 10);
	}

	public static Action createInitialStock(Date date, int quantite, float prixUnitaire) {
		return new Action(Action.INITIAL_STOCK, date, quantite, prixUnitaire);
	}

	public static Action createInput(Date date, int quantite, float prixUnitaire) {
		return new Action(Action.INPUT, date, quantite, prixUnitaire);
	}

	public static Action createOutput(Date date, int quantite) {
		return new Action(Action.OUTPUT, date, quantite, 0);
	}

	public int getType() {
		return this.type;
	}

	public boolean setType(String type) {
		for(int i = 0; i < TYPES.length; i++) {
			if(TYPES[i].equals(type)) {
				this.type = i;
				return true;
			}
		}
		return false;
	}

	public boolean setType(int type) {
		this.type = type;
		return (type >= 0 && type < TYPES.length);
	}

	public Date getDate() {
		return this.date;
	}

	public boolean setDate(Date date) {
		this.date = date;
		return true;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public boolean setQuantity(int quantity) {
		this.quantity = quantity;
		return quantity > 0;
	}

	public float getUnitPrice() {
		return this.unitPrice;
	}

	public boolean setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
		return this.unitPrice >= 0.0F;
	}

	public double getAmount() {
		return this.quantity * this.unitPrice;
	}

	public boolean isValid(int quantiteTotale) {
		return (this.type != OUTPUT || this.quantity <= quantiteTotale);
	}
}
