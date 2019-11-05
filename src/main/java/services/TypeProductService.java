package services;

import javax.ejb.Stateless;

import entities.TypeProduct;

@Stateless
public class TypeProductService extends GenericService<TypeProduct> {
	public TypeProductService() {
		super();
	}
}
