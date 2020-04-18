package com.teamtext.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.teamtext.cafe.util.IabHelper;
import com.teamtext.cafe.util.IabResult;
import com.teamtext.cafe.util.Purchase;

public class CafeBazzar implements Parcelable {

    private Activity activity;
    private boolean isSetup = false;
    private IabHelper iabHelper;
    private CafeBazzarInterface cafeBazzarInterface;
    public CafeBazzar(Activity activity,String base64) {
        this.activity = activity;
        iabHelper = new IabHelper(activity, base64);
        try {
            setup();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    CafeBazzar(Activity activity, String base64, CafeBazzarInterface cafeBazzarInterface) {
        this.activity = activity;
        this.cafeBazzarInterface=cafeBazzarInterface;
        iabHelper = new IabHelper(activity, base64);
        try {
            setup();
        } catch (Exception e) {
            cafeBazzarInterface.ErrorSetupIabHelper(e);
            e.printStackTrace();
        }
    }

    protected CafeBazzar(Parcel in) {
        isSetup = in.readByte() != 0;
    }

    public static final Creator<CafeBazzar> CREATOR = new Creator<CafeBazzar>() {
        @Override
        public CafeBazzar createFromParcel(Parcel in) {
            return new CafeBazzar(in);
        }

        @Override
        public CafeBazzar[] newArray(int size) {
            return new CafeBazzar[size];
        }
    };

    public void setup() throws Exception {
        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d("TAG", "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d("TAG", "Problem setting up In-app Billing: " + result);
                } else {
                    isSetup = true;
                }
                // Hooray, IAB is fully set up!

            }
        });
    }

    public void destroy() {
        if (iabHelper != null)
            iabHelper.dispose();
        iabHelper = null;
    }

    public void launchWithErrorLaunch(Activity activity,String s, int requestCode, IabHelper.OnIabPurchaseFinishedListener listener,String extra) {
        if (isSetup)
        {
            iabHelper.launchPurchaseFlow(activity, s, requestCode, listener,extra);
        }
        else
            cafeBazzarInterface.ErrorLaunch();
    }
    public void launch(String s, int requestCode, IabHelper.OnIabPurchaseFinishedListener listener,String extra) {
        if (isSetup){
            iabHelper.launchPurchaseFlow(activity, s, requestCode, listener,extra);
        }
        else
            Log.e("CafeBazzarError","NotSetup");
    }



    public void onActivityCafe(int requestCode, int resultCode, Intent data, OnResult onresult){
        if (!iabHelper.handleActivityResult(requestCode, resultCode, data))
        {
            onresult.OnResultCustom();
        } else {
            Log.d("TAG", "onActivityResult handled by IABUtil.");
        }
    }

    public void consumeAsync(Purchase purchase, IabHelper.OnConsumeFinishedListener finishedListener){
        iabHelper.consumeAsync(purchase,finishedListener);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeByte((byte) (isSetup ? 1 : 0));
    }

    public interface OnResult {
        public void OnResultCustom();
    }
}


