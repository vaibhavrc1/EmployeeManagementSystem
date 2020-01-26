package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Categories {
	
	private SimpleIntegerProperty Id;   //Fields of each object
	private SimpleStringProperty FirstName;
	private SimpleStringProperty LastName;
	private SimpleStringProperty Gender;
	private SimpleStringProperty Ph_no;
	private SimpleStringProperty Blood_grp;
	private SimpleStringProperty DOB;
	private SimpleStringProperty EmailID;
	private SimpleStringProperty Service_type;

	public Categories(int id, String firstName, String lastName,
		String gender, String ph_no, String blood_grp,
		String dOB, String emailID, String servicetype)  //Constructor
		{
			super();
			Id = new SimpleIntegerProperty(id);
			FirstName = new SimpleStringProperty(firstName);
			LastName = new SimpleStringProperty(lastName);
			Gender = new SimpleStringProperty(gender);
			Ph_no = new SimpleStringProperty(ph_no);
			Blood_grp = new SimpleStringProperty(blood_grp);
			DOB = new SimpleStringProperty(dOB);
			EmailID = new SimpleStringProperty(emailID);
			Service_type=new SimpleStringProperty(servicetype);
		}
	public int getId() {             //getters
		return Id.get();
	}
	public String getFirstName() {
		return FirstName.get();
	}
	public String getLastName() {
		return LastName.get();
	}
	public String getGender() {
		return Gender.get();
	}
	public String getPh_no() {
		return Ph_no.get();
	}
	public String getBlood_grp() {
		return Blood_grp.get();
	}
	public String getDOB() {
		return DOB.get();
	}
	public String getEmailID() {
		return EmailID.get();
	}
	public String getService_type() {
		return Service_type.get();
	}
	
	
	
}
