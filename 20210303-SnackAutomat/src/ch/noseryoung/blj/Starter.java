package ch.noseryoung.blj;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Starter {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        printTitle();
        VendingMachine vendingMachine = new VendingMachine(10, 5);
        while (true) {
            try {
                vendingMachine.run();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void printTitle() {
        System.err.println("""
                
                ██████╗  █████╗   ██╗       █████╗  ███████╗ █████╗\s
                ██╔══██╗ ██╔══██╗ ██║      ██╔══██╗ ██╔════╝ ██╔══██╗
                ██║  ██║ ███████║ ██║      ███████║ ███████╗ ███████║
                ██║  ██║ ██╔══██║ ██║      ██╔══██║ ╚════██║ ██╔══██║
                ██████╔╝ ██║  ██║ ███████╗ ██║  ██║ ███████║ ██║  ██║
                ╚═════╝  ╚═╝  ╚═╝ ╚══════╝ ╚═╝  ╚═╝ ╚══════╝ ╚═╝  ╚═╝
                """);
    }
}