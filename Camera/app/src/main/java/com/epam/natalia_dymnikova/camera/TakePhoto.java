package com.epam.natalia_dymnikova.camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;

import java.io.IOException;

/**
 * Created by Natalia Dymnikova on 03/13/15.
 */
public class TakePhoto extends ActionBarActivity implements View.OnClickListener {
    private static final String IMAGE_URL = "http://zdorovnavek.ru/wp-content/uploads/2011/11/gates1.jpg";
    private static final Integer REQUEST_CAMERA_CODE = 101;
    private final boolean FULL_SCREEN = true;

    private ImageView mPreview;
    private TextView mTakePhotoText;
    private Button mButtonTake;
    private Button mButtonRetake;
    private Button mButtonUse;
    private Button mButtonBack;
    private Button mButtonUseVolley;
    private Button mButtonTakePhoto;
    private Button mButtonFlash;
    private Button mButtonFrontal;
    private RelativeLayout mRelLayout;
    private LinearLayout mLinLayout;
    private GridLayout mLinPhotoButtons;
    private ProgressBar mProgressBar;
    private RelativeLayout mRelativeProgress;

    private ImageLoader mImageLoader;
    private AsyncLoader mAsyncLoader;

    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private HolderCallback mHolderCallback;
    private Camera mCamera;

    private int mCameraID = 0;
    private boolean mIsFlashOn;
    private boolean mIsFrontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPreview = (ImageView) findViewById(R.id.preview);
        mTakePhotoText = (TextView) findViewById(R.id.textView3);
        mRelLayout = (RelativeLayout) findViewById(R.id.rel_take);
        mLinLayout = (LinearLayout) findViewById(R.id.lin_take);
        mLinPhotoButtons = (GridLayout) findViewById(R.id.lin_photo_buttons);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mRelativeProgress = (RelativeLayout) findViewById(R.id.rel_progress);
        mRelativeProgress.setVisibility(View.INVISIBLE);

        mButtonTake = (Button) findViewById(R.id.button_take);
        mButtonTake.setOnClickListener(this);

        mButtonRetake = (Button) findViewById(R.id.button_retake);
        mButtonRetake.setOnClickListener(this);

        mButtonUse = (Button) findViewById(R.id.button_use);
        mButtonUse.setOnClickListener(this);

        mButtonBack = (Button) findViewById(R.id.button2);
        mButtonBack.setOnClickListener(this);

        mButtonTakePhoto = (Button) findViewById(R.id.button_take_picture);
        mButtonTakePhoto.setOnClickListener(this);

        mButtonUseVolley = (Button) findViewById(R.id.button_volley);
        mButtonUseVolley.setOnClickListener(this);

        mButtonFlash = (Button) findViewById(R.id.button_flash);
        mButtonFlash.setOnClickListener(this);

        mButtonFrontal = (Button) findViewById(R.id.button_frontal);
        mButtonFrontal.setOnClickListener(this);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = mSurfaceView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceView.setVisibility(View.INVISIBLE);
        mHolderCallback = new HolderCallback();
        mHolder.addCallback(mHolderCallback);

