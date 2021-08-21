package com.volmit.data;

import com.volmit.io.DataType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public class User implements DataType {
    private long id = 0;
    private long experience = 0;
    private long money = 0;
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
