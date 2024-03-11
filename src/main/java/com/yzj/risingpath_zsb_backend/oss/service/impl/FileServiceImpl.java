package com.yzj.risingpath_zsb_backend.oss.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.oss.service.FileService;
import com.yzj.risingpath_zsb_backend.oss.util.OssProperties;
import com.aliyun.oss.model.PutObjectRequest;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import com.aliyun.oss.OSS;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    /**
     * 文件上传阿里云
     *
     * @param inputStream
     * @param module
     * @param filename
     * @return
     */
    @Override
    public String upload(InputStream inputStream, String module, String filename) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(OssProperties.ENDPOINT, OssProperties.KEY_ID, OssProperties.KEY_SECRET);
        log.info("OSSClient实例创建成功！");
        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            if (!ossClient.doesBucketExist(OssProperties.BUCKET_NAME)) {
                //创建bucket
                ossClient.createBucket(OssProperties.BUCKET_NAME);
                log.info("bucket存储空间【{}】创建成功", OssProperties.BUCKET_NAME);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(OssProperties.BUCKET_NAME, CannedAccessControlList.PublicRead);
                log.info("【{}】存储空间访问权限设置为公共读成功");
            }
            //构建日期路径：avatar/2019/02/26/文件名
            String folder = new DateTime().toString("yyyy/MM/dd");
            //文件名：uuid.扩展名
            filename = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
            //文件根路径
            String key = module + "/" + folder + "/" + filename;
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(OssProperties.BUCKET_NAME, key, inputStream);
            // 创建PutObject请求。
            ossClient.putObject(putObjectRequest);
            log.info("oss文件上传成功");
            //阿里云文件绝对路径
            String endpoint = OssProperties.ENDPOINT.substring(OssProperties.ENDPOINT.lastIndexOf("//") + 2);
            //返回文件的访问路径
            return "https://" + OssProperties.BUCKET_NAME + "." + endpoint + "/" + key;
        } catch (OSSException oe) {
            log.error("OSSException 文件上传失败：{}", oe);
            throw new BusinessException(ErrorCode.UPLOAD_ERROR, oe.getErrorMessage());
        } catch (ClientException ce) {
            log.error("ClientException 文件上传失败：{}", ExceptionUtils.getStackTrace(ce));
            throw new BusinessException(ErrorCode.UPLOAD_ERROR, ce.getErrorCode());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
                log.info("关闭ossClient");
            }
        }

    }

    /**
     * 删除文件
     *
     * @param url
     */
    @Override
    public void remove(String url) {
        OSS ossClient = new OSSClientBuilder().build(OssProperties.ENDPOINT, OssProperties.KEY_ID, OssProperties.KEY_SECRET);
        log.info("OSSClient实例创建成功");
        try {
            String endpoint = OssProperties.ENDPOINT.substring(OssProperties.ENDPOINT.lastIndexOf("//") + 2);
            //文件名（服务器上的文件路径）
            String host = "https://" + OssProperties.BUCKET_NAME + "." + endpoint + "/";
            String objectName = url.substring(host.length());
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(OssProperties.BUCKET_NAME, objectName);
            log.info("{}文件删除成功", objectName);
        } catch (OSSException oe) {
            log.error("OSSException 文件删除失败：{}", oe);
            throw new BusinessException(ErrorCode.UPLOAD_ERROR, "文件删除失败，请检查文件路径是否正确");
        } catch (ClientException ce) {
            log.error("ClientException 文件删除失败：{}", ExceptionUtils.getStackTrace(ce));
            throw new BusinessException(ErrorCode.UPLOAD_ERROR, "文件删除失败，请检查文件路径是否正确");
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
                log.info("关闭ossClient");
            }
        }
    }
}
