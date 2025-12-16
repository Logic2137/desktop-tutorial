package Final_Assignment_For_Java.Clock;

import java.util.Calendar;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.applet.Applet;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.SelectionMode;
import javafx.beans.property.SimpleIntegerProperty;

public class Clock extends Application
{
	static ArrayList<BorderPane> AlarmingClock=new ArrayList<BorderPane>();
	static SimpleIntegerProperty on=new SimpleIntegerProperty(0);
	//on的含义是有多少闹钟是开启状态，它会被检查运行-响铃的方法监听
	static SimpleIntegerProperty member=new SimpleIntegerProperty(0);
	//member的含义是有多少闹钟被设定，它会被ShowAlarmTimeList方法监听
	
	static ArrayList<String> ClockList=new ArrayList<String>();
	
	static Room happiness=new Room();
	static ArrayList<Room> Hospital=new ArrayList<Room>();
	
	@Override
	public void start(Stage primaryStage)
	{	
		BorderPane pane=new BorderPane();
		pane.setPadding(new Insets(5,5,5,5));
		
		CreateClockPane(pane);
		AddPointer(pane);
		
		URL url=null;
		File file=new File("C:\\Users\\Logic.G\\Desktop\\Kevin.wav");
		try
		{
			url=file.toURL();
		}
		catch(MalformedURLException e1)
		{}
		java.applet.AudioClip dida;
		dida=Applet.newAudioClip(url);//加载音频
		
		EventHandler<ActionEvent> TR=e->
		{
			pane.getChildren().clear();
			CreateClockPane(pane);
			AddPointer(pane);
			Alarming();
			dida.play(); //播放音频
			
		};
		Timeline ANM=new Timeline( new KeyFrame(Duration.millis(1000),TR) );
		ANM.setCycleCount(Timeline.INDEFINITE);
		ANM.play();
		
		Scene scene=new Scene(pane,1500,900);
		primaryStage.setScene(scene);
		primaryStage.setTitle("时钟");
		primaryStage.show();
		if(!primaryStage.isShowing())
			dida.stop();
	}


	public static void CreateClockPane(BorderPane pane)
	{
		CreateShape(pane);                                                      //钟的表盘&钟点数值
		CreateButton(pane);                                                     //相关按钮
		ShowAlarmTimeList(pane);
	}


