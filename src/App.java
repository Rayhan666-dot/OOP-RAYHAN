import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.css.converter.InsetsConverter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    Stage windowStage;
    TableView<Kampus> table;
    TableView<Kampus> tableView = new TableView<Kampus>();
    TextField idInput, jurusanInput, namaInput;

    @Override
    public void start(Stage stage) {

        // Menampilkan nama window
        windowStage = stage;
        windowStage.setTitle("DataBase - Barang");
       
        //Menampilkan tabel
        TableColumn<Kampus, String> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("Id"));

        TableColumn<Kampus, String> columnJurusan = new TableColumn<>("Jurusan");
        columnJurusan.setCellValueFactory(new PropertyValueFactory<>("Jurusan"));

        TableColumn<Kampus, String> columnNama = new TableColumn<>("Nama");
        columnNama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
        
        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnJurusan);
        tableView.getColumns().add(columnNama);
       
        //Input id
        idInput = new TextField();
        idInput.setPromptText("id");
        idInput.setMinWidth(10);

        //Input Jurusan
        jurusanInput = new TextField();
        jurusanInput.setPromptText("Jurusan");
        jurusanInput.setMinWidth(20);

        //Input Nama
        namaInput = new TextField();
        namaInput.setPromptText("Nama");
        namaInput.setMinWidth(20);

        //Button
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editButtonClicked());
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(idInput, jurusanInput, namaInput, editButton, updateButton, deleteButton);

        String url = "jdbc:mysql://localhost:3306/kampus";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet record = stmt.executeQuery("select*from data");

            while (record.next()) {
                tableView.getItems().add(new Kampus(record.getInt("id"), record.getString("jurusan"), record.getString("nama")));
            }
        }
        catch (SQLException e) {
            System.out.print("koneksi gagal");
        }
        

        VBox vbox = new VBox(tableView);
        vbox.getChildren().addAll(hBox);

        Scene scene = new Scene(vbox);

        stage.setScene(scene);
        stage.show();

    }


    //Update Button Clicked
    private void updateButtonClicked(){

        Database db = new Database();
                try {
                    Statement state = db.conn.createStatement();
                    String sql = "insert into data SET jurusan='%s', nama='%s'";
                    sql = String.format(sql, jurusanInput.getText(), namaInput.getText());
                    state.execute(sql);
                    // idInput.clear();
                    jurusanInput.clear();
                    namaInput.clear();
                    loadData();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            
        };

    //Edit Button Clicked
    private void editButtonClicked(){

        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "update data set jurusan = '%s' WHERE nama ='%s'";
            sql = String.format(sql, jurusanInput.getText(), namaInput.getText());
            state.execute(sql);
            jurusanInput.clear();
            namaInput.clear();
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Delete button Clicked
    private void deleteButtonClicked(){

        Database db = new Database();
        try{
            Statement state = db.conn.createStatement();
            String sql = "delete from data where nama ='%s';";
            sql = String.format(sql, namaInput.getText());
            state.execute(sql);
            namaInput.clear();
            loadData();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    

    public static void main(String[] args) {
        launch();
    }

    private void loadData() {
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from data");
            tableView.getItems().clear();
            while(rs.next()){
                tableView.getItems().add(new Kampus(rs.getInt("id"), rs.getString("jurusan"), rs.getString("nama")));
            }
            
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

}