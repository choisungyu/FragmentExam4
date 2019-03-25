package com.csg.fragmentexam4;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CountDownFragment extends Fragment {

    private OnCountDownFragmentListener mListener;
    private TextView mNumTextView;

    public CountDownFragment() {
    }

    public void setCount(int count) {
        mNumTextView.setText(String.valueOf(count));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_count_down, container, false);
    }

    // view가 만들어 졌으니깐 onViewCreate를 새로 만들어서 view.~~하면서 만들어 나가기
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onStartButtonClicked();
            }
        });

        view.findViewById(R.id.button_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onResetButtonClicked();
            }
        });

        mNumTextView = view.findViewById(R.id.frag_text_number);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCountDownFragmentListener) {
            mListener = (OnCountDownFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCountDownFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCountDownFragmentListener {
        // TODO: Update argument type and name
        void onStartButtonClicked();
        void onResetButtonClicked();
    }
}
