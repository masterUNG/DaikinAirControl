package masterung.androidthai.in.th.daikinaircontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import masterung.androidthai.in.th.daikinaircontrol.fragment.ControlFragment;
import masterung.androidthai.in.th.daikinaircontrol.fragment.ListAirFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Add Fragment
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentMainFragment, new ListAirFragment()).commit();

        }




    }   // Main Method

}   // Main Class
