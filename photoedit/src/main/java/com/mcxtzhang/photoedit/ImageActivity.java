package com.mcxtzhang.photoedit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.photoedit.widget.CropDragView;
import com.mcxtzhang.photoedit.widget.CropImageView;

public class ImageActivity extends AppCompatActivity {
    CropImageView mCropImageView;

    CropDragView mCropDragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mCropImageView = findViewById(R.id.zoomImageView);

        mCropDragView = findViewById(R.id.cropView);

        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.long1);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                PhotoCropRotateModel photoCropRotateModel = (PhotoCropRotateModel) getIntent().getSerializableExtra("data");
                mCropImageView.showBitmapInCenter(bitmap, photoCropRotateModel);
                mCropImageView.setCropDragView(mCropDragView);

                mCropDragView.bindCropImageView(mCropImageView, photoCropRotateModel);

                mCropDragView.setCropRate(CropDragView.CROP_RATE_FREE);

                mModeNormal.setSelected(true);
                if (null == photoCropRotateModel) {
                    return;
                }
                float rotate = photoCropRotateModel.rotate;
                if (rotate == 0) {
                } else if (rotate == -90) {
                    //顺时针90度
                    mCropImageView.rotate();
                } else if (rotate == 180) {
                    mCropImageView.rotate();
                    mCropImageView.rotate();
                } else if (rotate == 90) {

                    mCropImageView.rotate();
                    mCropImageView.rotate();
                    mCropImageView.rotate();
                }
            }
        }, 500);

        findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("photo_crop", mCropImageView.crop(mCropDragView.getStartX(), mCropDragView.getStartY(), mCropDragView.getCropWidth(), mCropDragView.getCropHeight()));
                setResult(RESULT_OK, intent);
                finish();

                //mCropImageView.setImageBitmap(mCropImageView.crop(mCropDragView.getStartX(), mCropDragView.getStartY(), mCropDragView.getCropWidth(), mCropDragView.getCropHeight()));
            }
        });


        mModeNormal = findViewById(R.id.tvModeNormal);
        mMode11 = findViewById(R.id.tvMode11);
        mMode34 = findViewById(R.id.tvMode34);
        mMode43 = findViewById(R.id.tvMode43);

        findViewById(R.id.tvModeNormal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    return;
                }
                resetCropRateStatus();
                view.setSelected(true);
                mCropDragView.setCropRate(CropDragView.CROP_RATE_FREE);
            }
        });
        findViewById(R.id.tvMode11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    return;
                }
                resetCropRateStatus();
                view.setSelected(true);
                mCropDragView.setCropRate(CropDragView.CROP_RATE_11);
            }
        });

        findViewById(R.id.tvMode34).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    return;
                }
                resetCropRateStatus();
                view.setSelected(true);
                mCropDragView.setCropRate(CropDragView.CROP_RATE_34);
            }
        });
        findViewById(R.id.tvMode43).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    return;
                }
                resetCropRateStatus();
                view.setSelected(true);
                mCropDragView.setCropRate(CropDragView.CROP_RATE_43);
            }
        });


        findViewById(R.id.tvRotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropImageView.rotate();
                if (mCropDragView.getCropRate() == CropDragView.CROP_RATE_34) {
                    resetCropRateStatus();
                    mMode43.setSelected(true);
                    mCropDragView.setCropRate(CropDragView.CROP_RATE_43);
                } else if (mCropDragView.getCropRate() == CropDragView.CROP_RATE_43) {
                    resetCropRateStatus();
                    mMode34.setSelected(true);
                    mCropDragView.setCropRate(CropDragView.CROP_RATE_34);
                }
            }
        });

        findViewById(R.id.tvRestore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kuan);
                mCropImageView.reset();

                mCropDragView
                        .setCropRate(CropDragView.CROP_RATE_FREE)
                        .initCropAreaPosition();
            }
        });

    }

    private View mModeNormal, mMode11, mMode34, mMode43;

    private void resetCropRateStatus() {
        mModeNormal.setSelected(false);
        mMode11.setSelected(false);
        mMode34.setSelected(false);
        mMode43.setSelected(false);
    }
}
