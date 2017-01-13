#!/bin/sh
#supprimer les fichier .res

PATH=~/src/test/script/:"$PATH"
rm src/test/deca/etapeA/valid/*.res
rm src/test/deca/etapeB/valid/*.res
rm src/test/deca/etapeA/invalid/*.res
rm src/test/deca/etapeB/invalid/*.res
