package org.kys.bnmo.components.tabs;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.controllers.CustomerController;
import org.kys.bnmo.controllers.MemberController;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.Member;
import org.kys.bnmo.helpers.views.IconButtonHelper;

import java.util.ArrayList;

public class MemberFormTab extends TabContainer {

    private static final FormBuilder formBuilder = new FormBuilder();
    private static final IconButtonHelper iconButtonHelper = new IconButtonHelper();
    private static final CustomerController customerController = new CustomerController();
    private static final MemberController memberController = new MemberController();

    String formTitle;
    Member existingMember;

    EventHandler<ActionEvent> backButtonAction;
    EventHandler<ActionEvent> saveButtonAction;

    // State saver
    private final StringProperty name;
    private final StringProperty telephone;
    private final Property<String> memberLevel;
    private final StringProperty points;

    // Apply membership form
    public MemberFormTab(String title, int customerId, EventHandler<ActionEvent> backButtonAction)
    {
        this.formTitle = title;
        this.existingMember = null;
        this.backButtonAction = backButtonAction;
        this.name = new SimpleStringProperty();
        this.telephone = new SimpleStringProperty();
        this.points = new SimpleStringProperty();
        this.memberLevel = new SimpleStringProperty();

        // Set save button action to show states
        // TODO: Change this to save to database
        this.saveButtonAction = (event) -> {
            System.out.println("Name: " + name.getValue());
            System.out.println("Telephone: " + telephone.getValue());
            System.out.println("Points: " + points.getValue());
            System.out.println("Member Level: " + memberLevel.getValue());
        };
    }

    // Edit existing member form
    public MemberFormTab(String title, Member existingMember, EventHandler<ActionEvent> backButtonAction)
    {
        ArrayList<Member> members = memberController.fetchAll();

        this.formTitle = title;
        this.existingMember = existingMember;
        this.backButtonAction = backButtonAction;
        this.name = new SimpleStringProperty(existingMember.getName());
        this.telephone = new SimpleStringProperty(existingMember.getPhoneNumber());
        this.points = new SimpleStringProperty(String.valueOf(existingMember.getPoints()));
        this.memberLevel = new SimpleStringProperty(existingMember.getMemberLevel());

        // Set save button action to show states
        this.saveButtonAction = (event) -> {

            int editedCustomerID = existingMember.getCustomerID();
            int editedCustomerIndex = -1;
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getCustomerID() == editedCustomerID) {
                    editedCustomerIndex = i;
                    break;
                }
            }

            // TODO: Handle validations properly
            // If customer is not found, then do not save
            if (editedCustomerIndex == -1) {
                System.out.println("Customer not found");
                return;
            }

            // If any of the fields are empty, then do not save
            if (name.getValue().isEmpty() || telephone.getValue().isEmpty() || points.getValue().isEmpty()) {
                System.out.println("Empty fields");
                return;
            }

            // If points is not a number, then do not save
            if (!points.getValue().matches("\\d+")) {
                System.out.println("Points is not a number");
                return;
            }

            // If telephone is not a number, then do not save
            if (!telephone.getValue().matches("\\d+")) {
                System.out.println("Telephone is not a number");
                return;
            }

            // If points is negative, then do not save
            if (Integer.parseInt(points.getValue()) < 0) {
                System.out.println("Points is negative");
                return;
            }

            // Remove existing customer
            members.remove(editedCustomerIndex);

            // Create new customer with new values
            existingMember.setName(name.getValue());
            existingMember.setPhoneNumber(telephone.getValue());
            existingMember.setPoints(Integer.parseInt(points.getValue()));

            // Promote or demote member
            System.out.println(memberLevel.getValue());
            if (memberLevel.getValue().equals("Member") && existingMember.getMemberLevel().equals("VIP")) {
                existingMember.demote();
            } else if (memberLevel.getValue().equals("VIP") && existingMember.getMemberLevel().equals("Member")) {
                existingMember.promote();
            }
            System.out.println(existingMember.getMemberLevel());

            // Add new customer to the list
            members.add(editedCustomerIndex, existingMember);

            // Save to datastore
            memberController.save(members);

            // Go back to previous page
            backButtonAction.handle(event);
        };
    }

    @Override
    protected Pane getContent() {

        // Initialize form
        if (existingMember != null) {
            // If it is an existing customer, then create a form with the existing data
            formBuilder.addTitle("Member Details");
            formBuilder.addTextBox("Name", "Enter member name", existingMember.getName(), name);
            formBuilder.addTextBox("Telephone", "Enter telephone number", existingMember.getPhoneNumber(), telephone);
            formBuilder.addTextBox("Points", "Enter amount of points", String.valueOf(existingMember.getPoints()), points);
            formBuilder.addDropdown("Member Level", "Select the member level", new String[] {"Member", "VIP"}, memberLevel);
            formBuilder.addButton("Save", saveButtonAction);
        } else {
            // If it is a new customer, then create a new form
            formBuilder.addTitle("Member Details");
            formBuilder.addTextBox("Name", "Enter member name", name);
            formBuilder.addTextBox("Telephone", "Enter telephone number", telephone);
            formBuilder.addTextBox("Points", "Enter amount of points", points);
            formBuilder.addDropdown("Member Level", "Select the member level", new String[] {"Member", "VIP"}, memberLevel);
            formBuilder.addButton("Save", saveButtonAction);
        }

        return formBuilder.getAndResetComponent();
    }

    @Override
    protected void additionalAction()
    {
        Button backButton = new Button();
        iconButtonHelper.setButtonGraphic(backButton, "/icon/BackArrow.png", 20, 20);
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(backButtonAction);
        getHeader().getChildren().add(0, backButton);

        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle(this.formTitle);
    }
}
