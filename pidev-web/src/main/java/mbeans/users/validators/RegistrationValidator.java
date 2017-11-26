package mbeans.users.validators;


import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import mbeans.users.AuthentificationRegistrationBean;
import service.UserManagment;

@ManagedBean
@RequestScoped
public class RegistrationValidator implements Serializable {
	@EJB
	private UserManagment userManagment;
	@ManagedProperty("#{AuthentificationBean}")
	private AuthentificationRegistrationBean userBean;
	
	public AuthentificationRegistrationBean getUserBean() {
		return userBean;
	}

	public void setUserBean(AuthentificationRegistrationBean userBean) {
		this.userBean = userBean;
	}

	public void usernameValidation(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String loginToValidate = (String) value;
		if (loginToValidate == null || loginToValidate.trim().isEmpty()) {
			return;
		}
		if (userManagment.findByUsername(loginToValidate) != null) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "FannyError", "login already in use!"));
		}
	}

	public void mailValidation(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String mail = (String) value;
		if (mail == null || mail.trim().isEmpty()) {
			return;
		}
		if (userManagment.findByEmail(mail) != null) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "FannyError", "Email already in use!"));
		}
		if (!userManagment.verifyMail(mail)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "FannyError", "Bad email "));
		}
	}

	public void firstnameValidation(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String firstname = (String) value;
		for (int i = 0; i < firstname.trim().length(); i++) {
			if ((firstname.trim().toUpperCase().charAt(i) > 90) || (firstname.trim().toUpperCase().charAt(i) < 65)) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "FannyError",
						"First Name must Contains only characters"));
			}
		}
	}

	public void lastnameValidation(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String lastname = (String) value;
		for (int i = 0; i < lastname.trim().length(); i++) {
			if ((lastname.trim().toUpperCase().charAt(i) > 90) || (lastname.trim().toUpperCase().charAt(i) < 65)) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "FannyError",
						"Last Name must Contains only characters"));
			}
		}
	}

	public void passwordValidation(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String password = (String) value;
		String verification = (String) component.getAttributes().get("confirm");
		if (!password.equals(verification)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "FannyError",
					"Password Doesn't Match"));
		}

	}
	
	public void passwordUpValidator(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String password = (String) value;
		if (password.trim().length() < 6) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "FannyError",
					"Password minimum length is 6"));
		}

	}
	
	

}
