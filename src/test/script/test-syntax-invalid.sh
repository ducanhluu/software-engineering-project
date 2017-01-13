#!/bin/sh 
cd "$(dirname "$0")"/../../.. || exit 1

PATH=~/src/test/script/launchers/:"$PATH"

for i in ./src/test/deca/etapeA/invalid/*.deca
do
    if  test_synt "$i" 2>&1 | grep -q -e './src/test/deca'
    then
	test_synt "$i" >&  "${i%.deca}".res
	echo "Echec attendu pour le test ""${i#*invalid/}" "du test_synt"
    else
	echo "Succes inattendu du test ""${i#*invalid/}" "du test_synt"
	exit 1
    fi
    
    
done
