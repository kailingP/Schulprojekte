package testat01;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ServiceItem extends Item {
	private BigDecimal price;

	ServiceItem(String description) {
		super(description);
	}

	ServiceItem(String description, BigDecimal price) {
		super(description);
		this.price = price;
	}

	@Override
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public void print() {
		System.out.println("Service description: " + getDescription() + ", price: " + getPrice());
	}

	@Override
	protected boolean isNotCyclic(ArrayList<Item> bundleList) {
		return true;
	}
}
