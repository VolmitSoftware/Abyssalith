package volmbot.util;


import art.arcane.quill.random.RNG;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true, fluent = true )
public class Range {

    @Builder.Default
    private double min = 0;
    @Builder.Default
    private double max = 1;
    public static Range jitter(double center, double jitter){
        return new Range(center - (jitter/2), center + (jitter/2));
    }
    public double rand(){
        return RNG.r.d(min,max);
    }

    public boolean contains(double d) {
        return d>=min&&d<=max;
    }

}


