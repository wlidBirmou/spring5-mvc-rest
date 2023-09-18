package guru.springfamework.api.v1.model;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class CustomerDTO {


    private Long id;
    @NotBlank
    @Size(min = 1,max = 100)
    private String firstName;
    @NotBlank
    @Size(min = 1,max = 100)
    private String lastName;
    private String customerUrl;
}
