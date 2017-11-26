package mbeans.users;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import persistence.PlainPassword;
import persistence.Role;
import persistence.User;
import service.UserManagment;


@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

	@EJB
	private UserManagment userManagment;
	private User userLoggedIN;
	private User userChoosen;
	private User userProfile;
	private User user ;
	private boolean loggedIN = false;
	private boolean editIt = false;
	private boolean isVisitor = false;
	private boolean isSearched = false;

	private boolean AdminUser = false;
	private boolean NormalUser = false;
	List<User> Lu;
	List<User> Lru;
	List<User> filteredUsers;
	String typedQuery;
	private Map<User, Long> map;

	@PostConstruct
	public void remplirList() {
		user = new User();
		userLoggedIN = new User();
		userChoosen = new User();
		userProfile = new User();
		Lu = new ArrayList<User>();
		Lru = new ArrayList<User>();
		filteredUsers = new ArrayList<User>();
		map = new HashMap<User, Long>();
		

	}

	public User getUserLoggedIN() {
		return userLoggedIN;
	}

	public void setUserLoggedIN(User userLoggedIN) {
		this.userLoggedIN = userLoggedIN;
	}

	public Map<User, Long> getMap() {
		return map;
	}

	public void setMap(Map<User, Long> map) {
		this.map = map;
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public boolean isVisitor() {
		return isVisitor;
	}

	public void setVisitor(boolean isVisitor) {
		this.isVisitor = isVisitor;
	}

	public User getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(User userProfile) {
		this.userProfile = userProfile;
	}

	public boolean isSearched() {
		return isSearched;
	}

	public void setSearched(boolean isSearched) {
		this.isSearched = isSearched;
	}

	public String getTypedQuery() {
		return typedQuery;
	}

	public void setTypedQuery(String typedQuery) {
		this.typedQuery = typedQuery;
	}

	public List<User> getLru() {
		return Lru;
	}

	public void setLru(List<User> lru) {
		Lru = lru;
	}

	public boolean isEditIt() {
		return editIt;
	}

	public void setEditIt(boolean editIt) {
		this.editIt = editIt;
	}

	public boolean isLoggedIN() {
		return loggedIN;
	}

	public void setLoggedIN(boolean loggedIN) {
		this.loggedIN = loggedIN;
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

	public List<User> getLu() {
		return Lu;
	}

	public void setLu(List<User> lu) {
		Lu = lu;
	}

	public User getUserChoosen() {
		return userChoosen;
	}

	public void setUserChoosen(User userChoosen) {
		this.userChoosen = userChoosen;
	}

	

	public String doEdit() {
		PlainPassword PP = userManagment.getPlainPasswordByUsername(userChoosen.getUsername());
		userChoosen.setPassword(PP.getPassword());
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("window.open('','_parent',''); window.close();");
		String navTo = "userUpdate?faces-redirect=true";
		return navTo;
	}

	public void doEditPerform() {
			user.setFirstname(userChoosen.getFirstname());
			user.setLastname(userChoosen.getLastname());
			user.setEmail(userChoosen.getEmail());
			user.setPassword(userChoosen.getPassword());
			userManagment.updateUser(user);
		
	
		userChoosen = userManagment.findByUsername(userChoosen.getUsername());
		Lu = userManagment.getAllUsers();
		Lu.remove(userLoggedIN);
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("window.parent.opener.location.reload()");
	}

	public String BackFromEdit() throws InterruptedException {
		String navTo = "/pages/admin/ManageUsers?faces-redirect=true";
		return navTo;
	}

	
	public String ShowAllUsers() {
		String navTo = "ManageUsers?faces-redirect=true";
		Lu = userManagment.getAllUsers();
		Lu.remove(userLoggedIN);
		
	
		return navTo;
	}

	
	public void BlockUser() throws AddressException, MessagingException {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		userManagment.blockUser(userChoosen);
		Lu = userManagment.getAllUsers();
		Lu.remove(userLoggedIN);
		requestContext.execute("window.parent.opener.location.reload()");
		userManagment.sendMail(userChoosen.getEmail(),
				"FannyTUNISIA is sorry to announce that we blocked your account for the moment",
				"Something went wrong");
	}

	
	public void unBlockUser() throws AddressException, MessagingException {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		userManagment.unblockUser(userChoosen);
		Lu = userManagment.getAllUsers();
		Lu.remove(userLoggedIN);
		requestContext.execute("window.parent.opener.location.reload()");
		userManagment.sendMail(userChoosen.getEmail(),
				"FannyTUNISIA is so glad to have you again in our community welcome aboard again", "Welcome again");
	}


	public void ApproveUser() throws AddressException, MessagingException {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		userManagment.enableUser(userChoosen);
		Lu = userManagment.getAllUsers();
		Lu.remove(userLoggedIN);
		requestContext.execute("window.parent.opener.location.reload()");
		userManagment.sendMail(userChoosen.getEmail(),
				"FannyTUNISIA is so glad to have you in our community so please behave and respect other members",
				"Welcome aboard");
	}


	public void search() {
		Lru.clear();
		List<User> Ltmp = userManagment.filterLastNameAndLastName(typedQuery);
		isSearched = true;
		for (User user : Ltmp) {
			if (!(user.getRole().equals(Role.Admin))) {
				Lru.add(user);
			}
		}
		Lru.remove(userLoggedIN);
	}

	

	public String doUserProfile(User user) {
		isVisitor = true;
		String navTo="";
		userProfile = user;
		if (user.getRole().equals(Role.BO)||user.getRole().equals(Role.RH)||user.getRole().equals(Role.SI)) 
		{
		 navTo = "Profile?faces-redirect=true";
		}
		return navTo;
	}

	public String viewMyOwnPage() {
		isVisitor = false;
		userProfile = userLoggedIN;
		String navTo = "/pages/users/Profile?faces-redirect=true";
		return navTo;
	}
	

	public void Open() {
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("showMyPage()");
	}

	public void editUser() {
		userManagment.updateUser(userProfile);
		userLoggedIN = userManagment.findByUsername(userLoggedIN.getUsername());
	}

	public void handleFileUpload(FileUploadEvent event) {
		byte[] picture = event.getFile().getContents();
		userProfile.setPicture(picture);
	}
	
	

}
