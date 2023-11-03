package com.teste.admin;

import javax.net.ssl.SSLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

/**
 * 
 * @since 2023-10-24
 * @version 0.1.0
 * @author <a href="http://vertis-solutions.com">Vertis Solutions</a>
 */
@Configuration
@Profile("ssl")
public class SSLConfig {
	
    @Bean
    public ClientHttpConnector customhttpclient() throws SSLException {

    	SslContext sslcontext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
    	
          HttpClient httpclient = HttpClient.create().secure(
              ssl -> ssl.sslContext(sslcontext)
          );
          
          return new ReactorClientHttpConnector(httpclient);
    }

}