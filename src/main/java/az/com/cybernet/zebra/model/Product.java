/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.com.cybernet.zebra.model;

import com.sun.istack.internal.NotNull;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author SAMIR-PC
 */
@Entity(name = "Product")
@Table(name = "Product", schema = "app")
@NamedQuery(
        name = "findAll",
        query = "SELECT p FROM Product p"
)
public class Product implements Serializable {

    private static final long serialVersionUID = 11231239L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "PRODUCT_ID  ")
    private String productId;

    @Column(name = "PRODUCT_NAME")
    @NotNull
    private String name;
//    @Column(name = "PRODUCT_AMOUNT")
//    @NotNull
//    private String quantity;
    @Column(name = "CREATION_DATE")
    @NotNull
    private String date;

    @Column(name = "STATE", length = 1)
    @NotNull
    private String state;

    public Product() {
    }

    public Product(Long id, String productId, String name, String date, String state) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.date = date;
        this.state = state;
    }

  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "az.com.cybernet.zebra.model.Product[ id=" + id + " ]";
    }

}
