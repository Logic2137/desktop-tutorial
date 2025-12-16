package Final_Assignment_For_Java;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Stream;

public class ShoppingCartGui extends Application {
    private BorderPane borderPane;
    private GridPane newItemPane, radioButtonPane, leftButtonsGrid;
    private ObservableList<String> cartItems;
    private VBox topBox, leftBox, bottomBox, rightBox, finishBox;
    private Button checkOutButton, emptyCartButton, addButton, newCartButton;
    private Label thankYouLabel, headerLabel, listViewHeader, currentTotalLabel,
            currentTotalString, newItemHeader, itemPriceLabel, errorLabel;
    private ListView<String> listView;
    private CheckBox addFiveCheck;
    private ToggleGroup priceGroup;
    private ComboBox<String> itemType;
    private TextField itemNameField;
    private Stream<Price> priceStream;
    private int columnCounter, listNumber;
    private Price price;
    private ShoppingCart cart;

    public ShoppingCartGui() {
        // Default constructor
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("5/10/15/20 Store");
        cart = new ShoppingCart();

        // Initiate all VBoxes/GridPanes that will be added to the borderPane
        topBox = new VBox();
        leftBox = new VBox();
        bottomBox = new VBox();
        rightBox = new VBox();
        finishBox = new VBox();
        newItemPane = new GridPane();
        radioButtonPane = new GridPane();
        leftButtonsGrid = new GridPane();

        makeUI();
        manageLayout();
        addButtonListeners();
        addUIListeners();

        // Create main border pane & add associated VBoxes
        borderPane = new BorderPane();
        borderPane.setTop(topBox);
        borderPane.setLeft(leftBox);
        borderPane.setRight(rightBox);
        borderPane.setBottom(bottomBox);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 启动购物车项目列表的UI组件、“项目价格”单选按钮组、添加5个项目的复选框、项目类型的组合框和项目描述的文本字段。
     */
    public void makeUI() {

        cartItems = FXCollections.observableArrayList();
        listView = new ListView<String>(cartItems); // List of cart items
        listView.setFocusTraversable(false);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listNumber = 1;

        priceGroup = new ToggleGroup(); // Radio buttons for choosing a price

        addFiveCheck = new CheckBox("将5个本商品添加到我的购物车当中");
        addFiveCheck.setDisable(true);

        itemType = new ComboBox(); // Combo box for choosing item type
        itemType.setValue("请选择商品类型");
        itemType.getItems().addAll("普通类", "杂货类", "药品类");

        itemNameField = new TextField(); // User-entered item name/description
        itemNameField.setDisable(true);

        priceStream = Arrays.stream(Price.values());

        makeLabelsAndButtons();
        makeUIAesthetic();

    }

    /**
     * 初始化UI中的所有标签和按钮
     * BINDING-currentTotalLabel绑定到currentTotalString，后者永远不可见，但在用户向购物车添加新项目后，会用当前总价进行更新。
     */
    public void makeLabelsAndButtons() {
        checkOutButton = new Button("结算购物车");
        addButton = new Button("添加到购物车中");
        emptyCartButton = new Button("清空购物车");
        newCartButton = new Button("创建一个新购物车");

        addButton.setDisable(true); // Enabled after user has entered new item info
        checkOutButton.setDisable(true); // Enabled when there are items in cart
        emptyCartButton.setDisable(true); // Enabled when there are items in cart

        errorLabel = new Label(" "); // Text updates if user enters bad input
        thankYouLabel = new Label(" "); // Updates/appears at checkout

        // Header labels
        headerLabel = new Label("欢迎来到5/10/15/20元店!");
        listViewHeader = new Label("你的购物车中的商品");
        newItemHeader = new Label("创建新商品");
        itemPriceLabel = new Label("商品价格:");

        // BINDING
        currentTotalString = new Label("待付金额: $0.00");
        currentTotalLabel = new Label();
        currentTotalLabel.textProperty().bind(currentTotalString.textProperty());
        currentTotalLabel.setFont(Font.font(18));
        currentTotalLabel.setPadding(new Insets(20,0,10,0));

    }

    /**
     * 为UI的所有组件设置填充、字体、样式、宽度/高度等。
     */
    public void makeUIAesthetic() {

        // ----------- LABELS --------------
        errorLabel.setPadding(new Insets(20));
        errorLabel.setFont(Font.font(28));
        errorLabel.setTextFill(Color.RED);
        thankYouLabel.setPadding(new Insets(0,0,20,0));
        thankYouLabel.setFont(Font.font(28));
        thankYouLabel.setStyle("-fx-text-alignment: center");
        headerLabel.setPadding(new Insets(20));
        headerLabel.setFont(Font.font(28));
        listViewHeader.setPadding(new Insets(5, 0, 5, 0));
        listViewHeader.setFont(Font.font(14));
        newItemHeader.setFont(Font.font(18));

        addFiveCheck.setPadding(new Insets(0,0,25,0)); // Spacing for check box
        addFiveCheck.setAlignment(Pos.CENTER_LEFT);

        listView.setMaxHeight(130); // Height/width of list of cart items
        listView.setMinWidth(300);
        itemType.setMinWidth(100);

        itemNameField.setMinWidth(250); 							 // Textfield for item name/description:
        itemNameField.setStyle("-fx-prompt-text-fill: "			 // Make sure prompt text is visible even
                + "derive(-fx-control-inner-background, -30%);"); // if the text field is selected/in focus
        itemNameField.setPromptText("商品名称/描述");

        // -------- BUTTONS -----------
        checkOutButton.setMinWidth(145);
        checkOutButton.setMinHeight(35);
        emptyCartButton.setMinWidth(145);
        emptyCartButton.setMinHeight(35);
        addButton.setMinWidth(200);
        addButton.setMinHeight(35);
        newCartButton.setMinWidth(145);
        newCartButton.setMinHeight(35);
        addButtonDropShadows(checkOutButton);
        addButtonDropShadows(addButton);
        addButtonDropShadows(emptyCartButton);
        addButtonDropShadows(newCartButton);

    }

    /**
     * 将所有节点添加到适当的网格/VBoxes（单选按钮除外-请看makeRadioButtons（）方法）
     */
    public void manageLayout() {

        makeRadioButtons();
        makeLayoutAesthetic();

        leftButtonsGrid.add(checkOutButton, 0,0);
        leftButtonsGrid.add(emptyCartButton, 1, 0);

        newItemPane.add(itemType, 0, 1);
        newItemPane.add(itemNameField, 1, 1);

        topBox.getChildren().add(headerLabel);
        bottomBox.getChildren().addAll(errorLabel);

        leftBox.getChildren().addAll(listViewHeader, listView,
                currentTotalLabel, leftButtonsGrid);

        rightBox.getChildren().addAll( newItemHeader, newItemPane,
                radioButtonPane, addFiveCheck, addButton );

        finishBox.getChildren().addAll(thankYouLabel, newCartButton);

    }

    /**
     * STREAM（）方法-对于每个Price枚举，创建一个新的单选按钮并将其添加到priceGroup toggle组中。
     * 在将位于newItemPane下的新网格窗格中的一行中添加所有内容。
     */
    public void makeRadioButtons() {
        radioButtonPane.add(itemPriceLabel, 0, 0);
        columnCounter = 1;
        priceStream.forEach(price -> {

            RadioButton b = new RadioButton(price.getPriceString());
            b.setUserData(price);
            b.setDisable(true); // Enabled after user types in item name textfield
            radioButtonPane.add(b, columnCounter, 0);
            priceGroup.getToggles().add(b);
            columnCounter++;

            // Enable the add 5 checkbox & add item button once a radio button is selected
            b.setOnMouseClicked( e -> {
                addFiveCheck.setDisable(false);
                addButton.setDisable(false);
            });

        });
    }

    /**
     * VBoxes和GridPanes的填充/对齐/间距。
     */
    public void makeLayoutAesthetic() {

        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20, 0, 0, 0));
        leftBox.setAlignment(Pos.TOP_CENTER);
        leftBox.setPadding(new Insets(0, 40, 0, 40));
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        rightBox.setPadding(new Insets(20, 40, 0, 20));
        rightBox.setAlignment(Pos.TOP_CENTER);
        finishBox.setPadding(new Insets(60, 70, 0, 20));
        finishBox.setAlignment(Pos.TOP_CENTER);

