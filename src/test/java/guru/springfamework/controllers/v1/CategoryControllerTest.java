package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@SpringBootApplication
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private Environment environment;

    private MockMvc mockMvc;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        this.mockMvc= MockMvcBuilders.standaloneSetup(this.categoryController).build();
    }

    @Test
    void getCategories() throws Exception {
        List<CategoryDTO> categoryDTOS=new ArrayList<>();
        categoryDTOS.add(CategoryDTO.builder().id(1L).name("random1").build());
        categoryDTOS.add(CategoryDTO.builder().id(2L).name("random2").build());
        when(this.categoryService.getAllCategories()).thenReturn(categoryDTOS);

        this.mockMvc.perform(get("/api/v1/categories"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories",hasSize(2)));

        verify(this.categoryService,times(1)).getAllCategories();
    }

    @Test
    void getCategoryByName() throws Exception {

        CategoryDTO categoryDTO= CategoryDTO.builder().id(1L).name("random1").build();

        when(this.categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

        this.mockMvc.perform(get("/api/v1/categories/random1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("random1")));
        verify(this.categoryService,times(1)).getCategoryByName(anyString());
    }

}