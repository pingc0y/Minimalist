package com.minimalist.shiro;


import com.minimalist.menu.entity.Menu;
import com.minimalist.menu.service.MenuService;
import com.minimalist.util.ApplicationContextHeader;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ShiroUp {
    @Autowired
    MenuService menuService;

    public Map<String, String> loadFilterChainDefinitions() {
        /*
        配置访问权限
        - anon:所有url都都可以匿名访问
        - authc: 需要认证才能进行访问（此处指所有非匿名的路径都需要登陆才能访问）
        - user:配置记住我或认证通过可以访问
        */
        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/login", "anon");
        //静态资源
        filterChainDefinitionMap.put("/static/**","anon");
        //验证码
        filterChainDefinitionMap.put("/view/system/getValidateCode","anon");
        //登陆
        filterChainDefinitionMap.put("/user/login","anon");
        //后台
        filterChainDefinitionMap.put("/webTelnet/**", "authc");
        filterChainDefinitionMap.put("/webTelnet/**", "authc");
        filterChainDefinitionMap.put("/**","perms[1]");

        // 不存在什么特别关键的操作，所以直接使用user认证

        return filterChainDefinitionMap;
    }

    public void updatePermission() {
        ShiroFilterFactoryBean shirFilter = ApplicationContextHeader.getBean(ShiroFilterFactoryBean.class);
        synchronized (shirFilter) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shirFilter.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            // 清空老的权限控制
            manager.getFilterChains().clear();
            shirFilter.getFilterChainDefinitionMap().clear();
            shirFilter.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shirFilter.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace("", "");
                manager.createChain(url, chainDefinition);
            }
        }
    }
}
