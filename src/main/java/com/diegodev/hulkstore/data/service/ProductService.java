package com.diegodev.hulkstore.data.service;

import com.diegodev.hulkstore.data.entity.shop.Product;
import com.diegodev.hulkstore.data.service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService extends CrudBaseService {

    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Product> findAllProducts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.search(stringFilter);
        }
    }

    public void purchaseProducts(List<Product> products) {
        products.forEach(product -> {
            if(product.getStock() > 0) {
                product.setStock(product.getStock() - 1);
                if (product.getStock() == 0) {
                    product.setOutOfStock(true);
                }
                update(product);
            }
        });
    }

    public long countProducts() {
        return repository.count();
    }

    public void deleteProduct(Product Product) {
        repository.delete(Product);
    }

    public void saveProduct(Product Product) {
        if (Product == null) {
            System.err.println("Product is null. Are you sure you have connected your form to the application?");
            return;
        }
        repository.save(Product);
    }
}
