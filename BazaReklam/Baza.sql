/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017-03-28 17:43:55                          */
/*==============================================================*/


drop table if exists Administrator;

drop table if exists Pliki;

drop table if exists Reklama;

drop table if exists Reklamodawca;

drop table if exists U퓓tkownik;

drop table if exists zawiera;

/*==============================================================*/
/* Table: Administrator                                         */
/*==============================================================*/
create table Administrator
(
   User_ID              int not null,
   Admin_ID             int not null,
   Admin_Nazwa          varchar(150) not null,
   primary key (User_ID, Admin_ID)
);

/*==============================================================*/
/* Table: Pliki                                                 */
/*==============================================================*/
create table Pliki
(
   Plik_ID              int not null,
   Plik_똠ie퓃a         varchar(1024),
   primary key (Plik_ID)
);

/*==============================================================*/
/* Table: Reklama                                               */
/*==============================================================*/
create table Reklama
(
   Rek_ID               int not null,
   User_ID              int,
   Rekd_ID              int,
   Rek_Nazwa            varchar(1024) not null,
   Rek_Typ              int not null,
   Rek_CzyAktywna       bool,
   Rek_IloscWyswietlen  int,
   primary key (Rek_ID)
);

/*==============================================================*/
/* Table: Reklamodawca                                          */
/*==============================================================*/
create table Reklamodawca
(
   User_ID              int not null,
   Rekd_ID              int not null,
   Rekd_Nazwa           varchar(150) not null,
   Rekd_Adres           varchar(150),
   Rekd_Telefon         numeric(8,0),
   Rekd_Email           varchar(150),
   primary key (User_ID, Rekd_ID)
);

/*==============================================================*/
/* Table: U퓓tkownik                                            */
/*==============================================================*/
create table U퓓tkownik
(
   User_ID              int not null,
   User_Login           varchar(80),
   User_Pass            varchar(32),
   primary key (User_ID)
);

/*==============================================================*/
/* Table: zawiera                                               */
/*==============================================================*/
create table zawiera
(
   Rek_ID               int not null,
   Plik_ID              int not null,
   primary key (Rek_ID, Plik_ID)
);

alter table Administrator add constraint FK_D1 foreign key (User_ID)
      references U퓓tkownik (User_ID) on delete restrict on update restrict;

alter table Reklama add constraint FK_dostarcza foreign key (User_ID, Rekd_ID)
      references Reklamodawca (User_ID, Rekd_ID) on delete restrict on update restrict;

alter table Reklamodawca add constraint FK_D2 foreign key (User_ID)
      references U퓓tkownik (User_ID) on delete restrict on update restrict;

alter table zawiera add constraint FK_zawiera foreign key (Rek_ID)
      references Reklama (Rek_ID) on delete restrict on update restrict;

alter table zawiera add constraint FK_zawiera2 foreign key (Plik_ID)
      references Pliki (Plik_ID) on delete restrict on update restrict;

