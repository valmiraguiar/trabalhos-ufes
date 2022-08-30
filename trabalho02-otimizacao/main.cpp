#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <vector>
#include "trabalho02-mo.h"
#include <fstream>
#include <string.h>
#include <time.h>
#include <memory.h>

#define MAX(X,Y) ((X > Y) ? X : Y)

#define MAX_X 100
#define MAX_Y 50

using namespace std;

const int PESO = 100;

int main()
{
    const int repeticoes = 1000;

    string instancia[] = {"j301_1", "j3048_10", "j601_2", "j6048_9", "j901_3", "j9048_8", "j1201_4", "j12060_7"};
    int seeds[] = {0, 398647, 839458745};

    clock_t hI, hF;
    double tempo;
    double tempo_total, tempo_limite;

    Solucao solucao;

    FILE *file = fopen("solucoes/solucao.txt", "w");

    for(int i=0; i < 8; i++) {
        tempo_limite = 300;
        lerDados(instancia[i]+".sm");

        for(int j = 0; j < 3; j++) {
            srand(seeds[j]);
            vns(solucao, tempo_melhor, tempo_total, tempo_limite);
            fprintf(file, "INSTANCIA - %s\nseed - %d\nFO - %d\ntempo melhor solucao - %.8lf\ntempo total - %.8lf\n",
                    instancia[i].c_str(), seeds[j], solucao.funObj, solucao.tempo_melhor, tempo_total);
            fprintf(file, "\n********************************************\n\n");
        }
    }

    fclose(file);
}

void lerDados(string arq)
{
    FILE *f = fopen(arq.c_str(), "r");

    if(f == NULL)
    {
        printf("ERRO AO ABRIR ARQUIVO!\n");
        return;
    }

    int tmp = 0;
    char str_temp[200];

    memset(&matriz_sucessores, 0, sizeof(matriz_sucessores));
    memset(&consumo_tarefas, 0, sizeof(consumo_tarefas));
    memset(&duracao_tarefas, 0, sizeof(duracao_tarefas));
    memset(&capacidade_recursos, 0, sizeof(capacidade_recursos));

    n_recursos = 0;
    n_tarefas = 0;

    int i=0;

    while(fgetc(f) != EOF)
    {
        fscanf(f, "%s", &str_temp);
        i++;
        if(i == 27)  //scan resources
        {
            fscanf(f, "%d", &n_recursos);
        }
        if(i == 49)  //scan jobs
        {
            fscanf(f, "%d", &n_tarefas);
        }
        if(i == 61)  //first job
        {
            int successors = 0; // LE A PRIMEIRA LINHA DOS JOBS
            for(int j = 1; j<3; j++)   //scan successors
            {
                if(j == 2)
                {
                    fscanf(f, "%d", &successors);
                }
                else
                {
                    fscanf(f, "%d", &tmp);
                }
            }
            for(int j=0; j<successors; j++)
            {
                int idSuc = 0;
                fscanf(f, "%d", &idSuc);
                matriz_sucessores[0][idSuc-1] = 1;
            }
            for(int k=1; k<n_tarefas + 1; k++)   //LÊ A PARTIR DA SEGUNDA LINHA DOS JOBS
            {
                successors = 0;
                for(int j = 0; j<3; j++)   //scan successors
                {
                    if(j == 2)
                    {
                        fscanf(f, "%d", &successors);
                    }
                    else
                    {
                        fscanf(f, "%d", &tmp);
                    }
                }
                for(int j=0; j<successors; j++)
                {
                    int idSuc = 0;
                    fscanf(f, "%d", &idSuc);
                    matriz_sucessores[k][idSuc-1] =  1;
                }
            }
            //DURATIONS
            fscanf(f, "%d", &tmp);
            fscanf(f, "%d", &tmp);
            fscanf(f, "%d", &tmp);
            fscanf(f, "%d", &tmp);
            fscanf(f, "%d", &tmp);
            fscanf(f, "%d", &tmp);
            fscanf(f, "%d", &tmp);
            fscanf(f, "%d", &tmp);
            fscanf(f, "%d", &tmp);
            fscanf(f, "%d", &tmp);
            fscanf(f, "%s", str_temp);
            fscanf(f, "%s", str_temp);
            fscanf(f, "%s", str_temp);
            fscanf(f, "%s", str_temp);
            fscanf(f, "%s", str_temp);

            for(int i=0; i<n_recursos; i++)
            {
                fscanf(f, "%s", &str_temp);
                fscanf(f, "%s", &str_temp);
            }

            fscanf(f, "%s", &str_temp);

            for(int i=0; i<n_tarefas+2; i++)
            {
                for(int k=0; k<2; k++)   //pula id job e mode
                {
                    fscanf(f, "%s", &str_temp);
                }
                fscanf(f, "%d", &duracao_tarefas[i]); //duration
                for(int j=0; j<n_recursos; j++)
                {
                    fscanf(f, "%d", &consumo_tarefas[i][j]);
                }
            }

            //RESOURCE AVAILABILITIES

            fscanf(f, "%s", str_temp);
            fscanf(f, "%s", str_temp);
            for(int j=0; j<n_recursos; j++)
            {
                fscanf(f, "%s", &str_temp);
                fscanf(f, "%s", &str_temp);
            }

            for(int i=0; i<n_recursos; i++)
            {
                fscanf(f, "%d", &capacidade_recursos[i]);
            }
        }
    }

    fclose(f);
}

