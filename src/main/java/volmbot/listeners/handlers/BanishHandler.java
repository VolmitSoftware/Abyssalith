package volmbot.listeners.handlers;

import net.dv8tion.jda.api.entities.Member;
import volmbot.toolbox.Toolkit;

public class BanishHandler {

    public static boolean bok(Member m){
        return Toolkit.get().BanishedInsteadOfKick;
    }
}
