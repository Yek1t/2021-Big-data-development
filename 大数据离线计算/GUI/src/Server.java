import java.sql.*;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private String url;
    private static Connection conn;

    public void connect(String host, String port, String user,String password) throws SQLException {

        url = "jdbc:hive2://" + host + ":" + port + "/" + user + "_db";
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(url, user, password);

    }
    public static void execute(String sql, TextArea output) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            String row = res.getString(1);
            //输出所有表名
            output.appendText(row+"\n");
        }
    }

    public static List<String> bulidTree() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("show tables");
        List<String> list = new ArrayList<>();
        while(res.next()){
            list.add(res.getString(1));
        }
        return list;
    }

    public static List<String> buildSubTree(String s) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("show columns FROM " + s);
        List<String> list = new ArrayList<>();
        while(res.next()) {
            list.add(res.getString(1));
        }
        return list;
    }

}
