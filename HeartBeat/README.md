#HeartBeat
<div>
    <p>
     心跳检测各类应用服务器(如Tomcat,Jetty),WEB服务器(如 Apache,Nginx) 的JAVA WEB应用程序.
    </p>
    <p>
     如何实现?
     <br/>
     使用HttpClient对指定的服务器(application-instance) URL 按频率(10秒,20秒...) 发起请求并记录响应的信息(连接耗时,是否连接成功,是否有异常,响应数据包大小),
     若检测到不正常(响应码不是200,抛出异常...)时则发送邮件给指定的地址,当检测恢复正常时也发送提醒邮件.
     <br/>
     将来会添加更多的实时提醒方式接口,如微信,短信
    </p>
</div>

<div>
    <h3>使用的框架及版本</h3>
    <ul>
        <li>Spring Framework - 3.2.2.RELEASE</li>
        <li>Quartz - 2.2.1</li>
        <li>Hibernate - 4.1.7.Final</li>
        <li>HttpClient - 4.3.5</li>
        <li><a href="http://www.bootcss.com/p/flat-ui/">Flat UI</a></li>
        <li>Maven - 3.1.0</li>
    </ul>
</div>

<div>
    <h3>下载</h3>
    从0.3版本开始, 每一个版本的下载文件都在项目的 'dist' 目录.
    <ul>
        <li><a href="http://git.oschina.net/mkk/HeartBeat/raw/V-0.3/dist/HeartBeat-0.3.zip">HeartBeat-0.3.zip</a></li>
        <li>最新版本下载: <a href="http://git.oschina.net/mkk/HeartBeat/raw/V-0.4/dist/HeartBeat-0.4.zip">HeartBeat-0.4.zip</a></li>
    </ul>
</div>

<div>
    <h3>特点</h3>
    <ul>
        <li>无侵入,独立部署</li>
        <li>可同时监测多个应用服务器</li>
        <li>请求方式支持GET,POST; URL支持http与https, 可指定请求content-type, 添加请求参数(固定参数或随机参数)</li>
        <li>添加安全设置,可控制用户注册,设定用户权限等</li>
        <li>使用简洁,灵活</li>
        <li>提醒方式及时,多样(目前仅实现邮件提醒,将来会加入微信提醒,短信提醒等)</li>
    </ul>
</div>

<div>
    <h3>运行环境</h3>
    <ul>
        <li>JRE 1.7 +</li>
        <li>MySql 5.5 +</li>
        <li>Tomcat 7 +</li>
    </ul>
</div>

<div>
    <h3>在线测试</h3>
    <a href="https://andaily.com/hb/">https://andaily.com/hb/</a>
</div>

<div>
    <h3>如何使用?</h3>
    <ol>
        <li>项目是Maven管理的, 需要在电脑上安装maven(开发用的版本号为3.1.0), MySql(开发用的版本号为5.5)</li>
        <li>下载(或clone)项目到本地</li>
        <li>
            创建MySQL数据库(默认数据库名:heart_beat), 并运行相应的SQL脚本(脚本文件位于others/database目录),
            <br/>
            运行脚本的顺序: HeartBeat.ddl -> quartz_mysql_innodb.sql
        </li>
        <li>
            修改HeartBeat.properties(位于src/main/resources目录)中的数据库连接信息(包括username, password等)
            <br/>
            <strong>NOTE: 为了确保能收到提醒邮件,请将配置文件中的 <em>mail.develop.address</em> 配置为你的邮件地址;
            若在生产环境,请将 <em>mail.develop.environment</em> 值修改为 false (true表示为开发环境)</strong>
        </li>
        <li>
            将本地项目导入到IDE(如Intellij IDEA)中,配置Tomcat(或类似的servlet运行服务器), 并启动Tomcat(默认端口为8080)
            <br/>
            <br/>
               另: 也可通过maven package命令将项目编译为war文件(HeartBeat.war),
                     将war放在Tomcat中并启动(注意: 这种方式需要将HeartBeat.properties加入到classpath中并正确配置数据库连接信息).
                     <br/>
                     或直接在项目的'dist'目录下载完整版安装包.
        </li>
    </ol>

    <h2>
        注意: 由于昨天(2015-08-06)发现项目默认发送邮件地址no-reply-hb@andaily.com被人非法使用导致该邮箱被禁用了(因为安全检测发现非法的IP登录),若发现邮件发送不正常,
        请将默认的邮件地址配置修改为你自己的邮件配置信息(位于HeartBeat.properties文件中),不要再使用默认的no-reply-hb@andaily.com邮箱.
        <img src="http://77g1is.com1.z0.glb.clouddn.com/illegal_heartbeat_ip.jpg"/>
    </h2>
