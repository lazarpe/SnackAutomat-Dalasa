package ch.noseryoung.blj;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Starter {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        printTitle();
        VendingMachine vendingMachine = new VendingMachine(10, 5);
        vendingMachine.fillVendingMachine();
        vendingMachine.printToFile();
        while (true) {
            vendingMachine.run();
        }
    }

    public static void printTitle() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_CYAN = "\u001B[36m";
        System.out.println(ANSI_CYAN + """
                
                ██████╗  █████╗   ██╗       █████╗  ███████╗ █████╗\s
                ██╔══██╗ ██╔══██╗ ██║      ██╔══██╗ ██╔════╝ ██╔══██╗
                ██║  ██║ ███████║ ██║      ███████║ ███████╗ ███████║
                ██║  ██║ ██╔══██║ ██║      ██╔══██║ ╚════██║ ██╔══██║
                ██████╔╝ ██║  ██║ ███████╗ ██║  ██║ ███████║ ██║  ██║
                ╚═════╝  ╚═╝  ╚═╝ ╚══════╝ ╚═╝  ╚═╝ ╚══════╝ ╚═╝  ╚═╝
                """ + ANSI_RESET);
    }
}