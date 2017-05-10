/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017-05-10 02:59:59                          */
/*==============================================================*/


drop table if exists Administrator;

drop table if exists Pliki;

drop table if exists Reklama;

drop table if exists Reklamodawca;

/*==============================================================*/
/* Table: Administrator                                         */
/*==============================================================*/
create table Administrator
(
   Admin_ID             int not null,
   Admin_Login          varchar(150) not null,
   Admin_Pass           varchar(256),
   primary key (Admin_ID)
);

/*==============================================================*/
/* Table: Pliki                                                 */
/*==============================================================*/
create table Pliki
(
   Plik_ID              int not null,
   Rek_ID               int not null,
   Plik_Œcie¿ka         varchar(1024),
   Plik_Format          varchar(5),
   Plik_Rozdziel        varchar(50),
   primary key (Plik_ID)
);

/*==============================================================*/
/* Table: Reklama                                               */
/*==============================================================*/
create table Reklama
(
   Rek_ID               int not null,
   Rekd_ID              int not null,
   Rek_Nazwa            varchar(1024) not null,
   Rek_Typ              int not null,
   Rek_CzyAktywna       bool,
   Rek_IloscWyswietlen  int,
   Rek_PlanowanaIloscWysw int,
   primary key (Rek_ID)
);

/*==============================================================*/
/* Table: Reklamodawca                                          */
/*==============================================================*/
create table Reklamodawca
(
   Rekd_ID              int not null,
   Rekd_Nazwa           varchar(150) not null,
   Rekd_Adres           varchar(150),
   Rekd_Telefon         numeric(8,0),
   Rekd_Email           varchar(150),
   primary key (Rekd_ID)
);

alter table Pliki add constraint FK_zawiera foreign key (Rek_ID)
      references Reklama (Rek_ID) on delete restrict on update restrict;

alter table Reklama add constraint FK_dostarcza foreign key (Rekd_ID)
      references Reklamodawca (Rekd_ID) on delete restrict on update restrict;

