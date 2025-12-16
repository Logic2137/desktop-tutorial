package Final_Assignment_For_Java;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClassToHandleLargeNumbers extends Application {
    public void start(Stage primaryStage) {
        HBox textLine = new HBox();
        HBox buttonLine = new HBox();
        textLine.setAlignment(Pos.CENTER);
        textLine.setPadding(new Insets(10, 10, 10, 10));
        buttonLine.setAlignment(Pos.CENTER);
        textLine.setSpacing(5);
        buttonLine.setSpacing(20);
        Text text1 = new Text("Number 1:");
        Text text2 = new Text("Number 2:");
        Text text3 = new Text("Result:");

        TextField field1 = new TextField();
        TextField field2 = new TextField();
        TextField field3 = new TextField();
        field3.setEditable(false);

        Button addButton = new Button("Add");
        Button subtractButton = new Button("Subtract");
        Button multiplyButton = new Button("Multiply");
        Button divideButton = new Button("Divide");
        Button zeroButton = new Button("Return to zero");

        textLine.getChildren().addAll(text1, field1, text2, field2, text3, field3);
        buttonLine.getChildren().addAll(addButton, subtractButton, multiplyButton, divideButton, zeroButton);

        BorderPane pane = new BorderPane();
        pane.setCenter(textLine);
        pane.setBottom(buttonLine);

        Scene scene = new Scene(pane, 800, 200);
        primaryStage.setTitle("Work15_4");
        primaryStage.setScene(scene);
        primaryStage.show();

        addButton.setOnAction(e->{                  //add按钮的事件处理器
            BigNumber number1 = new BigNumber(field1.getText());
            BigNumber number2 = new BigNumber(field2.getText());
            String str = number1.add(number2);
            field3.setText(str);
        });

        subtractButton.setOnAction(e->{             //subtract按钮的事件处理器
            BigNumber number1 = new BigNumber(field1.getText());
            BigNumber number2 = new BigNumber(field2.getText());
            String str = number1.subtract(number2);
            field3.setText(str);
        });

        multiplyButton.setOnAction(e->{             //multiply按钮的事件处理器
            BigNumber number1 = new BigNumber(field1.getText());
            BigNumber number2 = new BigNumber(field2.getText());
            String str = number1.multiply(number2);
            field3.setText(str);
        });

        divideButton.setOnAction(e->{               //divide按钮的事件处理器
            BigNumber number1 = new BigNumber(field1.getText());
            BigNumber number2 = new BigNumber(field2.getText());
            String str = number1.divide(number2);
            field3.setText(str);
        });

        zeroButton.setOnAction(e -> {               //归零按钮的事件处理器
            field1.setText("");
            field2.setText("");
            field3.setText("");

        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

//BigNumber是一个大数类，可计算无穷大的数字加减乘除
class BigNumber {
    private String value;

    public BigNumber() {
        value = "0";
    }    //无参构造方法
    public BigNumber(String value) {
        this.value = value;
    }       //有参构造

    public String getValue() {
        return value;
    }
    public void setValue(String newValue) {
        this.value = newValue;
    }

    public String add(BigNumber newNumber) {             //加法方法
        String result = "", tempOfString;
        int number1, number2, remainder = 0, temp;
        int length1 = this.value.length();
        int length2 = newNumber.value.length();
        int i, j;
        int flag = 1;

        if (this.value.contains("-") && !newNumber.value.contains("-")) {     //如果number1数有“-”号的话，立马转换成number2-number1的形式
            this.value = this.value.substring(1);         //把-号截取掉
            tempOfString = this.value;                    //交换number1和number2的位置
            this.value = newNumber.value;
            newNumber.value = tempOfString;
            return this.subtract(newNumber);              //采用number2-number1的方法
        }
        else if (!this.value.contains("-") && newNumber.value.contains("-")) {     //如果number2有负号而number1没有，就转换成number1-number2
            newNumber.value = newNumber.value.substring(1);
            return this.subtract(newNumber);
        }
        else if (this.value.contains("-") && newNumber.value.contains("-")) {
            this.value = this.value.substring(1);
            newNumber.value = newNumber.value.substring(1);
            length1--;
            length2--;
            flag = -1;
        }

        for (i = length1 - 1, j = length2 - 1; i >= 0 && j >= 0; i--, j--) {
            number1 = (this.value.charAt(i) - '0');      //从最低为开始加
            number2 = (newNumber.value.charAt(j) - '0');
            temp = (number1 + number2 + remainder) % 10;                 //求本位应该放什么数字
            result = temp + "" + result;
            remainder = (number1 + number2 + remainder>= 10) ? 1 : 0;    //进位的数字
        }

        if (i < 0 && j >= 0) {          //解决剩余位数字的加法
            while (j >= 0) {
                number2 = (newNumber.value.charAt(j) - '0');
                temp = (number2 + remainder) % 10;
                result = temp + "" + result;
                remainder = (number2 + remainder >= 10) ? 1 : 0;
                j--;
            }
        }
        else if (i >= 0 && j < 0) {
            while (i >= 0) {
                number1 = (this.value.charAt(i) - '0');
                temp = (number1 + remainder) % 10;
                result = temp + "" + result;
                remainder = (number1 + remainder >= 10) ? 1 : 0;
                i--;
            }
        }

        if (remainder != 0) {      //如果最后算的最高位不为零，就加在结果字符的开头
            result = remainder + "" + result;
        }

        if (flag == -1) {
            result = "-" + result;
        }

        return result;
    }

    public String subtract(BigNumber newNumber) {                //减法操作，把大的数字设置成number1，小的数字设置成number2，然后进行从低位到到高位的减法运算
        String result = "", tempOfString;
        int number1, number2, remainder = 0, temp, count = 0, flag = 1;
        int length1 = this.value.length();
        int length2 = newNumber.value.length();
        int i, j;

        if (this.value.contains("-") && !newNumber.value.contains("-")) {
            this.value = this.value.substring(1);
            result = "-" + this.add(newNumber);
            return result;
        }
        else if (newNumber.value.contains("-") && !this.value.contains("-")) {
            newNumber.value = newNumber.value.substring(1);
            return this.add(newNumber);
        }
        else if (newNumber.value.contains("-") && this.value.contains("-")) {
            this.value = this.value.substring(1);
            newNumber.value = newNumber.value.substring(1);
            tempOfString = newNumber.value;
            newNumber.value = this.value;
            this.value = tempOfString;
            length1 = this.value.length();
            length2 = newNumber.value.length();
        }

        if (length1 < length2) {
            tempOfString = newNumber.value;
            newNumber.value = this.value;
            this.value = tempOfString;
            length1 = this.value.length();
            length2 = newNumber.value.length();
            flag = -1;
        }
        else if (length1 == length2) {
            for (int k = 0; k < length1; k++) {
                if (this.value.charAt(k) > newNumber.value.charAt(k)) {
                    break;
                }
                else if (this.value.charAt(k) < newNumber.value.charAt(k)) {
                    tempOfString = newNumber.value;
                    newNumber.value = this.value;
                    this.value = tempOfString;
                    flag = -1;
                    break;
                }
                count++;
            }
        }

        for (i = length1 - 1, j = length2 - 1; i >= count && j >= count; i--, j--) {
            number1 = (this.value.charAt(i) - '0');
            number2 = (newNumber.value.charAt(j) - '0');
            if (number1 - remainder < number2) {
                temp = number1 - remainder - number2 + 10;
                remainder = 1;
            }
            else {
                temp = number1 - remainder - number2;
                remainder = 0;
            }
            if (i != count || temp != 0)
                result = temp + "" + result;
        }

        while (count == 0 && i >= 0) {
            number1 = (this.value.charAt(i) - '0');
            number2 = 0;
            if (number1 - remainder < number2) {
                temp = number1 - remainder - number2 + 10;
                remainder = 1;
            }
            else {
                temp = number1 - remainder - number2;
                remainder = 0;
            }
            if (i != 0 || temp != 0)
                result = temp + "" + result;
            i--;
        }
        if (flag == -1)
            result = "-" + result;
        if (count == length1)
            return "0";
        else
            return result;
    }

    public String multiply(BigNumber newNumber) {              //采用了加法的相关操作，一位一位进行乘法运算，实际上就是将乘法分配为加法进行运算(以加法为基础)
        String result = "0", tempOfString,  mulSec = "";
        int i, j;
        int length1 = this.value.length();
        int length2 = newNumber.value.length();
        int flag = 1;

        if (this.value.contains("-")) {              //判断数字是否为-的操作
            this.value = this.value.substring(1);
            flag = -flag;
        }
        if (newNumber.value.contains("-")) {
            newNumber.value = newNumber.value.substring(1);
            flag = -flag;
        }

        if (this.value.equals("0") || newNumber.value.equals("0"))
            return "0";
        if (length1 < length2) {
            tempOfString = this.value;
            this.value = newNumber.value;
            newNumber.value = tempOfString;
            length1 = this.value.length();
            length2 = newNumber.value.length();
        }

        for (i = newNumber.value.length() - 1; i >= 0; i--) {
            String tempOfNumber = "0";
            for (j = newNumber.value.charAt(i) - '0'; j > 0; j--) {
                tempOfNumber = this.add(new BigNumber(tempOfNumber));
            }
            if (newNumber.value.charAt(i) != '0') {
                tempOfNumber = tempOfNumber + mulSec;
            }
            mulSec = mulSec + "0";
            result = new BigNumber(result).add(new BigNumber(tempOfNumber));
        }

        if (flag == -1) {
            result = "-" + result;
        }

        return result;
    }

    public String divide(BigNumber newNumber) {        //除法操作，就是每次都在number1数基础上减number2数，每减一次，结果result就加1，从而实现除法(以减法和加法为基础)
        String result = "0";
        String tempOfString, temp;
        /*
        this.value = this.value + ".0";
        newNumber.value = newNumber.value + ".0";
        BigDecimal number1 = new BigDecimal(this.value);
        BigDecimal number2 = new BigDecimal(newNumber.value);
        result = number1.divide(number2).toString();
        return result;
         */
        if (newNumber.value.equals("0"))
            return "False Input";
        else if (this.value.equals("0"))
            return "0";

        int flag = 1;
        if (this.value.contains("-")) {
            this.value = this.value.substring(1);
            flag = -flag;
        }
        if (newNumber.value.contains("-")) {
            newNumber.value = newNumber.value.substring(1);
            flag = -flag;
        }

        for ( ; !this.subtract(newNumber).contains("-"); this.value = this.subtract(newNumber)) {
            result = (new BigNumber(result)).add(new BigNumber("1"));
        }
        {
            temp = this.value;
            this.value = newNumber.value;
            newNumber.value = temp;
        }

        if (this.value.equals("0")){
            return result;
        }
        else {
            double remainder = Double.parseDouble(this.value);
            double divisor = Double.parseDouble(newNumber.value);
            double tempOfResult = remainder / divisor;
            tempOfString = tempOfResult + "";
            tempOfString = tempOfString.substring(1);
            result = result + tempOfString;
        }

        if (flag == -1) {
            result = "-" + result;
        }

        return result;

    }
}

//124444444444444444444444444222222222222222222333333333333333333333333333333       加法
//                     999999999999999999999999999999999999999999992341342143
//124444444444444444445444444222222222222222222333333333333333333325674675476

//11111111111111111111111111111111111111111       乘法
//400000000000000000000000000000000000000000000
//4444444444444444444444444444444444444444400000000000000000000000000000000000000000000

