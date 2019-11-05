package services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import entities.Passport;
import entities.PassportState;


@Stateless
public class PassportService extends GenericService<Passport> {

	public PassportService() {
		super();
	}

	public boolean add(Passport passport) {
		return this.create(passport);
	}

	public boolean updatePassport(Passport passport) {
		return this.update(passport);
	}
	
	public Optional<Passport> find(int id){
		if(id >= 0) {
			Passport ret = this.em.find(Passport.class, id);
			if(ret != null) {
				return Optional.of(ret);
			}
		}
		return Optional.empty();
	}
	
	public List<Passport> findAll() {
		TypedQuery<Passport> q = em.createNamedQuery("findAllPassPort", Passport.class);
		return q.getResultList();
	}
	
	public List<Passport> findAllByState(PassportState state) {
		List<Passport> list = findAll();
		return list.stream().filter(passport -> state.equals(passport.getState())).collect(Collectors.toList());
	}
}
