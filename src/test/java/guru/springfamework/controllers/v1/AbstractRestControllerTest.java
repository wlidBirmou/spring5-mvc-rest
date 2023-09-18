package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class
AbstractRestControllerTest {

    public String asJsonString(final Object o){
        try{
            return new ObjectMapper().writeValueAsString(o);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
