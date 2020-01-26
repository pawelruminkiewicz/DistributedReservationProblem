# DistributedReservationProblem

## Analizowany problem
Analizowany przez nas problem dotyczy rezerwacji grup wycieczkowych u przewodników turystycznych.
Przewodnik ma określona liczbę miejsc w trakcie trwania zwiedzania danego zabytku. Przewodnicy są nierozróżnianli, wszyscy mają taką samą maksymalną liczbę miejsc. Rezerwowana liczba miejsc musi mieścić się w dostępnej liczbie miejsc u danego przewodnika, jezeli żaden przewodnik nie ma wystarczającej liczby miejsc rezerwacja jest anulowana bądź może zostać skierowana na listę rezerwową. Jeżeli system potwierdzi rezerwację (sprzeda bilety), a po synchronizacji replik wystąpi konflikt i rezerwacja zostanie jednak anulowana, rezerwujący zgłasza zażalenie. Celem przygotowywanego rozwiązania jest minimalizacja liczby zażaleń. Rezerwacje napływają w losowym odstępie czasu i mają losowe wielkości z zakresu od 2 osób do maksymalnego rozmiaru grupy oprowadzanej przez przewodnika.

## Wnioski
Testy przygotowanego rozwiązania wykazały, że zażalenia pojawiają się jeżeli do wydania decyzji zostaną wzięte dane z nieaktualnej repliki (dane nie zdążą się zreplikować po rozłączeniu i ponownym połączeniu węzłów). Aby osiągnąć możliwie jak najmniejszą liczbę zażaleń należy pozostawić czas na replikację. Dodatkowo należy zauważyć, że czas ten powinien zostać dobrany z uwzględnieniem aktualnego obciążenia systemu.
