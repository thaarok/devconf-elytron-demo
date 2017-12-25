package cz.devconf.demo.ejbbackend;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.security.Principal;
import java.util.List;
import org.jboss.ejb3.annotation.SecurityDomain;

@Stateless
@SecurityDomain("other")
@PermitAll // need to use any security annotation to obtain caller principal
public class ItemsRepositoryBean implements ItemsRepository {

    @Resource
    private SessionContext ejbContext;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Item> getItems() {
        TypedQuery<Item> query = em.createQuery("SELECT i FROM Item i", Item.class);
        return query.getResultList();
    }

    @Override
    @RolesAllowed({"Adder", "Admin"})
    public void addItem(String name) {
        Principal caller = ejbContext.getCallerPrincipal();
        String addedBy = caller != null ? caller.getName() : null;
        em.persist(new Item(name, addedBy));
    }

    @Override
    @RolesAllowed({"Remover"})
    public void removeItem(String name) {
        em.createQuery("DELETE FROM Item WHERE name = :name")
                .setParameter("name", name)
                .executeUpdate();
    }
}
