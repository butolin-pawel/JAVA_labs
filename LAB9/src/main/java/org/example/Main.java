package org.example;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IllegalAccessException
    {
        PostgresConnection postgresConnection = PostgresConnection.connect("jdbc:postgresql://localhost:5432/postgres?user=postgres&password=Vq2R8FJ");
        SqlRepository<Horse> sqlRepository = SqlRepository.init(postgresConnection, "org.example.Horse");
        Horse horse1 = new Horse("Василий", 14, 111, 122);
        Horse horse2 = new Horse("Петрович", 10, 121, 150);
        Horse horse3 = new Horse("Колян", 13, 321, 145);
        Horse horse4 = new Horse("Санёк", 5, 101, 85);
        sqlRepository.insert(horse1);
        sqlRepository.insert(horse2);
        sqlRepository.insert(horse3);
        sqlRepository.insert(horse4);
    }
}