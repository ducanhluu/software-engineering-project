#!/bin/sh 
cd "$(dirname "$0")"/../../.. || exit 1

PATH=~/src/test/script/launchers/:"$PATH"

for i in ./src/test/deca/etapeB/valid/*.deca
do
    if  test_context "$i" 2>&1 | grep -q -e './src/test/deca'
    then
	test_context "$i" >&  "${i%.deca}".res
	echo "Echec inattendu pour le test ""${i#*valid/}" "du test_context"
        exit 1
    else
	test_context "$i" >&  "${i%.deca}".res
	echo "Succes attendu du test ""${i#*valid/}" "du test_context"
    fi
    
    
done
