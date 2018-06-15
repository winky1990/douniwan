package com.winky.expand.basics;

import android.view.View;
import android.view.ViewGroup;

import com.winky.expand.skin.SkinInflaterFactory;


public abstract class BaseSkinFragment extends BaseFragment {

    public final SkinInflaterFactory getSkinInflaterFactory() {
        if (getActivity() instanceof BaseSkinActivity) {
            return ((BaseSkinActivity) getActivity()).getInflaterFactory();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        removeAllView(getView());
        super.onDestroyView();
    }

    protected void removeAllView(View v) {
        if (getSkinInflaterFactory() != null) {
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    removeAllView(viewGroup.getChildAt(i));
                }
                getSkinInflaterFactory().removeSkinView(v);
            } else {
                getSkinInflaterFactory().removeSkinView(v);
            }
        }
    }
}
