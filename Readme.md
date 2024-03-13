# Commandes pour exécuter et lancer les exécutables
lien git : https://github.com/ndayonga/rabbitMQ_lab
javac -cp .:amqp-client-5.16.0.jar src/*.java -d ~/Bureau/M1/IDS/lab3

java -cp .:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar Process1

java -cp .:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar Process2