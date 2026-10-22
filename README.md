# receipt-printer

Jednoduchý konzolový nástroj pro tisk účtenky (jako ze samoobsluhy).
Verze: **1.0.0** (sémantické verzování `MAJOR.MINOR.PATCH`).

## Autoři

Manuel Pokraj
Honza Michel
Marek Jalowiczor
Maksym
## Požadavky

- Java 17 nebo novější
- Maven 3.8+ (jen pro sestavení)

## Sestavení

```bash
mvn clean package
```

Vznikne `target/receipt-printer-1.0.0.jar` (testy se spustí automaticky).

## Spuštění (bez IDE)

Z kořene projektu (kde leží `shop.properties`):

```bash
java -jar target/receipt-printer-1.0.0.jar
```

Jiný konfigurační soubor lze předat jako argument:

```bash
java -jar target/receipt-printer-1.0.0.jar /cesta/k/obchod.properties
```

Na Windows, pokud se špatně zobrazují české znaky, spusťte nejdřív `chcp 65001`.

## Konfigurace obchodu (`shop.properties`)

Soubor v UTF-8 s údaji, které se opakují na každé účtence:
`name`, `address`, `ico`, `dic`, `vatRate` (v %, ceny jsou včetně DPH), `currency`, `footer`.

## Zadávání nákupu

Každá položka na jeden řádek ve tvaru `název;počet;cena`, například:

```
Mléko;2;19,90
Chléb;1;32.50
```

Prázdný řádek ukončí nákup a vytiskne účtenku. Chybný řádek se ohlásí a ignoruje.

## Testy

```bash
mvn test
```

## Jak vydat novou verzi

1. V `pom.xml` změňte `<version>` (např. `1.0.0` na `1.0.1`) a případně verzi v README.
2. Spusťte `mvn clean package` a ověřte, že testy projdou.
3. Commit: `git commit -am "Release 1.0.1"`
4. Tag: `git tag v1.0.1 && git push && git push --tags`
5. (Volitelně) na GitHubu vytvořte Release z tagu a přiložte JAR z `target/`.
