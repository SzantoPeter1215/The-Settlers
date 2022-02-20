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
![uml](/uploads/e8d477b6244f874c39190ebc43130436/uml.PNG)

## Use case 
![usecase](/uploads/e9a363190fc56737d2fcdfadb2925183/usecase.PNG)

## User stories

| AS A      |       | Player                                                     |
| --------- | ----- | ---------------------------------------------------------- |
| I WANT TO |       | Build a tower                                              |
| 1         | GIVEN | That I clicked on a valid field                            |
|           | WHEN  | I have sufficient money                                    |
|           | THEN  | The tower gets built                                       |
| 2         | GIVEN | That I clicked on a valid field                            |
|           | WHEN  | I have don't have sufficient money                         |
|           | THEN  | The tower doesn't get built                                |
| AS A      |       | Player                                                     |
| I WANT TO |       | Train a unit                                               |
| 1         | GIVEN | That I clicked on the unit's icon                          |
|           | WHEN  | I have sufficient money                                    |
|           | THEN  | The unit will start moving in the attack phase             |
| 2         | GIVEN | That I clicked on the unit's icon                          |
|           | WHEN  | I don't have sufficient money                              |
|           | THEN  | Nothing happens                                            |
| AS A      |       | Player                                                     |
| I WANT TO |       | Begin the attack phase                                     |
| 1         | GIVEN | That I clicked on the end turn button                      |
|           | WHEN  | The other player has already ended their turn              |
|           | THEN  | The attack phase starts                                    |
| 2         | GIVEN | That I clicked on the end turn button                      |
|           | WHEN  | The other player has not yet ended their turn              |
|           | THEN  | The other player's build phase begins                      |
| AS A      |       | Player                                                     |
| I WANT TO |       | Get more gold                                              |
| 1         | GIVEN | That I have units on the board                             |
|           | WHEN  | The attack phase ends                                      |
|           | THEN  | I get money for my surviving units                         |
| 2         | GIVEN | That my towers killed enemy units in the attack phase      |
|           | WHEN  | The attack phase ends                                      |
|           | THEN  | I get money for each killed enemy unit                     |
| AS A      |       | Player                                                     |
| I WANT TO |       | Build a tower                                              |
| 1         | GIVEN | That I clicked on a field                                  |
|           | WHEN  | I wouldn't block all available routes to my castle         |
|           | THEN  | The tower gets built                                       |
| 2         | GIVEN | That I clicked on a field                                  |
|           | WHEN  | I would block all available routes to my castle            |
|           | THEN  | The tower doesn't get built                                |
| AS A      |       | Player                                                     |
| I WANT TO |       | Damage my opponents castle                                 |
| 1         | GIVEN | That I have units on the board                             |
|           | WHEN  | They reach the enemy castle                                |
|           | THEN  | The enemy castle takes damage                              |
| 2         | GIVEN | That I have units on the board                             |
|           | WHEN  | They can't reach the enemy castle                          |
|           | THEN  | The units move and take damage from enemy towers           |
| AS A      |       | Player                                                     |
| I WANT TO |       | Damage my opponents unit(s)                                |
| 1         | GIVEN | That I have a tower on the board                           |
|           | WHEN  | The enemy units walk in range of my towers or castle       |
|           | THEN  | The enemy unit takes damage                                |
| 2         | GIVEN | That I have a tower on the board                           |
|           | WHEN  | The enemy units don't walk in range of my towers or castle |
|           | THEN  | The enemy unit moves but doesn't take damage               |
