/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ca;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.viettel.cloud.ca.CertBO;
import com.viettel.cloud.ca.CredentialsInfoResponseBO;
import com.viettel.cloud.ca.CredentialsListRequestBO;
import com.viettel.cloud.ca.CredentialsListResponseBO;
import com.viettel.cloud.ca.DocumentBO;
import com.viettel.cloud.ca.LoginRequestBO;
import com.viettel.cloud.ca.LoginResponseBO;
import com.viettel.cloud.ca.SignHashRequestBO;
import com.viettel.cloud.ca.SignHashResponseBO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import com.viettel.signature.plugin.SignOOXmlFile;
import com.viettel.signature.plugin.SignPdfFile;
import com.viettel.signature.plugin.SignXmlFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author khanhdn1
 */
public class SignMobileCAAction {

    private static Logger logger = Logger.getLogger(SignMobileCAAction.class);
    SignPdfFile pdfSig;
    SignXmlFile xmlSig;
    SignOOXmlFile ooxmlSig;

    //private final static String BASE_URL = "http://125.212.254.26:8777";
//    private final static String CLIENT_ID = "samples_test_client";
    //Demo
//        private final static String CLIENT_SECRET = "3cd296acece6bae96f43dec0ec1c644cfb5b0093";
    //That
//    private final static String CLIENT_SECRET = "205640fd6ea8c7d80bb91c630b52d286d21ee511";
//    private final static String PROFILE_ID = "adss:ras:profile:001";
    private final static String OID_NIST_SHA1 = "1.3.14.3.2.26";
    private final static String OID_NIST_SHA256 = "2.16.840.1.101.3.4.2.1";
    private final static String OID_RSA_RSA = "1.2.840.113549.1.1.1";

//    private String accessToken;
    public static HashMap<String, CertBO> getAllCertificates(String userId, String BASE_URL, String CLIENT_ID, String CLIENT_SECRET, String PROFILE_ID, TokenBO accessToken) {
        try {
            HashMap<String, CertBO> certList = new HashMap<String, CertBO>();
            SignMobileCAAction signMobileCAAction = new SignMobileCAAction();

            //Step 1: login to Cloud CA
            Date accessTokenCreateOn = new Date();
            LoginResponseBO response = signMobileCAAction.login(userId, BASE_URL, CLIENT_ID, CLIENT_SECRET, PROFILE_ID);
            if (response == null || ((response.getAccess_token() == null || response.getExpires_in() <= 0)
                    && (response.getError() != null || response.getError_description() != null))) {
                logger.error("ERROR: Login to Cloud CA");
                return null;
            }
            accessToken.setToken(response.getAccess_token());

            Calendar accessTokenExpiresIn = Calendar.getInstance();
            accessTokenExpiresIn.setTimeInMillis(accessTokenCreateOn.getTime() + Long.valueOf((long) response.getExpires_in()));

//            if (isTokenExpired(accessTokenExpiresIn)) {
//                logger.error("Token is expire!");
//                return null;
//            }
            //Step 2: Get Credentials list
            CredentialsListResponseBO credentialsListResponceBO = signMobileCAAction.getCredentialsList(CLIENT_ID, CLIENT_SECRET, PROFILE_ID, userId, accessToken, BASE_URL);
            if (credentialsListResponceBO == null || (credentialsListResponceBO.getError() != null || credentialsListResponceBO.getError_description() != null)) {
                logger.error("ERROR: Get Credentials list: " + "\n"
                        + "Error: " + credentialsListResponceBO.getError() + "\n"
                        + "Error: " + credentialsListResponceBO.getError_description()
                );
                return null;
            }
            if (credentialsListResponceBO.getCredentialIDs() == null || credentialsListResponceBO.getCredentialIDs().size() == 0) {
                logger.error("ERROR: Not found Certificate of User");
                return null;
            }
            for (CredentialsInfoResponseBO credentialID : credentialsListResponceBO.getCredentialIDs()) {

                certList.put(credentialID.getCredential_id(), credentialID.getCert());
            }
            return certList;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public List<String> signHash(List<String> hashList, int id, String dataDisplay, String clientId, String clientSecret, String credentialID, String BASE_URL, TokenBO accessToken) {
        try {
            int numSignatures = hashList.size();
            List<DocumentBO> documents = new ArrayList<DocumentBO>();
            for (int i = 0; i < hashList.size(); i++) {
                documents.add(new DocumentBO(id, dataDisplay));
            }

            String hashAlgo = OID_NIST_SHA1;
            String hash = hashList.get(0);
            if (hash != null && hash.length() != 28) {
                hashAlgo = OID_NIST_SHA256;
            }

            String signAlgo = OID_RSA_RSA;
            SignHashResponseBO signHashResponseBO = signHash(clientId, clientSecret, credentialID, accessToken, documents, hashList, hashAlgo, signAlgo, BASE_URL);
            if (signHashResponseBO == null || (signHashResponseBO.getError() != null || signHashResponseBO.getError_description() != null)) {
                logger.error("ERROR: Sign Hash: " + "\n"
                        + "Error: " + signHashResponseBO.getError() + "\n"
                        + "Error: " + signHashResponseBO.getError_description()
                );
                return null;
            }
            if (signHashResponseBO.getSignatures() == null || signHashResponseBO.getSignatures().size() == 0) {
                logger.error("ERROR: Sign Hash");
                return null;
            }

            List<String> signatures = signHashResponseBO.getSignatures();
            return signatures;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public static String toJsonString(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Java object to JSON string
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public static boolean isTokenExpired(Calendar accessTokenExpiresIn) {
        logger.info("accessTokenExpiresIn: " + accessTokenExpiresIn);
        Calendar now = Calendar.getInstance();
        logger.info("now: " + now);
        now.setTime(new Date());
        if (accessTokenExpiresIn.compareTo(now) > 0) {
            return false;
        }
        logger.info("Token is expire!");
        return true;
    }

    public LoginResponseBO login(String userId, String BASE_URL, String CLIENT_ID, String CLIENT_SECRET, String PROFILE_ID) {
        try {
            URL url = new URL(BASE_URL + "/vtss/service/ras/v1/login");
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                LoginRequestBO request = new LoginRequestBO();
                request.setClient_id(CLIENT_ID);
                request.setUser_id(userId);
                request.setClient_secret(CLIENT_SECRET);
                request.setProfile_id(PROFILE_ID);

                String input = toJsonString(request);
                logger.info("input: " + input);

                OutputStream os = null;
                try {
                    os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();
                } catch (Exception e) {
                    logger.error(e);
                    return null;
                } finally {
                    if (os != null) {
                        os.close();
                    }
                }

                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    logger.error("Failed : HTTP error code : " + conn.getResponseCode());
                }

                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    logger.info("Output from Server .... \n");
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    String jsonInString = response.toString();
                    logger.info("responce: " + jsonInString);

                    //JSON from String to Object
                    LoginResponseBO loginResponse = mapper.readValue(jsonInString, LoginResponseBO.class);
                    return loginResponse;
                } catch (Exception e) {
                    logger.error(e);
                } finally {
                    if (br != null) {
                        br.close();
                    }
                }

                br = null;
                try {
                    br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    logger.info("Output from Server .... \n");
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    String jsonInString = response.toString();
                    logger.info("responce: " + jsonInString);
                } catch (Exception e) {
                    logger.error(e);
                    return null;
                } finally {
                    if (br != null) {
                        br.close();
                    }
                }
            } catch (Exception e) {
                logger.error(e);
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public CredentialsListResponseBO getCredentialsList(String clientId, String clientSecret, String profileId, String userId, TokenBO accessToken, String BASE_URL) {
        try {
            URL url = new URL(BASE_URL + "/vtss/service/certificates/info");
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Bearer " + accessToken.getToken());

                CredentialsListRequestBO request = new CredentialsListRequestBO();
                request.setClient_id(clientId);
                request.setClient_secret(clientSecret);
                request.setProfile_id(profileId);
                request.setUser_id(userId);
                request.setCertificates("chain");
                request.setCertInfo(true);
                request.setAuthInfo(true);

                String input = toJsonString(request);
                logger.info("input: " + input);

                OutputStream os = null;
                try {
                    os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();
                } catch (Exception e) {
                    logger.error(e);
                    return null;
                } finally {
                    if (os != null) {
                        os.close();
                    }
                }

                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    logger.error("Failed : HTTP error code : " + conn.getResponseCode());
                }

                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    logger.info("Output from Server .... \n");
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    String jsonInString = response.toString();
                    logger.info("responce: " + jsonInString);

                    //JSON from String to Object
                    List<CredentialsInfoResponseBO> creList = new ArrayList<CredentialsInfoResponseBO>(Arrays.asList(mapper.readValue(jsonInString, CredentialsInfoResponseBO[].class)));

                    Iterator<CredentialsInfoResponseBO> iter = creList.iterator();
                    while (iter.hasNext()) {
                        CredentialsInfoResponseBO item = iter.next();
                        if (!"valid".equals(item.getCert().getStatus()))
                            iter.remove();
                    }
                    CredentialsListResponseBO creListResponse = new CredentialsListResponseBO();
                    creListResponse.setCredentialIDs(creList);
                    return creListResponse;
                } catch (Exception e) {
                    logger.error(e);
                } finally {
                    if (br != null) {
                        br.close();
                    }
                }

                br = null;
                try {
                    br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    logger.info("Output from Server .... \n");
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    String jsonInString = response.toString();
                    logger.info("response: " + jsonInString);
                } catch (Exception e) {
                    logger.error(e);
                    return null;
                } finally {
                    if (br != null) {
                        br.close();
                    }
                }
            } catch (Exception e) {
                logger.error(e);
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public SignHashResponseBO signHash(String clientId, String clientSecret, String credentialID, TokenBO accessToken,
            List<DocumentBO> documents, List<String> hashs, String hashAlgo, String signAlgo, String BASE_URL) {
        try {
            URL url = new URL(BASE_URL + "/vtss/service/signHash");
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Bearer " + accessToken.getToken());

                SignHashRequestBO request = new SignHashRequestBO();
                request.setClient_id(clientId);
                request.setClient_secret(clientSecret);
                request.setCredentialID(credentialID);
                request.setDocuments(documents);
                request.setNumSignatures(documents.size());
                request.setHash(hashs);
                request.setHashAlgo(hashAlgo);
                request.setSignAlgo(signAlgo);
                request.setAsync(0);

                String input = toJsonString(request);
                logger.info("input: " + input);

                OutputStream os = null;
                try {
                    os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();
                } catch (Exception e) {
                    logger.error(e);
                    return null;
                } finally {
                    if (os != null) {
                        os.close();
                    }
                }

                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    logger.error("Failed : HTTP error code : " + conn.getResponseCode());
                }

                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    logger.info("Output from Server .... \n");
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    String jsonInString = response.toString();
                    logger.info("responce: " + jsonInString);

                    //JSON from String to Object
                    SignHashResponseBO signResponse = mapper.readValue(jsonInString, SignHashResponseBO.class);
                    return signResponse;
                } catch (Exception e) {
                    logger.error(e);
                } finally {
                    if (br != null) {
                        br.close();
                    }
                }

                br = null;
                try {
                    br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    logger.info("Output from Server .... \n");
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    String jsonInString = response.toString();
                    logger.info("responce: " + jsonInString);
                } catch (Exception e) {
                    logger.error(e);
                    return null;
                } finally {
                    if (br != null) {
                        br.close();
                    }
                }
            } catch (Exception e) {
                logger.error(e);
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

}
