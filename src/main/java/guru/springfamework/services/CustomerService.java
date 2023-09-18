package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import java.util.List;

public interface CustomerService {

    public List<CustomerDTO> getAllCustomers();
    public CustomerDTO getCustomerById(Long id);
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO);
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);


}
