package edu.uni.labManagement.service.impl;

import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Component
public class Tool implements ApplicationContextAware {
    public static ApplicationContext app;

    public static List<Long> listIntToLong(List<Integer> list) {
        if(list == null) return null;
        List<Long> res = new LinkedList<>();
        for(Integer var : list) res.add((long) var);
        return res;
    }

    public static List<Long> getIdsByCriteria(Object criteria, String method) {
        try {
            String criteriaName = criteria.getClass().getName();
            String exampleName = criteriaName.replace("$Criteria", "");
            Object example = Class.forName(exampleName).newInstance();

            Object oredCriteria = execute(example, "getOredCriteria");
            execute(oredCriteria, "add", criteria);
            return getIdsByExample(example, method);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Long> getIdsByExample(Object example, String method) {
        List<Long> idList = new LinkedList<>();
        try {
            String exampleName = example.getClass().getName();
            String mapperName = (exampleName.substring(0, exampleName.length()
                    - "Example".length()) + "Mapper").replace(".bean.", ".mapper.");
            Object mapper = app.getBean(Class.forName(mapperName));

            Object list = execute(mapper, "selectByExample", example);
            for(Object var : (List) list) idList.add((Long) execute(var, method));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if(idList.isEmpty()) idList.add(-9L);
        return idList;
    }

    public static Object execute(Object object, String method, Object... params) {
        try {
            Class[] classes = new Class[params.length];
            if(object instanceof List || object instanceof Map) {
                for(int i = 0; i < params.length; i++) classes[i] = Object.class;
            } else {
                for(int i = 0; i < params.length; i++) classes[i] = params[i].getClass();
            }
            Method methodObj = object.getClass().getDeclaredMethod(method, classes);
            methodObj.setAccessible(true);
            return methodObj.invoke(object, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        app = applicationContext;
    }
}
