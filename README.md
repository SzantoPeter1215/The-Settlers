# The Settlers

![image001](/uploads/56dfbe7d86428f0a786cebf1d76b2c8f/image001.png)
### Létrejön a matrix alapú pálya, a piros részben kigenerálódik a két vár és a barnában a természeti akadály.
![image002](/uploads/ab493bc9b807873ab3be883e534c6112/image002.png)
### Az első játékos elkezdi lerakni a tornyait(kék mezőbe rakhat). Az egységeit, az egységek a mostani példában csak a vár előtti mezőből indulhatnak, de a megvalósításban a vár körüli rész lesz a megengedett
![image003](/uploads/5a7718099c8903a2f993b8df81a2b484/image003.png)
### Az első játékos befejezte(rányomott az end turn gombra), szóval most a 2. játékos jön. Ugyanúgy a piros mezőbe rakhat és az egységek a vár előtt lesznek, egymáson. Alul lehet látni az egységek valódi számát.
![image004](/uploads/5b42d9602ed0cd2a9c3ee3f64c2f8e71/image004.png)
### Mindkét játékos befejezte, szóval most a támadó kör jön, pár lépés erejéig. Minden egység kiszámolja, mi a legrövidebb út az ellenség várához és azon lép egy megadott számot. A tüskés egységek lassabban  mennek, de több életerejűk van, a sima egységek gyorsak, de könnyen pusztíthatók
![image005](/uploads/3cdccf21b7a27121706b065737694e69/image005.png)
### Megtörténtek a lépések, szóval jön a következő építkező kör. A pénzhez hozzáadódik, az életbe lévő egységek száma és kilőtt egységek száma (ebben a körben még nem öltek a tornyok). Ugyanúgy várakat építhetünk a kék mezőbe (ahol az ellenség egysége van, nem lehet) .
![image006](/uploads/0f57847920fbea9872f8f0c124e12891/image006.png)
### Ugyanez a piros oldalon, “az ide se lehet” felirat érvénytelen, ha vártól bárhonnan indíthatok egységek, mivel akkor nem zárjuk el az egységek járásvonalat, max. onnantól a vártól jobbra nem lehet egységet rakni, mivel beszorul. 
![image007](/uploads/26e8276adefe981f92c0ee5d902c437d/image007.png)
### Az új egységek elindulnak a vártól és az új tornyok is működésbe lépnek. Újra kiszámítja az összes egység a legrövidebb útvonalat.
![image008](/uploads/ae93b7a97727a1eac3c89ed61e21daac/image008.png)
### Az egységek a tornyokhoz érnek, ahol sebzést kapnak tőlük(bizonyos távolságtól). A tornyok kölünbözően sebeznek(távolság és gyorsaság). Ha egy egység eljutt egy vár elötti mezőhöz, elpusztul és a vár ereje csökken. A kör végén az összes elpusztított egységért is fogunk már pénzt kapna, nemcsak a meglévő egységeinkért(ebben az esetben a piros játékos). 
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

Terminál:
Before: `git pull -r`  
New branch: `git checkout -b "your_branch_name"`  
Change branch: `git checkout "your_branch_name"` 
View existing branch: `git branch`  
Delete branch: `git branch -D "your_branch_name"` 
Fetch origin: `git fetch origin`  
Merge to your branch: `git merge origin/main`
