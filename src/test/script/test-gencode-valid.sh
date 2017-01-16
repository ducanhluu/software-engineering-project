#!/bin/sh 
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers/:./src/main/bin"$PATH"
rm -f ./src/test/deca/etapeC/valide/*.ass 2>/dev/null
for i in ./src/test/deca/etapeC/valide/*.deca
do
    decac "$i"|| exit 1
    if[! - f  "${i%.deca}".ass];then
	
	echo "Fichier" "${i#*valide/}"".ass" "non généré."
	echo 1
    else
	echo "Fichier" "${i#*valide/}"".ass" "est généré."
    fi
    
    
done
