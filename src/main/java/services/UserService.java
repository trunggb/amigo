package services;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import entities.User;

@Stateless
public class UserService extends GenericService<User> {

	Logger logger = Logger.getLogger(UserService.class);
	public boolean add(User user) {
		return this.create(user);
	}

	public List<User> findAll() {
		TypedQuery<User> q = em.createNamedQuery("findAllUser", User.class);
		return q.getResultList();
	}

	public Optional<User> findUserByUserName(String userName) {

		TypedQuery<User> query = this.em.createNamedQuery("findUserByUserName", User.class);
		query.setParameter("userName", userName);
		try {
			User ret =query.getSingleResult();
			return Optional.of(ret);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Optional.empty();
		}
	}
	
	public Optional<User> checkValidUser(String userName, String passWord){
		Optional<User> optionalUser = findUserByUserName(userName);
		if(optionalUser.isPresent() && passWord.equals(optionalUser.get().getPassword())) {
			return optionalUser;
		}else {
			return Optional.empty();
		}
	}
}
