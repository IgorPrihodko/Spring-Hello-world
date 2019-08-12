package com.prykhodko.shop.controller;

import com.prykhodko.shop.model.Product;
import com.prykhodko.shop.model.User;
import com.prykhodko.shop.service.ProductService;
import com.prykhodko.shop.service.UserService;
import com.prykhodko.shop.utils.HashUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class StartPageController {

    private static final Logger logger = Logger.getLogger(StartPageController.class);

    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public StartPageController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        if (userService.getAll().isEmpty()) {
            try {
                User firstUserTest = new User("test@test",
                        HashUtil.generateHashPassword("test"), "admin");
                userService.addUser(firstUserTest);
                logger.info("Add initial user with ADMIN role " + firstUserTest + " to DB");

                User secondUserTest = new User("igorprihodkoemail@gmail.com",
                        HashUtil.generateHashPassword("111"), "user");
                userService.addUser(secondUserTest);
                logger.info("Add initial user with USER role " + secondUserTest + " to DB");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                logger.error("Can not hashing password", e);
            }
        }
        if (productService.getAll().isEmpty()) {
            Product firstProductTest = new Product("firstProductTest", "test",
                    BigDecimal.valueOf(0.1));
            Product secondProductTest = new Product("secondProductTest", "test",
                    BigDecimal.valueOf(0.4));
            productService.addProduct(firstProductTest);
            logger.info("Add new product " + firstProductTest + " to DB");
            productService.addProduct(secondProductTest);
            logger.info("Add new product " + secondProductTest + " to DB");
        }
    }

    @GetMapping("/")
    public String initStartPage(@ModelAttribute(value = "wrong") String wrong,
                                @ModelAttribute(value = "success") String success,
                                ModelMap modelMap) {
        return "index";
    }
}
