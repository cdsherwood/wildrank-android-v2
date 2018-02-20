package org.wildstang.wildrank.androidv2.views.scouting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.models.CycleModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoutingCyclesView extends ScoutingView implements View.OnClickListener {

    private List<CycleModel> cycles = new ArrayList<>();

    private ScoutingCounterView gamepieceDroppedCounter;
    private ScoutingCheckboxView scaleWeakenCheckbox;
    private ScoutingCheckboxView scaleDiffCheckbox;
    private ScoutingCheckboxView scaleStrengthenCheckbox;
    private ScoutingCheckboxView switchWeakenCheckbox;
    private ScoutingCheckboxView switchDiffCheckbox;
    private ScoutingCheckboxView switchStrengthenCheckbox;
    private ScoutingCheckboxView ExchangeScoreCheckbox;
    private ScoutingCheckboxView notScoredCheckbox;

    public ScoutingCyclesView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.scouting_view_cycle, this, true);

        scaleWeakenCheckbox = (ScoutingCheckboxView) findViewById(R.id.scale_weaken);
        scaleDiffCheckbox = (ScoutingCheckboxView) findViewById(R.id.scale_diff);
        scaleStrengthenCheckbox = (ScoutingCheckboxView) findViewById(R.id.scale_strengthen);
        switchWeakenCheckbox = (ScoutingCheckboxView) findViewById(R.id.switch_weaken);
        switchDiffCheckbox = (ScoutingCheckboxView) findViewById(R.id.switch_diff);
        switchStrengthenCheckbox = (ScoutingCheckboxView) findViewById(R.id.switch_strengthen);
        switchStrengthenCheckbox = (ScoutingCheckboxView) findViewById(R.id.exchange_score);
        gamepieceDroppedCounter = (ScoutingCounterView) findViewById(R.id.gamepiece_dropped);
        notScoredCheckbox = (ScoutingCheckboxView) findViewById(R.id.not_scored);

        /* THIS IS IMPORTANT!
        preexistingStackCheckbox.setOnValueChangedListener(preexistingHeightSpinner::setEnabled);

        // Synchronize the state of the preexisting height spinner with the checkbox
        preexistingHeightSpinner.setEnabled(preexistingStackCheckbox.isChecked());
        */

        findViewById(R.id.finish_cycle).setOnClickListener(this);
    }

    @Override
    public void writeContentsToMap(Map<String, Object> map) {
        List<Map<String, Object>> mappedDataList = new ArrayList<>();
        for (CycleModel stack : cycles) {
            mappedDataList.add(stack.toMap());
        }
        map.put(key, mappedDataList);
    }

    @Override
    public void restoreFromMap(Map<String, Object> map) {
        List<Map<String, Object>> mappedDataList;
        try {
            mappedDataList = (List<Map<String, Object>>) map.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }

        if (mappedDataList != null) {
            cycles.clear();
            for (Map<String, Object> dataMap : mappedDataList) {
                cycles.add(CycleModel.fromMap(dataMap));
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.finish_cycle) {
            CycleModel data = new CycleModel();
            data.toteCount = totesCounter.getCount();
            data.hasBin = hasBinCheckbox.isChecked();
            data.hasNoodle = hasNoodleCheckbox.isChecked();
            data.binDropped = binDroppedCheckbox.isChecked();
            data.stackDropped = stackDroppedCheckbox.isChecked();
            data.isPreexisting = preexistingStackCheckbox.isChecked();
            data.notScored = notScoredCheckbox.isChecked();
            int preexistingHeight = Integer.parseInt(preexistingHeightSpinner.getSelectedItem());
            data.preexistingToteCount = preexistingHeight;

            // If the stack was not marked as preexisting, set the preexisting height to 0
            if (data.isPreexisting == false) {
                data.preexistingToteCount = 0;
            }

            cycles.add(data);

            // Reset all the views by creating a default StackData
            updateViewsFromData(new CycleModel());
        }
    }

    private void updateViewsFromData(CycleModel data) {
        totesCounter.setCount(data.toteCount);
        hasBinCheckbox.setChecked(data.hasBin);
        hasNoodleCheckbox.setChecked(data.hasNoodle);
        binDroppedCheckbox.setChecked(data.binDropped);
        stackDroppedCheckbox.setChecked(data.stackDropped);
        preexistingStackCheckbox.setChecked(data.isPreexisting);
        notScoredCheckbox.setChecked(data.notScored);
        preexistingHeightSpinner.setSelectionBasedOnText(Integer.toString(data.preexistingToteCount));
    }
}
