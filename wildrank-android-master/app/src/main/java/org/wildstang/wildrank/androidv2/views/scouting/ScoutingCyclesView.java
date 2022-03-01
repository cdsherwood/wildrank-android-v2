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

    //private ScoutingCounterView gamepiece1DroppedCounter;
    //private ScoutingCheckboxView NotScoredCheckbox;
    private ScoutingCheckboxView HubUpperCheckbox;
    private ScoutingCheckboxView HubLowerCheckbox;
    private ScoutingCheckboxView MissedCheckbox;
    private ScoutingCheckboxView DefendedCheckbox;
    private ScoutingCheckboxView Location1Checkbox;
    private ScoutingCheckboxView Location2Checkbox;
    private ScoutingCheckboxView Location3Checkbox;
    private ScoutingCheckboxView Location4Checkbox;
    private ScoutingCheckboxView Location5Checkbox;
    private ScoutingCheckboxView Location6Checkbox;
    private ScoutingCheckboxView Location7Checkbox;
    private ScoutingCheckboxView Location8Checkbox;

    public ScoutingCyclesView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.scouting_view_cycle, this, true);
        //gamepiece1DroppedCounter = (ScoutingCounterView) findViewById(R.id.hatch_dropped_counter);
        //NotScoredCheckbox = (ScoutingCheckboxView) findViewById(R.id.not_scored);
        HubUpperCheckbox = (ScoutingCheckboxView) findViewById(R.id.hub_upper_score);
        HubLowerCheckbox = (ScoutingCheckboxView) findViewById(R.id.hub_lower_score);
        MissedCheckbox = (ScoutingCheckboxView) findViewById(R.id.missed_shot);
        DefendedCheckbox = (ScoutingCheckboxView) findViewById(R.id.defended_shot);
        Location1Checkbox = (ScoutingCheckboxView) findViewById(R.id.location_1);
        Location2Checkbox = (ScoutingCheckboxView) findViewById(R.id.location_2);
        Location3Checkbox = (ScoutingCheckboxView) findViewById(R.id.location_3);
        Location4Checkbox = (ScoutingCheckboxView) findViewById(R.id.location_4);
        Location5Checkbox = (ScoutingCheckboxView) findViewById(R.id.location_5);
        Location6Checkbox = (ScoutingCheckboxView) findViewById(R.id.location_6);
        Location7Checkbox = (ScoutingCheckboxView) findViewById(R.id.location_7);
        Location8Checkbox = (ScoutingCheckboxView) findViewById(R.id.location_8);

        /* THIS IS IMPORTANT! (this enables a control that has been disabled by default)
        // enable a control with another control
        preexistingStackCheckbox.setOnValueChangedListener(preexistingHeightSpinner::setEnabled);

        // Synchronize the state of the preexisting height spinner with the checkbox
        preexistingHeightSpinner.setEnabled(preexistingStackCheckbox.isChecked());
        */
        ///Location2Checkbox.setOnValueChangedListener(Location1Checkbox::setEnabled);

        Location1Checkbox.setOnValueChangedListener(new ScoutingCheckboxView.OnValueChangedListener() {
            @Override
            public void onValueChanged(boolean newValue) {
                //Location1Checkbox.setChecked(!newValue);
                Location2Checkbox.setEnabled(!newValue);
                Location3Checkbox.setEnabled(!newValue);
                Location4Checkbox.setEnabled(!newValue);
                Location5Checkbox.setEnabled(!newValue);
                Location6Checkbox.setEnabled(!newValue);
                Location7Checkbox.setEnabled(!newValue);
                Location8Checkbox.setEnabled(!newValue);
            }
        });

        Location2Checkbox.setOnValueChangedListener(new ScoutingCheckboxView.OnValueChangedListener() {
            @Override
            public void onValueChanged(boolean newValue) {
                Location1Checkbox.setEnabled(!newValue);
                ///Location2Checkbox.setEnabled(!newValue);
                Location3Checkbox.setEnabled(!newValue);
                Location4Checkbox.setEnabled(!newValue);
                Location5Checkbox.setEnabled(!newValue);
                Location6Checkbox.setEnabled(!newValue);
                Location7Checkbox.setEnabled(!newValue);
                Location8Checkbox.setEnabled(!newValue);
            }
        });

        Location3Checkbox.setOnValueChangedListener(new ScoutingCheckboxView.OnValueChangedListener() {
            @Override
            public void onValueChanged(boolean newValue) {
                Location1Checkbox.setEnabled(!newValue);
                Location2Checkbox.setEnabled(!newValue);
                ///Location3Checkbox.setEnabled(!newValue);
                Location4Checkbox.setEnabled(!newValue);
                Location5Checkbox.setEnabled(!newValue);
                Location6Checkbox.setEnabled(!newValue);
                Location7Checkbox.setEnabled(!newValue);
                Location8Checkbox.setEnabled(!newValue);
            }
        });

        Location4Checkbox.setOnValueChangedListener(new ScoutingCheckboxView.OnValueChangedListener() {
            @Override
            public void onValueChanged(boolean newValue) {
                Location1Checkbox.setEnabled(!newValue);
                Location2Checkbox.setEnabled(!newValue);
                Location3Checkbox.setEnabled(!newValue);
                ///Location4Checkbox.setEnabled(!newValue);
                Location5Checkbox.setEnabled(!newValue);
                Location6Checkbox.setEnabled(!newValue);
                Location7Checkbox.setEnabled(!newValue);
                Location8Checkbox.setEnabled(!newValue);
            }
        });

        Location5Checkbox.setOnValueChangedListener(new ScoutingCheckboxView.OnValueChangedListener() {
            @Override
            public void onValueChanged(boolean newValue) {
                Location1Checkbox.setEnabled(!newValue);
                Location2Checkbox.setEnabled(!newValue);
                Location3Checkbox.setEnabled(!newValue);
                Location4Checkbox.setEnabled(!newValue);
                ///Location5Checkbox.setEnabled(!newValue);
                Location6Checkbox.setEnabled(!newValue);
                Location7Checkbox.setEnabled(!newValue);
                Location8Checkbox.setEnabled(!newValue);
            }
        });

        Location6Checkbox.setOnValueChangedListener(new ScoutingCheckboxView.OnValueChangedListener() {
            @Override
            public void onValueChanged(boolean newValue) {
                Location1Checkbox.setEnabled(!newValue);
                Location2Checkbox.setEnabled(!newValue);
                Location3Checkbox.setEnabled(!newValue);
                Location4Checkbox.setEnabled(!newValue);
                Location5Checkbox.setEnabled(!newValue);
                ///Location6Checkbox.setEnabled(!newValue);
                Location7Checkbox.setEnabled(!newValue);
                Location8Checkbox.setEnabled(!newValue);
            }
        });

        Location7Checkbox.setOnValueChangedListener(new ScoutingCheckboxView.OnValueChangedListener() {
            @Override
            public void onValueChanged(boolean newValue) {
                Location1Checkbox.setEnabled(!newValue);
                Location2Checkbox.setEnabled(!newValue);
                Location3Checkbox.setEnabled(!newValue);
                Location4Checkbox.setEnabled(!newValue);
                Location5Checkbox.setEnabled(!newValue);
                Location6Checkbox.setEnabled(!newValue);
                ///Location7Checkbox.setEnabled(!newValue);
                Location8Checkbox.setEnabled(!newValue);
            }
        });

        Location8Checkbox.setOnValueChangedListener(new ScoutingCheckboxView.OnValueChangedListener() {
            @Override
            public void onValueChanged(boolean newValue) {
                Location1Checkbox.setEnabled(!newValue);
                Location2Checkbox.setEnabled(!newValue);
                Location3Checkbox.setEnabled(!newValue);
                Location4Checkbox.setEnabled(!newValue);
                Location5Checkbox.setEnabled(!newValue);
                Location6Checkbox.setEnabled(!newValue);
                Location7Checkbox.setEnabled(!newValue);
                ///Location8Checkbox.setEnabled(!newValue);
            }
        });

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
            //data.HatchdropCount= gamepiece1DroppedCounter.getCount();  //counter example
            //data.isNotScored = NotScoredCheckbox.isChecked();          //checkbox example
            data.isHubUpper = HubUpperCheckbox.isChecked();
            data.isHubLower = HubLowerCheckbox.isChecked();
            data.isMissedShot = MissedCheckbox.isChecked();
            data.isDefended = DefendedCheckbox.isChecked();
            data.isLocation1 = Location1Checkbox.isChecked();
            data.isLocation2 = Location2Checkbox.isChecked();
            data.isLocation3 = Location3Checkbox.isChecked();
            data.isLocation4 = Location4Checkbox.isChecked();
            data.isLocation5 = Location5Checkbox.isChecked();
            data.isLocation6 = Location6Checkbox.isChecked();
            data.isLocation7 = Location7Checkbox.isChecked();
            data.isLocation8 = Location8Checkbox.isChecked();

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
        //gamepiece1DroppedCounter.setCount(data.dropCount1);
        HubUpperCheckbox.setChecked(data.isHubUpper);
        HubLowerCheckbox.setChecked(data.isHubLower);
        MissedCheckbox.setChecked(data.isMissedShot);
        DefendedCheckbox.setChecked(data.isDefended);
        Location1Checkbox.setChecked(data.isLocation1);
        Location2Checkbox.setChecked(data.isLocation2);
        Location3Checkbox.setChecked(data.isLocation3);
        Location4Checkbox.setChecked(data.isLocation4);
        Location5Checkbox.setChecked(data.isLocation5);
        Location6Checkbox.setChecked(data.isLocation6);
        Location7Checkbox.setChecked(data.isLocation7);
        Location8Checkbox.setChecked(data.isLocation8);


        //preexistingStackCheckbox.setChecked(data.isPreexisting);
        //preexistingHeightSpinner.setSelectionBasedOnText(Integer.toString(data.preexistingToteCount));
    }
}
