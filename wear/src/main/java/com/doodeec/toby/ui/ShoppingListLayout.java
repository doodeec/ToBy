package com.doodeec.toby.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doodeec.toby.R;

public class ShoppingListLayout extends RelativeLayout implements WearableListView.OnCenterProximityListener {

    private final float mFadedTextAlpha;
    private final int mFadedCircleColor;
    private final int mChosenCircleColor;
    private ImageView mCircle;
    private TextView mName;

    public ShoppingListLayout(Context context) {
        this(context, null);
    }

    public ShoppingListLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShoppingListLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mFadedTextAlpha = 40 / 100f;
        mFadedCircleColor = getResources().getColor(R.color.grey);
        mChosenCircleColor = getResources().getColor(R.color.blue);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircle = (ImageView) findViewById(R.id.circle);
        mName = (TextView) findViewById(R.id.name);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mName.setAlpha(1f);
        mCircle.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setInterpolator(new DecelerateInterpolator())
                .start();
        ((GradientDrawable) mCircle.getDrawable()).setColor(mChosenCircleColor);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        if (mCircle.getScaleX() != 1f) {
            mCircle.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }
        ((GradientDrawable) mCircle.getDrawable()).setColor(mFadedCircleColor);
        mName.setAlpha(mFadedTextAlpha);
    }
}
