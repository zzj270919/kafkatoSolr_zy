package main.scala.sailing.solr;

import org.apache.http.*;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.*;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.shaded.org.apache.http.conn.ssl.SSLContexts;

import javax.net.ssl.*;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by sailing on 2016/8/16.
 */
public class InsecureSolrHttpClient implements HttpClient ,Serializable {

        private HttpClient httpClient;

        public  InsecureSolrHttpClient (HttpClient httpClient, SolrParams params) throws Exception {
            SSLContext ctx = SSLContexts.createDefault();
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            X509HostnameVerifier verifier = new X509HostnameVerifier() {
                public void verify(String string, SSLSocket ssls) throws IOException {
                }

                public void verify(String string, X509Certificate xc) throws SSLException {
                }

                public void verify(String string, String[] strings, String[] strings1) throws SSLException {
                }

                public boolean verify(String string, SSLSession ssls) {
                    return true;
                }
            };
            ctx.init((KeyManager[])null, new TrustManager[]{tm}, (SecureRandom)null);
            org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(verifier);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            DefaultHttpClient wrappedClient = new DefaultHttpClient(ccm, httpClient.getParams());
            HttpClientUtil.configureClient(wrappedClient, params);
            this.httpClient = wrappedClient;
        }

        public HttpResponse execute(HttpHost arg0, HttpRequest arg1, HttpContext arg2) throws IOException, ClientProtocolException {
            return this.httpClient.execute(arg0, arg1, arg2);
        }

        public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2, HttpContext arg3) throws IOException, ClientProtocolException {
            return this.httpClient.execute(arg0, arg1, arg2, arg3);
        }

        public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2) throws IOException, ClientProtocolException {
            return this.httpClient.execute(arg0, arg1, arg2);
        }

        public HttpResponse execute(HttpHost arg0, HttpRequest arg1) throws IOException, ClientProtocolException {
            return this.httpClient.execute(arg0, arg1);
        }

        public HttpResponse execute(HttpUriRequest arg0, HttpContext arg1) throws IOException, ClientProtocolException {
            return this.httpClient.execute(arg0, arg1);
        }

        public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1, HttpContext arg2) throws IOException, ClientProtocolException {
            return this.httpClient.execute(arg0, arg1, arg2);
        }

        public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1) throws IOException, ClientProtocolException {
            return this.httpClient.execute(arg0, arg1);
        }

        public HttpResponse execute(HttpUriRequest arg0) throws IOException, ClientProtocolException {
            return this.httpClient.execute(arg0);
        }

        public ClientConnectionManager getConnectionManager() {
            return this.httpClient.getConnectionManager();
        }

        public HttpParams getParams() {
            return this.httpClient.getParams();
        }

        public class PreEmptiveBasicAuthenticator implements HttpRequestInterceptor {
            private final UsernamePasswordCredentials credentials;

            public PreEmptiveBasicAuthenticator(String user, String pass) {
                this.credentials = new UsernamePasswordCredentials(user, pass);
            }

            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                request.addHeader(BasicScheme.authenticate(this.credentials, StandardCharsets.UTF_8.name(), false));
            }
        }


}
