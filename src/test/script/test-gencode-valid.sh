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

neutre='\e[0;m'
PATH=./src/test/script/launchers/:./src/main/bin:"$PATH"
rm -f ./src/test/deca/etapeC/valid/*.ass 2>/dev/null
for i in ./src/test/deca/etapeC/valid/*.deca
do
    decac "$i"
    if  [ ! -f "${i%.deca}".ass ];then 
	echo -e "${rougefonce}Fail${neutre}" "Fichier ""${i%.deca}"".ass" "non généré"
	exit 1
    else
	echo -e "${vertclair}Pass${neutre}" "Fichier ""${i%.deca}"".ass" "généré"
    fi
    
    
done
