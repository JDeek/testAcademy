package com.softserve.presentations.transactions.blockPatterns.pessimistic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ${JDEEK} on ${11.11.2018}.
 */
public class UnitOfWork {
    //реализация методов, описаны выше
        private Connection connection = null;;
    public void update(List<DomainObject> updatedObjects) throws SQLException {
        PreparedStatement updateStatement =
                connection.prepareStatement(
                        "update domain_objects set data = ?, blocked = false " +
                                "where id = ? and blocked = true"
                );

        for (DomainObject domainObject: updatedObjects) {
            updateStatement.setObject(1, domainObject.getData());
            updateStatement.setInt(2, domainObject.getId());
            updateStatement.executeUpdate();
        }

    }

    public DomainObject get(Integer id) throws SQLException {
        PreparedStatement updateStatement =
                connection.prepareStatement(
                        "update domain_objects set blocked = true " +
                                "where id = ? and blocked = false"
                );

        updateStatement.setInt(1, id);

        if (updateStatement.executeUpdate() == 1) {
            PreparedStatement selectStatement =
                    connection.prepareStatement(
                            "select * from domain_objects where id = ?"
                    );
            selectStatement.setInt(1, id);
            ResultSet result = selectStatement.executeQuery();
            //result содержит необхродимые данные
            //возврашение объекта DomainObject на основе данных result
            return new DomainObject();
        }
        else {
            throw new RuntimeException("Блокировка уже захвачена");
        }
    }
}
