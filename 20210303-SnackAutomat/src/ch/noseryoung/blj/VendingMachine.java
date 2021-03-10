package ch.noseryoung.blj;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/****
 ---------------------------------------------------------------------
 Anwendung: Objektbasiert Programmieren NoserYoung
 Author: Davide
 Datum: 03.03.2021
 Zeit: 15:11
 Projekt: 20210303-SnackAutomat
 Programm: Java Programm
 Beschreibung:
 ----------------------------------------------------------------------
 ***/
public class VendingMachine {

    private final int width;
    private final int height;
    private final int min = 1;
    private final int max = 10;
    private int filled = 0;
    private int number;
    private int payedproduct;  // after the payment
    private final long secretKey = 86420; // long because it's unsigned
    //private double userbalance;
    Scanner scan = new Scanner(System.in);
    Random generate = new Random();
    FileWriter myWriter;
    String fileName = "productAmount.txt";

    String[] productName = {
            "Cola", "Ice Tea", "Fanta", "Chocolate", "Chips", "Crackers", "Pudding", "Milk", "Vanillecake",
            "Red Bull", "M & M's", "Maltesers", "Water", "Monster Energy", "Snickers", "Twixx", "Mars",
            "Energy Bar", "Kägi Fret", "Donut", "Waffles", "Gummy Bears", "Coffee", "Jelly Babies", "Rivella",
            "Cola Zero", "Capri Sun", "Apple spritzer"};
    Person person = new Person();
    ArrayList<Product> products = new ArrayList<>();
    Product[][] addedProducts;

    PlaySound audioPlayer = new PlaySound();

    public void run() {
        createMenu(number);
    }

    public void createMenu(int code) {
        System.out.println("Menu:");
        while (true) {
            try {
                System.out.println("[1] Show vending machine\n" +
                        "[2] Show items from vending machine\n" +
                        "[3] Enter product code\n" +
                        "[4] Insert money\n" +
                        "[5] Show user balance\n" +
                        "[6] Show users items\n" +
                        "[7] Music settings\n" +
                        "[8] Leave vending machine");

                System.out.print("Choose: ");
                code = scan.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("\nInvalid input. Try again: ");
                scan.nextLine();
            }
        }
        int inputForSecretMethods;
        switch (code) {
            case 1:
                printVendingMachine();
                break;
            case 2:
                printProducts(true);
                break;
            case 3:
                System.out.print("Enter a code: ");
                number = enterCode();
                while (true) {
                    try {
                        if (checkSecretKey(number)) {
                            System.err.println("""
                                                                | | \s
                                     ___   ___   ___  _ __  ___ | |_\s
                                    / __| / _ \\ / __|| '__|/ _ \\| __|
                                    \\__ \\|  __/| (__ | |  |  __/| |_\s
                                    |___/ \\___| \\___||_|   \\___| \\__|""");
                            System.out.println("\n\n[1] Change item price ");
                            System.out.println("[2] Change product ");
                            System.out.println("[3] Saving products to file");
                            System.out.println("[4] Loading products from file");
                            System.out.println("[5] Refill vending machine");
                            System.out.println("[6] Leave secret menu");
                            System.out.print("Choose: ");
                            inputForSecretMethods = scan.nextInt();
                            switch (inputForSecretMethods) {
                                case 1:
                                    changeProductPrice(products);
                                    break;
                                case 2:
                                    System.out.println("Change product...");
                                    changeProduct(products);
                                    break;
                                case 3:
                                    System.out.println("Loading to file...");
                                    sleep(2000);
                                    printToFile();
                                    break;
                                case 4:
                                    System.out.println("Loading from file...");
                                    sleep(2000);
                                    loadFromFile();
                                    break;
                                case 5:
                                    refillVendingMachine();
                                    System.out.println("Vending machine is getting filled...");
                                    sleep(4000);
                                    break;
                                case 6:
                                    System.out.println("Leaving secret menu...\n");
                                    sleep(1500);
                                    break;
                            }
                            break;
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("\nInvalid input. Try again: ");
                        scan.nextLine();
                    }
                }
                if (number < width * height && number >= 0) {
                    payedproduct = Money.displaySingleProductPrice(number, products);
                    if (payedproduct == -1) {/*if payed product is -1 then do nothing*/ } else {
                        giveOutProducts(payedproduct);
                    }
                }
                break;
            case 4:
                System.out.println("You can add your money here.");
                Money.addMoney();
                break;
            case 5:
                Money.showUserbalance();
                break;
            case 6:
                person.showInventory();
                break;
            case 7:
                int musicControl = 0;
                while (true) {
                    try {
                        System.out.println("Music settings\n" +
                                "[1] Turn music on\n" +
                                "[2] Turn music off\n" +
                                "[3] Leave music settings");
                        System.out.print("Choose: ");
                        musicControl = scan.nextInt();
                        switch (musicControl) {
                            case 1:
                                audioPlayer.playMusic();
                                continue;
                            case 2:
                                audioPlayer.stopMusic();
                                continue;
                            case 3:
                                System.out.println("\nLeaving music settings...\n");
                                sleep(1500);
                                break;
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("\nInvalid input. Try again: ");
                        scan.nextLine();
                    }
                }
                break;
            case 8:
                System.out.println("\nSystem is shutting down...");
                sleep(3000);
                System.exit(1);
                break;
        }
    }

    public void fillVendingMachine() {
        addedProducts = new Product[width][height];
        int dimensions = width * height;

        for (int k = width * height; k > 0; k--) {
            Product p1 = new Product(productName[generate.nextInt(productName.length)], (min + generate.nextFloat() *
                    (max - min)), dimensions - k, generate.nextInt(10));

            DecimalFormat df = new DecimalFormat("0.00");
            double f = min + Math.random() * (max - min);
            f = Double.parseDouble(df.format(f));
            p1.setPrice(f);
            products.add(p1);
        }

        int i = 0;
        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                addedProducts[l][k] = products.get(i);
                i++;
            }
        }
        filled = 1;
    }

