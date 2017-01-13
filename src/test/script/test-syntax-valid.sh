#! /bin/sh 
cd "$(dirname "$0")"/../../.. || exit 1

PATH=~/src/test/script/launchers/:"$PATH"

for i in ./src/test/deca/etapeA/valid/*.deca
do
    if  test_synt "$i" 2>&1 | grep -q -e './src/test/deca'
    then
	test_synt "$i" >&  "${i%.deca}".res
	echo "Echec inattendu pour le test ""${i#*valid/}" "du test_synt"
	exit 1
    else
	test_synt "$i" >&  "${i%.deca}".res
	echo "Succes attendu du test ""${i#*valid/}" "du test_synt"
    fi
    
    
done
