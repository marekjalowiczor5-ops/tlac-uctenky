package cz.vse.receipt;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Scanner;

/** Příkazová řádka: načte konfiguraci obchodu, položky nákupu a vytiskne účtenku. */
public class Main {

    public static final String NAME = "receipt-printer";

    public static void main(String[] args) {
        Path config = Path.of(args.length > 0 ? args[0] : "shop.properties");

        ShopInfo shop;
        try {
            shop = ShopInfo.load(config);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Chyba konfigurace: " + e.getMessage());
            System.exit(1);
            return;
        }

        System.out.println(NAME + " v" + version());
        System.out.println("Zadávejte položky ve tvaru: název;počet;cena");
        System.out.println("Prázdný řádek ukončí nákup a vytiskne účtenku.");

        Receipt receipt = new Receipt();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                break;
            }
            try {
                receipt.add(ItemParser.parse(line));
            } catch (IllegalArgumentException e) {
                System.out.println("Chyba: " + e.getMessage() + " (řádek ignorován)");
            }
        }

        if (receipt.isEmpty()) {
            System.out.println("Nákup je prázdný, účtenka se netiskne.");
            return;
        }

        System.out.println();
        System.out.print(new ReceiptFormatter().format(shop, receipt, LocalDateTime.now()));
    }

    static String version() {
        String v = Main.class.getPackage().getImplementationVersion();
        return v != null ? v : "dev";
    }
}
