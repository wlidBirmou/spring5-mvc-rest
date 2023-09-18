package guru.springfamework.repositories;

import guru.springfamework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by jt on 9/24/17.
 */
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    public Category findByName(String name);
}
