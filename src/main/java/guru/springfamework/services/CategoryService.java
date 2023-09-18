package guru.springfamework.services;

import guru.springfamework.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    public List<CategoryDTO> getAllCategories();
    public CategoryDTO getCategoryByName(String name);

}

