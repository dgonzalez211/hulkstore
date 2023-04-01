package com.diegodev.hulkstore.data.service;

import com.diegodev.hulkstore.data.entity.shop.ShoppingCart;
import com.diegodev.hulkstore.data.service.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShoppingCartService extends CrudBaseService {

    private ShoppingCartRepository repository;

    public ShoppingCartService(ShoppingCartRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<ShoppingCart> findAllShoppingCarts() {
        return repository.findAll();
    }

    public long countShoppingCarts() {
        return repository.count();
    }

    public void deleteShoppingCart(ShoppingCart ShoppingCart) {
        repository.delete(ShoppingCart);
    }

    public void saveShoppingCart(ShoppingCart ShoppingCart) {
        if (ShoppingCart == null) {
            System.err.println("ShoppingCart is null. Are you sure you have connected your form to the application?");
            return;
        }
        repository.save(ShoppingCart);
    }
}
