package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.sql.*;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserSection implements Initializable {
	@FXML
	TextField txId;
	@FXML
	TextField txFirstName;
	@FXML
	TextField txLastName;
	@FXML
	TextField txPhoneNo;
	@FXML
	TextField txEmail;
	@FXML
	ComboBox<String> cbBloodGrp;
	@FXML
	ComboBox<String> cbGender;
	@FXML
	ComboBox<String> cbServiceType;
	@FXML
	DatePicker dpDOB;
	@FXML
	Label lbError;
	LocalDate ld;
	
	Timeline t1=new Timeline();
	PreparedStatement  ps;
	
	public void submit()
		{	
			try
				{
					lbError.setOpacity(0);
					lbError.setText("Invalid ID");
					int id=Integer.valueOf(txId.getText());
					String first=txFirstName.getText();
					String last=txLastName.getText();
					String gender=cbGender.getSelectionModel().getSelectedItem();
					String phone_no=txPhoneNo.getText();
					String blood_grp=cbBloodGrp.getSelectionModel().getSelectedItem();
					ld=dpDOB.getValue();
					String dob=ld.toString();
					String email=txEmail.getText();
					String service_type=cbServiceType.getSelectionModel().getSelectedItem();
					if(first.isEmpty()||last.isEmpty()||gender.isEmpty()||phone_no.isEmpty()||blood_grp.isEmpty()||dob.isEmpty()||email.isEmpty()||service_type.isEmpty())
					{
					showException("Fill Complete form");	
					}
					else
					{
						if(phone_no.length()!=10)
						{
							showException("Phone no should be 10 digits");
							return;
						}
						if(!email.contains("@"))
						{
							showException("Not valid email");
							return;
						}
							
					String query = " INSERT INTO `employeerecsys`.`information` VALUES ("+id+",'"+first+"','"+last+"','"+phone_no+"','"+email+"','"+service_type+"','"+blood_grp+"','"+gender+"','"+dob+"');";
	
					
					ps=Main.con.prepareStatement(query);
	
					int i=ps.executeUpdate(query);
					System.out.println(i);

					if(i==1) 
						{
							System.out.println("Information uploaded successfully");
							AdminSection adminsection=new AdminSection();
							adminsection.downloadinfo("select * from employeerecsys.information;");
							System.out.println("Updated Successfully");
							showException("Updated Successfully");
						}
					else
							System.out.println("Error while uploading");
	
					txId.clear();
					txFirstName.clear();
					txLastName.clear();
					txPhoneNo.clear();
					txEmail.clear();
					cbServiceType.valueProperty().set(null);
					cbBloodGrp.valueProperty().set(null);
					cbGender.valueProperty().set(null);
					dpDOB.valueProperty().set(null);
					
					}
				}
			catch(SQLException e)
				{
					showException("ID is already taken");
				}
			catch(NumberFormatException e)
				{
					showException("Invalid ID");
				}
			catch(Exception e)
				{
					showException("Fill Complete form");
				}
		}
	
	public void close(ActionEvent event) throws IOException, SQLException
	{
	
		
		Stage primaryStage=new Stage();
		Main main =new Main();
		System.out.println("isOpen variable value before:"+Login.throughAdmin);
		
		System.out.println("isOpen variable value after:"+Login.throughAdmin);
		if(Login.throughAdmin==true)
			{
			System.out.println("Open?:"+Login.throughAdmin);
			((Node)(event.getSource())).getScene().getWindow().hide();
			}
		else
		{
			System.out.println("Open?:"+Login.throughAdmin);
			main.start(primaryStage);
		((Node)(event.getSource())).getScene().getWindow().hide();
		}
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
		{
			cbServiceType.setItems(Lists.servicetypelist);
			cbGender.setItems(Lists.genderlist);
			cbBloodGrp.setItems(Lists.bloodgrplist);
		}
	public void showException(String ExceptionText)
		{
			lbError.setText(ExceptionText); 
			t1.getKeyFrames().addAll(new KeyFrame(Duration.seconds(0), new KeyValue(lbError.opacityProperty(), 1)),
			new KeyFrame(Duration.seconds(3), new KeyValue(lbError.opacityProperty(), 0)));
			t1.playFromStart();
		}

}
