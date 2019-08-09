package com.prykhodko.shop.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "baskets")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User user;

    @Column(name = "totalPrice")
    private BigDecimal totalPrice;

    @OneToOne(fetch = FetchType.EAGER,
            targetEntity = ConfirmationCode.class,
            cascade = CascadeType.MERGE)
    private ConfirmationCode confirmationCode;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "products_in_busket",
            joinColumns = {@JoinColumn(name = "basket_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
            )
    private List<Product> productsInBasket = new ArrayList<>();

    public Basket() {
    }

    public Basket(User user) {
        this.user = user;
    }

    public Basket(User user, BigDecimal totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
    }

    public Basket(User user, BigDecimal totalPrice, ConfirmationCode confirmationCode) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.confirmationCode = confirmationCode;
    }

    public void addProductToBasket(Product product) {
        productsInBasket.add(product);
    }

    public void deleteProductFromBasket(Product product) {
        productsInBasket.remove(product);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProductsInBasket() {
        return productsInBasket;
    }

    public void setProductsInBasket(List<Product> productsInBasket) {
        this.productsInBasket = productsInBasket;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ConfirmationCode getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(ConfirmationCode confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Basket basket = (Basket) o;

        if (id != null ? !id.equals(basket.id) : basket.id != null) return false;
        if (user != null ? !user.equals(basket.user) : basket.user != null) return false;
        if (totalPrice != null ? !totalPrice.equals(basket.totalPrice) : basket.totalPrice != null)
            return false;
        if (confirmationCode != null ? !confirmationCode.equals(basket.confirmationCode)
                : basket.confirmationCode != null)
            return false;
        return productsInBasket != null ? productsInBasket.equals(basket.productsInBasket)
                : basket.productsInBasket == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (confirmationCode != null ? confirmationCode.hashCode() : 0);
        result = 31 * result + (productsInBasket != null ? productsInBasket.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", user=" + user +
                ", totalPrice=" + totalPrice +
                ", confirmationCode=" + confirmationCode +
                ", productsInBasket=" + productsInBasket +
                '}';
    }
}
