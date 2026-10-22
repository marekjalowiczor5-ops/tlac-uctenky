package cz.vse.receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Jedna položka na účtence. Cena je jednotková, včetně DPH. */
public record Item(String name, int quantity, BigDecimal unitPrice) {

    public Item {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Název položky nesmí být prázdný.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Počet kusů musí být kladný.");
        }
        if (unitPrice == null || unitPrice.signum() < 0) {
            throw new IllegalArgumentException("Cena nesmí být záporná.");
        }
        name = name.trim();
        unitPrice = unitPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal total() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