        newItemPane.setAlignment(Pos.TOP_LEFT);
        newItemPane.setPadding(new Insets(15, 0, 25, 0));
        newItemPane.setHgap(15);
        newItemPane.setVgap(10);

        radioButtonPane.setAlignment(Pos.TOP_LEFT);
        radioButtonPane.setPadding(new Insets(5, 0, 25, 0));
        radioButtonPane.setHgap(15);
        radioButtonPane.setVgap(10);

        leftButtonsGrid.setHgap(10);
    }

    /** 所有按钮的操作事件处理程序。
     * checkOutButton: 清空购物车，将rightBox（所有新项目控件）替换为finishBox（感谢标签并创建新购物车按钮）
     * emptyCartButton: 从购物车中删除所有的项目 & 可查看购物车列表
     * addButton: 仅当用户输入了非空的项描述时，才调用addItem（）
     * newCartButton: 使用新的项控件将窗口设置回原始设置。 */
    public void addButtonListeners() {

        checkOutButton.setOnAction( e -> {
            thankYouLabel.setText("感谢你的购买！\n 本次购物总金额是 $"
                    + cart.calculateTotalCost() + ".");
            emptyCartHelper();
            borderPane.setRight(finishBox);
        });

        emptyCartButton.setOnAction( e -> {
            emptyCartHelper();
        });

        addButton.setOnAction( e -> {
            String name = itemNameField.getText();

            if(!name.isEmpty() && !(name.indexOf(" ") == 0)) {
                addItem(name);
                if ( addFiveCheck.isSelected() ) { // add four more if check box is checked
                    for ( int i = 0; i < 4; i++ ) {
                        addItem(name);
                    }
                }
                reset();
            } else {
                reset();
                errorLabel.setText("请输入一个合法的商品名称！");
            }

            checkOutButton.setDisable(false);
            emptyCartButton.setDisable(false);
        });

        newCartButton.setOnAction( e -> {
            borderPane.setRight(rightBox);
        });
    }

    /**
     * MOUSE EVENT HANDLER - 当鼠标悬停在按钮上时，为按钮添加阴影。
     * @param b: 要应用放置阴影的按钮
     */
    private void addButtonDropShadows(Button b) {

        b.setOnMouseEntered( e -> {
            DropShadow d = new DropShadow(5, Color.DARKGRAY);
            b.setEffect(d);
        });

        b.setOnMouseExited( e -> {
            b.setEffect(null);
        });

    }

    /**
     * 移除购物车中所有商品、看用户列表; 再设置用户列表
     */
    public void emptyCartHelper() {
        cartItems.removeAll(cartItems);
        cart.empty();
        listNumber = 1;
        reset();
    }

    /**
     * 将所有用户控件重置为原始禁用/选定等设置
     */
    public void reset() {
        checkOutButton.setDisable(true);
        emptyCartButton.setDisable(true);
        currentTotalString.setText("待付金额: $" + cart.calculateTotalCost());
        itemNameField.setText(null);
        itemNameField.setPromptText("商品名称/描述");
        itemNameField.setDisable(true);
        errorLabel.setText(" ");
        itemType.setValue("请选择商品类型");
        priceGroup.selectToggle(null);
        addButton.setDisable(true);
        addFiveCheck.setSelected(false);
        for (Toggle t : priceGroup.getToggles()) {
            if (t instanceof RadioButton) {
                ((RadioButton) t).setDisable(true);
            }
        }
        addFiveCheck.setDisable(true);
    }

    /**
     * 根据用户向购物车中添加常规、杂货或药房项目
     * 从itemType组合框中选择。
     * 将新项目的名称和价格添加到可查看的cartItems列表中。
     * @param aName:用户在itemNameField textfield中输入了文本
     */
    public void addItem(String aName) {
        price = (Price) priceGroup.getSelectedToggle().getUserData();
        String name = aName;

        if ( itemType.getValue().equals("普通类") ) {
            cart.addGeneralItem(name, price);
            cartItems.add(listNumber + ". " + cart.item.getName() + " (普通类) - "
                    + cart.item.getPrice().getPriceString());
        } else if ( itemType.getValue().equals("杂货类") ) {
            cart.addGroceryItem(name, price);
            cartItems.add(listNumber + ". " + cart.item.getName() + " (杂货类) - "
                    + cart.item.getPrice().getPriceString());
        } else if ( itemType.getValue().equals("药品类") ) {
            cart.addPharmacyItem(name, price);
            cartItems.add(listNumber + ". " + cart.item.getName() + " (药品类) - "
                    + cart.item.getPrice().getPriceString());
        } else {
            cart.addGeneralItem(name, price);
            cartItems.add(listNumber + ". " + cart.item.getName() + " (普通类) - "
                    + cart.item.getPrice().getPriceString());
        }

        listNumber++;

    }

    /**
     * 其他UI组件的事件处理程序
     * itemType：当用户从itemType组合框中选择一个选项时，启用并设置焦点
     * 在项目描述文本字段中。
     * 键事件处理程序-项目名称字段：当用户在项目描述文本字段中键入时，启用所有单选按钮。
     */
    public void addUIListeners() {
        itemType.setOnAction( e -> {
            if (!itemType.getValue().equals("请选择商品类型")) {
                itemNameField.setDisable(false);
                itemNameField.requestFocus();
            }
        });

        itemNameField.setOnKeyTyped( k -> {
            for (Toggle t : priceGroup.getToggles()) {
                if (t instanceof RadioButton) {
                    ((RadioButton) t).setDisable(false);
                }
            }
        });
    }

    public void go() {
        launch();
    }

}

