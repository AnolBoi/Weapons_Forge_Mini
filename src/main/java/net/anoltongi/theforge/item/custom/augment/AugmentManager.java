package net.anoltongi.theforge.item.custom.augment;

import java.util.HashMap;
import java.util.Map;

public class AugmentManager {
    public static final String SCOURGE_UNDEAD = "scourge_undead";
    public static final String SCARABS_BANE = "scarabs_bane";
    public static final String MATERIALIST = "materialist";
    public static final String ABLAZE = "ablaze";

    // Let's store some data about each augment
    private static Map<String, AugmentInfo> AUGMENTS = new HashMap<>();
    static {
        AUGMENTS.put(SCOURGE_UNDEAD, new AugmentInfo("Scourge of the Undead", 6,
                "Increases damage vs undead."));
        AUGMENTS.put(SCARABS_BANE, new AugmentInfo("Scarab’s Bane", 6,
                "Increases damage vs arthropods."));
        AUGMENTS.put(MATERIALIST, new AugmentInfo("Materialist", 3,
                "Increases looting."));
        AUGMENTS.put(ABLAZE, new AugmentInfo("Ablaze", 3,
                "Ignites attacked mobs"));
    }

    public static AugmentInfo getAugmentInfo(String id) {
        return AUGMENTS.getOrDefault(id, null);
    }

    // Format e.g., "Scourge of the Undead III"
    public static String formatAugment(String id, int level) {
        AugmentInfo info = getAugmentInfo(id);
        if (info == null) return id + " " + level;
        return info.name + " " + toRoman(level);
    }

    private static String toRoman(int level) {
        // Quick helper for I, II, III, IV, V, VI etc. (or just do level + "")
        switch(level) {
            case 1: return "I";
            case 2: return "II";
            case 3: return "III";
            case 4: return "IV";
            case 5: return "V";
            case 6: return "VI";
            default: return "?";
        }
    }

    public static class AugmentInfo {
        public final String name;
        public final int maxLevel;
        public final String desc;
        public AugmentInfo(String name, int maxLevel, String desc) {
            this.name = name;
            this.maxLevel = maxLevel;
            this.desc = desc;
        }
    }
}

