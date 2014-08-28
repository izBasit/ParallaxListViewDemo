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
 * @date 27/8/14 2:14 PM
 * @modified 27/8/14 2:14 PM
 */

package com.mobility.iz.parallaxlistview.customViews;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import uk.co.chrisjenx.paralloid.ParallaxViewController;
import uk.co.chrisjenx.paralloid.Parallaxor;
import uk.co.chrisjenx.paralloid.transform.Transformer;

public class CustomListView extends ListView implements Parallaxor {

	private Context context;
	private CustomListScrollListener mScrollListener = null;
    private ParallaxViewController mParallaxViewController;

    public CustomListView(Context context) {
		super(context);
		
		this.context = context;
        mParallaxViewController = ParallaxViewController.wrap(this);
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	public void setOnScrollListener(CustomListScrollListener onScrollListener) {
	    this.mScrollListener = onScrollListener;
	}
	
	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (mScrollListener != null) {
			mScrollListener.onScrollChanged(x, y, oldx, oldy);
		}
		
	}

    @Override
    public void parallaxViewBy(View view, float multiplier) {
        mParallaxViewController.parallaxViewBy(view, multiplier);
    }

    @Override
    public void parallaxViewBy(View view, Transformer transformer, float multiplier) {
        mParallaxViewController.parallaxViewBy(view, transformer, multiplier);
    }

    @Override
    public void parallaxViewBackgroundBy(View view, Drawable drawable, float multiplier) {
        mParallaxViewController.parallaxViewBackgroundBy(view, drawable, multiplier);
    }

    public interface CustomListScrollListener {
        void onScrollChanged(int x, int y, int oldx, int oldy);
    }

}
