Questions:
create file task:
create login first ?
=> no
create file directory page ?
=> no

    relationship:
        Grundstuecksbezeichnung <-> Vertragsinformation
            => 1-1

    front-end design:
        above creation fields: supposed to be short tables ?
            => yes

        clarify Grundstuecksbezeichnung creation mask
        clarify Vertragsinformation creation mask
            => remove buttons, leave the creation masks on screen

        when and where to create anmerkungAufenthaltsort ?
            => when borrowing a file there should be a new window(not present in wireframes)
            => to input the following data: aufenthaltsort, anmerkungAufenthaltsort
            => default aufenthaltsort: Archiv

        whom to send the notification to ?
            => no1 to send to, it's just raised

        function of freitext search ?
            => go through each field of the akte and look for matches

        create notification missing ?
            => yes, create Aktionbutton("Meldung verfassen") in file information page

        Bemerkung(file creation) = Betreff(file directory)

        Bereich refers to Aktenfields: Stadtbezirk, Kennziffer, ...

	letzteHeftnummer -> letzte Aenderung ?

    data structure:
        Kennziffer & Stadtbezirk expected values ? + static / dynamic ?
            => simple as possible & static

	

(have to be updated)
Time Estimations:
create file:
1h x 3
4h x 3
4h x 2
1h x 2
1h x 2
1h
2h
2h

---

16h / 32h

edit existing file:
2h
2h
1h
4h x 2
4h x 2

---

13h / 21h
