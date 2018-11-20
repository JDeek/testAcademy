START TRANSACTION ;
  UPDATE table_name SET field_1 = NULL WHERE  id= 5;
  DELETE FROM other_table WHERE id=17;
ROLLBACK ;

