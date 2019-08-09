package com.prykhodko.shop.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stockOnOrders")
public class StockOnOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private User user;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "address")
    private String address;

    @JoinColumn(name = "basket")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Basket basket;

    public StockOnOrder() {
    }

    public StockOnOrder(String userEmail) {
        this.userEmail = userEmail;
    }

    public StockOnOrder(User user, String userEmail, String name,
                        String surname, String address, Basket basket) {
        this.user = user;
        this.userEmail = userEmail;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.basket = basket;
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

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockOnOrder stockOnOrder = (StockOnOrder) o;

        if (id != null ? !id.equals(stockOnOrder.id) : stockOnOrder.id != null) return false;
        if (user != null ? !user.equals(stockOnOrder.user) : stockOnOrder.user != null) return false;
        if (userEmail != null ? !userEmail.equals(stockOnOrder.userEmail) : stockOnOrder.userEmail != null)
            return false;
        if (name != null ? !name.equals(stockOnOrder.name) : stockOnOrder.name != null) return false;
        if (surname != null ? !surname.equals(stockOnOrder.surname) : stockOnOrder.surname != null) return false;
        if (address != null ? !address.equals(stockOnOrder.address) : stockOnOrder.address != null) return false;
        return basket != null ? basket.equals(stockOnOrder.basket) : stockOnOrder.basket == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (basket != null ? basket.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StockOnOrder{" +
                "id=" + id +
                ", user=" + user +
                ", userEmail='" + userEmail + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", basket=" + basket +
                '}';
    }
}
