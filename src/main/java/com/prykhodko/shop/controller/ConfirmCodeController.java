package com.prykhodko.shop.controller;

import com.prykhodko.shop.model.Basket;
import com.prykhodko.shop.model.ConfirmationCode;
import com.prykhodko.shop.model.User;
import com.prykhodko.shop.service.ConfirmationCodeService;
import com.prykhodko.shop.service.MailService;
import com.prykhodko.shop.service.impl.MailServiceImpl;
import com.prykhodko.shop.utils.ConfirmCodeGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/user")
@SessionAttributes({"user", "basket"})
public class ConfirmCodeController {

    private static final Logger logger = Logger.getLogger(ConfirmCodeController.class);

    private final ConfirmationCodeService confirmationCodeService;

    @Autowired
    public ConfirmCodeController(ConfirmationCodeService confirmationCodeService) {
        this.confirmationCodeService = confirmationCodeService;
    }

    @PostMapping("/sendCode")
    public String sendCode(@ModelAttribute(value = "user") User user,
                           @ModelAttribute(value = "basket") Basket basket,
                           ModelMap modelMap) {
        ConfirmationCode confirmationCode = new ConfirmationCode(user);
        String code = ConfirmCodeGenerator.generateCode();
        confirmationCode.setCode(code);
        MailService mailService = new MailServiceImpl();
        mailService.sendConfirmCode(confirmationCode);
        basket.setConfirmationCode(confirmationCode);
        modelMap.addAttribute("basket", basket);
        return "redirect:order";
    }
}
