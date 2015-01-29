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

Spoločnosť Killplag chce postaviť systém na detekciu plagiátov zadaní a záverečných prác. Usúdila, že na tento účel je najvhodnejšie postaviť webovú službu. Dokumentom budeme nazývať textový súbor s metadátami, zodpovedajúci záverečnej práci či zadaniu. Dokument obsahuje:

- názov
- autora
- jazyk
- text zadania či záverečnej práce
- kľúčové slová

Implementujte pre Killplag nasledovné operácie:

- pridanie zadania či záverečnej práce do korpusu dokumentov, ktoré sa budú používať v budúcich hľadaniach. Návratovou hodnotou je jednoznačný UUID identifikátor dokumentu. Vstupom je dokument. Ošetrite situácie, keď už daný dokument v korpuse existuje.
- zistenie miery podobnosti medzi vybraným dokumentom a korpusom. Návratovou hodnotou je zoznam identifikátorov dokumentov z korpusu, ktoré majú s vybraným dokumentom nenulovú zhodu. Pri volaní umožnite nastaviť percentuálny prah zhody, pre ktorý sa budú dokumenty považovať za zhodné i v prípade, že majú mieru zhody menšiu alebo rovnú než prah. Výstupom je zoznam identifikátorov dokumentov usporiadaný podľa miery zhody zostupne.
        *Príklad:* na vstupe je 71942845-c20b-402d-a28d-0a055cb5091e a prah 10%. Na výstupe je päť dokumentov so zhodami 50%, 30%, 20% a 12%. Dokument so zhodou 6% sa vo výsledku neobjaví.
- získanie metadát dokumentu podľa identifikátora.
- získanie dát dokumentu (textu) na základe identifikátora dokumentu.

Pri všetkých operáciách ošetrite situácie, keď sa dokument v korpuse nenachádza, a to rozumným spôsobom, ktorý klienta dostatočne oboznámi s chybovým stavom.

Implementáciu korpusu (sady dokumentov) zvoľte podľa vlastného uváženia. Nezabúdajte na to, že ku korpusu budú pristupovať viacerí klienti naraz.

Implementáciu algoritmu podobnosti textových dokumentov zvoľte podľa vlastného uváženia. Pokojne použite existujúce knižnice, či implementácie, ak uvediete zdroj a dodržíte licenciu.

Zvoľte si aspoň jednu z nasledovných možností (REST/SOAP/Akka).

*I. SOAP webservice*

- Vytvorte WSDL súbor pre webovú službu s vyššie uvedenými operáciami. Služba nech je realizovaná v štýle document/literal alebo RPC/encoded.
- Implementujte SOAP endpoint pre vyššie uvedené operácie. Použite ľubovoľný vhodný framework: odporúčaný je JAX-WS 2.0.
- Implementujte klienta pre uvedenú webovú službu v ľubovoľnej technológii.
- Demonštrujte funkčnosť servera i klienta -- odporúčaná forma sú unit testy.

*II. REST webservice*

- Navrhnite a implementujte REST API pre vyššie uvedené operácie na strane servera. Použite ľubovoľný vhodný framework: odporúčaný je Restlet.
- Implementujte klienta pre uvedenú webovú službu v odlišnej technológii / programovacom jazyku. Ak ste implementovali server v Jave, ste povinní implementovať klienta v odlišnom programovacom jazyku: napr. PHP / Python / Pascal a pod.
- Demonštrujte funkčnosť servera i klienta -- odporúčaná forma sú unit testy.

*III. Akka aktor*

- Navrhnite a implementujte aktorov pre vyššie uvedené operácie
- Demonštrujte funkčnosť pre aktora nasadeného na lokálny počítač, ale aj pre prípad aktora bežiaceho na vzdialenom serveri. (Konfigurácia pre vzdialený beh je popísaná v tutoriáli na http://ics.upjs.sk/~novotnyr/blog/1568/akka-a-vzdialene-volanie-aktorov-pre-otacanie-retazcov).

*Vyjasnenie*

- v prípade voľby variantu *I. SOAP* môžete i klienta i server implementovať v rovnakej technológii na rovnakej platforme (JAX-WS server + JAX-WS klient).
- v prípade voľby variantu *II. REST* musíte klientskú stranu implementovať v odlišnej technológii než server.
- v prípade voľby variantu *III. Akka* máte len jednu technológiu, s ktorou viete implementovať obe strany.