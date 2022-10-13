package zea_lab2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class ZEA_Lab2 {

    public static void main(String[] args) {
        try {
            // Адрес нашей базы данных "tsn_demo" на локальном компьютере (localhost)
            String url = "jdbc:mysql://localhost:3306/zea_labs?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            // Создание свойств соединения с базой данных
            Properties authorization = new Properties();
            authorization.put("user", "root"); // Зададим имя пользователя БД
            authorization.put("password", "root"); // Зададим пароль доступа в БД

            // Создание соединения с базой данных
            Connection connection = DriverManager.getConnection(url, authorization);

            // Создание оператора доступа к базе данных
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            // Выполнение запроса к базе данных, получение набора данных
            ResultSet table = statement.executeQuery("SELECT * FROM zea_labs.table_fridge ");

            System.out.println("Начальная БД:");
            table.first(); // Выведем имена полей
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
            }
            System.out.println();

            table.beforeFirst(); // Выведем записи таблицы
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("Введите параметры нового поля таблицы:");
            System.out.print("model - ");
            String scannedModel = sc.nextLine();
            System.out.print("price - ");
            String scannedPrice = sc.nextLine();
            
            System.out.println("После добавления:");
            statement.execute("INSERT zea_labs.table_fridge(model, price) VALUES ('" + scannedModel + "', '" + scannedPrice + "')");
            table = statement.executeQuery("SELECT * FROM zea_labs.table_fridge");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            System.out.println("Строку с каким id хотите удалить?");
            System.out.print("id - ");
            int scannedId = sc.nextInt();
            statement.execute("DELETE FROM zea_labs.table_fridge WHERE Id = " + scannedId);
            
            System.out.println("После удаления:");
            table = statement.executeQuery("SELECT * FROM zea_labs.table_fridge");
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            sc.nextLine();
            
            System.out.println("На что изменить в первую строку?");
            System.out.print("model - ");
            String scannedModelUp = sc.nextLine();
            System.out.print("price - ");
            String scannedPriceUp = sc.nextLine();
            statement.executeUpdate("UPDATE zea_labs.table_fridge SET Model = '" + scannedModelUp + "' WHERE Id = 1");
            statement.executeUpdate("UPDATE zea_labs.table_fridge SET Price = '" + scannedPriceUp + "' WHERE id = 1");
            System.out.println("После изменения:");
            table = statement.executeQuery("SELECT * FROM zea_labs.table_fridge");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            System.out.println("После фильтрации:");
            System.out.print("Фильтрация по числу - ");
            int scannedNum = sc.nextInt();
            table = statement.executeQuery("SELECT * FROM zea_labs.table_fridge WHERE price >="+ scannedNum + "  ORDER BY price");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            if (table != null) {
                table.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            } // Отключение от базы данных

        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

}
