package com.samples.webserver.webserversamples.log_in_system.internet;


import static com.samples.webserver.webserversamples.log_in_system.data.Contract.BAS_URL;

/**
 * Created by ibrahim on 19/01/18.
 */

public class UtilsApi {

    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BAS_URL).create(BaseApiService.class);
    }
}