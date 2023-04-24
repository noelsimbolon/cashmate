package org.kys.bnmo.components;

import javafx.beans.property.ListProperty;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MemberList implements ComponentFactory {

    private static final int transactionPreviewCount = 5;
    private VBox listView;
    private VBox detailView;
    private ListProperty<Object> members;
    public MemberList(ListProperty<Object> members)
    {
        this.members = members;
        this.listView = getListView();
        this.detailView = getDetailView("Jojo");
    }
    private HBox getCard(String name)
    {
        HBox root = new HBox();

        Button dropDownButton = new Button("[Dropdown_icon]");
        dropDownButton.getStyleClass().add("dropdown-button");
        VBox menu = new VBox();
        Button updateButton = new Button("Update");
        Button demoteButton = new Button("Disable Membership");
        Button promoteButton = new Button("Promote to VIP");
        menu.getChildren().addAll(updateButton, demoteButton, promoteButton);
        menu.getStyleClass().add("overlay-menu");

        StackPane menuStack = new StackPane();
        menuStack.getChildren().addAll(dropDownButton, menu);
        menuStack.getStyleClass().add("menu-stack");

        VBox cardContent = new VBox();
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("name-label");
        Label levelLabel = new Label("VIP");
        levelLabel.getStyleClass().add("level-label");
        cardContent.getChildren().addAll(nameLabel, levelLabel);
        cardContent.getStyleClass().add("card-content");

        root.getChildren().addAll(menuStack, cardContent);
        root.getStyleClass().add("member-card");

        return root;
    }
    private VBox getListView()
    {
        HBox filterRow = new HBox();
        Button customerFilterButton = new Button("Customers");
        Button memberFilterButton = new Button("Member/VIP");

        customerFilterButton.getStyleClass().add("filter-button");
        memberFilterButton.getStyleClass().add("filter-button");
        filterRow.getChildren().addAll(customerFilterButton, memberFilterButton);
        filterRow.getStyleClass().add("filter-row");

        ScrollPane listPanel = new ScrollPane();
        VBox list = new VBox();

        for (int i = 0; i < 10; i++)
        {
            list.getChildren().add(getCard(Integer.toString(i)));
        }

        list.getStyleClass().add("members-box");

        listPanel.setContent(list);
        listPanel.getStyleClass().add("member-scroll-panel");

        Button addMemberButton = new Button("[add_icon]");
        addMemberButton.getStyleClass().add("add-button");

        VBox root = new VBox();
        root.getChildren().addAll(filterRow, listPanel, addMemberButton);
        root.getStyleClass().add("list-column");
        return root;
    }

    private VBox getDetailView(String name)
    {
        VBox root;

        if (true)
        {
            root = getMemberDetailView(name);
        }

        else
        {
            root = getCustomerDetailView(name);
        }

        root.getStyleClass().add("detail-column");
        return root;
    }
    private VBox getMemberDetailView(String name)
    {
        HBox title = new HBox();
        Label nameLabel = new Label(name);
        Label level = new Label("member");

        title.getChildren().addAll(nameLabel, level);

        GridPane userInfo = new GridPane();

        Label idIcon = new Label("idIcon");
        Label phoneIcon = new Label("phoneIcon");
        Label poinIcon = new Label("poinIcon");
        GridPane.setConstraints(idIcon, 0, 0);
        GridPane.setConstraints(phoneIcon, 0, 1);
        GridPane.setConstraints(poinIcon, 0, 2);

        Label id = new Label("id");
        Label phone = new Label("phone");
        Label poin = new Label("poin");
        GridPane.setConstraints(id, 0, 0);
        GridPane.setConstraints(phone, 0, 1);
        GridPane.setConstraints(poin, 0, 2);

        userInfo.getChildren().addAll(idIcon, phoneIcon, poinIcon, id, phone, poin);

        VBox transactionPreviewPanel = new VBox();
        Label transactionTitle = new Label("Transactions");

        transactionPreviewPanel.getChildren().add(transactionTitle);
        for (int i = 0; i < transactionPreviewCount; i++)
        {
            Label transactionId = new Label("transaction" + i);
            transactionPreviewPanel.getChildren().add(transactionId);
        }

        Button showTransactionsButton = new Button("See More");

        transactionPreviewPanel.getChildren().add(showTransactionsButton);

        VBox root = new VBox();

        root.getChildren().addAll(title, userInfo, transactionPreviewPanel);

        return root;
    }

    private VBox getCustomerDetailView(String name)
    {
        return new VBox();
    }
    @Override
    public Parent getComponent() {
        HBox root = new HBox();
        root.getChildren().addAll(listView, detailView);
        root.getStyleClass().add("member-list");

        try {

            String css = this.getClass()
                    .getResource("/styles/memberList.css")
                    .toExternalForm();

          root.getStylesheets().add(css);
        }

        catch (NullPointerException e)
        {
            System.out.println("Failed to load css for memberList component!");
        }

        return root;
    }
}
