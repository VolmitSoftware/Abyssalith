package volmbot.data;

import lombok.Data;
import lombok.experimental.Accessors;
import volmbot.io.DataType;

@Data
@Accessors(chain = true, fluent = true)
public class User implements DataType {
    private long id = 0;
    private long experience = 0;
    private long points = 0;
    private UserStatistics stats = new UserStatistics();

    @Data
    @Accessors(chain = true, fluent = true)
    static class UserStatistics
    {
        private long messagesSent = 0;
        private long voiceMinutes = 0;
        private long reactions = 0;
    }
}
