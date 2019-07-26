package com.riyaz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.riyaz.bean.ProductBean;
import com.riyaz.dao.ProductDao;

@Component
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public ProductService() {
		//productDao = new ProductDao();
	}
	
	public List<ProductBean> gettingProduct() {
		return productDao.getProduct();
	}
}
