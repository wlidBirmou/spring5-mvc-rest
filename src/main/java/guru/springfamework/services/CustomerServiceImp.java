package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerServiceImp implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final Environment environment;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerDTOS=customerMapper.toCustomersDTO(this.customerRepository.findAll());
        customerDTOS.forEach(c -> c.setCustomerUrl(this.environment.getProperty("customer_root_path")+"/"+c.getId()));
        return customerDTOS;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        CustomerDTO customerDTO=customerMapper.toCustomerDTO(this.customerRepository.findById(id).orElse(null));
        if(customerDTO!=null) customerDTO.setCustomerUrl(this.environment.getProperty("customer_root_path")+"/"+customerDTO.getId());
        return customerDTO;
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer=this.customerMapper.toCustomer(customerDTO);
        Customer savedCustomer=this.customerRepository.save(customer);
        CustomerDTO returnedDTO=this.customerMapper.toCustomerDTO(savedCustomer);
        returnedDTO.setCustomerUrl(this.environment.getProperty("customer_root_path"));
        return returnedDTO;
    }

    @Override
    public CustomerDTO updateCustomer(Long id,CustomerDTO customerDTO) {
        customerDTO.setId(id);
        if(this.getCustomerById(customerDTO.getId())==null) return null;
        else  {
            Customer returnedCustomer=this.customerRepository.save(this.customerMapper.toCustomer(customerDTO));
            CustomerDTO returnDTO= this.customerMapper.toCustomerDTO(returnedCustomer);
            returnDTO.setCustomerUrl(this.environment.getProperty("customer_root_path"));
            return returnDTO;
        }

    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return this.customerRepository.findById(id).map(c -> {
                if (customerDTO.getFirstName() != null) c.setFirstName(customerDTO.getFirstName());
                if (customerDTO.getLastName() != null) c.setLastName(customerDTO.getLastName());
                CustomerDTO returnDTO=this.customerMapper.toCustomerDTO(this.customerRepository.save(c));
                returnDTO.setCustomerUrl(this.environment.getProperty("customer_root_path"));
                return returnDTO;
        }).orElse(null);

    }

    @Override
    public boolean deleteCustomer(Long id) {
        if(this.customerRepository.existsById(id)) {
            this.customerRepository.deleteById(id);
            return true;
        } else return false;
    }
}
