package com.qingcheng.service.impl;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class RestClientFactory {

    public static RestHighLevelClient getRestHighLevelClient(String hostName,int port){

        //连接Rest接口
        HttpHost http=new HttpHost(hostName,port,"http");
        RestClientBuilder restClientBuilder = RestClient.builder(http);
        return new RestHighLevelClient(restClientBuilder);
    }

}
