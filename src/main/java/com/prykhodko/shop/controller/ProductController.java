package com.prykhodko.shop.controller;

import com.prykhodko.shop.model.Product;
import com.prykhodko.shop.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String allProducts(ModelMap modelMap) {
        modelMap.addAttribute("products", productService.getAll());
        return "products";
    }

    @GetMapping("/addProduct")
    public String addForm() {
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addUser(@RequestParam(value = "title", required = false) String title,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "price", required = false) BigDecimal price,
                          ModelMap modelMap) {
        if (title.isEmpty() || description.isEmpty() || price.equals(BigDecimal.ZERO)) {
            modelMap.addAttribute("wrong", "Wrong data entered");
            return "redirect:addProduct";
        }

        Product product = new Product(title, description, price);
        productService.addProduct(product);
        logger.warn("Add new product " + product + " to db");
        return "redirect:products";
    }

    @GetMapping("/editProduct")
    public String editForm(@RequestParam("id") Long id, ModelMap modelMap) {
        Product editedProduct = productService.getById(id).get();
        modelMap.addAttribute("id", editedProduct.getId());
        modelMap.addAttribute("title", editedProduct.getTitle());
        modelMap.addAttribute("description", editedProduct.getDescription());
        modelMap.addAttribute("price", editedProduct.getPrice());
        return "editProduct";
    }

    @PostMapping("/editProduct")
    public String editProduct(@ModelAttribute(value = "id") Long id,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "description", required = false) String description,
                              @RequestParam(value = "price", required = false) BigDecimal price,
                              ModelMap modelMap) {
        Product product = productService.getById(id).get();
        if (title.isEmpty() || description.isEmpty() || price.equals(BigDecimal.ZERO)) {
            modelMap.addAttribute("wrong", "Wrong data entered");
            return "redirect:editProduct";
        }
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        productService.updateProduct(id, product);
        logger.warn("Edit product " + product + " in db");
        return "redirect:products";
    }

    @GetMapping("/deleteProduct")
    public String deleteForm(@RequestParam("id") Long id, ModelMap modelMap) {
        Product deletedProduct = productService.getById(id).get();
        modelMap.addAttribute("id", deletedProduct.getId());
        modelMap.addAttribute("title", deletedProduct.getTitle());
        modelMap.addAttribute("description", deletedProduct.getDescription());
        modelMap.addAttribute("price", deletedProduct.getPrice());
        return "deleteProduct";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@ModelAttribute(value = "id") Long id) {
        productService.removeProduct(id);
        logger.warn("Remove product with id " + id);
        return "redirect:products";
    }
}
