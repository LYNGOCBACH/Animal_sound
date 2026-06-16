package com.bach.animalsoundmvvm;

import android.os.AsyncTask;

public final  class MTask extends AsyncTask<Object, Object, Object> {
    private String key;
    private OnCallBack callBack;

    public MTask(String key, OnCallBack callBack) {
        this.key = key;
        this.callBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        callBack.preExec(key);
    }

    @Override
    protected Object doInBackground(Object... params) {
        return callBack.execTask(key, params == null ? null : params[0], this);
    }

    @Override
    protected void onProgressUpdate(Object... data) {
        callBack.updateUI(key, data);
    }

    @Override
    protected void onPostExecute(Object value) {
        callBack.completeTask(key, value);
    }

    public void start(Object data) {
        execute(data);
    }

    public void startAsync(Object data) {
        executeOnExecutor(THREAD_POOL_EXECUTOR, data);
    }

    public void stop() {
        cancel(true);
    }

    public interface OnCallBack {
        default void preExec(String key) {
            // do nothing
        }

        Object execTask(String key, Object params, MTask task);

        default void updateUI(String key, Object data) {
            // do nothing
        }

        default void completeTask(String key, Object data) {
            // do nothing
        }

    }
}
