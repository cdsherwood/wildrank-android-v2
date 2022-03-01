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
import rx.schedulers.Schedulers;

public class MatchDataAverageCyclesContributedToView extends MatchDataView implements IMatchDataView {
// Geometric mean of cycles contributed to
    public MatchDataAverageCyclesContributedToView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }

        Observable<Double> cyclesObservable = Observable.from(documents)
                .map(doc -> (Map<String, Object>) doc.getProperty("data"))
                .map(data -> {
                    List<Map<String, Object>> cycles = (List<Map<String, Object>>) data.get("stacks");
                    return (double) cycles.size();
                }).subscribeOn(Schedulers.computation());
        MathObservable.averageDouble(cyclesObservable)
                .map(average -> formatNumberAsString(average))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(average -> setValueText(average, "gray"), error -> Log.d("wildrank", this.getClass().getName()));
    }
}
