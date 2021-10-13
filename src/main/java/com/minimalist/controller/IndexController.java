package com.minimalist.controller;

import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.service.AssetsService;
import com.minimalist.log.service.LogService;
import com.minimalist.terminal.servlet.HttpTunnelServlet;
import com.minimalist.user.entity.User;
import com.minimalist.user.service.UserService;
import com.minimalist.timing.UserIsTiming;

import org.apache.guacamole.net.GuacamoleTunnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class IndexController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    LogService logService;
    @Autowired
    UserService userService;

    public String VIEW_PATH = "view/console/";

    @RequestMapping({"/","index"})
    public String index(){
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user",user);
        UserIsTiming.userCount.add(user.getUsername());
        return "index";
    }

    @RequestMapping({"/component/code/index"})
    public String component(){
        return "component/code/index";
    }

    @RequestMapping("/login*")
    public String login(){
        return "login";
    }

    //仪表盘
    @RequestMapping("/view/console/console")
    public String console1(){
        int users = logService.selectByUserCount();
        int count = logService.selectByCount();
        Map<String,String> map = logService.selectByUserKing();
        int userCount = UserIsTiming.userCount.size();
        List<User> list = userService.selectByTime(8);
        request.setAttribute("userCount",userCount);
        request.setAttribute("users",users);
        request.setAttribute("count",count);

        Integer tunnels = 0;
        //清除断开的会话
        if(HttpTunnelServlet.tunnels!=null) {
                try{
            for (Map<String, Object> tunnel : HttpTunnelServlet.tunnels) {
                GuacamoleTunnel tunnelt = (GuacamoleTunnel) tunnel.get("tunnel");
                if (!tunnelt.getSocket().isOpen()) {

                        tunnelt.close();

                    HttpTunnelServlet.tunnels.remove(tunnel);
                }
            }
            tunnels = HttpTunnelServlet.tunnels.size();
                } catch (Exception e) { }
        }
        request.setAttribute("tunnels",tunnels);
        User user = userService.selectByname(map.get("name"));
        request.setAttribute("userking",user.getName());
        request.setAttribute("list",list);

        //获取统计数据
        Map<String, Map<String, String>> stringMapMap = logService.selectByStatistics();
        List<String> day = new ArrayList<>();
        List<String> trunnel = new ArrayList<>();
        List<String> login = new ArrayList<>();
        List<String> active = new ArrayList<>();
        Set<String> days = stringMapMap.get("trunnel").keySet();

        day.addAll(days);
        Collections.sort(day, new Comparator< String >() {
            @Override
            public int compare(String lhs, String rhs) {
                int i = lhs.compareTo(rhs);
                if ( i > 0 ) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        for (String s : day) {
            Map<String, String> trunneld = stringMapMap.get("trunnel");
            trunnel.add(trunneld.get(s));
        }
        for (String s : day) {
            Map<String, String> logind = stringMapMap.get("login");
            login.add(logind.get(s));
        }
        for (String s : day) {
            Map<String, String> actived = stringMapMap.get("active");
            active.add(actived.get(s));
        }
//        trunnel.sort();
        request.setAttribute("day",day);
        request.setAttribute("trunnel",trunnel);
        request.setAttribute("login",login);
        request.setAttribute("active",active);
        return VIEW_PATH+"console";
    }













}
