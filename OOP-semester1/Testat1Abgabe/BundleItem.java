package testat01;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class BundleItem extends Item {
	private int discount;
	private Item[] items;

	public BundleItem(String description) {
		super(description);
	}

	public BundleItem(String description, int discount, Item... items) {
		super(description);
		this.discount = discount;
		this.items = items;
	}

	public void AddItem(Item item) {
		Item[] biggerArray = new Item[items.length + 1];
		for (int i = 0; i < items.length; i++) {
			biggerArray[i] = items[i];
		}
		items = biggerArray;
		items[items.length - 1] = item;
	}

	public void removeItem(Item item) {
		boolean hasBeenRemoved = false;
		if (items.length == 0) {
			System.out.println("Bundle " + getDescription() + " already empty!");
		}
		Item[] smallerArray = new Item[items.length - 1];
		for (int i = 0; i < items.length - 1; i++) {
			if (!hasBeenRemoved && items[i] == item) {
				System.out.println("Removing item " + item.getDescription() + " from Bundle " + getDescription());
				hasBeenRemoved = true;
				continue;
			}
			int index = hasBeenRemoved ? i - 1 : i;
			smallerArray[index] = items[i];
		}
		if (hasBeenRemoved) {
			smallerArray[smallerArray.length - 1] = items[items.length - 1];
		} else {
			if (items[items.length - 1] != item) {
				System.out.println("Item " + item.getDescription() + " is not contained in bundle " + getDescription());
				return;
			} else {
				System.out.println("Removing item " + item.getDescription() + " from Bundle " + getDescription());
			}
		}
		items = smallerArray;
	}

	protected boolean isNotCyclic(ArrayList<Item> bundleList) {
		if (bundleList.contains(this)) {
			System.out.println("Item " + getDescription() + " contains itself!");
			return false;
		}
		bundleList.add(this);
		for (Item item : items) {
			if (!item.isNotCyclic(bundleList)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public BigDecimal getPrice() {
		BigDecimal total = BigDecimal.ZERO;
		for (Item item : items) {
			total = total.add(item.getPrice());
		}
		BigDecimal withDiscount = total.multiply(BigDecimal.valueOf(100 - discount).divide(BigDecimal.valueOf(100)))
				.setScale(total.scale(), RoundingMode.HALF_UP);
		return withDiscount;
	}

	@Override
	public void print() {
		System.out.println("-----");
		System.out.println("Bundle description: " + getDescription() + ", Discount: " + discount + ", Price: "
				+ getPrice() + "\nThe following items are contained in " + getDescription() + ".");
		for (Item item : items) {
			item.print();
		}
		System.out.println("End of Bundle " + getDescription() + ".\n-----");
	}
}
