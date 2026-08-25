package org.dudafs.ui;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.dudafs.model.StoreItem;

public class Fs25IndexerApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        TableView<StoreItem> table = new TableView<>();

        TableColumn<StoreItem, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getBrand())
        );

        TableColumn<StoreItem, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getName())
        );

        TableColumn<StoreItem, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getCategory())
        );

        TableColumn<StoreItem, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> {
            int price = data.getValue().getPrice();
            return new ReadOnlyStringWrapper(Integer.toString(price));
        });

        table.getColumns().addAll(brandColumn, nameColumn, categoryColumn, priceColumn);

        ObservableList<StoreItem> items = FXCollections.observableArrayList();

        StoreItem testItem = new StoreItem("John Deere", "7810", "tractorsM", 95000);
        items.add(testItem);

        StoreItem testBaler = new StoreItem("John Deere", "F441R", "balers", 52000);
        items.add(testBaler);

        table.setItems(items);

        BorderPane root = new BorderPane();
        root.setCenter(table);

        Scene scene = new Scene(root, Color.GRAY);

        Image icon = new Image("/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("DudaFS Mod Manager");
        stage.setWidth(1080);
        stage.setHeight(720);
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }
}
