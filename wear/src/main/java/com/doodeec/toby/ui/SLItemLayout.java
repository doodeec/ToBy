package com.doodeec.toby.ui;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doodeec.toby.R;
import com.doodeec.toby.detail.SLItemAdapter;

public class SLItemLayout extends RelativeLayout implements WearableListView.OnCenterProximityListener {

    private final float mFadedTextAlpha;
    private ImageView mDelete;
    private ImageView mCheck;
    private RelativeLayout mActionButtons;
    private RelativeLayout mActionButtonsOverlay;
    private TextView mName;
    private SLItemAdapter.ActionButtonListener mActionBtnListener;

    public SLItemLayout(Context context) {
        this(context, null);
    }

    public SLItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SLItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mFadedTextAlpha = 40 / 100f;
    }

    public void setActionButtonListener(SLItemAdapter.ActionButtonListener actionBtnListener) {
        mActionBtnListener = actionBtnListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
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
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mName.setAlpha(1f);
        setBackgroundColor(getResources().getColor(R.color.light_grey));
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        setActionButtonsVisible(false);
        mName.setAlpha(mFadedTextAlpha);
        setBackgroundColor(getResources().getColor(R.color.white));
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
