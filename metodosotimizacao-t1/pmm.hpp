#ifndef PMM_HPP_INCLUDED
#define PMM_HPP_INCLUDED


#define MAX_JOBS 140
#define MAX_RESOURCES 10
#define MAX_TIME 1000

#include <string>

// Dados de entrada
int numRecursos = 0;
int numTarefas = 0;

int matrizSucessores[MAX_JOBS][MAX_JOBS];
int matrizCustoTarefas[MAX_JOBS][MAX_RESOURCES];
int vetDuracaoTarefas[MAX_JOBS];
int vetCapacidadeRecurso[MAX_RESOURCES];
int vetSequenciaTarefas[MAX_JOBS];
int vetExecucaoTarefas[MAX_TIME][MAX_JOBS];
int vetInicioTarefas[MAX_JOBS];

// Métodos
void lerDados(std::string arq);
void solucaoInicialTarefasEmSequencia();
int criarSequenciaSuccessores();



// MATRIZ DE TEMPO (H) X TAREFAS REALIZADAS NAQUELA HORA
// ISSO PARA CADA MÁQUINA
typedef struct tRecurso
{
    int sequenciaTarefas[MAX_JOBS];
    int execucao[MAX_TIME][MAX_JOBS];
} Recurso;

typedef struct tSolucao
{
    int funObj;
    int inicioTarefa[MAX_JOBS];
    Recurso execucao;
} Solucao;

Solucao melhorSolucao;
void clonarSolucao(Solucao &s1, Solucao &s2);
void verificarInicioTarefas(Solucao &solucao, const bool linear);
int calcularFO(Solucao &solucao, const bool linear);
Solucao heuristicaConstrucaoSequencial();
void escreverSolucao(Solucao &solucao, const bool somenteTela);
Solucao lerSolucao(std::string arq);

#endif // PMM_HPP_INCLUDED
