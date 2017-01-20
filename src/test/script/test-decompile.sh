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

PATH=~/src/test/script/launchers/:"$PATH"
echo -e "${bleuclair}décompilation des tests syntaximent valid${neutre}"

for i in ./src/test/deca/syntax/valid/*.deca
do
    if  decac -p "$i" 2>&1 | grep -q -e '/src/test/deca'
    then
	decac -p "$i" >&  "${i%.deca}".dec
	echo -e "${rougefonce}Fail${neutre}" "Echec inattendu pour le test ""${i#*valid/}" "de la decompilation"
        exit 1
    else
	decac -p "$i" >&  "${i%.deca}".dec
	echo -e "${vertclair}Pass${neutre}" "Succes attendu du test ""${i#*valid/}" "de la decompilation"
    fi
    
    
done

echo -e "${bleuclair}décompilation des tests contextuellement valid${neutre}"
for i in ./src/test/deca/context/valid/*.deca
do
    if  decac -p "$i" 2>&1 | grep -q -e '/src/test/deca'
    then
	decac -p "$i" >&  "${i%.deca}".dec
	echo -e "${rougefonce}Fail${neutre}" "Echec inattendu pour le test ""${i#*valid/}" "de la decompilation"
        exit 1
    else
	decac -p "$i" >&  "${i%.deca}".dec
	echo -e "${vertclair}Pass${neutre}" "Succes attendu du test ""${i#*valid/}" "de la decompilation"
    fi
    
    
done
