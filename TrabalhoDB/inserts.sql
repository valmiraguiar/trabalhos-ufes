/*------------------------ Endereço -----------------------------*/

insert into
    endereco(
        logradouro,
        cep,
        numero,
        bairro
    )
values
    ('Avenida São João', '13216000', '57', 'Vila Joana'),
    ('Avenida Desembargador Moreira', '60170001', '12', 'Aldeota'),
    ('Avenida Tocantins', '75802095', '17', 'Vila Jardim Rio Claro'),
    ('Travessa Antônio Ferreira', '68700216', '42', 'Igrejinha'),
    ('Rua Barão de Vitória', '09961660', '34', 'Casa Grande')
;

/*------------------------ Pessoa -----------------------------*/

insert into
    pessoa(
        cpf,
        telefone,
        nome,
        data_nascimento,
        sexo,
        id_endereco
    )
values
    ('95186832120', '66986623200', 'Amanda Isabela Carolina Peixoto', '1989.10.09', 'F', 1),
    ('97453795141', '66993473696', 'Pedro Renato Rodrigues', '1989.01.25', 'M', 2),
    ('60385121105', '66998954151', 'Isabelle Eduarda Lúcia Santos', '1990.10.15', 'F', 3),
    ('23313954111', '27987219176', 'Brenda Eduarda Lara Farias', '2000.01.27', 'F', 4),
    ('40622723189', '21994468596', 'Thiago Raimundo Henrique Caldeira', '1987.01.07', 'M', 5),
    
    /*-------------------------- Pessoa Funcionário --------------------------------*/
    /*----------------------------  Pessoa medico  ------------------------------------*/
    
    ('55354674727', '28981742556', 'Augusto Arthur Silva', '1993.05.05', 'M', 2),
    ('55312374727', '28981583564', 'José Nascimento Moraes', '1997.02.05', 'M', 2),
    ('48469391623', '34991995126', 'Vitor Enzo Gonçalves', '1994.07.22', 'M', 2),
    ('55312374727', '34989169942', 'Lorena Analu da Luz', '1994.11.13', 'F', 3),
    ('44484450623', '34992558012', 'Evelyn Lorena Dias', '1995.10.21', 'F', 4),
    
    /*-------------------------- Pessoa Recepcionista --------------------------------*/
    ('59267054600', '34994431065', 'Carla Louise Corte Real', '1992.09.16', 'F', 5),
    ('55008205676', '34988569066', 'Jéssica Andrea Aparecida da Rocha', '1998.06.07', 'F', 4),


    /*-------------------------- Pessoa Enfermeiro --------------------------------*/
    ('28313914661', '34998281826', 'Gustavo Heitor Lopes', '1991.06.12', 'M', 3),
    ('34252345600', '34984997628', 'Milena Carla Baptista', '1994.05.12', 'F', 2)
;

/*-------------------------------- Especialidade ----------------------------*/

insert into
    especialidade(
        descricao
    )
values
    ('Cirurgião Geral'),
    ('Pediatria'),
    ('Infectologista')
;

/*------------------ Ubs ---------------------------*/

insert into
    ubs(
        nome,
        id_endereco
    )
values
    ('Nossa Senhora da Penha', 3),
    ('UBS Coutinho', 2)
;

/*------------- Paciente ---------------*/

insert into
    paciente(
        id_paciente,
        carta_sus
    )
values
    (1, '799417848130002'),
    (2, '111700013000018'),
    (3, '136722197620003'),
    (4, '275092584770002'),
    (5, '708914082320018')
;

/* --------------- Cadastro UBS ---------------*/

insert into
    cadastroubs(
        id_paciente,
        id_ubs
    )
values
    (1, 1),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 1)
;

/*------------------  Funcionário ---------------------------*/

insert into
    funcionario(
        id_funcionario,
        id_ubs
    )
values
    (6, 1),
    (7, 1),
    (8, 2),
    (9, 2),
    (10, 1),
    (11, 1),
    (12, 2),
    (13, 1),
    (14, 2)
;

/*----------- Medico Especialista --------------*/

insert into
    medicoespecialista(
        id_medicoespecialista,
        id_especialidade
    )
values
    (6, 2),
    (7, 1),
    (8, 3),
    (9, 2),
    (10, 1)
;

/*----------- Recepcionista --------------*/

insert into
    recepcionista(
        id_recepcionista
    )
values
    (11),
    (12)
;

/* --------------- Agendamento -------------*/

insert into
    agendamento(
        data_agendamento,
        id_recepcionista,
        id_paciente
    )
values
    ('2021.08.12', 11, 1),
    ('2021.08.12', 11, 2),
    ('2020.10.22', 11, 1),
    ('2021.02.20', 11, 3),
    ('2021.02.20', 11, 3),
    ('2019.07.23', 11, 4),
    ('2021.10.15', 11, 5),
    ('2021.10.15', 11, 5)
;

/* ------------------- Consulta -----------------*/

insert into
    consulta(
        id_medicoespecialista,
        id_agendamento,
        data_consulta,
        prioritario,
        descricao
    )
values
    (7, 3, '2020.11.22', true, null),
    (9, 1, '2021.09.12', false, null),
    (8, 2, '2021.09.12', false, null),
    (8, 5, '2021.03.20', false, null),
    (6, 4, '2021.03.20', false, null),
    (7, 6, '2019.08.23', false, null),
    (7, 8, '2021.11.15', false, null),
    (8, 7, '2021.11.15', false, null)
;

insert into
    enfermeiro (
        id_enfermeiro
    )
values
    (13),
    (14)
;

insert into
    coleta (
        id_enfermeiro,
        id_paciente,
        data_coleta
    )
values
    (13, 1, '2021.12.06')
;

insert into
    examelaboratorial (
        id_medicoespecialista,
        id_coleta,
        laudo,
        observacoes,
        data_conclusao
    )
values
    (7, 1, null, null, null)
;
