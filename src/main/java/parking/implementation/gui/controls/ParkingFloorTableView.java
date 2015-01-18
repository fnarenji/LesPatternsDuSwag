package parking.implementation.gui.controls;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import parking.api.business.parking.Parking;
import parking.api.business.parkingspot.ParkingSpot;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.gui.MainApplication;
import parking.implementation.gui.Utils;

/**
 * Created by sknz on 1/18/15.
 */
public class ParkingFloorTableView extends TableView<ParkingFloorTableView.ParkingTableRow> {
    private ObservableList<ParkingTableRow> data = FXCollections.observableArrayList();

    public ParkingFloorTableView() {
        setItems(data);
        data.add(new ParkingTableRow(1, 10, CarParkingSpot.class));

        TableColumn<ParkingTableRow, Integer> floorColumn = new TableColumn<>("Etage");
        TableColumn<ParkingTableRow, Integer> quantityColumn = new TableColumn<>("Nb. places");
        TableColumn<ParkingTableRow, Class<? extends ParkingSpot>> typeColumn = new TableColumn<>("Type");

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

    private TextFieldTableCell<ParkingTableRow, Integer> unsignedIntegerTextFieldTableCell() {
        TextFieldTableCell<ParkingTableRow, Integer> field = new TextFieldTableCell<>();
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

    private ComboBoxTableCell<ParkingTableRow, Class<? extends ParkingSpot>> parkingSpotClassComboBoxTableCell() {
        ComboBoxTableCell<ParkingTableRow, Class<? extends ParkingSpot>> field = new ComboBoxTableCell<>();
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

    public class ParkingTableRow {
        private SimpleIntegerProperty floor = new SimpleIntegerProperty();
        private SimpleIntegerProperty quantity = new SimpleIntegerProperty();
        private SimpleObjectProperty<Class<? extends ParkingSpot>> type = new SimpleObjectProperty<>();

        public ParkingTableRow(Integer floor, Integer quantity, Class<? extends ParkingSpot> type) {
            setFloor(floor);
            setQuantity(quantity);
            setType(type);
        }

        public int getFloor() {
            return floor.get();
        }

        public void setFloor(int floor) {
            this.floor.set(floor);
        }

        public int getQuantity() {
            return quantity.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public Class<? extends ParkingSpot> getType() {
            return type.get();
        }

        public void setType(Class<? extends ParkingSpot> type) {
            this.type.set(type);
        }
    }
}
