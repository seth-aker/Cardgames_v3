BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, highscore CASCADE;

DROP TABLE IF EXISTS bj_highscore CASCADE;


CREATE TABLE bj_highscore (
  highscore_id serial,
  user_id varchar(3) NOT NULL,
  ending_money money NOT NULL,
  date_created timestamp,

    CONSTRAINT PK_highscore PRIMARY KEY (highscore_id)

);

COMMIT;



