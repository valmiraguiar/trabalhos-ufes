#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <vector>
#include "pmm.hpp"
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
    string instancia = "";

    const int repeticoes = 1000;

    //instancia = "j301_1.sm";
    //instancia = "j601_2.sm";
    //instancia = "j901_3.sm";
    instancia = "j12060_7.sm";

    clock_t hI, hF;
    double tempo;
    int fo;


    Solucao sol;
    lerDados(instancia);

    hI = clock();
    for(int i=0; i<1000; i++){
        sol = heuristicaConstrucaoSequencial();
        fo = calcularFO(sol, true);
    }
    hF = clock();
    tempo = (((double)hF - hI)/CLOCKS_PER_SEC) / 1000;
    printf("\n Tempo SOL e FO 1 vez: %.12f\n ", tempo);


    hI = clock();
    for(int i=0; i<10000; i++){
        sol = heuristicaConstrucaoSequencial();
    }
    hF = clock();
    tempo = (((double) hF - hI)/CLOCKS_PER_SEC) / 1000;
    printf("\n Tempo SOLUCAO 1000x: %.12f\n ", tempo);

    hI = clock();
    for(int i=0; i<10000; i++){
        fo = calcularFO(sol, true); // linear = true, else = false
    }
    hF = clock();
    tempo = (((double) hF - hI)/CLOCKS_PER_SEC) / 1000;
    printf("\n Tempo FO 1000x: %.12f\n ", tempo);

    printf("FO: %d\n", sol.funObj);

    escreverSolucao(sol, false); // only screen = true, else = false

    /*string solucaoTxt = "";
    solucaoTxt = "C:\\Users\\Valmir Aguiar\\Desktop\\solucao.txt"; // path
    Solucao sol2 = lerSolucao(solucaoTxt);
    escreverSolucao(sol2, true);*/

    return 0;
}


