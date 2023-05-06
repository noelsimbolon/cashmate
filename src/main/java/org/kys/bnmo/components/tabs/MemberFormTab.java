package org.kys.bnmo.components.tabs;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.model.Member;
import org.kys.bnmo.helpers.views.IconButtonHelper;

public class MemberFormTab extends TabContainer {

    private static final FormBuilder formBuilder = new FormBuilder();
    private static final IconButtonHelper iconButtonHelper = new IconButtonHelper();

    String formTitle;
    Member existingMember;

    EventHandler<ActionEvent> backButtonAction;

    public MemberFormTab(String title, EventHandler<ActionEvent> backButtonAction)
    {
        this.formTitle = title;
        this.existingMember = null;
        this.backButtonAction = backButtonAction;
    }

    public MemberFormTab(String title, Member existingMember, EventHandler<ActionEvent> backButtonAction)
    {
        this.formTitle = title;
        this.existingMember = existingMember;
        this.backButtonAction = backButtonAction;
    }


//    public MemberFormTab(int customerID, EventHandler<ActionEvent> backButtonAction)
//    {
//        this.customerID = customerID;
//        this.backButtonAction = backButtonAction;
//    }

    @Override
    protected Pane getContent() {

        // Initialize form
        if (existingMember != null) {
            // If it is an existing customer, then create a form with the existing data
            formBuilder.addTitle("Member Details");
            formBuilder.addTextBox("Name", "Enter member name", existingMember.getName());
            formBuilder.addTextBox("Telephone", "Enter telephone number", existingMember.getPhoneNumber());
            formBuilder.addDropdown("Member Level", "Select the member level", new String[] {"Member", "VIP", "Non-Active"}, existingMember.getCustomerClass());
            formBuilder.addTextBox("Points", "Enter amount of points", String.valueOf(existingMember.getPoints()));
            formBuilder.addButton("Save");
        } else {
            // If it is a new customer, then create a new form
            formBuilder.addTitle("Member Details");
            formBuilder.addTextBox("Name", "Enter member name");
            formBuilder.addTextBox("Telephone", "Enter telephone number");
            formBuilder.addDropdown("Member Level", "Select the member level", new String[] {"Member", "VIP", "Non-Active"});
            formBuilder.addTextBox("Points", "Enter amount of points");
            formBuilder.addButton("Save");
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
