package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);


    public CategoryDTO toCategoryDTO(Category category);
    public Category toCategory(CategoryDTO categoryDTO);
    public List<CategoryDTO> toCategoriesDTO(Iterable<Category> categories);
    public List<Category> toCategories(Iterable<CategoryDTO> categoriesDtos);
}
