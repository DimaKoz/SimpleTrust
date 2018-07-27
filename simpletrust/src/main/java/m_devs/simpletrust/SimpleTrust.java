package m_devs.simpletrust;

import android.annotation.SuppressLint;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SimpleTrust {

    public static final int EQUAL_MODE = 0;
    public static final int CONTAIN_MODE = 1;

    private static int CURRENT_MODE = -1;

    private static String sslContextString;
    private static SSLContext sslContext;

    private static HostnameVerifier hostnameVerifier;

    private static TrustManager[] trustManagers;

    private static List<String> trustedArray;

    private static HostnameVerifier defaultVerifier;
    private static SSLSocketFactory defaultFactory;

    /**
     * A constructor for SimpleTrust without a trusted domain or an array of domains. Use the
     * add trusted array or add trusted method to add trusted domains.
     *
     * @see #addTrusted(String) add one trusted domain.
     * @see #addTrustedArray(String[]) add a collection of trusted domains.
     */
    public SimpleTrust(){
        trustedArray = new ArrayList<>();
        loadDefault();
    }

    /**
     * A constructor for SimpleTrust
     *
     * @param trusted a trusted domain
     */
    public SimpleTrust(String trusted){
        trustedArray = new ArrayList<>();
        trustedArray.add(trusted);
        loadDefault();
    }

    /**
     * A constructor for SimpleTrust
     *
     * @param trustedArray an array of the trusted domains
     */
    public SimpleTrust(String[] trustedArray){
        SimpleTrust.trustedArray = new ArrayList<>();
        SimpleTrust.trustedArray.addAll(Arrays.asList(trustedArray));
        loadDefault();
    }

    /**
     * Sets the sslContext by an <code>SSLContext</code> object
     *
     * @param sslContext the context
     */
    public void setSSLContext(SSLContext sslContext){
        SimpleTrust.sslContext = sslContext;
        sslContextString = "";
    }

    /**
     * Sets the sslContext by a string (Can be SSL, TLS etc..)
     *
     * @param sslContextString the context
     */
    public void setSSLContext(String sslContextString){
        SimpleTrust.sslContextString = sslContextString;
    }

    /**
     * Returns the hostname verifier.
     *
     * @return the <code>HostnameVerifier</code> in use
     */
    public HostnameVerifier getHostnameVerifier(){
        return hostnameVerifier;
    }

    /**
     * Returns the trust manager.
     *
     * @return the <code>TrustManager</code> in use
     */
    public TrustManager[] getTrustManagers(){
        return trustManagers;
    }

    /**
     * Adds a trusted domain to the trusted domains list.
     *
     * @param trusted the trusted domain
     */
    public void addTrusted(String trusted){
        if (SimpleTrust.trustedArray != null){
            SimpleTrust.trustedArray.add(trusted);
        }
    }

    /**
     * Adds a collection of trusted domains to the trusted domains list.
     *
     * @param trustedArray the trusted domains
     */
    public void addTrustedArray(String[] trustedArray){
        if (SimpleTrust.trustedArray != null){
            SimpleTrust.trustedArray.addAll(Arrays.asList(trustedArray));
        }
    }

    /**
     * Removes a trusted domain from the trusted domains list.
     *
     * @param trusted the untrusted domain.
     */
    public void removeTrusted(String trusted){
        if (SimpleTrust.trustedArray != null){
            trustedArray.remove(trusted);
        }
    }

    /**
     * Removes a collection of trusted domains from the trusted domains list.
     *
     * @param trustedArray the untrusted domains.
     */
    public void removeTrustedArray(String[] trustedArray){
        if (SimpleTrust.trustedArray != null){
            SimpleTrust.trustedArray.removeAll(Arrays.asList(trustedArray));
        }
    }

    /**
     * Sets the mode of the <code>HostnameVerifier</code> to return the verify() method value.
     *
     * @param mode the mode can be 0 (EQUAL_MODE) and 1 (CONTAIN_MODE)
     *             @see #EQUAL_MODE
     *             @see #CURRENT_MODE
     *             @see #load()
     */
    public void setMode(int mode){
        CURRENT_MODE = mode;
    }

    /**
     * Loads the new <code>HostnameVerifier</code> and <codee>SSLSocketFactory</code> and
     * <code>TrustManager</code> using the given values.
     */
    public void load(){

        // If the current mode is not set.. set it to equal by default
        CURRENT_MODE = CURRENT_MODE == -1 ? EQUAL_MODE : CURRENT_MODE;

        // Create the hostname verifier with the special verify() method
        hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                switch (CURRENT_MODE){

                    case EQUAL_MODE: // Equal mode.. if one of the given domains is equal with the hostname
                        if (trustedArray != null){
                            for (String trusted : trustedArray){
                                if (trusted.equals(hostname)){
                                    return true; // Return true if the one of the domain is equal with the hostname
                                }
                            }
                        }
                        return false; // Return false if the array does not equal with any of the domains given

                    case CONTAIN_MODE: // Contain mode.. if the array contains one of the given domains
                        if (trustedArray != null){
                            for (String trusted : trustedArray){
                                if (trusted.contains(hostname)){
                                    return true; // Return true if the one of the domain is contains the hostname
                                }
                            }
                        }
                        return false; // Return false if the array does not contain any of the domains given

                    default:
                        return false; // Return false by default
                }
            }
        };

        // Create the trust manger
        trustManagers = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {

            }
            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {

            }
        }};

        // Set the new HostnameVerifier and SSLSocketFactory
        try {
            if (!sslContextString.isEmpty()){
                sslContext = SSLContext.getInstance("TLS");
            }
            if (sslContext != null){
                sslContext.init(null, trustManagers, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
            }
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the default <code>HostnameVerifier</code> and <codee>SSLSocketFactory</code> what will be used
     * in the reset method.
     *
     * @see #reset()
     */
    private void loadDefault(){
        defaultFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
        defaultVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
    }

    /**
     * Resets the <code>HostnameVerifier</code> and the <codee>SSLSocketFactory</code> to default
     */
    public void reset(){
        HttpsURLConnection.setDefaultSSLSocketFactory(defaultFactory);
        HttpsURLConnection.setDefaultHostnameVerifier(defaultVerifier);
    }

}
