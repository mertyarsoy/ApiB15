package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarWarsPojo {

    private int count;
    private String next;
    private String previous;
    private List<Map<String, Object>> maps;
    private List<StarWarsResultPojo> results;


}
