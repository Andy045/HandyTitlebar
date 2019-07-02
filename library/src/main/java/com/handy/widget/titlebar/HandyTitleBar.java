package com.handy.widget.titlebar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handy.widget.titlebar.entity.Action;
import com.handy.widget.titlebar.utils.HandyTitleBarUtils;
import com.handy.widget.titlebar.widget.MarqueeTextView;

import java.lang.reflect.Field;

/**
 * 新标题栏控件
 *
 * @author LiuJie https://www.Handy045.com
 * @description 支持沉浸式，可以在xml布局中通过自定义属性配置标题栏样式
 * @date Created in 2018/5/19 下午5:55
 * @modified By LiuJie
 */
public class HandyTitleBar extends ViewGroup {

    private Context context;
    private TypedArray typedArray;

    private int statusBarHeight;
    private int statusBarBackgroundColor;
    private boolean isShowCustomStatusBar;

    private int topLineHeight;
    private int topLineColor;

    private int titleBarPadding;
    private int titleBarPaddingTop;
    private int titleBarPaddingLeft;
    private int titleBarPaddingRight;
    private int titleBarPaddingBottom;
    private int titleBarHeight;
    private Drawable titleBarBackground;
    private int contentLayoutPadding;
    private String mainText;
    private float mainTextSize;
    private int mainTextColor;
    private int mainTextBackgroundColor;
    private String subText;
    private float subTextSize;
    private int subTextColor;
    private int subTextBackgroundColor;

    private int bottomLineHeight;
    private int bottomLineColor;

    private int actionViewPadding;
    private int actionLayoutPadding;
    private int actionTextMarginLeft;
    private int actionTextSize;
    private int actionTextColor;
    private int actionImageSize;

    private int displayWidth;
    private int parentWidth;
    private int parentHeight;

    private int paddingTop;
    private int paddingLeft;
    private int paddingRight;
    private int paddingBottom;

    private View statusBar;
    private View topLineView;
    private View titleBar;
    private LinearLayout leftActionsLayout;
    private LinearLayout contentLayout;
    private MarqueeTextView mainTextView;
    private MarqueeTextView subTextView;
    private LinearLayout rightActionsLayout;
    private View bottomLineView;

    public HandyTitleBar(Context context) {
        this(context, null);
    }

