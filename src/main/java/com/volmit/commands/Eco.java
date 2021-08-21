package com.volmit.commands;

import com.volmit.commands.eco.Get;
import com.volmit.commands.eco.Give;
import com.volmit.commands.eco.Remove;
import com.volmit.commands.eco.Set;
import com.volmit.util.VolmitCommand;
import volmbot.commands.eco.*;

public class Eco extends VolmitCommand {
    // Constructor
    public Eco() {
        super(
                "eco",
                new String[]{},
                new String[]{}, // Add role name here. Empty: always / 1+: at least one.
                "Economy Category",
                true,
                "Eco <subcommand>",
                new VolmitCommand[]{
                        new Give(),
                        new Set(),
                        new Remove(),
                        new Get(),
                }
        );
    }
}