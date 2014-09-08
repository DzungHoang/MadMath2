package com.example.madmath2;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.madmath2.MainActivity.OnResultListener;

public class MathFragment extends Fragment {
	private static final String KEY_TOTAL = "total";
	private static final String KEY_NUMBER1 = "number1";
	private static final String KEY_NUMBER2 = "number2";
	private static final String KEY_RESULT = "result";
	
	private MathFragment(){}
	
	public static MathFragment newInstance(int total, int number1, int number2, int result){
		MathFragment fragment = new MathFragment();
		
		Bundle bundle = new Bundle();
		bundle.putInt(KEY_TOTAL, total);
		bundle.putInt(KEY_NUMBER1, number1);
		bundle.putInt(KEY_NUMBER2, number2);
		bundle.putInt(KEY_RESULT, result);
		
		fragment.setArguments(bundle);
		
		return fragment;
	}
	
	CountDownTimer mTimer;
	ProgressBar mProgress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root;
		root = inflater.inflate(R.layout.fragment_math, container, false);
		
		int total, number1, number2, result;
		total = getArguments().getInt(KEY_TOTAL);
		number1 = getArguments().getInt(KEY_NUMBER1);
		number2 = getArguments().getInt(KEY_NUMBER2);
		result = getArguments().getInt(KEY_RESULT);
		//kiem tra: day la 1 phep tinh dung hay sai
		final boolean isRight = ((number1 + number2) == result)?true: false;
		
		TextView totalTxt = (TextView) root.findViewById(R.id.total);
		TextView formulaTxt = (TextView) root.findViewById(R.id.formula);
		TextView resultTxt = (TextView) root.findViewById(R.id.result);
		
		totalTxt.setText("" + total);
		formulaTxt.setText("" + number1 + " + " + number2);
		resultTxt.setText("= " + result);
		
		ImageView rightImg = (ImageView) root.findViewById(R.id.right);
		rightImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(isRight){
					mListener.onResult(OnResultListener.RESULT_OK);
				}else{
					mListener.onResult(OnResultListener.RESULT_NOK);
				}
			}
		});
		ImageView wrongImg = (ImageView) root.findViewById(R.id.wrong);
		wrongImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(isRight){
					mListener.onResult(OnResultListener.RESULT_NOK);
				}else{
					mListener.onResult(OnResultListener.RESULT_OK);	
				}
			}
		});
		
		mProgress = (ProgressBar)root.findViewById(R.id.progressBar);
		mProgress.setMax(100);
		mTimer = new CountDownTimer(1000, 10) {
			
			@Override
			public void onTick(long arg0) {
				mProgress.setProgress((int)arg0/10);
			}
			
			@Override
			public void onFinish() {
				if(mListener!=null){
					mListener.onResult(OnResultListener.RESULT_NOK);
				}
			}
		};
		mTimer.start();
		
		return root;
	}
	OnResultListener mListener;
	public void setOnResultListener(OnResultListener listener){
		mListener = listener;
	}
	@Override
	public void onDetach() {
		mTimer.cancel();
		mTimer = null;
		super.onDetach();
	}
}
