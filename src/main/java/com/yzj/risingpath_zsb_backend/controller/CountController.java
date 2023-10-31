package com.yzj.risingpath_zsb_backend.controller;

import com.yzj.risingpath_zsb_backend.domain.vo.ProfessionTypeVo;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import com.yzj.risingpath_zsb_backend.service.SchoolService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/count")
public class CountController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private ProfessinfoService professinfoService;

    @ApiOperation("得到学校数量")
    @GetMapping("/schoolCount")
    public Long getSchoolNumber(){
        return schoolService.count();
    }

    @ApiOperation("得到所有专业的数量")
    @GetMapping("/professionCount")
    public Integer getProfessionCount(){
        return professinfoService.countDistinctProfession();
    }

    @ApiOperation("得到重复的所有专业的数量")
    @GetMapping("/professionCountRep")
    public Long getProfessionCountRep(){
        return professinfoService.count();
    }

    @ApiOperation("得到所有的招生人数")
    @GetMapping("/allTotalPlan")
    public Integer getAllTotalPlan(){
        return professinfoService.CountAllTotalPlan();
    }
    @ApiOperation("公办院校数量")
    @GetMapping("/publicSchoolCount")
    public Integer getPublicSchool(){
        return schoolService.getPublicSchoolCount();
    }

    @ApiOperation("民办院校数量")
    @GetMapping("/privateSchoolCount")
    public Integer getPrivateSchool(){
        return schoolService.getPrivateSchoolCount();
    }
    @ApiOperation("公办专业招生数量")
    @GetMapping("/sumPublicProfession")
    public Integer sumPublicProfession(){
        return professinfoService.SumPublicProfession();
    }

    @ApiOperation("民办专业招生数量")
    @GetMapping("/sumPrivateProfession")
    public Integer sumPrivateProfession(){
        return professinfoService.SumPrivateProfession();
    }

    @ApiOperation("获取各专业的招生数量")
    @GetMapping("/getCountOfMajorsByType")
    public List<ProfessionTypeVo> getCountOfMajorsByType(){
        return  professinfoService.getCountOfMajorsByType();
    }


}
