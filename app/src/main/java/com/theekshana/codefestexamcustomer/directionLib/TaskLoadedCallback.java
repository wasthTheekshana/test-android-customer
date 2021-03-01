package com.theekshana.codefestexamcustomer.directionLib;
import com.theekshana.codefestexamcustomer.POJO.mapDistanceObj;
import com.theekshana.codefestexamcustomer.POJO.mapTimeObj;
public interface TaskLoadedCallback {

        void onTaskDone(Object... values);
        void onDistanceTaskDone(mapDistanceObj distance);
        void onTimeTaskDone(mapTimeObj time);
    }


