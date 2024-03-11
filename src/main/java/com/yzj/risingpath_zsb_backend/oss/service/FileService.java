package com.yzj.risingpath_zsb_backend.oss.service;

import java.io.InputStream;

public interface FileService {

    /**
     * 文件上传阿里云
     * @param inputStream
     * @param module
     * @param originalFilename
     * @return
     */
    String upload(InputStream inputStream, String module, String originalFilename);

    /**
     * 删除文件
     * @param url
     */
    void remove(String url);
}
