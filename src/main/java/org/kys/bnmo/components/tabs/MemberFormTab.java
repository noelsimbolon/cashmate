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
        // TODO: Change this to save to database
        this.saveButtonAction = (event) -> {
            int editedCustomerIndex = members.indexOf(existingMember);
            members.remove(existingMember);

            existingMember.setName(name.getValue());
            existingMember.setPhoneNumber(telephone.getValue());
            existingMember.setPoints(Integer.parseInt(points.getValue()));

            if (memberLevel.getValue().equals("Member")) {
                existingMember.promote();
            } else {
                existingMember.demote();
            }

            members.add(editedCustomerIndex, existingMember);

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
