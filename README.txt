Projet Génie Logiciel, Ensimag.
gl17, 01/01/2017.


Pour lancer tous les tests de notre base soit :
*En tapant la commande: mvn test
*En lançant les tests de chaque partie du projet séparement:
-> la partie syntaxique: ./src/test/script/test-syntax-valid.sh
			 ./src/test/script/test-syntax-invalid.sh
-> la partie contextuelle: ./src/test/script/test-context-valid.sh
			   ./src/test/script/test-context-invalid.sh
-> la partie de la génération du code : ./src/test/script/test-gencode-valid.sh
		              	 ./src/test/script/test-gencode-invalid.sh
