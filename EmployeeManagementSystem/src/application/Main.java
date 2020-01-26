package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application 
	{
	static Connection con=null;
	static Statement st=null;
		@Override
		public void start(Stage primaryStage) 
			{
			try
				{
					Parent root=FXMLLoader.load(getClass().getResource("Login.fxml"));
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.setTitle("Employee ID");
					primaryStage.show();
				} 
			catch(Exception e) 
				{
					e.printStackTrace();
				}
			}
	
	public static void main(String[] args) 
		{
		String url="jdbc:mysql://localhost:3306/"	;
		String user="pma";
		String pass="";
		try {
			con=DriverManager.getConnection(url, user, pass);
			System.out.println(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
			launch(args);
			
			
	}
	}
