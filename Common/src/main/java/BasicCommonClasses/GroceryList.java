package BasicCommonClasses;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

import CommonDefs.GroceryListExceptions.AmountIsBiggerThanAvailable;
import CommonDefs.GroceryListExceptions.ProductNotInList;
import api.contracts.IGroceryList;
import api.contracts.IGroceryPackage;
import api.contracts.ISale;

/**
 * GroceryList class - represent the container of the CartProducts that added to the cart.
 * 
 * @author Lior Ben Ami, Aviad Cohen
 * @since 2017-01-04
 */
public class GroceryList implements IGroceryList{
	HashMap<SmartCode, ProductPackage> groceryList = new HashMap<SmartCode, ProductPackage>();

	public void addProduct(ProductPackage newProductPackage) {
		/* initializing new list */
		if (groceryList == null)
			groceryList = new HashMap<SmartCode, ProductPackage>();
		
		
		ProductPackage productPackageInCart = groceryList.get(newProductPackage.getSmartCode());
		
		if (productPackageInCart == null)
			/* Add new ProductPackage to list */
			groceryList.put(newProductPackage.getSmartCode(), newProductPackage);
		else {
			/* Updating amount of existing ProductPackage */
			productPackageInCart.incrementAmount(newProductPackage.getAmount());
			groceryList.put(productPackageInCart.getSmartCode(), productPackageInCart);
		}
	}
	
	public void removeProduct(ProductPackage p) throws ProductNotInList, AmountIsBiggerThanAvailable {
		ProductPackage pp = groceryList.get(p.getSmartCode());
		
		if (pp == null)
			throw new ProductNotInList();
		
		int newAmount = pp.getAmount() - p.getAmount();
		
		if (newAmount < 0)
			throw new AmountIsBiggerThanAvailable();
		
		if (newAmount == 0)
			groceryList.remove(pp.getSmartCode());
		else {
			pp.setAmount(newAmount);
			groceryList.put(pp.getSmartCode(), pp);
		}
	}
	
	public HashMap<SmartCode, ProductPackage> getList() {
		return groceryList;
	}

	@Override
	public String getBuyer() {

		return null;
	}

	@Override
	public LocalDate getPurchaseDate() {

		return null;
	}

	@Override
	public Set<? extends ISale> getSalesList() {

		return null;
	}

	@Override
	public Set<? extends IGroceryPackage> getProductsList() {

		return null;
	}
}