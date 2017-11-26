package mbeans.users;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.Part;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import persistence.Role;
import persistence.User;
import service.UserManagment;



@ManagedBean(name = "AuthentificationBean")
@SessionScoped
public class AuthentificationRegistrationBean implements Serializable {

	@EJB
	private UserManagment userManagment;
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	private User userLoggedIN;
	private User newUser;
	private User user;
	

	private boolean isArtist = false;
	private boolean isGallery = false;
	private String Choice;
	private Part picture;
	private String mailForg;
	private String code;
	private String codeTypedIn;
	private String passwordTypedIn;

	public String getPasswordTypedIn() {
		return passwordTypedIn;
	}

	public void setPasswordTypedIn(String passwordTypedIn) {
		this.passwordTypedIn = passwordTypedIn;
	}

	public String getCodeTypedIn() {
		return codeTypedIn;
	}

	public void setCodeTypedIn(String codeTypedIn) {
		this.codeTypedIn = codeTypedIn;
	}

	public String getMailForg() {
		return mailForg;
	}

	public void setMailForg(String mailForg) {
		this.mailForg = mailForg;
	}

	public Part getPicture() {
		return picture;
	}

	public void setPicture(Part picture) {
		this.picture = picture;
	}

	public String getChoice() {
		return Choice;
	}

	public void setChoice(String choice) {
		Choice = choice;
	}

	public boolean isArtist() {
		return isArtist;
	}

	public void setArtist(boolean isArtist) {
		this.isArtist = isArtist;
	}

	public boolean isGallery() {
		return isGallery;
	}

	public void setGallery(boolean isGallery) {
		this.isGallery = isGallery;
	}

	@PostConstruct
	public void __init__() {
		userLoggedIN = new User();
		newUser = new User();
		Choice = new String();
		
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public UserManagment getUserManagment() {
		return userManagment;
	}

	public void setUserManagment(UserManagment userManagment) {
		this.userManagment = userManagment;
	}

	public User getUser() {
		return userLoggedIN;
	}

	public void setUser(User user) {
		this.userLoggedIN = user;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User userChoosen) {
		this.newUser = userChoosen;
	}

	public String doSignUp() throws NoSuchAlgorithmException {
		String navTo = "";
		userManagment.addUser(newUser);
		return navTo;
	}

	

	
	public String doLogin() {
		String navTo = "";
		if (userManagment.loginUser(userLoggedIN.getUsername(), userLoggedIN.getPassword())) {
			userLoggedIN = userManagment.findByUsername(userLoggedIN.getUsername());
			userBean.setUser(userLoggedIN);
			userBean.setLoggedIN(true);
			if (userLoggedIN.getRole().equals(Role.Admin)) {
				navTo = "pages/admin/Admin?faces-redirect=true";
			} else {
				if (userLoggedIN.getRole().equals(Role.BO)||userLoggedIN.getRole().equals(Role.RH)||userLoggedIN.getRole().equals(Role.SI) ) {
					
				navTo = "pages/users/Home?faces-redirect=true";
				}
			}
		}
		// status = "Login Failed please try again";
		return navTo;
	}

	public String Continue() {
		String navTo;
		navTo = "pages/registration/makeChoice?faces-redirect=true";
		return navTo;
	}

	public void artistChoice() {
		isArtist = true;
		isGallery = false;
	}

	public void galleryChoice() {
		isArtist = false;
		isGallery = true;
	}

	public String Go() {
		System.err.println(Choice);
		String navTo;
		if (Choice.contains("Artist")) {
			navTo = "pages/registration/LastStep?faces-redirect=true";
			return navTo;
		} else if (Choice.contains("Gallery")) {
			navTo = "pages/registration/LastStep?faces-redirect=true";
			return navTo;
		} else {
			return "";
		}
	}
	public String artist() {
		String navTo;
		navTo = "pages/registration/LastStep?faces-redirect=true";
			return navTo;
		
	}
	public String galery() {
		String navTo;
		navTo = "pages/registration/LastStep?faces-redirect=true";
		return navTo;
	
}
	

	public String doLogout() {
		String navigateTo = null;
		__init__();
		userBean.setLoggedIN(false);
		userBean.setUser(userLoggedIN);
		userBean.setUserChoosen(userLoggedIN);
		userBean.setUserProfile(userLoggedIN);
		navigateTo = "/index?faces-redirect=true";
		return navigateTo;
	}

	

	public String doAddUser() {
		user = new User();
		user.setFirstname(newUser.getFirstname());
		user.setLastname(newUser.getLastname());
		user.setEmail(newUser.getEmail());
		user.setPassword(newUser.getPassword());
		user.setUsername(newUser.getUsername());
		if(newUser.getRole().equals(Role.BO)){
			user.setRole(Role.BO);
		}else if(newUser.getRole().equals(Role.RH)){
			user.setRole(Role.RH);
		}else if(newUser.getRole().equals(Role.SI)){
			user.setRole(Role.SI);
		}
		byte[] bFile = new byte[(int) picture.getSize()];
		try {
			picture.getInputStream().read(bFile);

		} catch (Exception e) {
		}
		user.setPicture(bFile);
		userManagment.addUser(user);
		return "";
	}

	public void doForgetPW() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("RessetPassword()");
	}

	public void ResetPW1() {
		if (userManagment.findByEmail(mailForg) != null) {
			code = userManagment.codeGeneration();
			Runnable mailing = new Runnable() {
				@Override
				public void run() {
					try {
						userManagment.sendMail(mailForg, "to reset your password use this code : " + code, "Resetting password");
					} catch (MessagingException e) {
					}		
				}
				
			};
			new Thread(mailing).start();
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("RessetPassword1()");
		}
		else
		{
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("Error()");
		}

	}
	
	public void ResetPW2() {
		if(codeTypedIn.equals(code))
		{
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("RessetPassword2()");			
		}			
		else
		{
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("Error2()");
		}

	}
	
	public void ResetPW3() {
		User u = userManagment.findByEmail(mailForg);
		u.setPassword(passwordTypedIn);
		userManagment.updateUser(u);
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("FinalStep()");

	}
	
	
}
