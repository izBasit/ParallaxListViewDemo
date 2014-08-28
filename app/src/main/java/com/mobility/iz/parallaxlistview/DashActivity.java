/*
 * Copyright 2014 Basit Parkar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * @date 27/8/14 11:54 AM
 * @modified 14/8/14 5:44 PM
 */

package com.mobility.iz.parallaxlistview;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.melnykov.fab.FloatingActionButton;
import com.mobility.iz.parallaxlistview.customViews.ObservableScrollView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.paralloid.Parallaxor;


public class DashActivity extends ActionBarActivity implements ObservableScrollView.Callbacks {

    @InjectView(R.id.llStickyHeader)
    LinearLayout llStickyHeader;

    @InjectView(R.id.svObvScrollView)
    ObservableScrollView svObvScrollView;
    @InjectView(R.id.placeholder)
    View mPlaceholderView;
    @InjectView(R.id.button_floating_action)
    FloatingActionButton mFab;
    @InjectView((R.id.image_header))
    ImageView ivHeader;

    @InjectView(R.id.llParallaxHeader)
    LinearLayout llParallaxHeader;

    private Drawable mActionBarBackgroundDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        ButterKnife.inject(this);

        mActionBarBackgroundDrawable = getResources().getDrawable(R.drawable.ab_stacked_solid_ired);
        mActionBarBackgroundDrawable.setAlpha(0);

        getSupportActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mActionBarBackgroundDrawable.setCallback(mDrawableCallback);
        } else {
            getActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);
        }


        svObvScrollView.setCallbacks(this);
        svObvScrollView.getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onScrollChanged(svObvScrollView.getScrollY());
            }
        });

        if (svObvScrollView instanceof Parallaxor) {
            ((Parallaxor) svObvScrollView).parallaxViewBy(llParallaxHeader, 0.5f);
        }

//        mFab.attachToListView(svObvScrollView);
    }


    @Override
    public void onScrollChanged(int scrollY) {
        llStickyHeader.setTranslationY(Math.max(mPlaceholderView.getTop(), scrollY));
    }

    @Override
    public void onScrollChanged(int scrollX, int scrollY, int scrollOldX, int scrollOldY) {
        System.out.println("On Scroll changed!! >.<");
        final int headerHeight = findViewById(R.id.image_header).getHeight() - getActionBar().getHeight();
        final float ratio = (float) Math.min(Math.max(scrollY, 0), headerHeight) / headerHeight;
        final int newAlpha = (int) (ratio * 255);
        System.out.println("Alpha "+newAlpha);

        mActionBarBackgroundDrawable.setAlpha(newAlpha);

    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent() {
        mFab.show();
    }

    @Override
    public void onUpScroll() {
        mFab.hide();
    }

    @Override
    public void onDownScroll() {
        mFab.show();
    }

    private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
        }
    };


}
