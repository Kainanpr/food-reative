create table state (
  id INT NOT NULL auto_increment PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

INSERT INTO state (id, name) VALUES
(1, 'Minas Gerais'),
(2, 'São Paulo'),
(3, 'Ceará');
