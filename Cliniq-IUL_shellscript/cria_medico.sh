#!/bin/bash
nome=$1
numero_cedula=$2
especialidade=$3
email=$4
numero_cedula_txt=$(cat medicos.txt | cut -d ';' -f2 | grep -x $numero_cedula | wc -l)
email_txt=$(cat medicos.txt | cut -d ';' -f4 | grep -x $email | wc -l)

	if [ $numero_cedula_txt -eq 0 ] && [ $email_txt -eq 0 ]; then
		echo ""$nome";$numero_cedula;"$especialidade";$email;0;0;0" >> medicos.txt
		clear
		echo "----------O MÉDICO FOI REGISTADO COM SUCESSO----------"
		cat medicos.txt	
	else
		echo "----------ERROR: O MÉDICO JÁ FOI REGISTADO----------"
fi
