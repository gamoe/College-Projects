#include "defines.h"

int id;
int type;
char description[100];
int verify = 0;
mensagem message;

//aula-prática -dada pelo professor
int clean()
{
	while (getchar() != '\n')
		;
}

//c1
void creat_consulta()
{
	printf("\n  _____________________________________________________________ \n");
	printf(" |                    Tipos de Consulta                        |\n");
	printf(" |_____________________________________________________________|\n");
	printf(" | <Type 1> - Normal | <Type 2> - Covid | <Type 3> - Urgente   |\n");
	printf(" |_____________________________________________________________|\n");
	printf("\n");
	printf("\nIntroduza, por favor, o tipo de consulta que deseja---> ");
	scanf("%d", &type);

	while (type <= 0 || type > 3)
	{
		printf("\nERROR: Insira um número entre 1 e 3\n");
		printf("\n  _____________________________________________________________ \n");
		printf(" |                    Tipos de Consulta                        |\n");
		printf(" |_____________________________________________________________|\n");
		printf(" | <Type 1> - Normal | <Type 2> - Covid | <Type 3> - Urgente   |\n");
		printf(" |_____________________________________________________________|\n");
		printf("\n");
		printf("\nIntroduza, por favor, o tipo de consulta que deseja---> ");
		scanf("%d", &type);
	}

	clean();
	printf("Adicione uma descrição à consulta---> ");
	fgets(description, 100, stdin);
}

//c2
void sendPedidoConsulta()
{
	Consulta consulta;
	char final_message[120];

	//associa as informações ao objeto consulta
	consulta.tipo = type;
	strcpy(consulta.descricao, description);
	consulta.pid_consulta = getpid();
	consulta.descricao[strcspn(consulta.descricao, "\n")] = 0;
	consulta.status = 1;
	printf("\n|<--------Nova Consulta-------->|\n");
	printf("\nTipo da Consulta ---> %d\n", consulta.tipo);
	printf("Descrição ---> %s\n", consulta.descricao);
	printf("PID ---> %d\n", consulta.pid_consulta);
	printf("\n|<----------------------------->|\n");
	sprintf(final_message, "%d;%d;%d;%s", consulta.tipo, consulta.pid_consulta, consulta.status, consulta.descricao);

	//access to the message queue
	id = msgget(IPC_KEY, 0);
	exit_on_error(id, "error on the mssget");

	//sends a message to Server
	message.tipo = 1;
	strcpy(message.texto, final_message);
	int status = msgsnd(id, &message, sizeof(message.texto), 0);
}

//c4
void starting_consulta(int status_of_message)
{
	verify = 1;
	printf("\nConsulta iniciada para o processo %d\n", getpid());
}

//c5
void terminate_consulta(int status_of_message)
{

	if (verify == 1)
	{
		printf("Consulta concluída para o processo %d\n", getpid());
	}
	else
	{
		printf("ERROR: Consulta não foi sequer iniciada\n");
	}
	exit(0);
}

//c6
void decline_consulta(int status_of_message)
{
	printf("\nERROR: Consulta não é possível para o processo %d\n", getpid());
	exit(0);
}

//c3
void recieveMessageWithPid()
{
	int status;
	mensagem message;

	//accessing to the message queue
	id = msgget(IPC_KEY, 0);
	exit_on_error(id, "Connection to the Message Queue")

		//recieving message
		status = msgrcv(id, &message, sizeof(message.texto), getpid(), 0);
	exit_on_error(status, "Reception");
	int status_of_message = atoi(message.texto);

	switch (status_of_message)
	{
	case 2:
		starting_consulta(status_of_message);
		break;

	case 3:
		terminate_consulta(status_of_message);
		break;

	case 4:
		decline_consulta(status_of_message);
		break;
	}
}

//c7
void handler()
{
	//access to the message queue
	id = msgget(IPC_KEY, 0);
	exit_on_error(id, "error on the mssget");

	//sends a message to Client
	message.tipo = getpid();
	printf("%d\n", message.tipo);
	sprintf(message.texto, "%d", 5);
	int status = msgsnd(id, &message, sizeof(message.texto), 0);
	printf("\nPaciente cancelou o pedido\n");
	printf("\n");
	exit(0);
}

//Main
int main()
{
	creat_consulta();
	sendPedidoConsulta();
	while (1)
	{
		recieveMessageWithPid();
		signal(SIGINT, handler);
	}
}