package guru.springfamework.repositories;

import guru.springfamework.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long> {
    public Customer findByFirstName(String name);

}
