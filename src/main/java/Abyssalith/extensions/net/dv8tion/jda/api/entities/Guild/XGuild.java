package Abyssalith.extensions.net.dv8tion.jda.api.entities.Guild;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.dv8tion.jda.api.entities.Guild;

@Extension
public class XGuild {
    public static boolean hasRole(@This Guild self, String role) {
        return self.getRolesByName(role, false).size() == 1 && self.getRolesByName(role, false).contains(self.getRolesByName(role, true).get(0));
    }
}