CREATE TABLE "public"."usuario"
(
   login varchar(255) PRIMARY KEY NOT NULL,
   description varchar(255),
   firstname varchar(255),
   lastname varchar(255)
);

insert into usuario (login, description, firstname, lastname) values ('1', 'random user', 'john', 'doe');
insert into usuario (login, description, firstname, lastname) values ('2', 'nice user', 'mary', 'ann');
insert into usuario (login, description, firstname, lastname) values ('3', 'not a user', 'detective', 'chloe');
commit;
