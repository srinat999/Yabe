package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Users extends Model{
	
	public String email;
	public String password;
	public String fullName;
	public String isAdmin;
	
	public Users (String email, String password, String fullName){
		this.email = email;
		this.password = password;
		this.fullName = fullName;
	}
	
	public static Users connect(String email, String password){
		return Users.find("byEmailAndPassword", email, password).first();
	}
	
}
