# Minimalist

#### 介绍
使用java开发的堡垒机(跳板机)，没有什么骚操作，朴实无华，功能简单，适合学习使用。

#### 软件架构
软件架构说明  
Spring Boot + Mybatis-Plus + Thymeleaf + Shiro 

#### 安装教程
不建议公网部署  
Centos7环境下


1.安装Guacd（Guacamole-Server）  
如果yum没有包，使用阿里云yum源即可

    yum install -y libguac-client-kubernetes \
    libguac-client-rdp \
    libguac-client-ssh \
    libguac-client-telnet \
    libguac-client-vnc \
    guacd --nogpgcheck


配置guacd服务

    mkdir /etc/guacamole/ && cat <<EOF >> /etc/guacamole/guacd.conf
    [daemon]
    pid_file = /var/run/guacd.pid
    log_level = info

    [server]
    # 监听地址
    bind_host = 0.0.0.0
    bind_port = 4822
    EOF

修改启动用户
vi /usr/lib/systemd/system/guacd.servic

    [Unit]
    Description=Guacamole proxy daemon
    Documentation=man:guacd(8)
    After=network.target
    
    [Service]
    EnvironmentFile=-/etc/sysconfig/guacd
    Environment=HOME=/var/lib/guacd
    ExecStart=/usr/sbin/guacd -f $OPTS
    Restart=on-failure
    # User=guacd
    # Group=guacd
    
    [Install]
    WantedBy=multi-user.target

启动guacd服务

    systemctl daemon-reload //重载服务
    systemctl enable guacd  //开机自启
    systemctl start guacd   //启动服务
    systemctl status guacd  //查看状态

2.导入sql文件到mysql（src/main/resources/minimalist.sql）

3.修改application.yml配置文件  
修改mysql配置  
修改guacamole配置


#### 使用说明

1.启动Minimalist  

    #前台运行
    java -jar minimalist-1.0.jar
    #后台运行
    nohup java -jar minimalist-1.0.jar >/dev/null  2>&1 &
默认账号密码：admin/admin


#### 相关截图

仪表盘  
![Image text](https://gitee.com/pingc/minimalist/raw/master/img/1.png)
我的资产  
![Image text](https://gitee.com/pingc/minimalist/raw/master/img/2.png)
