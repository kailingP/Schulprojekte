package testat01;

import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * 
 * @author Solution by Linus Flury
 *
 */
public class OrderSystemTest {
	public static void main(String[] args) {
		// BigDecimal for reliable divisions and precision
		ProductItem cheese = new ProductItem("Cheese", 5, BigDecimal.valueOf(22.03));
		ProductItem tomatoes = new ProductItem("Tomatoes", 3, BigDecimal.valueOf(3.45));
		ProductItem dough = new ProductItem("Dough", 1, BigDecimal.valueOf(5.18));
		ServiceItem kneadDough = new ServiceItem("Knead Dough", BigDecimal.valueOf(523.45));
		BundleItem kneadedDough = new BundleItem("Kneaded Dough", 20, dough, kneadDough);
		BundleItem toppings = new BundleItem("Toppings", 80, tomatoes, cheese);
		BundleItem pizza = new BundleItem("Pizza", 10, tomatoes, toppings, kneadedDough);
		System.out.println("Expected prize of pizza: 0.9*(3*3.45+0.2*(3*3.45+5*22.03)+0.8*(5.18+523.45))=411.62");
		testOrder(new Order(pizza));
		pizza.removeItem(tomatoes);
		System.out.println("Expected prize of pizza: 0.9*(0.2*(3*3.45+5*22.03)+0.8*(5.18+523.45))=402.30");
		testOrder(new Order(pizza));
		System.out.println("Expected prize of toppings: 0.2*(3*3.45+5*22.03)=24.10");
		testItem(toppings);
		toppings.AddItem(toppings);
		System.out.println("Cycle expected for next item!");
		testItem(toppings);
		testOrder(new Order(toppings));
		toppings.removeItem(toppings);
		System.out.println("Expected: not contained");
		toppings.removeItem(dough);
		toppings.removeItem(tomatoes);
		toppings.removeItem(cheese);
		System.out.println("Expected: Empty bundle");
		testItem(toppings);
		pizza.AddItem(tomatoes);
		pizza.AddItem(tomatoes);
		System.out.println("Expected prize of pizza: 0.9*(0.2*(0)+0.8*(5.18+523.45)+3*3.45+3*3.45)=399.24");
		testOrder(new Order(pizza));
		kneadedDough.AddItem(toppings);
		toppings.AddItem(pizza);
		System.out.println("Test indirect cycle:");
		testItem(pizza);
		
	}

	private static void testOrder(Order order) {
		System.out.println("------------");
		if(order.isNotCyclic()) {
			System.out.println("Total price: " + order.getTotalPrice());
			order.printItems();	
		}
		System.out.println("------------");
	}

	private static void testItem(Item item) {
		System.out.println("------------\nTesting Item: "+item.getDescription());
		if (item.isNotCyclic(new ArrayList<Item>())) {
			System.out.println("Total price: " + item.getPrice());
			item.print();
		}
		System.out.println("------------");
	}
}
