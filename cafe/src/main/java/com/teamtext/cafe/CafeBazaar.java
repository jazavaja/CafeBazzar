package com.teamtext.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.teamtext.cafe.util.IabHelper;
import com.teamtext.cafe.util.IabResult;
import com.teamtext.cafe.util.Inventory;
import com.teamtext.cafe.util.Purchase;

import java.util.List;

public class CafeBazaar implements Parcelable {

    private Activity activity;
    private boolean isSetup = false;
    private IabHelper iabHelper;

    public CafeBazaar(Activity activity, String base64) {
        this.activity = activity;
        iabHelper = new IabHelper(activity, base64);
        try {
            setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CafeBazaar(Activity activity, String base64, CafeBazaarInterface cafeBazaarInterface) {
        this.activity = activity;
        activity = new Activity();
        iabHelper = new IabHelper(activity, base64);
        try {
            setup();
        } catch (Exception e) {
            cafeBazaarInterface.ErrorSetupIabHelper(e);
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

    private void setup() throws Exception {
        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d("TAG", "Problem setting up CafeBazaar: " + result);
                } else {
                    Log.d("TAG", "Setup finished CafeBazaar");
                    isSetup = true;
                }
                // Hooray, IAB is fully set up!

            }
        });
    }

    public void OnDestroyCafeBazaar() {
        if (iabHelper != null)
            iabHelper.dispose();
        iabHelper = null;

    }

    public void getDetailProduct(boolean showDetail, List<String> products, final OnGetProductDetailFinished detailFinished) {
        iabHelper.queryInventoryAsync(showDetail, products, new IabHelper.QueryInventoryFinishedListener() {
            @Override
            public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                detailFinished.OnGetProductFinished(result, inv);
            }
        });
    }

    public void launchForPayWithErrorListener(Activity activity, String s, int requestCode, IabHelper.OnIabPurchaseFinishedListener listener) {
        if (isSetup) {

            iabHelper.launchPurchaseFlow(activity, s, requestCode, listener, "developer-payload");
        }

    }

    public void launchForPayWithErrorListener(Activity activity, String s, int requestCode, String extra, IabHelper.OnIabPurchaseFinishedListener listener) {
        if (isSetup) {

            iabHelper.launchPurchaseFlow(activity, s, requestCode, listener, extra);
        }

    }

    public void launchForPay(Activity activity, String s, int requestCode, IabHelper.OnIabPurchaseFinishedListener listener) {
        if (isSetup) {
            iabHelper.launchPurchaseFlow(activity, s, requestCode, listener, "developer-payload");
        } else
            Log.e("CafeBazaarError", "NotSetup");
    }

    public void launchForPay(Activity activity, String s, int requestCode, IabHelper.OnIabPurchaseFinishedListener listener, String extra) {
        if (isSetup) {
            iabHelper.launchPurchaseFlow(activity, s, requestCode, listener, extra);
        } else
            Log.e("CafeBazaarError", "NotSetup");
    }

    public void onActivityResultCafeBazaar(int requestCode, int resultCode, Intent data, OnResult onresult) {
        if (!iabHelper.handleActivityResult(requestCode, resultCode, data)) {
            onresult.OnResultCustom();
        } else {
            Log.d("TAG", "onActivityResult handled by IABUtil.");
        }
    }

    public void onActivityResultCafeBazaar(Activity activity, int requestCode, int resultCode, Intent data) {
        if (!iabHelper.handleActivityResult(requestCode, resultCode, data)) {
            Intent intent = new Intent(activity, activity.getClass());
            activity.startActivityForResult(intent, requestCode);
        } else {
            Log.d("TAG", "onActivityResult handled by IABUtil.");
        }
        IabHelper.OnConsumeFinishedListener listener = new IabHelper.OnConsumeFinishedListener() {
            @Override
            public void onConsumeFinished(Purchase purchase, IabResult result) {

            }
        };

    }

    public interface OnConsumeCafeBazaarFinishedListener {
        void OnConsumeFinished(Purchase purchase, IabResult result);
    }

    public interface OnGetProductDetailFinished {
        void OnGetProductFinished(IabResult result, Inventory inventory);
    }

    public void consumeProduct(Purchase purchase, final OnConsumeCafeBazaarFinishedListener listener) {

        iabHelper.consumeAsync(purchase, new IabHelper.OnConsumeFinishedListener() {
            @Override
            public void onConsumeFinished(Purchase purchase, IabResult result) {
                listener.OnConsumeFinished(purchase, result);
            }
        });

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


