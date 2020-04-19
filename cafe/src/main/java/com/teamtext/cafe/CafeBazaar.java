package com.teamtext.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.teamtext.cafe.util.IabHelper;
import com.teamtext.cafe.util.IabResult;
import com.teamtext.cafe.util.Purchase;

public class CafeBazaar implements Parcelable {

    private Activity activity;
    private boolean isSetup = false;
    private IabHelper iabHelper;
    private CafeBazzarInterface cafeBazzarInterface;
    public CafeBazaar(Activity activity, String base64) {
        this.activity = activity;
        iabHelper = new IabHelper(activity, base64);
        try {
            setup();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public CafeBazaar(Activity activity, String base64, CafeBazzarInterface cafeBazzarInterface) {
        this.activity = activity;
        this.cafeBazzarInterface=cafeBazzarInterface;
        activity=new Activity();
        iabHelper = new IabHelper(activity, base64);
        try {
            setup();
        } catch (Exception e) {
            cafeBazzarInterface.ErrorSetupIabHelper(e);
            e.printStackTrace();
        }
    }

    private CafeBazaar(Parcel in) {
        isSetup = in.readByte() != 0;
    }

    public static final Creator<CafeBazaar> CREATOR = new Creator<CafeBazaar>() {
        @Override
        public CafeBazaar createFromParcel(Parcel in) {
            return new CafeBazaar(in);
        }

        @Override
        public CafeBazaar[] newArray(int size) {
            return new CafeBazaar[size];
        }
    };

    private void setup() throws Exception
    {
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

    public void launchForPayWithErrorListener(Activity activity, String s, int requestCode, IabHelper.OnIabPurchaseFinishedListener listener) {
        if (isSetup)
        {

            iabHelper.launchPurchaseFlow(activity, s, requestCode, listener,"developer-payload");
        }
        else
            cafeBazzarInterface.ErrorLaunch();
    }
    public void launchForPay(String s, int requestCode, IabHelper.OnIabPurchaseFinishedListener listener) {
        if (isSetup){
            iabHelper.launchPurchaseFlow(activity, s, requestCode, listener,"developer-payload");
        }
        else
            Log.e("CafeBazaarError","NotSetup");
    }



    public void onActivityResultCafeBazaar(int requestCode, int resultCode, Intent data, OnResult onresult){
        if (!iabHelper.handleActivityResult(requestCode, resultCode, data))
        {
            onresult.OnResultCustom();
        } else {
            Log.d("TAG", "onActivityResult handled by IABUtil.");
        }
    }
    public void onActivityResultCafeBazaar(Activity activity,int requestCode, int resultCode, Intent data){
        if (!iabHelper.handleActivityResult(requestCode, resultCode, data))
        {
            Intent intent=new Intent(activity,activity.getClass());
            activity.startActivityForResult(intent,requestCode);
        } else {
            Log.d("TAG", "onActivityResult handled by IABUtil.");
        }
    }

    public void consumeProduct(Purchase purchase, IabHelper.OnConsumeFinishedListener finishedListener){
        iabHelper.consumeAsync(purchase,finishedListener);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeByte((byte) (isSetup ? 1 : 0));
    }

    public interface OnResult {
        public void OnResultCustom();
    }
}


