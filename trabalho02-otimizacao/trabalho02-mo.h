#ifndef TRABALHO02-MO_H_INCLUDED
#define TRABALHO02-MO_H_INCLUDED


#define MAX_JOBS 140
#define MAX_RESOURCES 10
#define MAX_TIME 1000

#include <string>

//DADOS DE ENTRADA
int n_recursos;
int n_tarefas;

int matriz_sucessores[MAX_JOBS][MAX_JOBS];
int duracao_tarefas[MAX_JOBS];
int consumo_tarefas[MAX_JOBS][MAX_RESOURCES];
int capacidade_recursos[MAX_RESOURCES];
int vetor_predecessores[2][MAX_JOBS];
double tempo_melhor;

typedef struct tSolucao
{
    int n_recursos;
    int n_tarefas;
    int funObj;
    int solucao[2][MAX_JOBS];
    int matriz_sucessores[MAX_JOBS][MAX_JOBS];
    int duracao_tarefas[MAX_JOBS];
    int consumo_tarefas[MAX_JOBS][MAX_RESOURCES];
    int capacidade_recursos[MAX_RESOURCES];
    double tempo_melhor;
} Solucao;


//MÉTODOS
void lerDados(std::string arq);
void printarTUDO();
Solucao construtiva();
int calcular_predecessores(int id);
void ordenarPredecessores();
void ajustar_horarios(Solucao &solucao);
int calcular_FO(Solucao &solucao);
int verificar_viabilidade_sucessores(Solucao &s, int tarefa, int posicao_troca);
void gerarVizinha(Solucao &s, int qtd);
void vns(Solucao &s, double &tempo_melhor, double &tempo_total, double tempo_limite);
void clonarSolucao(Solucao &s1, Solucao &s2);
int pegar_posicao_tarefa(Solucao &s, int tarefa);
void BLMM(Solucao &s);
#endif // TRABALHO02-MO_H_INCLUDED
