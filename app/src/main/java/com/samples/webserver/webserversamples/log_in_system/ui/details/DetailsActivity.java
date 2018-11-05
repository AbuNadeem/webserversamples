package com.samples.webserver.webserversamples.log_in_system.ui.details;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.samples.webserver.webserversamples.R;
import com.samples.webserver.webserversamples.log_in_system.base.BaseActivity;
import com.samples.webserver.webserversamples.log_in_system.ui.Main.MainActivity;

import butterknife.BindView;

public class DetailsActivity extends BaseActivity {


    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detailstoolbar)
    protected Toolbar mToolbar;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setTitle(getResources().getString(R.string.app_name));
        }
    }



    @SuppressWarnings("SameReturnValue")
    @Override
    protected int getResourceLayout() {
        return R.layout.activity_details;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void init() {


    }

    @SuppressWarnings("EmptyMethod")
    @Override
    protected void setListener() {

    }

    @SuppressWarnings("SameReturnValue")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);

                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
