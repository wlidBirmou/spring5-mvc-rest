package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImpTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    Environment environment;
    private CustomerMapper customerMapper=CustomerMapper.INSTANCE;
    private CustomerServiceImp customerService;

    private final String FIRST_NAME ="Abderrahim";
    private final Long ID=1l;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.customerService=new CustomerServiceImp(this.customerRepository,this.customerMapper,environment);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers=new ArrayList<>();
        customers.add(Customer.builder().id(1l).firstName("Abderrahim").lastName("LAAKAB").build());
        customers.add(Customer.builder().id(2l).firstName("Said").lastName("MOUHOUNE").build());

        when(this.customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS=this.customerService.getAllCustomers();
        assertEquals(customers.size(),customerDTOS.size());

        verify(this.customerRepository,times(1)).findAll();
    }

    @Test
    void getAllCustomersReturnEmpty() {
        List<Customer> customers=new ArrayList<>();

        when(this.customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS=this.customerService.getAllCustomers();
        assertNotNull(customerDTOS);
        assertEquals(0,customerDTOS.size());

        verify(this.customerRepository,times(1)).findAll();
    }

    @Test
    void getCustomerById() {
        Customer customer=Customer.builder().id(this.ID).firstName(this.FIRST_NAME).lastName("LAAKAB").build();

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO=this.customerService.getCustomerById(this.ID);
        assertEquals(this.FIRST_NAME,  customerDTO.getFirstName());
        assertEquals(this.ID,  customerDTO.getId());
        verify(this.customerRepository,times(1)).findById(anyLong());
    }

    @Test
    void createNewCustomer() {
        CustomerDTO customerDTO=CustomerDTO.builder().id(1l).firstName("abderrahim").lastName("laakab").build();


        when(this.customerRepository.save(any(Customer.class))).thenReturn(this.customerMapper.toCustomer(customerDTO));
        when(environment.getProperty(anyString())).thenReturn("api/v1/customers");

        CustomerDTO returnedDTO=this.customerService.createNewCustomer(customerDTO);

        assertEquals(customerDTO.getFirstName(),returnedDTO.getFirstName());
        assertEquals(customerDTO.getLastName(),returnedDTO.getLastName());
        assertNotNull(returnedDTO.getCustomerUrl());

        verify(this.customerRepository,times(1)).save(any());
    }

    @Test
    void UpdateExistingCustomer(){

        Customer customer=Customer.builder().id(1l).firstName("Abderrahim").lastName("LAAKAB").build();
        CustomerDTO customerDTO=this.customerMapper.toCustomerDTO(customer);

        when(this.customerRepository.findById(any())).thenReturn(Optional.of(customer));
        when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(environment.getProperty(anyString())).thenReturn("api/v1/customers");

        CustomerDTO returnedDTO= this.customerService.updateCustomer(customerDTO.getId(),customerDTO);

        assertNotNull(returnedDTO.getCustomerUrl());
        assertEquals(customerDTO.getId(),returnedDTO.getId());
        assertEquals(customerDTO.getFirstName(),returnedDTO.getFirstName());
        assertEquals(customerDTO.getLastName(),returnedDTO.getLastName());

        verify(this.customerRepository,times(1)).findById(anyLong());
        verify(this.customerRepository,times(1)).save(any(Customer.class));

    }

    @Test
    void UpdateNonExistingCustomer(){

        Customer customer=Customer.builder().id(1l).firstName("Abderrahim").lastName("LAAKAB").build();
        CustomerDTO customerDTO=this.customerMapper.toCustomerDTO(customer);

        when(this.customerRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        CustomerDTO returnedDTO= this.customerService.updateCustomer(customerDTO.getId(),customerDTO);

        assertNull(returnedDTO);

        verify(this.customerRepository,times(1)).findById(anyLong());
        verify(this.customerRepository,times(0)).save(any(Customer.class));

    }

    @Test
    void patchExistingCustomer(){
        final String FIRST_NAME="Said";
        Customer customer=Customer.builder().id(1l).firstName("Abderrahim").lastName("LAAKAB").build();
        CustomerDTO customerDTOInput=CustomerDTO.builder().firstName(FIRST_NAME).build();
        Customer resultCustomer=Customer.builder().id(1l).firstName(FIRST_NAME).lastName("LAAKAB").build();

        when(this.environment.getProperty(anyString())).thenReturn("api/v1/customers/1");
        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(this.customerRepository.save(any())).thenReturn(customer);

        CustomerDTO patchedCustomerDTO=this.customerService.patchCustomer(1l,customerDTOInput);
        assertNotNull(patchedCustomerDTO.getCustomerUrl());
        assertEquals(FIRST_NAME,patchedCustomerDTO.getFirstName());
        assertEquals(customer.getId(),patchedCustomerDTO.getId());

        verify(this.customerRepository,times(1)).findById(anyLong());
        verify(this.customerRepository,times(1)).save(any());
    }

    @Test
    void patchNonExistingCustomer(){
        final String FIRST_NAME="Said";
        Customer customer=Customer.builder().id(1l).firstName("Abderrahim").lastName("LAAKAB").build();
        CustomerDTO customerDTOInput=CustomerDTO.builder().firstName(FIRST_NAME).build();

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        CustomerDTO patchedCustomerDTO=this.customerService.patchCustomer(1l,customerDTOInput);
        assertNull(patchedCustomerDTO);

        verify(this.customerRepository,times(1)).findById(anyLong());
        verify(this.customerRepository,times(0)).save(any());
    }
}