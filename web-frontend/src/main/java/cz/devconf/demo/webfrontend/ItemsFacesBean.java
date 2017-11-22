package cz.devconf.demo.webfrontend;

import cz.devconf.demo.ejbbackend.Item;
import cz.devconf.demo.ejbbackend.ItemsRepository;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "items")
@ViewScoped
public class ItemsFacesBean {

    @EJB
    private ItemsRepository repository;

    public List<Item> getAll() {
        return repository.getItems();
    }

    public void remove(String name) {
        repository.removeItem(name);
    }

}
