package utils;

import entity.Role;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import rest.SwappiData;

public class SetupTestUsers {

    public static void main(String[] args) {

        //testSwappiFutureCalls();
        startCorrectly();
    }

    public static String testSwappiFutureCalls() {
        //https://swapi.co/api/people/?format=json , der er 87 count, hvis man kan få count ud af json kan det være dynamisk
        int countpeople = 5;
        int iterator = 0;
        ForkJoinPool executor = new ForkJoinPool(25,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                null, false);
        List<Future<String>> futureArrayList = new ArrayList();
        while (iterator < countpeople) {
            Callable<String> worker = new SwappiData(iterator);
            futureArrayList.add(executor.submit(worker));
            iterator++;
        }
        StringBuilder sb = new StringBuilder();
        futureArrayList.parallelStream().forEach(future -> {
            try {
                String getFutureStr = future.get(5, TimeUnit.SECONDS);
                System.out.println("future value: " + getFutureStr + "\n");
                sb.append(getFutureStr);
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                Logger.getLogger(SetupTestUsers.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return sb.toString();
    }

public static void startCorrectly() {
        EntityManager em = PuSelector.getEntityManagerFactory("pu").createEntityManager();

        // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
        // CHANGE the three passwords below, before you uncomment and execute the code below
        //throw new UnsupportedOperationException("REMOVE THIS LINE, WHEN YOU HAVE READ WARNING");
        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        User user = new User("mads", "admin");
        user.addRole(userRole);
        User admin = new User("mark", "not");
        admin.addRole(adminRole);
        User both = new User("", "");
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.getTransaction().commit();
        System.out.println("PW: " + user.getUserPass());
        System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
        System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
        System.out.println("Created TEST Users");
    }

}