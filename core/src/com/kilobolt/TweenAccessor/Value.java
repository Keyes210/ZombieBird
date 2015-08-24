package com.kilobolt.TweenAccessor;

/**
 * Created by Alex on 8/23/2015.
 */
public class Value {
    /*only Objects can be tweened (primitives cannot). So, to tween a float, we must create a class for it.*/
    private float val = 1;

    public float getValue() {
        return val;
    }

    public void setValue(float val) {
        this.val = val;
    }
}
