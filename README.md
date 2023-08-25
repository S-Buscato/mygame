# mygame
- Pour builder le projet exécuter la commande : ./gradlew shadowJar
- Le projet se trouve: 'build/libs/com.playeranking.player-ranking-all.jar'
- Pour lancer le service via le script bash, à la racine du projet, executer : ./ranking.sh

# Endpoints :
Get : 
- /api/players => get all players
- /api/players/{pseudo} => get a player by pseudo
- /api/players/sortedByScore => get all players sorted by score

Post :
- /api/player => post a new player {pseudo=xx,score=00}

Put :
- /api/players/update/{pseudo} => update a player {pseudo=xx,score=00}

Delete :
- /api/players => delete all players 


