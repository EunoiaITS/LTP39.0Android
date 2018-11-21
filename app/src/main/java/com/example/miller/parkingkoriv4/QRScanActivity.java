package com.example.miller.parkingkoriv4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private ZXingScannerView mScannerView;
    private boolean mAutoFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        mScannerView.setAutoFocus(true);
        mScannerView.setFlash(true);
        setContentView(mScannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        if (result.getText() != null) {
            Intent qrCodeIntent = new Intent();
            qrCodeIntent.putExtra("barcode", result.getText());
            setResult(CommonStatusCodes.SUCCESS, qrCodeIntent);
            Log.v("Code", result.getText());
            Log.v("Code Format", result.getBarcodeFormat().toString());

            /*SharedPreferences qr_data = getSharedPreferences("qr_code", Context.MODE_PRIVATE);
            SharedPreferences.Editor qr_code = qr_data.edit();
            qr_code.putString("qr_detected", result.getText());
            qr_code.apply();*/
            finish();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
