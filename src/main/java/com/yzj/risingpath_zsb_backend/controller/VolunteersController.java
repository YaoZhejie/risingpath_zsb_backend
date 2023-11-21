package com.yzj.risingpath_zsb_backend.controller;

import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.contant.Grade;
import com.yzj.risingpath_zsb_backend.domain.dto.GenerateRequest;
import com.yzj.risingpath_zsb_backend.predictor.ModelPredictor;
import com.yzj.risingpath_zsb_backend.volunteerStrategy.*;
import com.yzj.risingpath_zsb_backend.domain.vo.SmartVolunteerVo;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/volunteer")
public class VolunteersController {

    @Resource
    private ProfessinfoService professinfoService;

//    /**
//     * 对口专业获取志愿
//     */
//    @ApiOperation("对口专业获取志愿")
//    @PostMapping("/getTargetVolunteer")
//    public List<SmartVolunteerVo> getTargetVolunteer(String remarks, Integer score, String type) {
//        List<SmartVolunteerVo> list = new ArrayList<>();
//        StrategyContext strategyContext = null;
//        DivideGrade grade = new DivideGrade();
//        //计算出等级
//        Integer scoreGrade = grade.getGrade(score);
//        List<SmartVolunteerVo> keyList = professinfoService.getKeySchoolVolunteersMajor(remarks);
//        List<SmartVolunteerVo> otherList = professinfoService.getPublicSchoolVolunteersMajor(remarks);
//        //验证等级
//        if (scoreGrade == Grade.GradeOne) {
//            strategyContext = new StrategyContext(new GradeOneStrategy());
//            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
//        } else if (scoreGrade == Grade.GradeTwo) {
//            strategyContext = new StrategyContext(new GradeTwoStrategy());
//            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
//        } else if (scoreGrade == Grade.GradeThree) {
//            strategyContext = new StrategyContext(new GradeThreeStrategy());
//            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
//        } else if (scoreGrade == Grade.GradeFour) {
//            strategyContext = new StrategyContext(new GradeFourStrategy());
//            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
//        } else if (scoreGrade == Grade.GradeFive) {
//            strategyContext = new StrategyContext(new GradeFiveStrategy());
//            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
//        } else if (scoreGrade == Grade.GradeSix) {
//            strategyContext = new StrategyContext(new GradeSixStrategy());
//            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
//        } else if (scoreGrade == Grade.GradeSeven) {
//            strategyContext = new StrategyContext(new GradeSevenStrategy());
//            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
//        }
//        return list;
//    }

    /**
     * 非对口专业获取志愿
     */
    @ApiOperation("非对口专业获取志愿")
    @PostMapping("/getNoTargetVolunteer")
    public BaseResponse<Map<String,SmartVolunteerVo>> getNoTargetVolunteer(@RequestBody GenerateRequest request) {
        List<SmartVolunteerVo> list = new ArrayList<>();
        StrategyContext strategyContext = null;
        DivideGrade grade = new DivideGrade();
//        System.out.println(request.getHours());
        String remarks = request.getRemarks();
//        ModelPredictor predictor = new ModelPredictor();
//        //获取ai预测的分数
//        Integer score = predictor.getAiScore(request);
        Integer score = request.getScore();
        if (score==0){
            score = request.getScore();
        }
        String type = request.getType();
        //计算出等级 scoreGrade
        Integer scoreGrade = grade.getGrade(score);
        List<SmartVolunteerVo> keyList = professinfoService.getKeySchoolVolunteersNoMajor(remarks);
        List<SmartVolunteerVo> otherList = professinfoService.getPublicSchoolVolunteersNoMajor(remarks);
        //验证等级
        if (scoreGrade == Grade.GradeOne) {
            strategyContext = new StrategyContext(new GradeOneStrategy());
            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
        } else if (scoreGrade == Grade.GradeTwo) {
            strategyContext = new StrategyContext(new GradeTwoStrategy());
            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
        } else if (scoreGrade == Grade.GradeThree) {
            strategyContext = new StrategyContext(new GradeThreeStrategy());
            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
        } else if (scoreGrade == Grade.GradeFour) {
            strategyContext = new StrategyContext(new GradeFourStrategy());
            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
        } else if (scoreGrade == Grade.GradeFive) {
            strategyContext = new StrategyContext(new GradeFiveStrategy());
            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
        } else if (scoreGrade == Grade.GradeSix) {
            strategyContext = new StrategyContext(new GradeSixStrategy());
            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
        } else if (scoreGrade == Grade.GradeSeven) {
            strategyContext = new StrategyContext(new GradeSevenStrategy());
            list = strategyContext.getGradeStrategy().getSmartListVo(keyList, otherList, type);
        }
        Map<String, SmartVolunteerVo> Map = toMapVolunteers(list);
        return ResultUtils.success(Map);
    }


    public Map<String,SmartVolunteerVo> toMapVolunteers(List<SmartVolunteerVo> list){
        Map<String,SmartVolunteerVo> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String name = null;
            switch (i){
                case 0:
                    name = "A";
                    break;
                case 1:
                    name = "B";
                    break;
                case 2:
                    name = "C";
                    break;
                case 3:
                    name = "D";
                    break;
                case 4:
                    name = "E";
                    break;
                case 5:
                    name = "F";
                    break;
                case 6:
                    name = "G";
                    break;
                case 7:
                    name = "H";
            }
            map.put(name,list.get(i));
        }
        return map;
    }

}
