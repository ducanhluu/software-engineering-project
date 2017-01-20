#!/bin/sh
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
echo -e "${rose}le test test-syntax-valid est lancé:${neutre}"
./src/test/script/test-syntax-valid.sh
echo -e "${orange}le test test-syntax-invalid est lancé:${neutre}"
./src/test/script/test-syntax-invalid.sh
echo -e "${cyanclair}le test test-context-valid est lancé:${neutre}"
./src/test/script/test-context-valid.sh
echo -e "${vertclair}le test test-context-invalid est lancé:${neutre}"
./src/test/script/test-context-invalid.sh
echo -e "${gris}le test test-gencode-valid est lancé:${neutre}"
./src/test/script/test-gencode-valid.sh
echo -e "${bleuclair}le test test-gencode-invalid est lancé:${neutre}"
./src/test/script/test-gencode-invalid.sh
echo -e "${jaune}le test test-decompile est lancé:${neutre}"
./src/test/script/test-decompile.sh 
