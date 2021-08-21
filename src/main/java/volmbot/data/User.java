package volmbot.data;

import lombok.Data;
import lombok.experimental.Accessors;
import volmbot.io.DataType;

@Data
@Accessors(chain = true, fluent = true)
public class User implements DataType {
    private long id = 0;
    private double experience = 0;
    private double money = 0.00;

    //private UserStatistics stats = new UserStatistics();
    private long messagesSent = 0;
    private long reactions = 0;


//    @Data
//    @Accessors(chain = true, fluent = true)
//    static class UserStatistics
//    {
//        private long messagesSent = 0;
//        private long voiceMinutes = 0;
//        private long reactions = 0;
//    }
}
