#!/bin/sh 
cd "$(dirname "$0")"/../../.. || exit 1
noir='\e[0;30m'
gris='\e[1;30m'
rougefonce='\e[0;31m'
rose='\e[1;31m'
vertfonce='\e[0;32m'
vertclair='\e[1;32m'
orange='\e[0;33m'
jaune='\e[1;33m'
bleufonce='\e[0;34m'
bleuclair='\e[1;34m'
violetfonce='\e[0;35m'
violetclair='\e[1;35m'
cyanfonce='\e[0;36m'
cyanclair='\e[1;36m'
grisclair='\e[0;37m'
blanc='\e[1;37m'

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
