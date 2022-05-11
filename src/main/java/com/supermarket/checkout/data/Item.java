package com.supermarket.checkout.data;

import java.util.Map;

public class Item {
    public String name;
    public Map<Integer,Integer> unitPriceList;
    public Map<String,Integer> clubOffer;

    public Item(String name,Map<Integer,Integer> unitPriceList, Map<String,Integer> clubOffer) {
        this.name = name;
        this.unitPriceList = unitPriceList;
        this.clubOffer = clubOffer;
    }
}
