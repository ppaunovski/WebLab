package mk.ukim.finki.mk.lab.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserFullname implements Serializable {
    private String name;
    private String surname;

    public UserFullname(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public UserFullname() {
    }
}
