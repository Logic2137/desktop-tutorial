package Final_Assignment_For_Java.ATM;

import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.Calendar;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class LogOn extends Application
{
	static int kind=0;
	static String NewAccount="";
//	public static ArrayList<Account> UserList=new ArrayList<Account>();
	//登录界面
	@Override
	public void start(Stage primaryStage)
	{
		Stage Log=new Stage();
		VBox Body=new VBox();
		Body.setSpacing(20);
		Body.setPadding(new Insets(10,10,10,10));
		
		//VB第一行是一个HB,账号
		HBox ID=new HBox();
		ID.setSpacing(20);
		Label id=new Label("请输入账号");
		TextField GetID=new TextField();
		ID.getChildren().addAll(id,GetID);
		//VB第二行是一个HB,密码
		HBox PassWord=new HBox();
		PassWord.setSpacing(20);
		Label code=new Label("请输入密码");
		TextField GetCode=new TextField();
		PassWord.getChildren().addAll(code,GetCode);
		//第三行是一个HB,两个按钮:确认登录和什么呢
		HBox Buttons=new HBox();
		Buttons.setSpacing(20);
		Buttons.setAlignment(Pos.CENTER);
		Button confirm=new Button("确认登录");
		Button register=new Button("注册");
		Buttons.getChildren().addAll(confirm,register);
		Label warming=new Label("");
		
		Body.getChildren().addAll(ID,PassWord,Buttons,warming);
		Scene scene=new Scene(Body);
		Log.setScene(scene);
		Log.setTitle("登录");
		
		confirm.setOnAction
		(
			e->
			{
				String account=GetID.getText();
				String password=GetCode.getText();
				if( CheckID(account,password) )
				{
					System.out.println("Successfully");
					
					ATM.FunctionPane(account);
					Log.close();
				}
				else
					warming.setText("账号或密码有误");
			}
		);
		register.setOnAction
		(
			e->
			{
				Log.close();
				Register();
			}
		);
		Log.show();
	}
	//检查账号密码是否正确
	public static boolean CheckID(String ID,String Code)
	{
		File file=new File("D:\\Java Study(NWU)\\src\\Final_Assignment_For_Java\\ATM\\UserList.txt");
		try
		{
			Scanner read=new Scanner(file);
			while(read.hasNext())
			{
				String target=read.nextLine();
				String []part=target.split("-");
				if(ID.equals(part[0])&&Code.equals(part[1]))
				{
					read.close();
					return true;
				}
					
				else
					continue;
			}
			read.close();
			return false;
		}
		catch(FileNotFoundException e)
		{
			System.out.println("源文件受损");
		}
		return false;
	}
	//注册账号
	public static void Register()
	{
		Stage stage=new Stage();
		VBox VB=new VBox();
		VB.setSpacing(20);
		VB.setAlignment(Pos.CENTER_LEFT);
		VB.setPadding(new Insets(20,20,20,20));
		
		Random random=new Random();
		for(int i=0;i<10;i++)
		{
			NewAccount+=random.nextInt(10);
			if(i==0&&NewAccount.equals("0"))
			{
				i=-1;
				NewAccount="";
			}
		}
		
		Label YourAccount=new Label("您的账号为:  "+NewAccount);
		HBox HB=new HBox();
		HB.setSpacing(20);
		HB.setAlignment(Pos.CENTER_LEFT);
		Label CodeSign=new Label("请设置您的密码:  ");
		TextField GetCode=new TextField();
		HB.getChildren().addAll(CodeSign,GetCode);
		HBox ChoiceKind=new HBox();
		ChoiceKind.setAlignment(Pos.CENTER_LEFT);
		ChoiceKind.setSpacing(15);
		Label tip=new Label("是否创建支票账户:   ");
		RadioButton Checking=new RadioButton("支票账户");
		ChoiceKind.getChildren().addAll(tip,Checking);
		ToggleGroup CCS=new ToggleGroup();
		Checking.setToggleGroup(CCS);
		Checking.setOnAction
		(
			e->
			{
				if( Checking.isSelected() )
					kind=1;
				else
					kind=0;
			}
		);
		HBox BUTTON=new HBox();
		Button Finished=new Button("确认创建");
		BUTTON.setAlignment(Pos.CENTER);
		BUTTON.getChildren().add(Finished);
		
		Finished.setOnAction
		(
			e->
			{
				String Code=GetCode.getText();
				boolean K=(kind==1);
				String AccountNumber=NewAccount;
				
				File NewFileArray=new File("D:\\Java Study(NWU)\\src\\Final_Assignment_For_Java\\ATM\\UserList.txt");
				if( !NewFileArray.exists() )
				{
					try
					{
						NewFileArray.createNewFile();
						System.out.println( NewFileArray.mkdir() );
					}
					catch(IOException er)
					{
						System.out.println("创建文件失败");
					}
				}
				
				NewFileArray=new File("D:\\Java Study(NWU)\\src\\Final_Assignment_For_Java\\ATM\\UserList.txt");
				try
				{
					int Date=Calendar.getInstance().get(Calendar.YEAR);
					FileWriter Record=new FileWriter(NewFileArray,true);
					Record.write(AccountNumber+"-"+Code+"-"+K+"-"+0+"-"+100*(K?1:0)+"-"+Date+"\n");
					Record.flush();
					Record.close();
				}
				catch (IOException er)
				{
					System.out.println("记录数据失败");
				}
				stage.close();
			}
		);
		
		VB.getChildren().addAll(YourAccount,HB,ChoiceKind,BUTTON);
		Scene scene=new Scene(VB);
		stage.setScene(scene);
		stage.setTitle("注册账号");
		stage.show();
	}
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}