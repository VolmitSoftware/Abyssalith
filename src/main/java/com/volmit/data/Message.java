package com.volmit.data;

import lombok.Data;
import lombok.experimental.Accessors;
import com.volmit.io.DataType;

@Data
@Accessors(chain = true, fluent = true)
public class Message implements DataType {
    private long id = 0;

}
