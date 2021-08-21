package volmbot.data;

import lombok.Data;
import lombok.experimental.Accessors;
import volmbot.io.DataType;

@Data
@Accessors(chain = true, fluent = true)
public class Message implements DataType {
    private long id = 0;

}