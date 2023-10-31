package com.yzj.risingpath_zsb_backend.mapper;

import com.yzj.risingpath_zsb_backend.domain.Collect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 姚浙杰
 * @description 针对表【collect】的数据库操作Mapper
 * @createDate 2023-10-20 10:30:25
 * @Entity com.yzj.risingpath_zsb_backend.domain.Collect
 */
public interface CollectMapper extends BaseMapper<Collect> {
    /**
     * 根据userId查找收藏列表
     *
     * @param userId
     * @return
     */
    @Select("SELECT * FROM collect WHERE userId = #{userId}")
    List<Collect> selectCollectListByUserId(Long userId);

    @Delete("DELETE FROM collect WHERE schoolId = #{schoolId} AND proId = #{proId}")
    Boolean deleteCollect(Integer schoolId, Integer proId);

}




