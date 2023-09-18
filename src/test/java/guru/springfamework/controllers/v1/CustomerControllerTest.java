package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
@SpringBootApplication
class CustomerControllerTest extends AbstractRestControllerTest{

    @Mock
    private CustomerService customerService;
    private MockMvc mockMvc;
    @InjectMocks
    private CustomerController customerController;
    @BeforeEach
    void setUp() {
        this.mockMvc= MockMvcBuilders.standaloneSetup(this.customerController).build();
    }

    @Test
    void getCustomers() throws Exception {
        List<CustomerDTO> customerDTOS=new ArrayList<>();
        customerDTOS.add(CustomerDTO.builder().id(1L).firstName("Abderrahim").lastName("LAAKAB").build());
        customerDTOS.add(CustomerDTO.builder().id(2L).firstName("Said").lastName("MOUHOUNE").build());
        when(this.customerService.getAllCustomers()).thenReturn(customerDTOS);
        this.mockMvc.perform(get("/api/v1/customers"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers",hasSize(2)));
        verify(this.customerService,times(1)).getAllCustomers();
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO= CustomerDTO.builder().id(1L).lastName("LAAKAB").build();
        when(this.customerService.getCustomerById(anyLong())).thenReturn(customerDTO);
        this.mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("LAAKAB")));
        verify(this.customerService,times(1)).getCustomerById(anyLong());
    }

    @Test
    void createNewCustomer() throws Exception{
        CustomerDTO customerDTO= CustomerDTO.builder().id(1l).firstName("Abderrahim").lastName("LAAKAB").build();
        when(this.customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);
        this.mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO) ))
                .andExpect(jsonPath("$.lastName",is("LAAKAB")))
                .andExpect(jsonPath("$.firstName",is("Abderrahim")));
        verify(this.customerService,times(1)).createNewCustomer(any(CustomerDTO.class));
    }

    @Test
    void updateExistingCustomer() throws Exception{
        CustomerDTO customerDTO= CustomerDTO.builder().id(1l).firstName("Abderrahim").lastName("LAAKAB").build();
        when(this.customerService.updateCustomer(anyLong(),any(CustomerDTO.class))).thenReturn(customerDTO);
        this.mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO) ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.lastName",is("LAAKAB")))
                .andExpect(jsonPath("$.firstName",is("Abderrahim")));
        verify(this.customerService,times(1)).updateCustomer(anyLong(),any(CustomerDTO.class));
    }

    @Test
    void updateNonExistingCustomer() throws Exception{
        CustomerDTO customerDTO= CustomerDTO.builder().id(1l).firstName("Abderrahim").lastName("LAAKAB").build();
        when(this.customerService.updateCustomer(anyLong(),any(CustomerDTO.class))).thenReturn(null);
        this.mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO) ))
                .andExpect(status().isNoContent());
        verify(this.customerService,times(1)).updateCustomer(anyLong(),any(CustomerDTO.class));
    }

    @Test
    void patchExistingCustomer() throws Exception{
        final String FIRST_NAME="Said";
        final String LAST_NAME="MOUHOUNE";
        CustomerDTO returnCustomerDTO= CustomerDTO.builder().id(1l).firstName(FIRST_NAME).lastName(LAST_NAME).build();
        CustomerDTO customerDTO= CustomerDTO.builder().firstName(FIRST_NAME).build();
        when(this.customerService.patchCustomer(anyLong(),any(CustomerDTO.class))).thenReturn(returnCustomerDTO);
        this.mockMvc.perform(patch("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO) ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.lastName",is(LAST_NAME)))
                .andExpect(jsonPath("$.firstName",is(FIRST_NAME)));

        verify(this.customerService,times(1)).patchCustomer(anyLong(),any(CustomerDTO.class));
    }

    @Test
    void patchNonExistingCustomer() throws Exception{
        final String FIRST_NAME="Said";
        CustomerDTO customerDTO= CustomerDTO.builder().firstName(FIRST_NAME).build();
        when(this.customerService.patchCustomer(anyLong(),any(CustomerDTO.class))).thenReturn(null);
        this.mockMvc.perform(patch("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO) ))
                .andExpect(status().isNoContent());
        verify(this.customerService,times(1)).patchCustomer(anyLong(),any(CustomerDTO.class));
    }


    @Test
    void deletingExistingCustomer() throws Exception{

        when(this.customerService.deleteCustomer(anyLong())).thenReturn(true);
        this.mockMvc.perform(delete("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.customerService,times(1)).deleteCustomer(anyLong());
    }

    @Test
    void deletingNonExistingCustomer() throws Exception{
        when(this.customerService.deleteCustomer(anyLong())).thenReturn(false);
        this.mockMvc.perform(delete("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(this.customerService,times(1)).deleteCustomer(anyLong());
    }
}