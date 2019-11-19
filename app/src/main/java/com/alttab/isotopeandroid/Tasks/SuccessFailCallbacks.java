package com.alttab.isotopeandroid.Tasks;

public interface SuccessFailCallbacks {
    public void onSuccess();

    public void onFail();

    public void onPostExecute(boolean isSuccessful);
}
