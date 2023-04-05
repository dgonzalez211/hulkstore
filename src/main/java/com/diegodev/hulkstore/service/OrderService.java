package com.diegodev.hulkstore.service;

import com.diegodev.hulkstore.dto.cart.CartDto;
import com.diegodev.hulkstore.dto.cart.CartItemDto;
import com.diegodev.hulkstore.exceptions.OrderNotFoundException;
import com.diegodev.hulkstore.model.Order;
import com.diegodev.hulkstore.model.OrderItem;
import com.diegodev.hulkstore.model.User;
import com.diegodev.hulkstore.repository.OrderItemsRepository;
import com.diegodev.hulkstore.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrderService extends CrudBaseService<OrderRepository, Order> {

    private final OrderRepository repository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    OrderItemsRepository orderItemsRepository;

    public OrderService(OrderRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public void placeOrder(User user) {
        CartDto cartDto = cartService.listCartItems(user);

        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDto.getTotalCost());
        save(newOrder);

        for (CartItemDto cartItemDto : cartItemDtoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartItemDto.getProduct().getPrice());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);
            productService.updateProductStock(cartItemDto.getProduct(), cartItemDto.getQuantity());
            orderItemsRepository.save(orderItem);
        }

        cartService.deleteUserCartItems(user);
    }

    public List<Order> listOrders(User user) {
        return repository.findAllByUserOrderByCreatedDateDesc(user);
    }


    public Order getOrder(Integer orderId) throws OrderNotFoundException {
        Optional<Order> order = get(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderNotFoundException("Order not found");
    }

    public List<Order> findAllOrders() {
        return repository.findAll();
    }
}


