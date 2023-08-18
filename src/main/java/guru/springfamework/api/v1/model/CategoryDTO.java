package guru.springfamework.api.v1.model;

import lombok.*;

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
    private String name;
}