	public static void AddPointer(BorderPane pane)                              //钟的表针&文本显示
	{
		HBox HB=new HBox();
		HB.setAlignment(Pos.CENTER);
		HB.setSpacing(10);
		
		Calendar time=Calendar.getInstance();
		int second=time.get(Calendar.SECOND);
		int minute=time.get(Calendar.MINUTE);
		int hour=time.get(Calendar.HOUR);

		Line HOUR=new Line();
		HOUR.setStroke(Color.BLACK);
		Line MINUTE=new Line();
		MINUTE.setStroke(Color.GREY);
		Line SECOND=new Line();
		SECOND.setStroke(Color.RED);
		HOUR.startXProperty().bind(pane.widthProperty().divide(3));
		MINUTE.startXProperty().bind(pane.widthProperty().divide(3));
		SECOND.startXProperty().bind(pane.widthProperty().divide(3));
		HOUR.startYProperty().bind(pane.heightProperty().divide(2));
		MINUTE.startYProperty().bind(pane.heightProperty().divide(2));
		SECOND.startYProperty().bind(pane.heightProperty().divide(2));
		HOUR.setStrokeWidth(4);
		MINUTE.setStrokeWidth(2.5);
		SECOND.setStrokeWidth(1);
		
		HOUR.endXProperty().bind( pane.widthProperty().divide(3).add(180*Math.sin(2*Math.PI*hour/12.0) )  );
		HOUR.endYProperty().bind( pane.heightProperty().divide(2).subtract(180*Math.cos(2*Math.PI*hour/12.0) ) );
		MINUTE.endXProperty().bind( pane.widthProperty().divide(3).add(210*Math.sin(2*Math.PI*minute/60.0) )  );
		MINUTE.endYProperty().bind( pane.heightProperty().divide(2).subtract(210*Math.cos(2*Math.PI*minute/60.0) ) );
		SECOND.endXProperty().bind( pane.widthProperty().divide(3).add(230*Math.sin(2*Math.PI*second/60.0) )  );
		SECOND.endYProperty().bind( pane.heightProperty().divide(2).subtract(230*Math.cos(2*Math.PI*second/60.0) ) );
		
		pane.getChildren().add(HOUR);
		pane.getChildren().add(MINUTE);
		pane.getChildren().add(SECOND);
		
		String S1=hour<10?"0":"";
		String S2=minute<10?"0":"";
		String S3=second<10?"0":"";
		if(time.get(Calendar.AM_PM)==1)
		{
			hour+=12;
			S1="";
		}
		Text NT=new Text("北京时间:  "+S1+hour+":"+S2+minute+":"+S3+second);
		NT.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 30;");
		HB.getChildren().add(NT);
		pane.setBottom(HB);
	}
	
	
	public static void CreateShape(BorderPane pane)                                 //钟的表盘
	{
		Circle clock=new Circle();
		clock.centerXProperty().bind(pane.widthProperty().divide(3));
		clock.centerYProperty().bind(pane.heightProperty().divide(2));
		clock.setRadius(300);
		clock.setFill(Color.WHITE);
		clock.setStroke(Color.BLACK);
		clock.setStrokeWidth(3);
		pane.getChildren().add(clock);
		int L=0;
		double D=0;
		double R=clock.getRadius();
		int N=12;
		for(int i=0;i<60;i++)
		{
			
			if(i%5==0)
			{
				if(i%15==0)
				{
					L=270;
					D=4;
				}	
				else
				{
					L=280;
					D=2.5;
				}
				double X=pane.widthProperty().divide(3).add( (L-10)*Math.sin(2*Math.PI*i/60.0) ).doubleValue()-5;
				double Y=pane.heightProperty().divide(2).subtract( (L-10)*Math.cos(2*Math.PI*i/60.0) ).doubleValue()+5;
				Text number=new Text(X,Y,""+N);
				pane.getChildren().add(number);
				if(N==12)
					N=0;
				N++;
			}
			else
			{
				L=290;
				D=1;
			}	
			Line sign=new Line();
			sign.startXProperty().bind(pane.widthProperty().divide(3).add( L*Math.sin(2*Math.PI*i/60.0) ) );
			sign.startYProperty().bind(pane.heightProperty().divide(2).subtract( L*Math.cos(2*Math.PI*i/60.0) ) );
			sign.endXProperty().bind(pane.widthProperty().divide(3).add( R*Math.sin(2*Math.PI*i/60.0) ) );
			sign.endYProperty().bind(pane.heightProperty().divide(2).subtract( R*Math.cos(2*Math.PI*i/60.0) ) );
			sign.setStrokeWidth(D);
			
			pane.getChildren().add(sign);
		}
	}
	
	static SimpleIntegerProperty play=new SimpleIntegerProperty(0);
	
	public static void CreateButton(BorderPane pane)                               //相关按钮
	{
		HBox HB=new HBox();
		HB.setAlignment(Pos.CENTER);
		
		Button SetNewAlarmTime=new Button("添加闹钟");
		
		SetNewAlarmTime.setOnMouseClicked
		(
			e1->
			{
				Hospital.add(happiness);
				happiness=new Room();
					
				Stage NewStage=new Stage();
				BorderPane NewPane=new BorderPane();
				HBox T_SetTime=new HBox();
				VBox B_SetText=new VBox();
				VBox FINALLY=new VBox();
				FINALLY.setAlignment(Pos.CENTER);
				
				ChoiceA_P(T_SetTime);
				ChoiceHour(T_SetTime);
				ChoiceMinute(T_SetTime);
				SetNote(B_SetText);
				SetLastTime(B_SetText);
				SetSpace(B_SetText);
				
				int []TimeList= {happiness.getAP(),happiness.getHour(),happiness.getMinute()};  	        //AM&PM，小时数，分钟数
				int Space[]= {happiness.getSpaceTime(),happiness.getLoopTimes()};
				Button finished=new Button("确认创建");
				FINALLY.getChildren().addAll(T_SetTime,B_SetText,finished);
				finished.setOnMouseClicked
				(
					e2->
					{
						Create(TimeList,happiness.getNoteText(),happiness.getLastLength(),Space);           //真正的创建闹钟对象
						NewStage.close();
					}
				);
				
				NewPane.setCenter(FINALLY);
				Scene NewScene=new Scene(NewPane,1000,800);
				NewStage.setScene(NewScene);
				NewStage.setTitle("新建闹钟");
				NewStage.show();
				
				member.addListener
				(
					ov->
					{
						ShowAlarmTimeList(pane);
					}
				);
				
			}
		);
		
		HB.getChildren().add(SetNewAlarmTime);
		pane.setTop(HB);
	}


