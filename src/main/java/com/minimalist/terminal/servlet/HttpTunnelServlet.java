package com.minimalist.terminal.servlet;

import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.service.AssetsService;
import com.minimalist.assetsAuthorization.entity.AssetsAuthorization;
import com.minimalist.assetsAuthorization.service.AssetsAuthorizationService;
import com.minimalist.assetsUser.entity.AssetsUser;
import com.minimalist.assetsUser.service.AssetsUserService;
import com.minimalist.user.entity.User;
import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.GuacamoleSocket;
import org.apache.guacamole.net.GuacamoleTunnel;
import org.apache.guacamole.net.InetGuacamoleSocket;
import org.apache.guacamole.net.SimpleGuacamoleTunnel;
import org.apache.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import org.apache.guacamole.servlet.GuacamoleHTTPTunnelServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name="HttpTunnelServlet",urlPatterns="/tunnel")
public class HttpTunnelServlet extends GuacamoleHTTPTunnelServletv {
    @Value("${guacamole.guacd.host}")
    String hostname;
    @Value("${guacamole.guacd.port}")
    Integer port;
    @Value("${upload.videoPath}")
    private String videoPath;
    @Value("${upload.filePath}")
    private String filePath;
    @Autowired
    AssetsService assetsService;
    @Autowired
    AssetsUserService assetsUserService;

    public static List<Map<String,Object>> tunnels = new ArrayList<>();


	/**
	 * 
	 */
	private static final long serialVersionUID = -3224942386695394818L;

	@Override
	protected GuacamoleTunnel doConnect(HttpServletRequest request) throws GuacamoleException {

        String width = request.getParameter("width");
        String height = request.getParameter("height");
        String[] ids = request.getParameter("id").split(",");
        Assets assets = assetsService.getById(ids[0]);
        AssetsUser assetsUser = null;
        if(ids[1]!="0000000000") {
            assetsUser = assetsUserService.getById(ids[1]);
        }
        GuacamoleConfiguration configuration = new GuacamoleConfiguration();
        configuration.setProtocol(assets.getProtocol()); // 远程连接协议
        switch (assets.getProtocol()){
            case "rdp" :
                configuration.setParameter("hostname", assets.getAddress());
                configuration.setParameter("port", String.valueOf(assets.getPort()));
                if(assetsUser!=null) {
                    configuration.setParameter("username", assetsUser.getUsername());
                    configuration.setParameter("password", assetsUser.getPassword());
                    if (assetsUser.getActiveDirectory() != null && assetsUser.getActiveDirectory().length() > 1) {
                        configuration.setParameter("domain", assetsUser.getActiveDirectory());
                    }
                }
                //configuration.setParameter("enable-wallpaper", "true");
                configuration.setParameter("resize-method", "display-update");
                configuration.setParameter("width", width);
                configuration.setParameter("height", height);
                configuration.setParameter("console", "true");

                configuration.setParameter("disable-audio", "true");
                configuration.setParameter("enable-drive", "true");

                configuration.setParameter("create-path", "true");
                configuration.setParameter("enable-full-window-drag", "true");
                configuration.setParameter("ignore-cert", "true");
                //文件传输
                configuration.setParameter("enable-drive", "true");
                configuration.setParameter("ignore-cert", "true");
                configuration.setParameter("drive-name", "file transfer");
                File dir = new File(filePath+((User)request.getSession().getAttribute("user")).getId()+","+request.getParameter("id"));
                if (!dir.exists()) {// 判断目录是否存在
                    dir.mkdir();
                }
                configuration.setParameter("drive-path", filePath+((User)request.getSession().getAttribute("user")).getId()+","+request.getParameter("id"));
                configuration.setParameter("create-drive-path", "true");

                //添加会话录制--录屏
                Date dt = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String date = sdf.format(dt);
                User user = (User)request.getSession().getAttribute("user");
                configuration.setParameter("recording-path", videoPath+date);
                configuration.setParameter("create-recording-path", "true");
                configuration.setParameter("recording-name", user.getName()+","+request.getParameter("id")+","+new Date().getTime()+".guac");
                break;
            case "ssh" :
                configuration.setParameter("hostname", assets.getAddress());
                configuration.setParameter("port", String.valueOf(assets.getPort()));
                if(assetsUser!=null) {
                configuration.setParameter("username", assetsUser.getUsername());
                configuration.setParameter("password", assetsUser.getPassword());
                if(assetsUser.getSecretKey()!=null && assetsUser.getSecretKey().length() > 1) {
                    configuration.setParameter("private-key", assetsUser.getSecretKey());
                }
                }


                break;
            case "telnet" :
                configuration.setParameter("hostname", assets.getAddress());
                configuration.setParameter("port", String.valueOf(assets.getPort()));
                if(assetsUser!=null) {
                    configuration.setParameter("username", assetsUser.getUsername());
                    configuration.setParameter("password", assetsUser.getPassword());
                }
                break;
            case "vnc" :
                configuration.setParameter("hostname", assets.getAddress());
                configuration.setParameter("port", String.valueOf(assets.getPort()));
                configuration.setParameter("autoretry", "3");
                if(assetsUser!=null) {
                    configuration.setParameter("password", assetsUser.getPassword());
                }

                break;
        }

            GuacamoleSocket socket = new ConfiguredGuacamoleSocket(
                    new InetGuacamoleSocket(hostname, port),
                    configuration);
            GuacamoleTunnel tunnel = new SimpleGuacamoleTunnel(socket);

        HashMap<String, Object> map = new HashMap<>();
        map.put("tunnel",tunnel);
        tunnels.add(map);
        return tunnel;
        }

}
