package lab10;

import org.example.PostgresConnection;

import java.sql.*;

public class Main {
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IllegalAccessException {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Vq2R8FJ");

            statement = connection.createStatement();

            System.out.println("Вывести список студентов, сдавших определенный предмет, на оценку выше 3:");
            One(statement);

            System.out.println("Посчитать средний бал по определенному предмету:");
            Two(statement);

            System.out.println("Посчитать средний балл по определенному студенту:");
            Three(statement);

            System.out.println("Найти три премета, которые сдали наибольшее количество студентов:");
            Four(statement);
            System.out.println("Те кто не получают стипендию:");
            Dop(statement);



        }
        catch (SQLException e) {
            System.out.println("Ошибка соединения");
            throw new RuntimeException(e);
        }
    }

    public static void One(Statement statement) throws SQLException {
        resultSet = statement.executeQuery(
                "SELECT s.name,p.mark,su.name as Sname FROM student s  join progress p ON(s.id = p.student) LEFT JOIN subject su ON(p.subject = su.id) WHERE p.mark >3 and su.name = 'Информатика';"
        );

        while (resultSet.next()) {
            String studName = resultSet.getString("name");
            String subjName = resultSet.getString("Sname");
            int grade = resultSet.getInt("mark");

            System.out.printf("%s %s %d\n", studName, subjName, grade);
        }
    }

    public static void Two(Statement statement) throws SQLException{
        resultSet = statement.executeQuery("SELECT avg(mark) as popred FROM progress WHERE subject = (SELECT id from subject WHERE name = 'Информатика');"
        );

        while (resultSet.next()) {;
            float avgGrade = resultSet.getFloat("popred");

            System.out.printf("%s\n", avgGrade);
        }
    }

    public static void Three(Statement statement) throws SQLException {
        resultSet = statement.executeQuery("SELECT avg(mark) as postud FROM progress WHERE student = (SELECT  id from student WHERE pass_ser =3317 and pass_num =485613);"
        );

        while (resultSet.next()) {
            float avgGrade = resultSet.getFloat("postud");

            System.out.printf("%s \n", avgGrade);
        }
    }

    public static void Four(Statement statement) throws SQLException {
        resultSet = statement.executeQuery("SELECT s.name,count(*) as cnt FROM subject s JOIN progress p ON (s.id = p.subject) GROUP BY s.name ORDER BY count(*) DESC limit 3;"
        );

        while (resultSet.next()) {
            String subjName = resultSet.getString("name");
            int cnt = resultSet.getInt("cnt");
            System.out.printf("%s\n", subjName,cnt);
        }
    }
    public static void Dop(Statement statement) throws SQLException{
        resultSet = statement.executeQuery(" \n" +
                "    SELECT S.name sn, P.mark mk,SU.name sun FROM progress P JOIN subject SU on (P.subject = SU.id) JOIN student S on P.student = S.id WHERE P.student IN\n" +
                "(SELECT DISTINCT S.id FROM student S JOIN progress P on (S.id = P.student) WHERE mark= 2 or mark = 3) and (mark =  2 or mark = 3) limit 3 offset 1; \n" );
        while(resultSet.next()){
            int mk = resultSet.getInt("mk");
            String sun = resultSet.getString("sun");
            String sn = resultSet.getString(("sn"));
            System.out.printf("%s %s %s\n", mk,sun,sn);
        }
    }


}