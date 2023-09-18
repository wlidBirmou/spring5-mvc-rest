package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    public CustomerDTO toCustomerDTO(Customer customer);
    public Customer toCustomer(CustomerDTO customerDTO);
    public List<CustomerDTO> toCustomersDTO(Iterable<Customer> customers);
    public List<Customer> toCustomers(Iterable<CustomerDTO> customerDTOS);
}
