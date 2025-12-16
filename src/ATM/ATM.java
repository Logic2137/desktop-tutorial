package Final_Assignment_For_Java.ATM;

import java.io.File;
import java.util.Scanner;
import java.util.Calendar;
import java.io.FileWriter;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.geometry.Pos;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.FileNotFoundException;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;


public class ATM
{
	static ArrayList<Account> UserList=new ArrayList<Account>();
	public static void FunctionPane (String TargetAccount)
	{
		//在一切开始之前，把文件里的数据读出成为ArrayList
		File DataFile=new File("D:\\Java Study(NWU)\\src\\Final_Assignment_For_Java\\ATM\\UserList.txt");
		try
		{
			Scanner First=new Scanner(DataFile);
			while(First.hasNext())
			{
				String user=First.nextLine();
				String[] Parts=user.split("-");
				boolean kind=Parts[2].equals("true")?true:false;
				double balance=Double.parseDouble(Parts[3]);
				double quota=Double.parseDouble(Parts[4]);
				int date=Integer.parseInt(Parts[5]);
				Account NewUser=new Account(Parts[0],Parts[1],kind,balance,quota,date);
				UserList.add(NewUser);
			}
			for(int k=0;k<UserList.size();k++)
			{
				System.out.println(UserList.get(k).getID()+"  "+UserList.get(k).getBalance());
			}
			First.close();
		}
		catch (FileNotFoundException e2)
		{
			System.out.println("源文件受损");
		}
	
		Stage primaryStage=new Stage();
		BorderPane Pane=new BorderPane();
		VBox Left=new VBox();
		Left.setSpacing(40);
		Left.setAlignment(Pos.CENTER_LEFT);
		Left.setPadding(new Insets(10,10,10,10));
		VBox Right=new VBox();
		Right.setSpacing(40);
		Right.setAlignment(Pos.CENTER_RIGHT);
		Right.setPadding(new Insets(10,10,10,10));
		HBox Title=new HBox();
		Title.setPadding(new Insets(10,10,10,10));
		Title.setAlignment(Pos.CENTER);
		Label sign=new Label("请选择服务");
		sign.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
		Title.getChildren().add(sign);
		
		Button Query=new Button("查询");
		Query.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
		Button Giro=new Button("转账");
		Giro.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
		Button ChangeCode=new Button("修改密码");
		ChangeCode.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
		Button Withdraw=new Button("取款");
		Withdraw.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
		Button Deposit=new Button("存款");
		Deposit.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
		Button Exit=new Button("退出系统");
		Exit.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
		
		//下面是给按钮设置事件部分
		//查询按钮
		Query.setOnAction
		(
			e->
			{
				primaryStage.close();
				Stage Q_Stage=new Stage();
				Q_Stage.setWidth(1200);
				Q_Stage.setHeight(900);
				BorderPane Q_Pane=new BorderPane();
				
				VBox L=new VBox();
				L.setPadding(new Insets(10,10,10,10));
				L.setAlignment(Pos.CENTER);
				VBox R=new VBox();
				R.setPadding(new Insets(10,10,10,10));
				R.setAlignment(Pos.CENTER);
				Button exit=new Button("返回");
				exit.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
				L.getChildren().add(exit);
				Button Q_Balance=new Button("查询余额");
				Q_Balance.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
				R.getChildren().add(Q_Balance);
				exit.setOnAction
				(
					ee->
					{
						Q_Stage.close();
						ATM.FunctionPane(TargetAccount);
					}
				);
				Q_Balance.setOnAction
				(
					eq->
					{
						Stage ShowBalance=new Stage();
						ShowBalance.setWidth(400);
						ShowBalance.setHeight(200);
						Label balance=new Label();
						
						File file=new File("D:\\Java Study(NWU)\\src\\Final_Assignment_For_Java\\ATM\\UserList.txt");
						try
						{
							Scanner read=new Scanner(file);
							while(read.hasNext())
							{
								String stack=read.nextLine();
								String []parts=stack.split("-");
								if(parts[0].equals(TargetAccount))
									balance.setText("您的余额为: "+parts[3]);
								else
									continue;
							}
							read.close();
						}
						catch (FileNotFoundException e1)
						{
							balance.setText("源文件受损");
						}
						HBox B=new HBox();
						B.setPadding(new Insets(10,10,10,10));
						B.setAlignment(Pos.CENTER);
						balance.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
						B.getChildren().add(balance);
						Scene s=new Scene(B);
						ShowBalance.setScene(s);
						ShowBalance.setTitle("余额");
						
						ShowBalance.show();
					}
				);
				
				Q_Pane.setLeft(L);
				Q_Pane.setRight(R);
				Scene s=new Scene(Q_Pane);
				Q_Stage.setScene(s);
				Q_Stage.setTitle("查询");
				Q_Stage.show();
			}
		);
		//转账按钮
		Giro.setOnAction
		(
			e->
			{
				primaryStage.close();
				Stage Q_Stage=new Stage();
				Q_Stage.setWidth(1200);
				Q_Stage.setHeight(900);
				BorderPane Q_Pane=new BorderPane();
				
				VBox L=new VBox();
				L.setPadding(new Insets(10,10,10,10));
				L.setAlignment(Pos.CENTER);
				VBox R=new VBox();
				R.setPadding(new Insets(10,10,10,10));
				R.setAlignment(Pos.CENTER);
				Button exit=new Button("返回");
				exit.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
				L.getChildren().add(exit);
				Button QiroBalance=new Button("转账");
				QiroBalance.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 40;");
				R.getChildren().add(QiroBalance);
				exit.setOnAction
				(
					ee->
					{
						Q_Stage.close();
						ATM.FunctionPane(TargetAccount);
					}
				);
				QiroBalance.setOnAction
				(
					eq->
					{
						Stage Q_B_Stage=new Stage();
						VBox V=new VBox();
						
						//第一行是一个HB，用于得到目标账户
						HBox GetTarget=new HBox();
						GetTarget.setPadding(new Insets(10,10,10,10));
						GetTarget.setAlignment(Pos.CENTER);
						Label Tip_T=new Label("请输入目标账户:   ");
						TextField TA_Number=new TextField();
						GetTarget.getChildren().addAll(Tip_T,TA_Number);
						//第二行是一个HB，用于得到转账数额
						HBox GetNumber=new HBox();
						GetNumber.setPadding(new Insets(10,10,10,10));
						GetNumber.setAlignment(Pos.CENTER);
						Label Tip_N=new Label("请输入转账数额:   ");
						TextField G_Number=new TextField();
						GetNumber.getChildren().addAll(Tip_N,G_Number);
						//第三行是什么都可以，确认按钮
						HBox Action=new HBox();
						Action.setPadding(new Insets(10,10,10,10));
						Action.setAlignment(Pos.CENTER);
						Button Yes=new Button("确认转账");
						Action.getChildren().addAll(Yes);
						//第四行是label，用于提示转账成功或者失败
						HBox TIP=new HBox();
						Label tip=new Label();
						TIP.setPadding(new Insets(10,10,10,10));
						TIP.setAlignment(Pos.CENTER);
						
						V.getChildren().addAll(GetTarget,GetNumber,TIP,Action);
						Q_B_Stage.setWidth(400);
						Q_B_Stage.setHeight(220);
						Scene s=new Scene(V);
						Q_B_Stage.setScene(s);
						Q_B_Stage.setTitle("转账");
						Q_B_Stage.show();
						
						Yes.setOnAction//确认转账
						(
							ey->
							{
								for(int i=0;i<UserList.size();i++)
								{
									if( UserList.get(i).getID().equals(TargetAccount) )//找到出账账户
									{
										double T=Double.parseDouble( G_Number.getText() );
										double B=UserList.get(i).getBalance();
										boolean CS=UserList.get(i).isC_Or_S();
										double Q=UserList.get(i).getQuota();
										if( B>T )
										{
											UserList.get(i).setBalance(B-T);
											tip.setText("转账成功");
											for(int j=0;j<UserList.size();j++)
											{
												if( UserList.get(j).getID().equals( TA_Number.getText() ) )
												//找到入账账户
												{
													double TB=UserList.get(j).getBalance();
													boolean TCS=UserList.get(j).isC_Or_S();
													double TQ=UserList.get(j).getQuota();
													if(TCS)
													{
														if(TQ==100)
														{
															UserList.get(j).setBalance(TB+T);
														}
														else if(TQ+T>=100)
														{
															UserList.get(j).setQuota(100);
															UserList.get(j).setBalance( Math.max(0,T+TQ-100)+TB );
														}
														else
														{
															UserList.get(j).setQuota(TQ+T);
														}
													}
													else
													{
														UserList.get(j).setBalance(TB+T);
													}
												}
											}
										}
										else if(CS)
										{
											if(B+Q>T+10)
											{
												UserList.get(i).setBalance(B-T-10);
												UserList.get(i).setQuota(Q-T+B);
												tip.setText("转账成功");
												for(int j=0;j<UserList.size();j++)
												{
													if( UserList.get(j).getID().equals( TA_Number.getText() ) )
													//找到入账账户
													{
														double TB=UserList.get(j).getBalance();
														boolean TCS=UserList.get(j).isC_Or_S();
														double TQ=UserList.get(j).getQuota();
														if(TCS)
														{
															if(TQ==100)
															{
																UserList.get(j).setBalance(TB+T);
															}
															else if(TQ+T>=100)
															{
																UserList.get(j).setQuota(100);
																UserList.get(j).setBalance(T+TQ-100+TB);
															}
															else
															{
																UserList.get(j).setQuota(TQ+T);
															}
														}
														else
														{
															UserList.get(j).setBalance(TB+T);
														}
													}
												}
											}
										}
										else
										{
											tip.setText("转账失败，余额不足");
										}
									}
								}
								//重写
								String data="";
								for(int i=0;i<UserList.size();i++)
								{
									String line=UserList.get(i).getID()+"-"+
											    UserList.get(i).getPassWord()+"-"+
											    UserList.get(i).isC_Or_S()+"-"+
											    UserList.get(i).getBalance()+"-"+
											    UserList.get(i).getQuota()+"\n";
									data+=line;
								}
								try
								{
									FileWriter ReWriter=new FileWriter(DataFile);
									ReWriter.write(data);
									System.out.println("正在重写入数据");
									ReWriter.flush();
									ReWriter.close();
								}
								catch (IOException e1)
								{
									System.out.println("源文件受损");
								}
							}
						);
					}
				);
				
				Q_Pane.setLeft(L);
				Q_Pane.setRight(R);
				Scene s=new Scene(Q_Pane);
				Q_Stage.setScene(s);
				Q_Stage.setTitle("查询");
				Q_Stage.show();
			}
		);
		//改密按钮
		ChangeCode.setOnAction
		(
			e->
			{
				Stage change=new Stage();
				VBox body=new VBox();
				
				//第一行得到原密码
				HBox one=new HBox();
				one.setPadding(new Insets(10,10,10,10));
				one.setAlignment(Pos.CENTER);
				one.setSpacing(20);
				Label tip1=new Label("请输入原密码");
				TextField get1=new TextField();
				one.getChildren().addAll(tip1,get1);
				//第二行得到新密码
				HBox two=new HBox();
				two.setPadding(new Insets(10,10,10,10));
				two.setAlignment(Pos.CENTER);
				two.setSpacing(20);
				Label tip2=new Label("请输入新密码");
				TextField get2=new TextField();
				two.getChildren().addAll(tip2,get2);
				//第三行提示修改成功或失败
				HBox three=new HBox();
				three.setPadding(new Insets(10,10,10,10));
				three.setAlignment(Pos.CENTER);
				Label tip3=new Label("");
				three.getChildren().addAll(tip3);
				//第四行按钮
				HBox four=new HBox();
				four.setPadding(new Insets(10,10,10,10));
				four.setAlignment(Pos.CENTER);
				Button yes=new Button("确认修改");
				four.getChildren().addAll(yes);
				
				yes.setOnAction
				(
					ey->
					{
						for(int i=0;i<UserList.size();i++)
						{
							if(UserList.get(i).getID().equals(TargetAccount))
							{
								if(UserList.get(i).getPassWord().equals(get1.getText()))
								{
									UserList.get(i).setPassWord(get2.getText());
									tip3.setText("修改成功");
								}
								else
								{
									tip3.setText("修改失败");
								}
							}
						}
						String data="";
						for(int i=0;i<UserList.size();i++)
						{
							String line=UserList.get(i).getID()+"-"+
									    UserList.get(i).getPassWord()+"-"+
									    UserList.get(i).isC_Or_S()+"-"+
									    UserList.get(i).getBalance()+"-"+
									    UserList.get(i).getQuota()+"\n";
							data+=line;
						}
						try
						{
							FileWriter ReWriter=new FileWriter(DataFile);
							ReWriter.write(data);
							System.out.println("正在重写入数据");
							ReWriter.flush();
							ReWriter.close();
						}
						catch (IOException e1)
						{
							System.out.println("源文件受损");
						}
					}
				);
				
				body.getChildren().addAll(one,two,three,four);
				change.setWidth(400);
				change.setHeight(240);
				Scene s=new Scene(body);
				change.setScene(s);
				change.setTitle("修改密码");
				change.show();
			}
		);
		//取款按钮
		Withdraw.setOnAction
		(
			e->
			{
				Stage draw=new Stage();
				VBox body=new VBox();
				body.setPadding(new Insets(10,10,10,10));
				body.setAlignment(Pos.CENTER);
				//第一行取款数额
				HBox one=new HBox();
				one.setPadding(new Insets(10,10,10,10));
				one.setSpacing(20);
				one.setAlignment(Pos.CENTER);
				Label tip1=new Label("请输入存款数额:");
				TextField get1=new TextField();
				one.getChildren().addAll(tip1,get1);
				//第二行提示成功或失败
				HBox two=new HBox();
				two.setAlignment(Pos.CENTER);
				two.setPadding(new Insets(10,10,10,10));
				Label tip2=new Label();
				two.getChildren().add(tip2);
				//第三行按钮
				HBox three=new HBox();
				three.setAlignment(Pos.CENTER);
				three.setPadding(new Insets(10,10,10,10));
				Button yes=new Button("确认存款");
				three.getChildren().add(yes);
				
				body.getChildren().addAll(one,two,three);
				Scene s=new Scene(body);
				draw.setWidth(400);
				draw.setHeight(300);
				draw.setScene(s);
				draw.setTitle("存款");
				draw.show();
				
				yes.setOnAction
				(
					ey->
					{
						for(int i=0;i<UserList.size();i++)
						{
							if(UserList.get(i).getID().equals(TargetAccount))//找到客户
							{
								if(UserList.get(i).getBalance()>=10000)//重要客户
								{
									tip2.setText("您存款超过10000$,取款服务请联系经理\n联系电话:17394113071");
								}
								else
								{
									double T=Double.parseDouble(get1.getText());
									double B=UserList.get(i).getBalance();
									boolean CS=UserList.get(i).isC_Or_S();
									double Q=UserList.get(i).getQuota();
									if(B>=T)
									{
										UserList.get(i).setBalance(B-T);
										tip2.setText("取款成功");
									}
									else if(CS)
									{
										if(B+Q-10>=T)
										{
											UserList.get(i).setBalance(B-10-T);
											UserList.get(i).setQuota(Q+B-T);
											tip2.setText("取款成功");
										}
										else
										{
											tip2.setText("余额不足,取款失败");
										}
									}
									else
									{
										tip2.setText("余额不足,取款失败");
									}
								}
							}
						}
						//重写
						String data="";
						for(int i=0;i<UserList.size();i++)
						{
							String line=UserList.get(i).getID()+"-"+
									    UserList.get(i).getPassWord()+"-"+
									    UserList.get(i).isC_Or_S()+"-"+
									    UserList.get(i).getBalance()+"-"+
									    UserList.get(i).getQuota()+"\n";
							data+=line;
						}
						try
						{
							FileWriter ReWriter=new FileWriter(DataFile);
							ReWriter.write(data);
							System.out.println("正在重写入数据");
							ReWriter.flush();
							ReWriter.close();
						}
						catch (IOException e1)
						{
							System.out.println("源文件受损");
						}
					}
				);
			}
		);
		//存款按钮
		Deposit.setOnAction
		(
			e->
			{

				Stage draw=new Stage();
				VBox body=new VBox();
				body.setPadding(new Insets(10,10,10,10));
				body.setAlignment(Pos.CENTER);
				//第一行取款数额
				HBox one=new HBox();
				one.setPadding(new Insets(10,10,10,10));
				one.setSpacing(20);
				one.setAlignment(Pos.CENTER);
				Label tip1=new Label("请输入取款数额:");
				TextField get1=new TextField();
				one.getChildren().addAll(tip1,get1);
				//第二行提示成功或失败
				HBox two=new HBox();
				two.setAlignment(Pos.CENTER);
				two.setPadding(new Insets(10,10,10,10));
				Label tip2=new Label();
				two.getChildren().add(tip2);
				//第三行按钮
				HBox three=new HBox();
				three.setAlignment(Pos.CENTER);
				three.setPadding(new Insets(10,10,10,10));
				Button yes=new Button("确认取款");
				three.getChildren().add(yes);
				
				body.getChildren().addAll(one,two,three);
				Scene s=new Scene(body);
				draw.setWidth(400);
				draw.setHeight(300);
				draw.setScene(s);
				draw.setTitle("取款");
				draw.show();
				
				yes.setOnAction
				(
					ey->
					{
						for(int i=0;i<UserList.size();i++)
						{
							if(UserList.get(i).getID().equals(TargetAccount))//找到客户
							{
								double T=Double.parseDouble(get1.getText());
								double B=UserList.get(i).getBalance();
								boolean CS=UserList.get(i).isC_Or_S();
								double Q=UserList.get(i).getQuota();
								if(CS)
								{
									if(Q<100)
									{
										if(Q+T<=100)
										{
											UserList.get(i).setQuota(Q+T);
											tip2.setText("存款成功");
										}
										else
										{
											UserList.get(i).setQuota(100);
											UserList.get(i).setBalance(B+Q+T-100);
											tip2.setText("存款成功");
										}
									}
									else
									{
										UserList.get(i).setBalance(B+T);
										tip2.setText("存款成功");
									}
								}
								else
								{
									UserList.get(i).setBalance(B+T);
									tip2.setText("存款成功");
								}
							}
						}
						//重写
						String data="";
						for(int i=0;i<UserList.size();i++)
						{
							String line=UserList.get(i).getID()+"-"+
									    UserList.get(i).getPassWord()+"-"+
									    UserList.get(i).isC_Or_S()+"-"+
									    UserList.get(i).getBalance()+"-"+
									    UserList.get(i).getQuota()+"\n";
							data+=line;
						}
						try
						{
							FileWriter ReWriter=new FileWriter(DataFile);
							ReWriter.write(data);
							System.out.println("正在重写入数据");
							ReWriter.flush();
							ReWriter.close();
						}
						catch (IOException e1)
						{
							System.out.println("源文件受损");
						}
					}
				);
			
			}
		);
		//退出系统
		Exit.setOnAction
		(
			e->
			{
				String data="";
				for(int i=0;i<UserList.size();i++)
				{
					String line=UserList.get(i).getID()+"-"+
							    UserList.get(i).getPassWord()+"-"+
							    UserList.get(i).isC_Or_S()+"-"+
							    UserList.get(i).getBalance()+"-"+
							    UserList.get(i).getQuota()+"\n";
					data+=line;
				}
				try
				{
					FileWriter ReWriter=new FileWriter(DataFile);
					ReWriter.write(data);
					System.out.println("正在保存数据");
					ReWriter.flush();
					ReWriter.close();
				}
				catch (IOException e1)
				{
					System.out.println("源文件受损");
				}
				primaryStage.close();
			}
		);
		//检查利息
		for(int i=0;i<UserList.size();i++)
		{
			int n=Calendar.getInstance().get(Calendar.YEAR)-UserList.get(i).getDate();
			if(n>0)
			{
				UserList.get(i).setBalance(UserList.get(i).getBalance()*Math.pow(n,1.05));
			}
		}
		String data="";
		for(int i=0;i<UserList.size();i++)
		{
			String line=UserList.get(i).getID()+"-"+
					    UserList.get(i).getPassWord()+"-"+
					    UserList.get(i).isC_Or_S()+"-"+
					    UserList.get(i).getBalance()+"-"+
					    UserList.get(i).getQuota()+"\n";
			data+=line;
		}
		try
		{
			FileWriter ReWriter=new FileWriter(DataFile);
			ReWriter.write(data);
			System.out.println("正在保存数据");
			ReWriter.flush();
			ReWriter.close();
		}
		catch (IOException e1)
		{
			System.out.println("源文件受损");
		}
		primaryStage.close();
		
		Left.getChildren().addAll(Query,Giro,ChangeCode);
		Right.getChildren().addAll(Withdraw,Deposit,Exit);
		Pane.setTop(Title);
		Pane.setLeft(Left);
		Pane.setRight(Right);
		Scene scene=new Scene(Pane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Automated Teller Machine");
		primaryStage.setWidth(1200);
		primaryStage.setHeight(900);
		primaryStage.show();
	}
}
