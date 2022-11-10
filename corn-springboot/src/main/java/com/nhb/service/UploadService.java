package com.nhb.service;

import com.nhb.utils.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 大只
 * @date 2022-11-09
 */
public interface UploadService {
    Result uploadImg(MultipartFile file);
}
