create table Endereco (
	id_endereco SERIAL primary key,
    logradouro varchar(100),
    cep char(8),
    numero varchar(4),
    bairro varchar(50)
);

create table Ubs (
	id_ubs SERIAL primary key,
    nome varchar(50),
    id_endereco int,
    foreign key (id_endereco) references Endereco(id_endereco)
);

create table Pessoa (
	id_pessoa SERIAL primary key ,
    cpf char(11),
    telefone char(11),
    nome varchar(50),
    data_nascimento char(10),
    sexo char(1),
    id_endereco int,
    foreign key (id_endereco) references Endereco(id_endereco)
);

create table Paciente (
	id_paciente int primary key,
    carta_sus varchar(50),
    foreign key (id_paciente) references Pessoa(id_pessoa)
);

create table CadastroUbs (
	id_cadastro_ubs SERIAL primary key,
    id_paciente int,
    id_ubs int,
    foreign key (id_paciente) references Paciente(id_paciente),
    foreign key (id_ubs) references Ubs(id_ubs)
);

create table Funcionario (
	id_funcionario int primary key,
    id_ubs int,
    foreign key (id_funcionario) references Pessoa(id_pessoa),
    foreign key (id_ubs) references Ubs(id_ubs)
);

create table Enfermeiro (
	id_enfermeiro int primary key,
    foreign key (id_enfermeiro) references Funcionario(id_funcionario)
);

create table Recepcionista (
	id_recepcionista int primary key,
    foreign key (id_recepcionista) references Funcionario(id_funcionario)
);

create table Especialidade (
	id_especialidade SERIAL primary key,
    descricao varchar(50)
);

create table MedicoEspecialista (
	id_medicoespecialista int primary key,
    id_especialidade int,
    foreign key (id_especialidade) references Especialidade(id_especialidade)
);

create table Consulta (
	id_consulta SERIAL primary key,
    id_medicoespecialista int,
    foreign key (id_medicoespecialista) references MedicoEspecialista(id_medicoespecialista)
);

create table Agendamento (
	id_agendamento SERIAL primary key,
    data_consulta char(10),
    data_agendamento char(10),
    id_recepcionista int,
    id_paciente int,
    id_consulta int,
    foreign key (id_recepcionista) references Recepcionista(id_recepcionista),
    foreign key (id_paciente) references Paciente(id_paciente),
    foreign key (id_consulta) references Consulta(id_consulta)
);

create table Coleta (
	id_coleta SERIAL primary key,
    data_coleta char(10),
    id_paciente int,
    id_enfermeiro int,
    foreign key (id_enfermeiro) references Enfermeiro(id_enfermeiro),
    foreign key (id_paciente) references Paciente(id_paciente)
);

create table ExameLaboratorial (
	id_examelaboratorial SERIAL primary key,
    laudo varchar(200),
    observacoes varchar(200),
    data_conclusao char(10),
    id_coleta int,
    id_medicoespecialista int,
    foreign key (id_coleta) references Coleta(id_coleta),
    foreign key (id_medicoespecialista) references MedicoEspecialista(id_medicoespecialista)
);


alter table consulta add column id_agendamento int;
alter table consulta add constraint  agendamento_ibfk_3 foreign key(id_agendamento) references agendamento(id_agendamento);

alter table agendamento drop column id_consulta;