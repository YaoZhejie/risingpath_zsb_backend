package com.yzj.risingpath_zsb_backend.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.yzj.risingpath_zsb_backend.domain.School;
import com.yzj.risingpath_zsb_backend.domain.dto.ProfessionInfoRequest;
import com.yzj.risingpath_zsb_backend.domain.vo.ProfessionAndSchoolVo;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.mapper.SchoolMapper;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import com.yzj.risingpath_zsb_backend.mapper.ProfessinfoMapper;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author 姚浙杰
 * @description 针对表【professinfo】的数据库操作Service实现
 * @createDate 2023-05-31 10:50:53
 */
@Service
public class ProfessinfoServiceImpl extends ServiceImpl<ProfessinfoMapper, Professinfo>
        implements ProfessinfoService {

    @Resource
    private ProfessinfoMapper professinfoMapper;

    @Resource
    private SchoolMapper schoolMapper;

    @Override
    public List<Professinfo> allProfessinfo() {
        return professinfoMapper.allProfessinfo();
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
     *
     * @param schoolId
     * @return
     */
    @Override
    public List<Professinfo> professBySchoolId(Integer schoolId) {
        QueryWrapper<Professinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schoolId", schoolId);
        List<Professinfo> list = professinfoMapper.selectList(queryWrapper);
        return list;
    }





}