Solucao construtiva()
{

    memset(&vetor_predecessores, 0, sizeof(vetor_predecessores));
    for(int i=0; i<n_tarefas+2; i++)
    {
        vetor_predecessores[0][i] = i;
        vetor_predecessores[1][i] = calcular_predecessores(i);
    }


    //ORDENAR
    ordenarPredecessores();


    int inseridos[n_tarefas+2];
    memset(&inseridos, 0, sizeof(inseridos));

    inseridos[0] = 1;
    inseridos[n_tarefas+1] = 1;


    int solucao[2][MAX_JOBS];
    memset(&solucao, 0, sizeof(solucao));

    int lista[n_tarefas];
    for(int i=0; i<n_tarefas; i++)
    {
        lista[i] = vetor_predecessores[0][i+1];
    }

    int pos = 0;
    int contador = n_tarefas;
    int ultima_posicao_insercao = 1;

    while(contador > 0)
    {
        int id = lista[pos];

        int pode = 1;

        for(int j=0; j<n_tarefas+2; j++)
        {
            if(matriz_sucessores[j][id] == 1 && inseridos[j] == 0)
            {
                pode = 0;
                j = n_tarefas +3;//break
            }
        }

        if(pode == 1)
        {
            solucao[0][ultima_posicao_insercao] = id;
            ultima_posicao_insercao++; //marca pra inserir no final
            //printf("ULTIMA POSICaO INSERCAO -> %d \n\n", ultima_posicao_insercao);
            //printf("POS -> %d ID-> %d\n\n", pos, id);
            for(int k=pos; k<contador-1; k++)
            {
                lista[k] = lista[k+1];
            } // remove da lista

            inseridos[id] = 1;
            contador--; //condicao de parada do while
        }
        else
        {
            pos += 1;
            if(pos == contador)
            {
                pos = 0;
            }
        }
    }

    inseridos[n_tarefas+1] = 1;
    solucao[0][ultima_posicao_insercao] = n_tarefas+1;


    //definir horário de início de cada tarefa

    for(int i=1; i<n_tarefas+2; i++)
    {
        int id = solucao[0][i-1];
        solucao[1][i] = solucao[1][i-1] + duracao_tarefas[id];
    }

    Solucao solucao_result;
    solucao_result.n_recursos = n_recursos;
    solucao_result.n_tarefas = n_tarefas;
    solucao_result.funObj = 0;
    memcpy(&solucao_result.capacidade_recursos, &capacidade_recursos, sizeof(capacidade_recursos));
    memcpy(&solucao_result.consumo_tarefas, &consumo_tarefas, sizeof(consumo_tarefas));
    memcpy(&solucao_result.duracao_tarefas, &duracao_tarefas, sizeof(duracao_tarefas));
    memcpy(&solucao_result.matriz_sucessores, &matriz_sucessores, sizeof(matriz_sucessores));
    memcpy(&solucao_result.solucao, &solucao, sizeof(solucao));

    return solucao_result;
}

