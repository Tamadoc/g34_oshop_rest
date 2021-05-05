package se.lexicon.oshop_rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import se.lexicon.oshop_rest.dto.CategoryDto;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {


    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        CategoryDto bookCategoryDto = new CategoryDto();
        bookCategoryDto.setName("Book");
        String json = objectMapper.writeValueAsString(bookCategoryDto);

        MvcResult mvcResult = mockMvc
                .perform(post("/api/category/")
                        .content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(content.contains("1"));
        Assertions.assertTrue(content.contains("Book"));

        //Convert JSON response to Java Object
        CategoryDto actual = objectMapper.readValue(content, new TypeReference<CategoryDto>() {
        });

        CategoryDto expected = new CategoryDto();
        expected.setId(1);
        expected.setName("Book");

        Assertions.assertEquals(expected, actual);
    }


    @DisplayName("Save BookCategory ")
    @Test
    public void test_save_json() throws Exception {
        mockMvc.perform(
                post("/api/category/")
                        .content("{\n" +
                                "    \"name\" : \"Sport\"\n" +
                                "}")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Sport")))
                //.andDo(print())
                .andReturn();
    }

    @DisplayName("Find BookCategory by Id 1")
    @Test
    public void test_find_by_id_1() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/category/1")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Book")))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        CategoryDto actual = objectMapper.readValue(content, new TypeReference<CategoryDto>() {
        });
        Assertions.assertEquals("Book", actual.getName());

    }


    @DisplayName("GET ALL - CHECK SIZE")
    @Test
    public void test_find_all_size_1() throws Exception {
        mockMvc.
                perform(
                        get("/api/category/")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Book")))
                //.andExpect(jsonPath("$[2].name",is("Book")))
                .andReturn();

    }


}
