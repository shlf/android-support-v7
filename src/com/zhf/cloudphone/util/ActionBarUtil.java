
package com.zhf.cloudphone.util;

import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.appcompat.R;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

public class ActionBarUtil {

    public static class ActionBarContains {
        private String title;
        private int iconId;

        private ClickListener homeListener;
        private ClickListener titleListener;
        private ClickListener moreListener;

        public ActionBarContains() {
        }

        public ActionBarContains(String title, int iconId) {
            super();
            this.title = title;
            this.iconId = iconId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

        public ClickListener getHomeListener() {
            return homeListener;
        }

        public void setHomeListener(ClickListener listener) {
            this.homeListener = listener;
        }

        public ClickListener getTitleListener() {
            return titleListener;
        }

        public void setTitleListener(ClickListener titleListener) {
            this.titleListener = titleListener;
        }

        public ClickListener getMoreListener() {
            return moreListener;
        }

        public void setMoreListener(ClickListener moreListener) {
            this.moreListener = moreListener;
        }
    }

    public static interface ClickListener {
        public void clickActionBarItem();
    }

    public static void setActionBar(ActionBarActivity activity, ActionBarContains homeC,
            ActionBarContains titleC, ActionBarContains moreC) {
        ActionBar actionBar = activity.getSupportActionBar();

        final ActionBarContains homeContain = homeC;
        final ActionBarContains titleContain = titleC;
        final ActionBarContains moreContain = moreC;

        boolean showHomeSpace = false;
        ViewGroup homeSpace = (ViewGroup) actionBar.getCustomView().findViewById(R.id.home_space);
        if (null == homeContain) {
            homeSpace.setVisibility(View.INVISIBLE);
        } else {
            boolean showHomeTitle = false;
            TextView homeTitle = (TextView) actionBar.getCustomView().findViewById(R.id.home_title);
            if (TextUtils.isEmpty(homeContain.getTitle())) {
                homeTitle.setVisibility(View.INVISIBLE);
            } else {
                showHomeSpace = true;
                showHomeTitle = true;

                homeTitle.setVisibility(View.VISIBLE);
                homeTitle.setText(homeContain.getTitle());
            }

            boolean showHomeIcon = false;
            ImageView home = (ImageView) actionBar.getCustomView().findViewById(R.id.home);
            if (homeContain.getIconId() <= 0) {
                home.setVisibility(View.INVISIBLE);
            } else {
                showHomeSpace = true;
                showHomeIcon = true;

                home.setVisibility(View.VISIBLE);
                home.setImageResource(homeContain.getIconId());
            }

            if (showHomeTitle && !showHomeIcon) {
                home.setVisibility(View.GONE);
            } else if (!showHomeTitle && showHomeIcon) {
                homeTitle.setVisibility(View.GONE);
            }

            if (showHomeSpace) {
                homeSpace.setVisibility(View.VISIBLE);

                if (null != homeContain.getHomeListener()) {
                    homeSpace.setClickable(true);
                    homeSpace.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeContain.getHomeListener().clickActionBarItem();
                        }
                    });
                }
            } else {
                homeSpace.setVisibility(View.INVISIBLE);
            }
        }

        boolean showTitle = false;
        TextView title = (TextView) actionBar.getCustomView().findViewById(R.id.title);
        if (null == titleContain) {
            title.setVisibility(View.INVISIBLE);
        } else {
            if (TextUtils.isEmpty(titleContain.getTitle())) {
                title.setVisibility(View.INVISIBLE);
            } else {
                showTitle = true;
                title.setVisibility(View.VISIBLE);
                title.setText(titleContain.getTitle());

                if (null != titleContain.getHomeListener()) {
                    title.setClickable(true);
                    title.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            titleContain.getTitleListener().clickActionBarItem();
                        }
                    });
                }
            }
        }

        boolean showMoreSpace = false;
        ViewGroup moreSpace = (ViewGroup) actionBar.getCustomView().findViewById(R.id.more_space);
        if (null == moreContain) {
            moreSpace.setVisibility(View.INVISIBLE);
        } else {
            boolean showMoreTitle = false;
            TextView moreTitle = (TextView) actionBar.getCustomView().findViewById(R.id.more_title);
            if (TextUtils.isEmpty(moreContain.getTitle())) {
                moreTitle.setVisibility(View.INVISIBLE);
            } else {
                showMoreSpace = true;
                showMoreTitle = true;

                moreTitle.setVisibility(View.VISIBLE);
                moreTitle.setText(moreContain.getTitle());
            }

            boolean showMoreIcon = false;
            ImageView more = (ImageView) actionBar.getCustomView().findViewById(R.id.more);
            if (moreContain.getIconId() <= 0) {
                more.setVisibility(View.INVISIBLE);
            } else {
                showMoreSpace = true;
                showMoreIcon = true;

                more.setVisibility(View.VISIBLE);
                more.setImageResource(moreContain.getIconId());
            }

            if (showMoreTitle && !showMoreIcon) {
                more.setVisibility(View.GONE);
            } else if (!showMoreTitle && showMoreIcon) {
                moreTitle.setVisibility(View.GONE);
            }

            if (showMoreSpace) {
                moreSpace.setVisibility(View.VISIBLE);

                if (null != moreContain.getMoreListener()) {
                    moreSpace.setClickable(true);
                    moreSpace.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            moreContain.getMoreListener().clickActionBarItem();
                        }
                    });
                }
            } else {
                moreSpace.setVisibility(View.INVISIBLE);
            }
        }

        if (showHomeSpace || showTitle || showMoreSpace) {
            // actionBar.show();
            setActionBarVisible(activity, true);
        } else {
            // actionBar.hide();
            setActionBarVisible(activity, false);
        }
    }

    // disable actionBar animation at all.
    public static void setActionBarVisible(ActionBarActivity activity, boolean isVisible) {
        View decorView = activity.getWindow().getDecorView();
        int resId;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB || Build.VERSION.SDK_INT >= 21/*
                                                                                                 * Build
                                                                                                 * .
                                                                                                 * VERSION_CODES
                                                                                                 * .
                                                                                                 * LOLLIPOP
                                                                                                 */) {
            resId = activity.getResources().getIdentifier("action_bar_container", "id",
                    activity.getPackageName());
        } else {
            resId = Resources.getSystem().getIdentifier("action_bar_container", "id", "android");
        }

        if (resId != 0) {
            decorView.findViewById(resId).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    // disabling the ActionBar animation by inspecting the source code
    public static void disableActionBarAnimation(ActionBar actionBar) {
        try {
            actionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class)
                    .invoke(actionBar, false);
        } catch (Exception exception) {
            try {
                Field mActionBarField = actionBar.getClass().getSuperclass()
                        .getDeclaredField("mActionBar");
                mActionBarField.setAccessible(true);
                Object icsActionBar = mActionBarField.get(actionBar);
                Field mShowHideAnimationEnabledField = icsActionBar.getClass().getDeclaredField(
                        "mShowHideAnimationEnabled");
                mShowHideAnimationEnabledField.setAccessible(true);
                mShowHideAnimationEnabledField.set(icsActionBar, false);
                Field mCurrentShowAnimField = icsActionBar.getClass().getDeclaredField(
                        "mCurrentShowAnim");
                mCurrentShowAnimField.setAccessible(true);
                mCurrentShowAnimField.set(icsActionBar, null);
                // icsActionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled",
                // boolean.class).invoke(icsActionBar, false);
            } catch (Exception e) {
                // ....
            }
        }
    }
}
