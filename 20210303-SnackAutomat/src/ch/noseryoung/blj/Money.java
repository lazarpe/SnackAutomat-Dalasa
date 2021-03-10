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
    static Scanner scan = new Scanner(System.in);

    public static int displaySingleProductPrice(int usernumber, ArrayList<Product> Products, double usersbalance) {
        char cancel;
        double productprice = Products.get(usernumber).getPrice();
        System.out.println("==========================================================");
        System.out.println("Item number : " + usernumber);
        System.out.println("This item costs : " + Products.get(usernumber).getPrice());
        while (true) {
            try {
                System.out.println("Would you like to continue ? [ Y / N ] :");
                cancel = scan.next().charAt(0);
                if (cancel == 'Y' || cancel == 'y') {
                    Money.payForProduct(productprice, usersbalance);              //pay product section
                    break;
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
        return usernumber;
    }

    public static double addMoney(double userbalance) {
        double usersbalance;
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
                    usersbalance = userbalance + addedNewBalance;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number above 0");
                scan.nextLine();
            }
        }
        VendingMachine.sleep(3000);
        return usersbalance;
    }

    public static double payForProduct(double productprice, double userbalance) {
        DecimalFormat df = new DecimalFormat("0.00");
        double returnMoney = 0;
        returnMoney = Double.parseDouble(df.format(returnMoney));
        double coinsNeeded = productprice;
        while (coinsNeeded > 0) {
            if (userbalance < 0) {
                userbalance = 0;
                System.out.println("Your balance is low");
                break;
            }
            coinsNeeded -= userbalance;
            if (coinsNeeded < 0) {
                returnMoney = Math.abs(coinsNeeded);
                System.out.println("Your change: " + returnMoney);
                break;
            }
        }
        System.out.println("Product cost: " + productprice);
        return productprice;
    }

    public static void showUserbalance(double userbalance) {
        System.out.println("Your balance: " + userbalance);
        VendingMachine.sleep(3000);
    }

    public Money() {
    }
}


