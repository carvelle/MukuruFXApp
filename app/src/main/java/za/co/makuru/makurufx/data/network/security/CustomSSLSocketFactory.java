package za.co.makuru.makurufx.data.network.security;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class CustomSSLSocketFactory extends SSLSocketFactory {

    private SSLContext sslcontext = null;

    public CustomSSLSocketFactory() {
        super();
    }

    private SSLContext createCustomSSLContext() throws IOException {
        try {
            SSLContext context = SSLContext.getInstance("TLS");

            context.init(null,
                    new TrustManager[]{new EasyX509TrustManager()},
                    null);

            return context;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    private SSLContext getSSLContext() throws IOException {
        if (this.sslcontext == null) {
            this.sslcontext = createCustomSSLContext();
        }
        return this.sslcontext;
    }

    @Override
    public Socket createSocket() throws IOException {
        return getSSLContext().getSocketFactory().createSocket();
    }

    @Override
    public final Socket createSocket(Socket s, String host, int port,
                                     boolean autoClose) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(s, host, port,
                autoClose);
    }

    private Socket enableTLSOnSocket(Socket socket) {
        if(socket != null && (socket instanceof SSLSocket)) {
            ((SSLSocket)socket).setEnabledProtocols(new String[] {"TLSv1.1", "TLSv1.2"});
        }
        return socket;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return null;
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return null;
    }

    @Override
    public final Socket createSocket(String host, int port) throws IOException {
        return enableTLSOnSocket(getSSLContext().getSocketFactory().createSocket(host, port));
    }

    @Override
    public final Socket createSocket(InetAddress host, int port)
            throws IOException {
        return enableTLSOnSocket(getSSLContext().getSocketFactory().createSocket(host, port));
    }

    @Override
    public final Socket createSocket(String host, int port,
                                     InetAddress localHost, int localPort) throws IOException {
        return enableTLSOnSocket(getSSLContext().getSocketFactory().createSocket(host, port, localHost, localPort));
    }

    @Override
    public final Socket createSocket(InetAddress address, int port,
                                     InetAddress localAddress, int localPort) throws IOException {
        return enableTLSOnSocket(getSSLContext().getSocketFactory().createSocket(address, port, localAddress, localPort));
    }

}
