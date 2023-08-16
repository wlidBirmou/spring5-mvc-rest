package guru.springfamework.bootsrap;

import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

@AllArgsConstructor
@Slf4j
public class ApplicationBootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        Category fruits= Category.builder().name("Fruits").build();
        Category dried= Category.builder().name("Dried").build();
        Category fresh= Category.builder().name("Fresh").build();
        Category exotic= Category.builder().name("Exotic").build();
        Category nuts= Category.builder().name("Nuts").build();

        this.categoryRepository.save(fruits);
        this.categoryRepository.save(dried);
        this.categoryRepository.save(fresh);
        this.categoryRepository.save(exotic);
        this.categoryRepository.save(nuts);

        log.debug("Category Data loaded : "+ this.categoryRepository.count());
    }
}
