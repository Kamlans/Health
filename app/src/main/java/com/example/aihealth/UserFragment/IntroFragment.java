package com.example.aihealth.UserFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aihealth.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


public class IntroFragment extends Fragment {

    private EditText symptoms;
    private Button sendSymptomsBtn;

    public IntroFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        CarouselView carouselView = view.findViewById(R.id.carouselView);

        int[] img = {
          R.drawable.one ,
          R.drawable.two
        };
        symptoms = view.findViewById(R.id.symptomsEditText);
        sendSymptomsBtn = view.findViewById(R.id.sendsymtomsBtn);

        carouselView.setPageCount(img.length);
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(img[position]);
            }
        };

        carouselView.setImageListener(imageListener);




        sendSymptomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(symptoms.getText().toString())){
                    Toast.makeText(getContext(), "empty", Toast.LENGTH_SHORT).show();
                }
                if (!TextUtils.isEmpty(symptoms.getText().toString())){
                    Toast.makeText(getContext(), symptoms.getText().toString() , Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }
}