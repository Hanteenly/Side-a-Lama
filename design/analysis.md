# Základná logika hry Slide-a-Lama

## Generovanie herného poľa
Na začiatku hry sa vytvorí tabuľka (pole) s číslami. 
Toto pole obsahuje očíslované dlaždice a jedno prázdne miesto, ktoré v kóde voláme EMPTY.

Aby hra nebola od začiatku vyriešená, všetky dlaždice sa náhodne premiešajú. Program je nastavený tak, aby sa po premiešaní dala hra vždy poskladať do cieľa.

## Ťah hráča
Hráč môže pohnúť iba tou dlaždicou, ktorá je hneď vedľa prázdneho miesta (hore, dole, vľavo alebo vpravo).

Keď hráč klikne na dlaždicu alebo stlačí klávesu:
- Vybrané číslo sa presunie na voľné miesto.
- Tam, kde predtým bolo číslo, vznikne nové prázdne miesto.
- Program si započíta, že hráč urobil jeden ťah.

## Smer pohybu
Dlaždice sa môžu hýbať v štyroch smeroch: hore, dole, vľavo a vpravo. Ak chce hráč pohnúť dlaždicou smerom, kde nie je prázdne miesto alebo kde je už okraj tabuľky, nič sa nestane.

## Overovanie stavu hry
Po každom jednom pohybe program skontroluje, či už hráč nevyhral.

Hra končí výhrou, ak:
- Všetky čísla idú po sebe (1, 2, 3...).
- Prázdne políčko zostalo úplne posledné v pravom dolnom rohu.

Ak čísla ešte nie sú správne uložené, hráč pokračuje v hraní. Po výhre sa uloží čas a počet ťahov do tabuľky výsledkov.