#!/bin/bash

if [ -f medicos.txt ];then






	if [ -f lista_negra_medicos.txt ]; then
		rm lista_negra_medicos.txt
fi
i=1
numero_medicos=$(cat medicos.txt | wc -l)

	while [ $i -le $numero_medicos ]; do

	rating_medicos=$(cat medicos.txt | cut -d ';' -f5 | head -$i | tail -1)
	consultas_medicos=$(cat medicos.txt | cut -d ';' -f6 | head -$i | tail -1)
	
	if [[ $consultas_medicos -gt 6 ]] && [[ $rating_medicos -lt 5 ]]; then
		linha=$(cat medicos.txt | head -$i | tail -1)
		echo "$linha" >> lista_negra_medicos.txt
	fi
	i=$(($i+1))
done
	echo "--------------------LISTA NEGRA---------------------"
	cat lista_negra_medicos.txt






else
	clear
	echo "---------------O FICHEIRO "medicos.txt" NÃO EXISTE--------------"
fi
