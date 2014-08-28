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
 * @date 27/8/14 1:59 PM
 * @modified 27/8/14 1:59 PM
 */

package com.mobility.iz.parallaxlistview;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.mobility.iz.parallaxlistview.customViews.CustomListView;
import com.nirhart.parallaxscroll.views.ParallaxListView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ParallaxListViewActivity extends ActionBarActivity implements CustomListView.CustomListScrollListener {

    private Drawable mActionBarBackgroundDrawable;

    @InjectView(R.id.lvCheeses)
    ParallaxListView lvCheeses;

    private ImageView ivHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax_list_view);
        ButterKnife.inject(this);

        ivHeader = new ImageView(this);
        ivHeader.setImageResource(R.drawable.bg);
        ivHeader.setScaleType(ImageView.ScaleType.CENTER_CROP);

        lvCheeses.addParallaxedHeaderView(ivHeader);
        lvCheeses.setAdapter(new ArrayAdapter<String>(this, R.layout.my_text_view,Cheeses.CHEESES));

        mActionBarBackgroundDrawable = getResources().getDrawable(R.drawable.ab_stacked_solid_ired);
        mActionBarBackgroundDrawable.setAlpha(0);

        getSupportActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mActionBarBackgroundDrawable.setCallback(mDrawableCallback);
        } else {
            getActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parallax_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onScrollChanged(int x, int y, int oldx, int oldy) {
        final int headerHeight = ivHeader.getHeight() - getActionBar().getHeight();
        final float ratio = (float) Math.min(Math.max(y, 0), headerHeight) / headerHeight;
        final int newAlpha = (int) (ratio * 255);
        System.out.println("Alpha "+newAlpha);

        mActionBarBackgroundDrawable.setAlpha(newAlpha);
    }
}
