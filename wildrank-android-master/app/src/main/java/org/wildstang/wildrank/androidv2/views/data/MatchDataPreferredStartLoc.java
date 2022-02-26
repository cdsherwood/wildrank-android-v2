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
import java.util.HashMap;


public class MatchDataPreferredStartLoc extends MatchDataView implements IMatchDataView {

    public MatchDataPreferredStartLoc(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static String mostCommonElement(List<String> list) {

        Map<String, Integer> map = new HashMap<String, Integer>();

        for(int i=0; i< list.size(); i++) {

            Integer frequency = map.get(list.get(i));
            if(frequency == null) {
                map.put(list.get(i), 1);
            } else {
                map.put(list.get(i), frequency+1);
            }
        }

        String mostCommonKey = null;
        int maxValue = -1;
        for(Map.Entry<String, Integer> entry: map.entrySet()) {

            if(entry.getValue() > maxValue) {
                mostCommonKey = entry.getKey();
                maxValue = entry.getValue();
            }
        }

        return mostCommonKey;
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }
        boolean didSomething = false;               // catch teams that did nothing -> present a "N/A"
        List<String> startLocs = new ArrayList<>();

        for (Document document : documents) {
            Map<String, Object> data = (Map<String, Object>) document.getProperty("data");
            if (data == null) {
                return;
            }
            String startingLocation = (String) data.get("auto-startPos");
            startLocs.add(startingLocation);
            didSomething = true;
        }
        if (!didSomething) {
            setValueText("N/A", "gray");
        } else {
            setValueText(mostCommonElement(startLocs), "gray");
        }
    }
}
