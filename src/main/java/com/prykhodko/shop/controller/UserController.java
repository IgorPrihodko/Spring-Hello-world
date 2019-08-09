package com.prykhodko.shop.controller;

import com.prykhodko.shop.model.Basket;
import com.prykhodko.shop.model.User;
import com.prykhodko.shop.service.UserService;
import com.prykhodko.shop.utils.HashUtil;
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

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"user", "basket", "wrong"})
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signIn")
    public String signIn(@RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "password", required = false) String password,
                         ModelMap modelMap) {
        if (email.isEmpty() || password.isEmpty()) {
            modelMap.addAttribute("wrong", "Wrong email or password");
            return "redirect:/";
        }
        Optional<User> optionalUser = userService.getByEmail(email);
        if (optionalUser.isPresent()) {
            try {
                boolean isValid = HashUtil.validatePassword(password, optionalUser.get().getPassword());
                if (isValid) {
                    modelMap.addAttribute("user", optionalUser.get());
                    logger.info("Sign in user " + optionalUser.get());
                    if (optionalUser.get().getRole().equals("admin")) {
                        return "redirect:users";
                    } else {
                        Basket basket = new Basket(optionalUser.get());
                        modelMap.addAttribute("basket", basket);
                        return "redirect:account";
                    }
                } else {
                    modelMap.addAttribute("wrong", "Wrong email or password");
                    return "redirect:/";
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                logger.error("Can not validate password");
                modelMap.addAttribute("wrong", "Wrong email or password");
                return "redirect:/";
            }
        }
        modelMap.addAttribute("wrong", "Wrong email or password");
        return "redirect:/";
    }

    @GetMapping("/account")
    public String userAccount() {
        return "account";
    }

    @GetMapping("/users")
    public String allUsers(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/addUser")
    public String addForm() {
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam(value = "email", required = false) String email,
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "role", required = false) String role,
                          @RequestParam(value = "repeatPassword", required = false) String repeatPassword,
                          ModelMap modelMap) {
        if (email.isEmpty() || password.isEmpty() ||
                repeatPassword.isEmpty() || !repeatPassword.equals(password) || role == null) {
            modelMap.addAttribute("wrong", "Wrong email or password or role");
            return "redirect:addUser";
        } else if (userService.getByEmail(email).isPresent()) {
            modelMap.addAttribute("wrong", "Email already registered! Try another.");
            return "redirect:addUser";
        }

        try {
            String hashedPassword = HashUtil.generateHashPassword(password);
            User user = new User(email, hashedPassword, role);
            userService.addUser(user);
            logger.info("Add new user " + user + " to db");
            return "redirect:users";
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("Can not hashing password", e);
            modelMap.addAttribute("wrong", "Wrong password");
            return "redirect:addUser";
        }
    }

    @GetMapping("/editUser")
    public String editForm(@RequestParam("id") Long id, ModelMap modelMap) {
        User editedUser = userService.getById(id).get();
        modelMap.addAttribute("id", editedUser.getId());
        modelMap.addAttribute("email", editedUser.getEmail());
        return "editUser";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute(value = "id") Long id,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "role", required = false) String role,
                           @RequestParam(value = "repeatPassword", required = false) String repeatPassword,
                           ModelMap modelMap) {
        User user = userService.getById(id).get();
        if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || role == null) {
            modelMap.addAttribute("wrong", "Wrong! One or more fields are empty");
            return "redirect:editUser";
        } else if (!repeatPassword.equals(password) || userService.getByEmail(email).isPresent() &&
                !user.getEmail().equals(email)) {
            modelMap.addAttribute("wrong", "Email already registered! Try another.");
            return "redirect:editUser";
        }
        try {
            String hashPassword = HashUtil.generateHashPassword(password);
            user.setEmail(email);
            user.setPassword(hashPassword);
            user.setRole(role);
            userService.updateUser(id, user);
            logger.warn("Edit user data " + user + " in db");
            return "redirect:users";
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("Can not hashing password", e);
            modelMap.addAttribute("wrong", "Wrong password");
            return "redirect:editUser";
        }
    }

    @GetMapping("/deleteUser")
    public String deleteForm(@RequestParam("id") Long id, ModelMap modelMap) {
        User deletedUser = userService.getById(id).get();
        modelMap.addAttribute("id", deletedUser.getId());
        modelMap.addAttribute("email", deletedUser.getEmail());
        modelMap.addAttribute("password", deletedUser.getPassword());
        modelMap.addAttribute("role", deletedUser.getRole());
        return "deleteUser";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute(value = "id") Long id) {
        userService.removeUser(id);
        logger.warn("Remove user with id " + id);
        return "redirect:users";
    }
}
