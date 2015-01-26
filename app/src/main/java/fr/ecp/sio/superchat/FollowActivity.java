package fr.ecp.sio.superchat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


/**
 * Created by mac on 19/01/15.
 */
public class FollowActivity extends ActionBarActivity{
    private static final String ARG_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.follow_fragment);

        if (savedInstanceState == null) {
            FollowFragment followFragment = new FollowFragment();
            followFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.follow_frag, followFragment)
                    .commit();
        }
    }


}