package com.example.springbootthuatdev.api;

import com.example.springbootthuatdev.entity.CartItem;
import com.example.springbootthuatdev.entity.Product;
import com.example.springbootthuatdev.repository.CartItemRepository;
import com.example.springbootthuatdev.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/cartitems")
@CrossOrigin(value = "*")
public class CartItemApi {
    @Autowired
    private CartItemRepository cartItemRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<CartItem> findAll(){
        return cartItemRepository.findAll();
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public CartItem findById(@PathVariable String id) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(String.valueOf(id));
        if(optionalCartItem.isPresent()){
            return optionalCartItem.get();
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public CartItem create(@RequestBody CartItem cartItem){
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public boolean deleteById(@PathVariable String id){
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(String.valueOf(id));
        if(optionalCartItem.isPresent()){
            CartItem cartItem = optionalCartItem.get();
            cartItemRepository.delete(cartItem);
        }
        return false;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public CartItem updateById(@RequestBody CartItem updateCartItem, @PathVariable String id){
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(String.valueOf(id));
        if(optionalCartItem.isPresent()){
            CartItem existingCartItem = optionalCartItem.get();
            existingCartItem.setName(updateCartItem.getName());
            existingCartItem.setThumbnails(updateCartItem.getThumbnails());
            existingCartItem.setQuantity(updateCartItem.getQuantity());
            existingCartItem.setPrice(updateCartItem.getPrice());
            cartItemRepository.save(existingCartItem);
        }
        return null;
    }
}
