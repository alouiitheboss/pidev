package mbeans.users;


import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import service.UserManagment;



@ManagedBean
@RequestScoped
public class UsersImageBean implements Serializable {
	
	private StreamedContent image;
	@EJB
	private UserManagment userManagment;
	@ManagedProperty("#{helperBean.defaultPicture}")
	private byte[] defaultPicture;
	@ManagedProperty("#{param.userName}")
	private String userName;
	
	@PostConstruct
	public void init() {
		if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
		
			image = new DefaultStreamedContent();
		} else {
			
			byte[] usersPicture = (userManagment.findByUsername(userName)).getPicture();
			if (usersPicture == null) {
				image = new DefaultStreamedContent(new ByteArrayInputStream(
						defaultPicture));
			} else {
				image = new DefaultStreamedContent(new ByteArrayInputStream(
						usersPicture));
			}
		}
	}

	public StreamedContent getImage() {
		return image;
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	public UserManagment getUserManagment() {
		return userManagment;
	}

	public void setUserManagment(UserManagment userManagment) {
		this.userManagment = userManagment;
	}

	public byte[] getDefaultPicture() {
		return defaultPicture;
	}

	public void setDefaultPicture(byte[] defaultPicture) {
		this.defaultPicture = defaultPicture;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
