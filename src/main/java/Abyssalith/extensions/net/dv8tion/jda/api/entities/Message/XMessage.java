package Abyssalith.extensions.net.dv8tion.jda.api.entities.Message;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.dv8tion.jda.api.entities.Message;
import static art.arcane.amulet.MagicalSugar.*;

@Extension
public class XMessage {
  public static String lower(@This Message self) {
    return self.getContentRaw()lc;
  }
}