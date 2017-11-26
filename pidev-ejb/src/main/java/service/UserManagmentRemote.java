package service;


import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;

import persistence.PlainPassword;
import persistence.User;



@Remote
public interface UserManagmentRemote {
	
	/**
	 * this method add a user to the database
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * this method allowed to Update User's information
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * this method allowed to enable a user isEnable will be = true
	 * @param user
	 */
	public void enableUser(User user);
	
	/**
	 * this method allowed to disable a user isEnable will be = false
	 * @param user
	 */
	public void disableUser(User user);
	
	/**
	 * this method allowed to get all users 
	 * @return
	 */
	public List<User> getAllUsers();
	
	/**
	 * this method will check if the username and the password entred bellow are avaible
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean loginUser(String username , String password);
	
	/**
	 * this method will redirect user to admin dashboard , artist dashboard or Gallery dashboard
	 * @param user
	 * @return 
	 * if returned : 0 -> Admin , 1 -> artist , 2 -> Gallery
	 */
	public int RedirectUser(User user);
	
	/**
	 * this method will return the user with the same id given
	 * @param id
	 * @return
	 */
	public User findById(int id);
	
	/**
	 * this method will return the user with the same username given
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	
	/**
	 * this method will return the user with the same email given
	 * @param email
	 * @return
	 */
	public User findByEmail(String email);
	
	/**
	 * this method allowed you to block a user ; set his isBlock value to true
	 * @param user
	 */
	public void blockUser(User user);
	
	/**
	 * this method allowed you to unblock a user ; set his isBlock value to false
	 * @param user
	 */
	public void unblockUser(User user);
	
	/**
	 * this method will filter the Users by firstName
	 * @param name
	 * @return
	 */
	public List<User> filterFirstName(String name);
	
	/**
	 * this method will filter the Users by lastName 
	 * @param name
	 * @return
	 */
	public List<User> filterLastName(String name);
	
	/**
	 * this method will filter the Users by lastName and FirstName 
	 * @param name
	 * @return
	 */
	public List<User> filterLastNameAndLastName(String name);
	
	/**
	 *  this method will filter the Users by isActive status
	 * @return only the blockedUser
	 */
	public List<User> filterBlockedUser();
	
	/**
	 * this method will filter the Users by isActive status
	 * @return only the blockedUser
	 */
	public List<User> filterActiveUser();
	
	/**
	 * In this method the Recipient will get an email from fannytunisia
	 * @param Recipient
	 * @param text
	 * @param subject
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendMail(String Recipient ,String text , String subject) throws AddressException, MessagingException;
	
	/**
	 * this method will returns an 8 alphaNumeric code that the user will use it to reset his password
	 * @return
	 */
	public String codeGeneration();
	
	/**
	 * this method returns a list of all the Artists
	 * @return
	 */

	
	/**
	 * this method allowed user to follow another user
	 * @param follower
	 * @param user
	 */
	
	/**
	 * this method return true in case the user entered a valid mail
	 * @param mail
	 * @return
	 */
	public boolean verifyMail(String mail);
	
	/**
	 * this method returns true in case that the mail entred doesn't exist in the database
	 * @param mail
	 * @return
	 */
	public boolean checkMailExistance(String mail);
	
	/**
	 * this method returns true in case that the username entred doesn't exist in the database
	 * @param username
	 * @return
	 */
	public boolean checkUsernameExistance(String username);
	
	/**
	 * this method returns all artist followed by user
	 * @param user
	 * @return
	 */
	
	
	public String generateMD5Code(String passwordToCrypt) throws NoSuchAlgorithmException;
	
	public PlainPassword getPlainPasswordByUsername(String username);
	

}
