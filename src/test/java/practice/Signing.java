package practice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.hash.Hashing;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.tomitribe.auth.signatures.MissingRequiredHeaderException;
import org.tomitribe.auth.signatures.PEM;
import org.tomitribe.auth.signatures.Signature;
import org.tomitribe.auth.signatures.Signer;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
* This example creates a {@link RequestSigner}, then prints out the Authorization header
* that is inserted into the HttpGet object.
*
* <p>
* apiKey is the identifier for a key uploaded through the console.
* privateKeyFilename is the location of your private key (that matches the uploaded public key for apiKey).
* </p>
*
* The signed HttpGet request is not executed, since instanceId does not map to a real instance.
*/
public class Signing {
    public static void main(String[] args) throws UnsupportedEncodingException {
        HttpRequestBase request;

        // This is the keyId for a key uploaded through the console
        String apiKey = ("ocid1.tenancy.oc1..aaaaaaaatux2eyqrab4qrcnxzvczxhdisa4fy4l6lnjchc24cpjujknja55q/"
                + "ocid1.user.oc1..aaaaaaaa3dazinds4rcxitibxb5hd5xed66salt4uepch5bo6jkza5y6fnrq/"
                + "6f:99:a2:a0:7d:1a:b3:e5:a3:27:4d:5f:c3:9a:ed:ed");
        String privateKeyFilename = "/Users/kpolusan/Desktop/private.pem";
        PrivateKey privateKey = loadPrivateKey(privateKeyFilename);
        RequestSigner signer = new RequestSigner(apiKey, privateKey);

        // GET with query parameters
        String uri = "https://objectstorage.eu-frankfurt-1.oraclecloud.com/n/tbe/b/bucket-20190611-1058/o/image1.jpeg";
        /*uri = String.format(uri,
                "Pjwf%3A%20PHX-AD-1",
                // Older ocid formats included ":" which must be escaped
                "ocid1.compartment.oc1..aaaaaaaam3we6vgnherjq5q2idnccdflvjsnog7mlr6rtdb25gilchfeyjxa".replace(":", "%3A"),
                "TeamXInstances",
                "ocid1.volume.oc1.phx.abyhqljrgvttnlx73nmrwfaux7kcvzfs3s66izvxf2h4lgvyndsdsnoiwr5q".replace(":", "%3A")
        );*/

        request = new HttpGet(uri);
        // Uncomment to use a fixed date
        // request.setHeader("Date", "Thu, 05 Jan 2014 21:31:40 GMT");

        signer.signRequest(request);
        System.out.println(uri);
        System.out.println(request.getFirstHeader("Authorization"));


        /*// POST with body
        uri = "https://iaas.us-ashburn-1.oraclecloud.com/20160918/volumeAttachments";
        request = new HttpPost(uri);
        // Uncomment to use a fixed date
        // request.setHeader("Date", "Thu, 05 Jan 2014 21:31:40 GMT");
        HttpEntity entity = new StringEntity("{\n" +
                "    \"compartmentId\": \"ocid1.compartment.oc1..aaaaaaaam3we6vgnherjq5q2idnccdflvjsnog7mlr6rtdb25gilchfeyjxa\",\n" +
                "    \"instanceId\": \"ocid1.instance.oc1.phx.abuw4ljrlsfiqw6vzzxb43vyypt4pkodawglp3wqxjqofakrwvou52gb6s5a\",\n" +
                "    \"volumeId\": \"ocid1.volume.oc1.phx.abyhqljrgvttnlx73nmrwfaux7kcvzfs3s66izvxf2h4lgvyndsdsnoiwr5q\"\n" +
                "}");
        ((HttpPost)request).setEntity(entity);*/

        signer.signRequest(request);
        System.out.println("\n" + uri);
        System.out.println(request.getFirstHeader("Authorization"));

    }

