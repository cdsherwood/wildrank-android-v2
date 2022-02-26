package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;
import org.wildstang.wildrank.androidv2.models.CycleModel;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.MathObservable;
import rx.schedulers.Schedulers;

public class MatchDataMaxGamePieceDropView extends MatchDataView implements IMatchDataView {

    public MatchDataMaxGamePieceDropView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }

        Observable dropsObservable = Observable.from(documents)
                .map(doc -> (Map<String, Object>) doc.getProperty("data"))
                .flatMap(data -> Observable.from((List<Map<String, Object>>) data.get("stacks")))
                .map(stack -> {
                    int dropCount = (int) stack.get(CycleModel.GAMEPIECE_1_DROP_KEY);
                    return dropCount;
                }).defaultIfEmpty(0).subscribeOn(Schedulers.computation());

        MathObservable.averageDouble(dropsObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(max -> setValueText("h" + max, "gray"), error -> Log.d("wildrank", this.getClass().getName()));
    }
}
