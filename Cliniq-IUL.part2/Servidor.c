#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include "struct.h"

//s1
Consulta lista_consultas[10];

int tipo1=0;
int tipo2=0;
int tipo3=0;
int perdidas=0;
int n=0;
Consulta consulta;
  
//s2
void write_pid(){
	FILE *file;
	file=fopen("SrvConsultas.pid", "w");
	fprintf(file, "%d", getpid());
	fclose(file);
	printf("\n--------- <holding>---------\n");
	printf("Waiting for a Client to get in\n");
}

//s3.1 e s3.2
void chega_consulta(){
	FILE *doc;
	doc=fopen("PedidoConsulta.txt", "r");
	fgets(consulta.descricao,100,doc);
        fscanf(doc, "\n%d\n%d", &consulta.tipo, &consulta.pid_consulta);
        fclose(doc);
	printf("<Chegou novo pedido de consulta> do tipo --> %d, descricao --> %s e PID --> %d\n", consulta.tipo, consulta.descricao, consulta.pid_consulta );
}

//Tratamento de Sinais
void trata_sinal() {
	
	n=1;
	chega_consulta();
	
//s3.3
 	int vagas=0;
    for(int i=0; i<10;i++){
     if(lista_consultas[i].tipo==-1){
     vagas++;
   }
 }	
     if(vagas==0){
	 printf("ERROR: Lista de consultas cheia\n");
     kill(consulta.pid_consulta,SIGUSR2);
	 perdidas++;
	
	}else{

//s3.4
	int i;
	for ( i=0 ; i<10 ; i++){
	if(lista_consultas[i].tipo == -1){
	lista_consultas[i]=consulta;

	lista_consultas[i].tipo=consulta.tipo;
	strcpy(lista_consultas[i].descricao,consulta.descricao);
	lista_consultas[i].pid_consulta=consulta.pid_consulta;

	switch(consulta.tipo){
		case 1: tipo1++;
		break;
		case 2: tipo2++;
		break;
		case 3: tipo3++;
		break;
	}
	printf("\n<Consulta agendada> para a sala %d\n", i);
	break;
	}
} 

//s3.4.1 e 3.4.2
	if(lista_consultas[i].pid_consulta==consulta.pid_consulta){
	int clone=fork();
	if(clone<0){
		perror("erro no fork");
	exit(1);
}
	if (clone==0){	 
	 kill(consulta.pid_consulta,SIGHUP);
	 printf("\n<...Consulta a decorrer...>\n");
	 sleep(10);
	 printf("\n<Consulta terminada> na sala %d\n",i);
     printf("\n________________________________//____________________________________________\n");
     kill(consulta.pid_consulta,SIGTERM); 
     exit(0);
     }else{
	 wait(NULL);
     lista_consultas[i].tipo=-1;
}
}
}
n=0;
}

//s4	

	void trata_sinalSIGINT(){
	n=1;
	remove("SrvConsultas.pid");
	int perdidas_antigo;
	int tipo1_antigo;
	int tipo2_antigo;
	int tipo3_antigo;
	int w [4];
	int r [4];
	
	//If the file already exists
	if(access("StatsConsultas.dat", F_OK) != -1){

	FILE *read_binary_file = fopen("StatsConsultas.dat","r");
	fread(r,sizeof(r),1,read_binary_file);
	fclose(read_binary_file);

	w[0]=r[0]+perdidas;
	w[1]=r[1]+tipo1;
	w[2]=r[2]+tipo2;
	w[3]=r[3]+tipo3;

	FILE *read_and_write_binary_file = fopen ("StatsConsultas.dat", "w");
	fwrite(w, sizeof(w), 1 , read_and_write_binary_file);
	fclose(read_binary_file);
	
	
	for(int i=0; i<4 ; i++){
		printf("\n%d\n", w[i]);
	}
	
	//If it doesn't exist
	}else{

	w[0]=perdidas;
	w[1]=tipo1;
	w[2]=tipo2;
	w[3]=tipo3;

	FILE *write_binary_file = fopen ("StatsConsultas.dat", "w");
	fwrite(w, sizeof(w), 1 , write_binary_file);
	fclose(write_binary_file);

	
	for(int i=0; i<4 ; i++){
		printf("\n%d\n", w[i]);
	}
	}

	n=0;
	exit(0);
	}

//MAIN

int main(){

for(int i=0; i<10;i++){
lista_consultas[i].tipo=-1;
}
	write_pid();

    signal(SIGUSR1,trata_sinal); //s3
    signal(SIGINT,trata_sinalSIGINT); //s4
 
    while(n==0){
    pause();
    }
 }