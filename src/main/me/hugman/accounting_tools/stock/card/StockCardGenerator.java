package me.hugman.accounting_tools.stock.card;

import me.hugman.accounting_tools.stock.action.Action;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class StockCardGenerator
{
	/**
	 * First-in First-out
	 *
	 * @return
	 * @throws IllegalStateException
	 */
	public static StockCard createFIFO(List<Action> actions) throws IllegalStateException {
		ArrayList<StockCard.Line> lines = new ArrayList<>();
		Deque<StockCard.Cell> stocks = new ArrayDeque<>();
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
					if(stock == stocks.peek()) // if this stock is the first one
						lines.add(new StockCard.Line(action.getDate(), cell, null, stock));
					else
						lines.add(new StockCard.Line(action.getDate(), null, null, stock));
				}
			}
			else if(action.getType() == Action.OUTPUT) {
				int toOutput = action.getQuantity(); // the quantity to output
				while(toOutput > 0) {
					if(stocks.isEmpty()) throw new IllegalStateException("Une sortie a été tentée alors qu'il ne reste plus assez de stock !");

					StockCard.Cell stock = stocks.poll(); // take the first stock
					int newQuantity = stock.quantity() - toOutput; // new quantity after the output

					if(newQuantity <= 0) { // if the stock has been emptied
						lines.add(new StockCard.Line(action.getDate(), null, new StockCard.Cell(stock.quantity(), stock.unitPrice()), null));
					}
					else { // if there's still something in the stock
						StockCard.Cell celStock = new StockCard.Cell(newQuantity, stock.unitPrice());
						stocks.add(celStock);
						lines.add(new StockCard.Line(action.getDate(), null, new StockCard.Cell(toOutput, stock.unitPrice()), celStock));
						// add the stock with its new quantity and add the output
					}
					toOutput = toOutput - stock.quantity();
				}
			}
		}
		return new StockCard(lines);
	}
}
