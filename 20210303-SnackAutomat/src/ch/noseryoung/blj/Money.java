package ch.noseryoung.blj;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;

/****
 ---------------------------------------------------------------------
 Anwendung: Objektbasiert Programmieren NoserYoung
 Author: Davide
 Datum: 03.03.2021
 Zeit: 16:22
 Projekt: 20210303-SnackAutomat
 Programm: Java Programm
 Beschreibung:
 ----------------------------------------------------------------------
 ***/
public class Money {
    static double usersbalance;
    static Scanner scan = new Scanner(System.in);
    static DecimalFormat df = new DecimalFormat("0.00");

    public static int displaySingleProductPrice(int usernumber, ArrayList<Product> Products) {
        char cancel;
        double productprice = Products.get(usernumber).getPrice();
        System.out.println("==========================================================");
        System.out.println("Item number : " + usernumber);
        System.out.println("This item costs : " + Products.get(usernumber).getPrice() + " €");
        while (true) {
            try {
                System.out.println("Would you like to continue ? [ Y / N ] :");
                cancel = scan.next().charAt(0);
                if (cancel == 'Y' || cancel == 'y') {
                    Money.payForProduct(productprice);              //pay product section
                    return usernumber;
                } else if (cancel == 'N' || cancel == 'n') {
                    System.out.println("Canceling ... ");
                    VendingMachine.sleep(3000);
                    //canceling value
                    return -1;
                }
            } catch (Exception ignore) {
            }
            System.err.println("Invalid input");
        }
    }

    public static double addMoney() {
        double addedNewBalance;
        while (true) {
            try {
                System.out.print("How much will you be adding : ");
                addedNewBalance = scan.nextDouble();
                if (addedNewBalance <= 0) {
                    System.out.println("Invalid amount... ");
                    continue;
                } else {
                    System.out.println("You added " + addedNewBalance);
                    usersbalance = usersbalance + addedNewBalance;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number above 0");
                scan.nextLine();
            }
        }
        setBalance(usersbalance);
        VendingMachine.sleep(3000);
        return usersbalance;
    }

    public static double payForProduct(double productprice) {
        double returnMoney = 0;
        double coinsNeeded = productprice;
        while (coinsNeeded > 0) {
            if (usersbalance <= 0) {
                usersbalance = 0;
                System.out.println("Your balance is low");
                break;
            }
            coinsNeeded -= usersbalance;
            if (coinsNeeded <= 0) {
                returnMoney = Math.abs(coinsNeeded);
                returnMoney = Double.parseDouble(df.format(returnMoney));
                System.out.println("Your change: " + returnMoney + " €");
                break;
            }
        }
        usersbalance -= productprice;
        setBalance(usersbalance);
        System.out.println("Product cost: " + productprice + " €");
        return productprice;
    }

    public static void showUserbalance() {
        System.out.println("Your balance: " + usersbalance + " €");
        VendingMachine.sleep(3000);
    }

    public static void setBalance(double balance){
        usersbalance = Double.parseDouble(df.format(balance));
    }

    public Money() {
    }
}


