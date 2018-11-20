package com.softserve.presentations.transactions.blockPatterns.pessimistic;

/**
 * Created by ${JDEEK} on ${11.11.2018}.
 */
public class DomainObject {
    private Integer id;
    private Boolean blocked;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
