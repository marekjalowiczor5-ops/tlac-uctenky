package cz.vse.receipt;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Údaje o obchodě, které se opakují na každé účtence. */
public record ShopInfo(String name, String address, String ico, String dic,
                       BigDecimal vatRate, String currency, String footer) {

    public static ShopInfo load(Path file) throws IOException {
        if (!Files.exists(file)) {
            throw new IOException("Konfigurační soubor nenalezen: " + file.toAbsolutePath());
        }
        Properties props = new Properties();
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            props.load(reader);
        }
        return fromProperties(props);
    }

    public static ShopInfo fromProperties(Properties p) {
        BigDecimal rate;
        String rateText = required(p, "vatRate");
        try {
            rate = new BigDecimal(rateText.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("vatRate musí být číslo: " + rateText);
        }
        if (rate.signum() < 0) {
            throw new IllegalArgumentException("vatRate nesmí být záporná.");
        }
        return new ShopInfo(
                required(p, "name"),
                required(p, "address"),
                required(p, "ico"),
                required(p, "dic"),
                rate,
                p.getProperty("currency", "Kč").trim(),
                p.getProperty("footer", "").trim());
    }

    private static String required(Properties p, String key) {
        String value = p.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("V konfiguraci chybí hodnota: " + key);
        }
        return value.trim();
    }
}
