package com.theme.keyboardthemeapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.theme.keyboardthemeapp.UI.Activities.MainActivity;

public class AdsClass {

    public static com.google.android.gms.ads.interstitial.InterstitialAd interstitial;
    public static MyAppCallback myAppCallback;
    public static NativeAd Ad;
        //banner
        /* if (AdsClass.isInternetOn(context)) {
                AdsClass.showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlBannerAdView), (RelativeLayout) findViewById(R.id.RlBannerAd),Constants.BannerAd,"true");
        }*/

        //native
         /*  if (AdsClass.isInternetOn(context)) {
                AdsClass.showNative250(this, findViewById(R.id.FlNativeAds),"true");
         }*/

        //interstitial
     /*AdsClass.showInter(MainActivity.this, new AdsClass.MyAppCallback() {
            @Override
            public void AppCallback() {

            }
        },"true");*/


    public static void showBanner(Activity activity, AdSize adSize, RelativeLayout RlBannerView, RelativeLayout RlBannerParent, String id, String show) {
        AdView view = new AdView(activity);
        view.setAdSize(adSize);
        view.setAdUnitId(id);
        view.loadAd(new AdRequest.Builder().build());
        view.setAdListener(new AdListener() {

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdClosed() {
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                RlBannerView.setVisibility(View.INVISIBLE);
            }

            @Override

            public void onAdLoaded() {
            }

            @Override
            public void onAdOpened() {
            }
        });
        RlBannerView.addView(view);

        if (show.equalsIgnoreCase("yes")) {
            RlBannerParent.setVisibility(View.VISIBLE);
        } else {
            RlBannerParent.setVisibility(View.INVISIBLE);
        }
    }

    private AdSize getAdSize(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, (int) (((float) displayMetrics.widthPixels) / displayMetrics.density));
    }


    public interface MyAppCallback {
        void AppCallback();

    }

    public void loadInterOne(Activity activity, String id, String show) {
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {

            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        interstitial.load(activity, id, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {

            public void onAdLoaded(InterstitialAd interstitialAd) {
                interstitial = interstitialAd;
                interstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        interstitial = null;
                        loadInterOne(activity, id, show);
                        if (myAppCallback != null) {
                            myAppCallback.AppCallback();
                            myAppCallback = null;
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {

                        interstitial = null;
                        loadInterOne(activity, id, show);
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                interstitial = null;
            }
        });
    }

    public static void showInter(Activity activity, MyAppCallback myAppCallback2, String show) {
        myAppCallback = myAppCallback2;

        if (show.equalsIgnoreCase("yes")) {
            InterstitialAd interstitialAd = interstitial;
            if (interstitialAd != null) {
                interstitialAd.show(activity);
                return;
            }

            MyAppCallback myAppCallback3 = myAppCallback;
            if (myAppCallback3 != null) {
                myAppCallback3.AppCallback();
                myAppCallback = null;
            }
        } else {
            MyAppCallback myAppCallback4 = myAppCallback;
            if (myAppCallback4 != null) {
                myAppCallback4.AppCallback();
                myAppCallback = null;
            }
        }
    }

    public static boolean isInternetOn(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        if (connectivityManager.getNetworkInfo(0).getState() != NetworkInfo.State.DISCONNECTED) {
            connectivityManager.getNetworkInfo(1).getState();
            NetworkInfo.State state = NetworkInfo.State.DISCONNECTED;
        }
        return false;
    }

    public static void alert(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Internet Alert");
        builder.setMessage("You need to internet connection");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public static void LoadNative(final Activity activity, String id) {

        AdLoader.Builder ALertBuilder = new AdLoader.Builder(activity, id);
        ALertBuilder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {

            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                Ad = nativeAd;
            }
        });
        ALertBuilder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
        ALertBuilder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
            }
        }).build().loadAd(new AdRequest.Builder().build());
    }

    public static void showNative250(Activity activity, String id, FrameLayout FlNative, String show) {
        if (Ad != null) {
            NativeAdView nativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.layout_ads_item_big_native_layout, (ViewGroup) null);
            populateUnifiedNativeAdView(Ad, nativeAdView);
            FlNative.removeAllViews();

            if (show.equalsIgnoreCase("yes")) {
                FlNative.setVisibility(View.VISIBLE);
                FlNative.addView(nativeAdView);
            } else {
                FlNative.setVisibility(View.INVISIBLE);
            }

        }
        LoadNative(activity, id);
    }

    @SuppressLint("WrongConstant")
    private static void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.MvAdMedia));
        adView.setHeadlineView(adView.findViewById(R.id.TvAdHeadline));
        adView.setBodyView(adView.findViewById(R.id.TvAdBody));
        Button button = adView.findViewById(R.id.BtnAdCallToAction);
        adView.setCallToActionView(button);
        adView.setIconView(adView.findViewById(R.id.IvAdIcon));
        adView.setPriceView(adView.findViewById(R.id.TvAdPrice));
        adView.setStarRatingView(adView.findViewById(R.id.RbAdStars));
        adView.setStoreView(adView.findViewById(R.id.TvAdStore));
        adView.setAdvertiserView(adView.findViewById(R.id.TvAdAdvertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(4);
        } else {
            adView.getBodyView().setVisibility(0);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(4);
        } else {
            adView.getCallToActionView().setVisibility(0);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(8);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(0);
        }
        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(4);
        } else {
            adView.getPriceView().setVisibility(0);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(4);
        } else {
            adView.getStoreView().setVisibility(0);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(4);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(0);
        }
        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(4);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(0);
        }
        adView.setNativeAd(nativeAd);
    }
}
