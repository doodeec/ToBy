package com.doodeec.toby.views.shoppinglist.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doodeec.toby.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author dusan.bartos
 */
public class SLViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.list_name)
    TextView name;

    public SLViewHolder(View view) {
        super(view);

        ButterKnife.inject(this, view);
    }

    public void setName(String name) {
        this.name.setText(name);
    }
}
