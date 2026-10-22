package cz.vse.receipt;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ReceiptEdgeCasesTest {

    @Test
    void manySmallItemsSumExactly() {
        Receipt r = new Receipt();
        for (int i = 0; i < 100; i++) {
            r.add(new Item("Bonbon", 1, new BigDecimal("0.10")));
        }
        assertEquals(new BigDecimal("10.00"), r.total());
    }

    @Test
    void parserTrimsWhitespace() {
        Item item = ItemParser.parse("  Káva ; 2 ; 45,5 ");
        assertEquals("Káva", item.name());
        assertEquals(2, item.quantity());
        assertEquals(new BigDecimal("45.50"), item.unitPrice());
    }

    @Test
    void zeroVatRateGivesZeroVat() {
        Receipt r = new Receipt();
        r.add(new Item("Zboží", 1, new BigDecimal("100.00")));
        assertEquals(new BigDecimal("0.00"), r.vat(BigDecimal.ZERO));
    }
}