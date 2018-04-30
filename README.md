# IG3A-Java-2048
This is a Java project implementing the 2048 Game

## Table of Contents
1. Présentation général
    1. Présentation du jeu
    2. Squelette de sources
    3. Interface Utilisateur
2. Développement
    1. Le Pattern Observer

## Présentation général

### Présentation du jeu
2048 est un jeu vidéo de type puzzle. Le but du jeu est de faire glisser des tuiles sur une grille, pour combiner les tuiles de mêmes valeurs et créer ainsi une tuile portant le nombre 2048. Le joueur peut toutefois continuer à jouer après cet objectif atteint pour faire le meilleur score possible.

### Squelette des sources
Les sources sont répartit dans plusieurs packages :
- Le package `miniprojet.model` contient les classes du model de données de l'application
- Le package `miniprojet.ui` contient les classes de l'interfaces utilisateur (les composants)
- Le package `miniprojet` contient le main, les ressources textuel ainsi que les interfaces "misc".

### Interface Utilisateur
L'interface graphique permet à l'utilisateur de jouer au jeu `2048` de manière simple avec à sa disposition un leaderboard qu'il pourra nettoyer. La librairie graphique utilisé est `Swing`. 

## Développement

### Principes POO
Différents principe de la programmation objet sont utilisés dans ce projet :
- Héritage
- Interface
- Exception

### Le Pattern Observer
La classe `Data` permet de stocker les données de l'application. (elle pourra à l'avenir permettre d'importer et exporter les données - en JSON par exemple).
J'utilise dans ce projet le pattern Observer, il consiste à créé 2 interfaces deux interfaces :
- `Observer` : Une classe implémentant l'interface `Observer` devient observateur.
- `Observable` : Une classe implémentant l'interface `Obeservable` est observé car elle contient les données, elle contient aussi la liste de tous ses observateurs (les classes implémtentant `Observer`) pour ainsi les notifier de la modification des données.

Pour rappel, les classes implémentant une interface doivent forcément implémenter les méthodes de l'interface. (On peut aussi créer un classe anonyme comme vu en TP)

Ainsi mes classes servant d'interface utilisateur tels que la vue `Leaderboard` pourra être notifié de la modification des données.
Ainsi en généralisant quand une données sera ajouté depuis une interface 1, l'interface 2 pourra être notifié et se mettre à jour.
