package com.doodeec.toby.views.shoppinglist.detail;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.doodeec.toby.R;

/**
 * @author dusan.bartos
 */
public class SLDetailActivity extends ActionBarActivity {

    public static final String SHOPPING_LIST_ID = "slId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame, SLDetailFragment.newInstance(getIntent().getExtras()),
                        SLDetailFragment.TAG)
                .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
