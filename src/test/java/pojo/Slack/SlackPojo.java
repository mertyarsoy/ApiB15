package pojo.Slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackPojo {
    private boolean ok;
    private String channel;
    private String ts;
    private SlackMessagePojo message;

}
