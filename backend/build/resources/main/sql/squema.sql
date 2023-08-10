create database usuarios;

use usuarios;

CREATE TABLE usuario (
       id_usuario BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
       usuario_email VARCHAR(255) NOT NULL UNIQUE,
       usuario_nome VARCHAR(255),
       usuario_sobrenome VARCHAR(255),
       password_hash BLOB NOT NULL,
       password_salt BLOB NOT NULL
) CHARACTER SET utf8mb4;


create table curso(
       id_curso BIGINT not null primary key auto_increment,
       curso_nome varchar(100) not null,
       duracao_periodo_curso int not null
);

create table curso_usuario(
       curso_usuario_id BIGINT not null primary key auto_increment,
       usuario_id BIGINT not null,
       curso_id BIGINT not null,
       nome_status_curso_usuario varchar(45) not null,
       foreign key (usuario_id) references usuario(id_usuario),
       foreign key (curso_id) references curso(id_curso)
);

create table disciplina(
        id_disciplina BIGINT not null primary key auto_increment,
        nome_disciplina varchar(255) not null,
        professor_disciplina varchar(255) not null
);

create table curso_disciplina(
       id_curso_disciplina BIGINT not null primary key auto_increment,
       curso_id BIGINT not null,
       disciplina_id BIGINT not null,
       foreign key (curso_id) references curso(id_curso),
       foreign key (disciplina_id) references disciplina(id_disciplina)
);

create table usuario_disciplina(
       id_usuario_disciplina BIGINT not null primary key auto_increment,
       usuario_id BIGINT not null,
       disciplina_id BIGINT not null,
       nota int,
       status_disciplina varchar(45) not null,
       foreign key (usuario_id) references usuario(id_usuario),
       foreign key (disciplina_id) references disciplina(id_disciplina)
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


INSERT INTO curso
VALUES (1, 'Análise e desenvolvimento de sistemas', 3),
       (2, 'Ciência da computação', 4),
       (3, 'Educação Física', 3),
       (4, 'Direito', 5),
       (5, 'Medicina', 6),
       (6, 'Agronomia', 5);
