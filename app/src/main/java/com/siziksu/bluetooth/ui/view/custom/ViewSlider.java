package com.siziksu.bluetooth.ui.view.custom;

import android.view.View;

import com.siziksu.bluetooth.common.utils.MetricsUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

public final class ViewSlider implements ViewSliderContract {

    private static final int NOT_MOVING = -1;
    private static final int LEFT = 0;
    private static final int CENTER = 1;
    private static final int RIGHT = 2;

    private View leftView;
    private View centerView;
    private View rightView;
    private int position = LEFT;
    private int moving = NOT_MOVING;

    private Disposable[] disposables = new Disposable[3];

    private final MetricsUtils metricsUtils;

    public ViewSlider(View left, View center, View right, MetricsUtils metricsUtils) {
        this.rightView = right;
        this.leftView = left;
        this.centerView = center;
        this.metricsUtils = metricsUtils;
        centerView.setTranslationX(metricsUtils.getWidth());
        rightView.setTranslationX(metricsUtils.getWidth() * 2);
    }

    @Override
    public void showLeftView() {
        if (moving == NOT_MOVING) {
            position = LEFT;
            moving = LEFT;
            List<Completable> completableList = new ArrayList<>();
            completableList.add(Completable.create(emitter -> leftView.animate().translationX(0).withEndAction(emitter::onComplete)));
            completableList.add(Completable.create(emitter -> centerView.animate().translationX(metricsUtils.getWidth()).withEndAction(emitter::onComplete)));
            completableList.add(Completable.create(emitter -> rightView.animate().translationX(metricsUtils.getWidth() * 2).withEndAction(emitter::onComplete)));
            clearDisposable(0);
            disposables[0] = Completable.merge(completableList).subscribe(() -> moving = NOT_MOVING);
        }
    }

    @Override
    public void showCenterView() {
        if (moving == NOT_MOVING) {
            position = CENTER;
            moving = CENTER;
            List<Completable> completableList = new ArrayList<>();
            completableList.add(Completable.create(emitter -> leftView.animate().translationX(-metricsUtils.getWidth()).withEndAction(emitter::onComplete)));
            completableList.add(Completable.create(emitter -> centerView.animate().translationX(0).withEndAction(emitter::onComplete)));
            completableList.add(Completable.create(emitter -> rightView.animate().translationX(metricsUtils.getWidth()).withEndAction(emitter::onComplete)));
            clearDisposable(1);
            disposables[1] = Completable.merge(completableList).subscribe(() -> moving = NOT_MOVING);
        }
    }

    @Override
    public void showRightView() {
        if (moving == NOT_MOVING) {
            position = RIGHT;
            moving = RIGHT;
            List<Completable> completableList = new ArrayList<>();
            completableList.add(Completable.create(emitter -> leftView.animate().translationX(-metricsUtils.getWidth() * 2).withEndAction(emitter::onComplete)));
            completableList.add(Completable.create(emitter -> centerView.animate().translationX(-metricsUtils.getWidth()).withEndAction(emitter::onComplete)));
            completableList.add(Completable.create(emitter -> rightView.animate().translationX(0).withEndAction(emitter::onComplete)));
            clearDisposable(2);
            disposables[2] = Completable.merge(completableList).subscribe(() -> moving = NOT_MOVING);
        }
    }

    @Override
    public void onConfigurationChanged() {
        if (position == LEFT) {
            showLeftView();
        } else if (position == CENTER) {
            showCenterView();
        } else if (position == RIGHT) {
            showRightView();
        }
    }

    @Override
    public boolean onBackAvailable() {
        return position != LEFT;
    }

    @Override
    public int getViewId() {
        if (position == CENTER) {
            return centerView.getId();
        } else if (position == RIGHT) {
            return rightView.getId();
        } else {
            return leftView.getId();
        }
    }

    private void clearDisposable(int index) {
        if (disposables[index] != null) {
            disposables[index].dispose();
            disposables[index] = null;
        }
    }

    @Override
    public void onDestroy() {
        clearDisposable(0);
        clearDisposable(1);
        disposables = null;
    }
}
