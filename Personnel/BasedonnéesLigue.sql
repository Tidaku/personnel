CREATE DATABASE Ligue;
CREATE TABLE Employés (
  IdEmployé int PRIMARY KEY,
  NomEmployé Varchar (255),
  PrénomEmployé Varchar (255),
  MailEmployé Varchar (255),
  MdpEmployé Varchar (255),
  RoleEmployé Varchar (255),

   CONSTRAINT fk_Ligue
    FOREIGN KEY (IdLigue),
    REFERENCES Ligue(IdLigue),
  
);

CREATE TABLE Ligue (
  IdLigue int PRIMARY KEY,
  NomLigue Varchar(255),
  );

CREATE TABLE Utilisateur (
  IdUtilisateur int PRIMARY KEY,
  NomUtilisateur Varchar (255), 
  PrénomUtilisateur Varchar (255), 
  MdpUtilisateur Varchar (255), 
  RoleUtilisateur Varchar(255), 

     CONSTRAINT fk_Ligue
    FOREIGN KEY (IdLigue),
    REFERENCES Ligue(IdLigue),

     CONSTRAINT fk_Employés
    FOREIGN KEY (IdEmployé),
    REFERENCES Employés(IdEmployé),
); 
