package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class SignUp {
	@FXML
	TextField username;
	@FXML
	PasswordField password;
	@FXML
	PasswordField re_password;
	@FXML
	Label status;
	
	Timeline t1=new Timeline();
	PreparedStatement ps;
public void signUp(ActionEvent event)
{
	try {
	if(re_password.getText().equals(password.getText()))
	{
		
	String query = " INSERT INTO `employeerecsys`.`userlogin` VALUES ('"+username.getText()+"','"+password.getText()+"');";

	
	ps=Main.con.prepareStatement(query);

	int i=ps.executeUpdate(query);
	System.out.println(i);

	if(i==1) 
		{
		Status("Signed-Up successfully");
			System.out.println("Information uploaded successfully");
			username.clear();
			password.clear();
			re_password.clear();
		}
}
	else
	{
		Status("Passwords not matching");
	}
	}
	catch(SQLIntegrityConstraintViolationException duplicate)
	{
		System.out.println(duplicate+" username address");
		Status("username address already taken");
	}
	catch(Exception e)
	{
		System.out.println(e);
		Status("Unknown Exception");
	}
}
public void Status(String ExceptionText)
{
	status.setText(ExceptionText); 
	t1.getKeyFrames().addAll(new KeyFrame(Duration.seconds(0), new KeyValue(status.opacityProperty(), 1)),
	new KeyFrame(Duration.seconds(3), new KeyValue(status.opacityProperty(), 0)));
	t1.playFromStart();
}
public void back(ActionEvent event)
{
	((Node)(event.getSource())).getScene().getWindow().hide();

}
}