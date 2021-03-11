package ch.noseryoung.blj;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;

/****
 ---------------------------------------------------------------------
 Anwendung: Objektbasiert Programmieren NoserYoung
 Authoren: Davide, Lazar, Sascha
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
                    if (payForProduct(productprice) == 1) {
                        return usernumber;
                    } else {
                        return -1;
                    }
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
                if (addedNewBalance < 0) {
                    System.out.println("Invalid amount... ");
                } else {
                    System.out.println("You added " + addedNewBalance + " €");
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

    public static int payForProduct(double productprice) {
        double returnMoney = 0;
        double coinsNeeded = productprice;
        while (coinsNeeded > 0) {
            if (usersbalance <= 0) {
                usersbalance = 0;
                System.out.println("Your balance is low");
                return -1;
            }
            coinsNeeded -= usersbalance;
            if (coinsNeeded <= 0) {
                returnMoney = Math.abs(coinsNeeded);
                returnMoney = Double.parseDouble(df.format(returnMoney));
                System.out.println("Your change: " + returnMoney + " €");
                usersbalance -= productprice;
                return 1;
            }
        }
        setBalance(usersbalance);
        System.out.println("Product cost: " + productprice + " €");
        return 0;
    }

    public static void showUserbalance() {
        System.out.println("Your balance: " + Double.parseDouble(df.format(usersbalance)) + " €");
        VendingMachine.sleep(3000);
    }

    public static void setBalance(double balance) {
        usersbalance = Double.parseDouble(df.format(balance));
    }

    /**
     * This is a method to help to showUserbalance,so it doesnt change the usersbalance if he has no money
     * @param money Money, that the users adds
     */
    public static void addFastMoney(double money) {
        usersbalance += money;
    }

    public Money() {
    }
}


