package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Lists //lists required for table and combo-box
	{
		public static ObservableList<Categories> list=FXCollections.observableArrayList();
		public static ObservableList<String> servicetypelist=FXCollections.observableArrayList("Engineer","Assistant","Developer","Manager","Head");
		public static ObservableList<String> genderlist=FXCollections.observableArrayList("Male","Female");
		public static ObservableList<String> bloodgrplist=FXCollections.observableArrayList("A+","O+","B+","AB+","A-","O-","B-","AB-");
		public static ObservableList<String> searchbylist=FXCollections.observableArrayList("ID","First_Name","Last_Name","Phone_No","Email","Service_Type","Blood_Group","Gender","Date_of_Birth");
		
	}
