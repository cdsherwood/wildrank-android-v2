package org.wildstang.wildrank.androidv2.models;

import java.util.HashMap;
import java.util.Map;

public class CycleModel {

    public static final String SCALE_SCORE_WEAKEN_KEY = "scale_weaken";
    public static final String SCALE_SCORE_DIFF_KEY = "scale_diff";
    public static final String SCALE_SCORE_STRENGHTEN_KEY = "scale_strengthen";
    public static final String SWITCH_SCORE_WEAKEN_KEY = "switch_weaken";
    public static final String SWITCH_SCORE_DIFF_KEY = "switch_diff";
    public static final String SWITCH_SCORE_STRENGTHEN_KEY = "switch_strengthen";
    public static final String EXCHANGE_SCORE_KEY = "exchange_score";
    public static final String GAMEPIECE_DROP_KEY = "gamepiece_dropped";
    public static final String GAMEPIECE_NOT_SCORED_KEY = "not_scored";

    public int dropCount;
    public boolean notScored;
    public boolean isScaleWeaken;
    public boolean isScaleDiff;
    public boolean isScaleStrengthen;
    public boolean isSwitchWeaken;
    public boolean isSwitchDiff;
    public boolean isSwitchStrengthen;
    public boolean isExchangeScore;

    public CycleModel() {
        // Initialize everything to zero/false, you can set default values here (i.e. preexistingToteCount = 1;)
        dropCount = 0;
        notScored = false;
        isScaleWeaken = false;
        isScaleDiff = false;
        isScaleStrengthen = false;
        isSwitchWeaken = false;
        isSwitchDiff = false;
        isSwitchStrengthen = false;
        isExchangeScore = false;
    }

    public static CycleModel fromMap(Map<String, Object> map) {
        CycleModel cycle = new CycleModel();
        cycle.dropCount = (Integer) map.get(GAMEPIECE_DROP_KEY);
        cycle.notScored = (Boolean) map.get(GAMEPIECE_NOT_SCORED_KEY);
        cycle.isScaleWeaken = (Boolean) map.get(SCALE_SCORE_WEAKEN_KEY);
        cycle.isScaleDiff = (Boolean) map.get(SCALE_SCORE_DIFF_KEY);
        cycle.isScaleStrengthen = (Boolean) map.get(SCALE_SCORE_STRENGHTEN_KEY);
        cycle.isSwitchWeaken = (Boolean) map.get(SWITCH_SCORE_WEAKEN_KEY);
        cycle.isSwitchDiff = (Boolean) map.get(SWITCH_SCORE_DIFF_KEY);
        cycle.isSwitchStrengthen = (Boolean) map.get(SWITCH_SCORE_STRENGTHEN_KEY);
        cycle.isExchangeScore = (Boolean) map.get(EXCHANGE_SCORE_KEY);
        if (map.containsKey(GAMEPIECE_NOT_SCORED_KEY)) {
            cycle.notScored = (Boolean) map.get(GAMEPIECE_NOT_SCORED_KEY);
        } else {
            cycle.notScored = false;
        }
        return cycle;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(CycleModel.GAMEPIECE_DROP_KEY, this.dropCount);
        map.put(CycleModel.SCALE_SCORE_WEAKEN_KEY, this.isScaleWeaken);
        map.put(CycleModel.SCALE_SCORE_DIFF_KEY, this.isScaleDiff);
        map.put(CycleModel.SCALE_SCORE_STRENGHTEN_KEY, this.isScaleStrengthen);
        map.put(CycleModel.SWITCH_SCORE_WEAKEN_KEY, this.isSwitchWeaken);
        map.put(CycleModel.SWITCH_SCORE_DIFF_KEY, this.isSwitchDiff);
        map.put(CycleModel.SWITCH_SCORE_STRENGTHEN_KEY, this.isSwitchStrengthen);
        map.put(CycleModel.EXCHANGE_SCORE_KEY, this.isExchangeScore);
        map.put(CycleModel.GAMEPIECE_NOT_SCORED_KEY, this.notScored);
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CycleModel)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        CycleModel comparing = (CycleModel) o;
        boolean equals = true;
        equals &= (comparing.dropCount == this.dropCount);
        equals &= (comparing.isScaleWeaken == this.isScaleWeaken);
        equals &= (comparing.isScaleDiff == this.isScaleDiff);
        equals &= (comparing.isScaleStrengthen == this.isScaleStrengthen);
        equals &= (comparing.isSwitchWeaken == this.isScaleWeaken);
        equals &= (comparing.isSwitchDiff == this.isScaleDiff);
        equals &= (comparing.isScaleStrengthen == this.isScaleStrengthen);
        equals &= (comparing.isExchangeScore == this.isExchangeScore);
        equals &= (comparing.notScored == this.notScored);
        return equals;
    }

    /**
     * A meaningful cycle is defined as one that indicates something actually happened.
     * <p>
     * We will define a meaningful cycle as one that has scored game pieces or robot actions
     * resulting in scoring.
     *
     * @return true if it is meaningful cycle, false if otherwise
     */
    public boolean isMeaningfulCycle() {
        if (isScaleWeaken == true || isScaleDiff == true || isScaleStrengthen == true ||
                isSwitchWeaken == true || isSwitchDiff == true || isSwitchStrengthen == true ||
                isExchangeScore == true) {
            return true;
        }
        return false;
    }
}
