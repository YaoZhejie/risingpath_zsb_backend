package com.yzj.risingpath_zsb_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ExcelUtil;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.School;
import com.yzj.risingpath_zsb_backend.domain.dto.SimVolunteersCache;
import com.yzj.risingpath_zsb_backend.domain.vo.SimVolunteerOutput;
import com.yzj.risingpath_zsb_backend.domain.vo.SimulationMajorVo;
import com.yzj.risingpath_zsb_backend.domain.vo.SimulationSchoolVo;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import com.yzj.risingpath_zsb_backend.service.SchoolService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/simulation")
public class SimulationController {

    @Resource
    private SchoolService schoolService;

    @Resource
    private ProfessinfoService professinfoService;

    @Autowired
    private RedisTemplate redisTemplate;


    @ApiOperation("获取模拟填报志愿学校的数据")
            @GetMapping("/getSimulationSchool/{remarks}/{type}")
    public BaseResponse<List<SimulationSchoolVo>> getSimulationSchool(@PathVariable String remarks,@PathVariable String type) {

        List<School> schools = schoolService.getSchoolForSim(remarks,type);

        List<SimulationSchoolVo> simulationSchools = new ArrayList<>();
        schools.forEach(school -> {
            SimulationSchoolVo simulationSchoolVo = new SimulationSchoolVo();
            simulationSchoolVo.setSchoolCode(school.getSchoolCode());
            simulationSchoolVo.setSchoolName(school.getSchoolName());
            simulationSchools.add(simulationSchoolVo);
        });

        return ResultUtils.success(simulationSchools);
    }

    @ApiOperation("获取模拟填报志愿专业的数据")
    @GetMapping("/getSimulationMajor/{remarks}/{school}/{type}")
    public BaseResponse<List<SimulationMajorVo>> getSimulationMajor(@PathVariable String remarks, @PathVariable String school, @PathVariable String type) {
        if (remarks == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<SimulationMajorVo> list = professinfoService.getSimulationMajorVo(remarks, school, type);
        return ResultUtils.success(list);
    }

    @ApiOperation("缓存redis中的志愿信息")
    @PostMapping("/SimVolunteersCache")
    public BaseResponse<Boolean> SimVolunteersCache(@RequestBody SimVolunteersCache simVolunteersCache) {
        if (simVolunteersCache == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取 RedisTemplate 中的 ValueOperations
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Long userId = simVolunteersCache.getUserId();
        SimVolunteersCache cache = (SimVolunteersCache) valueOperations.get("simCache" + userId);
        if (cache != null) {
            redisTemplate.delete("simCache" + userId);
        }
        Object previousValue = valueOperations.getAndSet("simCache" + userId, simVolunteersCache);
        //半小时后缓存过期
        redisTemplate.expire("simCache" + userId, 1800, TimeUnit.SECONDS);
        boolean isCacheSaved = (previousValue == null);
        return ResultUtils.success(isCacheSaved);
    }

    @ApiOperation("获取缓存在redis中的志愿信息")
    @PostMapping("/getSimVolunteersCache/{userId}")
    public BaseResponse<SimVolunteersCache> getSimVolunteersCache(@PathVariable Long userId ) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        SimVolunteersCache cache = (SimVolunteersCache) valueOperations.get("simCache" + userId);
        return ResultUtils.success(cache);
    }

    @ApiOperation("导出志愿")
    @GetMapping("/exportSimVolunteers/{userId}")
    public void generateSimVolunteers(@PathVariable Long userId, HttpServletResponse response){
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String fileName = "模拟志愿.xls";
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        SimVolunteersCache cache = (SimVolunteersCache) valueOperations.get("simCache" + userId);
        List<SimVolunteerOutput> SimVolunteerOutputs = new ArrayList<>();
        SimVolunteerOutput simVolunteerOutput = new SimVolunteerOutput();
        simVolunteerOutput.setCode(cache.getCode());
        simVolunteerOutput.setSchool(cache.getSchool());
        simVolunteerOutput.setMajorCode(cache.getMajorCode());
        simVolunteerOutput.setMajor(cache.getMajor());
        SimVolunteerOutput simVolunteerOutput2 = new SimVolunteerOutput();
        simVolunteerOutput2.setCode(cache.getCode2());
        simVolunteerOutput2.setSchool(cache.getSchool2());
        simVolunteerOutput2.setMajorCode(cache.getMajorCode2());
        simVolunteerOutput2.setMajor(cache.getMajor2());
        SimVolunteerOutput simVolunteerOutput3 = new SimVolunteerOutput();
        simVolunteerOutput3.setCode(cache.getCode3());
        simVolunteerOutput3.setSchool(cache.getSchool3());
        simVolunteerOutput3.setMajorCode(cache.getMajorCode3());
        simVolunteerOutput3.setMajor(cache.getMajor3());
        SimVolunteerOutput simVolunteerOutput4 = new SimVolunteerOutput();
        simVolunteerOutput4.setCode(cache.getCode4());
        simVolunteerOutput4.setSchool(cache.getSchool4());
        simVolunteerOutput4.setMajorCode(cache.getMajorCode4());
        simVolunteerOutput4.setMajor(cache.getMajor4());
        SimVolunteerOutput simVolunteerOutput5 = new SimVolunteerOutput();
        simVolunteerOutput5.setCode(cache.getCode5());
        simVolunteerOutput5.setSchool(cache.getSchool5());
        simVolunteerOutput5.setMajorCode(cache.getMajorCode5());
        simVolunteerOutput5.setMajor(cache.getMajor5());
        SimVolunteerOutput simVolunteerOutput6 = new SimVolunteerOutput();
        simVolunteerOutput6.setCode(cache.getCode6());
        simVolunteerOutput6.setSchool(cache.getSchool6());
        simVolunteerOutput6.setMajorCode(cache.getMajorCode6());
        simVolunteerOutput6.setMajor(cache.getMajor6());
        SimVolunteerOutput simVolunteerOutput7 = new SimVolunteerOutput();
        simVolunteerOutput7.setCode(cache.getCode7());
        simVolunteerOutput7.setSchool(cache.getSchool7());
        simVolunteerOutput7.setMajorCode(cache.getMajorCode7());
        simVolunteerOutput7.setMajor(cache.getMajor7());
        SimVolunteerOutput simVolunteerOutput8 = new SimVolunteerOutput();
        simVolunteerOutput8.setCode(cache.getCode8());
        simVolunteerOutput8.setSchool(cache.getSchool8());
        simVolunteerOutput8.setMajorCode(cache.getMajorCode8());
        simVolunteerOutput8.setMajor(cache.getMajor8());
        Collections.addAll(SimVolunteerOutputs,
                           simVolunteerOutput,simVolunteerOutput2,
                           simVolunteerOutput3,simVolunteerOutput4,
                           simVolunteerOutput5,simVolunteerOutput6,
                           simVolunteerOutput7,simVolunteerOutput8);
        try {
            ExcelUtil.process(fileName,SimVolunteerOutputs,SimVolunteerOutput.class,response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
