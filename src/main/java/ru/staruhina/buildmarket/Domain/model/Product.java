package ru.staruhina.buildmarket.Domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    /*
     * Описание фильма
     */
    @Column(name = "description")
    private String description;

    /*
     * Цена
     */

    @Column(name = "price")
    private double price;

    /*
     * Картинка товара
     */
    @Column(name = "image")
    private String image;

    // OneToMany
    //    @Column(name = "genre")
    //    private String genre;
}