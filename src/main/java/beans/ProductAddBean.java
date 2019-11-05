package beans;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import entities.Product;
import entities.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import services.ProductService;

@SuppressWarnings("deprecation")
@ManagedBean(name="productAddBean")
@ViewScoped
public class ProductAddBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4098294323352760786L;
	@Setter
	@Getter
	private Product product;
	
	@Setter
	@Getter
	private String inDateString;
	
	@EJB
	ProductService productService;
	
	final static Logger logger = Logger.getLogger(ProductService.class);
	
	@PostConstruct
	public void init() {
		this.product = new Product();
		this.product.setInDate(new Date());
		this.inDateString = this.product.getInDate().toString();
	}
	
	public ProductStatus[] getProductStatus() {
		return ProductStatus.values();
	}
	 
	public void addProduct() {
		this.productService.create(product);
		
		PrimeFaces.current().executeScript("parent.showSuccessMessage('Product added succesfully!')");
		PrimeFaces.current().executeScript("parent.reloadPage();");
	}
	public void updatedDate() {
		this.inDateString = this.product.getInDate().toString();
	}
	
	public void onStatusChange(ValueChangeEvent e) {
		System.out.println(e.getNewValue().toString());
	}
}
