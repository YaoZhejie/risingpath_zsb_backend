package com.yzj.risingpath_zsb_backend.volunteerStrategy;
import com.yzj.risingpath_zsb_backend.domain.vo.SmartVolunteerVo;
import java.util.ArrayList;
import java.util.List;

/**
 * 三个一本四个二本，一个三本
 */
public class GradeOneStrategy implements SelectStrategy{

    @Override
    public List<SmartVolunteerVo> getSmartListVo(List<SmartVolunteerVo> keyList, List<SmartVolunteerVo> otherList, String type) {
        List<SmartVolunteerVo> list = new ArrayList<>();
        DivideGrade grade = new DivideGrade();
            for (SmartVolunteerVo smartVo : keyList) {
                if (grade.ifKeySchool(smartVo.getSchoolName()) && list.size() < 3 && smartVo.getType().equals(type)) {
                    list.add(smartVo);
                }
            }
            for (SmartVolunteerVo otherVo : otherList) {
                if (grade.ifPublicSchool(otherVo.getSchoolName()) && list.size() < 7 && otherVo.getType().equals(type)) {
                    list.add(otherVo);
                }
            }
            for (SmartVolunteerVo otherVo : otherList) {
                if (grade.ifPrivateSchool(otherVo.getSchoolName()) && list.size() < 8 && otherVo.getType().equals(type)) {
                    list.add(otherVo);
                }
            }

        return list;
    }
}
