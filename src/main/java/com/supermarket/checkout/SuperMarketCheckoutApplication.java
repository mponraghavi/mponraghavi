package com.supermarket.checkout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.supermarket.checkout.data.Item;
import com.supermarket.checkout.data.SuperMarketData;

@SpringBootApplication
public class SuperMarketCheckoutApplication {

	public static void main(String[] args) {
	    Map<String, Integer> checkoutItems = new HashMap<>();
        checkoutItems.put("D", 1);
        checkoutItems.put("A", 1);
        System.out.println(getTotalPrice(checkoutItems));
		SpringApplication.run(SuperMarketCheckoutApplication.class, args);
	}
	

    public static int getTotalPrice(Map<String, Integer> checkoutItems) {
        SuperMarketData superMarketData = getSuperMarketStubData();
        int total = 0;
        for (Map.Entry<String, Integer> checkoutItem : checkoutItems.entrySet()) {
            for (Item item : superMarketData.itemList) {
                if (item.name.equals(checkoutItem.getKey())) {
                    int count = checkoutItem.getValue();
                    if (item.clubOffer != null && !item.clubOffer.isEmpty()) {
                        for (Map.Entry<String, Integer> clubOffer : item.clubOffer.entrySet()) {
                            if (checkoutItems.containsKey(clubOffer.getKey())) {
                                int clubbedItemCheckoutCount = checkoutItems.get(clubOffer.getKey());
                                if (count >= clubbedItemCheckoutCount) {
                                    total += clubbedItemCheckoutCount * clubOffer.getValue();
                                    count -= clubbedItemCheckoutCount;
                                } else {
                                    total += count * clubOffer.getValue();
                                    break;
                                }
                            }
                        }
                    }
                    if (count <= 0)
                        return total;
                    List<Integer> sortedUnits = new ArrayList<>(item.unitPriceList.keySet());
                    Collections.sort(sortedUnits, Collections.reverseOrder());
                    for (Integer unit : sortedUnits) {
                        if (count >= unit) {
                            int div = count / unit;
                            int mod = count % unit;
                            total += div * item.unitPriceList.get(unit);
                            if (mod == 0) break;
                            else count = mod;
                        }
                    }
                }
            }
        }
        return total;
    }

    public static SuperMarketData getSuperMarketStubData() {
        SuperMarketData superMarket = new SuperMarketData();
        Map<Integer, Integer> unitAPriceList = new HashMap<>();
        unitAPriceList.put(1, 50);
        unitAPriceList.put(3, 130);
        superMarket.itemList.add(new Item("A", unitAPriceList, null));

        Map<Integer, Integer> unitBPriceList = new HashMap<>();
        unitBPriceList.put(1, 30);
        unitBPriceList.put(2, 45);
        superMarket.itemList.add(new Item("B", unitBPriceList, null));

        Map<Integer, Integer> unitCPriceList = new HashMap<>();
        unitCPriceList.put(1, 20);
        unitCPriceList.put(2, 38);
        unitCPriceList.put(3, 50);
        superMarket.itemList.add(new Item("C", unitCPriceList, null));

        Map<Integer, Integer> unitDPriceList = new HashMap<>();
        unitDPriceList.put(1, 15);
        Map<String, Integer> clubOfferList = new HashMap<>();
        clubOfferList.put("A", 5);
        superMarket.itemList.add(new Item("D", unitDPriceList, clubOfferList));

        Map<Integer, Integer> unitEPriceList = new HashMap<>();
        unitEPriceList.put(1, 5);
        superMarket.itemList.add(new Item("E", unitEPriceList, null));

        return superMarket;
    }


	

}
