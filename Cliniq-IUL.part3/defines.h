#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/msg.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <sys/wait.h>

#define exit_on_error(s,m) if (s<0) { perror(m); exit(1);}
#define exit_on_null(s,m) if (s==NULL) { perror(m); exit(1); }

#define IPC_KEY 0x0a92458 // Substitua pelo seu número de aluno
#define NACONSULTAS 10

typedef struct {
 int tipo; // Tipo de Consulta: 1-Normal, 2-COVID19, 3-Urgente
 char descricao[100]; // Descrição da Consulta
 int pid_consulta; // PID do processo que quer fazer a consulta
 int status; // Estado da consulta: 1-Pedido, 2-Iniciada, 3-Terminada, 4-Recusada, 5-Cancelada
} Consulta; 

//struct with the pointers
typedef struct{
Consulta lista_consultas[10];
int type1;
int type2;
int type3;
int lost;
} SHM_Consultas;

//struct for the message
typedef struct {
long tipo;
char texto[120];
} mensagem;

//substring function
void substring(char line [], char final[], char splitter, int index){
    int i=0, j=0, aux=0;
    for(int i = 0; line[i] != '\0'; i++){
        if(line[i] == splitter){
            aux++;
        }else if (aux == index){
            final[j++] = line [i];
        }
    }
    final[j] = '\0';
}

//semaphores
//up the semaphore value
struct sembuf UP = { 
    .sem_num = 0, 
    .sem_op = +1,
    .sem_flg = 0
};

//down the semaphore value
struct sembuf DOWN = { 
    .sem_num = 0, 
    .sem_op = -1,
    .sem_flg = 0
};


