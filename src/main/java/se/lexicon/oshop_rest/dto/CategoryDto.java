package se.lexicon.oshop_rest.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CategoryDto {
    private int id;
    @NotEmpty(message = "Category Name cannot be empty")
    @Size(min = 2, max = 20, message = "Category Name should be in 2-20 chars range")
    private String name;
}
