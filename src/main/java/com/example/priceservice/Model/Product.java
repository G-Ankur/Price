package com.example.priceservice.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private Integer productId;

    @Column(name = "currenttime")
    private Date currentTime;

    @Column(name = "responsetime")
    private long responseTime;

    @Column(name = "userid")
    private String userId;

    @Column(name = "productcode")
    private String productCode;

    @Column(name = "price")
    private Integer price;

    @Column(name = "source")
    private String source;

}
