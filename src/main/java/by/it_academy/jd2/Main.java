package by.it_academy.jd2;

import by.it_academy.jd2.entites.manytomany.GenreEntity;
import by.it_academy.jd2.entites.manytomany.VoteEntity;
import by.it_academy.jd2.entites.onetomany.VoteEntityOneMany;
import by.it_academy.jd2.entites.onetoone.VoteEntityOne;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;



import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

public class Main {
    static public final Properties prop = new Properties();
    static {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException();
            }
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static public Properties hibernateProperties = new Properties();
    static {
        hibernateProperties.put("jakarta.persistence.jdbc.driver", prop.getProperty("db.driver"));
        hibernateProperties.put("jakarta.persistence.jdbc.url", prop.getProperty("db.url"));
        hibernateProperties.put("jakarta.persistence.jdbc.user", prop.getProperty("db.user"));
        hibernateProperties.put("jakarta.persistence.jdbc.password", prop.getProperty("db.password"));
    }

    public final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test",
            hibernateProperties);
    public final static String url = prop.getProperty("db.url");
    public final static String user = prop.getProperty("db.user");
    public final static String password = prop.getProperty("db.password");
    public static void main(String[] args) {


        // Cleanup database before use
        try(Connection conn = DriverManager.getConnection(url,user,password);
            Statement statement = conn.createStatement()) {

            String sql = loadSQL("cleanUp.sql");

            for (String query : sql.split(";")) {
                query = query.trim();
                if (!query.isEmpty()) {
                    statement.execute(query);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Init cleanup failed");
        }


        int choice = 0;
        Scanner sc = new Scanner(System.in);
        boolean success = false;
        do {
            System.out.print("""
                    <!HIBERNATE TEST!>
                    Please choose mode:
                    1. Many to many
                    2. One to many
                    3. One to one
                    Choice:\s""");

            try {
                choice = Integer.parseInt(sc.nextLine());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("Please enter one of specified options");
            }

            success = switch (choice) {
                case 1 -> manyToMany();
                case 2 -> oneToMany();
                case 3 -> oneToOne();
                default -> false;
            };
        } while (!success);

    }
    private static boolean manyToMany() {

        // Create table for testing
        try(Connection conn = DriverManager.getConnection(url,user,password);
        Statement statement = conn.createStatement()) {

            String sql = loadSQL("initManyToMany.sql");

            for (String query : sql.split(";")) {
                query = query.trim();
                if (!query.isEmpty()) {
                    statement.execute(query);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(".SQL parsing/execution error");
        }

        List<VoteEntity> voteEntities = new ArrayList<>();
        try(EntityManager em = emf.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<VoteEntity> cq = cb.createQuery(VoteEntity.class);
            Root<VoteEntity> root = cq.from(VoteEntity.class);

            TypedQuery<VoteEntity> query = em.createQuery(cq);

            voteEntities = query.getResultList();
        }
        Iterator<GenreEntity> it;
        for (VoteEntity voteEntity : voteEntities) {
            System.out.println("id: " + voteEntity.getId());
            System.out.println("dt create: " + voteEntity.getDtCreate());
            System.out.println("artist id:" + voteEntity.getArtistId());
            System.out.println("about: " + voteEntity.getAbout());
            it = voteEntity.getGenre().iterator();
            System.out.println("genres:");
            while (it.hasNext()) {
                GenreEntity element = it.next();
                System.out.println("\tname: " + element.getName());
            }
            System.out.println("--------------------------");
        }

        System.out.println("Feel free to look into the db.\nPress Enter to continue...");
        try{
            System.in.read();
        }
        catch (IOException ignored){
        }
        // Cleanup
        try(Connection conn = DriverManager.getConnection(url,user,password);
            Statement statement = conn.createStatement()) {

            String sql = loadSQL("cleanUp.sql");

            for (String query : sql.split(";")) {
                query = query.trim();
                if (!query.isEmpty()) {
                    statement.execute(query);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Cleanup failed");
        }
        return true;
    }

    private static boolean oneToMany() {
        // Create table for testing
        try(Connection conn = DriverManager.getConnection(url,user,password);
            Statement statement = conn.createStatement()) {

            String sql = loadSQL("initOneToMany.sql");

            for (String query : sql.split(";")) {
                query = query.trim();
                if (!query.isEmpty()) {
                    statement.execute(query);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(".SQL parsing/execution error");
        }

        List<VoteEntityOneMany> voteEntities = new ArrayList<>();
        try(EntityManager em = emf.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<VoteEntityOneMany> cq = cb.createQuery(VoteEntityOneMany.class);
            Root<VoteEntityOneMany> root = cq.from(VoteEntityOneMany.class);

            TypedQuery<VoteEntityOneMany> query = em.createQuery(cq);

            voteEntities = query.getResultList();
        }
        for (VoteEntityOneMany voteEntity : voteEntities) {
            System.out.println("id: " + voteEntity.getId());
            System.out.println("dt create: " + voteEntity.getDtCreate());
            System.out.println("artist id:" + voteEntity.getArtistId());
            System.out.println("about: " + voteEntity.getAbout());
            System.out.println("genre: " + voteEntity.getGenre().getName());
            System.out.println("--------------------------");
        }

        System.out.println("Feel free to look into the db.\nPress Enter to continue...");
        try{
            System.in.read();
        }
        catch (IOException ignored){
        }

        // Cleanup
        try(Connection conn = DriverManager.getConnection(url,user,password);
            Statement statement = conn.createStatement()) {

            String sql = loadSQL("cleanUp.sql");

            for (String query : sql.split(";")) {
                query = query.trim();
                if (!query.isEmpty()) {
                    statement.execute(query);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Cleanup failed");
        }
        return true;

    }

    private static boolean oneToOne() {
        // Create table for testing
        try(Connection conn = DriverManager.getConnection(url,user,password);
            Statement statement = conn.createStatement()) {

            String sql = loadSQL("initOneToOne.sql");

            for (String query : sql.split(";")) {
                query = query.trim();
                if (!query.isEmpty()) {
                    statement.execute(query);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(".SQL parsing/execution error");
        }

        List<VoteEntityOne> voteEntities = new ArrayList<>();
        try(EntityManager em = emf.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<VoteEntityOne> cq = cb.createQuery(VoteEntityOne.class);
            Root<VoteEntityOne> root = cq.from(VoteEntityOne.class);

            TypedQuery<VoteEntityOne> query = em.createQuery(cq);

            voteEntities = query.getResultList();
        }
        for (VoteEntityOne voteEntity : voteEntities) {
            System.out.println("id: " + voteEntity.getId());
            System.out.println("dt create: " + voteEntity.getDtCreate());
            System.out.println("artist id:" + voteEntity.getArtistId());
            System.out.println("about: " + voteEntity.getAbout());
            System.out.println("genre: " + voteEntity.getGenre().getName());
            System.out.println("--------------------------");
        }
        System.out.println("Feel free to look into the db.\nPress Enter to continue...");
        try{
            System.in.read();
        }
        catch (IOException ignored){
        }

        // Cleanup
        try(Connection conn = DriverManager.getConnection(url,user,password);
            Statement statement = conn.createStatement()) {

            String sql = loadSQL("cleanUp.sql");

            for (String query : sql.split(";")) {
                query = query.trim();
                if (!query.isEmpty()) {
                    statement.execute(query);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Cleanup failed");
        }
        return true;

    }
    public static String loadSQL(String resourceName) {
        try (InputStream in = Main.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) {
                throw new RuntimeException("Resource not found: " + resourceName);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
