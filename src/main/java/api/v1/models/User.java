package api.v1.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class User {
    @NonNull
    @NotBlank
    private String login;
    @NonNull
    @NotBlank
    private String password;
    private String date;

}
