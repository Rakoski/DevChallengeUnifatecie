create database usuarios;

use usuarios;

create table usuario(
    id_usuario BIGINT not null primary key auto_increment,
    email varchar(255) not null unique,
    nome varchar(255),
    sobrenome varchar(255),
    password_hash varchar(255) not null,
    password_salt varchar(255) not null,
    password varchar(255) NULL
);

create table cursos(
       id_curso BIGINT not null primary key auto_increment,
       nome varchar(100) not null,
       duracao_periodo int not null
);

create table cursos_usuario(
       usuario_id BIGINT not null,
       curso_id BIGINT not null,
       nome_status varchar(45) not null,
       foreign key (usuario_id) references usuario(id_usuario),
       foreign key (curso_id) references cursos(id_curso)
);

create table disciplinas(
        id_disciplinas BIGINT not null primary key auto_increment,
        nome_disciplina varchar(255) not null,
        professor varchar(255) not null
);

create table cursos_disciplinas(
       curso_id BIGINT not null,
       disciplinas_id BIGINT not null,
       foreign key (curso_id) references cursos(id_curso),
       foreign key (disciplinas_id) references disciplinas(id_disciplinas)
);

create table usuario_disciplina(
       usuario_id BIGINT not null,
       disciplina_id BIGINT not null,
       nota int,
       status_disciplina varchar(45) not null,
       foreign key (usuario_id) references usuario(id_usuario),
       foreign key (disciplina_id) references disciplinas(id_disciplinas)
);

create table protocolo(
    id_protocolo INT not null primary key auto_increment,
    protocolo_nome varchar(255) not null,
    protocolo_descricao varchar(255) not null,
    status_curso_protocolo varchar(255) not null,
    protocolo_docname varchar(255),
    protocolo_doctype varchar(255)
);

CREATE TABLE protocolo_usuario (
       id_protocolouser INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
       usuario_id INT NOT NULL,
       curso_id INT NOT NULL,
       protocolo_typeid INT NOT NULL,
       protocolo_etapa INT NOT NULL,
       protocolo_setor VARCHAR(255),
       protocolo_status VARCHAR(255),
       protocolo_campo1 VARCHAR(255),
       protocolo_docpath VARCHAR(255),
       protocolo_docreturn VARCHAR(255)
);


INSERT INTO cursos
VALUES (1, 'Análise e desenvolvimento de sistemas', 3),
       (2, 'Ciência da computação', 4),
       (3, 'Educação Física', 3),
       (4, 'Direito', 5),
       (5, 'Medicina', 6),
       (6, 'Agronomia', 5);
