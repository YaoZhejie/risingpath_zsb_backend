package com.yzj.risingpath_zsb_backend.volunteerStrategy;

import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.contant.Grade;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import java.util.ArrayList;
import java.util.Arrays;

public class DivideGrade {
    /**
     * 分辨人工智能给出的分数是哪一个档次的
     */
    public Integer getGrade(Integer predictScore) {
        if (predictScore == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (predictScore >= 260) {
            return Grade.GradeOne;
        } else if (predictScore >= 240 && predictScore < 260) {
            return Grade.GradeTwo;
        } else if (predictScore >= 220 && predictScore < 240) {
            return Grade.GradeThree;
        } else if (predictScore >= 200 && predictScore < 220) {
            return Grade.GradeFour;
        } else if (predictScore >= 180 && predictScore < 200) {
            return Grade.GradeFive;
        } else if (predictScore >= 150 && predictScore < 180) {
            return Grade.GradeSix;
        } else {
            return Grade.GradeSeven;
        }
    }

    /**
     *判断是否一本院校
     */
    public Boolean ifKeySchool(String schoolName) {
        String[] keySchoolArray = {"杭州电子科技大学", "浙江工商大学", "杭州师范大学", "湖州师范学院",
                "温州大学", "浙江财经大学", "浙江海洋大学", "浙江理工大学", "浙江农林大学",
                "浙江中医药大学", "温州医科大学", "中国计量大学"};
        ArrayList<String> keySchoolList = new ArrayList<>(Arrays.asList(keySchoolArray));
        return keySchoolList.contains(schoolName);
    }

    /**
     * 判断是否两本院校
     */
    public Boolean ifPublicSchool(String schoolName) {
        String[] publicSchool = {"浙江水利水电学院", "杭州医学院", "湖州学院", "嘉兴南湖学院", "嘉兴学院",
                "丽水学院", "宁波工程学院", "衢州学院", "绍兴文理学院", "台州学院", "温州理工学院",
                "浙大城市学院", "浙大宁波理工学院", "浙江科技学院", "浙江外国语学院",
                "浙江药科职业大学", "浙江音乐学院"};
        ArrayList<String> publicSchoolList = new ArrayList<>(Arrays.asList(publicSchool));
        return publicSchoolList.contains(schoolName);
    }

    /**
     *判断是否三本院校
     */
    public Boolean ifPrivateSchool(String schoolName) {
        String[] privateSchool = {"杭州电子科技大学信息工程学院", "宁波财经学院", "浙江财经大学东方学院", "浙江工商大学杭州商学院",
                "浙江工业大学之江学院", "宁波大学科学技术学院", "上海财经大学浙江学院", "绍兴文理学院元培学院",
                "同济大学浙江学院", "温州商学院", "温州医科大学仁济学院", "浙江广厦建设职业技术大学", "浙江理工大学科技与艺术学院",
                "浙江农林大学暨阳学院", "浙江师范大学行知学院", "浙江树人学院","浙江万里学院","浙江越秀外国语学院", "中国计量大学现代科技学院"};
        ArrayList<String> privateSchoolList = new ArrayList<>(Arrays.asList(privateSchool));
        return privateSchoolList.contains(schoolName);
    }


}
