package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    public CategoryDTO toCategoryDTO(Category category);
    public Category toCategory(CategoryDTO categoryDTO);
}
