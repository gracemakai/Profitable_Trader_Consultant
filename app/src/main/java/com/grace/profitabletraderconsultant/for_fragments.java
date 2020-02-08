package com.grace.profitabletraderconsultant;

import android.content.Context;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;

import java.util.List;

public interface for_fragments{

    class forFrag extends ArrayAdapter<User>{
        List<User> user;

        public forFrag(@NonNull Context context, int resource) {
            super(context, resource);
        }
    }
}
