package testat01;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Order {
	private Item[] items;

	Order(Item... items) {
		this.items = items;
	}

	public BigDecimal getTotalPrice() {
		BigDecimal total = BigDecimal.ZERO;
		for (Item item : items) {
			total = total.add(item.getPrice());
		}
		return total;
	}

	public void printItems() {
		System.out.println("The following items are in this order:");
		for (Item item : items) {
			item.print();
		}
	}

	public boolean isNotCyclic() {
		for (Item item : items) {
			if (!item.isNotCyclic(new ArrayList<Item>())) {
				System.out.println("Bundle Item " + item.getDescription() + " contains a cycle!");
				return false;
			}
		}
		return true;
	}
}
