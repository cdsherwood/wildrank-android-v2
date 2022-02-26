package org.wildstang.wildrank.androidv2.models;

import java.util.HashMap;
import java.util.Map;

public class CycleModel {

    public static final String CS_HATCH_SCORE_KEY= "cs_hatch_score";
    public static final String CS_CARGO_SCORE_KEY= "cs_cargo_score";
    public static final String RO_HATCH_SCORE_L1_KEY= "ro_hatch_l1_score";
    public static final String RO_HATCH_SCORE_L2_KEY= "ro_hatch_l2_score";
    public static final String RO_HATCH_SCORE_L3_KEY= "ro_hatch_l3_score";
    public static final String RO_CARGO_SCORE_L1_KEY= "ro_cargo_l1_score";
    public static final String RO_CARGO_SCORE_L2_KEY= "ro_cargo_l2_score";
    public static final String RO_CARGO_SCORE_L3_KEY= "ro_cargo_l3_score";
    public static final String GAMEPIECE_NOT_SCORED_KEY = "not_scored";
    public static final String GAMEPIECE_1_DROP_KEY = "gamepiece_1_dropped";
    public static final String GAMEPIECE_2_DROP_KEY = "gamepiece_2_dropped";

    public int dropCount1;
    public int dropCount2;
    public boolean notScored;
    public boolean isCSHatch;
    public boolean isCSCargo;
    public boolean isROHatchL1;
    public boolean isROHatchL2;
    public boolean isROHatchL3;
    public boolean isROCargoL1;
    public boolean isROCargoL2;
    public boolean isROCargoL3;

    public CycleModel() {
        // Initialize everything to zero/false, you can set default values here (i.e. preexistingToteCount = 1;)
        dropCount1 = 0;
        dropCount2 = 0;
        notScored = false;
        isCSHatch = false;
        isCSCargo = false;
        isROHatchL1 = false;
        isROHatchL2 = false;
        isROHatchL3 = false;
        isROCargoL1 = false;
        isROCargoL2 = false;
        isROCargoL3 = false;
    }

    public static CycleModel fromMap(Map<String, Object> map) {
        CycleModel cycle = new CycleModel();
        cycle.dropCount1 = (Integer) map.get(GAMEPIECE_1_DROP_KEY);
        cycle.dropCount2 = (Integer) map.get(GAMEPIECE_2_DROP_KEY);
        cycle.notScored = (Boolean) map.get(GAMEPIECE_NOT_SCORED_KEY);
        cycle.isCSHatch = (Boolean) map.get(CS_HATCH_SCORE_KEY);
        cycle.isCSCargo = (Boolean) map.get(CS_CARGO_SCORE_KEY);
        cycle.isROHatchL1 = (Boolean) map.get(RO_HATCH_SCORE_L1_KEY);
        cycle.isROHatchL2 = (Boolean) map.get(RO_HATCH_SCORE_L2_KEY);
        cycle.isROHatchL3 = (Boolean) map.get(RO_HATCH_SCORE_L3_KEY);
        cycle.isROCargoL1 = (Boolean) map.get(RO_CARGO_SCORE_L1_KEY);
        cycle.isROCargoL2 = (Boolean) map.get(RO_CARGO_SCORE_L2_KEY);
        cycle.isROCargoL3 = (Boolean) map.get(RO_CARGO_SCORE_L3_KEY);
        if (map.containsKey(GAMEPIECE_NOT_SCORED_KEY)) {
            cycle.notScored = (Boolean) map.get(GAMEPIECE_NOT_SCORED_KEY);
        } else {
            cycle.notScored = false;
        }
        return cycle;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(CycleModel.GAMEPIECE_1_DROP_KEY, this.dropCount1);
        map.put(CycleModel.GAMEPIECE_2_DROP_KEY, this.dropCount2);
        map.put(CycleModel.CS_HATCH_SCORE_KEY, this.isCSHatch);
        map.put(CycleModel.CS_CARGO_SCORE_KEY, this.isCSCargo);
        map.put(CycleModel.RO_HATCH_SCORE_L1_KEY, this.isROHatchL1);
        map.put(CycleModel.RO_HATCH_SCORE_L2_KEY, this.isROHatchL2);
        map.put(CycleModel.RO_HATCH_SCORE_L3_KEY, this.isROHatchL3);
        map.put(CycleModel.RO_CARGO_SCORE_L1_KEY, this.isROCargoL1);
        map.put(CycleModel.RO_CARGO_SCORE_L2_KEY, this.isROCargoL2);
        map.put(CycleModel.RO_CARGO_SCORE_L3_KEY, this.isROCargoL3);
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
        equals &= (comparing.dropCount1 == this.dropCount1);
        equals &= (comparing.dropCount2 == this.dropCount2);
        equals &= (comparing.isCSHatch == this.isCSHatch);
        equals &= (comparing.isCSCargo == this.isCSCargo);
        equals &= (comparing.isROHatchL1 == this.isROHatchL1);
        equals &= (comparing.isROHatchL2 == this.isROHatchL2);
        equals &= (comparing.isROHatchL3 == this.isROHatchL3);
        equals &= (comparing.isROCargoL1 == this.isROCargoL1);
        equals &= (comparing.isROCargoL2 == this.isROCargoL2);
        equals &= (comparing.isROCargoL3 == this.isROCargoL3);
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
        if (isCSHatch == true || isCSCargo == true || isROHatchL1 == true ||
                isROHatchL2 == true || isROHatchL3 == true || isROCargoL1 == true ||
                isROCargoL2 == true ||isROCargoL3 == true) {
            return true;
        }
        return false;
    }
}
