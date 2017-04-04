/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017-04-05 01:51:26                          */
/*==============================================================*/


drop table if exists Administrator;

drop table if exists Pliki;

drop table if exists Reklama;

/*==============================================================*/
/* Table: Administrator                                         */
/*==============================================================*/
create table Administrator
(
   Admin_ID             int not null,
   Admin_Nazwa          varchar(150) not null,
   primary key (Admin_ID)
);

/*==============================================================*/
/* Table: Pliki                                                 */
/*==============================================================*/
create table Pliki
(
   Plik_ID              int not null,
   Rek_ID               int not null,
   Plik_sciezka         varchar(1024),
   primary key (Plik_ID)
);

/*==============================================================*/
/* Table: Reklama                                               */
/*==============================================================*/
create table Reklama
(
   Rek_ID               int not null,
   Rek_Nazwa            varchar(1024) not null,
   Rek_Typ              int not null,
   Rek_CzyAktywna       bool,
   Rek_IloscWyswietlen  int,
   primary key (Rek_ID)
);

alter table Pliki add constraint FK_zawiera foreign key (Rek_ID)
      references Reklama (Rek_ID) on delete restrict on update restrict;

