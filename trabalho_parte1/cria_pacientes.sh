#!/bin/bash

			if [ -f pacientes.txt ]; then
				rm pacientes.txt
			fi
	i=10
		while [ $i -gt 0 ]; do

		numero_registo=$(cat /etc/passwd | grep ^a[0-9] | head | cut -d ':' -f1 | cut -d 'a' -f2 | tail -$i | head -1)
		nome=$(cat /etc/passwd | grep ^a[0-9] | head | cut -d ':' -f5 | cut -d ',' -f1 | tail -$i | head -1) 
		email=$(cat /etc/passwd | grep ^a[0-9] | head | cut -d ':' -f1 | tail -$i | head -1)
		saldo=100

		echo "$numero_registo;$nome;;;$email"@iscte-iul.pt";$saldo" >> pacientes.txt
			i=$(($i-1))
	done
	clear
	echo "--------------------OS PACIENTES FORAM CRIADOS------------------------"
	cat pacientes.txt

