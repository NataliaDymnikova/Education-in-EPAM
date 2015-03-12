package com.epam.natalia_dymnikova.camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 *
 */
public class TakePhoto extends ActionBarActivity implements View.OnClickListener {
    private static final String IMAGE_URL = "http://zdorovnavek.ru/wp-content/uploads/2011/11/gates1.jpg";
    private static final Integer REQUEST_CAMERA_CODE = 101;

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
    private LinearLayout mLinPhotoButtons;
    private ProgressBar mProgressBar;
    private RelativeLayout mRelativeProgress;

    private ImageLoader mImageLoader;
    private AsyncLoader mAsyncLoader;

    private SurfaceView sv;
    private SurfaceHolder holder;
    private HolderCallback holderCallback;
    private Camera camera;

    private int CAMERA_ID = 0;
    private final boolean FULL_SCREEN = true;

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
        mLinPhotoButtons = (LinearLayout) findViewById(R.id.lin_photo_buttons);
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

        mLinPhotoButtons.setVisibility(View.INVISIBLE);

        sv = (SurfaceView) findViewById(R.id.surfaceView);
        holder = sv.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        sv.setVisibility(View.INVISIBLE);
        holderCallback = new HolderCallback();
        holder.addCallback(holderCallback);

        mIsFlashOn = false;
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT))
            mButtonFlash.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPreview.getDrawable() != null) {
            mPreview.setVisibility(View.VISIBLE);
            mLinLayout.setVisibility(View.VISIBLE);
            mRelLayout.setVisibility(View.INVISIBLE);
            mLinPhotoButtons.setVisibility(View.INVISIBLE);
            stopCamera();
            setTitle(R.string.preview);
        } else {
            mPreview.setVisibility(View.INVISIBLE);
            mLinLayout.setVisibility(View.INVISIBLE);
            mRelLayout.setVisibility(View.VISIBLE);
            mLinPhotoButtons.setVisibility(View.INVISIBLE);
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
            startCamera();
            if (mPreview.getDrawable() != null)
                mPreview.setImageDrawable(null);
            camera.startPreview();
            mLinLayout.setVisibility(View.INVISIBLE);
            mRelLayout.setVisibility(View.INVISIBLE);
            mPreview.setVisibility(View.INVISIBLE);
            mButtonTake.setVisibility(View.INVISIBLE);
            sv.setVisibility(View.VISIBLE);
            mLinPhotoButtons.setVisibility(View.VISIBLE);
        } else if (v == mButtonUse) {
            mRelativeProgress.setVisibility(View.VISIBLE);
            mAsyncLoader = new AsyncLoader();
            mAsyncLoader.startLoad(new AsyncLoaderDelegate() {
                @Override
                public void finishLoad(Object result) {
                    mRelativeProgress.setVisibility(View.INVISIBLE);
                }
            });
        } else if (v == mButtonTakePhoto) {
            sv.setVisibility(View.INVISIBLE);
            camera.takePicture(null, null, new PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    // ? перевернуть
                    mPreview.setImageBitmap(bitmap);
                    onResume();
                }
            });
            onResume();
        } else if (v == mButtonFlash) {
            if (camera != null) {
                Camera.Parameters p = camera.getParameters();
                if (mIsFlashOn) {
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mButtonFlash.setText("Flash on");
                } else {
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    mButtonFlash.setText("Flash off");
                }
                mIsFlashOn = !mIsFlashOn;
                camera.setParameters(p);
            }
        } else if (v == mButtonFrontal) {
            sv.setVisibility(View.INVISIBLE);
            if (!mIsFrontal) {
                CAMERA_ID = 1;
                mButtonFrontal.setText("Back");
            } else {
                CAMERA_ID = 0;
                mButtonFrontal.setText("Frontal");
            }
            mIsFrontal = !mIsFrontal;
            stopCamera();
            startCamera();
            sv.setVisibility(View.VISIBLE);
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



    class HolderCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            camera.stopPreview();
            setCameraDisplayOrientation(CAMERA_ID);
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

    }

    void setPreviewSize(boolean fullScreen) {

        // получаем размеры экрана
        Display display = getWindowManager().getDefaultDisplay();
        boolean widthIsMax = display.getWidth() > display.getHeight();

        // определяем размеры превью камеры
        Size size = camera.getParameters().getPreviewSize();
        RectF rectDisplay = new RectF();
        RectF rectPreview = new RectF();

        // RectF экрана, соотвествует размерам экрана
        rectDisplay.set(0, 0, display.getWidth(), display.getHeight());

        // RectF первью
        if (widthIsMax) {
            // превью в горизонтальной ориентации
            rectPreview.set(0, 0, size.width, size.height);
        } else {
            // превью в вертикальной ориентации
            rectPreview.set(0, 0, size.height, size.width);
        }

        Matrix matrix = new Matrix();
        // подготовка матрицы преобразования
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
        // преобразование
        matrix.mapRect(rectPreview);

        // установка размеров surface из получившегося преобразования
        int paramSize = (int) Math.max(rectPreview.bottom, rectPreview.right);
        sv.getLayoutParams().height = paramSize;
        sv.getLayoutParams().width = paramSize;
    }

    void setCameraDisplayOrientation(int cameraId) {
        // определяем насколько повернут экран от нормального положения
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

        // получаем инфо по камере cameraId
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        // задняя камера
        if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
            result = ((360 - degrees) + info.orientation);
        } else
            // передняя камера
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                result = ((360 - degrees) - info.orientation);
                result += 360;
            }
        result = result % 360;
        camera.setDisplayOrientation(result);
    }

    private void startCamera() {
        if (camera == null) {
            camera = Camera.open(CAMERA_ID);
            setPreviewSize(FULL_SCREEN);
            if (mIsFlashOn) {
                Camera.Parameters param = camera.getParameters();
                param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(param);
            }
        }
        camera.startPreview();
    }

    private void stopCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
        camera = null;
    }
}
