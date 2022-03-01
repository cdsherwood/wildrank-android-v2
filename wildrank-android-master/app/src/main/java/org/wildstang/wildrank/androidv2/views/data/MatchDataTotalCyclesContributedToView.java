package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.MathObservable;

public class MatchDataTotalCyclesContributedToView extends MatchDataView implements IMatchDataView {

    public MatchDataTotalCyclesContributedToView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        int totalCycles = 0;
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }

        Observable cyclesObservable = Observable.from(documents)
                .map(doc -> (Map<String, Object>) doc.getProperty("data"))
                .map(data -> (List<Map<String, Object>>) data.get("stacks"))
                .map(cycles -> cycles.size());

        MathObservable.sumInteger(cyclesObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sum -> setValueText("" + sum, "gray"), error -> Log.d("wildrank", this.getClass().getName()));

    }
}
