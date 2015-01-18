package parking.implementation.gui.controls;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import parking.api.business.parkingspot.ParkingSpot;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.gui.MainApplication;
import parking.implementation.gui.Utils;

import java.util.Collection;

/**
 * Created by sknz on 1/18/15.
 */
public class ParkingFloorTableView extends TableView<ParkingTableViewRow> {
    private ObservableList<ParkingTableViewRow> data = FXCollections.observableArrayList();

    public ParkingFloorTableView() {
        setItems(data);
        addNewLine();

        TableColumn<ParkingTableViewRow, Integer> floorColumn = new TableColumn<>("Etage");
        TableColumn<ParkingTableViewRow, Integer> quantityColumn = new TableColumn<>("Nb. places");
        TableColumn<ParkingTableViewRow, Class<? extends ParkingSpot>> typeColumn = new TableColumn<>("Type");

        floorColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));

        quantityColumn.setSortable(false);
        typeColumn.setSortable(false);
        typeColumn.setPrefWidth(100);

        floorColumn.setCellFactory(cell -> unsignedIntegerTextFieldTableCell());
        floorColumn.setOnEditCommit(event -> getItems().get(event.getTablePosition().getRow()).setFloor(event.getNewValue()));

        quantityColumn.setCellFactory(cell -> unsignedIntegerTextFieldTableCell());
        quantityColumn.setOnEditCommit(event -> getItems().get(event.getTablePosition().getRow()).setQuantity(event.getNewValue()));

        typeColumn.setCellFactory(cell -> parkingSpotClassComboBoxTableCell());
        typeColumn.setOnEditCommit(event -> getItems().get(event.getTablePosition().getRow()).setType(event.getNewValue()));

        getColumns().add(floorColumn);
        getColumns().add(quantityColumn);
        getColumns().add(typeColumn);
        setEditable(true);
    }

    private TextFieldTableCell<ParkingTableViewRow, Integer> unsignedIntegerTextFieldTableCell() {
        TextFieldTableCell<ParkingTableViewRow, Integer> field = new TextFieldTableCell<>();
        field.setConverter(new IntegerStringConverter() {
            @Override
            public Integer fromString(String value) {
                try {
                    Integer intValue = Math.max(1, Math.min(99, super.fromString(value)));
                    return intValue > 0 ? intValue : 1;
                } catch (NumberFormatException e) {
                    field.cancelEdit();
                }
                return Integer.MAX_VALUE;
            }
        });

        return field;
    }

    private ComboBoxTableCell<ParkingTableViewRow, Class<? extends ParkingSpot>> parkingSpotClassComboBoxTableCell() {
        ComboBoxTableCell<ParkingTableViewRow, Class<? extends ParkingSpot>> field = new ComboBoxTableCell<>();
        field.getItems().addAll(MainApplication.ParkingSpotTypes);
        field.setConverter(new StringConverter<Class<? extends ParkingSpot>>() {
            @Override
            public String toString(Class<? extends ParkingSpot> object) {
                return object.getSimpleName().replace("ParkingSpot", "");
            }

            @Override
            public Class<? extends ParkingSpot> fromString(String string) {
                try {
                    Class type = Class.forName(string + "ParkingSpot");
                    if (ParkingSpot.class.isAssignableFrom(type))
                        return Utils.<Class<? extends ParkingSpot>>uncheckedCast(type);
                } catch (ClassNotFoundException e) {
                    field.cancelEdit();
                }

                return null;
            }
        });
        return field;
    }

    public void addNewLine() {
        int position = data.contains(getSelectionModel().getSelectedItem())
                ? data.indexOf(getSelectionModel().getSelectedItem())
                : data.size();

        if (position + 1 < data.size()) {
            ParkingTableViewRow nextItem = data.get(position + 1);
            if (nextItem.isLocked())
            {
                new Alert(Alert.AlertType.ERROR, "Vous ne pouvez que rajouter de nouvelles places à la fin.").show();
                return;
            }
        }

        data.add(position, new ParkingTableViewRow(1, 10, CarParkingSpot.class));
    }

    public void removeSelectedLine() {
        ParkingTableViewRow selectedItem = getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (!selectedItem.isLocked())
                data.remove(selectedItem);
            else new Alert(Alert.AlertType.ERROR, "Vous ne pouvez que rajouter de nouvelles places à la fin.").show();
        }
    }

    public Collection<ParkingTableViewRow> getData() {
        return data;
    }
}
