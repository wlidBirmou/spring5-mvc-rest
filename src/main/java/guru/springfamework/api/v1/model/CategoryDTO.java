package guru.springfamework.api.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * Created by jt on 9/24/17.
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class CategoryDTO {



    private Long id;
    @NotBlank
    private String name;
    private String categoryUrl;

}
