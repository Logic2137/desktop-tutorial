package Final_Assignment_For_Java;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.beans.property.DoubleProperty;

public class TurtleGraphics extends Application{

    Text stateText = new Text("");                      //stateText文本变量显示海龟(画笔)在当前哪个点
    Text highText = new Text("抬起状态");               //highText文本显示画笔的抬起或是落下状态
    Text successText = new Text("等待操作");            //successText文本显示操作是否成功
    TextField instructText = new TextField();           //输入指令的文本框
    Pane finalPane = new Pane();                        //最底层的程序画板，所有控件都作用于这个画板上
    Point2D currentPoint = new Point2D(0 ,0);     //当前点对象，可以存储当前点在哪个位置
    boolean isDropPen = false;                          //记录画笔是否落下的变量
    Circle circle = new Circle(500,50,3);           //显示画笔当前位置的圆，当前点走到哪这个circle就走到哪
    Rectangle backgroundPane = new Rectangle(500,50, 400,400);          //网格画板的纯白背景对象


    public void start(Stage primaryStage) {

        VBox instructionPane = new VBox();                         //指令框面板(显示各种指令提示的面板)
        HBox statePane = new HBox();                               //实时显示当前点位置的面板
        HBox highPane = new HBox();                                //实时显示当前画笔高度的面板
        instructText.setEditable(true);                            //将指令输入框设置为可编辑类型
        instructionPane.setSpacing(18);                            //instructionPane之间的元素距离均为18
        circle.setFill(Color.RED);                         //画笔圆的颜色为红
        backgroundPane.setFill(Color.WHITE);               //将网格画板的纯白背景对象设置为白底颜色
        backgroundPane.setStroke(Color.WHITE);
        finalPane.getChildren().add(backgroundPane);

        Button instructButton = new Button("确认指令");          //各种指令的罗列
        Text text1 = new Text("请按以下提示输入指令:");
        Text text2 = new Text("1.抬起画笔: lift pen");
        Text text3 = new Text("2.落下画笔: drop pen");
        Text text4 = new Text("3.从当前点移动到(x,y)点: move to (x,y)");
        Text text5 = new Text("4.擦拭从点(x1,y1)到点(x2,y2)的直线: clear (x1, y1) to (x2, y2)");
        Text text6 = new Text("5.以当前点为中心画一个半径为r的圆: circle r");
        Text text7 = new Text("6.清除全屏: remove all");
        Text text8 = new Text("7.绘制五角星: draw star");
        Text text9 = new Text("8.退出本程序: exit application");
        Text text10 = new Text("画笔高度信息:  ");
        Text text11 = new Text("画笔位置信息:  ");

        text1.setFont(Font.font("宋体",FontWeight.BOLD, FontPosture.REGULAR, 15));
        instructionPane.getChildren().addAll(text1, text2, text3, text4, text5, text6, text7, text8, text9, instructText, instructButton);
        instructionPane.setLayoutX(20);
        instructionPane.setLayoutY(20);
        highPane.getChildren().addAll(text10, highText);
        statePane.getChildren().addAll(text11, stateText);
        highPane.setLayoutX(20);
        highPane.setLayoutY(450);
        statePane.setLayoutX(20);
        statePane.setLayoutY(470);
        successText.setLayoutX(110);
        successText.setLayoutY(387);                //操作是否成功文本的位置设定
        printGrid();                //绘制方格网画板
        stateText.setText("当前位置为" + "(" + (int)currentPoint.getX() + ", " + (int)currentPoint.getY() + ")");
        finalPane.getChildren().addAll(instructionPane, highPane, statePane, successText, circle);
        instructText.requestFocus();         //聚焦在instructText上

        Scene scene = new Scene(finalPane, 1000, 500);
        primaryStage.setTitle("Turtle Graphics Application");               //舞台的相关设置
        primaryStage.setScene(scene);
        primaryStage.show();

        //监听指令文本框
        instructText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                successText.setText("用户输入中......");
            }
        });

        //instructButton按钮单击事件，通过analyse()方法解决单击事件，判断输入的指令是什么
        instructButton.setOnAction(event -> {
            if (((instructText.getText()).toLowerCase()).contains("exit") && ((instructText.getText()).toLowerCase()).contains("application")) {
                primaryStage.close();
            }
            int analysis;
            analysis = analyse();                //将analyse()返回值给analysis,提示相关的操作信息
            instructText.setText("");
            if (analysis == -1) {
                successText.setText("操作失败, 请检查输入格式!");
            }
            else {
                successText.setText("操作成功!");
            }
        });
    }

    public void printGrid() {                               //绘制方格网画板的方法
        for (int i = 500; i <= 900; i = i + 20) {
            Line line = new Line(i, 50, i, 450);
            Label label = new Label((i - 500) / 20 + "");
            label.setFont(Font.font("Times New Roman", FontWeight.BLACK, 12));
            label.setAlignment(Pos.BASELINE_LEFT);
            label.setLayoutX(i-5);
            label.setLayoutY(35);
            finalPane.getChildren().addAll(line, label);
        }

        for (int i = 50; i <= 450; i = i + 20) {
            Line line = new Line(500, i, 900, i);

            Label label = new Label((i-50) / 20 + "");
            label.setLayoutX(485);
            label.setLayoutY(i-5);
            label.setFont(Font.font("Times New Roman", FontWeight.BLACK, 12));
            label.setAlignment(Pos.TOP_CENTER);
            finalPane.getChildren().addAll(line, label);
        }

    }

    public TextField getInstructText() {
        return instructText;
    }

    //本程序的主方法
    public static void main(String[] args) {
        Application.launch(args);
    }

    //分析指令的方法
    public int analyse() {
        String string, tempX, tempY, tempRadius;
        int firstLeft, firstRight;           //firstLeft表示第一个左括号在字符串中的位置，firstRight表示第一个右括号位置
        int secondLeft, secondRight;         //同上
        int firstD, secondD;                 //firstD表示第一个逗号位置，secondD表示第二个逗号位置
        int startX, startY;                  //startX起始点X在图上的坐标，startY起始点Y坐标
        int targetX, targetY;                //targetX终点X坐标，同上
        int firstWord, secondWord;           //firstWord第一个单词在字符串中的位置
        double radius;                   //表示半径
        int analysis = -1;               //获得的指令信息初始为-1，即为未获得有效指令
        if (((instructText.getText()).toLowerCase()).contains("lift") && ((instructText.getText()).toLowerCase()).contains("pen")) {     //判断是否为lift pen指令
            firstWord = ((instructText.getText()).toLowerCase()).indexOf("lift");
            secondWord = ((instructText.getText()).toLowerCase()).indexOf("pen");
            if (firstWord < secondWord) {      //lift在pen之前，则本指令成立
                analysis = 1;          //1就是获得的指令的序号，没有什么实际意义
                liftPen();             //抬笔方法
            }
        }
        else if (((instructText.getText()).toLowerCase()).contains("drop") && ((instructText.getText()).toLowerCase()).contains("pen")) {    //判断是否为drop pen指令
            firstWord = ((instructText.getText()).toLowerCase()).indexOf("drop");
            secondWord = ((instructText.getText()).toLowerCase()).indexOf("pen");
            if (firstWord < secondWord) {
                analysis = 2;
                dropPen();
            }
        }
        else if (((instructText.getText()).toLowerCase()).contains("move") && ((instructText.getText()).toLowerCase()).contains("to")) {   //move to指令
            string = instructText.getText();
            firstWord = ((instructText.getText()).toLowerCase()).indexOf("move");
            secondWord = ((instructText.getText()).toLowerCase()).indexOf("to");
            firstLeft = instructText.getText().indexOf("(");
            firstRight = instructText.getText().indexOf(")");
            firstD = instructText.getText().indexOf(",");
            if (firstWord < secondWord && firstLeft < firstRight && secondWord < firstLeft && firstD > firstLeft && firstD < firstRight) {
                analysis = 3;
                tempX = string.substring(firstLeft+1, firstD);      //获得表示X坐标的字符串
                tempX = tempX.trim();
                tempY = string.substring(firstD+1, firstRight);     //获得表示Y坐标的字符串
                tempY = tempY.trim();
                targetX = Integer.parseInt(tempX);         //把X字符串的值转换为int型，方便计算
                targetY = Integer.parseInt(tempY);
                moveLine(targetX, targetY);           //进入移动方法
            }
        }
        else if (((instructText.getText()).toLowerCase()).contains("clear") && ((instructText.getText()).toLowerCase()).contains("to")) {     //擦拭直线的指令判断
            string = instructText.getText();
            firstLeft = string.indexOf("(");
            firstRight = string.indexOf(")");
            firstD = string.indexOf(",");
            secondLeft = string.indexOf("(", firstLeft+1);
            secondRight = string.indexOf(")", firstRight+1);
            secondD = string.indexOf(",", firstD+1);                       //clear (3, 4) to (9, 8)
            if (firstLeft < firstD && firstD < firstRight && firstRight < secondLeft && secondLeft < secondD && secondD < secondRight) {
                analysis = 4;
                tempX = string.substring(firstLeft+1, firstD);                //同上，因为指令里有两个坐标，所有操作较多
                tempX = tempX.trim();
                tempY = string.substring(firstD+1, firstRight);
                tempY = tempY.trim();
                startX = Integer.parseInt(tempX);
                startY = Integer.parseInt(tempY);

                tempX = string.substring(secondLeft+1, secondD);
                tempX = tempX.trim();
                tempY = string.substring(secondD+1, secondRight);
                tempY = tempY.trim();
                targetX = Integer.parseInt(tempX);
                targetY = Integer.parseInt(tempY);

                clearLine(startX, startY, targetX, targetY);
            }
        }
        //以下为画圆的判断
        else if (((instructText.getText()).toLowerCase()).contains("circle")) {
            string = instructText.getText();
            firstD = string.indexOf("circle");
            tempRadius = string.substring(firstD+6, string.length()).trim();
            radius = Double.parseDouble(tempRadius);
            startX = (int)currentPoint.getX();
            startY = (int)currentPoint.getY();
            if (startX - radius >= 0 && startX + radius <= 20 && startY - radius >= 0 && startY + radius <= 20) {
                analysis = 5;
                drawCircle(startX, startY, radius);
            }
            else {
                stateText.setText("当前点位置无法绘制半径为" + radius + "的圆,请重新设计尺寸!");
            }
        }
        //以下为清除全屏的操作
        else if (((instructText.getText()).toLowerCase()).contains("remove") && ((instructText.getText()).toLowerCase()).contains("all")) {
            analysis = 6;
            finalPane.getChildren().remove(backgroundPane);
            finalPane.getChildren().add(backgroundPane);
            printGrid();
            finalPane.getChildren().remove(circle);
            finalPane.getChildren().add(circle);
        }
        //以下为绘制五角星的操作
        else if (((instructText.getText()).toLowerCase()).contains("draw") && ((instructText.getText()).toLowerCase()).contains("star")) {
            startX = (int)currentPoint.getX();
            startY = (int)currentPoint.getY();
            if (startX + 3 <= 20 && startX >= 0 && startY + 2 <= 20 && startY >= 0) {
                analysis = 7;
                drawStar(startX, startY);
            }
            else {
                stateText.setText("当前点位置无法绘制五角星,请重新设计点位置!");
            }
        }

        return analysis;
    }

    public void liftPen() {
        isDropPen = false;
        highText.setText("抬起状态");
    }

    public void dropPen() {
        isDropPen = true;
        highText.setText("落下状态");
    }

    public void moveLine(int targetX, int targetY) {
        if (isDropPen) {
            Line line = new Line(currentPoint.getX() * 20 + 500, currentPoint.getY() * 20 + 50, targetX * 20 + 500, targetY * 20 + 50);
            line.setStrokeWidth(3);
            line.setStroke(Color.BLUE);
            finalPane.getChildren().add(line);
        }
        finalPane.getChildren().remove(circle);         //先将circle从finalPane，方便更新时插入新值
        circle.setCenterX(targetX * 20 + 500);          //更新当前点X值
        circle.setCenterY(targetY * 20 + 50);           //更新当前点Y值
        currentPoint = new Point2D(targetX, targetY);      //更新currentPoint点的信息
        finalPane.getChildren().add(circle);
        stateText.setText("当前位置为" + "(" + (int)currentPoint.getX() + ", " + (int)currentPoint.getY() + ")");
    }

    public void clearLine(int startX, int startY, int targetX, int targetY) {         //本操作实际上是通过白色的线覆盖在原有线基础上，所进行的擦拭操作
        Line whiteLine = new Line(startX * 20 + 500, startY * 20 + 50, targetX * 20 + 500, targetY * 20 + 50);
        whiteLine.setStroke(Color.WHITE);
        whiteLine.setFill(Color.WHITE);
        whiteLine.setStrokeWidth(4);
        finalPane.getChildren().add(whiteLine);
        printGrid();
        finalPane.getChildren().remove(circle);
        finalPane.getChildren().add(circle);
    }

    public void drawCircle(int startX, int startY, double radius) {
        Circle circleShape = new Circle(startX * 20 + 500, startY * 20 + 50, radius * 20);
        circleShape.setFill(null);
        circleShape.setStrokeWidth(3);
        circleShape.setStroke(Color.LIME);
        finalPane.getChildren().add(circleShape);
    }

    public void drawStar(double startX, double startY) {    //绘制五角星的操作，通过Polygon一步一步添加的，没有什么逻辑值
        Polygon star = new Polygon();
        star.setFill(Color.RED);
        star.setStroke(Color.RED);
        ObservableList<Double> list = star.getPoints();
        startX = startX * 20 + 500;
        startY = startY * 20 + 50;
        list.add(startX);
        list.add(startY);
        list.add(startX + 20);
        list.add(startY);
        list.add(startX + 30);
        list.add(startY - 20);
        list.add(startX + 40);
        list.add(startY);
        list.add(startX + 60);
        list.add(startY);

        list.add(startX+45);
        list.add(startY+20);
        list.add(startX+50);
        list.add(startY+40);
        list.add(startX+30);
        list.add(startY+20);
        list.add(startX+10);
        list.add(startY+40);
        list.add(startX+15);
        list.add(startY+20);
        finalPane.getChildren().add(star);
    }
}
