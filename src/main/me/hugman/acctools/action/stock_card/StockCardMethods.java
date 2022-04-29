package me.hugman.acctools.action.stock_card;

import me.hugman.acctools.action.Action;

import java.util.ArrayList;
import java.util.List;

public class StockCardMethods
{
	public static final String[] NAMES = new String[]{"FIFO", "ACM"};

	/**
	 * <a href="https://en.wikipedia.org/wiki/FIFO_and_LIFO_accounting#FIFO">First-in, first-out method</a>
	 */
	public static StockCard toFifo(List<Action> actions) throws IllegalStateException {
		ArrayList<StockCard.Line> lines = new ArrayList<>();
		ArrayList<StockCard.Cell> stocks = new ArrayList<>();
		for(Action action : actions) {
			if(action.getType() == Action.INITIAL_STOCK) {
				StockCard.Cell stockCell = new StockCard.Cell(action.getQuantity(), action.getUnitPrice());
				lines.add(new StockCard.Line(action.getDate(), null, null, stockCell));
				stocks.add(stockCell);
			}
			else if(action.getType() == Action.INPUT) {
				StockCard.Cell cell = new StockCard.Cell(action.getQuantity(), action.getUnitPrice());
				stocks.add(cell);

				for(StockCard.Cell stock : stocks) {
					if(stock == stocks.get(0)) // if this stock is the first one
						lines.add(new StockCard.Line(action.getDate(), cell, null, stock));
					else
						lines.add(new StockCard.Line(action.getDate(), null, null, stock));
				}
			}
			else if(action.getType() == Action.OUTPUT) {
				ArrayList<StockCard.Cell> outputs = new ArrayList<>();
				int toOutput = action.getQuantity(); // the quantity to output
				while(toOutput > 0) {
					if(stocks.isEmpty()) throw new IllegalStateException("Une sortie a été tentée alors qu'il ne reste plus assez de stock !");

					StockCard.Cell stock = stocks.get(0);
					stocks.remove(stock); // take the first stock out

					int newQuantity = stock.quantity() - toOutput; // new quantity after the output

					if(newQuantity <= 0) { // if the stock has been emptied
						outputs.add(new StockCard.Cell(stock.quantity(), stock.unitPrice()));
					}
					else { // if there's still something in the stock
						outputs.add(new StockCard.Cell(toOutput, stock.unitPrice()));
						stocks.add(new StockCard.Cell(newQuantity, stock.unitPrice()));
						// add the stock with its new quantity and add the remaining output
					}
					toOutput = toOutput - stock.quantity();
				}
				for(int i = 0; i < Math.max(outputs.size(), stocks.size()); i++) {
					StockCard.Cell output, stock;
					try {output = outputs.get(outputs.size() >= stocks.size() ? i : i - outputs.size());}
					catch(IndexOutOfBoundsException e) {output = null;}
					try {stock = stocks.get(stocks.size() >= outputs.size() ? i : i - stocks.size());}
					catch(IndexOutOfBoundsException e) {stock = null;}
					lines.add(new StockCard.Line(action.getDate(), null, output, stock));
				}
			}
		}
		return new StockCard(lines);
	}

	/**
	 * <a href="https://en.wikipedia.org/wiki/Average_cost_method">Average cost method</a>
	 */
	public static StockCard toAcm(List<Action> actions) throws IllegalStateException {
		ArrayList<StockCard.Line> lines = new ArrayList<>();
		StockCard.Cell stock = null;
		for(Action action : actions) {
			if(action.getType() == Action.INITIAL_STOCK) {
				stock = new StockCard.Cell(action.getQuantity(), action.getUnitPrice());
				lines.add(new StockCard.Line(action.getDate(), null, null, stock));
			}
			else {
				if(stock == null) throw new IllegalStateException("Une entrée ou sortie a été tentée alors qu'il n'y a pas de stock !");
				if(action.getType() == Action.INPUT) {
					StockCard.Cell newCell = new StockCard.Cell(action.getQuantity(), action.getUnitPrice());
					float newUnitPrice = (stock.unitPrice() * stock.quantity() + action.getUnitPrice() * action.getQuantity()) / (stock.quantity() + action.getQuantity());
					int newQuantity = stock.quantity() + action.getQuantity();
					stock = new StockCard.Cell(newQuantity,  newUnitPrice);
					lines.add(new StockCard.Line(action.getDate(), newCell, null, stock));
				}
				else if(action.getType() == Action.OUTPUT) {
					StockCard.Cell newCell = new StockCard.Cell(action.getQuantity(), stock.unitPrice());
					int newQuantity = stock.quantity() - action.getQuantity();
					stock = newQuantity > 0 ? new StockCard.Cell(newQuantity, stock.unitPrice()) : null;
					lines.add(new StockCard.Line(action.getDate(), null, newCell, stock));
				}
			}
		}
		return new StockCard(lines);
	}
}
