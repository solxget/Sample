package org.enduser.service.model;

import org.springframework.stereotype.Component;

@Component
public class TourType {
    private int id;
    private String name;

    public TourType() {}

    public TourType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