	public static void ChoiceA_P(HBox HB)
	{
		VBox VB=new VBox();
		VB.setAlignment(Pos.TOP_CENTER);
		VB.setPadding(new Insets(20,20,20,20));
		VB.setSpacing(20);
		RadioButton AM=new RadioButton("AM");
		AM.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 60;");
		RadioButton PM=new RadioButton("PM");
		PM.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 60;");
		VB.getChildren().addAll(AM,PM);
		ToggleGroup CAP=new ToggleGroup();
		AM.setToggleGroup(CAP);
		PM.setToggleGroup(CAP);
		HB.getChildren().add(VB);
		AM.setOnAction
		(
			e->
			{
				if(AM.isSelected())
				{
					happiness.setAP(0);
				}
					
			}
		);
		PM.setOnAction
		(
			e->
			{
				if(PM.isSelected())
				{
					happiness.setAP(1);
				}	
			}
		);
	}
	

	public static void ChoiceHour(HBox HB)
	{
		VBox VB=new VBox();
		VB.setSpacing(20);
		VB.setPadding(new Insets(20,20,20,20));
		VB.setAlignment(Pos.TOP_CENTER);
		VB.setMaxHeight(200);
		String[] number=new String[12];
		for(int i=0;i<12;i++)
			number[i]=i+"";
		ListView<String> Hour=new ListView<String>( FXCollections.observableArrayList( number ) );
		Hour.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		Hour.setPrefSize(350,350);
		
		Label sign=new Label("选择小时数");
		
		VB.getChildren().add(sign);
		VB.getChildren().add(Hour);
		HB.getChildren().add(VB);
		
		Hour.getSelectionModel().selectedItemProperty().addListener
		(
			ov->
			{
				for(Integer i:Hour.getSelectionModel().getSelectedIndices())
				{
					happiness.setHour(i);
				}	
			}
		);
	}	
	
	
	public static void ChoiceMinute(HBox HB)
	{
		VBox VB=new VBox();
		VB.setSpacing(20);
		VB.setPadding(new Insets(20,20,20,20));
		VB.setAlignment(Pos.TOP_CENTER);
		VB.setMaxHeight(200);
		String[] number=new String[60];
		for(int i=0;i<60;i++)
			number[i]=i+"";
		ListView<String> Minute=new ListView<String>( FXCollections.observableArrayList( number ) );
		Minute.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		Minute.setPrefSize(350,350);
		
		Label sign=new Label("选择分钟数");
		
		VB.getChildren().add(sign);
		VB.getChildren().add(Minute);
		HB.getChildren().add(VB);
		
		Minute.getSelectionModel().selectedItemProperty().addListener
		(
			ov->
			{
				for(Integer i:Minute.getSelectionModel().getSelectedIndices())
				{
					happiness.setMinute(i);
				}	
			}
		);
	}
	
	
	public static void SetNote(VBox VB)
	{
		HBox HB=new HBox();
		HB.setPadding(new Insets(20,20,20,20));
		HB.setSpacing(20);
		Label sign=new Label("设置备注");
		sign.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 60;");
		HB.getChildren().add(sign);
		
		TextField NOTE=new TextField();
		NOTE.setText("闹钟");
		NOTE.setPrefWidth(500);
		NOTE.setPrefHeight(60);
		
		HB.getChildren().add(NOTE);
		VB.getChildren().add(HB);
		
		happiness.setNoteText( NOTE.getText() );
	}
	
	
	public static void SetLastTime(VBox VB)
	{	
		HBox HB=new HBox();
		HB.setPadding(new Insets(20,20,20,20));
		HB.setSpacing(20);
		Label sign=new Label("设置持续时间");
		sign.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 60;");
		HB.getChildren().add(sign);
		
		String[] last= {"1分钟" , "5分钟" , "10分钟" , " 15分钟 " , "20分钟" , "30分钟"};
		ListView<String> LastTime=new ListView<String>( FXCollections.observableArrayList( last ) );
		LastTime.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		LastTime.setPrefSize(350,60);
		HB.getChildren().add(LastTime);
		VB.getChildren().add(HB);
		
		LastTime.getSelectionModel().selectedItemProperty().addListener
		(
			ov->
			{
				for(int i:LastTime.getSelectionModel().getSelectedIndices())
				{
					switch(i)
					{
					case 0:happiness.setLastLength(1);break;
					case 1:happiness.setLastLength(5);break;
					case 2:happiness.setLastLength(10);break;
					case 3:happiness.setLastLength(15);break;
					case 4:happiness.setLastLength(20);break;
					case 5:happiness.setLastLength(30);break;
					}
				}
			}
		);
	}
	
	
	public static void SetSpace(VBox VB)//Unfinished
	{
		VBox SS=new VBox();
		SS.setSpacing(5);
		SS.setPadding(new Insets(20,20,20,20));
		SS.setAlignment(Pos.CENTER_LEFT);
		
		Label space=new Label("响铃时间间隔（分钟）");
		space.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 30;");
		
		Slider SPACING=new Slider(5,30,5);
		SPACING.setShowTickLabels(true);
		SPACING.setShowTickMarks(true);
		SPACING.setMinorTickCount(4);
		
		Label times=new Label("重复响铃次数");
		times.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 30;");
		
		Slider TIMES=new Slider(1,5,1);
		TIMES.setShowTickLabels(true);
		TIMES.setShowTickMarks(true);
		TIMES.setMinorTickCount(3);
		
		SS.getChildren().addAll(space,SPACING,times,TIMES);
		VB.getChildren().add(SS);
	}
	
	
	//Finally
	public static void Create(int[] TimeList,String NOTE,int LastTime,int[] space)
	{	
		
		happiness.getDelete().setOnAction
		(
			e->
			{
				if(AlarmingClock.size()==1)
					AlarmingClock.clear();
				else	
					AlarmingClock.remove(happiness.getPane());
				ClockList.remove( (3600*(TimeList[1]+12*TimeList[0]*12)+60*TimeList[2])+"" );
				member.subtract(1);
				
			}
		);
		happiness.getIfOn().setOnAction
		(
			e->
			{
				if( happiness.getIfOn().isSelected()||!AlarmingClock.contains( (3600*(TimeList[1]+12*TimeList[0]*12)+60*TimeList[2])+""  ) )
					ClockList.add( (3600*(TimeList[1]+12*TimeList[0]*12)+60*TimeList[2])+"" );
				else
					ClockList.remove( (3600*(TimeList[1]+12*TimeList[0]*12)+60*TimeList[2])+"" );
			}
		);
		
		AlarmingClock.add(happiness.getPane());
		member.add(1);
		ClockList.add( (3600*(TimeList[1]+12*TimeList[0]*12)+60*TimeList[2])+"" );
	}
	
	
	//FinallyShow
	public static void ShowAlarmTimeList(BorderPane pane)
	{
		VBox VB=new VBox();
		for(int i=0;i<AlarmingClock.size();i++)
			VB.getChildren().add(AlarmingClock.get(i));
		pane.setRight(VB);
	}
	
	
	public static void Alarming()
	{
		Calendar time=Calendar.getInstance();
		int minute=time.get(Calendar.MINUTE);
		int hour=time.get(Calendar.HOUR);
		int ap=time.get(Calendar.AM_PM);

		URL url=null;
		File file=new File("C:\\Users\\Logic.G\\Desktop\\Kevin.wav");
		try
		{
			url=file.toURL();
		}
		catch(MalformedURLException e1)
		{}
		java.applet.AudioClip ring;
		ring=Applet.newAudioClip(url);//加载音频
		
		ClockList.sort(null);
		for(int i=0;i<ClockList.size();i++)
		{
			if( (((ap*12+hour)*3600+60*minute)+"").equals( ClockList.get(i) ) )
			{
				System.out.println("闹钟已响铃");
				ring.play();
				/* ... */
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}