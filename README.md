### Lokalno pokretanje projekta

Tehnologije potrebne za pokretanje projekta:
- npm
- Docker

Upute:
- preuzetu zip datoteku raspakirati u željeni direktorij 
- pozicionirati se u SMEN/frontend i pokrenuti ```npm install```, pa zatim ```npm run dev``
- Backend dio aplikacije najbolje postaviti kroz Intellij 
    - Java 17
    - Gradle 8.10
- Pozicionirati se u KEYCLOAK_SMEN/ direktorij i izvršiti naredbu ```docker compose up --build```

Aplikacija bi nakon navedenih koraka trebala biti dostupna na http://localhost:5173/themes. 
Repozitorij aplikacije (bez postavki za Keycloak) je dostupan na poveznici - https://github.com/filipgrabovac-fer/SMEN. Keycloak je zbog osjetljivih podataka moguće pokrenuti samo iz .zip foldera.
