package cz.devconf.demo.ejbbackend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Item implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String addedBy;

    public Item() {}

    public Item(String name, String addedBy) {
        this.name = name;
        this.addedBy = addedBy;
    }

    public String getName() {
        return name;
    }

    public String getAddedBy() {
        return addedBy;
    }

}
