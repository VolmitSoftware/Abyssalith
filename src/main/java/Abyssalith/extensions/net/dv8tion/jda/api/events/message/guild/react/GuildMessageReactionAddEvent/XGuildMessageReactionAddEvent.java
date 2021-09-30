package Abyssalith.extensions.net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

@Extension
public class XGuildMessageReactionAddEvent {
    public static Message getMessage(@This GuildMessageReactionAddEvent self) {
      return self.getChannel().retrieveMessageById(self.getMessageId()).complete();
    }
  }