    public HandyTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("CustomViewStyleable")
    public HandyTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        displayWidth = HandyTitleBarUtils.getScreenWidth(context);
        typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HandyTitleBarStyleable);

        isShowCustomStatusBar = typedArray.getBoolean(R.styleable.HandyTitleBarStyleable_handy_isShowCustomStatusBar, false);
        statusBarHeight = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_statusBarHeight, isShowCustomStatusBar ? getStatusBarHeight(context) : 0);
        statusBarBackgroundColor = typedArray.getColor(R.styleable.HandyTitleBarStyleable_handy_statusBarBackgroundColor, getResources().getColor(R.color.handyTitleBar_statusBarBackgroundColor));

        topLineHeight = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_topLineHeight, 0);
        topLineColor = typedArray.getColor(R.styleable.HandyTitleBarStyleable_handy_topLineColor, getResources().getColor(R.color.handyTitleBar_topLineColor));

        titleBarPadding = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_titleBarPadding, 0);
        titleBarPaddingTop = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_titleBarPaddingTop, 0);
        titleBarPaddingLeft = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_titleBarPaddingLeft, 0);
        titleBarPaddingRight = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_titleBarPaddingRight, 0);
        titleBarPaddingBottom = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_titleBarPaddingBottom, 0);
        titleBarHeight = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_titleBarHeight, getResources().getDimension(R.dimen.handyTitleBar_titleBarHeight));
        titleBarBackground = typedArray.getDrawable(R.styleable.HandyTitleBarStyleable_handy_titleBarBackground);
        titleBarBackground = titleBarBackground != null ? titleBarBackground : new ColorDrawable(getResources().getColor(R.color.handyTitleBar_titleBarBackground));

        contentLayoutPadding = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_contentLayoutPadding, getResources().getDimension(R.dimen.handyTitleBar_contentLayoutPadding));
        mainText = typedArray.getString(R.styleable.HandyTitleBarStyleable_handy_mainText);
        mainTextSize = typedArray.getDimensionPixelSize(R.styleable.HandyTitleBarStyleable_handy_mainTextSize, getResources().getDimensionPixelSize(R.dimen.handyTitleBar_mainTextSize));
        mainTextColor = typedArray.getColor(R.styleable.HandyTitleBarStyleable_handy_mainTextColor, getResources().getColor(R.color.handyTitleBar_mainTextColor));
        mainTextBackgroundColor = typedArray.getColor(R.styleable.HandyTitleBarStyleable_handy_mainTextBackgroundColor, getResources().getColor(R.color.handyTitleBar_mainTextBackgroundColor));

        subText = typedArray.getString(R.styleable.HandyTitleBarStyleable_handy_subText);
        subTextSize = typedArray.getDimensionPixelSize(R.styleable.HandyTitleBarStyleable_handy_subTextSize, getResources().getDimensionPixelSize(R.dimen.handyTitleBar_subTextSize));
        subTextColor = typedArray.getColor(R.styleable.HandyTitleBarStyleable_handy_subTextColor, getResources().getColor(R.color.handyTitleBar_subTextColor));
        subTextBackgroundColor = typedArray.getColor(R.styleable.HandyTitleBarStyleable_handy_subTextBackgroundColor, getResources().getColor(R.color.handyTitleBar_subTextBackgroundColor));

        bottomLineHeight = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_bottomLineHeight, 0);
        bottomLineColor = typedArray.getColor(R.styleable.HandyTitleBarStyleable_handy_bottomLineColor, getResources().getColor(R.color.handyTitleBar_bottomLineColor));

        actionViewPadding = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_actionViewPadding, getResources().getDimension(R.dimen.handyTitleBar_actionViewPadding));
        actionLayoutPadding = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_actionLayoutPadding, getResources().getDimension(R.dimen.handyTitleBar_actionLayoutPadding));
        actionTextMarginLeft = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_actionTextMarginLeft, getResources().getDimension(R.dimen.handyTitleBar_actionTextMarginLeft));
        actionTextSize = typedArray.getDimensionPixelSize(R.styleable.HandyTitleBarStyleable_handy_actionTextSize, getResources().getDimensionPixelSize(R.dimen.handyTitleBar_actionTextSize));
        actionTextColor = typedArray.getColor(R.styleable.HandyTitleBarStyleable_handy_actionTextColor, getResources().getColor(R.color.handyTitleBar_actionTextColor));
        actionImageSize = (int) typedArray.getDimension(R.styleable.HandyTitleBarStyleable_handy_actionImageSize, getResources().getDimension(R.dimen.handyTitleBar_actionImageSize));

        typedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {
        statusBar = new View(context);
        statusBar.setBackgroundColor(statusBarBackgroundColor);
        if (isShowCustomStatusBar) {
            Activity activity = getActivity();
            if (activity != null) {
                showCustomStatusBar(activity);
            }
        }

        topLineView = new View(context);
        topLineView.setBackgroundColor(topLineColor);

        titleBar = new View(context);
        titleBar.setBackground(titleBarBackground);

        leftActionsLayout = new LinearLayout(context);
        leftActionsLayout.setOrientation(LinearLayout.HORIZONTAL);
        leftActionsLayout.setPadding(actionLayoutPadding, 0, actionLayoutPadding, 0);
        leftActionsLayout.setBackgroundColor(Color.TRANSPARENT);
        leftActionsLayout.setGravity(Gravity.CENTER);

        mainTextView = new MarqueeTextView(context);
        mainTextView.setText(mainText);
        mainTextView.setTextColor(mainTextColor);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mainTextSize);
        mainTextView.setBackgroundColor(mainTextBackgroundColor);
        mainTextView.setSingleLine();
        mainTextView.setGravity(Gravity.CENTER);
        mainTextView.setIncludeFontPadding(false);
        mainTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mainTextView.setVisibility((mainText == null || "".equals(mainText)) ? GONE : VISIBLE);

        subTextView = new MarqueeTextView(context);
        subTextView.setText(subText);
        subTextView.setTextColor(subTextColor);
        subTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, subTextSize);
        subTextView.setBackgroundColor(subTextBackgroundColor);
        subTextView.setSingleLine();
        subTextView.setGravity(Gravity.CENTER);
        subTextView.setIncludeFontPadding(false);
        subTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        subTextView.setVisibility((subText == null || "".equals(subText)) ? GONE : VISIBLE);

        contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setGravity(Gravity.CENTER);
        contentLayout.setBackgroundColor(Color.TRANSPARENT);
        contentLayout.setPadding(contentLayoutPadding, 0, contentLayoutPadding, 0);
        contentLayout.addView(mainTextView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        contentLayout.addView(subTextView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        rightActionsLayout = new LinearLayout(context);
        rightActionsLayout.setOrientation(LinearLayout.HORIZONTAL);
        rightActionsLayout.setPadding(actionLayoutPadding, 0, actionLayoutPadding, 0);
        rightActionsLayout.setBackgroundColor(Color.TRANSPARENT);
        rightActionsLayout.setGravity(Gravity.CENTER);

        bottomLineView = new View(context);
        bottomLineView.setBackgroundColor(bottomLineColor);

        addView(statusBar, new LayoutParams(LayoutParams.MATCH_PARENT, statusBarHeight));
        addView(topLineView, new LayoutParams(LayoutParams.MATCH_PARENT, topLineHeight));
        addView(titleBar, new LayoutParams(LayoutParams.MATCH_PARENT, titleBarHeight));
        addView(leftActionsLayout, new LayoutParams(LayoutParams.WRAP_CONTENT, titleBarHeight));
        addView(contentLayout, new LayoutParams(LayoutParams.WRAP_CONTENT, titleBarHeight));
        addView(rightActionsLayout, new LayoutParams(LayoutParams.WRAP_CONTENT, titleBarHeight));
        addView(bottomLineView, new LayoutParams(LayoutParams.MATCH_PARENT, bottomLineHeight));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (subTextView.getVisibility() == View.GONE) {
            mainTextView.setPadding(0, 0, 0, 0);
            subTextView.setPadding(0, 0, 0, 0);

        } else {
            if (contentLayout.getOrientation() == LinearLayout.VERTICAL) {
                mainTextView.setPadding(0, 0, 0, HandyTitleBarUtils.dpTopx(1));
                subTextView.setPadding(0, HandyTitleBarUtils.dpTopx(1), 0, 0);

            } else if (contentLayout.getOrientation() == LinearLayout.HORIZONTAL) {
                mainTextView.setPadding(0, 0, HandyTitleBarUtils.dpTopx(2), 0);
                subTextView.setPadding(HandyTitleBarUtils.dpTopx(2), 0, 0, 0);
            }
        }

        paddingTop = titleBarPadding > 0 ? titleBarPadding : titleBarPaddingTop;
        paddingLeft = titleBarPadding > 0 ? titleBarPadding : titleBarPaddingLeft;
        paddingRight = titleBarPadding > 0 ? titleBarPadding : titleBarPaddingRight;
        paddingBottom = titleBarPadding > 0 ? titleBarPadding : titleBarPaddingBottom;

        parentWidth = widthMode != MeasureSpec.AT_MOST ? widthSize : displayWidth;
        parentHeight = heightMode != MeasureSpec.AT_MOST ? heightSize : statusBarHeight + topLineHeight + titleBarHeight + bottomLineHeight;

        measureChild(statusBar, MeasureSpec.makeMeasureSpec(displayWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(statusBarHeight, MeasureSpec.EXACTLY));
        measureChild(titleBar, MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(titleBarHeight, MeasureSpec.EXACTLY));
        measureChild(topLineView, MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(topLineHeight, MeasureSpec.EXACTLY));
        measureChild(bottomLineView, MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(bottomLineHeight, MeasureSpec.EXACTLY));
        measureChild(leftActionsLayout, widthMeasureSpec, MeasureSpec.makeMeasureSpec(titleBarHeight, MeasureSpec.EXACTLY));
        measureChild(rightActionsLayout, widthMeasureSpec, MeasureSpec.makeMeasureSpec(titleBarHeight, MeasureSpec.EXACTLY));
        if (leftActionsLayout.getMeasuredWidth() > rightActionsLayout.getMeasuredWidth()) {
            contentLayout.measure(MeasureSpec.makeMeasureSpec(parentWidth - 2 * leftActionsLayout.getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(titleBarHeight, MeasureSpec.EXACTLY));
        } else {
            contentLayout.measure(MeasureSpec.makeMeasureSpec(parentWidth - 2 * rightActionsLayout.getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(titleBarHeight, MeasureSpec.EXACTLY));
        }

        setMeasuredDimension(displayWidth, parentHeight + paddingTop + paddingBottom);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        statusBar.layout(0, 0, displayWidth, statusBarHeight);

        topLineView.layout(0, statusBarHeight, parentWidth, statusBarHeight + topLineHeight);

        titleBar.layout(paddingLeft, statusBarHeight + topLineHeight + paddingTop, parentWidth - paddingRight, statusBarHeight + topLineHeight + titleBarHeight + paddingTop);

        if (leftActionsLayout.getChildCount() > 0) {
            leftActionsLayout.layout(paddingLeft, statusBarHeight + topLineHeight + paddingTop, leftActionsLayout.getMeasuredWidth(), statusBarHeight + topLineHeight + titleBarHeight + paddingTop);
        }

        if (leftActionsLayout.getChildCount() > 0 || rightActionsLayout.getChildCount() > 0) {
            if (leftActionsLayout.getMeasuredWidth() > rightActionsLayout.getMeasuredWidth()) {
                contentLayout.layout(leftActionsLayout.getMeasuredWidth() + paddingLeft, statusBarHeight + topLineHeight + paddingTop, parentWidth - leftActionsLayout.getMeasuredWidth() - paddingRight, statusBarHeight + topLineHeight + titleBarHeight + paddingTop);
            } else {
                contentLayout.layout(rightActionsLayout.getMeasuredWidth() + paddingLeft, statusBarHeight + topLineHeight + paddingTop, parentWidth - rightActionsLayout.getMeasuredWidth() - paddingRight, statusBarHeight + topLineHeight + titleBarHeight + paddingTop);
            }
        } else {
            contentLayout.layout(paddingLeft, statusBarHeight + topLineHeight + paddingTop, parentWidth - paddingRight, statusBarHeight + topLineHeight + titleBarHeight + paddingTop);
        }

        if (rightActionsLayout.getChildCount() > 0) {
            rightActionsLayout.layout(parentWidth - rightActionsLayout.getMeasuredWidth() - paddingRight, statusBarHeight + topLineHeight + paddingTop, parentWidth - paddingRight, statusBarHeight + topLineHeight + titleBarHeight + paddingTop);
        }

        bottomLineView.layout(paddingLeft, statusBarHeight + topLineHeight + titleBarHeight + paddingTop, parentWidth - paddingRight, statusBarHeight + topLineHeight + titleBarHeight + bottomLineHeight + paddingTop);
    }

    /*============================== 内部控件开放 ==============================*/

    public View getStatusBar() {
        return statusBar;
    }

    public View getTopLineView() {
        return topLineView;
    }

    public View getTitleBar() {
        return titleBar;
    }

    public LinearLayout getLeftActionsLayout() {
        return leftActionsLayout;
    }

    public LinearLayout getContentLayout() {
        return contentLayout;
    }

    public MarqueeTextView getMainTextView() {
        return mainTextView;
    }

    public MarqueeTextView getSubTextView() {
        return subTextView;
    }

    public LinearLayout getRightActionsLayout() {
        return rightActionsLayout;
    }

    public View getBottomLineView() {
        return bottomLineView;
    }

    /*============================== 元素属性修改 ==============================*/

    public HandyTitleBar setStatusBarHeight(int statusBarHeight) {
        if (statusBarHeight >= 0) {
            this.statusBarHeight = HandyTitleBarUtils.dpTopx(statusBarHeight);
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setStatusBarBackgroundColor(@DrawableRes int statusBarBackgroundColor) {
        try {
            statusBar.setBackgroundResource(statusBarBackgroundColor);
            this.statusBarBackgroundColor = statusBarBackgroundColor;
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置系统状态栏是否可见，安卓系统版本大于等于19
     */
    public HandyTitleBar showCustomStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBarHeight = getStatusBarHeight(activity);
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_STABLE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

        requestLayout();
        return this;
    }

    public HandyTitleBar setTopLineHeight(int topLineHeight) {
        if (topLineHeight >= 0) {
            this.topLineHeight = HandyTitleBarUtils.dpTopx(topLineHeight);
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setTopLineColor(@DrawableRes int topLineColor) {
        try {
            topLineView.setBackgroundResource(topLineColor);
            this.topLineColor = topLineColor;
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public HandyTitleBar setTitleBarPadding(int titleBarPadding) {
        if (titleBarPadding >= 0) {
            this.titleBarPadding = HandyTitleBarUtils.dpTopx(titleBarPadding);
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setTitleBarPaddingTop(int titleBarPaddingTop) {
        if (titleBarPaddingTop >= 0) {
            this.titleBarPaddingTop = HandyTitleBarUtils.dpTopx(titleBarPaddingTop);
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setTitleBarPaddingLeft(int titleBarPaddingLeft) {
        if (titleBarPaddingLeft >= 0) {
            this.titleBarPaddingLeft = HandyTitleBarUtils.dpTopx(titleBarPaddingLeft);
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setTitleBarPaddingRight(int titleBarPaddingRight) {
        if (titleBarPaddingRight >= 0) {
            this.titleBarPaddingRight = HandyTitleBarUtils.dpTopx(titleBarPaddingRight);
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setTitleBarPaddingBottom(int titleBarPaddingBottom) {
        if (titleBarPaddingBottom >= 0) {
            this.titleBarPaddingBottom = HandyTitleBarUtils.dpTopx(titleBarPaddingBottom);
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setTitleBarHeight(int titleBarHeight) {
        if (titleBarHeight >= 0) {
            this.titleBarHeight = HandyTitleBarUtils.dpTopx(titleBarHeight);
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setTitleBarBackground(@DrawableRes int titleBarBackground) {
        try {
            titleBar.setBackgroundResource(titleBarBackground);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.titleBarBackground = getContext().getDrawable(titleBarBackground);
            }
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public HandyTitleBar setTitleBarBackground(Drawable titleBarBackground) {
        if (titleBarBackground != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    titleBar.setBackground(titleBarBackground);
                } else {
                    titleBar.setBackgroundDrawable(titleBarBackground);
                }
                this.titleBarBackground = titleBarBackground;
                requestLayout();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * 设置标题栏文本布局方向
     *
     * @param orientation LinearLayout.VERTICAL or LinearLayout.HORIZONTAL
     * @return HandyTitleBar
     */
    public HandyTitleBar setContentLayoutOrientatio(int orientation) {
        if (orientation == LinearLayout.VERTICAL || orientation == LinearLayout.HORIZONTAL) {
            contentLayout.setOrientation(orientation);
            requestLayout();
        }
        return this;
    }

    @SuppressLint("SetTextI18n")
    public HandyTitleBar setTitleBarContent(CharSequence title) {
        mainTextView.setVisibility(View.VISIBLE);
        subTextView.setVisibility(View.VISIBLE);
        int index = title.toString().indexOf("\n");
        if (index > 0) {
            contentLayout.setOrientation(LinearLayout.VERTICAL);
            mainTextView.setText(title.subSequence(0, index));
            subTextView.setText(title.subSequence(index + 1, title.length()));
        } else {
            index = title.toString().indexOf("\t");
            if (index > 0) {
                contentLayout.setOrientation(LinearLayout.HORIZONTAL);
                mainTextView.setText(title.subSequence(0, index));
                subTextView.setText(title.subSequence(index + 1, title.length()));
            } else {
                mainTextView.setText(title);
                subTextView.setVisibility(View.GONE);
            }
        }
        requestLayout();
        return this;
    }

    public HandyTitleBar setMainText(String mainText) {
        this.mainText = mainText;
        mainTextView.setVisibility(VISIBLE);
        mainTextView.setText(mainText);
        requestLayout();
        return this;
    }

    public HandyTitleBar setMainTextSize(float mainTextSize) {
        if (mainTextSize >= 0) {
            this.mainTextSize = HandyTitleBarUtils.spTopx(mainTextSize);
            mainTextView.setVisibility(VISIBLE);
            mainTextView.setTextSize(mainTextSize);
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setMainTextColor(@ColorInt int mainTextColor) {
        try {
            mainTextView.setVisibility(VISIBLE);
            mainTextView.setTextColor(mainTextColor);
            this.mainTextColor = mainTextColor;
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public HandyTitleBar setMainTextBackgroundColor(@DrawableRes int mainTextBackgroundColor) {
        try {
            mainTextView.setVisibility(VISIBLE);
            mainTextView.setBackgroundResource(mainTextBackgroundColor);
            this.mainTextBackgroundColor = mainTextBackgroundColor;
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public HandyTitleBar setSubText(String subText) {
        this.subText = subText;
        subTextView.setVisibility(VISIBLE);
        subTextView.setText(subText);
        requestLayout();
        return this;
    }

    public HandyTitleBar setSubTextSize(float subTextSize) {
        if (subTextSize >= 0) {
            this.subTextSize = HandyTitleBarUtils.spTopx(subTextSize);
            subTextView.setVisibility(VISIBLE);
            subTextView.setTextSize(subTextSize);
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setSubTextColor(@ColorInt int subTextColor) {
        try {
            subTextView.setVisibility(VISIBLE);
            subTextView.setTextColor(subTextColor);
            this.subTextColor = subTextColor;
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public HandyTitleBar setSubTextBackgroundColor(@DrawableRes int subTextBackgroundColor) {
        try {
            subTextView.setVisibility(VISIBLE);
            subTextView.setBackgroundResource(subTextBackgroundColor);
            this.subTextBackgroundColor = subTextBackgroundColor;
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 移除左右侧容器内所有的动作按钮
     */
    public void removeAllActions() {
        leftActionsLayout.removeAllViews();
        rightActionsLayout.removeAllViews();
    }

    /**
     * 在左侧容器内新增单个动作按钮
     */
    public HandyTitleBar addLeftAction(Action action) {
        final int index = leftActionsLayout.getChildCount();
        return addLeftAction(action, index);
    }

    /**
     * 在左侧容器内指定的位置新增动作按钮
     */
    public HandyTitleBar addLeftAction(Action action, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        View view = inflateAction(action);
        action.actionView = view;
        leftActionsLayout.addView(view, index, params);
        return this;
    }

    /**
     * 移除左侧容器内全部的动作按钮
     */
    public void removeLeftActions() {
        leftActionsLayout.removeAllViews();
    }

    /**
     * 移除左侧容器内指定位置的动作按钮
     */
    public void removeLeftAction(int index) {//
        leftActionsLayout.removeViewAt(index);
    }

    /**
     * 在右侧容器内新增单个动作按钮
     */
    public HandyTitleBar addRightAction(Action action) {
        final int index = rightActionsLayout.getChildCount();
        return addRightAction(action, index);
    }

    /**
     * 在右侧容器内指定的位置新增动作按钮
     */
    public HandyTitleBar addRightAction(Action action, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        View view = inflateAction(action);
        action.actionView = view;
        rightActionsLayout.addView(view, index, params);
        return this;
    }

    /**
     * 移除右侧容器内全部的动作按钮
     */
    public void removeRightActions() {
        rightActionsLayout.removeAllViews();
    }

    /**
     * 移除右侧容器内指定位置的动作按钮
     */
    public void removeRightAction(int index) {
        rightActionsLayout.removeViewAt(index);
    }

    public HandyTitleBar setBottomLineHeight(int bottomLineHeight) {
        if (bottomLineHeight >= 0) {
            this.bottomLineHeight = bottomLineHeight;
            requestLayout();
        }
        return this;
    }

    public HandyTitleBar setBottomLineColor(@DrawableRes int bottomLineColor) {
        try {
            bottomLineView.setBackgroundResource(bottomLineColor);
            this.bottomLineColor = bottomLineColor;
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /*============================== 其他方法及内部类 ==============================*/

    /**
     * 从View内部获取到Activity的实例
     */
    private Activity getActivity() {
        Context context = getContext();
        for (int i = 0; i < 10; i++) {
            if (context == null) {
                return null;
            } else if (context instanceof Activity) {
                return (Activity) context;
            } else {
                try {
                    context = ((ContextWrapper) context).getBaseContext();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取状态栏高度高度
     */
    @SuppressLint("PrivateApi")
    private int getStatusBarHeight(Context context) {
        try {
            Object obj = Class.forName("com.android.internal.R$dimen").newInstance();
            Field field = Class.forName("com.android.internal.R$dimen").getField("status_bar_height");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(field.get(obj).toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 初始化动作按钮控件布局
     */
    private View inflateAction(Action action) {
        LinearLayout view = new LinearLayout(getContext());
        view.setGravity(Gravity.CENTER_VERTICAL);
        view.setOrientation(LinearLayout.HORIZONTAL);
        view.setPadding(actionViewPadding, 0, actionViewPadding, 0);
        view.setTag(action);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Object tag = v.getTag();
                if (tag instanceof Action) {
                    final Action action = (Action) tag;
                    action.onClick();
                }
            }
        });

        //若图片设置不为空，添加动作按钮的图片
        if (action.actionImageSrc != 0) {
            ImageView img = new ImageView(getContext());
            LayoutParams imglp = new LayoutParams(action.actionImageSize == 0 ? actionImageSize : action.actionImageSize, action.actionImageSize == 0 ? actionImageSize : action.actionImageSize);
            img.setLayoutParams(imglp);

            if (action.imagePressType == 0) {
                img.setImageResource(action.actionImageSrc);

            } else if (action.imagePressType == 1) {
                StateListDrawable stateListDrawable = HandyTitleBarUtils.getStateDrawable(context, action.nImageResId, action.pImageResId, action.pImageResId);
                img.setImageDrawable(stateListDrawable);

            } else if (action.imagePressType == 2) {
                Drawable imageDrawable = getResources().getDrawable(action.actionImageSrc);
                Drawable normalDrawable = action.nImageColorId == 0 ? imageDrawable : HandyTitleBarUtils.tintDrawable(context, action.actionImageSrc, action.nImageColorId);
                Drawable pressDrawable = action.pImageColorId == 0 ? imageDrawable : HandyTitleBarUtils.tintDrawable(context, action.actionImageSrc, action.pImageColorId);
                StateListDrawable stateListDrawable = HandyTitleBarUtils.getStateDrawable(normalDrawable, pressDrawable, pressDrawable);
                img.setImageDrawable(stateListDrawable);
            }

            img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            img.setClickable(false);
            view.addView(img);
            action.imageView = img;
        }

        //若文字设置不为空，添加动作按钮的文字
        if (!TextUtils.isEmpty(action.actionText)) {
            TextView text = new TextView(getContext());
            text.setGravity(Gravity.CENTER);
            text.setText(action.actionText);
            text.setPadding(action.actionImageSrc != 0 ? action.actionTextMarginLeft != -1 ? action.actionTextMarginLeft : actionTextMarginLeft : 0, 0, 0, 0);
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, action.actionTextSize == 0 ? actionTextSize : action.actionTextSize);

            if (action.textPressType == 0) {
                text.setTextColor(actionTextColor);

            } else if (action.textPressType == 1) {
                text.setTextColor(action.nTextColorId == 0 ? actionTextColor : getResources().getColor(action.nTextColorId));

            } else if (action.textPressType == 2) {
                int normalColor = action.nTextColorId == 0 ? actionTextColor : action.nTextColorId;
                int pressColor = action.pTextColorId == 0 ? actionTextColor : action.pTextColorId;
                text.setTextColor(HandyTitleBarUtils.getStateColor(context, normalColor, pressColor, pressColor));
            }

            text.setClickable(false);
            view.addView(text);
            action.textView = text;
        }
        return view;
    }
}
