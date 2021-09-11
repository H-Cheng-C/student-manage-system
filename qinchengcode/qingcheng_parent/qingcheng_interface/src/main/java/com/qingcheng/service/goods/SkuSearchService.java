package com.qingcheng.service.goods;

import java.io.IOException;
import java.util.Map;

public interface SkuSearchService {

    public Map search(Map<String,String> searchMap) throws IOException;
}
