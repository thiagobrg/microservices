package com.teste.admin;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

/**
 * 
 * @since   2023-10-24
 * @version 0.1.0
 * @author  <a href="http://vertis-solutions.com">Vertis Solutions</a>
 */
@Configuration
@Profile("ssl")
public class SSLConfig {
	
	@Bean
    public ClientHttpConnector customHttpClient() throws SSLException {
		
		TrustManager[] trustAllCerts = new TrustManager[] { new X509ExtendedTrustManager() {
			public X509Certificate[] getAcceptedIssuers() { return null;}
			public void checkClientTrusted(X509Certificate[] certs, String authType) {}
			public void checkServerTrusted(X509Certificate[] certs, String authType) {}
			public void checkClientTrusted(X509Certificate[] xcs, String string, Socket socket) throws CertificateException {}
			public void checkServerTrusted(X509Certificate[] xcs, String string, Socket socket) throws CertificateException {}
			public void checkClientTrusted(X509Certificate[] xcs, String string, SSLEngine ssle) throws CertificateException {}
			public void checkServerTrusted(X509Certificate[] xcs, String string, SSLEngine ssle) throws CertificateException {}

		} };

        SslContext sslContext = SslContextBuilder.forClient() .build();

        var httpClient = reactor.netty.http.client.HttpClient.create().secure(
                ssl -> ssl.sslContext(sslContext)
                        .handlerConfigurator(sslHandler -> {
                            SSLEngine engine = sslHandler.engine();
                            SSLParameters params = new SSLParameters();
                            params.setEndpointIdentificationAlgorithm(null);
                            engine.setSSLParameters(params);
                        }));
        return new ReactorClientHttpConnector(httpClient);
    }
}