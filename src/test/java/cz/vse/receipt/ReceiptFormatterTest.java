package cz.vse.receipt;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReceiptFormatterTest {

    private final ShopInfo shop = new ShopInfo("Obchod", "Praha 3", "123", "CZ123",
            new BigDecimal("21"), "Kč", "Děkujeme!");
    private final LocalDateTime time = LocalDateTime.of(2026, 10, 22, 16, 30, 0);

    private String render(Receipt r) {
        return new ReceiptFormatter().format(shop, r, time);
    }

    @Test
    void containsShopDataTotalAndDate() {
        Receipt r = new Receipt();
        r.add(new Item("Mléko", 2, new BigDecimal("60.50")));
        String out = render(r);
        assertTrue(out.contains("Obchod"));
        assertTrue(out.contains("IČO: 123"));
        assertTrue(out.contains("Mléko"));
        assertTrue(out.contains("121,00 Kč"));
        assertTrue(out.contains("22.10.2026 16:30:00"));
        assertTrue(out.contains("Děkujeme!"));
    }

    @Test
    void showsVatAmount() {
        Receipt r = new Receipt();
        r.add(new Item("Zboží", 1, new BigDecimal("121.00")));
        assertTrue(render(r).contains("21,00 Kč"));
    }

    @Test
    void noLineIsWiderThanReceipt() {
        Receipt r = new Receipt();
        r.add(new Item("Velmi dlouhý název produktu, který se na účtenku rozhodně nevejde", 12,
                new BigDecimal("1234.50")));
        for (String line : render(r).split(System.lineSeparator())) {
            assertTrue(line.length() <= ReceiptFormatter.WIDTH, "Příliš dlouhý řádek: " + line);
        }
    }

    @Test
    void twoColumnsAlignsRight() {
        String line = ReceiptFormatter.twoColumns("CELKEM", "10,00 Kč");
        assertEquals(ReceiptFormatter.WIDTH, line.length());
        assertTrue(line.startsWith("CELKEM"));
        assertTrue(line.endsWith("10,00 Kč"));
    }

    @Test
    void truncateShortensLongText() {
        assertEquals("abcd…", ReceiptFormatter.truncate("abcdefgh", 5));
        assertEquals("abc", ReceiptFormatter.truncate("abc", 5));
    }

    @Test
    void showsNumberOfPieces() {
        Receipt r = new Receipt();
        r.add(new Item("A", 2, new BigDecimal("10")));
        r.add(new Item("B", 3, new BigDecimal("5")));
        assertTrue(render(r).contains("Počet kusů"));
    }
}
