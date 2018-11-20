package com.softserve.presentations.transactions.blockPatterns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ${JDEEK} on ${11.11.2018}.
 */
public class UnitOfWork {

    private List<DomainObject> newObjects;
    private List<DomainObject> updatedObjects;
    private List<DomainObject> deletedObjects;

    /**
     * Создать объект
     * @return вернуть вновь созданный объект
     */
    public DomainObject create() {
        DomainObject domainObject = new DomainObject();
        newObjects.add(domainObject);
        return domainObject;
    }

    /**
     * Пометить объект как измененный
     * @param domainObject измененный объект
     */
    public void update(DomainObject domainObject) {
        updatedObjects.add(domainObject);
    }

    /**
     * Пометить объект как удалённый
     * @param domainObject удалённый объект
     */
    public void remove(DomainObject domainObject) {
        deletedObjects.add(domainObject);
    }

    /**
     * Выполнить изменения в базе данных
     */
    public void commit() throws SQLException{
        //выполняет SQL запросы на вставку данных INSERT
    //    insert(newObjects);
        //выполняет SQL запросы на обновление данных UPDATE
        udpate(updatedObjects);
        //выполняет SQL запросы на удаление данных DELETE
        //delete(deletedObjects);
    }

    private void udpate(List<DomainObject> updatedObjects) throws SQLException {  //OPTIMISTIC PATTERN
        Connection connection = null;
        PreparedStatement ps =
                connection.prepareStatement(
                        "update domain_objects set data = ?, version = version + 1 " +
                                "where id = ? and version = ?"
                );

        for (DomainObject domainObject: updatedObjects) {
            ps.setObject(1, domainObject.getData());
            ps.setInt(2, domainObject.getId());
            ps.setInt(3, domainObject.getVersion());
            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Конфликт версий");
            }
        }

    }



//реализация методов insert, update, delete и прочих

}
