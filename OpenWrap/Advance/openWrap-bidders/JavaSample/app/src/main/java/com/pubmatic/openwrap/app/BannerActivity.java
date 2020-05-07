package com.pubmatic.openwrap.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.pubmatic.sdk.common.OpenWrapSDK;
import com.pubmatic.sdk.common.POBAdSize;
import com.pubmatic.sdk.common.POBError;
import com.pubmatic.sdk.common.models.POBApplicationInfo;
import com.pubmatic.sdk.fanbidder.POBFANBidderConstants;
import com.pubmatic.sdk.openwrap.banner.POBBannerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BannerActivity extends AppCompatActivity {

    private static final String OPENWRAP_AD_UNIT_ID = "OpenWrapBannerAdUnit";
    private static final String PUB_ID = "156276";
    private static final int PROFILE_ID = 1165;

    private static final String appId = "2526468451010379";
    private static final String placementId = "IMG_16_9_APP_INSTALL#2526468451010379_2529643057359585";

    private POBBannerView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        // A valid Play Store Url of an Android application is required.
        POBApplicationInfo appInfo = new POBApplicationInfo();
        try {
            appInfo.setStoreURL(new URL("https://play.google.com/store/apps/details?id=com.example.android&hl=en"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // This app information is a global configuration & you
        // Need not set this for every ad request(of any ad type)
        OpenWrapSDK.setApplicationInfo(appInfo);

        // Initialise banner view
        banner = findViewById(R.id.banner);
        banner.init(PUB_ID,
                PROFILE_ID,
                OPENWRAP_AD_UNIT_ID, POBAdSize.BANNER_SIZE_320x50);

        //optional listener to listen banner events
        banner.setListener(new BannerActivity.POBBannerViewListener());
        banner.getAdRequest().enableTestMode(true);

        //Add facebook ad details like app id, placementid and size.
        Map<String, Object> slotInfo = new HashMap<>();
        slotInfo.put(POBFANBidderConstants.POB_BIDDER_KEY_FB_APP_ID, appId);
        slotInfo.put(POBFANBidderConstants.POB_BIDDER_KEY_FB_PLACEMENT_ID, placementId);

        //Pass required POBAdsize instance
        slotInfo.put(POBFANBidderConstants.POB_BIDDER_KEY_FB_BANNER_AD_SIZE, POBAdSize.BANNER_SIZE_320x50);

        // Pass slot info to banner instance
        banner.addBidderSlotInfo(POBFANBidderConstants.POB_FAN_BIDDER_ID_FAN, slotInfo);

        // Call loadAd() on banner instance
        banner.loadAd();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Destroy banner before destroying activity
        if (null != banner) {
            banner.destroy();
        }
    }


    class POBBannerViewListener extends POBBannerView.POBBannerViewListener {
        private final String TAG = "POBBannerViewListener";

        // Callback method Notifies that an ad has been successfully loaded and rendered.
        @Override
        public void onAdReceived(POBBannerView view) {
            Log.d(TAG, "Ad Received");
        }

        // Callback method Notifies an error encountered while loading or rendering an ad.
        @Override
        public void onAdFailed(POBBannerView view, POBError error) {
            Log.e(TAG, error.toString());
        }

        // Callback method Notifies that the banner ad view will launch a dialog on top of the current view
        @Override
        public void onAdOpened(POBBannerView view) {
            Log.d(TAG, "Ad Opened");
        }

        // Callback method Notifies that the banner ad view has dismissed the modal on top of the current view
        @Override
        public void onAdClosed(POBBannerView view) {
            Log.d(TAG, "Ad Closed");
        }

        @Override
        public void onAppLeaving(POBBannerView view) {
            // Implement your custom logic
            Log.d(TAG, "Banner : App Leaving");
        }

    }
}
