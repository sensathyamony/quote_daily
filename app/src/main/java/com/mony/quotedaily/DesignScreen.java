package com.mony.quotedaily;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.NetworkOnMainThreadException;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.mony.quotedaily.adapter.ImageViewAdapter;
import com.mony.quotedaily.adapter.ShareUtils;
import com.mony.quotedaily.controller.UnsplashAPI;
import com.mony.quotedaily.model.ImageModel;
import com.mony.quotedaily.model.Result;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.listeners.ColorListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.jfenn.colorpickerdialog.dialogs.ColorPickerDialog;
import me.jfenn.colorpickerdialog.dialogs.ImageColorPickerDialog;
import me.jfenn.colorpickerdialog.interfaces.OnColorPickedListener;
import me.jfenn.colorpickerdialog.views.picker.ImageColorPickerView;
import me.jfenn.colorpickerdialog.views.picker.ImagePickerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DesignScreen extends AppCompatActivity {

    TextView quoteText, backgroundText, fontsText, shareBy;
    public static ImageView imageBg, imageTextTest;
    Button shareBtn, goback, changeTextBtn, changeAlignButton, changeColor;
    RecyclerView listImage, fontList;

    RelativeLayout frameLayout, viewContainer;
//    FrameLayout viewContainer;

    String quoteTextJson, quoteMoodJson;

    Intent intent;

    ArrayList<String> imageList = new ArrayList<>();
    ArrayList<Color> colorList = new ArrayList<>();
    ArrayList<Typeface> fontLists = new ArrayList<>();

    private final String CLIENT_ID = "a9d5c1f8f83e557aadf8d39c9f07db94959ccf96beb7552fd116b140f29abf85";

    ImageViewAdapter adapter;

    int xDelta, yDelta, baseDistance, clickColor = 1, alignClick = 1 ;
    float mRation = 1.0f;
    float mBaseRation;
    final static float step = 200;

    Bitmap bitmap;

    private static String imageData = "";

    CallbackManager callbackManager;
    public static ShareDialog shareDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_screen);

        intent = getIntent();
        quoteTextJson = intent.getStringExtra("quoteText");
        quoteMoodJson = intent.getStringExtra("quoteMood");

        shareBy = findViewById(R.id.shareBy);
        viewContainer = findViewById(R.id.viewContainer);
        frameLayout = findViewById(R.id.frameLayout);
        changeColor = findViewById(R.id.colorCard);
        changeAlignButton = findViewById(R.id.changeAlignButton);
        shareBtn = findViewById(R.id.shareButton);
        changeTextBtn = findViewById(R.id.changeTextButton);
        goback = findViewById(R.id.goBackButton);
        quoteText = findViewById(R.id.quoteText);
        backgroundText = findViewById(R.id.textBackground);
        imageBg = findViewById(R.id.imageViewBg);
        listImage = findViewById(R.id.rvImage);
        listImage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        quoteText.setText(quoteTextJson);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (quoteMoodJson == null || quoteMoodJson.equals("null"))
            quoteMoodJson = "view abstract tokyo japan tower people office";

        Typeface fredokaone = ResourcesCompat.getFont(getApplicationContext(), R.font.fredokaone);
        Typeface righteous = ResourcesCompat.getFont(getApplicationContext(), R.font.righteous);
        Typeface lobster = ResourcesCompat.getFont(getApplicationContext(), R.font.lobster);
        Typeface dancingscript = ResourcesCompat.getFont(getApplicationContext(), R.font.dancingscript);
        Typeface edu = ResourcesCompat.getFont(getApplicationContext(), R.font.edu);
        Typeface jura = ResourcesCompat.getFont(getApplicationContext(), R.font.jura);
        Typeface orbitron = ResourcesCompat.getFont(getApplicationContext(), R.font.orbitron);
        Typeface reemkufi = ResourcesCompat.getFont(getApplicationContext(), R.font.reemkufi);

        fontLists.add(fredokaone);
        fontLists.add(righteous);
        fontLists.add(lobster);
        fontLists.add(dancingscript);
        fontLists.add(edu);
        fontLists.add(jura);
        fontLists.add(orbitron);
        fontLists.add(reemkufi);

        getImageList();

        changeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int quoteNumb = random.nextInt(fontLists.size() - 1);
                quoteText.setTypeface(fontLists.get(quoteNumb));
                fontLists.add(fontLists.get(quoteNumb));
                fontLists.remove(quoteNumb);
            }
        });

        backgroundText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontList.setVisibility(View.INVISIBLE);
                listImage.setVisibility(View.VISIBLE);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                shareImage(viewContainer, getApplicationContext());
                ShareUtils.share(viewContainer, DesignScreen.this);
            }
        });

        changeAlignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: text align click "+alignClick);
                alignClick++;
                switch (alignClick){
                    case 1:
                        quoteText.setGravity(Gravity.CENTER_HORIZONTAL);
                        break;
                    case 2:
                        quoteText.setGravity(Gravity.START);
                        break;
                    case 3:
                        quoteText.setGravity(Gravity.END);
                        alignClick = 0;
                        break;
                }
            }
        });

        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int xScreenTouch = (int) event.getRawX(); // x location relative to the screen
                int yScreenTouch = (int) event.getRawY();

                if (event.getPointerCount() == 1) {
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            xDelta = xScreenTouch - lParams.leftMargin;
                            yDelta = yScreenTouch - lParams.topMargin;
                            break;

                        case MotionEvent.ACTION_MOVE:
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();

                            layoutParams.leftMargin = Math.max(0, Math.min(width - v.getWidth(), xScreenTouch - xDelta));
                            layoutParams.topMargin = Math.max(0, Math.min(height - v.getHeight(), yScreenTouch - yDelta));
                            v.setLayoutParams(layoutParams);
                            break;

                        case MotionEvent.ACTION_UP:
                            frameLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
                            break;
                    }
                }
                if (event.getPointerCount() == 2){
                    int action = event.getAction();
                    int pureAction = action & MotionEvent.ACTION_MASK;
                    if (pureAction == MotionEvent.ACTION_POINTER_DOWN){
                        baseDistance = getDistance(event);
                        mBaseRation = mRation;
                    }else{
                        float delta = (getDistance(event) - baseDistance) / step;
                        float multi = (float) Math.pow(2, delta);
                        mRation = Math.min(125.0f, Math.max(0.1f, mBaseRation * multi));

                        frameLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transBackground));

                        quoteText.setTextSize(mRation + 12);

                        v.getLayoutParams().width = xScreenTouch;
                        v.getLayoutParams().height = yScreenTouch;
                        v.requestLayout();
                    }
                }
                return true;
            }
        });

        changeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogMessage();
            }
        });

    }


    public Uri getLocalBitmapUri(String filepath) {

        try
        {
            URL url = new URL("Enter the URL to be downloaded");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
            String filename="downloadedFile.png";
            Log.i("Local filename:",""+filename);
            File file = new File(SDCardRoot,filename);
            if(file.createNewFile())
            {
                file.createNewFile();
            }
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ( (bufferLength = inputStream.read(buffer)) > 0 )
            {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                Log.i("Progress:","downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;
            }
            fileOutput.close();
            if(downloadedSize==totalSize) filepath=file.getPath();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            filepath=null;
            e.printStackTrace();
        }
        Log.i("filepath:"," "+filepath) ;

        return Uri.parse(filepath);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void showDialogMessage(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Pick Color")
                .setMessage("Do you want to pick from Background Image Color?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new ColorPickerDialog()
                                .withAlphaEnabled(true)
                                .withColor(quoteText.getCurrentTextColor())
                                .withCornerRadius(50.0f)
                                .withListener(new OnColorPickedListener<ColorPickerDialog>() {
                                    @Override
                                    public void onColorPicked(@Nullable ColorPickerDialog pickerView, int color) {
                                        quoteText.setTextColor(color);
                                    }
                                })
                                .show(getSupportFragmentManager(), "Color Picker");
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new ImageColorPickerDialog()
                                .withImagePath(getApplicationContext(), imageData)
                                .withColor(Color.WHITE)
                                .withCornerRadius(50.0f)
                                .withCornerRadiusPx(30)
                                .withListener(new OnColorPickedListener<ImageColorPickerDialog>() {
                                    @Override
                                    public void onColorPicked(@Nullable ImageColorPickerDialog pickerView, int color) {
                                        quoteText.setTextColor(color);
                                    }
                                })
                                .show(getSupportFragmentManager(), "Image Color Picker");
                    }
                }).show();
        alertDialog.create();
    }


    int getDistance(MotionEvent motionEvent){
        int dx = (int)(motionEvent.getX(0) - motionEvent.getX(1));
        int dy = (int)(motionEvent.getY(0) - motionEvent.getY(1));

        return (int)(Math.sqrt(dx * dx + dy * dy));
    }

    public static void setImageUrl(String imageSource){
        imageData = imageSource;
        Picasso.get().load(imageSource).into(imageBg);
    }

    //client_id=a9d5c1f8f83e557aadf8d39c9f07db94959ccf96beb7552fd116b140f29abf85&query=natural&color=black&orientation=landscape&order_by=latest&per_page=5
    public void getImageList(){
//        imageBg.setImageResource(R.drawable.test_background);
        int randPage = new Random().nextInt(10);
        QuoteService service = UnsplashAPI.getImage().create(QuoteService.class);
        Call<ImageModel> call = service.getImage(CLIENT_ID, quoteMoodJson, "portrait", "relevant", 15, randPage);
        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                if (response.isSuccessful()){
                    List<Result> resultsJson = response.body().getResults();
                    ArrayList<String> tempImage = new ArrayList<>();
                    int respondIndex = response.body().getResults().size();

                    for (int i = 0; i < respondIndex; i++) {
                        imageList.add(resultsJson.get(i).getUrls().getRegular());
                        tempImage.add(resultsJson.get(i).getUrls().getThumb());
                    }
                    adapter = new ImageViewAdapter(imageList, tempImage, getApplicationContext());
                    listImage.setAdapter(adapter);
                }else{
                    Log.e("IMAGE_API", "onResponse: Error API "+response.code() + " API Respond " +response );
                    Toast.makeText(getApplicationContext(), "Api calling error "+response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                Log.e("IMAGE_API", "onFailure: Error API "+t.getMessage() );
                Toast.makeText(getApplicationContext(), "Api calling error "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}