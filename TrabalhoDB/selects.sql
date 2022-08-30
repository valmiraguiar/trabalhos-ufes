select * from endereco;

select * from ubs;

select * from pessoa;

select * from paciente;

select * from cadastroubs;

select * from funcionario;

select * from enfermeiro;

select * from recepcionista;

select * from especialidade;

select * from medicoespecialista;

select * from agendamento;

select * from consulta;

select * from coleta;

select * from examelaboratorial;

select
    ps.id_pessoa as id_pessoa,
    ps.nome as nomepessoa,
    ps.data_nascimento as datanascimento,
    ps.sexo as sexo,
    cs.id_consulta as id_consulta,
    ag.data_agendamento as dataconsulta,
    pss.nome as nomemedico,
    esp.descricao as especialidade,
    ag.data_agendamento as dataagendamento
from
    pessoa ps
inner join
    paciente pc
on
    ps.id_pessoa = pc.id_paciente
inner join
    agendamento ag
on
    pc.id_paciente = ag.id_paciente
inner join
    consulta cs
on
    ag.id_agendamento = cs.id_agendamento
inner join
    medicoespecialista mde
on
    cs.id_medicoespecialista  = mde.id_medicoespecialista
inner join
    funcionario fc
on
    mde.id_medicoespecialista = fc.id_funcionario
inner join
    especialidade esp
on
    mde.id_especialidade = esp.id_especialidade
inner join
    pessoa pss
on
    fc.id_funcionario = pss.id_pessoa  
-- where
--     pc.carta_sus = ''
;

select
    e.*
from
    endereco as e
where
    logradouro = ''
    or cep = '09961660'
    or numero = ''
    or bairro = ''
;

select
    mde.id_medicoespecialista as id_medico,
    ps.nome as nome,
    esp.descricao as especialidade
from
    especialidade as esp
inner join
    medicoespecialista as mde
on
    mde.id_especialidade = esp.id_especialidade
inner join
    funcionario as fc
on
    mde.id_medicoespecialista = fc.id_funcionario
inner join
    pessoa as ps
on
    fc.id_funcionario = ps.id_pessoa
;

SELECT
    p.*
FROM
    pessoa as p
WHERE
    nome = '' 
    or cpf = '' 
    or telefone = '' 
    or data_nascimento = '1997.02.16' 
    or id_endereco = '1'
;

select
    ps.id_pessoa,
    ps.nome,
    ps.sexo,
    ps.data_nascimento
from
    pessoa as ps
inner join
    paciente as pc
on
    ps.id_pessoa = pc.id_paciente
where 
    pc.carta_sus = '111700013000018'
;
