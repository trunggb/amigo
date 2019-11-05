package beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import entities.Passport;
import entities.PassportState;
import lombok.Getter;
import lombok.Setter;
import services.PassportService;

@SuppressWarnings("deprecation")
@ManagedBean(name="passportApprovalBean")
@ViewScoped
public class PassportApprovalBean implements Serializable{
	/**
	 * 
	 */
	@Getter
	@Setter
	private Passport passport;
	
	@Getter
	@Setter
	private List<Passport> passports;
	
	
	
	@EJB
	PassportService passportService;
	
	private static final long serialVersionUID = 4098294323352760786L;
	
	@PostConstruct
	public void init() {
		this.passport = new Passport();
		this.passports = passportService.findAllByState(PassportState.APPROVAL);
	}
}
