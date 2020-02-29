package controller;

import controller.builder.StaffBuilder;
import controller.manager.StaffManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.company.Company;
import model.company.CompanyDirector;
import model.staff.Staff;
import java.util.List;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;


public class Controller {

    private Company company = new Company(new CompanyDirector());

    /**
     * staff name TextField
     */
    @FXML
    private TextField nameTextField;

    /**
     * staff experience TextField
     */
    @FXML
    private TextField experienceTextField;

    /**
     * staff salary TextField
     */
    @FXML
    private TextField salaryTextField;

    /**
     * staff department of work name
     */
    @FXML
    private TextField departmentTextField;

    /**
     * min experience TextField for selecting
     */
    @FXML
    public TextField minTextField;

    /**
     * max experience TextField for selecting
     */
    @FXML
    public TextField maxTextField;

    /**
     * is reversed sequence CheckBox
     */
    @FXML
    private CheckBox isReversedCheckBox;

    /**
     * workman department CheckBox
     */
    @FXML
    private CheckBox workmanCheckBox;

    /**
     * finance department CheckBox
     */
    @FXML
    private CheckBox financeCheckBox;

    /**
     * staff info TableView
     */
    @FXML
    private TableView<Staff> tableView = new TableView<>();

    /**
     * staff name TableColumn
     */
    @FXML
    private TableColumn<Staff, String> nameColumn = new TableColumn<>();

    /**
     * staff experience TableColumn
     */
    @FXML
    private TableColumn<Staff, Number> experienceColumn = new TableColumn<>();

    /**
     * staff salary TableColumn
     */
    @FXML
    private TableColumn<Staff, Number> salaryColumn = new TableColumn<>();

    /**
     * staff department TableColumn
     */
    @FXML
    private TableColumn<Staff, String> departmentColumn = new TableColumn<>();

    /**
     * initialize method
     */
    @FXML
    private void initialize() {
        addCompanyData();
        identifyDependencies();
    }

    /**
     * method to add test company data
     */
    private void addCompanyData() {
        StaffBuilder builder = new StaffBuilder();
        StaffManager manager = new StaffManager();

        company.addStaff(manager.provideWelder(builder, "Mike Tompson", "Workman department"));
        builder.reset();
        company.addStaff(manager.provideLoader(builder, "David Quick", "Workman department"));
        builder.reset();
        company.addStaff(manager.provideForeman(builder, "Henry Ford", "Workman department"));
        builder.reset();
        company.addStaff(manager.provideReceptionist(builder, "Kara Lind", "Finance department"));
        builder.reset();
        company.addStaff(manager.provideAccountant(builder, "Tina Berkley", "Finance department"));
        builder.reset();
        company.addStaff(manager.provideAccountant(builder, "Stew Harrison", "Finance department"));
        builder.reset();
        company.addStaff(manager.provideReceptionist(builder, "Polly Dolly", "Finance department"));
        builder.reset();
        company.addStaff(manager.provideForeman(builder, "Laura Smith", "Workman department"));
        builder.reset();
        company.addStaff(manager.provideLoader(builder, "Fred O'cinly", "Workman department"));
        builder.reset();
        company.addStaff(manager.provideWelder(builder, "Alexander Popov", "Workman department"));
        builder.reset();
    }

    /**
     * method that identifies dependencies within TableView
     */
    private void identifyDependencies() {
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        experienceColumn.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(BigDecimal.valueOf(cellData.getValue()
                .getExperience())
                .setScale(2, RoundingMode.HALF_DOWN)
                .doubleValue()));
        //experienceColumn.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getExperience()));
        salaryColumn.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(BigDecimal.valueOf(cellData.getValue()
                .getSalary())
                .setScale(2, RoundingMode.HALF_DOWN)
                .doubleValue()));
        //salaryColumn.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getSalary()));
        departmentColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDepartment()));


        //List<Staff> staffs = company.getStaff();
        //tableView.getItems().addAll(staffs);
        ObservableList<Staff> staff = FXCollections.observableArrayList(company.getStaff());
        //tableView.setItems(FXCollections.observableArrayList(company.getStaff()));

        //List staffs = company.getStaff();
        tableView.getItems().addAll(staff);

        //tableView.setEditable(false);
    }

    /**
     * handling tap sort by experience button
     *
     * @param actionEvent event of tapping on button
     */
    public void onSortByExperienceTapped(ActionEvent actionEvent) {
        tableView.getItems().clear();
        tableView.getItems().addAll(FXCollections.observableArrayList(company
                .sortStaffByExperience(isReversedCheckBox.isSelected())));
    }

    /**
     * handling tap select by experience button
     *
     * @param actionEvent some tapping on Select by experience button
     */
    public void onSelectByExperienceTapped(ActionEvent actionEvent) {
        String minExp = minTextField.getText();
        String maxExp = maxTextField.getText();
        if (maxExp.isEmpty() || minExp.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter correct range of experience", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.hide();
            }
        } else {
            System.out.println("min : " + minExp);
            System.out.println("max : " + maxExp);
            tableView.getItems().clear();
            tableView.getItems().addAll(company.selectStaffByExperience(Double.valueOf(minExp), Double.valueOf(maxExp)));
        }
    }

    /**
     * handling tap select by department button
     *
     * @param actionEvent some tapping on Select by department button
     */
    public void onSelectByDepartmentTapped(ActionEvent actionEvent) {
        tableView.getItems().clear();
        boolean isWorkman = workmanCheckBox.isSelected();
        boolean isFinance = financeCheckBox.isSelected();
        if(isWorkman)
            tableView.getItems().addAll(company.selectStaffByDepartment("Workman department"));
        if(isFinance)
            tableView.getItems().addAll(company.selectStaffByDepartment("Finance department"));
    }

    /**
     * handling add a new staff to tableView
     *
     * @param actionEvent some tapping on Add button
     */
    public void onAddButtonTapped(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String department = departmentTextField.getText();
        if (name.isEmpty() || department.isEmpty() ||
                (!department.equals("Workman department")) && !department.equals("Finance department")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill correctly all fields of new staff", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.hide();
            }
        } else {
            Staff newStaff;
            StaffBuilder builder = new StaffBuilder();
            StaffManager manager = new StaffManager();
            Random random = new Random();
            int choice = random.nextInt() % 3;

            if(department.equals("Workman department")) {
                switch (choice) {
                    case 0:
                        newStaff = manager.provideWelder(builder, name, department);
                        break;
                    case 1:
                        newStaff = manager.provideForeman(builder, name, department);
                        break;
                    case 2:
                        newStaff = manager.provideLoader(builder, name, department);
                        break;
                    default:
                        newStaff = manager.provideForeman(builder, name, department);
                        break;
                }
                tableView.getItems().addAll(newStaff);
            }
            else {
                choice %= 2;
                if(choice == 0) {
                    newStaff = manager.provideAccountant(builder, name, department);
                }
                else {
                    newStaff = manager.provideReceptionist(builder, name, department);
                }
                tableView.getItems().addAll(newStaff);
            }
        }
    }
}