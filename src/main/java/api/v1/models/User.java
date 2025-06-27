package api.v1.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class User {
    @NonNull
    @NotBlank (message = "Поле должно быть заполнено")
    private String login;
    @NonNull
    @NotBlank (message = "Поле должно быть заполнено")
    private String password;
    @NonNull
    @NotBlank (message = "Поле должно быть заполнено")
    private String email;
    private String date;

}
