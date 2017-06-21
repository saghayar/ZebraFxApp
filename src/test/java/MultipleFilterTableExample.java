//
//import az.com.cybernet.zebra.model.Product;
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Function;
//import java.util.function.Predicate;
//
//import javafx.application.Application;
//import javafx.beans.binding.Bindings;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.transformation.FilteredList;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
//
//public class MultipleFilterTableExample extends Application {
//
//    @Override
//    public void start(Stage primaryStage) {
//        TableView<Product> table = new TableView<>();
//        table.getColumns().add(column("I", Product::getProductId));
//        table.getColumns().add(column("name", Person::emailProperty));
//        table.getColumns().add(column("q", Person::genderProperty));
//
//        ComboBox<Person.Gender> genderFilterCombo = new ComboBox<>();
//        genderFilterCombo.getItems().addAll(Person.Gender.values());
//
//        TextField nameFilterField = new TextField();
//
//        ObjectProperty<Predicate<Person>> nameFilter = new SimpleObjectProperty<>();
//        ObjectProperty<Predicate<Person>> genderFilter = new SimpleObjectProperty<>();
//
//        nameFilter.bind(Bindings.createObjectBinding(()
//                -> person -> person.getName().toLowerCase().contains(nameFilterField.getText().toLowerCase()),
//                nameFilterField.textProperty()));
//
//        genderFilter.bind(Bindings.createObjectBinding(()
//                -> person -> genderFilterCombo.getValue() == null || genderFilterCombo.getValue() == person.getGender(),
//                genderFilterCombo.valueProperty()));
//
//        FilteredList<Person> filteredItems = new FilteredList<>(FXCollections.observableList(createData()));
//        table.setItems(filteredItems);
//
//        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(
//                () -> nameFilter.get().and(genderFilter.get()),
//                nameFilter, genderFilter));
//
//        Button clear = new Button("Clear Filters");
//        clear.setOnAction(e -> {
//            genderFilterCombo.setValue(null);
//            nameFilterField.clear();
//        });
//
//        HBox filters = new HBox(5, nameFilterField, genderFilterCombo, clear);
//        filters.setPadding(new Insets(5));
//        BorderPane root = new BorderPane(table, filters, null, null, null);
//        Scene scene = new Scene(root, 600, 600);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    private List<Person> createData() {
//        return Arrays.asList(
//                new Person("Jacob Smith", "jacob.smith@example.com", Person.Gender.MALE),
//                new Person("Isabella Johnson", "isabella.johnson@example.com", Person.Gender.FEMALE),
//                new Person("Ethan Williams", "ethan.williams@example.com", Person.Gender.MALE),
//                new Person("Emma Jones", "emma.jones@example.com", Person.Gender.FEMALE),
//                new Person("Michael Brown", "michael.brown@example.com", Person.Gender.MALE)
//        );
//    }
//
//    private static <S, T> TableColumn<S, T> column(String title, Function<S, ObservableValue<T>> property) {
//        TableColumn<S, T> col = new TableColumn<>(title);
//        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
//        return col;
//    }
//
//    public static class Person {
//
//        public enum Gender {
//            MALE, FEMALE
//        }
//
//        private final StringProperty name = new SimpleStringProperty();
//        private final StringProperty email = new SimpleStringProperty();
//        private final ObjectProperty<Gender> gender = new SimpleObjectProperty<>();
//
//        public Person(String name, String email, Gender gender) {
//            setName(name);
//            setEmail(email);
//            setGender(gender);
//        }
//
//        public final StringProperty emailProperty() {
//            return this.email;
//        }
//
//        public final String getEmail() {
//            return this.emailProperty().get();
//        }
//
//        public final void setEmail(final String email) {
//            this.emailProperty().set(email);
//        }
//
//        public final ObjectProperty<Gender> genderProperty() {
//            return this.gender;
//        }
//
//        public final Gender getGender() {
//            return this.genderProperty().get();
//        }
//
//        public final void setGender(final Gender gender) {
//            this.genderProperty().set(gender);
//        }
//
//        public final StringProperty nameProperty() {
//            return this.name;
//        }
//
//        public final String getName() {
//            return this.nameProperty().get();
//        }
//
//        public final void setName(final String name) {
//            this.nameProperty().set(name);
//        }
//
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
