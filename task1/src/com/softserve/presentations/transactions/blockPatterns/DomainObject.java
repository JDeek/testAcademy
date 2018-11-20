package com.softserve.presentations.transactions.blockPatterns;

/**
 * Created by ${JDEEK} on ${11.11.2018}.
 */
public class DomainObject {
    private Integer id;
    private Integer version;
    private Object data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
