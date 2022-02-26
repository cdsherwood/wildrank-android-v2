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

    private ScoutingCounterView gamepiece1DroppedCounter;
    private ScoutingCounterView gamepiece2DroppedCounter;
    private ScoutingCheckboxView CSHatchCheckbox;
    private ScoutingCheckboxView CSCargoCheckbox;
    private ScoutingCheckboxView ROHatchL1Checkbox;
    private ScoutingCheckboxView ROHatchL2Checkbox;
    private ScoutingCheckboxView ROHatchL3Checkbox;
    private ScoutingCheckboxView ROCargoL1Checkbox;
    private ScoutingCheckboxView ROCargoL2Checkbox;
    private ScoutingCheckboxView ROCargoL3Checkbox;
    private ScoutingCheckboxView notScoredCheckbox;

    public ScoutingCyclesView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.scouting_view_cycle, this, true);

        CSHatchCheckbox = (ScoutingCheckboxView) findViewById(R.id.cs_hatch);
        CSCargoCheckbox = (ScoutingCheckboxView) findViewById(R.id.cs_cargo);
        ROHatchL1Checkbox = (ScoutingCheckboxView) findViewById(R.id.ro_hatch_l1);
        ROHatchL2Checkbox = (ScoutingCheckboxView) findViewById(R.id.ro_hatch_l2);
        ROHatchL3Checkbox = (ScoutingCheckboxView) findViewById(R.id.ro_hatch_l3);
        ROCargoL1Checkbox = (ScoutingCheckboxView) findViewById(R.id.ro_cargo_l1);
        ROCargoL2Checkbox = (ScoutingCheckboxView) findViewById(R.id.ro_cargo_l2);
        ROCargoL3Checkbox = (ScoutingCheckboxView) findViewById(R.id.ro_cargo_l3);
        gamepiece1DroppedCounter = (ScoutingCounterView) findViewById(R.id.gamepiece_1_dropped);
        gamepiece2DroppedCounter = (ScoutingCounterView) findViewById(R.id.gamepiece_2_dropped);
        notScoredCheckbox = (ScoutingCheckboxView) findViewById(R.id.not_scored);

        /* THIS IS IMPORTANT! (this enables a control that has been disabled by default)
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
            data.dropCount1 = gamepiece1DroppedCounter.getCount();
            data.dropCount2 = gamepiece2DroppedCounter.getCount();
            data.isCSHatch = CSHatchCheckbox.isChecked();
            data.isCSCargo = CSCargoCheckbox.isChecked();
            data.isROHatchL1 = ROHatchL1Checkbox.isChecked();
            data.isROHatchL2 = ROHatchL2Checkbox.isChecked();
            data.isROHatchL3 = ROHatchL3Checkbox.isChecked();
            data.isROCargoL1 = ROCargoL1Checkbox.isChecked();
            data.isROCargoL2 = ROCargoL2Checkbox.isChecked();
            data.isROCargoL3 = ROCargoL3Checkbox.isChecked();
            data.notScored = notScoredCheckbox.isChecked();

            /* IMPORTANT! - see above (line # 45ish)
            int preexistingHeight = Integer.parseInt(preexistingHeightSpinner.getSelectedItem());
            data.preexistingToteCount = preexistingHeight;

            // If the stack was not marked as preexisting, set the preexisting height to 0
            if (data.isPreexisting == false) {
                data.preexistingToteCount = 0;
            }
            */

            cycles.add(data);

            // Reset all the views by creating a default StackData
            updateViewsFromData(new CycleModel());
        }
    }

    private void updateViewsFromData(CycleModel data) {
        gamepiece1DroppedCounter.setCount(data.dropCount1);
        gamepiece2DroppedCounter.setCount(data.dropCount2);
        CSHatchCheckbox.setChecked(data.isCSHatch);
        CSCargoCheckbox.setChecked(data.isCSCargo);
        ROHatchL1Checkbox.setChecked(data.isROHatchL1);
        ROHatchL2Checkbox.setChecked(data.isROHatchL2);
        ROHatchL3Checkbox.setChecked(data.isROHatchL3);
        ROCargoL1Checkbox.setChecked(data.isROCargoL1);
        ROCargoL2Checkbox.setChecked(data.isROCargoL2);
        ROCargoL3Checkbox.setChecked(data.isROCargoL3);
        notScoredCheckbox.setChecked(data.notScored);

        //preexistingStackCheckbox.setChecked(data.isPreexisting);
        //preexistingHeightSpinner.setSelectionBasedOnText(Integer.toString(data.preexistingToteCount));
    }
}
