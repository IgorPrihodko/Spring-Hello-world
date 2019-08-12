package com.prykhodko.shop.controller;

import com.prykhodko.shop.model.Basket;
import com.prykhodko.shop.model.ConfirmationCode;
import com.prykhodko.shop.model.StockOnOrder;
import com.prykhodko.shop.model.User;
import com.prykhodko.shop.service.BasketService;
import com.prykhodko.shop.service.ConfirmationCodeService;
import com.prykhodko.shop.service.OrderService;
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

import java.math.BigDecimal;

@Controller
@RequestMapping("/user")
@SessionAttributes({"user", "basket"})
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class);

    private final OrderService orderService;
    private final ConfirmationCodeService confirmationCodeService;
    private final BasketService basketService;

    @Autowired
    public OrderController(OrderService orderService,
                           ConfirmationCodeService confirmationCodeService,
                           BasketService basketService) {
        this.orderService = orderService;
        this.confirmationCodeService = confirmationCodeService;
        this.basketService = basketService;
    }

    @GetMapping("/order")
    public String getOrder(@ModelAttribute(value = "user") User user,
                           @ModelAttribute(value = "basket") Basket basket,
                           @ModelAttribute(value = "wrong") String wrong,
                           ModelMap modelMap) {
        if (basket == null || basket.getProductsInBasket().isEmpty()) {
            modelMap.addAttribute("totalPrice", BigDecimal.ZERO);
            modelMap.addAttribute("wrong", "Your basket is empty! Nothing to buy.");
            return "basket";
        }
        modelMap.addAttribute("userID", user.getId());
        modelMap.addAttribute("userEmail", user.getEmail());
        modelMap.addAttribute("totalPrice", basket.getTotalPrice());
        return  "add_order";
    }

    @PostMapping("/order")
    public String makeOrder(@ModelAttribute(value = "userID") Long userID,
                            @ModelAttribute(value = "userEmail") String userEmail,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname,
                            @RequestParam(value = "address", required = false) String address,
                            @RequestParam(value = "confirmationCode", required = false) String code,
                            ModelMap modelMap) {
        if (name.isEmpty() || surname.isEmpty() || address.isEmpty() || code.isEmpty()) {
            modelMap.addAttribute("wrong", "Wrong! Empty fields");
            return "redirect:order";
        }
        User userFromSession = (User) modelMap.get("user");
        Basket basketFromSession = (Basket) modelMap.get("basket");
        ConfirmationCode confirmationCode = basketFromSession.getConfirmationCode();
        if (confirmationCode == null || !code.equals(confirmationCode.getCode())) {
            modelMap.addAttribute("wrong", "Wrong confirmation code! Try another");
            return "redirect:order";
        } else {
            confirmationCodeService.addConfirmationCode(confirmationCode);
            basketService.addBasket(basketFromSession);
            StockOnOrder order = new StockOnOrder();
            order.setUser(userFromSession);
            order.setUserEmail(userEmail);
            order.setName(name);
            order.setSurname(surname);
            order.setAddress(address);
            order.setBasket(basketFromSession);
            orderService.addOrder(order);

            modelMap.addAttribute("success", "Successfull purchase!");
            return "redirect:/admin/account";
        }
    }
}
