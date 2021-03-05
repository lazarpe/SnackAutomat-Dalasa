package ch.noseryoung.blj;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

/**
 * Created by lazar on 05.03.2021.
 * Project name: 20210303-SnackAutomat
 **/
public class Person {
    ArrayList<Product> myItems;
    double wallet;

    public void setInventory(Product product) {
        product.setAmount(1);
        product.setPrice(0);
        myItems.add(product);
    }

    public void showInventory() {
        for (Product myItem : myItems) {
            System.out.println(myItem.getName());
        }
    }

    public Person(ArrayList<Product> myItems, double wallet) {
        this.myItems = myItems;
        this.wallet = wallet;
    }
}
