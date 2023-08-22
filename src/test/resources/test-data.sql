BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, highscore CASCADE;

CREATE TABLE users(
  user_id serial,
  username varchar(30) NOT NULL,
  password_hash varchar(100) NOT NULL,
  display_name char(3) NOT NULL,

  CONSTRAINT PK_user_id PRIMARY KEY (user_id)

);

CREATE TABLE highscore (
  highscore_id serial,
  user_id int,
  score numeric(10,2),
  date_created timestamptz NOT NULL DEFAULT NOW(),

    CONSTRAINT PK_highscore PRIMARY KEY (highscore_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

INSERT INTO users(username, password_hash, display_name) VALUES ('USER1', 'PASSWORD', 'USE');
INSERT INTO users(username, password_hash, display_name) VALUES ('TESTER1', 'PASSWORD', 'TES');

INSERT INTO highscore (user_id, score) VALUES (1, 100);
INSERT INTO highscore (user_id, score) VALUES (1, 250);
INSERT INTO highscore (user_id, score) VALUES (2, 200);

COMMIT;

