package ch.noseryoung.blj;

import java.util.ArrayList;

/**
 * Created by lazar on 05.03.2021.
 * Project name: 20210303-SnackAutomat
 **/
public class Person {
    ArrayList<Product> myItems = new ArrayList<>();

    public void setInventory(Product product) {
        product.setAmount(1);
        product.setPrice(0);
        myItems.add(product);
    }

    public void showInventory() {
        if (myItems.size() <= 0) {
            System.out.println("No items");
        } else {
            System.out.println("Your Items:");
            for (Product myItem : myItems) {
                System.out.println(" - " + myItem.getName());
            }
            VendingMachine.sleep(1800);
        }
    }
}