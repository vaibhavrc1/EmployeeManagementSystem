package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Login implements Initializable{
	@FXML
	Label dev=new Label();
	@FXML
	TextField username;
	@FXML
	PasswordField password;
	@FXML
	Label errorIndicate;
	Timeline t1=new Timeline();
	Timeline t2=new Timeline();
	@FXML
	Button loginbtn;
	static boolean throughAdmin=false;
	
	
	
	public void adminlogin(ActionEvent event) //program to enter into admin section
{
		try {
		throughAdmin=true;
		passwordcheck("Select Password from employeerecsys.adminlogin where username='"+username.getText()+"';","AdminSection.fxml","Logged in as an admin", event);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
}
	
	
	public void userlogin(ActionEvent event) throws IOException //program to enter into user section
		{
		passwordcheck("Select Password from employeerecsys.userlogin where username='"+username.getText()+"';","UserSection.fxml","Fill information", event);
		}
	
	public void exit(ActionEvent event)  //exit button
		{
			System.exit(0);
		}
	
	public void fade(String error)   //animation for displaying wrong password
		{
			errorIndicate.setText(error);
			t1.getKeyFrames().addAll(new KeyFrame(Duration.seconds(0), new KeyValue(errorIndicate.opacityProperty(), 1)),
			new KeyFrame(Duration.seconds(3), new KeyValue(errorIndicate.opacityProperty(), 0)));
			t1.playFromStart();
		}
	
	public void blink()  //animation for developer name
		{
			t2.setCycleCount(Animation.INDEFINITE);
			t2.autoReverseProperty().set(true);;
			t2.getKeyFrames().addAll(new KeyFrame(Duration.seconds(0), new KeyValue(dev.opacityProperty(), 1)),
			new KeyFrame(Duration.seconds(1), new KeyValue(dev.opacityProperty(), 0)));
			t2.playFromStart();
		}
	
	public void blinkstop()  //for stopping animation
		{
			t2.stop();
			dev.setOpacity(1);
		}
	public void passwordcheck (String querystring,String fxmlString, String windowTitle ,ActionEvent event) throws IOException
	{
		try {
		
		String query =querystring;
		
		System.out.println(Main.con);
		Main.st=Main.con.createStatement();
		ResultSet rs=Main.st.executeQuery(query);
		rs.next();
		String password_db=rs.getString(1);
		if(password.getText().equals(password_db))
				{
					Stage primaryStage=new Stage();
					Parent root=FXMLLoader.load(getClass().getResource(fxmlString));
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.setTitle(windowTitle);
					primaryStage.show();
					((Node)(event.getSource())).getScene().getWindow().hide();
				
		}
	}
		catch(com.mysql.jdbc.exceptions.jdbc4.CommunicationsException NoSignal)
		{
			fade("Check your internet connection");
			System.out.println(NoSignal);
		}
catch(SQLException sqlException) {
	fade("Invalid username or/and password");
	System.out.println(sqlException);
}
			}
	
	public void signUp(ActionEvent event) throws IOException
	{
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("SignUp.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sign-Up page");
		primaryStage.show();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if(Main.con==null)
		{
			errorIndicate.setText("No Connection");
		}
	}

	
}
