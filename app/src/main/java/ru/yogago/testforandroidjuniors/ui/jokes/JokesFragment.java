package ru.yogago.testforandroidjuniors.ui.jokes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import ru.yogago.testforandroidjuniors.R;

public class JokesFragment extends Fragment {

    private final String LOG_TAG = "myLog";
    private JokesViewModel jokesViewModel;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        jokesViewModel = ViewModelProviders.of(this).get(JokesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_jokes, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final EditText evCountJokes = root.findViewById(R.id.countJokes);
        final ListView lvJokes = root.findViewById(R.id.jokesList);
        final Button buttonStart = root.findViewById(R.id.button);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int jokesCount = Integer.parseInt(evCountJokes.getText().toString());
                jokesViewModel.loadContent(jokesCount);
            }
        });
        jokesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        jokesViewModel.getTextCountJokes().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                evCountJokes.setText(integer + "");
            }
        });
        jokesViewModel.getJokes().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                lvJokes.setAdapter(getAdapter(strings));
            }
        });

        return root;
    }

    private ListAdapter getAdapter(ArrayList<String> strings){
        return new ArrayAdapter<>(this.context, android.R.layout.simple_list_item_1, strings);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.context = this.getContext();
    }

}