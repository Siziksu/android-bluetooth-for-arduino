package com.siziksu.bluetooth.ui.common;

import android.view.View;

import com.siziksu.bluetooth.common.utils.MetricsUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

public final class AnimationHelper {

    private View leftView;
    private View rightView;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean normal = true;
    
    private Disposable[] disposables = new Disposable[2];

    private final MetricsUtils metricsUtils;

    public AnimationHelper(View left, View right, MetricsUtils metricsUtils) {
        this.rightView = right;
        this.leftView = left;
        this.metricsUtils = metricsUtils;
        rightView.setTranslationX(metricsUtils.getWidth());
    }

    public void animateToRight() {
        if (!movingLeft) {
            normal = true;
            movingRight = false;
            movingLeft = true;
            List<Completable> completableList = new ArrayList<>();
            completableList.add(Completable.create(emitter -> leftView.animate().translationX(-metricsUtils.getWidth()).withEndAction(emitter::onComplete)));
            completableList.add(Completable.create(emitter -> rightView.animate().translationX(0).withEndAction(emitter::onComplete)));
            clearDisposable(0);
            disposables[0] = Completable.merge(completableList).subscribe(() -> movingLeft = false);
        }
    }

    public void animateToLeft() {
        if (!movingRight) {
            normal = false;
            movingLeft = false;
            movingRight = true;
            List<Completable> completableList = new ArrayList<>();
            completableList.add(Completable.create(emitter -> leftView.animate().translationX(0).withEndAction(emitter::onComplete)));
            completableList.add(Completable.create(emitter -> rightView.animate().translationX(metricsUtils.getWidth()).withEndAction(emitter::onComplete)));
            clearDisposable(1);
            disposables[1] = Completable.merge(completableList).subscribe(() -> movingRight = false);
        }
    }

    public void onConfigurationChanged() {
        if (normal) {
            animateToRight();
        } else {
            animateToLeft();
        }
    }

    private void clearDisposable(int index) {
        if (disposables[index] != null) {
            disposables[index].dispose();
            disposables[index] = null;
        }
    }

    public void onDestroy() {
        clearDisposable(0);
        clearDisposable(1);
        disposables = null;
    }
}
