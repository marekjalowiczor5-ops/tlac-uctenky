package cz.vse.receipt;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ReceiptTest {

    @Test
    void emptyReceiptHasZeroTotal() {
        Receipt r = new Receipt();
        assertTrue(r.isEmpty());
        assertEquals(new BigDecimal("0.00"), r.total());
    }

    @Test
    void totalSumsItemsWithDecimals() {
        Receipt r = new Receipt();
        r.add(new Item("Mléko", 2, new BigDecimal("19.90")));
        r.add(new Item("Chléb", 1, new BigDecimal("32.50")));
        assertEquals(new BigDecimal("72.30"), r.total());
    }

    @Test
    void vatIsContainedInTotal() {
        Receipt r = new Receipt();
        r.add(new Item("Zboží", 1, new BigDecimal("121.00")));
        assertEquals(new BigDecimal("21.00"), r.vat(new BigDecimal("21")));
    }

    @Test
    void itemsListIsReadOnly() {
        Receipt r = new Receipt();
        assertThrows(UnsupportedOperationException.class,
                () -> r.getItems().add(new Item("X", 1, BigDecimal.ONE)));
    }

    @Test
    void rejectsInvalidItems() {
        assertThrows(IllegalArgumentException.class, () -> new Item("", 1, BigDecimal.ONE));
        assertThrows(IllegalArgumentException.class, () -> new Item("X", 0, BigDecimal.ONE));
        assertThrows(IllegalArgumentException.class, () -> new Item("X", 1, new BigDecimal("-1")));
    }
}
