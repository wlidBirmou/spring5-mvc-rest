package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@Controller
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
@Profile("v1_api")
@Slf4j
public class CustomerController  {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerListDTO> getCustomers(){
        CustomerListDTO customerListDTO=new CustomerListDTO(this.customerService.getAllCustomers());
        return new ResponseEntity<>(customerListDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id){
        CustomerDTO customerDTO=this.customerService.getCustomerById(id);
        if(customerDTO!=null)  return new ResponseEntity<>(customerDTO,HttpStatus.OK);
         else return new ResponseEntity<>(customerDTO,HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){

        CustomerDTO createdCustomer=customerService.createNewCustomer(customerDTO);
        return new ResponseEntity<>(createdCustomer,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id,@RequestBody CustomerDTO customerDTO){

        CustomerDTO updatedCustomer=customerService.updateCustomer(id,customerDTO);
        if(updatedCustomer!=null)  return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
        else return new ResponseEntity<>(customerDTO,HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable long id, @RequestBody CustomerDTO customerDTO){
        log.debug("inside patch method");
        CustomerDTO resultDTO=this.customerService.patchCustomer(id,customerDTO);
        if(resultDTO==null) return new ResponseEntity<>(customerDTO,HttpStatus.NO_CONTENT);
        else return  new ResponseEntity<>(resultDTO,HttpStatus.OK);
    }

}
