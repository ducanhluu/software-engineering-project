#!/bin/sh 
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers/:"$PATH"

for i in ./src/test/deca/codegen/invalid/notprovided/*.deca
do
    if  test_codegen "$i" 2>&1 | grep -q -e './src/test/deca'
    then
	test_codegen "$i" >&  "${i%.deca}".res
	echo "Echec attendu pour le test ""${i#*notprovided/}" "du test_context"
    else
	echo "Succes inattendu du test ""${i#*notprovided/}" "du test_context"
    fi
    
    
done
