create table endereco (
	id_endereco serial primary key,
    logradouro varchar(100),
    cep char(8),
    numero varchar(4),
    bairro varchar(50)
);

create table ubs (
	id_ubs serial primary key,
    nome varchar(50),
    id_endereco int,
    foreign key (id_endereco) references endereco(id_endereco)
);

create table pessoa (
	id_pessoa serial primary key,
    cpf char(11),
    telefone char(11),
    nome varchar(50),
    data_nascimento date,
    sexo char(1),
    id_endereco int,
    foreign key (id_endereco) references endereco(id_endereco)
);

create table paciente (
	id_paciente int primary key,
    carta_sus varchar(50),
    foreign key (id_paciente) references pessoa(id_pessoa)
);

create table cadastroubs (
	id_cadastro_ubs serial primary key,
    id_paciente int,
    id_ubs int,
    foreign key (id_paciente) references paciente(id_paciente),
    foreign key (id_ubs) references ubs(id_ubs)
);

create table funcionario (
	id_funcionario int primary key,
    id_ubs int,
    foreign key (id_funcionario) references pessoa(id_pessoa),
    foreign key (id_ubs) references ubs(id_ubs)
);

create table enfermeiro (
	id_enfermeiro int primary key,
    foreign key (id_enfermeiro) references funcionario(id_funcionario)
);

create table recepcionista (
	id_recepcionista int primary key,
    foreign key (id_recepcionista) references funcionario(id_funcionario)
);

create table especialidade (
	id_especialidade serial primary key,
    descricao varchar(50)
);

create table medicoespecialista (
	id_medicoespecialista int primary key,
    id_especialidade int,
    foreign key (id_especialidade) references especialidade(id_especialidade)
);

create table agendamento (
	id_agendamento serial primary key,
    id_recepcionista int,
    id_paciente int,
    data_agendamento date,
    foreign key (id_recepcionista) references recepcionista(id_recepcionista),
    foreign key (id_paciente) references paciente(id_paciente)
);

create table consulta (
	id_consulta serial primary key,
    id_medicoespecialista int,
    id_agendamento int,
    data_consulta date,
    prioritario boolean,
    descricao varchar(150),
    foreign key (id_medicoespecialista) references medicoespecialista(id_medicoespecialista),
    foreign key (id_agendamento) references agendamento(id_agendamento)
);

create table coleta (
	id_coleta serial primary key,
    id_paciente int,
    id_enfermeiro int,
    data_coleta date,
    foreign key (id_enfermeiro) references enfermeiro(id_enfermeiro),
    foreign key (id_paciente) references paciente(id_paciente)
);

create table examelaboratorial (
	id_examelaboratorial serial primary key,
    id_medicoespecialista int,
    id_coleta int,
    laudo varchar(200),
    observacoes varchar(200),
    data_conclusao date,
    foreign key (id_coleta) references coleta(id_coleta),
    foreign key (id_medicoespecialista) references medicoespecialista(id_medicoespecialista)
);
