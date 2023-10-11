package com.yzj.risingpath_zsb_backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzj.risingpath_zsb_backend.domain.dto.ProfessionInfoRequest;
import com.yzj.risingpath_zsb_backend.domain.vo.ProfessionAndSchoolVo;

import java.util.List;

/**
* @author 姚浙杰
* @description 针对表【professinfo】的数据库操作Service
* @createDate 2023-05-31 10:50:53
*/
public interface ProfessinfoService extends IService<Professinfo> {

    /**
     * 所有学校的报考专业查询
     * @return
     */
    public List<Professinfo> allProfessinfo();

    /**
     * 专业模糊查询
     * @param
     * @param
     * @return
     */


    /**
     * 根据备注模糊查询
     * @param remark
     * @return
     */
    public List<Professinfo> professLikeRemark(String remark);

    /**
     * 根据院校名模糊查询
     * @param schoolName
     * @return
     */
    public List<Professinfo> professLikeSchoolName(String schoolName);


    /**
     * 根据学校主键查询专业
     */
    public List<Professinfo> professBySchoolId(Integer schoolId);


    /**
     * 根据学校名称，升本专业名称，升本的备注名称分页查询信息
     */
//    public Page<ProfessionAndSchoolVo> PageAllProfessionsByLike(ProfessionInfoRequest professionInfoRequest);

}
