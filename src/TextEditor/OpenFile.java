package Final_Assignment_For_Java.TextEditor;

import java.io.File;
import java.net.URL;
import java.util.List;
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

public class OpenFile extends Application
{
	static int i=0;
	@Override
	public void start(Stage primaryStage)
	{}
	
	public static void ShowPane(String FileRoad) throws NullPointerException
	{
		String[] period;
		if(FileRoad==null)
			period=new String[]{};
		else 
			period=FileRoad.split("/");
		if(FileRoad.contains(".txt"))
		{
			System.out.println("Find之前");
			OpenTXT.FindFile(FileRoad);
			return ;
		}
		
		Stage Open=new Stage();
		BorderPane pane=new BorderPane();
		VBox Body=new VBox();
		Body.setPadding(new Insets(20,20,20,20));
		
		HBox Location=new HBox();
		Location.setPadding(new Insets(20,20,20,20));
		Location.setAlignment(Pos.CENTER_LEFT);
		
		ArrayList<Label> ShowLocation=new ArrayList<Label>();
		Label ThisComputer=new Label("此电脑  > ");
		ThisComputer.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 20;");
		ShowLocation.add(ThisComputer);
		Location.getChildren().add(ThisComputer);
		for(i=0;i<period.length;i++)
		{
			Label target=new Label(period[i]+"  >  ");
			target.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 20;");
			ShowLocation.add(target);
			Location.getChildren().add(target);
			target.setOnMouseClicked
			(
				e->
				{
					String NL="";
					for(int j=0;j<period.length;j++)
					{
						NL+=(period[j]+"/");
						if(period[j].equals( target.getText().replaceAll("  >  ", "") ) )
							break;
					}
					System.out.println(NL);
					Open.close();
					ShowPane(NL);
				}
			);
		}
		
		HBox Stack=new HBox();//文件预览区
		Stack.setPadding(new Insets(20,20,20,20));
		Stack.setAlignment(Pos.CENTER_LEFT);
		
		VBox listfile=new VBox();
		ScrollPane FileView=new ScrollPane(listfile);
		Stack.widthProperty().addListener
		(
			ov->
			{
				FileView.setPrefWidth(Stack.widthProperty().doubleValue()-100);
			}
		);
		
		FileView.setMinViewportHeight(520);
		FileView.setMinViewportWidth(886);
		
		File file=new File(FileRoad);
		listfile.setSpacing(10);
		listfile.setPadding(new Insets(10,10,10,10));
		listfile.setAlignment(Pos.CENTER_LEFT);
		File[] filelist=file.listFiles();
		for(int j=0;j<filelist.length;j++)
		{
			Label filename=new Label(filelist[j].getName());
			filename.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 20;");
			listfile.getChildren().add(filename);
			filename.setOnMouseClicked
			(
				e->
				{
					if(e.getClickCount() == 2)
					{
						if(filename.getText().contains(".txt")) {
							System.out.println("Find2之前");
							OpenTXT.FindFile(FileRoad + filename.getText());
						}
						else
						{
							try
							{
								ShowPane( FileRoad+"/"+filename.getText() );
								Open.close();
							}
							catch(NullPointerException ex)
							{
								Stage warming=new Stage();
								warming.setHeight(120);
								warming.setWidth(400);
								BorderPane W=new BorderPane();
								Label WARMING=new Label("无法打开这种文件");
								WARMING.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 20;");
								W.setCenter(WARMING);
								Scene JustWarming=new Scene(W);
								warming.setScene(JustWarming);
								warming.setTitle("发生错误");
								warming.show();
							}
						}	
					}
				}
			);
		}
		
		
		VBox BUTTON=new VBox();
		BUTTON.setPadding(new Insets(20,20,20,20));
		BUTTON.setAlignment(Pos.CENTER);
		BUTTON.setSpacing(40);
		BUTTON.setPrefWidth(200);
		BUTTON.setMinWidth(200);
		Button C=new Button(" C盘 ");
		C.setOnAction
		(
			e->
			{
				Open.close();
				ShowPane("C:/");
			}
		);
		Button D=new Button(" D盘 ");
		D.setOnAction
		(
			e->
			{
				Open.close();
				ShowPane("D:/");
			}
		);
		Button E=new Button(" E盘 ");
		E.setOnAction
		(
			e->
			{
				Open.close();
				ShowPane("E:/");
			}
		);
		Button[] Control= {C,D,E};
		for(int i=0;i<Control.length;i++)
		{
			Control[i].setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
			BUTTON.getChildren().add(Control[i]);
			Control[i].setPrefWidth(200);
		}
		
		Stack.getChildren().add(BUTTON);
		Stack.getChildren().add(FileView);
		Body.getChildren().add(Location);
		Body.getChildren().add(Stack);
		pane.setCenter(Body);
		Scene scene=new Scene(pane);
		Open.setWidth(1300);
		Open.setHeight(750);
		Open.setScene(scene);
		Open.setTitle("打开文件");
		Open.show();
	}
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}