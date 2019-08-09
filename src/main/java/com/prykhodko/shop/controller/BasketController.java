package com.prykhodko.shop.controller;

import com.prykhodko.shop.model.Basket;
import com.prykhodko.shop.model.Product;
import com.prykhodko.shop.service.ProductService;
import com.prykhodko.shop.utils.TotalPriceCounter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/user")
@SessionAttributes({"user", "basket"})
public class BasketController {

    private static final Logger logger = Logger.getLogger(BasketController.class);

    private final ProductService productService;

    @Autowired
    public BasketController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String allProducts(ModelMap modelMap) {
        modelMap.addAttribute("products", productService.getAll());
        return "products_for_users";
    }

    @GetMapping("/basket")
    public String getBasket(@ModelAttribute(value = "basket") Basket basket,
                            ModelMap modelMap) {
        modelMap.addAttribute("basket", basket);
        modelMap.addAttribute("totalPrice", basket.getTotalPrice());
        return "basket";
    }

    @PostMapping("/addToBasket")
    public String addToBasket(@ModelAttribute(value = "basket") Basket basket,
                              @RequestParam("id") Long id,
                              ModelMap modelMap) {
        Product product = productService.getById(id).get();
        basket.addProductToBasket(product);
        basket.setTotalPrice(TotalPriceCounter.count(basket));
        modelMap.addAttribute("basket", basket);
        modelMap.addAttribute("totalPrice", basket.getTotalPrice());
        return "redirect:products";
    }

    @PostMapping("/deleteFromBasket")
    public String deleteFromBasket(@ModelAttribute(value = "basket") Basket basket,
                                   @RequestParam("id") Long id,
                                   ModelMap modelMap) {
        Product product = productService.getById(id).get();
        basket.deleteProductFromBasket(product);
        basket.setTotalPrice(TotalPriceCounter.count(basket));
        modelMap.addAttribute("basket", basket);
        modelMap.addAttribute("totalPrice", basket.getTotalPrice());
        return "redirect:basket";
    }
}
