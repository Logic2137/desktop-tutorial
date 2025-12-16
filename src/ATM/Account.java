package Final_Assignment_For_Java.ATM;

public class Account
{
	
	private String ID="";
	private String PassWord="";
	boolean C_Or_S=false;//true代表checking,支票类型
	private double Balance=0;
	private double Quota=0.0;
	private int Date;
	Account()
	{}
	Account(String A_Number,String P_Number,boolean COS,double Balance,double Quota,int Date)
	{
		this.ID=A_Number;
		this.PassWord=P_Number;
		this.C_Or_S=COS;
		this.Balance=Balance;
		this.Quota=Quota;
		this.Date=Date;
	}
	public double getBalance()
	{
		return Balance;
	}
	public void setBalance(double balance)
	{
		Balance = balance;
	}
	public String getID()
	{
		return ID;
	}
	public void setID(String iD)
	{
		ID = iD;
	}
	public String getPassWord()
	{
		return PassWord;
	}
	public void setPassWord(String passWord)
	{
		PassWord = passWord;
	}
	public boolean isC_Or_S()
	{
		return C_Or_S;
	}
	public void setC_Or_S(boolean c_Or_S)
	{
		C_Or_S = c_Or_S;
	}
	public double getQuota()
	{
		return Quota;
	}
	public void setQuota(double quota)
	{
		Quota = quota;
	}
	public int getDate()
	{
		return Date;
	}
}
