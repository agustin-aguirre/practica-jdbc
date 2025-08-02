package org.example.views.displays;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleDisplay implements Display {

    private static final String inputNotValidMsg = "Opción inválida.";

    private final Scanner scanner;

    public ConsoleDisplay() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void print(String msg) {
        System.out.print(msg);
    }

    @Override
    public void println(String msg) {
        System.out.println(msg);
    }

    @Override
    public String readString(String msg) {
        print(msg);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String msg) {
        while (true) {
            try {
                print(msg);
                int input = scanner.nextInt();
                flush();
                return input;
            }
            catch (InputMismatchException e) {
                println(inputNotValidMsg);
                scanner.nextLine();
            }
        }
    }


    @Override
    public void flush() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}
