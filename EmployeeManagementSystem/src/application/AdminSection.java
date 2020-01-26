package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.sql.*;

public class AdminSection implements Initializable {
	@FXML
	TableView<Categories> table;
	@FXML
	TableColumn<Categories, Integer> tid;
	@FXML
	TableColumn<Categories, String> tfirst;
	@FXML
	TableColumn<Categories, String> tlast;
	@FXML
	TableColumn<Categories, String> tph_no;
	@FXML
	TableColumn<Categories, String> temail;
	@FXML
	TableColumn<Categories, String> tservicetype;
	@FXML
	TableColumn<Categories, String> tbloodgrp;
	@FXML
	TableColumn<Categories, String> tgender;
	@FXML
	TableColumn<Categories, String> tdob;
	@FXML
	ComboBox<String> searchby;
	@FXML
	TextField searchtext;
	@FXML
	Label lberror;
	@FXML
	Button searchbtn;
	@FXML
	Label lbAdmin;
	static List<String> SelectedId = new ArrayList<String>();
	
	Timeline t2=new Timeline();
	Timeline t1=new Timeline();
	Stage primaryStage=new Stage();
	@Override
	public void initialize(URL location, ResourceBundle resources) 
		{
			try 
				{
				downloadinfo("select * from employeerecsys.information;");
				} 
			catch (SQLException e)
				{
					e.printStackTrace();
				}
		
			tid.setCellValueFactory(new PropertyValueFactory<Categories, Integer>("Id"));
			tfirst.setCellValueFactory(new PropertyValueFactory<Categories, String>("FirstName"));
			tlast.setCellValueFactory(new PropertyValueFactory<Categories, String>("LastName"));
			tph_no.setCellValueFactory(new PropertyValueFactory<Categories, String>("Ph_no"));
			temail.setCellValueFactory(new PropertyValueFactory<Categories, String>("EmailID"));
			tservicetype.setCellValueFactory(new PropertyValueFactory<Categories, String>("Service_type"));
			tbloodgrp.setCellValueFactory(new PropertyValueFactory<Categories, String>("Blood_grp"));
			tgender.setCellValueFactory(new PropertyValueFactory<Categories, String>("Gender"));
			tdob.setCellValueFactory(new PropertyValueFactory<Categories, String>("DOB"));
			table.setItems(Lists.list);
			table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			searchby.setItems(Lists.searchbylist);
		
		}
	
	public void downloadinfo(String queryString) throws SQLException //for anything related to displaying content on table
		{
			Lists.list.clear();
		
			String query=queryString;
			
			Main.st=Main.con.createStatement();
			ResultSet rs=Main.st.executeQuery(query);
		
		while(rs.next())
			{
				int ID=rs.getInt(1);
				String FirstName=rs.getString(2);
				String LastName=rs.getString(3);
				String PhoneNo=rs.getString(4);
				String Email=rs.getString(5);
				String Service=rs.getString(6);
				String BloodGroup=rs.getString(7);
				String Gender=rs.getString(8);
				String DateOfBirth=rs.getString(9);
			
				Lists.list.addAll(new Categories(ID, FirstName, LastName, Gender, PhoneNo, BloodGroup, DateOfBirth, Email, Service));
		
			}
		
		
	}
	
