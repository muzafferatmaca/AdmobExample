package com.muzafferatmaca.admobexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class OyunActivity extends AppCompatActivity {

    TextView textViewSonuc;
    Button buttonPuanKazan, buttonBolumGec;
    AdView banner;
    InterstitialAd interstitialAd;
    int puan = 10;

    RewardedAd rewardedAd;
    RewardedAdLoadCallback yuklemeListener;
    RewardedAdCallback calismaListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun);

        textViewSonuc = findViewById(R.id.textViewSonuc);
        buttonPuanKazan = findViewById(R.id.buttonPuanKazan);
        buttonBolumGec = findViewById(R.id.buttonBolumGec);
        banner = findViewById(R.id.banner);

        buttonPuanKazan.setVisibility(View.INVISIBLE);


        MobileAds.initialize(OyunActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        bannerKurulum();
        interstitialKurulum();
        rewardKurulum();

        buttonPuanKazan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rewardedAd.isLoaded()) {

                    rewardedAd.show(OyunActivity.this, calismaListener);

                }

            }
        });

        calismaListener = new RewardedAdCallback() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                puan = puan + 20;
                textViewSonuc.setText("Toplam Puan : "+puan);
                buttonPuanKazan.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onRewardedAdClosed() {

                rewardKurulum();

            }
        };

        buttonBolumGec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (puan >= 30) {

                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "puanınız yeterli değil", Toast.LENGTH_LONG).show();

                    buttonPuanKazan.setVisibility(View.VISIBLE);

                }

            }
        });

        interstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                startActivity(new Intent(OyunActivity.this, SonrakiBolumActivity.class));
                finish();

            }
        });
    }

    public void bannerKurulum() {

        banner.loadAd(new AdRequest.Builder().build());

    }

    public void interstitialKurulum() {

        //ca-app-pub-3940256099942544/1033173712

        interstitialAd = new InterstitialAd(getApplicationContext());
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        interstitialAd.loadAd(new AdRequest.Builder().build());

    }

    public void rewardKurulum() {

        //ca-app-pub-3940256099942544/5224354917

        rewardedAd = new RewardedAd(getApplicationContext(), "ca-app-pub-3940256099942544/5224354917");

        yuklemeListener = new RewardedAdLoadCallback() {

            @Override
            public void onRewardedAdLoaded() {


            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError loadAdError) {


            }
        };

        rewardedAd.loadAd(new AdRequest.Builder().build(), yuklemeListener);


    }
}