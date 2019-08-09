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
@SessionAttributes({"user", "basket", "totalPrice", "wrong"})
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
        return "redirect:saveOrder";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(@ModelAttribute(value = "user") User user,
                            @ModelAttribute(value = "basket") Basket basket,
                            @ModelAttribute(value = "order") StockOnOrder stockOnOrder,
                            @ModelAttribute(value = "name") String name,
                            @ModelAttribute(value = "surname") String surname,
                            @ModelAttribute(value = "address") String address,
                            @ModelAttribute(value = "code") String code,
                            ModelMap modelMap) {
        ConfirmationCode confirmationCode = basket.getConfirmationCode();
        if (confirmationCode == null || !code.equals(confirmationCode.getCode())) {
            modelMap.addAttribute("wrong", "Wrong confirmation code! Try another");
            return "redirect:order";
        } else {
            confirmationCode.getUser().setEmail(user.getEmail());
            confirmationCodeService.addConfirmationCode(confirmationCode);
            ConfirmationCode codeFromDB =
                    confirmationCodeService.getLastConfirmationCodeForUser(user).get();

            basket.setConfirmationCode(codeFromDB);
            basketService.addBasket(basket);
            Basket basketFromDB = basketService.getLastBasketForUser(user).get();

            stockOnOrder.setName(name);
            stockOnOrder.setSurname(surname);
            stockOnOrder.setAddress(address);
            stockOnOrder.setBasket(basketFromDB);
            orderService.addOrder(stockOnOrder);

            modelMap.addAttribute("success", "Successfull purchase!");
            modelMap.addAttribute("basket", new Basket());
            return "redirect:account";
        }
    }
}
