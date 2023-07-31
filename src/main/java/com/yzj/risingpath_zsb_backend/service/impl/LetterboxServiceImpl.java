package com.yzj.risingpath_zsb_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.risingpath_zsb_backend.domain.Letterbox;
import com.yzj.risingpath_zsb_backend.service.LetterboxService;
import com.yzj.risingpath_zsb_backend.mapper.LetterboxMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author 姚浙杰
* @description 针对表【letterbox】的数据库操作Service实现
* @createDate 2023-05-30 20:39:07
*/
@Service
public class LetterboxServiceImpl extends ServiceImpl<LetterboxMapper, Letterbox>
    implements LetterboxService{

     @Resource
     private LetterboxMapper letterboxMapper;
    @Override
    public Boolean insertLetter(String title , String content, Date publicTime) {
          Letterbox letterbox = new Letterbox();
          letterbox.setTitle(title);
          letterbox.setContent(content);
          letterbox.setPublishTime(publicTime);
         return letterboxMapper.insert(letterbox)>0;
    }


}




