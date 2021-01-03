#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include "struct.h"

//aula-prática -dada pelo professor
int clean(){
	while(getchar() != '\n');
}

//Tratamento de Sinais 
int n=0;
int verificar=0;
  
//c6
void trata_sinalSIGUSR2() {
 n=1;
 printf("\n <ERROR>: Consulta não é possível para o processo %d\n", getpid());
 remove("PedidoConsulta.txt");
 exit(0);
 n=0;
}

//c4
void trata_sinalSIGHUP(){
n=1;
 verificar=1;
 remove("PedidoConsulta.txt");
 printf("\n<Consulta Iniciada> para o processo %d\n", getpid());
 n=0;
}

//c5
void trata_sinalSIGTERM(){
 n=1;
 if(verificar==1)
 printf("\n<Consulta concluída> para o processo %d\n", getpid());
 else
 printf("\nERROR: O Sinal SIGHUP tem de ser recebido primeiro\n");
 exit(0);
 n=0;
}

//c7
void trata_sinalSIGINT(int sinal){
 n=1;
 printf("\nPaciente cancelou pedido\n");
 exit(1);
 n=0;
}

//c1
void cria_consulta(){
int tipo;
char descricao[100];
	printf("Introduza, por favor, o tipo de consulta que deseja--->");
	scanf("%d", &tipo);

	while(tipo <= 0 || tipo > 3){
	printf("\nERROR: Insira um número entre 1 e 3\n");
	printf("<Tipo 1> - Normal | <Tipo 2> - Covid | <Tipo 3> - Urgente\n");
	printf("Introduza novamente o tipo de consulta que deseja--->");
	scanf("%d", &tipo);
}
	clean();
	printf("Adicione uma descrição à consulta--->");
	fgets(descricao,100,stdin);

//c2
Consulta consulta;
consulta.tipo=tipo;
strcpy(consulta.descricao,descricao);
consulta.pid_consulta=getpid();
consulta.descricao[strcspn(consulta.descricao, "\n")] = 0;
printf("\n<--------Nova Consulta-------->\n");
printf("\nTipo da Consulta ---> %d\n",consulta.tipo);
printf("Descrição ---> %s\n", consulta.descricao);
printf("PID ---> %d\n", consulta.pid_consulta);

FILE *file;
	file=fopen("PedidoConsulta.txt", "w");
	fprintf(file, "%s\n", consulta.descricao);
        fprintf(file, "%d\n", consulta.tipo);
	fprintf(file, "%d\n", consulta.pid_consulta);	
    fclose(file);
}

//c3 
void read_pid_send_SIGUSR1(){
int pid_lido;
FILE *file1;
file1=fopen("SrvConsultas.pid", "r");
fscanf(file1,"%d", &pid_lido);
fclose(file1);
kill (pid_lido, SIGUSR1);
}

//MAIN
int main(){

cria_consulta();
read_pid_send_SIGUSR1();

signal(SIGUSR2,trata_sinalSIGUSR2);
signal(SIGHUP,trata_sinalSIGHUP);
signal(SIGTERM,trata_sinalSIGTERM);
signal(SIGINT,trata_sinalSIGINT);

while(n==0){
	pause();
 }
}