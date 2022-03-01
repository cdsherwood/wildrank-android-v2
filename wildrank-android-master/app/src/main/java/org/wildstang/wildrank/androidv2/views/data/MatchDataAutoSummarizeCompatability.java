package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;

import java.util.List;
import java.util.Map;


public class MatchDataAutoSummarizeCompatability extends MatchDataView implements IMatchDataView {

    public MatchDataAutoSummarizeCompatability(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        int sumAutoCompat = 0;
        //int instances = 0;
        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null) {
                return;
            }
            if ((boolean)data.get("auto-drive")&& ! (boolean)data.get("auto-robot_contact") &&
                    ((int) data.get("auto-score_cargo_high") > 0 | (int) data.get("auto-score_cargo_low") > 0)) {
                sumAutoCompat ++;
                didSomething = true;

            }
        }
        //Log.d("wildrank_autocompat", formatNumberAsString(sumAutoCompat) +" " + formatNumberAsString(instances));
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            double percentage = ((double)sumAutoCompat / (double)documents.size());
            setValueText(formatPercentageAsString(percentage), "gray");
        }
    }
}
