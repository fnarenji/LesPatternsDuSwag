package parking.implementation.gui.controls;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import parking.api.business.parkingspot.ParkingSpot;
import parking.implementation.business.parkingspot.CarParkingSpot;
import parking.implementation.gui.MainApplication;
import parking.api.business.Utils;

/**
 * Created by sknz on 1/18/15.
 */
public class ParkingFloorTableView extends TableView<ParkingTableViewRow> {
    public ParkingFloorTableView() {
        TableColumn<ParkingTableViewRow, Integer> floorColumn = new TableColumn<>("Etage");
        TableColumn<ParkingTableViewRow, Integer> quantityColumn = new TableColumn<>("Nb. places");
        TableColumn<ParkingTableViewRow, Class<? extends ParkingSpot>> typeColumn = new TableColumn<>("Type");

        floorColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("parkingSpotType"));

        quantityColumn.setSortable(false);
        typeColumn.setSortable(false);
        typeColumn.setPrefWidth(100);

        floorColumn.setCellFactory(cell -> unsignedIntegerTextFieldTableCell());
        floorColumn.setOnEditCommit(event -> {
            ParkingTableViewRow row = getItems().get(event.getTablePosition().getRow());

            if (row.getLocked())
                new Alert(Alert.AlertType.ERROR, "Vous ne pouvez modifier une place déjà existante.").show();

            row.setFloor(event.getNewValue());
        });

        quantityColumn.setCellFactory(cell -> unsignedIntegerTextFieldTableCell());
        quantityColumn.setOnEditCommit(event -> {
            ParkingTableViewRow row = getItems().get(event.getTablePosition().getRow());

            if (row.getLocked())
                new Alert(Alert.AlertType.ERROR, "Vous ne pouvez modifier une place déjà existante.").show();

            row.setQuantity(event.getNewValue());
        });

        typeColumn.setCellFactory(cell -> parkingSpotClassComboBoxTableCell());
        typeColumn.setOnEditCommit(event -> {
            ParkingTableViewRow row = getItems().get(event.getTablePosition().getRow());

            if (row.getLocked())
                new Alert(Alert.AlertType.ERROR, "Vous ne pouvez modifier une place déjà existante.").show();

            row.setParkingSpotType(event.getNewValue());
        });

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

    private CheckBoxTableCell<ParkingTableViewRow, Boolean> lockedCheckBoxTableCell() {
        return new CheckBoxTableCell<>();
    }

    public void addNewLine() {
        int position = getItems().contains(getSelectionModel().getSelectedItem())
                ? getItems().indexOf(getSelectionModel().getSelectedItem()) + 1
                : getItems().size();

        if (position + 1 < getItems().size()) {
            ParkingTableViewRow nextItem = getItems().get(position + 1);
            if (nextItem.getLocked())
            {
                new Alert(Alert.AlertType.ERROR, "Vous ne pouvez que rajouter de nouvelles places à la fin.").show();
                return;
            }
        }

        getItems().add(position, new ParkingTableViewRow(1, 10, CarParkingSpot.class, false));
    }

    public void removeSelectedLine() {
        ParkingTableViewRow selectedItem = getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (!selectedItem.getLocked())
                getItems().remove(selectedItem);
            else new Alert(Alert.AlertType.ERROR, "Vous ne pouvez que rajouter de nouvelles places à la fin.").show();
        }
    }
}
