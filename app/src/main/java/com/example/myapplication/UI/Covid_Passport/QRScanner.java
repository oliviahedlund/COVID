package com.example.myapplication.UI.Covid_Passport;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.myapplication.API.Model.User.UserResponse;
import com.example.myapplication.Helpers.UserAPIHelper;
import com.example.myapplication.R;
import com.example.myapplication.UI.AlertWindow;
import com.example.myapplication.UI.LoadingAnimation;
import com.example.myapplication.UI.UserAppointment.Appointment_Info;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.OnDialogButtonClickListener;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

public class QRScanner extends Fragment {
    private CodeScanner mCodeScanner;
    private UserResponse user;
    private UserAPIHelper userAPIHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_qr_scanner, container, false);
        user = (UserResponse) getActivity().getIntent().getSerializableExtra("userInfo");
        userAPIHelper = new UserAPIHelper(QRScanner.this);

        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userAPIHelper.API_isFullyDosed(user, new Runnable() {
                            @Override
                            public void run() {
                                if(userAPIHelper.isFullyDosed()){
                                    new AlertWindow(QRScanner.this).createAlertWindow(QRScanner.this.getResources().getString(R.string.fullyVaccinated));
                                } else {
                                    new AlertWindow(QRScanner.this).createAlertWindow(QRScanner.this.getResources().getString(R.string.notFullyVaccinated));
                                }
                                LoadingAnimation.dismissLoadingAnimation();
                            }
                        });
                        LoadingAnimation.startLoadingAnimation(QRScanner.this.getActivity());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                if(permission == PackageManager.PERMISSION_GRANTED){
                    mCodeScanner.startPreview();
                } else{
                    handlePermanentDeniedPermission();
                }
            }
        });

        requestCamera();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    public void requestCamera(){
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        mCodeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        if(permissionDeniedResponse.isPermanentlyDenied()){
                            handlePermanentDeniedPermission();
                        } else {
                            CovidPassportFragment covidPassportFragment = new CovidPassportFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, covidPassportFragment).commit();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        showPermissionRationale(permissionToken);
                    }
                }).check();
    }

    public void showPermissionRationale(final PermissionToken token){
        new AlertDialog.Builder(getContext()).setTitle(QRScanner.this.getResources().getString(R.string.cameraPermissionTitle)).
                setMessage(QRScanner.this.getResources().getString(R.string.cameraPermissionMessage)).
                setPositiveButton(QRScanner.this.getResources().getString(R.string.allow), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        token.continuePermissionRequest();
                        dialogInterface.dismiss();
                    }
                }).
                setNegativeButton(QRScanner.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        token.cancelPermissionRequest();
                        dialogInterface.dismiss();
                    }
                }).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        token.cancelPermissionRequest();
                    }
                }).show();
    }

    public void handlePermanentDeniedPermission(){
        new AlertDialog.Builder(getContext()).setTitle(QRScanner.this.getResources().getString(R.string.cameraPermissionTitle)).
                setMessage(QRScanner.this.getResources().getString(R.string.cameraPermissionSettings)).
                setPositiveButton(QRScanner.this.getResources().getString(R.string.goToSettingsButton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openSettings();
                        dialogInterface.dismiss();
                    }
                }).
                setNegativeButton(QRScanner.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        CovidPassportFragment covidPassportFragment = new CovidPassportFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, covidPassportFragment).commit();
                    }
                }).show();
    }

    public void openSettings(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}