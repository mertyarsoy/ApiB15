package pojo.Pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonResultPojo {

    private String url;
    private String name;


}
