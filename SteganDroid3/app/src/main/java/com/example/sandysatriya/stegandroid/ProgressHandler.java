package com.example.sandysatriya.stegandroid;

/**
 * Created by SandySatriya on 4/19/2017.
 */
public interface ProgressHandler {

    public void setTotal(int tot);
    public void increment(int inc);
    public void finished();
}