        mIsFlashOn = false;
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT))
            mButtonFlash.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPreview.getDrawable() != null) {
            stopCamera();
            mPreview.setVisibility(View.VISIBLE);
            mLinLayout.setVisibility(View.VISIBLE);
            mRelLayout.setVisibility(View.INVISIBLE);
            setTitle(R.string.preview);
        } else {
            mPreview.setVisibility(View.INVISIBLE);
            mLinLayout.setVisibility(View.INVISIBLE);
            mRelLayout.setVisibility(View.VISIBLE);
            setTitle(R.string.take_picture);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCamera();
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonUseVolley) {
            stopCamera();
            mImageLoader = new ImageLoader(this);
            mImageLoader.loadImageByUrl(IMAGE_URL, new ImageLoaderDelegate() {
                @Override
                public void finishLoad(Bitmap result) {
                    mPreview.setImageBitmap(result);
                    onResume();
                }
            });
        } else if (v == mButtonTake || v == mButtonRetake
                || (v == mButtonBack && mPreview.getDrawable() != null)) {
            // Camera.
            startCamera();
            if (mPreview.getDrawable() != null)
                mPreview.setImageDrawable(null);
            mCamera.startPreview();
        } else if (v == mButtonUse) {
            // Use picture.
            mRelativeProgress.setVisibility(View.VISIBLE);
            mAsyncLoader = new AsyncLoader();
            mAsyncLoader.startLoad(new AsyncLoaderDelegate() {
                @Override
                public void finishLoad(Object result) {
                    mRelativeProgress.setVisibility(View.INVISIBLE);
                }
            });
        } else if (v == mButtonTakePhoto) {
            // Take new photo.
            mSurfaceView.setVisibility(View.INVISIBLE);
            mCamera.takePicture(null, null, new PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    mPreview.setImageBitmap(bitmap);
                    onResume();
                }
            });
            onResume();
        } else if (v == mButtonFlash) {
            // flash turn on and off.
            if (mCamera != null) {
                Camera.Parameters p = mCamera.getParameters();
                if (mIsFlashOn) {
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mButtonFlash.setText("Flash on");
                } else {
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    mButtonFlash.setText("Flash off");
                }
                mIsFlashOn = !mIsFlashOn;
                mCamera.setParameters(p);
            }
        } else if (v == mButtonFrontal) {
            // frontal and back cameras.
            mSurfaceView.setVisibility(View.INVISIBLE);
            if (!mIsFrontal) {
                mCameraID = 1;
                mButtonFrontal.setText("Back");
            } else {
                mCameraID = 0;
                mButtonFrontal.setText("Frontal");
            }
            mIsFrontal = !mIsFrontal;
            stopCamera();
            startCamera();
            mSurfaceView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mPreview.setImageBitmap(photo);
        }
    }



    /// Callback - start and stop camera.
    class HolderCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            mCamera.stopPreview();
            setCameraDisplayOrientation(mCameraID);
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

    }

    /// Set size of preview.
    void setPreviewSize(boolean fullScreen) {
        // size of display
        Display display = getWindowManager().getDefaultDisplay();

        // camera preview size
        Size size = mCamera.getParameters().getPreviewSize();
        RectF rectDisplay = new RectF();
        RectF rectPreview = new RectF();
        float sizeMin = Math.min(size.width, size.height);
        // RectF экрана, соотвествует размерам экрана
        rectDisplay.set(0, 0, display.getWidth(), display.getWidth());
        rectPreview.set(0, 0, sizeMin, sizeMin);

        Matrix matrix = new Matrix();
        if (!fullScreen) {
            // если превью будет "втиснут" в экран (второй вариант из урока)
            matrix.setRectToRect(rectPreview, rectDisplay,
                    Matrix.ScaleToFit.START);
        } else {
            // если экран будет "втиснут" в превью (третий вариант из урока)
            matrix.setRectToRect(rectDisplay, rectPreview,
                    Matrix.ScaleToFit.START);
            matrix.invert(matrix);
        }
        matrix.mapRect(rectPreview);

        int paramSize = (int) Math.min(rectPreview.bottom, rectPreview.right);
        mSurfaceView.getLayoutParams().height = paramSize;
        mSurfaceView.getLayoutParams().width = paramSize;
    }

    void setCameraDisplayOrientation(int cameraId) {
        // angle of rotate
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result = 0;

        // info in cameraId
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        // back camera
        if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
            result = ((360 - degrees) + info.orientation);
        } else
            // front camera
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                result = ((360 - degrees) - info.orientation);
                result += 360;
            }
        result = result % 360;
        mCamera.setDisplayOrientation(result);
    }

    /// Start camera and make visible layout with buttons.
    private void startCamera() {
        if (mCamera == null) {
            mCamera = Camera.open(mCameraID);
            setPreviewSize(FULL_SCREEN);
            if (mIsFlashOn) {
                Camera.Parameters param = mCamera.getParameters();
                param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(param);
            }
        }
        mLinLayout.setVisibility(View.INVISIBLE);
        mRelLayout.setVisibility(View.INVISIBLE);
        mPreview.setVisibility(View.INVISIBLE);
        mButtonTake.setVisibility(View.INVISIBLE);
        mSurfaceView.setVisibility(View.VISIBLE);
        mLinPhotoButtons.setVisibility(View.VISIBLE);
        mCamera.startPreview();
    }

    /// Stop camera and make invisible layout with buttons.
    private void stopCamera() {
        mLinPhotoButtons.setVisibility(View.INVISIBLE);
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
        }
        mCamera = null;
    }
}
