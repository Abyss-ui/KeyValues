
package com.example.keyvalues.ui.dashboard;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.keyvalues.R;

public class DashboardFragment extends Fragment {
    public int counter;
    private Button button;
    private int highscore;
    private int current;
    private TextView texthighscore;
    private TextView textcurrentscore;
    private TextView textView;
    private CountDownTimer timer;
    private TextView text_view_countdown;
    private Button mash;

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        text_view_countdown = root.findViewById(R.id.text_view_countdown);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        texthighscore = root.findViewById(R.id.Highscore);
        textcurrentscore = root.findViewById(R.id.Currentscore);
        button = root.findViewById(R.id.button_reset);
        mash = root.findViewById(R.id.mash_button);
        button.setEnabled(true);
        mash.setEnabled(false);
        //Context context = getActivity();
        final SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int defaultValue = 0;
        highscore=sharedPref.getInt(getString(R.string.highsc), defaultValue);
        texthighscore.setText("High score: "+highscore);
        current = 0;
        textcurrentscore.setText("Current score: "+current);

        timer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                text_view_countdown.setText("" + millisUntilFinished / 1000);
            }
            public void onFinish() {
                text_view_countdown.setText("");
                button.setEnabled(true);
                mash.setEnabled(false);
                if(current > highscore){
                    highscore=current;
                    texthighscore.setText("High score: "+highscore);
                }
                    current=0;
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.highsc), highscore);
                editor.commit();

            }

        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                mash.setEnabled(true);
                timer.start();
            }
        });

        mash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current = current+1;
                textcurrentscore.setText("Current score: "+current);

            }
        });

        return root;
    }


}

