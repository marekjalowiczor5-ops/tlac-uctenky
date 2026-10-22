package cz.vse.receipt;

import java.math.BigDecimal;

/** Převádí řádek ve tvaru "název;počet;cena" na položku. */
public final class ItemParser {

    private ItemParser() {
    }

    public static Item parse(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Prázdný vstup.");
        }
        String[] parts = line.split(";");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Očekávám formát: název;počet;cena");
        }
        int quantity;
        try {
            quantity = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Počet musí být celé číslo: " + parts[1].trim());
        }
        BigDecimal price;
        try {
            price = new BigDecimal(parts[2].trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cena musí být číslo: " + parts[2].trim());
        }
        return new Item(parts[0], quantity, price);
    }
}
