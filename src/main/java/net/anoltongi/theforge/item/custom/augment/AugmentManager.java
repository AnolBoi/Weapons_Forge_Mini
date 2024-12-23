package net.anoltongi.theforge.item.custom.augment;

import java.util.HashMap;
import java.util.Map;

public class AugmentManager {
    public static final String SCOURGE_UNDEAD = "scourge_undead";
    public static final String SCARABS_BANE = "scarabs_bane";
    public static final String MATERIALIST = "materialist";
    public static final String ABLAZE = "ablaze";
    public static final String BLESSING_OF_FAES = "blessing_of_faes";
    public static final String EVERLASTING = "everlasting";

    public static Map<String, Integer> AUGMENT_MAX_LEVELS = new HashMap<>();
    static {
        AUGMENT_MAX_LEVELS.put("scourge_undead", 6);
        AUGMENT_MAX_LEVELS.put("scarabs_bane", 6);
        AUGMENT_MAX_LEVELS.put("materialist", 3);
        AUGMENT_MAX_LEVELS.put("ablaze", 3);
        AUGMENT_MAX_LEVELS.put("blessing_of_faes", 5);
        AUGMENT_MAX_LEVELS.put("everlasting", 7);
    }
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
        AUGMENTS.put(BLESSING_OF_FAES, new AugmentInfo("Blessing of Faes", 7,
                "Chance to spawn pixies which help the wielder attack."));
        AUGMENTS.put(EVERLASTING, new AugmentInfo("Everlasting", 7,
                "Increases the durability of the affected weapon."));
    }

    public static AugmentInfo getAugmentInfo(String id) {
        return AUGMENTS.getOrDefault(id, null);
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

    // Format e.g., "Scourge of the Undead III"
    public static String formatAugment(String id, int level) {
        AugmentInfo info = getAugmentInfo(id);
        if (info == null) return id + " " + level;
        return info.name + " " + toRoman(level);
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

