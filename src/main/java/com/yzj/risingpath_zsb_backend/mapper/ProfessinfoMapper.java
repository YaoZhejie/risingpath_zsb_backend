package com.yzj.risingpath_zsb_backend.mapper;

import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 姚浙杰
* @description 针对表【professinfo】的数据库操作Mapper
* @createDate 2023-05-31 10:50:53
* @Entity com.yzj.risingpath_zsb_backend.domain.Professinfo
*/
public interface ProfessinfoMapper extends BaseMapper<Professinfo> {
    /**
     * 查询出院校专业等信息（多表联查）
     * @return
     */
    public List<Professinfo> allProfessinfo();

    /**
     *   根据专业模糊查询
     */

    public List<Professinfo> professinfoLikeKeyinfo(String professName);

    /**
     * 根据备注名称模糊查询
     */
    public List<Professinfo> professLikeRemark(String remark);

    /**
     * 根据院校名称模糊查询专业信息
     */
    public List<Professinfo> professLikeSchoolName(String schoolName);

}




