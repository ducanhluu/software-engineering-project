#!/bin/sh 
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers/:"$PATH"

for i in ./src/test/deca/codegen/valid/notprovided/*.deca
do
    if  test_codegen "$i" 2>&1 | grep -q -e './src/test/deca'
    then
	test_codegen "$i" >&  "${i%.deca}".res
	echo "Echec inattendu pour le test ""${i#*notprovided/}" "du test_context"
	echo 1
    else
	echo "Succes attendu du test ""${i#*notprovided/}" "du test_context"
    fi
    
    
done
