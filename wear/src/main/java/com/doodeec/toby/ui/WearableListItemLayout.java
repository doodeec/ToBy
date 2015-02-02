package com.doodeec.toby.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doodeec.toby.R;
import com.doodeec.toby.detail.ShoppingListItemWearableAdapter;

public class WearableListItemLayout extends RelativeLayout implements WearableListView.OnCenterProximityListener {

    private final float mFadedTextAlpha;
    private final int mFadedCircleColor;
    private final int mChosenCircleColor;
    private ImageView mCircle;
    private ImageView mDelete;
    private ImageView mCheck;
    private RelativeLayout mActionButtons;
    private RelativeLayout mActionButtonsOverlay;
    private TextView mName;
    private ShoppingListItemWearableAdapter.ActionButtonListener mActionBtnListener;

    public WearableListItemLayout(Context context) {
        this(context, null);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mFadedTextAlpha = 40 / 100f;
        mFadedCircleColor = getResources().getColor(R.color.grey);
        mChosenCircleColor = getResources().getColor(R.color.blue);
    }

    public void setActionButtonListener(ShoppingListItemWearableAdapter.ActionButtonListener actionBtnListener) {
        mActionBtnListener = actionBtnListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircle = (ImageView) findViewById(R.id.circle);
        mName = (TextView) findViewById(R.id.name);

        mActionButtons = (RelativeLayout) findViewById(R.id.action_buttons);
        mActionButtonsOverlay = (RelativeLayout) findViewById(R.id.overlay);
        mDelete = (ImageView) findViewById(R.id.delete);
        mCheck = (ImageView) findViewById(R.id.check);

        mDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionBtnListener != null) {
                    mActionBtnListener.onDeleteClicked(null);
                }
            }
        });
        mCheck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionBtnListener != null) {
                    mActionBtnListener.onCheckClicked(null);
                }
            }
        });

        measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // hide buttons and overlay initially
        mActionButtons.setTranslationX(getMeasuredWidth());
        mActionButtonsOverlay.setAlpha(0);

        ((GradientDrawable) mDelete.getDrawable()).setColor(getResources().getColor(R.color.red));
        ((GradientDrawable) mCheck.getDrawable()).setColor(getResources().getColor(R.color.green));
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
        setActionButtonsVisible(false);

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

    public void setActionButtonsVisible(boolean visible) {
        if (visible) {
            if (mActionButtons.getTranslationX() == 0) return;
            mActionButtons.animate()
                    .translationX(0)
                    .start();
            mActionButtonsOverlay.animate()
                    .alpha(1)
                    .start();
        } else {
            if (mActionButtons.getTranslationX() == getMeasuredWidth()) return;
            mActionButtons.animate()
                    .translationX(getMeasuredWidth())
                    .start();
            mActionButtonsOverlay.animate()
                    .alpha(0)
                    .start();
        }
    }
}
