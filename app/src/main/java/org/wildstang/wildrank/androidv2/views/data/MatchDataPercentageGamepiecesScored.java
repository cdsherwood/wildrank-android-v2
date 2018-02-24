package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.models.CycleModel;

import java.util.List;
import java.util.Map;

public class MatchDataPercentageGamepiecesScored extends MatchDataView implements IMatchDataView {

    public MatchDataPercentageGamepiecesScored(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }

        double totalScored = 0;
        double notScoreds = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null) {
                return;
            }
            List<Map<String, Object>> cycles = (List<Map<String, Object>>) data.get("stacks");
            for (Map<String, Object> cycle : cycles) {
                totalScored++; // if gamepiece is not abandoned we assume it to be scored (not the right way to do it)
                boolean notScored = (boolean) cycle.get(CycleModel.GAMEPIECE_NOT_SCORED_KEY);
                if (notScored) {
                    notScoreds++;
                }
            }
        }
        if (totalScored == 0) {
            setValueText("N/A");
        } else {
            double percentage = (notScoreds / totalScored);
            setValueText(formatPercentageAsString(percentage));
        }
    }
}
