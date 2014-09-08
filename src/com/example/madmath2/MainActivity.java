package com.example.madmath2;

import java.util.Random;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	FragmentManager mFragmentManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mFragmentManager = getFragmentManager();
        
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.container, new MainFragment());
        transaction.commit();
    }
    
    public int mScore = 0;
    public Random mRandom = new Random();
    public void newMathFragment(){
    	int number1 = mRandom.nextInt(10);
    	int number2 = mRandom.nextInt(10);
    	int result = number1 + number2 + mRandom.nextInt(4);
    	
    	MathFragment fragment = MathFragment.newInstance(mScore, number1, number2, result);
    	fragment.setOnResultListener(mListener);
    	mFragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }
    
    public static interface OnResultListener{
    	public static final int RESULT_OK = 0;
    	public static final int RESULT_NOK = 1;
    	public void onResult(int resultCode);
    }
    OnResultListener mListener = new OnResultListener() {
		
		@Override
		public void onResult(int resultCode) {
			if(resultCode == OnResultListener.RESULT_OK){
				mScore++;
				newMathFragment();
			}else{
				MainFragment fragment = new MainFragment();
				fragment.setScore(mScore);
				FragmentTransaction transaction = mFragmentManager.beginTransaction();
		        transaction.replace(R.id.container, fragment);
		        transaction.commit();
		        
		        
			}
		}
	};
    
    
    public class MainFragment extends Fragment{
    	TextView mScoreTxt;
    	int mMainScore = -1;
    	
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		View root;
    		
    		root = inflater.inflate(R.layout.fragment_main, container, false);
    		mScoreTxt = (TextView)root.findViewById(R.id.score);
    		if(mMainScore >= 0){
    			mScoreTxt.setVisibility(View.VISIBLE);
    			mScoreTxt.setText("Score: "+mMainScore);
    		}
    		ImageView playBtn = (ImageView) root.findViewById(R.id.playBtn);
    		playBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					mScore = 0;
					newMathFragment();
				}
			});
    		
    		return root;
    	}
    	
    	public void setScore(int score){
    		mMainScore = score;
    	}
    }
    
}
