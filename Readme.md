NDAYONGEJE Aimé Trésor


lien git : https://github.com/ndayonga/rabbitMQ_lab

# Commandes pour exécuter et lancer les exécutables

javac -cp .:amqp-client-5.16.0.jar src/*.java -d /home/n/ndayonga/Bureau/M1/IDS/lab3

java -cp .:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar Process1

java -cp .:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar Process2

# Commentaires sur le code :

Les exécutions marchaient et je pouvais observer le ping pong entre mes deux processus et dans la démarche de gestion des cas d'arrêts d'exécution d'un des processus, l'échange de ping pong ne marche plus.

Mon implémentation consiste en deux fichiers presque identiques à l'exception de l'intervalle à laquelle appartient l'ID du processus.

Le but à atteindre était que une fois que le deuxième processus lancé, l'échange du ping pong commence automatiquement.