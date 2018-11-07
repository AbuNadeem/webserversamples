package com.samples.webserver.webserversamples.log_in_system.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;


import com.samples.webserver.webserversamples.R;
import com.samples.webserver.webserversamples.log_in_system.data.Contract;
import com.samples.webserver.webserversamples.log_in_system.internet.BaseApiService;
import com.samples.webserver.webserversamples.log_in_system.internet.UtilsApi;
import com.samples.webserver.webserversamples.log_in_system.internet.model.ResponseApiModel;
import com.samples.webserver.webserversamples.log_in_system.ui.log_in.LogInActivity;
import com.samples.webserver.webserversamples.log_in_system.util.LangUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {

    private final Context mCtx;
    private final BaseApiService mApiService;
    private ProgressDialog mLoading;

    private static final String TAG = RegisterPresenter.class.getSimpleName();

    public RegisterPresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    /*this mothod work when user select image while regestring*/
    public void requestRegisterWithPhoto(
            String old_part_img,//pass path the file of image as string from MainActivity
            File myfile,//pass file  from MainActivity
            String nameval,//pass name come from editText from MainActivity
            String emailval,//pass email come from editText from MainActivity
            String passval,//pass password come from editText from MainActivity
            String phoneval//pass phone come from editText from MainActivity
      ) {
        //initialize ProgressDialog status message
        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.creating_new), true, false);

        File imagefile = new File(old_part_img);//initialize new file as file that come from string old_part_img this string come from parameter


        if (myfile != null) {/*all codes below inside this if statment
                              to use file come from parameters to compress the size of
                              the file of our image before uploading to server*/
            try {
                try {
                    imagefile = new Compressor(mCtx)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(myfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }


        /*initailize new request created fom device files */
        RequestBody reqBody = RequestBody.create(MediaType.parse(Contract.MULTIPART_FILE_PATH), imagefile);
        /*make multipart request  with our image file and our initilzed requestBody*/
        MultipartBody.Part partImage = MultipartBody.Part.createFormData(Contract.PIC_TO_LOAD, imagefile.getName(), reqBody);

        RequestBody name = createPartFromString(nameval);/*initialize new request created fom string value of name */
        RequestBody email = createPartFromString(emailval);/*initialize new request created fom string value of email*/
        RequestBody password = createPartFromString(passval);/*initialize new request created fom string value of password*/
        RequestBody phone = createPartFromString(phoneval);/*initialize new request created fom string value of phone*/
        RequestBody lang = createPartFromString(LangUtil.getCurrentLanguage(mCtx));/*initailize new request created fom string value of language device*/

        //creat new HashMap with string and RequestBody
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put(Contract.NAME_COL, name);//add RequestBody name to initialized HashMap
        map.put(Contract.EMAIL_COL, email);//add RequestBody email to initialized HashMap
        map.put(Contract.PASSWORD_COL, password);//add RequestBody password to initialized HashMap
        map.put(Contract.PHONE_COL, phone);//add RequestBody phone to initialized HashMap
        map.put(Contract.LANG_COL, lang);//add RequestBody lang to initialized HashMap

        /*make new Call retrofit with our HashMap that carry all
         requests body with strings and with MultipartBody that carry image file value * */
        Call<ResponseApiModel> upload = mApiService.uploadImage(map, partImage);

        upload.enqueue(new Callback<ResponseApiModel>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ResponseApiModel> call, @NonNull final Response<ResponseApiModel> response) {

                Log.d(TAG, "myjson = : " +
                        Objects.requireNonNull(response.body()).toString());
                //get value from json value if value is false all things good and successfull request no error
                if (Objects.requireNonNull(response.body()).getError().equals(Contract.FALSE_VAL)) {
                    //   mLoading.setMessage(response.body().getError_msg());


                    Log.d(TAG, "server_message : " + Objects.requireNonNull(response.body()).getError_msg()
                            + "\n" + Objects.requireNonNull(response.body()).getSuccess_msg());

                    //    mLoading.dismiss();
                    if (Objects.requireNonNull(response.body()).getSuccess_msg().equals(Contract.SUCCESS_MSG_VALUE)) {
                        Toast.makeText(mCtx,
                                Objects.requireNonNull(response.body()).getError_msg()
                                , Toast.LENGTH_SHORT).show();
                        mCtx.startActivity(new Intent(mCtx, LogInActivity.class));
                    }
                    mLoading.dismiss();

                    //get value from json value if value is true there are error that come inside error_message JsonObject

                } else if (Objects.requireNonNull(response.body()).getError().equals("true")) {
                    Toast.makeText(mCtx,
                            Objects.requireNonNull(response.body()).getError_msg()
                            , Toast.LENGTH_SHORT).show();
                    mLoading.dismiss();


                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseApiModel> call, @NonNull Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
            }
        });

    }


    private RequestBody createPartFromString(String partString) {
        return RequestBody.create(MultipartBody.FORM, partString);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    /*this mothod work when user not select image while regestring*/
    public void requestRegister(String nameval,
                                String emailval,
                                String passval,
                                String phoneval) {

        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.creating_new), true, false);

        mApiService.registerRequest(
                nameval,
                emailval,
                passval,
                phoneval,
                LangUtil.getCurrentLanguage(mCtx))
                .enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();
                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);
                                Log.d("JSONString", remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    mLoading.setMessage(jsonRESULTS.optString(Contract.ERROR_MSG));
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                    if (jsonRESULTS.optString(Contract.SUCCESS_MSG).equals(Contract.SUCCESS_MSG_VALUE)) {
                                        mCtx.startActivity(new Intent(mCtx, LogInActivity.class));
                                    }
                                    mLoading.dismiss();

                                } else {
                                    String error_message = jsonRESULTS.optString(Contract.ERROR_MSG);
                                    Toast.makeText(mCtx, error_message, Toast.LENGTH_SHORT).show();
                                    mLoading.setMessage(error_message);
                                    mLoading.dismiss();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        mLoading.dismiss();

                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.e("JSONSdebug", "onFailure: ERROR > " + t.getMessage());
                    }
                });
    }

}