    public void printProducts(boolean advanced) {
        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                if (advanced) {
                    if (addedProducts[l][k].getProduct_code() < 10) {
                        System.out.print(addedProducts[l][k].getProduct_code() + "  | " + addedProducts[l][k].getName() + ": " +
                                addedProducts[l][k].getAmount());
                    } else {
                        System.out.print(addedProducts[l][k].getProduct_code() + " | " + addedProducts[l][k].getName() + ": " +
                                addedProducts[l][k].getAmount());
                    }
                } else {
                    if (addedProducts[l][k].getProduct_code() < 10) {
                        System.out.print(addedProducts[l][k].getProduct_code() + "  | " + addedProducts[l][k].getName());
                    } else {
                        System.out.print(addedProducts[l][k].getProduct_code() + " | " + addedProducts[l][k].getName());
                    }
                }
                printPrices(l, k);
            }
        }
    }

    public void refillVendingMachine() { // ----- SECRET KEY -----
        int i = 0;
        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                if (addedProducts[l][k].getAmount() < 3) {
                    i++;
                }
            }
        }

        if (i != 0) {
            for (int k = 0; k < height; k++) {
                for (int l = 0; l < width; l++) {
                    Product product = new Product(addedProducts[l][k].getName(), addedProducts[l][k].getPrice(), addedProducts[l][k].getProduct_code(), 9);
                    addedProducts[l][k] = product;
                }
            }
        }
    }

    public void printPrices(int l, int k) {
        for (int j = addedProducts[l][k].getName().length(); j < 15; j++) {
            System.out.print(" ");
        }

        System.out.print("| ");
        System.out.println(addedProducts[l][k].getPrice() + " €");
    }

    public void printVendingMachine() {
        System.out.print("╔");
        for (int m = 1; m < width; m++) {
            System.out.print("══╦");
        }
        System.out.println("══╗");

        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                if (addedProducts[l][k].getProduct_code() < 10) {
                    System.out.print("║ ");
                } else {
                    System.out.print("║");
                }

                if (addedProducts[l][k].getAmount() != 0) {
                    System.out.print(addedProducts[l][k].getProduct_code());
                } else if (addedProducts[l][k].getProduct_code() < 10) {
                    System.out.print(" ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("║");

            if (k + 1 < height) {
                System.out.print("╠");
                for (int m = 1; m < width; m++) {
                    System.out.print("══╬");
                }
                System.out.println("══╣");
            }
        }

        System.out.print("╚");
        for (int m = 1; m < width; m++) {
            System.out.print("══╩");
        }
        System.out.println("══╝");
    }

    public int enterCode() {
        int number;
        while (true) {
            try {
                number = scan.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Please try again");
                scan.nextLine();
            }
        }
        return number;
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printToFile() {
        try {
            myWriter = new FileWriter("productAmount.txt", false);

            String stringToFile;
            double doubleToFile;
            int intToFile;

            for (int k = 0; k < height; k++) {
                for (int l = 0; l < width; l++) {
                    stringToFile = addedProducts[l][k].getName();
                    myWriter.write(stringToFile + "\n");
                    doubleToFile = addedProducts[l][k].getPrice();
                    myWriter.write(doubleToFile + "\n");
                    intToFile = addedProducts[l][k].getProduct_code();
                    myWriter.write(intToFile + "\n");
                    intToFile = addedProducts[l][k].getAmount();
                    myWriter.write(intToFile + "\n");
                }
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void loadFromFile() { // ----- SECRET KEY -----

        File file = new File(fileName);

        if (!file.canRead() || !file.isFile()) {
            System.exit(0);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileName));
            String line = null;
            for (int k = 0; k < height; k++) {
                for (int l = 0; l < width; l++) {
                    int i = 0;
                    while (i < 4) {
                        line = in.readLine();
                        switch (i) {
                            case 0 -> addedProducts[l][k].setName(line);
                            case 1 -> addedProducts[l][k].setPrice(Double.parseDouble(line));
                            case 2 -> addedProducts[l][k].setProduct_code(Integer.parseInt(line));
                            case 3 -> addedProducts[l][k].setAmount(Integer.parseInt(line));
                        }
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        filled = 1;
    }

    public boolean checkSecretKey(int num) {
        return num == this.secretKey;
    }

    public void giveOutProducts(int number) {
        int stuck = 0;
        if (products.get(number).getAmount() > 0) {
            if (generate.nextInt(10) != 0) {
                person.setInventory(products.get(number));
                products.get(number).setAmount(products.get(number).getAmount() - 1);
                System.out.println("Your Product is on the way!");
            } else {
                System.out.println("\nNoooo, your product got stuck, bad luck!");
                stuck = 1;
            }

            System.out.print("\n╔");
            for (int j = 0; j < 17; j++) {
                System.out.print("═");
            }
            System.out.println("╗");
            System.out.print("║");
            if (stuck == 0) {
                System.out.print(" " + products.get(number).getName());
            } else {
                for (int n = 0; n <= products.get(number).getName().length(); n++) {
                    System.out.print(" ");
                }
            }
            for (int i = products.get(number).getName().length(); i < 16; i++) {
                System.out.print(" ");
            }
            System.out.println("║");
            System.out.print("╚");
            for (int k = 0; k < 17; k++) {
                System.out.print("═");
            }
            System.out.println("╝");

        } else {
            System.out.println("This products is out of stock, return later for more!");
            Money.addFastMoney(products.get(number).getPrice());
        }
        sleep(3000);
    }

    public void changeProductPrice(ArrayList<Product> Products) { // ----- SECRET KEY -----
        char answerToChangePrice = ' ';
        double newPrice = 0;
        System.out.println("You can change the price now...");
        printProducts(true);
        int number = 0;
        while (true) {
            try {
                System.out.print("\nWhat's the product number of the price you want to change: ");
                number = scan.nextInt();
                System.out.println("You entered this number: " + number + "\nProduct name: " + Products.get(number).getName());
                break;
            } catch (Exception e) {
                System.out.print("Something went wrong with the product number. Try again.\n");
                scan.nextLine();
            }
        }
        while (true) {
            try {
                System.out.println("Are you sure you want to change the price of this product? (Y / N): ");
                answerToChangePrice = scan.next().charAt(0);
                if (answerToChangePrice == 'y' || answerToChangePrice == 'Y' || answerToChangePrice == 'n' ||
                        answerToChangePrice == 'N') {
                    break;
                } else {
                    System.out.println("Please enter a valid input!");
                }
            } catch (Exception e) {
                System.out.print("Something went wrong with your answer. Try again.\n");
                scan.nextLine();
            }
        }
        int i = 0;
        while (true) {
            try {
                if (answerToChangePrice == 'Y' || answerToChangePrice == 'y') {
                    System.out.print("Enter the new price: ");
                    newPrice = scan.nextDouble();
                    Products.get(number).setPrice(newPrice);
                    System.out.println("You successfully changed the price to: " + Products.get(number).getPrice());
                    sleep(4000);
                    break;
                } else if (answerToChangePrice == 'N' || answerToChangePrice == 'n') {
                    System.out.println("Ok leaving secret methods...");
                    sleep(4000);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Something went wrong with your answer. Try again.\n");
                scan.nextLine();
            }
        }

        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                addedProducts[l][k] = products.get(i);
                i++;
            }
        }
    }

    public void changeProduct(ArrayList<Product> Products) { // ----- SECRET KEY -----
        char answerToChangeProduct = ' ';
        int newProductNumber = 0;
        System.out.println("You can change the product now...");
        printProducts(true);
        int number = 0;
        while (true) {
            try {
                System.out.print("\nWhat's the product number of the product you want to change: ");
                number = scan.nextInt();
                System.out.println("You entered this number: " + number + "\nProduct name: " + Products.get(number).getName());
                break;
            } catch (Exception e) {
                System.out.print("Something went wrong with the product number. Try again.\n");
                scan.nextLine();
            }
        }
        while (true) {
            try {
                System.out.println("Are you sure you want to change the product? (Y / N): ");
                answerToChangeProduct = scan.next().charAt(0);
                if (answerToChangeProduct == 'y' || answerToChangeProduct == 'Y' || answerToChangeProduct == 'n' ||
                        answerToChangeProduct == 'N') {
                    break;
                } else {
                    System.out.println("Please enter a valid input!");
                }
            } catch (Exception e) {
                System.out.print("Something went wrong with your answer. Try again.\n");
                scan.nextLine();
            }
        }
        int i = 0;
        while (true) {
            try {
                if (answerToChangeProduct == 'Y' || answerToChangeProduct == 'y') {
                    printProducts(false);
                    System.out.print("Enter the number of the new product: ");
                    newProductNumber = scan.nextInt();
                    int tempProductNumber = Products.get(number).getProduct_code();
                    Products.set(number, Products.get(newProductNumber));
                    Products.get(number).setProduct_code(tempProductNumber);
                    System.out.println("You successfully changed the product to: " + Products.get(newProductNumber).getName());
                    sleep(4000);
                    break;
                } else if (answerToChangeProduct == 'N' || answerToChangeProduct == 'n') {
                    System.out.println("Ok leaving secret methods...");
                    sleep(4000);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Something went wrong with your answer. Try again.\n");
                scan.nextLine();
            }
        }
        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                addedProducts[l][k] = products.get(i);
                i++;
            }
        }
    }

    public VendingMachine(int width, int height) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.width = width;
        this.height = height;
    }
}