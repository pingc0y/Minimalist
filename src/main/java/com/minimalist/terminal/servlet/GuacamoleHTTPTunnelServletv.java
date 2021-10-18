package com.minimalist.terminal.servlet;

import org.apache.guacamole.GuacamoleClientException;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.GuacamoleResourceNotFoundException;
import org.apache.guacamole.GuacamoleServerException;
import org.apache.guacamole.net.GuacamoleTunnel;
import org.apache.guacamole.servlet.GuacamoleHTTPTunnelServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class GuacamoleHTTPTunnelServletv extends GuacamoleHTTPTunnelServlet {
    private final Logger logger = LoggerFactory.getLogger(GuacamoleHTTPTunnelServlet.class);
    private static final int READ_PREFIX_LENGTH = "read:".length();
    private static final int WRITE_PREFIX_LENGTH = "write:".length();


    protected void handleTunnelRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            String query = request.getQueryString();
            if (query == null) {
                throw new GuacamoleClientException("No query string provided.");
            }
            //修改部分
            if (query.contains("connect")) {
                GuacamoleTunnel tunnel = this.doConnect(request);
                if (tunnel == null) {
                    throw new GuacamoleResourceNotFoundException("No tunnel created.");
                }

                this.registerTunnel(tunnel);

                try {
                    response.setHeader("Cache-Control", "no-cache");
                    response.getWriter().print(tunnel.getUUID().toString());
                } catch (IOException var6) {
                    throw new GuacamoleServerException(var6);
                }
            } else if (query.startsWith("read:")) {
                this.doRead(request, response, query.substring(READ_PREFIX_LENGTH, READ_PREFIX_LENGTH + 36));
            } else {
                if (!query.startsWith("write:")) {
                    throw new GuacamoleClientException("Invalid tunnel operation: " + query);
                }
                this.doWrite(request, response, query.substring(WRITE_PREFIX_LENGTH, WRITE_PREFIX_LENGTH + 36));
            }
        } catch (GuacamoleClientException var7) {
            this.logger.warn("HTTP tunnel request rejected: {}", var7.getMessage());
            this.sendError(response, var7.getStatus().getGuacamoleStatusCode(), var7.getStatus().getHttpStatusCode(), var7.getMessage());
        } catch (GuacamoleException var8) {
            this.logger.error("HTTP tunnel request failed: {}", var8.getMessage());
            this.logger.debug("Internal error in HTTP tunnel.", var8);
            this.sendError(response, var8.getStatus().getGuacamoleStatusCode(), var8.getStatus().getHttpStatusCode(), "Internal server error.");
        }

    }
}
