#include "defines.h"

int x;
int id;
int id_sem;
int res;
int status;
int DURACAO = 10;
mensagem message;
Consulta consulta;
SHM_Consultas *pointer;

//s1
void checkSharedMemory()
{
    int shm_id = shmget(IPC_KEY, NACONSULTAS * sizeof(Consulta), IPC_CREAT | IPC_EXCL | 0666); //IPC_EXCL returns -1 if already exists

    if (shm_id != -1)
    {

        shm_id = shmget(IPC_KEY, NACONSULTAS * sizeof(Consulta), IPC_CREAT | 0666);
        exit_on_error(shm_id, "At the shmget");

        pointer = (SHM_Consultas *)shmat(shm_id, NULL, 0);

        for (int i = 0; i < NACONSULTAS; i++)
        {
            pointer->lista_consultas[i].tipo = -1;
            printf("%d\n", pointer->lista_consultas[i].tipo);
        }
        printf("SMH iniciated\n");
        pointer->type1 = 0;
        pointer->type2 = 0;
        pointer->type3 = 0;
        pointer->lost = 0;
    }
    else
    {
        shm_id = shmget(IPC_KEY, NACONSULTAS * sizeof(Consulta), IPC_CREAT | 0666);
        pointer = (SHM_Consultas *)shmat(shm_id, NULL, 0);
    }
}

//create semaphore
void creatSemaphore()
{
    id_sem = semget(IPC_KEY, 1, IPC_CREAT | 0666);
    exit_on_error(id_sem, "Error at the semget");
    int status = semctl(id_sem, 0, SETVAL, 1);
    exit_on_error(status, "inicialização");
}

//up semaphore
void upSemaphore()
{
    int res = semop(id_sem, &UP, 1);
    exit_on_error(res, "UP failed");
}

//down sempahore
void downSemaphore()
{
    int res = semop(id_sem, &DOWN, 1);
    exit_on_error(res, "erro no DOWN");
}

//verify the number of vagas
int vagas()
{
    int vagas = 0;
    for (int i = 0; i < NACONSULTAS; i++)
    {
        if (pointer->lista_consultas[i].tipo == -1)
        {
            vagas++;
        }
    }
    return vagas;
}

//sends message status 4
void consultaMessage4()
{
    //access the message queue
    id = msgget(IPC_KEY, 0);
    exit_on_error(id, "error on the mssget");

    //sends a message to Client
    consulta.status = 4;
    message.tipo = consulta.pid_consulta;
    sprintf(message.texto, "%d", consulta.status);
    status = msgsnd(id, &message, sizeof(message.texto), 0);
}

//sends message status 2
void consultaMessage2()
{
    //access the message queue
    id = msgget(IPC_KEY, 0);
    exit_on_error(id, "error on the mssget");

    //sends a message to Client
    consulta.status = 2;
    message.tipo = consulta.pid_consulta;
    sprintf(message.texto, "%d", consulta.status);
    status = msgsnd(id, &message, sizeof(message.texto), 0);
}

//sends message status 3
void consultaMessage3()
{
    //access the message queue
    id = msgget(IPC_KEY, 0);
    exit_on_error(id, "error on the mssget");

    //sends a message to Client
    consulta.status = 3;
    message.tipo = consulta.pid_consulta;
    sprintf(message.texto, "%d", consulta.status);
    status = msgsnd(id, &message, sizeof(message.texto), 0);
}

//recieves message status 5
void verifyIfClienteCanceled()
{
    //accessing the message queue
    id = msgget(IPC_KEY, 0);
    exit_on_error(id, "Connection to the Message Queue")

    //recieving message
    status = msgrcv(id, &message, sizeof(message.texto), consulta.pid_consulta, 0);
    exit_on_error(status, "Reception");
    int status_of_message = atoi(message.texto);

    if (status_of_message == 5)
    {
        downSemaphore();
        printf("Consulta cancelada pelo o processo %d\n", consulta.pid_consulta);
        pointer->lista_consultas[x].tipo = -1;
        upSemaphore();
    }
}

//handle the signal alarm
void handler_alarm()
{
    downSemaphore();
    printf("<Consulta terminada> na sala %d\n", x);
    pointer->lista_consultas[x].tipo = -1;
    consultaMessage3();
    upSemaphore();
    exit(0);
}

//s3.3
void dedicatedServer()
{
    int filho = 0;
    filho = fork();

    if (filho < 0)
    {
        perror("ERROR: At the fork");
        exit(0);
    }

    if (filho == 0)
    {
        downSemaphore();
        if (vagas() == 0)
        {
            printf("ERROR: Lista de consultas cheia\n");
            consultaMessage4();
            pointer->lost++;

            upSemaphore();
            exit(0);
        }
        else
        {

            for (int i = 0; i < NACONSULTAS; i++)
            {
                if (pointer->lista_consultas[i].tipo == -1)
                {
                    pointer->lista_consultas[i] = consulta;
                    pointer->lista_consultas[i].tipo = consulta.tipo;
                    strcpy(pointer->lista_consultas[i].descricao, consulta.descricao);
                    pointer->lista_consultas[i].pid_consulta = consulta.pid_consulta;

                    switch (consulta.tipo)
                    {
                    case 1:
                        pointer->type1++;
                        break;
                    case 2:
                        pointer->type2++;
                        break;
                    case 3:
                        pointer->type3++;
                        break;
                    }
                    printf("\n<Consulta agendada> para a sala %d\n", i);
                    x = i;
                    break;
                }
            }

            consultaMessage2();

            upSemaphore();

            alarm(DURACAO);
            signal(SIGALRM, handler_alarm);

            verifyIfClienteCanceled();

            exit(0);
        }
    }
    else
    {
        waitpid(-1, NULL, WNOHANG);
    }
}

//s2
void recieveMessage()
{

    // creating and accessing to the message queue
    id = msgget(IPC_KEY, IPC_CREAT | 0666);
    exit_on_error(id, "Connection to the Message Queue")

    //recieving message
    int status = msgrcv(id, &message, sizeof(message.texto), 1, 0);
    exit_on_error(status, "Reception");

    //s3.1 & s3.2
    //printing the message with all the information
    char type_message_aux[1];
    char pid_message_aux[50];
    char description_message[50];
    char status_message_aux[1];

    substring(message.texto, type_message_aux, ';', 0);
    substring(message.texto, pid_message_aux, ';', 1);
    substring(message.texto, status_message_aux, ';', 2);
    substring(message.texto, description_message, ';', 3);

    int type_message = atoi(type_message_aux);
    int pid_message = atoi(pid_message_aux);
    int status_message_recieved = atoi(status_message_aux);

    consulta.tipo = type_message;
    consulta.pid_consulta = pid_message;
    consulta.status = status_message_recieved;
    strcpy(consulta.descricao, description_message);

    printf("\nChegou novo pedido do tipo %d, descrição %s e PID %d\n", type_message, description_message, pid_message);

    dedicatedServer();
}

//s4
void handler()
{
    printf("\n  ______________________________________________________________\n");
    printf(" |                         Statistics                           |\n");
    printf(" |______________________________________________________________|\n");
    printf(" | <Type 1>-> %d | <Type 2>-> %d | <Type 3>-> %d | <Lost>-> %d  |\n", pointer->type1, pointer->type2, pointer->type3, pointer->lost);
    printf(" |______________________________________________________________|\n");
    printf("\n");
    exit(0);
}

//main
int main()
{
    creatSemaphore();
    checkSharedMemory();
    signal(SIGINT, handler);
    while (1)
    {
        recieveMessage();
    }
}