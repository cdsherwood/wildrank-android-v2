package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.models.CycleModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchDataGeomStatsRocketElements extends MatchDataView implements IMatchDataView {

    public MatchDataGeomStatsRocketElements(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public double calculateNthRoot(Double base, Double n) {
        return Math.pow(Math.E, Math.log(base)/n);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        boolean didSomething = false;                   // catch teams that did nothing -> present a "N/A"
        double geometricPiCargo = 1;                    // set to one for multiplication in geometric mean
        double geometricPiHatches = 1;                  // set to one for multiplication in geometric mean
        List<Integer> geoPiValues = new ArrayList<>();
        double geometricVariance = 0;
        int meaningfulCycleSets = 0;    // meaningful games containing n cycles
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null) {
                return;
            }
            int cargoScored = 0;
            int hatchScored = 0;
            List<Map<String, Object>> cycles = (List<Map<String, Object>>) data.get("cycles");
            for (Map<String, Object> cycle : cycles) {      // throwing null pointer exception
                if ((boolean) cycle.get(CycleModel.RO_CARGO_SCORE_L1_KEY) | (boolean) cycle.get(CycleModel.RO_CARGO_SCORE_L2_KEY) |
                        (boolean) cycle.get(CycleModel.RO_CARGO_SCORE_L3_KEY)) {
                    cargoScored++; // if gamepiece meets a scored condition add to tally
                    didSomething = true;
                }
            }

            for (Map<String, Object> cycle : cycles) {      // throwing null pointer exception
                if ((boolean) cycle.get(CycleModel.RO_HATCH_SCORE_L1_KEY) | (boolean) cycle.get(CycleModel.RO_HATCH_SCORE_L2_KEY) |
                        (boolean) cycle.get(CycleModel.RO_HATCH_SCORE_L3_KEY)) {
                    hatchScored++; // if gamepiece meets a scored condition add to tally
                    didSomething = true;
                }
            }

            if (cargoScored > 0) {
                meaningfulCycleSets++;
                geoPiValues.add(cargoScored);
                geometricPiCargo *= cargoScored;
            }

            if (hatchScored > 0) {
                meaningfulCycleSets++;
                geoPiValues.add(hatchScored);
                geometricPiHatches *= hatchScored;
            }
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double geometricMeanCargo = calculateNthRoot(geometricPiCargo, (double) meaningfulCycleSets);
            double geometricMeanHatches = calculateNthRoot(geometricPiHatches, (double) meaningfulCycleSets);
            Log.d("wildrank_geoMean_RO_Car", formatNumberAsString(geometricMeanCargo));
            Log.d("wildrank_geoMean_RO_Hat", formatNumberAsString(geometricMeanCargo));
            setValueText(formatNumberAsString(geometricMeanHatches) + "  /  " +
                    formatNumberAsString(geometricMeanCargo), "gray");
        }
    }
}