/**
 * 这是包含main方法的类。Main创建的一个实例ShoppingCartController.java，并调用它的“go()”方法
 */

class ShoppingCartApplication {
    public static void main(String[] args) {
        ShoppingCartGui gui = new ShoppingCartGui();
        gui.go();
        System.exit(0);
    }
}


class ShoppingCart {
    LinkedList<Product> list;
    ListIterator<Product> iterator;
    Map<Product, Price> map;
    GeneralItem item;
    Price price;

    /**
     * 这是MVC设计的“模型”部分。它有一个构造函数
     * 初始化全局变量“LinkedList<Product>list”、“ListIterator<Product>iterator”和“Map<Product，Price>Map”
     */
    public ShoppingCart() {
        list = new LinkedList<>();
        map = new LinkedHashMap<>();
        iterator = list.listIterator();
    }

    /**
     * 从ShoppingCartController获取产品名称和价格，并使用它们创建
     * 新的购物车。然后，它将ShoppingCartItem添加到列表和映射中。
     */
    public void addGeneralItem(String aName, Price price2) {
        price = price2;
        item = new GeneralItem(aName, price2);
        list.add(item);
        map.put(item, price);
    }

    public void addGroceryItem(String aName, Price price2) {
        price = price2;
        item = new GroceryItem(aName, price);
        list.add(item);
        map.put(item, price);
    }

