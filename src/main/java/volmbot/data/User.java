package volmbot.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import volmbot.io.DataType;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class User implements DataType {
    private long id = 0;
    private double experience = 0.0f;
    private double money = 0.00f;

    //private UserStatistics stats = new UserStatistics();
    private long messagesSent = 0;
    private long reactions = 0;
    private Set<String> roleIds = new HashSet<>();

}
