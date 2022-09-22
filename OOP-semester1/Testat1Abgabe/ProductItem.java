package testat01;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductItem extends Item {
	private int amount;
	private BigDecimal pricePerUnit;

	ProductItem(String description) {
		super(description);
	}

	ProductItem(String description, int amount, BigDecimal pricePerUnit) {
		super(description);
		this.amount = amount;
		this.pricePerUnit = pricePerUnit;
	}

	@Override
	public BigDecimal getPrice() {
		return pricePerUnit.multiply(BigDecimal.valueOf(amount));
	}

	@Override
	public void print() {
		System.out.println("Product description: " + this.getDescription() + ", Price per Unit: " + pricePerUnit
				+ ", Amount: " + amount + ", Price: " + getPrice());
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	@Override
	protected boolean isNotCyclic(ArrayList<Item> bundleList) {
		return true;
	}
}