int calcular_predecessores(int id)
{
    int soma = 0;
    for(int i=0; i<n_tarefas+2; i++)
    {
        soma += matriz_sucessores[i][id];
    }
    return soma;
}

void ordenarPredecessores()
{
    int houve_trocas = 1;
    for(int j=0; j < n_tarefas + 2; j++)
    {
        {
            for(int i=n_tarefas+1; i > 0; i--)
            {
                if(vetor_predecessores[1][i] < vetor_predecessores[1][i-1])
                {
                    int temp[2][1];
                    temp[0][0] = vetor_predecessores[0][i-1];
                    temp[1][0] = vetor_predecessores[1][i-1];

                    vetor_predecessores[0][i-1] = vetor_predecessores[0][i];
                    vetor_predecessores[1][i-1] = vetor_predecessores[1][i];

                    vetor_predecessores[0][i] = temp[0][0];
                    vetor_predecessores[1][i] = temp[1][0];
                }
            }
        }
    }
}

void ajustar_horarios(Solucao &solucao)
{
    //SOMAR DURACOES
    int duracao_total = 0; //DURACAO TOTAL ========================
    for(int i=0; i< solucao.n_tarefas +2; i++)
    {
        duracao_total += solucao.duracao_tarefas[i];
    } // duracao_total = sum(duracao)


    //DURACAO TOTAL ========================

    int mat_consumo[solucao.n_recursos][duracao_total]; // matriz consumo ======
    memset(&mat_consumo, 0, sizeof(mat_consumo));

    // FIM MATRIZ CONSUMO ===============


    // HORA TERMINO ===============

    int hor_termino[solucao.n_tarefas+2];
    memset(&hor_termino, 0, sizeof(hor_termino));

    for(int i=0; i<solucao.n_tarefas+1; i++)
    {
        int id = solucao.solucao[0][i];
        hor_termino[id] = solucao.solucao[1][i] + solucao.duracao_tarefas[id];
    }


    // FIM HORA TERMINO ------------------------

    for(int i=1; i<solucao.n_tarefas+1; i++)   //AJUSTE DE HORARIOS ===========================
    {

        int id1 = solucao.solucao[0][i];
        int hor_ini = 0;

        for(int j=0; j<solucao.n_tarefas+2; j++)
        {
            int id2 = solucao.solucao[0][j];

            if(solucao.matriz_sucessores[id2][id1] == 1)
            {
                hor_ini = max(hor_ini, hor_termino[id2]);
            }
        }

        int sem_recurso = 1;

        while(sem_recurso == 1)
        {
            sem_recurso = 0;

            for(int r = 0; r < solucao.n_recursos; r++)
            {
                if(solucao.consumo_tarefas[id1][r] != 0)
                {
                    int flag = 1;

                    for(int t=hor_ini; t < (hor_ini + solucao.duracao_tarefas[id1]); t++)
                    {
                        if((mat_consumo[r][t] + solucao.consumo_tarefas[id1][r]) > solucao.capacidade_recursos[r])
                        {
                            flag = 0;
                            t = (hor_ini + solucao.duracao_tarefas[id1]) + 10; // break
                        }
                    }
                    if(flag == 0)
                    {
                        hor_ini += 1;
                        sem_recurso = 1;
                        r = solucao.n_recursos + 10; //break
                    }
                }
            }
        }

        solucao.solucao[1][i] = hor_ini;
        hor_termino[id1] = solucao.solucao[1][i] + solucao.duracao_tarefas[id1];
        for(int r=0; r < solucao.n_recursos; r++)
        {

            if(solucao.consumo_tarefas[id1][r] != 0)
            {
                for(int t=solucao.solucao[1][i]; t < hor_termino[id1]; t++)
                {
                    mat_consumo[r][t] += solucao.consumo_tarefas[id1][r];
                }
            }
        }


    }  //FIM AJUSTE DE HORARIOS ===========================


    int max_hor_termino = 0;
    for(int i=0; i<solucao.n_tarefas+1; i++)
    {
        if(hor_termino[i] > max_hor_termino)
        {
            max_hor_termino = hor_termino[i];
        }
    }

    solucao.solucao[1][solucao.n_tarefas+1] = max_hor_termino;
}

