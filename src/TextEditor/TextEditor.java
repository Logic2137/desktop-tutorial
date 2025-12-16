package Final_Assignment_For_Java.TextEditor;


import java.io.File;
import java.net.URL;
import java.util.Calendar;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.applet.Applet;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.shape.Line;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;
import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.net.MalformedURLException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.control.SelectionMode;
import javafx.beans.property.SimpleIntegerProperty;

public class TextEditor extends Application
{
	String s=null;
	@Override
	public void start(Stage primaryStage)
	{
		BorderPane pane=new BorderPane();
		HBox HB=new HBox();
		HB.setSpacing(20);
		HB.setPadding(new Insets(10,10,10,10));
		HB.setAlignment(Pos.CENTER);
		
		HBox warm=new HBox();
		warm.setSpacing(20);
		warm.setPadding(new Insets(10,10,10,10));
		
		VBox body=new VBox();
		body.setSpacing(20);
		body.setPadding(new Insets(10,10,10,10));
		
		Label sign=new Label("请输入文件目录:");
		Label WarmWords=new Label("");
		TextField GetLocation=new TextField();
		Button research=new Button("寻找本地文件");
		
		research.setOnAction
		(
			e->
			{
				OpenFile.ShowPane("C:/Users/Logic.G/Desktop");
				primaryStage.close();
			}
		);
		GetLocation.setOnKeyPressed
		(
			e->
			{
				if(e.getCode()==KeyCode.ENTER)
				{
					try
					{
						s=GetLocation.getText();
						OpenFile.ShowPane(s);
						primaryStage.close();
					}
					catch(NullPointerException ex)
					{
						WarmWords.setText("无效的文件路径");
					}
				}	
			}
		);
		
		HB.getChildren().addAll(sign,GetLocation,research);
		warm.getChildren().add(WarmWords);
		
		body.getChildren().addAll(HB,warm);
		Scene scene=new Scene(body);
		primaryStage.setScene(scene);
		primaryStage.setTitle("打开文件");
		primaryStage.show();
		
		GetLocation.requestFocus();
	}
	
	public static void main(String [] args)
	{
		Application.launch(args);
	}
}