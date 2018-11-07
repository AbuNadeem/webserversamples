package com.samples.webserver.webserversamples.log_in_system.internet;


import static com.samples.webserver.webserversamples.log_in_system.data.Contract.BAS_URL;

/**
 * Created by ibrahim on 19/01/18.
 */

/**class just pass first part of Url
 **named BASE_URL that found in {@linkplain com.samples.webserver.webserversamples.log_in_system.data.Contract}
 * to create RetrofitClient with all second url wanted in BaseApiService
 */
public class UtilsApi {

    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BAS_URL).create(BaseApiService.class);
    }
}