package service;

import javax.ejb.Local;

import persistence.*;

@Local
public interface TokenServiceRemote {
	public void setToken(String tokenValue, User user);
	public User getUser(String tokenValue);
	public Token find(String tokenValue);
	public Token find(User u);
	public void save(Token token);
	public void disableToken(User u) throws Exception;
}
