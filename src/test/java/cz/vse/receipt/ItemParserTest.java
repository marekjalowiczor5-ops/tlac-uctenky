package cz.vse.receipt;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ItemParserTest {

    @Test
    void parsesValidLine() {
        Item item = ItemParser.parse("Rohlík;3;2,50");
        assertEquals("Rohlík", item.name());
        assertEquals(3, item.quantity());
        assertEquals(new BigDecimal("2.50"), item.unitPrice());
        assertEquals(new BigDecimal("7.50"), item.total());
    }

    @Test
    void acceptsDotAsDecimalSeparator() {
        assertEquals(new BigDecimal("12.90"), ItemParser.parse("Jogurt;1;12.9").unitPrice());
    }

    @Test
    void rejectsWrongNumberOfParts() {
        assertThrows(IllegalArgumentException.class, () -> ItemParser.parse("jen název"));
    }

    @Test
    void rejectsTextInsteadOfNumbers() {
        assertThrows(IllegalArgumentException.class, () -> ItemParser.parse("Mléko;dva;20"));
        assertThrows(IllegalArgumentException.class, () -> ItemParser.parse("Mléko;2;abc"));
    }

    @Test
    void rejectsNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> ItemParser.parse("Mléko;-1;20"));
    }
}