</div>


<hr/>
<h3>开发计划</h3>
<p>
从 0.5版本开始将项目的所有计划的开发内容列出来, 方便大家跟进, 也欢迎你加入.
<br/>
项目的开发管理使用开源项目 <a href="http://git.oschina.net/mkk/andaily-developer">andaily-developer</a>.
</p>
<ul>
       <li>
            <p>
                Version: <strong>0.5</strong> [planning]
                <br/>
                Date: ------
            </p>
            <ol>
                <li><p>#70 - Why set archived = 1 in mysql application_instance table(Fix issue #6)</p></li>
                <li><p>#83 - 对于注册的用户, 登录后只能管理自己 创建的instances</p></li>
                <li><p>......</p></li>
            </ol>
       </li>

</ul>
<br/>


<div>
    <h3>Change-Log</h3>
    <ol>
        <li>
            <p>
                2014-10-17   ----    Initial project
            </p>
        </li>
        <li>
            <p>
                2015-02-13   ----    Move development to <a href="https://coding.net/u/monkeyk/p/HeartBeat/git">coding.net</a>
            </p>
        </li>
        <li>
            <p>
                2015-03-01   ----    Back to OSC and update documents; Add 0.1 branch
            </p>
        </li>
        <li>
            <p>
                2015-03-14   ----    Monitoring log add response data size;Add list of monitoring reminder logs; Update page styles; Add 0.2 branch
            </p>
        </li>
        <li>
            <p>
                2015-03-15   ----    0.3 branch is developing
            </p>
        </li>
        <li>
            <p>
                2015-04-02   ----    Add 0.3 branch and publish it
            </p>
        </li>
        <li>
            <p>
                2015-04-06   ----    0.4 branch is developing
            </p>
        </li>
        <li>
            <p>
                2015-05-01   ----    Publish 0.4 version
            </p>
        </li>
    </ol>
</div>


<div>
    <h3>程序运行主要截图</h3>
    <ol>
        <li>
            <p>
                Monitoring
                <br/>
                <img src="http://andaily.qiniudn.com/hbmonitoring_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Instance - Monitoring details
                <br/>
                <img src="http://andaily.qiniudn.com/hbmonitoring-details_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Instance - Overview
                <br/>
                <img src="http://andaily.qiniudn.com/hbinstances_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Instance - Create
                <br/>
                <img src="http://andaily.qiniudn.com/hbnew-instance_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Monitoring-Log
                <br/>
                <img src="http://andaily.qiniudn.com/hbhb-log_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Monitoring-Reminder-Log
                <br/>
                <img src="http://andaily.qiniudn.com/hbreminder-log_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Search
                <br/>
                <img src="http://andaily.qiniudn.com/hbsearch_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
    </ol>
</div>


<hr/>
<div>
    <h3>相关链接</h3>
    <ul>
        <li><a href="http://www.oschina.net/p/java-heartbeat">应用服务器心跳检测 Java HeartBeat</a></li>
        <li><a href="http://blog.csdn.net/monkeyking1987/article/details/44004231">心跳检测服务器是否正常的开源项目</a></li>
    </ul>
</div>

<hr/>
<div>
    More Open-Source projects see <a href="http://andaily.com/my_projects.html">http://andaily.com/my_projects.html</a>
    <br/>
    From <a href="http://andaily.com">andaily.com</a>
    <br/>
    Email <a href="mailto:monkeyk@shengzhaoli.com">monkeyk@shengzhaoli.com</a>
</div>