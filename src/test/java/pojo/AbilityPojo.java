package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbilityPojo {

    private Map<String , Object> map;
    private List<AbilityResultPojo> abilites;
    private boolean is_hidden;
    private int slot;


}
