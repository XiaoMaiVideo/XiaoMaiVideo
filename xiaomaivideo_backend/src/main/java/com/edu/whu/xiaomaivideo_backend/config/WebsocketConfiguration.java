/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo_backend.config;

import com.edu.whu.xiaomaivideo_backend.controller.OneToOneWebsocketServer;
import com.edu.whu.xiaomaivideo_backend.service.CommentRestJPAServiceImpl;
import com.edu.whu.xiaomaivideo_backend.service.MessageRestJPAServiceImpl;
import com.edu.whu.xiaomaivideo_backend.service.MovieRestJPAServiceImpl;
import com.edu.whu.xiaomaivideo_backend.service.UserRestJPAServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebsocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setUserRestService(UserRestJPAServiceImpl u){
        OneToOneWebsocketServer.userRestService=u;
    }

    @Autowired
    public void setMessageRestService(MessageRestJPAServiceImpl m){
        OneToOneWebsocketServer.messageRestService=m;
    }

    @Autowired
    public void setCommitRestService(CommentRestJPAServiceImpl c){
        OneToOneWebsocketServer.commentRestService=c;
    }

    @Autowired
    public void setMovieRestService(MovieRestJPAServiceImpl m){
        OneToOneWebsocketServer.movieRestService=m;
    }
}