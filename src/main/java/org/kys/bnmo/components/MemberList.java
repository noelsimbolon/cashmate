package org.kys.bnmo.components;

import javafx.beans.property.ListProperty;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

    private HBox getStatusRow()
    {
        HBox statusRow = new HBox();
        Label level = new Label("Member");
        Label status = new Label("Active");
        level.getStyleClass().add("level-label");
        status.getStyleClass().add("level-label");

        statusRow.getChildren().addAll(level, status);
        statusRow.getStyleClass().add("status-row");
        return statusRow;
    }
    private StackPane getMenuStack()
    {
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

        return menuStack;
    }

    private HBox getCard(String name)
    {
        HBox root = new HBox();

        StackPane menuStack = getMenuStack();

        VBox cardContent = new VBox();
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("name-label");
        HBox statusRow = getStatusRow();
        cardContent.getChildren().addAll(nameLabel, statusRow);
        cardContent.getStyleClass().add("card-content");

        root.getChildren().addAll(menuStack, cardContent);
        root.getStyleClass().add("member-card");

        return root;
    }

    private HBox getSearchBar()
    {
        HBox root = new HBox();

        TextField searchTextField = new TextField();
        searchTextField.promptTextProperty().set("Seach Name...");

        Button searchButton = new Button("Search");

        root.getChildren().addAll(searchTextField, searchButton);
        root.getStyleClass().add("search-bar");
        return root;
    }
    private HBox getFilterRow()
    {
        HBox filterRow = new HBox();
        Button customerFilterButton = new Button("Customers");
        Button memberFilterButton = new Button("Member/VIP");

        customerFilterButton.getStyleClass().add("filter-button");
        memberFilterButton.getStyleClass().add("filter-button");
        filterRow.getChildren().addAll(customerFilterButton, memberFilterButton);
        filterRow.getStyleClass().add("filter-row");

        return filterRow;
    }

    private VBox getFilterHeader()
    {
        VBox root = new VBox();

        root.getChildren().addAll(getFilterRow(), getSearchBar());
        root.getStyleClass().add("filter-header");

        return root;
    }

    private ScrollPane getListPanel()
    {
        ScrollPane listPanel = new ScrollPane();
        VBox list = new VBox();

        for (int i = 0; i < 10; i++)
        {
            list.getChildren().add(getCard(Integer.toString(i)));
        }

        list.getStyleClass().add("members-box");

        listPanel.setContent(list);
        listPanel.getStyleClass().add("member-scroll-panel");

        return listPanel;
    }

    private VBox getListView()
    {
        VBox filterHeader = getFilterHeader();

        ScrollPane listPanel = getListPanel();

        Button addMemberButton = new Button("[add_icon]");
        addMemberButton.getStyleClass().add("add-button");

        StackPane listStack = new StackPane();
        listStack.getChildren().addAll(listPanel, addMemberButton);

        VBox root = new VBox();
        root.getChildren().addAll(filterHeader, listStack);
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

    private VBox getMemberNameTitle(String name)
    {
        VBox title = new VBox();
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("name-label");

        HBox statusRow = getStatusRow();

        title.getChildren().addAll(nameLabel, statusRow);
        title.getStyleClass().add("detail-title");

        return title;
    }



    private GridPane getMemberInfo(String name)
    {
        GridPane userInfo = new GridPane();

        Label idIcon = new Label("[idIcon]");
        Label phoneIcon = new Label("[phoneIcon]");
        Label pointIcon = new Label("[poinIcon]");
        GridPane.setConstraints(idIcon, 0, 0);
        GridPane.setConstraints(phoneIcon, 0, 1);
        GridPane.setConstraints(pointIcon, 0, 2);

        Label id = new Label("id");
        Label phone = new Label("phone");
        Label point = new Label("point");
        GridPane.setConstraints(id, 1, 0);
        GridPane.setConstraints(phone, 1, 1);
        GridPane.setConstraints(point, 1, 2);

        userInfo.getChildren().addAll(idIcon, phoneIcon, pointIcon, id, phone, point);
        userInfo.getStyleClass().add("user-info");
        return userInfo;
    }

    private GridPane getPreviewList()
    {
        GridPane root = new GridPane();

        for (int i = 0; i < transactionPreviewCount; i++)
        {
             Label dateTime = new Label("06:99, 2" + i + "January" + "2023");
             GridPane.setConstraints(dateTime, 0, i);
             root.getChildren().add(dateTime);
        }

        for (int i = 0; i < transactionPreviewCount; i++)
        {
            Label id = new Label("TRANSACTIONID" + i);
            GridPane.setConstraints(id, 1, i);
            root.getChildren().add(id);
        }

        return root;
    }
    private VBox getTransactionPreview()
    {
        VBox transactionPreviewPanel = new VBox();
        Label transactionTitle = new Label("Transactions");
        transactionTitle.getStyleClass().add("title");

        transactionPreviewPanel.getChildren().add(transactionTitle);

        transactionPreviewPanel.getChildren().add(getPreviewList());

        Button showTransactionsButton = new Button("See More");

        transactionPreviewPanel.getChildren().add(showTransactionsButton);
        transactionPreviewPanel.getStyleClass().add("transaction-preview");
        return transactionPreviewPanel;
    }

    private VBox getMemberDetailView(String name)
    {
        VBox title = getMemberNameTitle(name);
        GridPane userInfo = getMemberInfo(name);
        VBox transactionPreviewPanel = getTransactionPreview();

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
