create table city (
  id INT NOT NULL auto_increment PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  state_id INT NOT NULL,

  CONSTRAINT city_fk_state FOREIGN KEY (state_id) REFERENCES state (id)
);
