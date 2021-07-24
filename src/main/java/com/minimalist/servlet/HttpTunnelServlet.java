package com.minimalist.servlet;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.GuacamoleSocket;
import org.apache.guacamole.net.GuacamoleTunnel;
import org.apache.guacamole.net.InetGuacamoleSocket;
import org.apache.guacamole.net.SimpleGuacamoleTunnel;
import org.apache.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import org.apache.guacamole.servlet.GuacamoleHTTPTunnelServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@WebServlet(urlPatterns = "/tunnel")
public class HttpTunnelServlet extends GuacamoleHTTPTunnelServlet {

    public static GuacamoleConfiguration conf ;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3224942386695394818L;

	@Override
	protected GuacamoleTunnel doConnect(HttpServletRequest request) throws GuacamoleException {
        String hostname = "127.0.0.1"; //guacamole server地址
        int port = 4822; //guacamole server端口
        GuacamoleConfiguration configuration = new GuacamoleConfiguration();
        configuration.setProtocol("rdp"); // 远程连接协议
        configuration.setParameter("hostname", "t1.szbst.net");
        configuration.setParameter("port", "23814");
        configuration.setParameter("username", "administrator");
        configuration.setParameter("password", "cisco@888666");
        //configuration.setParameter("enable-wallpaper", "true");
        configuration.setParameter("resize-method", "display-update");
        configuration.setParameter("width", "1536");
        configuration.setParameter("height", "794");
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
        configuration.setParameter("drive-path", "/file");
        configuration.setParameter("create-drive-path", "true");


        //添加会话录制--录屏
        configuration.setParameter("recording-path", "/www/server/tomcat/webapps/recording");
        configuration.setParameter("create-recording-path", "true");
        configuration.setParameter("recording-name", ""+new Date().getTime()+".guac");


            GuacamoleSocket socket = new ConfiguredGuacamoleSocket(
                    new InetGuacamoleSocket(hostname, port),
                    configuration
            );
            conf = configuration;
            GuacamoleTunnel tunnel = new SimpleGuacamoleTunnel(socket);
            return tunnel
                    ;
        }

}
