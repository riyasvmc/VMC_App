package com.zeefive.vmcapp;

import android.util.SparseBooleanArray;

import com.zeefive.vmcapp.model.ToDo;

import java.util.List;

/**
 * Created by Riyas V on 9/29/2016.
 */

public interface MySelectableInterface {
    SparseBooleanArray selectedItems = new SparseBooleanArray();
    public abstract void toggleSelection(int pos);
    public abstract void clearSelections();
    public abstract int getSelectedItemCount();
    public abstract Object getSelectedItems();
}
