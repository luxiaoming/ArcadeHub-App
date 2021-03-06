/*
 * This file is part of MAME4droid.
 *
 * Copyright (C) 2013 David Valdeita (Seleuco)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 * Linking MAME4droid statically or dynamically with other modules is
 * making a combined work based on MAME4droid. Thus, the terms and
 * conditions of the GNU General Public License cover the whole
 * combination.
 *
 * In addition, as a special exception, the copyright holders of MAME4droid
 * give you permission to combine MAME4droid with free software programs
 * or libraries that are released under the GNU LGPL and with code included
 * in the standard release of MAME under the MAME License (or modified
 * versions of such code, with unchanged license). You may copy and
 * distribute such a system following the terms of the GNU GPL for MAME4droid
 * and the licenses of the other code concerned, provided that you include
 * the source code of that other code when and as the GNU GPL requires
 * distribution of source code.
 *
 * Note that people who make modified versions of MAME4idroid are not
 * obligated to grant this special exception for their modified versions; it
 * is their choice whether to do so. The GNU General Public License
 * gives permission to release a modified version without this exception;
 * this exception also makes it possible to release a modified version
 * which carries forward this exception.
 *
 * MAME4droid is dual-licensed: Alternatively, you can license MAME4droid
 * under a MAME license, as set out in http://mamedev.org/
 */

package com.yunluo.android.arcadehub.views;

import java.util.ArrayList;
import com.yunluo.android.arcadehub.Emulator;
import com.yunluo.android.arcadehub.GamePlayActivity;
import com.yunluo.android.arcadehub.helpers.PrefsHelper;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class EmulatorViewSW extends SurfaceView implements Callback, IEmuView{
	

	protected int scaleType = PrefsHelper.PREF_ORIGINAL;
	
	protected GamePlayActivity mm = null;

	public int getScaleType() {
		return scaleType;
	}

	public void setScaleType(int scaleType) {
		this.scaleType = scaleType;
	}

	public void setMAME4droid(GamePlayActivity mm) {
		this.mm = mm;
	}
	
	public EmulatorViewSW(Context context) {
		super(context);
		init();
	}

	public EmulatorViewSW(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public EmulatorViewSW(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();

	}
	
	protected void init(){
		this.getHolder().addCallback(this);		
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		this.requestFocus();
	}
		
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(mm==null)
		{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			return;
		}
		ArrayList<Integer> l = mm.getMainHelper().measureWindow(widthMeasureSpec, heightMeasureSpec, scaleType);
		setMeasuredDimension(l.get(0).intValue(),l.get(1).intValue());
	}
		
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		super.onSizeChanged(w, h, oldw, oldh);
		Emulator.setWindowSize(w, h);		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {	
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Emulator.setHolder(holder);		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Emulator.setHolder(null);		
	}
	
	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		return mm.getInputHandler().onTrackballEvent(event);
	}

}
