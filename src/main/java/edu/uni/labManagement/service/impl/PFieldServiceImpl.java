package edu.uni.labManagement.service.impl;

import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.*;
import edu.uni.labManagement.mapper.PFieldMapper;
import edu.uni.labManagement.mapper.PSchoolAreaMapper;
import edu.uni.labManagement.mapper.PSchoolMapper;
import edu.uni.labManagement.service.PFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PFieldServiceImpl implements PFieldService {
    @Resource
    private PFieldMapper pFieldMapper;

    @Resource
    private PSchoolAreaMapper pSchoolAreaMapper;

    @Resource
    private PSchoolMapper pSchoolMapper;
    @Autowired
    private AuthService authService;

    /**
     * 获取所有目的地ID的子列表
     * @return List<Map<String,Object>>
     */
    @Override
    public List<Map<String,Object>> fieldIdsSonList() {
        List<Map<String,Object>> res = new LinkedList<>();
        PSchoolExample example = new PSchoolExample();
        example.or().andDeletedEqualTo(0);
        List<PSchool> list = pSchoolMapper.selectByExample(example);
        for(PSchool school : list) {
            Map<String,Object> mapSchool = new HashMap<>();
            mapSchool.put("value", school.getId());
            mapSchool.put("label", school.getName());

            List<Map<String,Object>> mapSchoolList = new LinkedList<>();
            PSchoolAreaExample example1 = new PSchoolAreaExample();
            example1.or().andSchoolIdEqualTo(school.getId()).andDeletedEqualTo(0);
            List<PSchoolArea> list1 = pSchoolAreaMapper.selectByExample(example1);
            for(PSchoolArea area : list1) {
                Map<String,Object> mapArea = new HashMap<>();
                mapArea.put("value", area.getId());
                mapArea.put("label", area.getName());

                List<Map<String,Object>> mapAreaList = new LinkedList<>();
                PFieldExample example2 = new PFieldExample();
                example2.or().andAreaIdEqualTo(area.getId()).andDeletedEqualTo(0);
                List<PField> list2 = pFieldMapper.selectByExample(example2);
                for(PField field : list2) {
                    Map<String,Object> mapField = new HashMap<>();
                    mapField.put("value", field.getId());
                    mapField.put("label", field.getName());
                    mapAreaList.add(mapField);
                }
                mapArea.put("children", mapAreaList);

                if(!mapAreaList.isEmpty()) mapSchoolList.add(mapArea);
            }
            mapSchool.put("children", mapSchoolList);

            if(!mapSchoolList.isEmpty()) res.add(mapSchool);
        }
        return res;
    }
}
