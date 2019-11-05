package beans;

import java.io.Serializable;

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
@ManagedBean(name="passportAddBean")
@ViewScoped
public class PassportAddBean implements Serializable{
	/**
	 * 
	 */
	@Getter
	@Setter
	private Passport passport;
	
	@EJB
	PassportService passportService;
	
	private static final long serialVersionUID = 4098294323352760786L;
	
	@PostConstruct
	public void init() {
		this.passport = new Passport();
	}
	
	public String onclickSubmitButton() {
		this.passport.setState(PassportState.AUTHENTICATION);
		passportService.add(passport);
		return "thank-page";
	}
	
}
