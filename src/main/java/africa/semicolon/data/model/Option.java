package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Option {
    @Id
    private String optionId;
    private String optionContent;
}
