# Projet Génie Logiciel, Ensimag.
Write a compiler for an object-oriented programming language similar to Java
Project by team of 5 students for the application of software engineering methods
## gl17, 01/01/2017.

Pour lancer tous les tests de notre base soit :
1. En tapant la commande: 
```mvn test```
2. En lançant les tests de chaque partie du projet séparement:
* la partie syntaxique: 
```
./src/test/script/test-syntax-valid.sh
./src/test/script/test-syntax-invalid.sh
```
* la partie contextuelle: 
```
./src/test/script/test-context-valid.sh
./src/test/script/test-context-invalid.sh
```
* la partie de la génération du code : 
```
./src/test/script/test-gencode-valid.sh
./src/test/script/test-gencode-invalid.sh
```
* la décompilation : 
```
./src/test/script/test-decompile.sh
``` 

Pour lancer tous ces tests ci-dessus : 
```
./src/test/script/launch-all-test.sh
```

Pour supprimer les fichers .ass, .res et .dec générés par les commandes ci-dessus tapez la commande:
```
./src/test/script/removeResult.sh
```
