package testat01;

import java.math.BigDecimal;
import java.util.ArrayList;

public abstract class Item {
	private final String description;

	Item(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public abstract BigDecimal getPrice();

	public abstract void print();

	protected abstract boolean isNotCyclic(ArrayList<Item> bundleList);
}
