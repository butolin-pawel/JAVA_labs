package org.example;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class SqlRepository<Entity> {

    private final PostgresConnection postgresConnection;

    private SqlRepository(PostgresConnection postgresConnection) {
        this.postgresConnection = postgresConnection;
    }

    public static <Entity> SqlRepository<Entity> init(PostgresConnection postgresConnection, String className) throws ClassNotFoundException, SQLException {
        SqlRepository<Entity> sqlRepository = new SqlRepository<>(postgresConnection);

        StringBuilder sqlQuery = new StringBuilder("CREATE TABLE ");

        Class<?> clazz = Class.forName(className);
        Table table = clazz.getAnnotation(Table.class);

        if (Objects.isNull(table)) {
            System.out.println("Ошибка: В сущности должна быть аннотация 'Column'!");
        } else {
            sqlQuery.append(table.name()).append("\s").append("(").append("\n");

            for (Field field : clazz.getDeclaredFields()) {
                Column annotation = field.getDeclaredAnnotation(Column.class);

                if (annotation != null) {
                    String typeName = field.getType().getSimpleName();
                    String fieldName = field.getName();

                    switch (typeName) {
                        case "String" -> sqlQuery.append(fieldName).append("\s").append("TEXT").append("\s");
                        case "Integer" -> sqlQuery.append(fieldName).append("\s").append("INTEGER").append("\s");
                        default -> System.out.println("Ошибка: Другие типы данных не поддерживаются!");
                    }

                    sqlQuery.append(",\n");
                }
            }

            sqlQuery = new StringBuilder(sqlQuery.substring(0, sqlQuery.length() - 2)).append("\n);");
        }
        try {
            sqlRepository.executeQuery(sqlQuery.toString());
        } catch (SQLException e) {
            System.out.println("Таблица уже существует, но вы можете продолжать работу!");
        }
        return sqlRepository;
    }

    public void insert(Entity horse) throws IllegalAccessException, SQLException {
        Class<?> clazz = horse.getClass();
        Table table = clazz.getAnnotation(Table.class);

        if (Objects.isNull(table)) {
            System.out.println("Ошибка: В сущности должна быть аннотация 'Column'!");
        } else {
            StringBuilder query = new StringBuilder("INSERT INTO ").append(table.name()).append("\s")
                    .append("VALUES (");
            for (Field field : clazz.getDeclaredFields()) {
                Column column = field.getDeclaredAnnotation(Column.class);
                field.setAccessible(true);

                if (column != null) {
                    Object fieldValue = field.get(horse);
                    if (fieldValue instanceof String) {
                        query.append("'").append((String) fieldValue).append("',\s");
                    } else if (fieldValue instanceof Integer) {
                        query.append(fieldValue).append(",\s");
                    }
                }
            }

            query = new StringBuilder(query.substring(0, query.length() - 2)).append(");");
            executeQuery(query.toString());
        }
    }

    public void executeQuery(String query) throws SQLException {
        PreparedStatement preparedStatement = postgresConnection.preparedStatement(query);
        preparedStatement.execute();
    }
}
