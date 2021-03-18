package com.cos.chattest;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
@CrossOrigin
@RestController
public class TestController2 {
	
	Sinks.Many<String> sink;	//process = 지속적인 응답
	
	public TestController2() {
		this.sink=Sinks.many().multicast().onBackpressureBuffer();
	}
	
	//중간에 데이터가 들어와도
	@GetMapping("/send")
	public void send(String text) {
		sink.tryEmitNext(text);
	}
	
	//data: 실제값 \n\n
	@GetMapping(value = "/sse")
	public Flux<ServerSentEvent<String>> sse() {	//ServerSendEvent의 Content타입은 text event Stream
		return sink.asFlux().map(e -> ServerSentEvent.builder(e).build()).doOnCancel(()->{
			sink.asFlux().blockLast();
		});//구독
	}
	
	
}
