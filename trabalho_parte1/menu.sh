#!/bin/bash

quit=off

while [ "$quit" = "off" ]; do

echo
echo "----Cliniq-IUL----"
echo
echo "-------MENU-------"
echo
echo "1. Cria Pacientes"
echo "2. Cria Médicos"
echo "3. Stats"
echo "4. Avalia médicos"
echo "0. Sair"
echo
echo -n "Escolha um opção:"

read option
case $option in

1)	
	clear
	./cria_pacientes.sh
	;;


2)	clear
	echo "Insira o nome:"
		read nome

	echo "Insira o numero de cédula:"
		read numero_cedula

	echo "Insira a especialidade:"
		read especialidade

	echo "Insira o endereço de email:"
		read email
	
	./cria_medico.sh "$nome" $numero_cedula "$especialidade" $email 
	;;


3)  clear
	echo -n "Insira a Localidade:"
		read localidade
	
	echo -n "Insira o saldo:"
		read saldo
	
	./stats.sh "$localidade" $saldo;;

4)  clear
	./avalia_medicos.sh;;

0) quit=on
	clear
	;;

*)echo "-------OPÇÃO INVÁLIDA--------"

esac
done
