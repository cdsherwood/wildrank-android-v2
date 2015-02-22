package org.wildstang.wildrank.androidv2.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;

import org.wildstang.wildrank.androidv2.R;
import org.wildstang.wildrank.androidv2.Utilities;
import org.wildstang.wildrank.androidv2.activities.NotesActivity;
import org.wildstang.wildrank.androidv2.activities.ScoutMatchActivity;
import org.wildstang.wildrank.androidv2.adapters.MatchListAdapter;
import org.wildstang.wildrank.androidv2.data.DatabaseManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Liam on 2/21/2015.
 */
public class NotesMainFragment extends Fragment implements View.OnClickListener {

    private ListView list;

    private TextView scoutingTeam;
    private Button beginScouting;
    private TextView matchNumber;

    private String[] selectedTeams;
    private String selectedTeamsString;
    private String selectedMatchKey;

    private MatchListAdapter adapter;

    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    public NotesMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            // If the team we are configured to scout for changes, reload the
            // data
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("assignedTeam")) {
                    if (NotesMainFragment.this.isAdded()) {
                        // Requery the list to update which matches are scouted or not
                        try {
                            Log.d("wildrank", "Requerying match list!");

                            runQuery();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error querying the match list. Check logcat!", Toast.LENGTH_LONG).show();
                        }

                        // Update the selected match view if it exists
                        if (selectedMatchKey != null) {
                            try {
                                Log.d("wildrank", "Requerying match details!");
                                onMatchSelected(DatabaseManager.getInstance(NotesMainFragment.this.getActivity()).getMatchFromKey(selectedMatchKey));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("wildrank", "You idiot, it's null!");

                        }
                    } else {
                        Log.d("wildrank", "Fragment not added!");
                    }
                }

            }
        };

        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_scouting_main, container, false);

        list = (ListView) view.findViewById(R.id.match_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QueryRow row = (QueryRow) parent.getItemAtPosition(position);
                onMatchSelected(row.getDocument());
            }
        });

        // We reuse this view to show the "select a match" message to avoid having another view
        matchNumber = (TextView) view.findViewById(R.id.match_number);
        matchNumber.setText("Please select a match.");

        scoutingTeam = (TextView) view.findViewById(R.id.scouting_team);
        scoutingTeam.setText("");

        beginScouting = (Button) view.findViewById(R.id.begin_scouting);
        beginScouting.setOnClickListener(this);
        beginScouting.setEnabled(false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            runQuery();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error querying the match list. Check logcat!", Toast.LENGTH_LONG).show();
        }
    }

    private void runQuery() throws Exception {
        Query query = DatabaseManager.getInstance(getActivity()).getAllMatches();

        adapter = new MatchListAdapter(getActivity(), new ArrayList<QueryRow>());

        QueryEnumerator enumerator = query.run();

        Log.d("wildrank", "match query count: " + enumerator.getCount());

        List<QueryRow> queryRows = new ArrayList<>();
        for (Iterator<QueryRow> it = enumerator; it.hasNext(); ) {
            QueryRow row = it.next();
            queryRows.add(row);
            Log.d("wildstang", "Document key: " + row.getKey());
        }
        Parcelable state = list.onSaveInstanceState();
        adapter = new MatchListAdapter(getActivity(), queryRows);
        list.setAdapter(adapter);
        list.onRestoreInstanceState(state);
    }

    private void onMatchSelected(Document matchDocument) {
        selectedMatchKey = (String) matchDocument.getProperty("key");
        Log.d("wildrank", "match key is null? " + (selectedMatchKey == null));

        int matchNumber = (Integer) matchDocument.getProperty("match_number");
        this.matchNumber.setText("Match " + matchNumber);

        Object[] objects = Utilities.getTeamsFromMatchDocument(matchDocument);
        selectedTeams = Arrays.copyOf(objects, objects.length, String[].class);

        StringBuilder teams = new StringBuilder();
        for(int i = 0; i < selectedTeams.length; i++)
        {
            if(i != selectedTeams.length - 1)
            {
                teams.append(selectedTeams[i].replace("frc", "") + ", ");
            }
            else
            {
                teams.append(selectedTeams[i].replace("frc", ""));
            }
        }
        selectedTeamsString = teams.toString();
        scoutingTeam.setText(selectedTeamsString);

        beginScouting.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.begin_scouting) {
            // Launch the scouting activity
            final Intent intent = NotesActivity.createIntent(getActivity(), selectedMatchKey, selectedTeamsString, selectedTeams);
            startActivity(intent);
        }
    }
}
