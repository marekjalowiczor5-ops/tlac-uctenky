# Tlač-účtenky

Jednoduchá konzolová aplikace pro vytváření účtenek. Verze **1.0.1**.

## Autoři

- Manuel Pokraj
- Jan Michel
- Marek Jalowiczor
- Maksym Kucher

## Požadavky

Java 17 nebo novější. Maven 3.8+ pro sestavení projektu.

## Sestavení a spuštění

V kořenové složce projektu spusťte:

```bash
mvn clean package
java -jar target/receipt-printer-1.0.0.jar
```

Program načítá údaje o obchodě ze souboru `shop.properties`. Položky se zadávají po jedné ve formátu `název;počet;cena`, například `Mléko;2;19,90`. Prázdný řádek ukončí zadávání a vytiskne účtenku.

## Zadávání nákupu

Každá položka na jeden řádek ve tvaru `název;počet;cena`

## Vytvoření nové verze

1. Změňte číslo verze v souboru `pom.xml` a v README.
2. Spusťte `mvn clean package` a ověřte, že testy projdou.
3. Uložte změny do Gitu a vytvořte tag nové verze, například `v1.0.1`:

```bash
git add .
git commit -m "Release 1.0.1"
git tag v1.0.1
git push
git push --tags
```

Novou verzi lze následně zveřejnit na GitHubu v sekci Releases.
