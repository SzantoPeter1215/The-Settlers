# The Settlers

![image001](/uploads/56dfbe7d86428f0a786cebf1d76b2c8f/image001.png)
### Letrejon a matrix alapu palya, a piros reszben kigeneralodik a ket var es a barnaban a termeszeti akadaly.
![image002](/uploads/ab493bc9b807873ab3be883e534c6112/image002.png)
### Az elso jatekos elkezdi lerakni a tornyait(kek mezobe rakhat). Az egysegeit, az egysegek a mostani peldaban csak a var elotti mezobol indulhatnak, de a megvalositasban a var koruli resz lesz a megengedett
![image003](/uploads/5a7718099c8903a2f993b8df81a2b484/image003.png)
### Az elso jatekos befejezte(ranyomott az end turn gombra), szoval most a 2. jatekos jon. Ugyanugy a piros mezobe rakhat es az egysegek a var elott lesznek, egymason. Alul lehet latni az egysegek valodi szamat.
![image004](/uploads/5b42d9602ed0cd2a9c3ee3f64c2f8e71/image004.png)
### Mindket jatekos befejezte, szoval most a tamado kor jon, par lepes erejeig. Minden egyseg kiszamolja, mi a legrovidebb ut az ellenseg varahoz es azon lep egy megadott szamot. A tuskes egysegek lassabban  mennek, de tobb eleterejuk van, a sima egysegek gyorsak, de konnyen pusztithatok
![image005](/uploads/3cdccf21b7a27121706b065737694e69/image005.png)
### Megtortentek a lepesek, szoval jon a kovetkezo epitkezo kor. A penzhez hozzaadodik, az eletbe levo egysegek szama es kilott egysegek szama (ebben a korben meg nem oltek a tornyok). Ugyanugy varakat epithetunk a kek mezobe (ahol az ellenseg egysege van, nem lehet) .
![image006](/uploads/0f57847920fbea9872f8f0c124e12891/image006.png)
### Ugyanez a piros oldalon, “az ide se lehet” felirat ervenytelen, ha vartol barhonnan indithatok egysegek, mivel akkor nem zarjuk el az egysegek jarasvonalat, max. onnantol a vartol jobbra nem lehet egyseget rakni, mivel beszorul. 
![image007](/uploads/26e8276adefe981f92c0ee5d902c437d/image007.png)
### Az uj egysegek elindulnak a vartol es az uj tornyok is mukodesbe lepnek. Ujra kiszamitja az osszes egyseg a legrovidebb utvonalat.
![image008](/uploads/ae93b7a97727a1eac3c89ed61e21daac/image008.png)
### Az egysegek a tornyokhoz ernek, ahol sebzest kapnak toluk(bizonyos tavolsagtol). A tornyok kolunbozoen sebeznek(tavolsag es gyorsasag). Ha egy egyseg eljutt egy var elotti mezohoz, elpusztul es a var ereje csokken. A kor vegen az osszes elpusztitott egysegert is fogunk mar penzt kapna, nemcsak a meglevo egysegeinkert(ebben az esetben a piros jatekos). 
## Diagram
![uml_done](/uploads/65052e3fa94e2ec2701ea09b8b071035/uml_done.PNG)

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

Terminal:
Before: `git pull -r`  
New branch: `git checkout -b "your_branch_name"`  
Change branch: `git checkout "your_branch_name"`  
View existing branch: `git branch`  
Delete branch: `git branch -D "your_branch_name"`  
Fetch origin: `git fetch origin`  
Merge to your branch: `git merge origin/main`
