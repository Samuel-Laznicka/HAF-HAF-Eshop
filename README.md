# "HAF HAF E-shop" - CZ

**Psí Eshop** pro nákup potřeb pro psy.

**Uživatel bez přihlášení:**
Uživatel je bez přihlášení schopný se podívat do nabídky obchodu.

**Uživatel s přihlášením:**
S User přihlášením - uživatelské jméno: **customer** | heslo: **customer123**
S přihlášením je uživatel schopný přidat produkty do košíku. 
V košíku jde manipulovat s počtem produktů a celkovým košíkem, který může za imaginární HAF HAF body uživatel "zakoupit".
Dále je uživatel manipulovat s profilem, upravit jej, přidat si HAF HAF body a smazat svůj profil.

**Uživatel Admin:**
S Admin přihlášením - uživatelské jméno: **admin** | heslo: **admin123**

## Použité technologie

JAVA verze: 17
MAVEN verze: 3.9.11
Spring Boot - starter verze: 3.5.6
Databáze: SQLITE verze: 3.45.0.0
Plugin: spring-boot-maven-plugin

## Instalace

1. Naklonujte uložiště/repository:
    ```
       git clone https://github.com/Samuel-Laznicka/HAF-HAF-Eshop
        cd eshop
    ```

2. Upravte src/main/resources/application.properties
    ```
    # SQLite databáze (vytvoří se automaticky)
    spring.datasource.url=jdbc:sqlite:eshop.db
    spring.datasource.driver-class-name=org.sqlite.JDBC
    
    # JPA/Hibernate
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.community.dialect.SQLiteDialect
    
    # Server
    server.port=8080
    ```

3. Spuštění aplikace
    ```
       mvn spring-boot:run
    ```

4. Přístup do aplikace
    ```
       http://localhost:8080
    ```

## Použití

Po spuštění aplikace k ní můžete přistupovat ve webovém prohlížeči na adrese `http://localhost:8080/`.

Dále se můžete přihlásit jako uživatel stránky s přístupem k košíku a profilu, který může nakupovat. Nebo si založit vlastní účet.
Přihlašovací jméno: `customer`
Přihlašovací heslo: `customer123`

Dále se můžete přihlásit jako ADMIN stránky, který může dodatečně vytvářet, mazat a upravovat produkty v prodeji.
Přihlašovací e-mail: `admin`
Přihlašovací heslo: `admin123`
