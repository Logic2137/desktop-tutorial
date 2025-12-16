package Final_Assignment_For_Java.TextEditor;


import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.Calendar;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.applet.Applet;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Line;
import javafx.scene.image.Image;
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
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import java.net.MalformedURLException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.collections.FXCollections;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.SelectionMode;
import javafx.beans.property.SimpleIntegerProperty;

public class OpenTXT extends BorderPane
{
	private static String TEXT="";
	public static void FindFile(String FileRoad) throws NullPointerException
	{
		Scanner read=null;
		File target=new File(FileRoad);
		
		if( FileRoad.equals("无标题") )
			//创建新文件
			;
		else
		try
		{
			System.out.println(1);
			read=new Scanner(target);
			System.out.println(2);
			while(read.hasNext()) {
				setTEXT(getTEXT() + read.nextLine());
				System.out.println(3);
			}
		}
		catch (FileNotFoundException e)
		{
			Stage warming=new Stage();
			BorderPane P=new BorderPane();
			P.setPadding(new Insets(5,20,20,5));
			Label W=new Label("文件未找到");
			P.setCenter(W);
			Scene scene=new Scene(P);
			warming.setScene(scene);
			warming.setTitle("错误");
			warming.show();
			return;
		}
		ShowPane(target);
		read.close();
	}
	public static void ShowPane(File TargetFile)
	{
		Stage MainStage=new Stage();
		HBox HB_L=new HBox();
		HB_L.setAlignment(Pos.TOP_LEFT);
		HB_L.setPadding(new Insets(5,10,10,5));
		HBox HB_R=new HBox();
		HB_R.setAlignment(Pos.CENTER_RIGHT);
		HB_R.setPadding(new Insets(5,10,10,5));
		
		Image save=new Image("C:/Users/Logic.G/Desktop/save.jpg");
		ImageView SAVE=new ImageView(save);
		Label s=new Label("保存文件");
		HB_L.getChildren().addAll(SAVE,s);
		
		TextArea WriteArea=new TextArea(TEXT);
		WriteArea.setEditable(true);
		WriteArea.setWrapText(true);
		
		HBox BIG=new HBox();
		VBox VERYBIG=new VBox();
		BIG.setPadding(new Insets(0,20,20,0));
		BIG.getChildren().addAll(HB_L,HB_R);
		BorderPane pane=new BorderPane();
		VERYBIG.getChildren().addAll(BIG,WriteArea);
		pane.setTop(VERYBIG);
		Scene scene=new Scene(pane);
		MainStage.setScene(scene);
		MainStage.setTitle("");
		MainStage.setWidth(1200);
		MainStage.setMinWidth(1200);
		MainStage.setHeight(900);
		MainStage.setMinHeight(900);
		
		MainStage.heightProperty().addListener
		(
			ov->
			{
				WriteArea.setPrefHeight(MainStage.getHeight()-150);
			}
		);
		WriteArea.setPrefHeight(MainStage.getHeight()-150);
		
		HB_L.setOnMouseClicked
		(
			e->
			{
				FileWriter WriteIn=null;
				//保存文件
				try
				{
					WriteIn=new FileWriter(TargetFile);
					setTEXT( WriteArea.getText() );
					WriteIn.write(TEXT);
					WriteIn.flush();
					WriteIn.close();
					
					Stage TIP=new Stage();
					BorderPane amz=new BorderPane();
					Label SUC=new Label("保存成功");
					amz.setCenter(SUC);
					Scene tip=new Scene(amz);
					TIP.setScene(tip);
					TIP.setWidth(250);
					TIP.setHeight(100);
					TIP.show();
				}
				catch (IOException e1)
				{
					System.out.println("写入发生错误");
				}	
			}
		);
		
		MainStage.show();
	}
	public static String getTEXT()
	{
		return TEXT;
	}
	public static void setTEXT(String text)
	{
		TEXT=text;
	}
	
}