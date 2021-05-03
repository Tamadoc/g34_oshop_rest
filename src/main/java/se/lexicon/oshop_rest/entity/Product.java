package se.lexicon.oshop_rest.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false,unique = true,length = 100)
    private String name;
    @Column(nullable = false)
    private int price;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ProductDetails productDetails;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Category category;

}
