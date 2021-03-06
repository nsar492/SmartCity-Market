package ml.deducer.deductionrules;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import api.preferences.SalesPreferences;
import ml.common.basiccalsses.CombinedStorePackage;
import ml.common.property.AProperty;
import ml.common.property.basicproperties.storestatistics.LastPopularProductProperty;
import ml.common.property.basicproperties.storestatistics.MostPopularProductOfCustomerProperty;
import ml.common.property.saleproperty.ProductSaleByMulFactorProperty;

/**
 * This class represents the rule: if product is not popular in store, and its popular for the customer
 * then suggest sale
 *  
 * @author noam
 *
 */
public class Popularity_Rule extends ADeductionRule {

	@Override
	public Set<? extends AProperty> deduceProperties(SalesPreferences preferences, Set<AProperty> ps) {
		
		Set<AProperty> result = new HashSet<>();
		Set<MostPopularProductOfCustomerProperty> customer = ps.stream()
				.filter(p -> p instanceof MostPopularProductOfCustomerProperty)
				.map(p -> (MostPopularProductOfCustomerProperty)p)
				.collect(Collectors.toSet());
		
		Set<LastPopularProductProperty> resultAll = ps.stream()
				.filter(p -> p instanceof LastPopularProductProperty)
				.map(p -> (LastPopularProductProperty)p)
				.collect(Collectors.toSet());
		
		for (MostPopularProductOfCustomerProperty propery : customer) 
			if (resultAll.contains(new LastPopularProductProperty(propery.getProduct(), 0)))
				result.add(new ProductSaleByMulFactorProperty(new CombinedStorePackage(propery.getProduct(), 
						LocalDate.now(), 1), 1, 0.5, preferences.getMaxDiscount(), this));
	
		return result;
	}

	@Override
	public boolean canDeduceProperty(AProperty p) {
		return p instanceof ProductSaleByMulFactorProperty;
	}

	@Override
	public Set<AProperty> whatNeedToDeduceProperty(AProperty p) {
		if (!canDeduceProperty(p))
			return null;
		
		ProductSaleByMulFactorProperty actualProperty = (ProductSaleByMulFactorProperty) p;
		
		Set<AProperty> result = new HashSet<>();
		
		result.add(new MostPopularProductOfCustomerProperty(actualProperty.getPackageSale().getProduct(), 0));
		result.add(new LastPopularProductProperty(actualProperty.getPackageSale().getProduct(), 0));
		
		return result;
	}

}
