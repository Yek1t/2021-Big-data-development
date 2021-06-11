import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Demo extends Application {

    private Server server = new Server();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane bp = new BorderPane();

        TreeItem<String> ti = new TreeItem<>("null");
        ti.getChildren().clear();
        // 展开树
        ti.setExpanded(true);

        TreeView<String> tv = new TreeView<>(ti);
        tv.setStyle("-fx-font-size: 20;");

        MenuBar mb = new MenuBar();
        Menu operation = new Menu("     操 作    ");
        MenuItem createConnect = new MenuItem("   创建连接   ");

        /*
          “操作”下拉菜单中“创建连接”的事件监听
         */
        createConnect.setOnAction(event -> {
            System.out.println("打开连接窗口");

            Stage createStage = new Stage();

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(15);
            grid.setVgap(15);
            grid.setPadding(new Insets(25, 25, 25, 25));

            grid.setStyle("-fx-font-size: 20;");

            Label connectionNameLabel = new Label("连接名");
            TextField connectionNameTextField = new TextField();
            grid.add(connectionNameLabel,0,1);
            grid.add(connectionNameTextField,1,1);

            Label hostLabel = new Label("主机名");
            TextField hostTextField = new TextField();
            grid.add(hostLabel,0,2);
            grid.add(hostTextField,1,2);

            Label portLabel = new Label("端口号");
            TextField portTextField = new TextField();
            portTextField.setPrefWidth(2);
            grid.add(portLabel,0,3);
            grid.add(portTextField,1,3);

            Label userNameLabel = new Label("用户名");
            TextField userNameTextField = new TextField();
            grid.add(userNameLabel,0,4);
            grid.add(userNameTextField,1,4);

            Label passWordLabel = new Label("密码");
            PasswordField passWordTextField = new PasswordField();
            grid.add(passWordLabel,0,5);
            grid.add(passWordTextField,1,5);

            HBox hbBtn = new HBox(10);
            Button confirm = new Button("确认");
            Button cancel  = new Button("取消");

            /*
               确认时间监听逻辑
             */
            confirm.setOnAction(event1 -> {
                String connectionName = connectionNameTextField.getText();
                String host = hostTextField.getText();
                String port = portTextField.getText();
                String userName = userNameTextField.getText();
                String passWord = passWordTextField.getText();

                try {
                    server.connect(host, port, userName, passWord);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if(connectionName.length()!=0)
                    ti.setValue(connectionName);
                else ti.setValue("default");
                List<String> list = new ArrayList<>();
                List<String> subList = new ArrayList<>();
                try {
                    list = Server.bulidTree();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ti.getChildren().clear();
                for(String s : list){
                    TreeItem<String> item = new TreeItem<>(s);
                    try {
                        subList = Server.buildSubTree(s);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    for(String ss : subList){
                        item.getChildren().add(new TreeItem<>(ss));
                    }
                    ti.getChildren().add(item);
                }

                System.out.println(
                        "connectionName: " + connectionName +
                        "\nhost: " + host +
                        "\nport: " + port +
                        "\nuserName: " + userName +
                        "\npassWord: " + passWord
                );
                System.out.println("连接成功！");
                createStage.close();
            });

            cancel.setOnAction(event2 -> {
                createStage.close();
                System.out.println("关闭连接窗口");
            });

            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().addAll(confirm,cancel);
            grid.add(hbBtn,1,6);

            Scene scene = new Scene(grid, 400, 600);
            createStage.setScene(scene);
            createStage.setTitle("创建连接");
            createStage.getIcons().add(new Image("file:./GUI/icon/connect.png"));
            createStage.show();
        });

        MenuItem run = new MenuItem("   运 行    ");
        MenuItem exit = new MenuItem("   退 出   ");
        /*
          “操作”下拉菜单中“退出”的事件监听
         */
        exit.setOnAction(event -> {
            System.out.println("关闭客户端");
            System.exit(0);
        });
        operation.getItems().addAll(createConnect, new SeparatorMenuItem(), run, new SeparatorMenuItem(), exit);

        Menu help = new Menu("     帮 助    ");
        MenuItem documentation = new MenuItem("   在线文档   ");
        /*
          @// TODO: 2021/06/10
         * “帮助”下来菜单中“在线文档”的事件监听
         */
        documentation.setOnAction(event -> {

        });
        
        MenuItem github = new MenuItem("   Github   ");
        /*
          帮助下拉菜单中github选项的事件监听
         */
        github.setOnAction(event -> {
            try {
                URI uri = new URI("https://github.com/Yek1t/2021-Big-data-development");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }

        });
        help.getItems().addAll(documentation, github);

        // 设置“运行”的样式
        run.setStyle("-fx-text-base-color: red");
        
        // MenuBar的样式
        mb.setStyle("-fx-font-size: 25;" +
                    "-fx-font-family: 'NSimSun';" +
                    "-fx-padding: 0.333333em 0.083333em 0.666667em 0.083333em;" +
                    "-fx-background-insets: 0, 1, 2;" +
                    "-fx-font-weight:bold;"
        );

        mb.getMenus().addAll(operation, help);

        Label sql = new Label("请输入SQL语句：");

        TextArea ta = new TextArea();
        ta.setPrefColumnCount(10);
        ta.setStyle("-fx-font-size: 20;");

        sql.setStyle(
                "-fx-font-size: 25;" +
                "-fx-font-family: 'NSimSun';"
        );


        TextArea log = new TextArea();
//        log.setDisable(false);
        log.setEditable(false);
        log.setPrefHeight(600);
        log.setStyle("-fx-background-color: #BEBEBE;");

        /*
         * “运行”按钮的时间监听
         */
        run.setOnAction(event -> {
            String query = ta.getText();
            log.clear();
            System.out.println(query);
            try {
                server.execute(query, log);
            } catch (SQLException throwables) {
                log.appendText(throwables.toString());
                throwables.printStackTrace();
            }
        });

        Label loglabel = new Label("结果如下：");

        loglabel.setStyle(
                "-fx-font-size: 25;" +
                "-fx-font-family: 'NSimSun';"
        );

        VBox vb = new VBox();
        vb.getChildren().addAll(sql, ta, loglabel, log);
        vb.setPadding(new Insets(25));
        vb.setSpacing(20);

        log.setPrefColumnCount(20);
        log.setOpacity(1);
        log.setStyle(
                "-fx-background-color: white;" +
                "-fx-font-size: 22;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: red"
        );

        bp.setTop(mb);
        bp.setLeft(tv);
        bp.setCenter(vb);

        Scene scene = new Scene(bp, 1200, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Spark SQL查询分析器");
        primaryStage.getIcons().add(new Image("file:./GUI/icon/SQL.png"));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
