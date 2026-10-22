package cz.vse.receipt;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Properties;
import org.junit.jupiter.api.Test;

class ShopInfoTest {

    private Properties valid() {
        Properties p = new Properties();
        p.setProperty("name", "Obchod");
        p.setProperty("address", "Praha");
        p.setProperty("ico", "123");
        p.setProperty("dic", "CZ123");
        p.setProperty("vatRate", "21");
        return p;
    }

    @Test
    void loadsValidProperties() {
        ShopInfo shop = ShopInfo.fromProperties(valid());
        assertEquals("Obchod", shop.name());
        assertEquals(new BigDecimal("21"), shop.vatRate());
        assertEquals("Kč", shop.currency());
        assertEquals("", shop.footer());
    }

    @Test
    void missingRequiredKeyFails() {
        Properties p = valid();
        p.remove("ico");
        assertThrows(IllegalArgumentException.class, () -> ShopInfo.fromProperties(p));
    }

    @Test
    void invalidVatRateFails() {
        Properties p = valid();
        p.setProperty("vatRate", "abc");
        assertThrows(IllegalArgumentException.class, () -> ShopInfo.fromProperties(p));
    }

    @Test
    void missingFileThrowsIOException() {
        assertThrows(IOException.class, () -> ShopInfo.load(Path.of("neexistuje.properties")));
    }
}
