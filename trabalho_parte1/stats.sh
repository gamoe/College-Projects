#!/bin/bash


if [ -f medicos.txt ] && [ -f pacientes.txt ]; then



Localidade=$1
Saldo_Minimo=$2
Numero_Localidade=$(cat pacientes.txt | cut -d ';' -f3 | grep -x "$Localidade" | wc -l)
Numero_Medicos_No_Ficheiro=$(cat medicos.txt | wc -l)
Numero_Medicos_Com_Saldo_Superior=0
i=1
		while [ $i -le $Numero_Medicos_No_Ficheiro ]; do

		Salario_Medicos=$(cat medicos.txt | cut -d ';' -f7 | head -$i | tail -1)

		if [ $Salario_Medicos -gt $2 ]; then
			Numero_Medicos_Com_Saldo_Superior=$(($Numero_Medicos_Com_Saldo_Superior+1))
		fi
 
		i=$(($i+1))
		
	done
	clear
	echo " ----Existe(m) $Numero_Localidade paciente(s) registado(s) em "$1"-----
----Existe(m) $Numero_Medicos_Com_Saldo_Superior médico(s) com saldo superior a $2----" 




else
	clear
	echo "---------- UM DOS FICHEIROS NÃO EXISTE----------- "
fi
