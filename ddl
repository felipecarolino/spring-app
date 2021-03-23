use spring;

create table usr_usuario (
usr_id bigint unsigned not null auto_increment,
usr_nome varchar(20) not null,
usr_email varchar(100) not null,
usr_senha varchar(100) not null,
primary key (usr_id),
unique key uni_usuario_nome (usr_nome),
unique key uni_usuario_email (usr_email)
);

create table aut_autorizacao (
aut_id bigint unsigned not null auto_increment,
aut_nome varchar(20) not null,
primary key (aut_id),
unique key uni_aut_nome (aut_nome)
);

create table uau_usuario_autorizacao (
usr_id bigint unsigned not null,
aut_id bigint unsigned not null,
primary key (usr_id, aut_id),
foreign key uau_usr_fk (usr_id) references usr_usuario (usr_id) on delete restrict on update cascade,
foreign key uau_aut_fk (aut_id) references aut_autorizacao (aut_id) on delete restrict on update cascade
);

create table est_estrela (
est_id bigint unsigned not null auto_increment,
est_nome varchar(100) not null,
est_dec varchar(12) not null,
est_ra varchar(11) not null,
est_tipo varchar(10) not null,
est_data_hora datetime not null,
usr_descobridor_id bigint unsigned not null,
primary key (est_id),
foreign key est_usr_fk (usr_descobridor_id) references usr_usuario (usr_id) on delete restrict on update cascade,
unique key uni_est_nome (est_nome)
);


insert into usr_usuario (usr_nome, usr_email, usr_senha) values ('felipe', 'felipe@mail.com', 'pass');

insert into aut_autorizacao (aut_nome) values ('ROLE_ADMIN');

insert into uau_usuario_autorizacao values (1, 1);

insert into est_estrela (est_nome, est_dec, est_ra, est_tipo, est_data_hora, usr_descobridor_id) values ('AM Her', '+49 52 04.75', '18 16 13.25', 'CV', CURRENT_TIMESTAMP, 1)
