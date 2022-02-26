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

public class MatchDataGeomStatsGameElementsL3 extends MatchDataView implements IMatchDataView {

    public MatchDataGeomStatsGameElementsL3(Context context, AttributeSet attrs) {
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
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        double geometricPi = 1;                    // set to one for multiplication in geometric mean
        List<Integer> geoPiValues = new ArrayList<>();
        double geometricVariance = 0;
        int meaningfulCycleSets = 0;    // meaningful games containing n cycles
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null) {
                return;
            }
            int totalScored = 0;
            List<Map<String, Object>> cycles = (List<Map<String, Object>>) data.get("cycles");
            for (Map<String, Object> cycle : cycles) {      // throwing null pointer exception
                if ((boolean) cycle.get(CycleModel.RO_CARGO_SCORE_L3_KEY) | (boolean) cycle.get(CycleModel.RO_HATCH_SCORE_L3_KEY)) {
                    totalScored++; // if gamepiece meets a scored condition add to tally
                    didSomething = true;
                }
            }
            if (totalScored > 0) {
                meaningfulCycleSets++;
                geoPiValues.add(totalScored);
                geometricPi *= totalScored;
            }
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double geometricMean = calculateNthRoot(geometricPi,(double) meaningfulCycleSets);
            Log.d("wildrank_geoMean", formatNumberAsString(geometricMean));
            for(double val : geoPiValues) {
                double iVarianceSquared= Math.pow((Math.log((val/geometricMean))),2);
                geometricVariance += iVarianceSquared;
            }
            double geometricStdDev = Math.sqrt((geometricVariance/meaningfulCycleSets));
            double geometricCoeffVar = Math.sqrt((Math.exp(geometricVariance)-1));
            Log.d("wildrank_geoStdDev", formatNumberAsString(geometricStdDev));
            Log.d("wildrank_geoVariance", formatNumberAsString(geometricVariance));
            Log.d("wildrank_geoCoeffOfDev", formatNumberAsString(geometricCoeffVar));
            setValueText(formatNumberAsString(geometricMean)+ "   " +
                    formatNumberAsString(geometricStdDev) + "   "+
                    formatPercentageAsString(geometricCoeffVar), "black");
        }
    }
}
