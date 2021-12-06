import Controller.*;
import Model.*;
import View.*;
import java.io.*;
import java.util.Scanner;

public class Program implements Serializable, IList {

    public static void printCommand() {
        System.out.println("RF\n" +
                "RC\n" +
                "RI\n" +
                "RL\n" +
                "RD\n" +
                "RE\n" +
                "CC\n" +
                "CI\n" +
                "CE\n" +
                "CF\n" +
                "G\n" +
                "L");
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        FileManagement fileMng = new FileManagement();

        Scanner scanner = new Scanner(System.in);

        printCommand();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] arguments = line.split(" ");
            int wordLength = arguments.length;

            switch (arguments[0]) {
                case "RF":
                    try {
                        generator.registerWorker(arguments);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "RC":
                    try {
                        generator.registerClient(arguments);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "RI":
                    try {
                        generator.registerItem(arguments);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "RL":
                    try {
                        generator.registerLocal(arguments);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "RD":
                    try {
                        generator.registerDeposit(arguments);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "RE":
                    try {
                        generator.registerDelivery(arguments);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "CC":
                    try {
                        generator.consultClient(arguments);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "CI":
                    try {
                        generator.consultItem(arguments);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "CE":
                    try {
                        generator.consultDelivery(arguments);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "CF":
                    try {
                        generator.consultWorker(arguments);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "G":
                    fileMng.serialize(arguments[1], fileMng.getYourself());
                    break;
                case "L":
                    fileMng.deserialize(arguments[1]);
                    break;
                default://TODO:nunca chega aqui
                    System.out.println("Instrução inválido.");
                    break;
                    //throw new IllegalArgumentException("Invalido");
            }
        }
    }
}