void lerDados(string arq)
{
    FILE *f = fopen(arq.c_str(), "r");

    if(f == NULL) {
        printf("ERRO AO ABRIR ARQUIVO!\n");
        return;
    }

    int tmp = 0;
    char str_temp[200];

    memset(&matrizSucessores, 0, sizeof(matrizSucessores));
    memset(&matrizCustoTarefas, 0, sizeof(matrizCustoTarefas));
    memset(&vetDuracaoTarefas, 0, sizeof(vetDuracaoTarefas));
    memset(&vetCapacidadeRecurso, 0, sizeof(vetCapacidadeRecurso));

    numRecursos = 0;
    numTarefas = 0;

    int i=0;

    while(fgetc(f) != EOF){
        fscanf(f, "%s", &str_temp);
        i++;
        if(i == 27) {//scan resources
            fscanf(f, "%d", &numRecursos);
        }
        if(i == 49) {//scan jobs
            fscanf(f, "%d", &numTarefas);
        }
        if(i == 61) {//first job
            int successors = 0; // LE A PRIMEIRA LINHA DOS JOBS
            for(int j = 1; j<3; j++) { //scan successors
                if(j == 2) {
                    fscanf(f, "%d", &successors);
                } else {
                    fscanf(f, "%d", &tmp);
                }
            }
            for(int j=0; j<successors; j++) {
                fscanf(f, "%d", &matrizSucessores[0][j]);
            }
            for(int k=1; k<numTarefas + 1; k++) { //LE A PARTIR DA SEGUNDA LINHA DOS JOBS
                successors = 0;
                for(int j = 0; j<3; j++) { //scan successors
                    if(j == 2) {
                        fscanf(f, "%d", &successors);
                    } else {
                        fscanf(f, "%d", &tmp);
                    }
                }
                for(int j=0; j<successors; j++) {
                    fscanf(f, "%d", &matrizSucessores[k][j]);
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

            for(int i=0; i<numRecursos; i++){
                fscanf(f, "%s", &str_temp);
                fscanf(f, "%s", &str_temp);
            }

            fscanf(f, "%s", &str_temp);

            //PULA O PRIMEIRO-----------

            for(int k=0; k<2; k++) {
                fscanf(f, "%s", &str_temp);
            }
            fscanf(f, "%s", &str_temp);//duracao
            for(int i=0; i<numRecursos; i++) {
                fscanf(f, "%s", &str_temp);
            }

            //FIM PULA O PRIMEIRO-------

            for(int i=1; i<numTarefas+1; i++) {
                for(int k=0; k<2; k++) { //pula id job e mode
                    fscanf(f, "%s", &str_temp);
                }
                fscanf(f, "%d", &vetDuracaoTarefas[i]); //duration
                for(int j=0; j<numRecursos; j++) {
                    fscanf(f, "%d", &matrizCustoTarefas[i-1][j]);
                }
            }


            //RESOURCE AVAILABILITIES

            fscanf(f, "%s", str_temp); //pula a última linha dos pesos dos jobs
            fscanf(f, "%s", str_temp); // e pula o cabecalho da capacidade dos recursos
            fscanf(f, "%s", str_temp);
            for(int j=0; j<numRecursos; j++) {
                fscanf(f, "%s", &str_temp);
            }
            fscanf(f, "%s", str_temp);
            fscanf(f, "%s", str_temp);
            for(int j=0; j<numRecursos; j++) {
                fscanf(f, "%s", &str_temp);
                fscanf(f, "%s", &str_temp);
            }

            for(int i=0; i<numRecursos; i++) {
                fscanf(f, "%d", &vetCapacidadeRecurso[i]);
            }
        }
    }

    fclose(f);
}

void solucaoInicialTarefasEmSequencia(){
    Recurso solucaoInicial;
    int vetExecucao[MAX_TIME];
    int vetInicioTarefa[MAX_JOBS];

    memset(&vetExecucao, -1, sizeof(vetExecucao));

    int lastInsertId = 0;
    for(int j=0; j < numTarefas + 2; j++) {

        int duracaoJob = vetDuracaoTarefas[j];
        lastInsertId += duracaoJob;

        for(int k=lastInsertId-duracaoJob; k < lastInsertId; k++) {
            vetExecucao[k] = j;
        }
    }
}

int calcularFO(Solucao &solucao, const bool linear) {
    int fo = 0;

    if(linear) {
        while(solucao.execucao.execucao[fo][0] != -1) {
            fo++;
        }
        solucao.funObj = fo;
    } else {
        //TO DO
    }
    return fo;
}

Solucao heuristicaConstrucaoSequencial() {
    criarSequenciaSuccessores();

    Solucao solucao;

    memcpy(&solucao.execucao.sequenciaTarefas, &vetSequenciaTarefas, sizeof(vetSequenciaTarefas));

    int vetExecucao[MAX_TIME][MAX_JOBS];
    memset(vetExecucao, -1, sizeof(vetExecucao));

    int insertId = 0;
    for(int j=0; j<numTarefas+2; j++) {
        int tarefa = solucao.execucao.sequenciaTarefas[j];
        int duracao = vetDuracaoTarefas[tarefa-1];
        for(int i=0; i<duracao; i++) {
            vetExecucao[insertId + i][0] = tarefa;
        }
        insertId = insertId + duracao;
    }

    memcpy(&vetExecucaoTarefas, &vetExecucao, sizeof(vetExecucao));

    memcpy(&solucao.execucao.execucao, &vetExecucao, sizeof(vetExecucao));

    verificarInicioTarefas(solucao, true);

    return solucao;
}

/*
    CRIA UMA SEQUENCIA DE TAREFAS USANDO OS
    SUCCESSORES. ADICIONA A PRIMEIRA TAREFA; PEGA OS
    SUCESSORES DELA E ADICIONA NA LISTA; PEGA OS SUCCESSORES DA
    SEGUNDA E OS ADICIONA; E ASSIM POR DIANTE, ATÉ ADICIONAR TODOS OS SUCCESSORES
*/
int criarSequenciaSuccessores() {
    int sequencia[MAX_JOBS];
    memset(&sequencia, -1, sizeof(sequencia));

    sequencia[0] = 1;
    int lastIndex = 1;

    for(int i=0; i < numTarefas; i++) {
        int j = 0;
        while(matrizSucessores[i][j] != 0) {
            sequencia[lastIndex] = matrizSucessores[i][j];
            lastIndex++;
            j++;
        }
    }

    //remover tarefas repitidas

    for(int i=0; i<lastIndex; i++) {
        int value = sequencia[i];
        for(int j=i+1; j<lastIndex+1; j++) {
            if(sequencia[j] == value) {
                sequencia[j] = -2;
            }
        }
    }

    //refazer o vetor
    int sequenciaCerto[MAX_JOBS];
    memset(&sequenciaCerto, -1, sizeof(sequenciaCerto));
    int tmp = 0;
    int indexInsert = 0;
    for(int i=0; i<lastIndex+1; i++) {
        if(sequencia[i] != -2) {
            sequenciaCerto[indexInsert] = sequencia[i];
            indexInsert++;
        }
    }

    //verificar o que falta e adicionar
    int vetVerificacao[numTarefas+2];
    memset(&vetVerificacao, 0, sizeof(vetVerificacao));

    for(int i=0; i<lastIndex+1; i++) {
       for(int j=0; j<numTarefas+2; j++) {
            if(i == sequenciaCerto[j]) {
                vetVerificacao[i] = 1;
            }
       }
    }

    for(int i=1; i<numTarefas + 2; i++) {
        if(vetVerificacao[i] == 0){
            sequenciaCerto[indexInsert] = i;
            vetVerificacao[i] = 1;
            indexInsert++;
        }
    }

    memcpy(&vetSequenciaTarefas, &sequenciaCerto, sizeof(sequenciaCerto));
}


void clonarSolucao(Solucao &s1, Solucao &s2){
	memcpy(&s2, &s1, sizeof (s1));
}

void verificarInicioTarefas(Solucao &solucao, const bool linear) {
    int vetorInicioTarefas[MAX_JOBS];

    int c =0;
    while(solucao.execucao.execucao[c][0] != -1 ) {
        c++;
    }
    int lastTime = c;

    int result = 0;
    for(int i=0; i<numTarefas+2; i++) {
        result += vetDuracaoTarefas[i];
    }

    if(linear) {
        int pos = 0;
        for(int i=0; i < numTarefas + 2; i++) {
            pos = -2;
            for(int j=0; j < lastTime ; j++) {
                if(solucao.execucao.execucao[j][0] == i) {
                    pos = j;
                    vetorInicioTarefas[i] = pos;
                    j = MAX_TIME *2;
                }
            }
        }

        int vetTmpInit[numTarefas+2];
        vetTmpInit[0] = 0;
        for(int k=1; k < numTarefas + 2; k++) {
            vetTmpInit[k] = vetorInicioTarefas[k+1];
        }
        vetTmpInit[numTarefas+1] = vetDuracaoTarefas[numTarefas] + vetorInicioTarefas[numTarefas+1];

        memcpy(&solucao.inicioTarefa, &vetTmpInit, sizeof(vetTmpInit));
    }
}

void escreverSolucao(Solucao &solucao, const bool somenteTela) {
    if(somenteTela) {
        printf("FO: %d\n", solucao.funObj);
        printf("Makespan: %d\n", solucao.funObj);
        printf("------------------\n");
        printf("Job    Start Time\n");
        for(int i=0; i < numTarefas + 2; i++) {
            printf("%d", i);
            printf("         ");
            printf("%d\n", solucao.inicioTarefa[i]);
        }
    } else {

        FILE *result = fopen("solucao.txt", "w");

        if(result == NULL) {
            printf("ERRO AO ABRIR ARQUIVO\n");
            return;
        }


        fprintf(result, "FO: %d\n", solucao.funObj);
        fprintf(result, "Makespan: %d\n", solucao.funObj);
        fprintf(result, "------------------\n");
        fprintf(result, "Job    Start Time\n");
        for(int i=0; i < numTarefas + 2; i++) {
            fprintf(result, "%d", i);
            fprintf(result, "         ");
            fprintf(result, "%d\n", solucao.inicioTarefa[i]);
        }

        printf("FO: %d\n", solucao.funObj);
        printf("Makespan: %d\n", solucao.funObj);
        printf("------------------\n");
        printf("Job    Start Time\n");
        for(int i=0; i < numTarefas + 2; i++) {
            printf("%d", i);
            printf("         ");
            printf("%d\n", solucao.inicioTarefa[i]);
        }

    }
}

Solucao lerSolucao(string arq) {
    FILE *f = fopen(arq.c_str(), "r");
    Solucao sol;

    if(f == NULL) {
        printf("ERRO AO ABRIR ARQUIVO\n");
        return sol;
    }

    char tmp_string[200];
    int tmp;

    int vetInitJob[MAX_JOBS];

    int i = 0;

    fscanf(f, "%s", &tmp_string);
    fscanf(f, "%d", &tmp);
    sol.funObj = tmp;
    fscanf(f, "%s", &tmp_string);
    fscanf(f, "%d", &tmp);
    fscanf(f, "%s", &tmp_string);
    fscanf(f, "%s", &tmp_string);
    fscanf(f, "%s", &tmp_string);
    fscanf(f, "%s", &tmp_string);
    while(fgetc(f) != EOF) {
        fscanf(f, "%d", &tmp);
        fscanf(f, "%d", &tmp);
        vetInitJob[i] = tmp;
        i++;
    }

    int vetJobsCerto[i-1];

    for(int j=0; j < i-1;j++) {
        vetJobsCerto[j] = vetInitJob[j];
    }

    memcpy(&sol.inicioTarefa, &vetJobsCerto, sizeof(vetJobsCerto));

    return sol;
}
