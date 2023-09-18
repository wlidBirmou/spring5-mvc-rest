package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.services.CategoryServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImpTest {

    @Mock
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper=CategoryMapper.INSTANCE;
    private CategoryServiceImp categoryService;

    private final String NAME="Abderrahim";
    private final Long ID=1l;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.categoryService=new CategoryServiceImp(this.categoryRepository,this.categoryMapper);
    }

    @Test
    void getAllCategories() {
        List<Category> categories=new ArrayList<>();
        categories.add(Category.builder().id(1l).name("random1").build());
        categories.add(Category.builder().id(2l).name("random2").build());

        when(this.categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> categoryDTOS=this.categoryService.getAllCategories();
        assertEquals(categories.size(),categoryDTOS.size());

        verify(this.categoryRepository,times(1)).findAll();
    }

    @Test
    void getAllCategoriesReturnEmpty() {
        List<Category> categories=new ArrayList<>();

        when(this.categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> categoryDTOS=this.categoryService.getAllCategories();
        assertNotNull(categoryDTOS);
        assertEquals(0,categoryDTOS.size());

        verify(this.categoryRepository,times(1)).findAll();
    }

    @Test
    void getCategoryByName() {
        Category category=Category.builder().id(1l).name(this.NAME).build();

        when(this.categoryRepository.findByName(anyString())).thenReturn(category);

        CategoryDTO categoryDTO=this.categoryService.getCategoryByName(this.NAME);
        assertEquals(this.NAME,  categoryDTO.getName());
        assertEquals(this.ID,  categoryDTO.getId());

        verify(this.categoryRepository,times(1)).findByName(anyString());
    }
}