	public void UserSection(ActionEvent event) throws IOException //For entering into user section
		{
			Stage primaryStage=new Stage();
			Parent root=FXMLLoader.load(getClass().getResource("UserSection.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Add Information");
			primaryStage.show();
		}
	
	public void RemoveID(ActionEvent event) throws SQLException  //Removing content from table
		{
			List<Integer> Selected;
			Selected=table.getSelectionModel().getSelectedIndices();
		
			for(int x:Selected)
				{
					int tempSelected=tid.getCellData(x);
					String a=String.valueOf(tempSelected);
					SelectedId.add(a);
					System.out.println("ID's :"+SelectedId);
					removefromdb();
				}
			downloadinfo("select * from employeerecsys.information;");
		}
	
	public void blink()  //animation for displaying admin section when cursor is on the label
		{
			t2.setCycleCount(Animation.INDEFINITE);
			t2.autoReverseProperty().set(true);;
			t2.getKeyFrames().addAll(new KeyFrame(Duration.seconds(0), new KeyValue(lbAdmin.opacityProperty(), 1)),
			new KeyFrame(Duration.seconds(1), new KeyValue(lbAdmin.opacityProperty(), 0)));
			t2.playFromStart();
		}
	
	public void blinkstop() //stop animation
		{
			t2.stop();
			lbAdmin.setOpacity(1);
		}
	
    public void removefromdb() throws SQLException //remove content from database
    	{
    		String AtoC=String.join(",", SelectedId);
    		
    		System.out.println(AtoC);
    		String query="delete from employeerecsys.information where ID IN ("+AtoC+");";
			
			Main.st=Main.con.createStatement();
			int i=Main.st.executeUpdate(query);
			if(i==1)
				System.out.println("removed successfully");
			
    	}
    
    public void search()  //search button
    	{
    		String searchbystr=searchby.getSelectionModel().getSelectedItem();
    		if(searchbystr!=null)
    			{	
    		
    				ObjectProperty<Paint> op =searchbtn.textFillProperty();
    				ColorPicker cp=new ColorPicker();
    				op.bind(cp.valueProperty());
    				lberror.setText("Select Search by");
    				t1.autoReverseProperty().set(true);;
    				t1.getKeyFrames().addAll(
    					new KeyFrame(Duration.seconds(0), new KeyValue(lberror.opacityProperty(), 0)),
    					new KeyFrame(Duration.seconds(0.2),new KeyValue(cp.valueProperty(), Color.DARKTURQUOISE)),
    					new KeyFrame(Duration.seconds(0.5),new KeyValue(cp.valueProperty(), Color.FUCHSIA)),
    					new KeyFrame(Duration.seconds(0.7),new KeyValue(cp.valueProperty(), Color.LIME)),
    					new KeyFrame(Duration.seconds(0.9),new KeyValue(cp.valueProperty(), Color.TEAL)),
    					new KeyFrame(Duration.seconds(1.2),new KeyValue(cp.valueProperty(), Color.CORAL)),
    					new KeyFrame(Duration.seconds(1.5),new KeyValue(cp.valueProperty(), Color.ALICEBLUE)),
    					new KeyFrame(Duration.seconds(2),new KeyValue(cp.valueProperty(), Color.GOLD)),
    					new KeyFrame(Duration.seconds(2.5),new KeyValue(cp.valueProperty(), Color.BLUE)),
    					new KeyFrame(Duration.seconds(3),new KeyValue(cp.valueProperty(), Color.PURPLE)),
    					new KeyFrame(Duration.seconds(3.5),new KeyValue(cp.valueProperty(), Color.RED)),
    					new KeyFrame(Duration.seconds(1),new KeyValue(lberror.opacityProperty(), 1)),
    					new KeyFrame(Duration.seconds(4),new KeyValue(cp.valueProperty(), Color.WHITE)),
    					new KeyFrame(Duration.seconds(4), new KeyValue(lberror.opacityProperty(), 0)));
    					t1.playFromStart();
    			}
    		
       		String getSearchText=searchtext.getText();
    		System.out.println("select * from employeerecsys.information where "+searchbystr+" Like '"+getSearchText+"';");
    	
    		try 
    			{
    				downloadinfo("select * from employeerecsys.information where "+searchbystr+" Like '"+getSearchText+"%';");
    			}
    		catch (SQLException e) 
    			{
    				System.out.println(e);
    			}
    }
    
    public void reload() throws SQLException  //downloading content from database
    	{
    		downloadinfo("select * from employeerecsys.information;");
    	}
    
    public void sign_out(ActionEvent event) throws IOException, SQLException
        {
    	Parent root=FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Employee ID");
		primaryStage.show();
    	 ((Node)(event.getSource())).getScene().getWindow().hide();
        }	
    
}
