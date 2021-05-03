package se.lexicon.oshop_rest.dto;

import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String name;
    private int price;
    private ProductDetailsDto productDetails;
    private CategoryDto category;

}
