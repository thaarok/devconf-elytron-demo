package cz.devconf.demo.ejbbackend;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ItemsRepository {

    List<Item> getItems();

    void addItem(String name);

    void removeItem(String name);

}
