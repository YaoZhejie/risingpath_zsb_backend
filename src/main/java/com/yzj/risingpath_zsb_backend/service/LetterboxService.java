package com.yzj.risingpath_zsb_backend.service;

import com.yzj.risingpath_zsb_backend.domain.Letterbox;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
* @author 姚浙杰
* @description 针对表【letterbox】的数据库操作Service
* @createDate 2023-05-30 20:39:07
*/
public interface LetterboxService extends IService<Letterbox> {

     /*
       插入信件
      */
     Boolean insertLetter(String title , String content , Date publicTime);

}
