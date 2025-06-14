CREATE PROCEDURE new_client(IN firstname VARCHAR(50), IN lastname VARCHAR(50), IN datebirth DATE)
    MODIFIES SQL DATA
  BEGIN ATOMIC
    DECLARE temp_id INTEGER;
    INSERT INTO CLIENT VALUES (DEFAULT, firstname, lastname, datebirth);
    SET temp_id = IDENTITY();
  END
