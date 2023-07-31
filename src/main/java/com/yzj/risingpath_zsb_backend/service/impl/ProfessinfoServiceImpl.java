package com.yzj.risingpath_zsb_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import com.yzj.risingpath_zsb_backend.mapper.ProfessinfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 姚浙杰
* @description 针对表【professinfo】的数据库操作Service实现
* @createDate 2023-05-31 10:50:53
*/
@Service
public class ProfessinfoServiceImpl extends ServiceImpl<ProfessinfoMapper, Professinfo>
    implements ProfessinfoService{

    @Resource
    private ProfessinfoMapper professinfoMapper;

    @Override
    public List<Professinfo> allProfessinfo() {
        return professinfoMapper.allProfessinfo();
    }
    /**
     * 根据专业模糊查询
     */
    @Override
    public List<Professinfo> professinfoLikeKey(String professinfo) {
        return professinfoMapper.professinfoLikeKeyinfo(professinfo);
    }


    @Override
    public List<Professinfo> professLikeRemark(String remark) {
        return professinfoMapper.professLikeRemark(remark);
    }

    @Override
    public List<Professinfo> professLikeSchoolName(String schoolName) {
        return professinfoMapper.professLikeSchoolName(schoolName);
    }

    /**
     * 根据学校id查询专业信息
     * @param schoolId
     * @return
     */
    @Override
    public List<Professinfo> professBySchoolId(Integer schoolId) {
        QueryWrapper<Professinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schoolId",schoolId);
        List<Professinfo> list = professinfoMapper.selectList(queryWrapper);
        return list;
    }


}




