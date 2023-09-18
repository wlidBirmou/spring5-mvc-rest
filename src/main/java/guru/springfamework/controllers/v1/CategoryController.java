package guru.springfamework.controllers.v1;


import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
@Profile("v1_api")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryListDTO> getCategories(){
        CategoryListDTO categoryListDTO=new CategoryListDTO(this.categoryService.getAllCategories());
        return new ResponseEntity<CategoryListDTO>(categoryListDTO, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable("name") String name){
        CategoryDTO categoryDTO=this.categoryService.getCategoryByName(name);
       if(categoryDTO!=null) return new ResponseEntity<>(categoryDTO,HttpStatus.OK);
       else return new ResponseEntity<>(categoryDTO,HttpStatus.NO_CONTENT);
    }


}
