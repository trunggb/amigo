package beans;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import entities.Passport;
import entities.Product;
import lombok.Getter;
import lombok.Setter;
import services.PassportService;
import services.ProductService;

@SuppressWarnings("deprecation")
@ManagedBean(name="passportCompareBean")
@ViewScoped
public class PassportCompareBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4098294323352760786L;
	@Setter
	@Getter
	private Passport passport;
	
	@EJB
	PassportService passportService;
	final static Logger logger = Logger.getLogger(ProductService.class);
	
	@PostConstruct
	public void init() {
		String passportIdAsString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("passportId");
		if(StringUtils.isNotBlank(passportIdAsString)) {
			Optional<Passport> optionalPassport = passportService.find(Integer.valueOf(passportIdAsString));
			if(optionalPassport.isPresent()) {
				this.passport = optionalPassport.get();
			}else {
				logger.info("Can not find passport with id: " + passportIdAsString);
			}
		}else {
			logger.error("Can not get id of passport from list passport");
		}
		
	}
}
