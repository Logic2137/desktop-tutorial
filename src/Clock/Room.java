package Final_Assignment_For_Java.Clock;

import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.RadioButton;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.geom.transform.BaseTransform;

public class Room extends Node
{
	private int AP=0;
	private int Hour=0;
	private int Minute=0;
	private String NoteText="ƒ÷÷”";
	private int LastLength=0;
	private int LoopTimes=0;
	private int SpaceTime=0;
	private BorderPane pane=new BorderPane();
	private Label HeadSign=new Label();
	private Label Note=new Label("ƒ÷÷”");
	private RadioButton IfOn=new RadioButton("ø™∆Ùƒ÷÷”");
	private Button Delete=new Button("…æ≥˝ƒ÷÷”");
	
	public Room()
	{
		pane.setPadding(new Insets(20,20,20,20));
		pane.setMaxSize(600,200);
		
		VBox VB1=new VBox();
		VB1.setSpacing(20);
		VB1.setAlignment(Pos.CENTER_LEFT);
		VB1.setPadding(new Insets(20,20,20,20));
		
		String TimeStage="";
		if(AP==0)
		{
			if(Hour<4)
				TimeStage="¡Ë≥ø";
			else if(Hour<7)
				TimeStage="«Â≥ø";
			else if(Hour<10)
				TimeStage="‘Á≥ø";
			else
				TimeStage="÷–ŒÁ";
		}
		else
		{
			if(Hour<4)
				TimeStage="÷–ŒÁ";
			else if(Hour<7)
				TimeStage="œ¬ŒÁ";
			else if(Hour<10)
				TimeStage="ª∆ªË";
			else
				TimeStage="∞Î“π";
		}
		HeadSign.setText(TimeStage+"  "+Hour+" : "+Minute);
		HeadSign.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 30;");
		Note.setText(NoteText);
		Note.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 30;");
		VB1.getChildren().addAll(HeadSign,Note);
		
		VBox VB2=new VBox();
		VB2.setPadding(new Insets(20,20,20,20));
		VB2.setAlignment(Pos.CENTER);

		IfOn.setPadding(new Insets(20,20,20,20));
		IfOn.setSelected(true);
		IfOn.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 20;");
		VB2.getChildren().add(IfOn);
		
		Delete.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 15;");
		VB2.getChildren().add(Delete);
		
		pane.setLeft(VB1);
		pane.setRight(VB2);
	}
	
	
	public int getAP()
	{
		return AP;
	}
	public void setAP(int aP)
	{
		AP = aP;
		String TimeStage="";
		if(AP==0)
		{
			if(Hour<4)
				TimeStage="¡Ë≥ø";
			else if(Hour<7)
				TimeStage="«Â≥ø";
			else if(Hour<10)
				TimeStage="‘Á≥ø";
			else
				TimeStage="÷–ŒÁ";
		}
		else
		{
			if(Hour<4)
				TimeStage="÷–ŒÁ";
			else if(Hour<7)
				TimeStage="œ¬ŒÁ";
			else if(Hour<10)
				TimeStage="ª∆ªË";
			else
				TimeStage="∞Î“π";
		}
		HeadSign.setText(TimeStage+"  "+Hour+" : "+Minute);
	}


	public int getHour()
	{
		return Hour;
	}
	public void setHour(int hour)
	{
		Hour = hour;
		String TimeStage="";
		if(AP==0)
		{
			if(Hour<4)
				TimeStage="¡Ë≥ø";
			else if(Hour<7)
				TimeStage="«Â≥ø";
			else if(Hour<10)
				TimeStage="‘Á≥ø";
			else
				TimeStage="÷–ŒÁ";
		}
		else
		{
			if(Hour<4)
				TimeStage="÷–ŒÁ";
			else if(Hour<7)
				TimeStage="œ¬ŒÁ";
			else if(Hour<10)
				TimeStage="ª∆ªË";
			else
				TimeStage="∞Î“π";
		}
		HeadSign.setText(TimeStage+"  "+Hour+" : "+Minute);
	}
	
	
	public int getMinute()
	{
		return Minute;
	}
	public void setMinute(int minute)
	{
		Minute = minute;
		String TimeStage="";
		if(AP==0)
		{
			if(Hour<4)
				TimeStage="¡Ë≥ø";
			else if(Hour<7)
				TimeStage="«Â≥ø";
			else if(Hour<10)
				TimeStage="‘Á≥ø";
			else
				TimeStage="÷–ŒÁ";
		}
		else
		{
			if(Hour<4)
				TimeStage="÷–ŒÁ";
			else if(Hour<7)
				TimeStage="œ¬ŒÁ";
			else if(Hour<10)
				TimeStage="ª∆ªË";
			else
				TimeStage="∞Î“π";
		}
		HeadSign.setText(TimeStage+"  "+Hour+" : "+Minute);
	}


	public String getNoteText()
	{
		return NoteText;
	}
	public void setNoteText(String noteText)
	{
		NoteText = noteText;
		Note.setText(NoteText);
	}


	public int getLastLength()
	{
		return LastLength;
	}
	public void setLastLength(int lastLength)
	{
		LastLength = lastLength;
	}


	public int getLoopTimes()
	{
		return LoopTimes;
	}
	public void setLoopTimes(int loopTimes)
	{
		LoopTimes = loopTimes;
	}


	public int getSpaceTime()
	{
		return SpaceTime;
	}
	public void setSpaceTime(int spaceTime)
	{
		SpaceTime = spaceTime;
	}


	public BorderPane getPane()
	{
		return pane;
	}
	public void setPane(BorderPane pane)
	{
		this.pane = pane;
	}


	public Label getHeadSign()
	{
		return HeadSign;
	}
	public void setHeadSign(Label headSign)
	{
		HeadSign = headSign;
	}

	
	public Label getNote()
	{
		return Note;
	}
	public void setNote(Label note)
	{
		Note = note;
		Note.setStyle("-fx-font-family: 'FangSong';-fx-font-size: 30;");
	}

	public RadioButton getIfOn()
	{
		return IfOn;
	}
	public void setIfOn(RadioButton ifOn)
	{
		IfOn = ifOn;
	}


	public Button getDelete()
	{
		return Delete;
	}
	public void setDelete(Button delete)
	{
		Delete = delete;
	}


	@Override
	protected boolean impl_computeContains(double arg0, double arg1)
	{
		return false;
	}
	@Override
	public BaseBounds impl_computeGeomBounds(BaseBounds arg0, BaseTransform arg1)
	{
		return null;
	}
	@Override
	protected NGNode impl_createPeer()
	{
		return null;
	}
	@Override
	public Object impl_processMXNode(MXNodeAlgorithm arg0, MXNodeAlgorithmContext arg1)
	{
		return null;
	}
}
