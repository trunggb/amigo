package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import entities.Resident;

@Stateless
public class ResidentService extends GenericService<Resident> {

	public ResidentService() {
		super();
	}

	public List<Resident> findAll() {
		TypedQuery<Resident> q = em.createNamedQuery("findAllResident", Resident.class);
		return q.getResultList();
	}

	public List<Resident> findResidentByIdNumber(String idenNum) {

		TypedQuery<Resident> query = this.em.createNamedQuery("findResidentByIdNumber", Resident.class);
		query.setParameter("idenNum", idenNum);
		return query.getResultList();
	}

}
