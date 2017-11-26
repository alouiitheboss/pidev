package persistence;


import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: PlainPassword
 *
 */
@Entity

public class PlainPassword implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPlain;
	private String Password;
	private String Username;
	private static final long serialVersionUID = 1L;

	public PlainPassword() {
		super();
	}   
	public int getIdPlain() {
		return this.idPlain;
	}

	public void setIdPlain(int idPlain) {
		this.idPlain = idPlain;
	}   
	public String getPassword() {
		return this.Password;
	}

	public void setPassword(String Password) {
		this.Password = Password;
	}
	public String getUsername() {
		return this.Username;
	}
	public void setUsername(String user) {
		this.Username = user;
	}
	
   
}
