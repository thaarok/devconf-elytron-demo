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
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
        Context context = new InitialContext(properties);
        ItemsRepository repository = (ItemsRepository) context.lookup("ejb:ear-1.0-SNAPSHOT/ejb-backend/ItemsRepositoryBean!cz.devconf.demo.ejbbackend.ItemsRepository");

        System.out.println("Testing client application");
        System.out.println("**************************");

        System.out.println("Adding items1, item2 and item3...");
        repository.addItem("item1");
        repository.addItem("item2");
        repository.addItem("item3");

        System.out.println("\nItems on the server:");
        List<Item> items = repository.getItems();
        for (Item item : items) {
            System.out.println("* " + item.getName());
        }
        System.out.println();

        System.out.println("Removing item3...");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: "); System.out.flush();
        String username = scanner.next();
        System.out.print("Password: "); System.out.flush();
        String password = scanner.next();
        AuthenticationContext.empty().with(
            MatchRule.ALL.matchHost("localhost").matchPort(8080),
            AuthenticationConfiguration.empty()
                .setSaslMechanismSelector(SaslMechanismSelector.NONE.addMechanism("DIGEST-MD5"))
                .useName(username)
                .usePassword(password)
        ).run(() -> {
            try {
                repository.removeItem("item3");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println("\nItems on the server:");
        items = repository.getItems();
        for (Item item : items) {
            System.out.println("* " + item.getName());
        }
        System.out.println();
    }
}
