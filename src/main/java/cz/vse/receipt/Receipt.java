package cz.vse.receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Seznam položek nákupu a výpočet součtů. */
public class Receipt {

    private final List<Item> items = new ArrayList<>();

    public void add(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Položka nesmí být null.");
        }
        items.add(item);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    /** Celková cena včetně DPH. */
    public BigDecimal total() {
        return items.stream()
                .map(Item::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /** DPH obsažené v celkové ceně (ceny jsou včetně DPH). */
    public BigDecimal vat(BigDecimal ratePercent) {
        BigDecimal total = total();
        BigDecimal divisor = BigDecimal.ONE.add(ratePercent.divide(BigDecimal.valueOf(100)));
        BigDecimal net = total.divide(divisor, 2, RoundingMode.HALF_UP);
        return total.subtract(net);
    }
}
