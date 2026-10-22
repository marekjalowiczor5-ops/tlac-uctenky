package cz.vse.receipt;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Vytvoří text účtenky pevné šířky. */
public class ReceiptFormatter {

    public static final int WIDTH = 40;
    private static final DateTimeFormatter DATE_TIME =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public String format(ShopInfo shop, Receipt receipt, LocalDateTime time) {
        StringBuilder sb = new StringBuilder();
        String separator = "-".repeat(WIDTH);
        String doubleSeparator = "=".repeat(WIDTH);

        appendLine(sb, center(shop.name()));
        appendLine(sb, center(shop.address()));
        appendLine(sb, center("IČO: " + shop.ico()));
        appendLine(sb, center("DIČ: " + shop.dic()));
        appendLine(sb, separator);

        for (Item item : receipt.getItems()) {
            appendLine(sb, truncate(item.name(), WIDTH));
            String detail = "  " + item.quantity() + " x " + money(item.unitPrice(), shop);
            appendLine(sb, twoColumns(detail, money(item.total(), shop)));
        }

        appendLine(sb, doubleSeparator);
        appendLine(sb, twoColumns("CELKEM", money(receipt.total(), shop)));
        appendLine(sb, twoColumns("z toho DPH " + stripZeros(shop.vatRate()) + " %",
                money(receipt.vat(shop.vatRate()), shop)));
        appendLine(sb, separator);
        appendLine(sb, center(time.format(DATE_TIME)));
        if (!shop.footer().isEmpty()) {
            appendLine(sb, center(shop.footer()));
        }
        return sb.toString();
    }

    static String money(BigDecimal amount, ShopInfo shop) {
        return amount.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString().replace('.', ',')
                + " " + shop.currency();
    }

    static String twoColumns(String left, String right) {
        int space = WIDTH - left.length() - right.length();
        if (space < 1) {
            left = truncate(left, Math.max(0, WIDTH - right.length() - 1));
            space = Math.max(1, WIDTH - left.length() - right.length());
        }
        return left + " ".repeat(space) + right;
    }

    static String center(String text) {
        String t = truncate(text, WIDTH);
        int pad = (WIDTH - t.length()) / 2;
        return " ".repeat(pad) + t;
    }

    static String truncate(String text, int max) {
        if (text.length() <= max) {
            return text;
        }
        return text.substring(0, Math.max(0, max - 1)) + "…";
    }

    private static String stripZeros(BigDecimal value) {
        return value.stripTrailingZeros().toPlainString();
    }

    private static void appendLine(StringBuilder sb, String line) {
        sb.append(line).append(System.lineSeparator());
    }
}