void vns(Solucao &s, double &tempo_melhor, double &tempo_total, double tempo_limite)
{
    int v;
    clock_t hi, hf;
    Solucao s_vizinha;

    Solucao melhor;

    hi = clock();
    s = construtiva();
    ajustar_horarios(s);
    calcular_FO(s);
    hf = clock();
    tempo_melhor = ((double)(hf - hi))/CLOCKS_PER_SEC;

    clonarSolucao(s, melhor);

    tempo_total = tempo_melhor;

    while (tempo_total < tempo_limite)
    {
        v = 1;
        while (v <= 2)
        {
            memcpy(&s_vizinha, &s, sizeof(s));

            /* Cada mudança no valor de v altera a forma de gerar vizinhos. */

            if(v == 1)
            {
                gerarVizinha(s_vizinha, 1);
            }
            else if(v == 2)
            {
                gerarVizinha(s_vizinha, 2);
            }

            BLMM(s_vizinha);

            if(s_vizinha.funObj < melhor.funObj) {
                clock_t tempo_atual = clock();
                s_vizinha.tempo_melhor = ((double)(tempo_atual - hi))/CLOCKS_PER_SEC;
                clonarSolucao(s_vizinha, melhor);
            }

            /*
             * Se a solução gerada for melhor que a anterior, o valor da FO é atualizado
             * e a geração de vizinhos se mantém a mesma. Caso contrário, a geração de
             * vizinhos é alterada.
             */

            if(s_vizinha.funObj > s.funObj)
            {
                memcpy(&s, &s_vizinha, sizeof(s_vizinha));
                hf = clock();
                tempo_melhor = ((double)(hf - hi))/CLOCKS_PER_SEC;
                v = 1;
            }
            else {
                v++;
            }
        }
        hf = clock();
        tempo_total = ((double)(hf - hi))/CLOCKS_PER_SEC;
    }

    clonarSolucao(melhor, s);
}



int gerarNumero(int inf, int sup)
{
    return(inf + rand() % (sup - inf + 1));
}

void gerarVizinha(Solucao &s, int tipo)
{
    int tarefa_1, tarefa_2, posicao, posicao_1, posicao_2, aux;

    // Escolhe uma tarefa aleatória e a altera pra uma posicao aleatoria
    if(tipo == 1)
    {
        tarefa_1 = rand() % s.n_tarefas;

        posicao = rand() % s.n_tarefas;
        while(posicao == tarefa_1)
        {
            posicao = rand() % s.n_tarefas;
        }

        int viavel = verificar_viabilidade_sucessores(s, tarefa_1, posicao);

        if(viavel == 1)
        {
            printf("VIAVEL\n\n");
        }
        else
        {
            printf("INVIAVEL\n\n");
        }
    }

    // Escolhe duas tarefas aleatorias e as troca de posicao
    if(tipo == 2)
    {
        tarefa_1 = rand() % s.n_tarefas; //escolhe tarefa 1
        while(tarefa_1 == 0 || tarefa_1 == s.n_tarefas+2 ) {
            tarefa_1 = rand() % s.n_tarefas; //escolhe tarefa 1
        }

        tarefa_2 = rand() % s.n_tarefas; //escolhe tarefa 2
        while(tarefa_2 == tarefa_1 || tarefa_2 == 0 || tarefa_2 == s.n_tarefas+2)
        {
            tarefa_2 = rand() % s.n_tarefas; //se escolher a mesma, continua ate escolher diferentes
        }

        posicao_1 = pegar_posicao_tarefa(s, tarefa_1);
        posicao_2 = pegar_posicao_tarefa(s, tarefa_2);

        int viavel = -1;

        viavel = verificar_viabilidade_sucessores(s, tarefa_1, posicao_2);
        viavel = verificar_viabilidade_sucessores(s, tarefa_2, posicao_1);


        if(viavel == 1)
        {
            printf("VIAVEL\n\n");
        }
        else
        {
            printf("INVIAVEL\n\n");
        }
    }
}

