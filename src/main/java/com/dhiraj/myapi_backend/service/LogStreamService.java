// ==========================================================================
// File: LogStreamService.java
// Purpose: Manage active SSE connections and broadcast new logs to clients.
// Approach:
//   - Stores list of active SseEmitters.
//   - Provides method to send new ApiLog to all connected emitters.
// Changes:
//   - [Init] Created basic SSE broadcasting service.
// ==========================================================================

package com.dhiraj.myapi_backend.service;

import com.dhiraj.myapi_backend.model.ApiLog;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class LogStreamService {
    // Thread safe list of active client emitters
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // add new emitter
    public SseEmitter addEmitter(){
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));
        return emitter;
    }

    // broadcast new log to all connected clients
    public void sendLog(ApiLog log){
        for(SseEmitter emitter : emitters){
            try{
                emitter.send(SseEmitter.event()
                        .name("log")
                        .data(log));
            }catch(Exception e){
                emitters.remove(emitter);
            }
        }
    }
}
