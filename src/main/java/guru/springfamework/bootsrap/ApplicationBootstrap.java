package guru.springfamework.bootsrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class ApplicationBootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        log.debug("Category Data loaded : "+ this.categoryRepository.count());
    }

    private void loadCustomers() {
        Customer customer1=Customer.builder().firstName("Abderrahim").lastName("LAAKAB").build();
        Customer customer2=Customer.builder().firstName("Said").lastName("Mouhoune").build();
        Customer customer3=Customer.builder().firstName("Moncef").lastName("Bedjaoui").build();
        Customer customer4=Customer.builder().firstName("Djamel").lastName("Drifel").build();

        this.customerRepository.save(customer1);
        this.customerRepository.save(customer2);
        this.customerRepository.save(customer3);
        this.customerRepository.save(customer4);
    }

    private void loadCategories() {
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
    }
}
