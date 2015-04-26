package com.doodeec.toby.views.shoppinglist.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.doodeec.toby.R;

/**
 * @author dusan.bartos
 */
public class SLDetailActivity extends AppCompatActivity {

    public static final String SHOPPING_LIST_NEW = "slClean";
    public static final String SHOPPING_LIST_ID = "slId";

    private SLDetailFragment mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame, SLDetailFragment.newInstance(getIntent().getExtras()),
                            SLDetailFragment.TAG)
                    .commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Fragment detail = getSupportFragmentManager().findFragmentByTag(SLDetailFragment.TAG);
        if (detail != null) {
            mDetailFragment = (SLDetailFragment) detail;
        }
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

            case R.id.action_complete:
                if (mDetailFragment != null) {
                    mDetailFragment.completeShoppingList();
                }
                finish();
                return true;

            case R.id.action_about:
                //TODO Open about
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
