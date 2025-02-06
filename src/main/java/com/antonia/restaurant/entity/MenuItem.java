package com.antonia.restaurant.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

/** Clasa pentru a reprezenta un element de meniu din restaurant.
 * @author Andrei Antonia-Stefania
 * @version 3 Ianuarie 2025
 */


@Entity
@Table(name = "menu_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Menu item name cannot be blank")
    private String name;

    @Column(length = 2000)
    private String description;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be a positive number")
    private Double price;

    private String category;

    @Column(length = 2000)
    private String ingredients;

    private String imageUrl;
}
