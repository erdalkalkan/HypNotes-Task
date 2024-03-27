package utilities;


import pojo.PPromoCode;
import pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtilities {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    public static ResultSet resultSet;

    //@SneakyThrows annotation kullanamadık, manifold ve Lombok kütüphaneleri çalıkışıyor
    //DB bağlanmak için kullanıyoruz
    public static void createConnection() {
        try {
            connection = DriverManager.getConnection(
                    ConfigurationReader.getProperty("url1"),
                    ConfigurationReader.getProperty("username"),
                    ConfigurationReader.getProperty("password1")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //DB'i kapatmak için kullanıyoruz
    public static void closeConnection() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    //Statement'i execute etmek için kullanıyoruz; query'i parametre olarak girmelisiniz
    //C U D işlemleri için kullanılır (create,update, delete)
    public static void executeQueryStatement(String query) { // insert update delete
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Hub Product'da product_listing_state'i approved yapacak query
    public static void approvedLastProduct() {
        executeQueryStatement("UPDATE `hub_product` SET `product_listing_state` = 'APPROVED' WHERE `product_listing_state` LIKE 'IN_REVIEW' order BY id DESC;");
    }

    //address create etmek için kullanabilirsiniz
    public static void createNewAddress(Integer owner_id, String AddressTitle, Integer is_default) {

        String query = "INSERT INTO address (owner_id,title,address,city, state,postal, is_default)"
                + " VALUES('"+owner_id+"','"+AddressTitle+"','San Jose, CA 95109, USA','San Jose','Santa Clara County', 95109, '"+is_default+"');";

        executeQueryStatement(query);
    }
    //prommo Code create etmek için kullanabilirsiniz
    public static void createPromoCode(String promoCodeName, String startDate, String endDate) {

        String query = "INSERT INTO promo_code (code,discount,discount_type,ends_at,starts_at)"
                + " VALUES('"+promoCodeName+"',30,'percentage','"+startDate+".000000','"+endDate+"');";

        executeQueryStatement(query);
    }

    //prommo Code create etmek için kullanabilirsiniz /prestatement kullanımı için örnek/
    public static void createPromoCodeWithPreStatement(String promoCodeName, String startDate, String endDate) {

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO 'promo_code' ('id','code','starts_at','end_at','number_of_users','discount','discount_type')"
                    + " VALUES (NULL, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, promoCodeName);
            preparedStatement.setString(2, startDate);
            preparedStatement.setString(3, endDate);
            preparedStatement.setInt(4, 5);
            preparedStatement.setInt(5, 20);
            preparedStatement.setString(6, "money");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //prommo Code Update etmek için kullanabilirsiniz
    public static void updatePromoCode(String columnName, String columnValue, int id) {
        String query = "update promo_code set "+columnName+" = '"+columnValue+"' where promo_code.id = "+id+";";
        executeQueryStatement(query);
    }

    //prommo Code Delete etmek için kullanabilirsiniz
    public static void deletePromoCode(int id) {
        String query = "DELETE FROM promo_code WHERE promo_code.id = "+id+";";
        executeQueryStatement(query);
    }

    //istenen listedeki elementi Update etmek için kullanabilirsiniz
    public static void updateElement(String columnName, String columnValue, int id, String listName) {
        String query = "update"+ listName +" "+columnName+" = '"+columnValue+"' where"+ listName+".id = "+id+";";
        executeQueryStatement(query);
    }

    // istenen listeden Delete islemi yapmak icin
    public static void deleteElementFromRelatedList(int id, String listName){
        String query = "DELETE FROM "+ listName+" WHERE "+listName+".id= "+id+";";
        executeQueryStatement(query);

    }

    //istenen listesiyi okumak için kullanabilirsiniz, List<Map<String, Object>> bir liste dönecek
    public static List<Map<String, Object>> getList(String listName) throws SQLException {

        //statement = connection.createStatement();
        //resultSet = statement.executeQuery("SELECT * FROM + listName" );

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + listName);
        resultSet = statement.executeQuery();


        List<Map<String, Object>> list = new ArrayList<>();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {

            Map<String, Object> map = new HashMap<>();

            for (int i = 1; i <= columnCount; i++) {
                map.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            list.add(map);

//                map.put("id",resultSet.getInt(1));
//                map.put("code",resultSet.getString("code"));
//            System.out.println("resultSet.getInt(1) = " + resultSet.getInt(1));

        }
        return list;
    }

    //promo Code listesini okumak için kullanabilirsiniz, List<PPromoCode> bir liste dönecek
    public static List<PPromoCode> getPromoCodeList_pojo() throws SQLException {

        statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from promo_code;");

        List<PPromoCode> list = new ArrayList<>();

        while (resultSet.next()) {

            PPromoCode promoCode = new PPromoCode(
                    resultSet.getInt("id"),
                    resultSet.getString("code"),
                    resultSet.getString("starts_at"),
                    resultSet.getString("ends_at"),
                    resultSet.getInt("number_of_users"),
                    resultSet.getInt("discount"),
                    resultSet.getString("discount_type")
            );
            list.add(promoCode);
        }
        return list;
    }


    public static void createUser(int allow_anonymous_chat, int browser_notifications,int is_verified,
                                  Object delivery_type,String email,String first_name,
                                  String password,Object roles){

        String query = "insert into user (allow_anonymous_chat,browser_notifications,delivery_type,email,first_name,is_verified,password,roles)"
                  +"values("+allow_anonymous_chat+","+browser_notifications+","+delivery_type+","+email+","+first_name+","+is_verified+", "+password+","+roles+"')";

        executeQueryStatement(query);

    }


    //user listesini okumak için kullanabilirsiniz, List<User> bir liste dönecek
    public static List<User> getUserList_pojo() throws SQLException {

        statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from user;");

        List<User> list = new ArrayList<>();

        while (resultSet.next()) {

            User user=new User(
                   resultSet.getInt("allow_anonymous_chat"),
                   resultSet.getInt("browser_notifications"),
                    resultSet.getInt("is_verified"),
                    resultSet.getString("delivery_type"),
                    resultSet.getString("email"),
                    resultSet.getString("first_name"),
                    resultSet.getString("password"),
                    resultSet.getString("roles")


            );
            list.add(user);
        }
        return list;
    }

}
