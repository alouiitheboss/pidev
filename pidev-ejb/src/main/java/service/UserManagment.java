package service;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import persistence.PlainPassword;
import persistence.Role;
import persistence.State;
import persistence.User;


/**
 * Session Bean implementation class UserManagment
 */
@Stateless
@LocalBean
public class UserManagment implements UserManagmentRemote {

	@PersistenceContext
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public UserManagment() {
		// TODO Auto-generated constructor stub
	}

	public boolean login(User u) throws Exception{
		System.out.println("Login from service : "+u);
		Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username "
				+ "AND u.password = :password");
		query.setParameter("username", u.getUsername());
		query.setParameter("password", MD5.crypt(u.getPassword()));
		int resultCount = query.getResultList().size();
		System.out.println("Found "+resultCount+" Result(s) ");
		if(resultCount != 1){
			return false;
		}
		return true;
	}
	
	public User findUser(int id) {
		User userFromDB = em.find(User.class, id);
		return userFromDB;
	}
	
	public User findUser(User user) {
		Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username OR u.phoneNumber = :phone");
		query.setParameter("username", user.getUsername());
		query.setParameter("phone", user.getPhoneNumber());
		User userFromDB = (User) query.getSingleResult();
		return userFromDB;
	}
	@Override
	public void addUser(User user) {
		user.setState(State.waiting);

		PlainPassword PP = new PlainPassword();
		PP.setUsername(user.getUsername());
		PP.setPassword(MD5.crypt(user.getPassword()));
		em.persist(em.merge(PP));
		try {
			user.setPassword(generateMD5Code(user.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		em.persist(em.merge(user));

	}

	@Override
	public void updateUser(User user) {
		String pwd;
		try {
			pwd = generateMD5Code(user.getPassword());
			User u = findByUsername(user.getUsername());
			if(u.getPassword().equals(user.getPassword()))
			{
				em.merge(user);
			}
			else
			{
				PlainPassword PP = getPlainPasswordByUsername(user.getUsername());
				PP.setPassword(user.getPassword());
				user.setPassword(pwd);
				em.merge(user);
				em.merge(PP);
			}
		} catch (NoSuchAlgorithmException e) {
		}

	}

	@Override
	public void enableUser(User user) {
		user.setState(State.activated);
		updateUser(user);

	}

	@Override
	public void disableUser(User user) {
		user.setState(State.waiting);
		updateUser(user);
	}

	@Override
	public List<User> getAllUsers() {
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u", User.class);
		List<User> Luser = q.getResultList();
		return Luser;

	}

	@Override
	public boolean loginUser(String username, String password) {
		User user = findByUsername(username);
		if (user == null) {
			return false;
		} else
			try {
				if (user.getPassword().equals(generateMD5Code(password))) {
					if (user.getState().equals(State.activated)) {
						return true;
					}
					return false;

				}
			} catch (NoSuchAlgorithmException e) {
				return false;
			}
		return false;

	}

	@Override
	public int RedirectUser(User user) {

		if (user.getRole().equals(Role.Admin)) {
			return 1;
		}
		if (user.getRole().equals(Role.BO)|| user.getRole().equals(Role.RH) || user.getRole().equals(Role.SI)) {
			return 2;
		}

		return 0;
	}

	@Override
	public User findById(int id) {
		try {
			User user = em.find(User.class, id);
			return user ;
		} catch (javax.persistence.NoResultException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User with id = " + id + " not found");
			return null;
		}
	}

	@Override
	public User findByUsername(String username) {
		try {
			TypedQuery<User> q = em.createQuery("SELECT u FROM User u where u.username =:username", User.class);
			q.setParameter("username", username);
			User user = q.getSingleResult();
			return user;
		} catch (javax.persistence.NoResultException  e) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO,
					"User with username = " + username + " not found");
			return null;
		}

	}

	@Override
	public User findByEmail(String email) {
		try {
			TypedQuery<User> q = em.createQuery("SELECT u FROM User u where u.email =:email", User.class);
			q.setParameter("email", email);
			User user = q.getSingleResult();
			return user;
		} catch (javax.persistence.NoResultException E) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User with email = " + email + " not found");
			return null;
		}
	}

	@Override
	public void blockUser(User user) {

		user.setState(State.blocked);
		updateUser(user);
	}

	@Override
	public void unblockUser(User user) {
		user.setState(State.activated);
		updateUser(user);
	}

	@Override
	public List<User> filterFirstName(String name) {
		try {
			TypedQuery<User> q = em.createQuery("SELECT u FROM User u where u.firstName LIKE :ln ", User.class);
			q.setParameter("ln", '%' + name + '%');
			List<User> Luser = q.getResultList();
			return Luser;
		} catch (javax.persistence.NoResultException E) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User with FirstName = " + name + " not found");
			return null;

		}
	}

	@Override
	public List<User> filterLastName(String name) {
		try {
			TypedQuery<User> q = em.createQuery("SELECT u FROM User u where u.lastName LIKE :fn ", User.class);
			q.setParameter("fn", '%' + name + '%');
			List<User> Luser = q.getResultList();
			return Luser;
		} catch (javax.persistence.NoResultException E) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User with LastName = " + name + " not found");
			return null;
		}
	}

	@Override
	public List<User> filterBlockedUser() {
		try {
			TypedQuery<User> q = em.createQuery("SELECT u FROM User u where u.isActive =:status ", User.class);
			q.setParameter("status", false);
			List<User> LuserBlocked = q.getResultList();
			return LuserBlocked;
		} catch (javax.persistence.NoResultException E) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "no blocked user found");
			return null;
		}
	}

	@Override
	public List<User> filterActiveUser() {
		try {

			TypedQuery<User> q = em.createQuery("SELECT u FROM User u where u.isActive =:status ", User.class);
			q.setParameter("status", true);
			List<User> LuserActive = q.getResultList();
			return LuserActive;
		} catch (javax.persistence.NoResultException E) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "no active user found");
			return null;
		}
	}

	@Resource(name = "java:jboss/mail/gmail")
	private Session session;

	@Asynchronous
	public void sendMail(String Recipient, String text, String subject) throws AddressException, MessagingException {
		// Recipient's email ID needs to be mentioned.
		String to = Recipient;

		// Sender's email ID needs to be mentioned
		String from = "fannytunisia@gmail.com";

		// Get system properties
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// Get the default Session object.

		// Session session = Session.getDefaultInstance(props);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("fannytunisia@gmail.com", "fanny2017");
			}
		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(text);

			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	@Override
	public String codeGeneration() {

		List<Integer> Lascii = new ArrayList<>();
		for (int i = 48; i < 58; i++) {
			Lascii.add(i);
		}

		for (int i = 65; i < 91; i++) {
			Lascii.add(i);
		}

		for (int i = 97; i < 123; i++) {
			Lascii.add(i);
		}
		Random randomizer = new Random();
		String random = "";
		for (int i = 0; i < 8; i++) {
			int x;
			x = Lascii.get(randomizer.nextInt(Lascii.size()));
			random += Character.toString((char) ((char) x));
		}
		return random;
	}

	

	@Override
	public boolean verifyMail(String mail) {
		String masque = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
				+ "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
		Pattern pattern = Pattern.compile(masque);
		Matcher controler = pattern.matcher(mail);
		if (controler.matches()) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean checkMailExistance(String mail) {
		if (findByEmail(mail) == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkUsernameExistance(String username) {
		if (findByUsername(username) == null) {
			return true;
		}

		return false;
	}



	@Override
	public List<User> filterLastNameAndLastName(String name) {
		try {
			TypedQuery<User> q = em
					.createQuery("SELECT u FROM User u where u.firstName LIKE :ln or u.lastName LIKE :ln ", User.class);
			q.setParameter("ln", '%' + name + '%');
			List<User> Luser = q.getResultList();
			return Luser;
		} catch (javax.persistence.NoResultException E) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO,
					"no user found with firstName like = " + name + " or lastName like = " + name);
			return null;
		}
	}

	@Override
	public String generateMD5Code(String passwordToCrypt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(passwordToCrypt.getBytes());

		byte byteData[] = md.digest();
		// convert the byte to hex format method 2
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		String passwordCrypted = hexString.toString();
		return passwordCrypted;
	}

	@Override
	public PlainPassword getPlainPasswordByUsername(String username) {
		try {
			TypedQuery<PlainPassword> q = em
					.createQuery("SELECT PP FROM PlainPassword PP where PP.Username =:usn ", PlainPassword.class);
			q.setParameter("usn",username);
			PlainPassword PP = q.getSingleResult();
			return PP;
		} catch (javax.persistence.NoResultException E) {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO,
					"no user found with username = " + username);
			return null;
		}
	}

	

	
}
