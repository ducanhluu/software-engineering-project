
#!/bin/sh 
cd "$(dirname "$0")"/../../.. || exit 1

PATH=~/src/test/script/launchers/:"$PATH"

for i in ./src/test/deca/etapeB/invalid/*.deca
do
    if  test_context "$i" 2>&1 | grep -q -e './src/test/deca'
    then
	test_context "$i" >&  "${i%.deca}".res
	echo "Echec attendu pour le test ""${i#*invalid/}" "du test_context"
    else
	echo "Succes inattendu du test ""${i#*invalid/}" "du test_context"
	exit 1
    fi
    
    
done
