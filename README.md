# Minimalist

#### 介绍
基于java开发的堡垒机(跳板机)，简单就是美


#### 软件架构
软件架构说明  
1.基于Spring Boot + Mybatis-Plus + Thymeleaf + Shiro 


#### 安装教程
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
2.安装mysql  
安装前，我们可以检测系统是否自带安装 MySQL

    rpm -qa | grep mysql
如果你系统有安装，那可以选择进行卸载

    rpm -e mysql　　// 普通删除模式
    rpm -e --nodeps mysql　　// 强力删除模式，如果使用上面命令删除时，提示有依赖的其它文件，则用该命令可以对其进行强力删除
下载包并安装

    wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm
    rpm -ivh mysql-community-release-el7-5.noarch.rpm
    yum update
    yum install mysql-server

权限设置

    chown -R mysql:mysql /var/lib/mysql
初始化 MySQL

    mysqld --initialize
启动 MySQL

    systemctl start mysqld

3.导入sql文件（src/main/resources/minimalist.sql）

4.安装javaJDK  
安装之前先检查一下系统有没有自带open-jdk

    yum list installed | grep [java][jdk]
卸载系统自带的jdk

    yum -y remove java-1.6.0-openjdk*  //表示卸载所有openjdk相关文件输入

查看JDK软件包列表

    yum search java | grep -i --color jdk
选择版本安装

    yum install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel
    #或者如下命令，安装jdk1.8.0的所有文件
    yum install -y java-1.8.0-openjdk*

配置环境变量  

JDK默认安装路径/usr/lib/jvm
在/etc/profile文件添加如下命令

    # set java environment
    JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.181-3.b13.el7_5.x86_64
    PATH=$PATH:$JAVA_HOME/bin  
    CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar  
    export JAVA_HOME  CLASSPATH  PATH

保存关闭profile文件，执行如下命令生效

    source  /etc/profile
使用如下命令，查看JDK变量

    echo $JAVA_HOME
    echo $PATH
    echo $CLASSPATH

4.修改application.yml配置文件  
修改mysql信息  
修改guacamole信息


#### 使用说明

1.启动Minimalist  

    #前台运行
    java -jar minimalist-1.0.jar
    #后台运行
    nohup java -jar minimalist-1.0.jar >/dev/null  2>&1 &
默认账号密码：admin/admin

