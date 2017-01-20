#!/bin/sh
#supprimer les fichier .res

PATH=~/src/test/script/:"$PATH"
rm src/test/deca/syntax/valid/*.res
rm src/test/deca/context/valid/*.res
rm src/test/deca/syntax/invalid/*.res
rm src/test/deca/context/invalid/*.res
rm src/test/deca/codegen/valid/*.res
rm src/test/deca/codegen/valid/*.ass
rm src/test/deca/codegen/invalid/*.res
rm src/test/deca/codegen/invalid/*.ass
rm src/test/deca/syntax/valid/*.dec
rm src/test/deca/context/valid/*.dec
