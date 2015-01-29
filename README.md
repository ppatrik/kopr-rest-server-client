# Konkurentné programovanie (ÚINF/KOPR/2014)

## Ako na to? Server
Je potrebne spustit server cez `sk.upjs.kopr2014.ppatrik.rest.SimpleRestletRunner`

Pre testovanie zakladnej funkionality (manipulacia s datami) spustit `DataManagerTest`

## Ako na to? Klient
Klienta najdeme v priecinku klient, na spravne fungovanie klienta potrebujeme mat na pocitaci nainstalovany apache server
v ktorom si nastavime VirtualHost
```
# ADD PORT 84
Listen 84
<VirtualHost *:84>
   DocumentRoot "{cesta ku projektu}/client/"
   ServerName 127.0.0.1:84
   ProxyRequests Off
   ProxyPass        /dokument  http://127.0.0.1:8182/dokument
   ProxyPassReverse /dokument  http://127.0.0.1:8182/dokument
</VirtualHost>
```
Nastavenie proxy vo VirtualHoste je dolezite z dovodu ze prehliadac nedovoli javascriptu pristupovat na ine adresy akej patri.
Pre spravne fungovanie povolme v apache2 moduly proxy_module, proxy_http_module.

### Spustenie klienta
Do prehliadaca vlozte http://127.0.0.1:84/

### REST API
- `GET http://127.0.0.1:8182/dokument` - vrati zoznam vsetkych prac v systeme
- `POST http://127.0.0.1:8182/dokument` - pridanie prace do zoznamu, vkladame vo formate json s udajmi '{nazov:"", autor:"", jazyk:"", text:"", kluc:"", data:""}'
- `GET http://127.0.0.1:8182/dokument/{uuid}` - vrati metadata pre pracu so zadanym uuid
- `GET http://127.0.0.1:8182/dokument/{uuid}/data` - vrati samotne data prace
- `GET http://127.0.0.1:8182/dokument/{uuid}/{prah}` - vrati zoznam prac ktore su podobne s aktualnou ak podobnost je vyzsia ako zadany prah

#### Responses
Kazda odpoved je vo formate json `{status: 200, text: "", data: ""}`
- *status* - 200 ak je vsetko v poriadku, v pripade chyby 404
- *text* - obsahuje informacie o chybe
- *data* - obsahuje data ktore sme od servera dopytovali

(c) 2015 Patrik Pekarčík

## Zadanie