    public void addPharmacyItem(String aName, Price price2) {
        price = price2;
        item = new PharmacyItem(aName, price);
        list.add(item);
        map.put(item, price);
    }

    /**
     * 从列表和映射中删除所有项。
     */
    public void empty() {
        while (!list.isEmpty()) {
            list.removeFirst();
            map.clear();
        }
    }

    /**
     * @return 整个列表
     */
    public LinkedList<Product> getList() {
        return list;
    }

    /**
     * @return 用户购物车中项目数的整数值
     */
    public int listSize() {
        int size = list.size();
        return size;
    }

    /**
     * 获取用户购物车中每个项目的价格值，然后将它们相加并返回总和。
     */
    public String calculateTotalCost(){
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getPrice().price;
        }
        return Double.toString(sum) + "0";
    }

    /**
     * 接受已实例化的链接列表，并将当前列表设置为与之相等。
     */
    public void setList(LinkedList<Product> aList) {
        list = aList;
    }

    /**
     * @return 列表中的所有内容
     */
    @Override
    public String toString() {
        String a = "";
        Product temp;
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            a += temp.toString() + " ";
        }
        return a;
    }
}

// 12/23/2020

/**
 * 这是我们的接口类。它是购物车、食品杂货和医药用品的蓝图
 */

interface Product {

    /**
     * @return 用户购物车中项目的字符串值名称。
     */
    public String getName();

    /**
     * @return 与用户购物车中的项目关联的价格枚举。
     */
    public Price getPrice();

    /**
     * 将用户购物车中项目的字符串值名称设置为等于aName。
     */
    public void setName(String aName);

    /**
     * 将价格值设置为等于aPrice。
     */
    public void setPrice(int aPrice);

    /**
     * 用于区分ShoppingCartItem的重写的toString方法，
     * 食品杂货和药品相互联系。
     */
    @Override
    public String toString();

}


enum Price {

    FIVE(5.00), TEN(10.00), FIFTEEN(15.00), TWENTY(20.00);

    public double price;

    Price(double price) {
        this.price = price;
    }

    Price(Price p){

        p.price = price;

    }

    /**
     * @return 与所需枚举关联的双精度值。
     */
    public double getPrice() {

        switch (this) {
            case FIVE:
                return FIVE.price;
            case TEN:
                return TEN.price;
            case FIFTEEN:
                return FIFTEEN.price;
            case TWENTY:
                return TWENTY.price;
            default:
                return 0;
        }

    }

    public String getPriceString() {
        switch (this) {
            case FIVE:
                return "$5.00";
            case TEN:
                return "$10.00";
            case FIFTEEN:
                return "$15.00";
            case TWENTY:
                return "$20.00";
            default:
                return "";
        }
    }

}

class GeneralItem implements Product {

    private String name;
    private Price price;

    public GeneralItem(String aName, Price aPrice) {
        this.name = aName;
        this.price = aPrice;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    public void setName(String aName) {
        name = aName;
    }

    @Override
    public void setPrice(int aPrice) {
        price.price = aPrice;
    }

    @Override
    public String toString() {
        return "General Item [name = " + name + ", price = $" + price.getPrice() + "]" + "\n";
    }

}

class PharmacyItem extends GeneralItem {

    public PharmacyItem(String aName, Price aPrice) {
        super(aName, aPrice);
    }

    @Override
    public String toString() {
        return "Pharmacy Item [name = " + super.getName()+ ", price = $" + super.getPrice().price + "]" + "\n";
    }


}

class GroceryItem extends GeneralItem {

    public GroceryItem(String aName, Price aPrice) {
        super(aName, aPrice);
    }

    @Override
    public String toString() {
        return "Grocery Item [name = " + super.getName() + ", price = $" + super.getPrice().price + "]" + "\n";
    }

}
