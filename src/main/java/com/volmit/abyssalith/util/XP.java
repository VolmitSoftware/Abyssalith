package com.volmit.abyssalith.util;

import com.volmit.abyssalith.toolbox.Toolkit;

public class XP {

    public static double getExponent()
    {
        // Higher values take exponentially more xp per level increase.
        // Lower values make more consistent leveling. I.e. lvl 1 > 2 takes a little less than lvl 34 > 35
        return Toolkit.get().XpBaseMultiplier;
    }
    public static double getXpForLevel(double level)
    {
        return Math.pow(level, getExponent());
    }
    public static int getLevelForXp(double xp)
    {
        return (int)Math.floor(Math.pow(xp, 1D / getExponent()));
    }
}