int pegar_posicao_tarefa(Solucao &s, int tarefa)
{
    int position = 0;
    for(int i = 0 ; i < s.n_tarefas+2; i++)
    {
        if(s.solucao[0][i] == tarefa)
        {
            position = i;
        }
    }
    return position;
}

int verificar_viabilidade_sucessores(Solucao &solucao, int tarefa, int posicao_troca)
{

    int posicao_atual = pegar_posicao_tarefa(solucao, tarefa);

    Solucao s;
    clonarSolucao(solucao, s);

    int aux[2];
    aux[0] = -1;
    aux[1] = -1;


    int sucessores[MAX_JOBS];
    memset(&sucessores, -1, sizeof(sucessores));

    int qnt_sucessores = 0;
    // VERIFICAR QUAIS SAO OS SUCESSORES

    for(int i=0; i< s.n_tarefas + 2; i++)
    {
        if(s.matriz_sucessores[tarefa][i] == 1)
        {
            sucessores[qnt_sucessores] = i;
            qnt_sucessores++;
        }
    }

    if(posicao_atual < posicao_troca)
    {
        // VERIFICAR SE TODOS OS SUCESSORES ESTAO DEPOIS DA POSICAO DE TROCA
        int suc = 0;
        int qnt_depois = 0;
        while(sucessores[suc] != -1)
        {
            for(int i = posicao_troca + 1; i < s.n_tarefas+2; i++)
            {
                if(s.solucao[0][i] == sucessores[suc])
                {
                    qnt_depois++;
                }
            }
            suc++;
        }

        if(qnt_depois == qnt_sucessores)   // VALIDA! PODE INSERIR NA POSICAO
        {
            aux[0] = s.solucao[0][posicao_atual];// TAREFA
            aux[1] = s.solucao[1][posicao_atual];// HORA INICIO

            for(int i=tarefa; i<posicao_troca; i++)
            {
                s.solucao[0][i] = s.solucao[0][i+1];
                s.solucao[1][i] = s.solucao[1][i+1];
            }

            s.solucao[0][posicao_troca] = aux[0];
            s.solucao[1][posicao_troca] = aux[1];

            ajustar_horarios(s);

            clonarSolucao(s, solucao);
            return 1;
        }
    } else {

        aux[0] = s.solucao[0][posicao_atual];// TAREFA
        aux[1] = s.solucao[1][posicao_atual];// HORA INICIO

        for(int i = posicao_atual; i > posicao_troca; i--)
        {
            s.solucao[0][i] = s.solucao[0][i-1];
            s.solucao[1][i] = s.solucao[1][i-1];
        }

        s.solucao[0][posicao_troca] = aux[0];
        s.solucao[1][posicao_troca] = aux[1];

        ajustar_horarios(s);

        clonarSolucao(s, solucao);
        return 1;

    }


    return -1;

}

void clonarSolucao(Solucao &s1, Solucao &s2)
{
    memcpy(&s2, &s1, sizeof (s1));
}

