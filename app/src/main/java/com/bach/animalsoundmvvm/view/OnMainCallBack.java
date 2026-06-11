package com.bach.animalsoundmvvm.view;

public interface OnMainCallBack {
    void showFragment(String tag, Object data, Boolean isBack);

    void backToPrevious();
}
