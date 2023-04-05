package com.diegodev.hulkstore.service;

import com.diegodev.hulkstore.dto.product.ProductDto;
import com.diegodev.hulkstore.exceptions.ProductNotExistException;
import com.diegodev.hulkstore.model.Product;
import com.diegodev.hulkstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends CrudBaseService<ProductRepository, Product> {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<ProductDto> listProducts() {
        List<Product> products = repository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public static ProductDto getDtoFromProduct(Product product) {
        return new ProductDto(product);
    }

    public static Product getProductFromDto(ProductDto productDto, String category) {
        return new Product(productDto, category);
    }

    public void addProduct(ProductDto productDto, String category) {
        Product product = getProductFromDto(productDto, category);
        save(product);
    }

    public void updateProduct(Integer productID, ProductDto productDto, String category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productID);
        repository.save(product);
    }

    public void updateProductStock(Product product, double quantity) {
        double newStock = product.getStock() - quantity;
        product.setStock(newStock);
        repository.save(product);
    }

    public Product getProductById(Integer productId) throws ProductNotExistException {
        Optional<Product> optionalProduct = get(productId);
        if (optionalProduct.isEmpty())
            throw new ProductNotExistException("Product id is invalid " + productId);
        return optionalProduct.get();
    }

    public List<Product> findAllProducts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.search(stringFilter);
        }
    }

    public long countProducts() {
        return count();
    }

    public void deleteProduct(Product product) {
        delete(product.getId());
    }

    public void saveProduct(Product product) {
        if (product == null) {
            System.err.println("Product is null. Are you sure you have connected your form to the application?");
            return;
        }
        save(product);
    }

    public boolean isOutOfStock(Integer id) {
        return repository.findProductWithStockEqualZero(id) != null;
    }


}
