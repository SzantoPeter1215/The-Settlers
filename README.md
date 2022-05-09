# The Settlers

![attackPhase](/uploads/42a5ec00150e3c95b22d230be82d2c8b/attackPhase.PNG)
### Letrejon a matrix alapu palya, a piros reszben kigeneralodik a ket var es a barnaban a termeszeti akadaly.
![base1](/uploads/cddde277fdb2fe9ba5913e0de3e2f29c/base1.PNG)
### Az elso jatekos elkezdi lerakni a tornyait(kek mezobe rakhat). Az egysegeit, az egysegek a mostani peldaban csak a var elotti mezobol indulhatnak, de a megvalositasban a var koruli resz lesz a megengedett
![xinfo](/uploads/bb5c4b43dd6d418d16bdad34d49a7d37/xinfo.PNG)
### Az elso jatekos befejezte(ranyomott az end turn gombra), szoval most a 2. jatekos jon. Ugyanugy a piros mezobe rakhat es az egysegek a var elott lesznek, egymason. Alul lehet latni az egysegek valodi szamat.
![towerupdate](/uploads/848c2f6ec188959ec0fd36d503ac1a1f/towerupdate.PNG)
### Mindket jatekos befejezte, szoval most a tamado kor jon, par lepes erejeig. Minden egyseg kiszamolja, mi a legrovidebb ut az ellenseg varahoz es azon lep egy megadott szamot. A tuskes egysegek lassabban  mennek, de tobb eleterejuk van, a sima egysegek gyorsak, de konnyen pusztithatok
![error1](/uploads/15bbec291103c405fbaa266d7975ac8e/error1.PNG)
### Megtortentek a lepesek, szoval jon a kovetkezo epitkezo kor. A penzhez hozzaadodik, az eletbe levo egysegek szama es kilott egysegek szama (ebben a korben meg nem oltek a tornyok). Ugyanugy varakat epithetunk a kek mezobe (ahol az ellenseg egysege van, nem lehet) .
![error2](/uploads/b16185776f0d37f5ab2f62be6d0aa9d2/error2.PNG)
### Ugyanez a piros oldalon, “az ide se lehet” felirat ervenytelen, ha vartol barhonnan indithatok egysegek, mivel akkor nem zarjuk el az egysegek jarasvonalat, max. onnantol a vartol jobbra nem lehet egyseget rakni, mivel beszorul. 
![builder1](/uploads/aa330396159a5602ced2fa50e9f6daa6/builder1.PNG)
### Az uj egysegek elindulnak a vartol es az uj tornyok is mukodesbe lepnek. Ujra kiszamitja az osszes egyseg a legrovidebb utvonalat.
![builder2](/uploads/22e563b17a62560e249dc0502fccaba1/builder2.PNG)
### Az egysegek a tornyokhoz ernek, ahol sebzest kapnak toluk(bizonyos tavolsagtol). A tornyok kolunbozoen sebeznek(tavolsag es gyorsasag). Ha egy egyseg eljutt egy var elotti mezohoz, elpusztul es a var ereje csokken. A kor vegen az osszes elpusztitott egysegert is fogunk mar penzt kapna, nemcsak a meglevo egysegeinkert(ebben az esetben a piros jatekos). 
## Diagram
![uml](/uploads/29a71f0fac961fdf29d51c9a41e96360/uml.png)

## Use case 
![useCase_done](/uploads/7a6ad3d171eda6ad2459d0ca47a1bfa1/useCase_done.PNG)

## User stories

| AS A      |       | Player                                                           |
|-----------|-------|------------------------------------------------------------------|
| I WANT TO |       | Build a tower                                                    |
| 1         | GIVEN | That I clicked on a valid field                                  |
|           | WHEN  | I have sufficient money                                          |
|           | THEN  | The tower gets built                                             |
| 2         | GIVEN | That I clicked on a valid field                                  |
|           | WHEN  | I have don't have sufficient money                               |
|           | THEN  | The tower doesn't get built                                      |
| 3         | GIVEN | That I clicked on a field                                        |
|           | WHEN  | I wouldn't block all available routes to my castle               |
|           | THEN  | The tower gets built                                             |
| 4         | GIVEN | That I clicked on a field                                        |
|           | WHEN  | I would block all available routes to my castle                  |
|           | THEN  | The tower doesn't get built                                      |
| AS A      |       | Player                                                           |
| I WANT TO |       | Train a unit                                                     |
| 1         | GIVEN | That I clicked on the unit's icon                                |
|           | WHEN  | I have sufficient money                                          |
|           | THEN  | The unit will start moving in the attack phase                   |
| 2         | GIVEN | That I clicked on the unit's icon                                |
|           | WHEN  | I don't have sufficient money                                    |
|           | THEN  | Nothing happens                                                  |
| AS A      |       | Player                                                           |
| I WANT TO |       | Begin the attack phase                                           |
| 1         | GIVEN | That I clicked on the end turn button                            |
|           | WHEN  | The other player has already ended their turn                    |
|           | THEN  | The attack phase starts                                          |
| 2         | GIVEN | That I clicked on the end turn button                            |
|           | WHEN  | The other player has not yet ended their turn                    |
|           | THEN  | The other player's build phase begins                            |
| AS A      |       | Player                                                           |
| I WANT TO |       | Quit the game                                                    |
| 1         | GIVEN | That I clicked on the exit button                                |
|           | WHEN  | The game is saved                                                |
|           | THEN  | The game exits                                                   |
| 2         | GIVEN | That I clicked on the exit button                                |
|           | WHEN  | The game isn't saved                                             |
|           | THEN  | The game asks if the players want to save the game               |
| AS A      |       | Player                                                           |
| I WANT TO |       | Upgrade a tower                                                  |
| 1         | GIVEN | That I clicked on a tower upgrade button                         |
|           | WHEN  | I have sufficient money                                          |
|           | THEN  | The tower gets upgraded                                          |
| 2         | GIVEN | That I clicked on a tower upgrade button                         |
|           | WHEN  | I don't have sufficient money                                    |
|           | THEN  | The tower doesn't get upgraded                                   |
| AS A      |       | Player                                                           |
| I WANT TO |       | Build a barrack                                                  |
| 1         | GIVEN | That I clicked on a valid field                                  |
|           | WHEN  | I have sufficient money                                          |
|           | THEN  | The barrack gets built                                           |
| 2         | GIVEN | That I clicked on a valid field                                  |
|           | WHEN  | I have don't have sufficient money                               |
|           | THEN  | The barrack doesn't get built                                    |
| 3         | GIVEN | That I clicked on a field                                        |
|           | WHEN  | There would be a route between the barrack and the enemy castle  |
|           | THEN  | The barrack gets built                                           |
| 4         | GIVEN | That I clicked on a field                                        |
|           | WHEN  | There would be no route between the barrack and the enemy castle |
|           | THEN  | The barrack doesn't get built                                    |
| AS A      |       | Player                                                           |
| I WANT TO |       | Save the game                                                    |
| 1         | GIVEN | That I clicked on the save game button                           |
|           | WHEN  |                                                                  |
|           | THEN  | The game gets saved                                              |
