package cz.devconf.demo.clientapp;

import cz.devconf.demo.ejbbackend.Item;
import cz.devconf.demo.ejbbackend.ItemsRepository;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;
import org.wildfly.security.sasl.SaslMechanismSelector;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.List;
import java.util.Properties;

public class Client {
    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
        Context context = new InitialContext(properties);
        ItemsRepository repository = (ItemsRepository) context.lookup("ejb:/ejb-backend-1.0-SNAPSHOT/ItemsRepositoryBean!cz.devconf.demo.ejbbackend.ItemsRepository");

        repository.addItem("pokus1");
        repository.addItem("pokus2");

        List<Item> items = repository.getItems();
        for (Item item : items) {
            System.out.println(item.getName());
        }
        System.out.println(items.size());

        AuthenticationContext.empty()
                .with(MatchRule.ALL, AuthenticationConfiguration.empty()
                        .setSaslMechanismSelector(SaslMechanismSelector.NONE.addMechanism("DIGEST-MD5"))
                        .useName("remover")
                        .usePassword("spongesForever!")
                ).run(() -> {
            try {
                repository.removeItem("pokus2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        items = repository.getItems();
        for (Item item : items) {
            System.out.println(item.getName());
        }
        System.out.println(items.size());

    }
}