    /**
     * Load a {@link PrivateKey} from a file.
     */
    private static PrivateKey loadPrivateKey(String privateKeyFilename) {
        try (InputStream privateKeyStream = Files.newInputStream(Paths.get(privateKeyFilename))){
            return PEM.readPrivateKey(privateKeyStream);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid format for private key");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load private key");
        }
    }

    /**
     * A light wrapper around https://github.com/tomitribe/http-signatures-java
     */
    public static class RequestSigner {
        private static final SimpleDateFormat DATE_FORMAT;
        private static final String SIGNATURE_ALGORITHM = "rsa-sha256";
        private static final Map<String, List<String>> REQUIRED_HEADERS;
        static {
            DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
            DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
            REQUIRED_HEADERS = ImmutableMap.<String, List<String>>builder()
                    .put("get", ImmutableList.of("date", "(request-target)", "host"))
                    .put("head", ImmutableList.of("date", "(request-target)", "host"))
                    .put("delete", ImmutableList.of("date", "(request-target)", "host"))
                    .put("put", ImmutableList.of("date", "(request-target)", "host", "content-length", "content-type", "x-content-sha256"))
                    .put("post", ImmutableList.of("date", "(request-target)", "host", "content-length", "content-type", "x-content-sha256"))
                    .build();
        }
        private final Map<String, Signer> signers;

        /**
         * @param apiKey The identifier for a key uploaded through the console.
         * @param privateKey The private key that matches the uploaded public key for the given apiKey.
         */
        public RequestSigner(String apiKey, Key privateKey) {
            this.signers = REQUIRED_HEADERS
                    .entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getKey(),
                            entry -> buildSigner(apiKey, privateKey, entry.getKey())));
        }

        /**
         * Create a {@link Signer} that expects the headers for a given method.
         * @param apiKey The identifier for a key uploaded through the console.
         * @param privateKey The private key that matches the uploaded public key for the given apiKey.
         * @param method HTTP verb for this signer
         * @return
         */
        protected Signer buildSigner(String apiKey, Key privateKey, String method) {
            final Signature signature = new Signature(
                    apiKey, SIGNATURE_ALGORITHM, null, REQUIRED_HEADERS.get(method.toLowerCase()));
            return new Signer(privateKey, signature);
        }

        /**
         * Sign a request, optionally including additional headers in the signature.
         *
         * <ol>
         * <li>If missing, insert the Date header (RFC 2822).</li>
         * <li>If PUT or POST, insert any missing content-type, content-length, x-content-sha256</li>
         * <li>Verify that all headers to be signed are present.</li>
         * <li>Set the request's Authorization header to the computed signature.</li>
         * </ol>
         *
         * @param request The request to sign
         */
        public void signRequest(HttpRequestBase request) {
            final String method = request.getMethod().toLowerCase();
            // nothing to sign for options
            if (method.equals("options")) {
                return;
            }

            final String path = extractPath(request.getURI());

            // supply date if missing
            if (!request.containsHeader("date")) {
                request.addHeader("date", DATE_FORMAT.format(new Date()));
            }

            // supply host if mossing
            if (!request.containsHeader("host")) {
                request.addHeader("host", request.getURI().getHost());
            }

            // supply content-type, content-length, and x-content-sha256 if missing (PUT and POST only)
            if (method.equals("put") || method.equals("post")) {
                if (!request.containsHeader("content-type")) {
                    request.addHeader("content-type", "application/json");
                }
                if (!request.containsHeader("content-length") || !request.containsHeader("x-content-sha256")) {
                    byte[] body = getRequestBody((HttpEntityEnclosingRequestBase) request);
                    if (!request.containsHeader("content-length")) {
                        request.addHeader("content-length", Integer.toString(body.length));
                    }
                    if (!request.containsHeader("x-content-sha256")) {
                        request.addHeader("x-content-sha256", calculateSHA256(body));
                    }
                }
            }

            final Map<String, String> headers = extractHeadersToSign(request);
            final String signature = this.calculateSignature(method, path, headers);
            request.setHeader("Authorization", signature);
        }

        /**
         * Extract path and query string to build the (request-target) pseudo-header.
         * For the URI "http://www.host.com/somePath?example=path" return "/somePath?example=path"
         */
        private static String extractPath(URI uri) {
            String path = uri.getRawPath();
            String query = uri.getRawQuery();
            if (query != null && !query.trim().isEmpty()) {
                path = path + "?" + query;
            }
            return path;
        }

        /**
         * Extract the headers required for signing from a {@link HttpRequestBase}, into a Map
         * that can be passed to {@link RequestSigner#calculateSignature}.
         *
         * <p>
         * Throws if a required header is missing, or if there are multiple values for a single header.
         * </p>
         *
         * @param request The request to extract headers from.
         */
        private static Map<String, String> extractHeadersToSign(HttpRequestBase request) {
            List<String> headersToSign = REQUIRED_HEADERS.get(request.getMethod().toLowerCase());
            if (headersToSign == null) {
                throw new RuntimeException("Don't know how to sign method " + request.getMethod());
            }
            return headersToSign.stream()
                    // (request-target) is a pseudo-header
                    .filter(header -> !header.toLowerCase().equals("(request-target)"))
                    .collect(Collectors.toMap(
                            header -> header,
                            header -> {
                                if (!request.containsHeader(header)) {
                                    throw new MissingRequiredHeaderException(header);
                                }
                                if (request.getHeaders(header).length > 1) {
                                    throw new RuntimeException(
                                            String.format("Expected one value for header %s", header));
                                }
                                return request.getFirstHeader(header).getValue();
                            }));
        }

        /**
         * Wrapper around {@link Signer#sign}, returns the {@link Signature} as a String.
         *
         * @param method Request method (GET, POST, ...)
         * @param path The path + query string for forming the (request-target) pseudo-header
         * @param headers Headers to include in the signature.
         */
        private String calculateSignature(String method, String path, Map<String, String> headers) {
            Signer signer = this.signers.get(method);
            if (signer == null) {
                throw new RuntimeException("Don't know how to sign method " + method);
            }
            try {
                return signer.sign(method, path, headers).toString();
            } catch (IOException e) {
                throw new RuntimeException("Failed to generate signature", e);
            }
        }

        /**
         * Calculate the Base64-encoded string representing the SHA256 of a request body
         * @param body The request body to hash
         */
        private String calculateSHA256(byte[] body) {
            byte[] hash = Hashing.sha256().hashBytes(body).asBytes();
            return Base64.getEncoder().encodeToString(hash);
        }

        /**
         * Helper to safely extract a request body.  Because an {@link HttpEntity} may not be repeatable,
         * this function ensures the entity is reset after reading.  Null entities are treated as an empty string.
         *
         * @param request A request with a (possibly null) {@link HttpEntity}
         */
        private byte[] getRequestBody(HttpEntityEnclosingRequestBase request) {
            HttpEntity entity = request.getEntity();
            // null body is equivalent to an empty string
            if (entity == null) {
                return "".getBytes(StandardCharsets.UTF_8);
            }
            // May need to replace the request entity after consuming
            boolean consumed = !entity.isRepeatable();
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            try {
                entity.writeTo(content);
            } catch (IOException e) {
                throw new RuntimeException("Failed to copy request body", e);
            }
            // Replace the now-consumed body with a copy of the content stream
            byte[] body = content.toByteArray();
            if (consumed) {
                request.setEntity(new ByteArrayEntity(body));
            }
            return body;
        }
    }
}
