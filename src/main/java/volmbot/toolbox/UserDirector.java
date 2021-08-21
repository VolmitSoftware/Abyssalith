package volmbot.toolbox;

import art.arcane.quill.collections.KMap;
import art.arcane.quill.execution.J;
import art.arcane.quill.io.IO;
import com.google.gson.Gson;
import lombok.Data;

import java.io.File;
import java.io.IOException;

@Data
public class UserDirector {
    private static final KMap<Long, UserDirector> kash = new KMap<>();


    // Add dans shitty variables here, all will be added to json perfectly // accessed
    private String SKey1 = "";
    private String Money = "0";
    private long id;
    private Boolean onCd = false;


    /*
    User s = User.load(id);
    //FAST Save
    J.a(s::save);
    */


    private UserDirector(long id) {
        this.id = id;
    }


    public void save() {
        File f = new File("data/Users/" + id + ".json");
        f.getParentFile().mkdirs();

        //Try-catch, but better ;)
        J.attempt(() -> IO.writeAll(f, new Gson().toJson(this)));

        //Never used: Hunk<String> dna = Hunk.newMappedHunk(5, 5, 5);


    }

    public static UserDirector load(long id) {

        return kash.compute(id, (k, v) -> {
            if (v == null) {

                File f = new File("data/Users/" + id + ".json");
                f.getParentFile().mkdirs();
                UserDirector u = new UserDirector(id);

                if (!f.exists()) {
                    try {
                        IO.writeAll(f, new Gson().toJson(u));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    return new Gson().fromJson(IO.readAll(f), UserDirector.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return u;

            }
            return v;
        });


    }

    static {
        //:(:[
        Runtime.getRuntime().addShutdownHook(new Thread(() -> kash.v().forEach(UserDirector::save)));
    }

}

