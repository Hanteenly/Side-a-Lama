## Generovanie herného poľa
Pri spustení hry sa vytvorí dvojrozmerné pole dlaždíc. 
Pole obsahuje číselné dlaždice a jednu prázdnu dlaždicu (EMPTY).

Dlaždice sa na začiatku hry premiešajú do náhodného poradia.

## Ťah hráča
Hráč môže presunúť dlaždicu iba v prípade, že sa nachádza vedľa prázdnej dlaždice.

Po vykonaní ťahu:
- dlaždica sa presunie na miesto prázdnej dlaždice
- pôvodná pozícia sa stane prázdnou

## Overovanie stavu hry
Po každom ťahu sa kontroluje stav hry.

Hra je vyhratá, ak sú všetky dlaždice usporiadané v správnom poradí.

Ak správne usporiadanie ešte nebolo dosiahnuté, hra pokračuje ďalším ťahom hráča.

## Diagram stavov dlaždice

![Tile State Diagram](tile_state_diagram.png)

## Sekvenčný diagram ťahu hráča
