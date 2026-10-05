CREATE DATABASE Ligue;
CREATE TABLE Employés (
  IdEmployé int PRIMARY KEY,
  NomEmployé Varchar (255),
  PrénomEmployé Varchar (255),
  MailEmployé Varchar (255),
  MdpEmployé Varchar (255),
  RoleEmployé Varchar (255),
);

CREATE TABLE Ligue (
  IdLigue int PRIMARY KEY,
  NomLigue Varchar(255),
  );
