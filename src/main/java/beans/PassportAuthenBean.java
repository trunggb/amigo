package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import entities.Passport;
import entities.PassportState;
import lombok.Getter;
import lombok.Setter;
import services.DialogService;
import services.PassportService;

@SuppressWarnings("deprecation")
@ManagedBean(name="passportAuthenBean")
@ViewScoped
public class PassportAuthenBean implements Serializable{
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
	
	@EJB
	DialogService dialogService;
	
	private static final long serialVersionUID = 4098294323352760786L;
	
	@PostConstruct
	public void init() {
		this.passport = new Passport();
		this.passports = passportService.findAllByState(PassportState.AUTHENTICATION);
	}
	
	public void comparePassport(Integer passportId) {
		
		Map<String, Object> options = dialogService.createDialogOption(600,500);

		Map<String, List<String>> params = new HashMap<>();
		List<String> productIdAsTring = new ArrayList<>(); // just send one id
		productIdAsTring.add(String.valueOf(passportId));
		params.put("passportId", productIdAsTring);
		PrimeFaces.current().dialog().openDynamic("comparePassport", options, params);
	}
}
