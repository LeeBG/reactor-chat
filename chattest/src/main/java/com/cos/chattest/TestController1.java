 package com.cos.chattest;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Netty 서버는 비동기 서버, Tomcat서버는 스레드서버
//요청할 때마다 이벤트루프

//Flux N개 이상의 데이터를 응답할 때
//Mono 0~1개의 데이터를 응답할 때
@CrossOrigin(origins = "*")
@RestController
public class TestController1 {
	@GetMapping(value ="/flux1")
	public Flux<Integer> flux1() {
		return Flux.just(1,2,3,4).log();	//Iterable 데이터를 담을 수 있음
		//unbounded 전부다 호출
	}
	
	@GetMapping(value = "/flux2", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Integer> flux2() {
		return Flux.just(1,2,3,4).delayElements(Duration.ofSeconds(1)).log();	//Iterable 데이터를 담을 수 있음
		//request(unbounded)
	}
	
	@GetMapping(value = "/flux3", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Long> flux3() {
		return Flux.interval(Duration.ofSeconds(2)).log();
		//request(unbounded)
	}
	
	
	@GetMapping(value = "/mono1")
	public Mono<Integer> mono1() {
		return Mono.just(1).log();	//request(unbounded)
	}
	

}