int calcular_FO(Solucao &solucao)
{
    int makespan = 0;

    // MAKESPAN
    for(int i=0; i< solucao.n_tarefas + 2; i++)
    {
        int id = solucao.solucao[0][i];
        int x = solucao.solucao[1][i];
        makespan = max(makespan, x+solucao.duracao_tarefas[id]);
    }

    // VIOLACOES DE PRECEDENCIA
    int vio_pre = 0;
    for(int i=0; i<solucao.n_tarefas+2; i++)
    {
        int id_1 = solucao.solucao[0][i];
        int x_ini = solucao.solucao[1][i];
        int x_fin = x_ini + solucao.duracao_tarefas[id_1];

        for(int j=0; j < solucao.n_tarefas+2; j++)
        {
            if(j != i)
            {
                int id_2 = solucao.solucao[0][j];
                if(solucao.matriz_sucessores[id_1][id_2] == 1 && solucao.solucao[1][j] < x_fin)
                {
                    vio_pre += x_fin - solucao.solucao[1][j];
                }
            }
        }
    }


    // VIOLACOES DE RECURSOS
    int duracao_total = 0;
    for(int i=0; i< solucao.n_tarefas +2; i++)
    {
        duracao_total += solucao.duracao_tarefas[i];
    }
    int mat_consumo[solucao.n_recursos][duracao_total];
    memset(&mat_consumo, 0, sizeof(mat_consumo));

    for(int i=0; i < solucao.n_tarefas+2; i++)
    {
        int id = solucao.solucao[0][i];
        int x_ini = solucao.solucao[1][i];
        int x_fin = x_ini + solucao.duracao_tarefas[id];

        for(int r=0; r < solucao.n_recursos; r++)
        {
            if(solucao.consumo_tarefas[id][r] != 0)
            {
                for(int t=x_ini; t < x_fin; t++)
                {
                    mat_consumo[r][t] += solucao.consumo_tarefas[id][r];
                }
            }
        }
    }

    int vio_rec = 0;
    for(int r = 0; r < solucao.n_recursos; r++)
    {
        int vet_vio[duracao_total];
        memset(&vet_vio, 0, sizeof(vet_vio));
        int sum = 0;
        for(int j=0; j<duracao_total; j++)
        {
            vet_vio[j] = max(0, mat_consumo[r][j] - solucao.capacidade_recursos[r]);
            sum += vet_vio[j];
        }
        vio_rec += sum;
    }

    // FO

    int fo = makespan + 10*vio_pre + 10*vio_rec;

    solucao.funObj = fo;

    return fo;
}


void BLMM(Solucao &s)
{
	Solucao melhor_solucao;
	clonarSolucao(s,melhor_solucao);

	Solucao s_temp;

	int indice_aux;
	int tarefa_posicao_aux;

	int i = 1;
	while(i < s.n_tarefas + 1) {
        int posicao_1 = pegar_posicao_tarefa(s, i);

		for(int j = 1; j < s.n_tarefas + 1; j++) {
			if(i != j )
			{
			    clonarSolucao(s, s_temp);

			    int viabilidade = verificar_viabilidade_sucessores(s_temp, i, j);

			    calcular_FO(s_temp);


			    printf("TEMP FO -> %d  |  Melhor FO -> %d \n\n", s_temp.funObj, melhor_solucao.funObj );

				if(viabilidade != -1 && s_temp.funObj < melhor_solucao.funObj){
                    printf("CHEGUEI AQUI\n\n");
					printf("\n------------------------------\n");
					printf("Melhora de FO - %d\n", s_temp.funObj);
					printf("Troca: Tarefa %d | Posicao original: %d | Posicao destino: %d\n", i, s.solucao[0][posicao_1], j);
					printf("Valor da FO: de %d para %d",melhor_solucao.funObj,s_temp.funObj);
					clonarSolucao(s_temp, melhor_solucao);
				}
			}
		}
		i++;
	}

	calcular_FO(melhor_solucao);
	clonarSolucao(melhor_solucao, s);
}
