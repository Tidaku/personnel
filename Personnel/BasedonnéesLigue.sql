CREATE DATABASE IF NOT EXISTS ligue CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ligue;

CREATE TABLE employe (
  id_employe   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nom          VARCHAR(100) NOT NULL,
  prenom       VARCHAR(100) NOT NULL,
  mail         VARCHAR(255) NOT NULL,
  password     VARCHAR(255) NOT NULL,
  date_arrivee DATE NULL,
  date_depart  DATE NULL,

  id_ligue     INT UNSIGNED NULL,
  CONSTRAINT ck_employe_dates CHECK (
    date_depart IS NULL
    OR (date_arrivee IS NOT NULL AND date_depart >= date_arrivee)
  )
) ENGINE=InnoDB;

CREATE TABLE ligue (
  id_ligue         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nom              VARCHAR(100) NOT NULL,

  id_administrateur INT UNSIGNED NOT NULL,
  CONSTRAINT uq_ligue_nom UNIQUE (nom),
  CONSTRAINT fk_ligue_administrateur FOREIGN KEY (id_administrateur)
    REFERENCES employe (id_employe) ON DELETE RESTRICT
) ENGINE=InnoDB;


ALTER TABLE employe
  ADD CONSTRAINT fk_employe_ligue FOREIGN KEY (id_ligue)
    REFERENCES ligue (id_ligue) ON DELETE CASCADE;
