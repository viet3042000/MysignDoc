using DEMO_CLOUD_CA_DOTNET.BO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DEMO_CLOUD_CA_DOTNET
{
    public class MobileCA
    {
        private static readonly log4net.ILog logger = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        private const String OID_NIST_SHA1 = "1.3.14.3.2.26";
        private const String OID_NIST_SHA256 = "2.16.840.1.101.3.4.2.1";
        private const String OID_RSA_RSA = "1.2.840.113549.1.1.1";
        public MobileCA()
        {

        }

        public static Dictionary<String, CertBO> getAllCertificates(String userId, String BASE_URL, String CLIENT_ID, String CLIENT_SECRET, String PROFILE_ID, ref string accessToken)
        {
            Dictionary<String, CertBO> certList = new Dictionary<String, CertBO>();
            try
            {
                //Step 1: login to Cloud CA
                LoginResponseBO responce = login(userId, BASE_URL, CLIENT_ID, CLIENT_SECRET, PROFILE_ID);
                if (responce == null || ((responce.access_token == null || responce.expires_in <= 0)
                        && (responce.error != null || responce.error_description != null)))
                {
                    logger.Error("ERROR: Login to Cloud CA");
                    return certList;
                }
                accessToken = responce.access_token;

                CredentialsListResponseBO credentialsListResponceBO = getCredentialsList(CLIENT_ID, CLIENT_SECRET, PROFILE_ID, userId, accessToken, BASE_URL);
                if (credentialsListResponceBO == null)
                {
                    logger.Error("ERROR: Get Credentials list");
                    return certList;
                }
                else if (credentialsListResponceBO.error != null || credentialsListResponceBO.error_description != null)
                {
                    logger.Error("ERROR: Get Credentials list: " + "\n"
                            + "Error: " + credentialsListResponceBO.error + "\n"
                            + "Error: " + credentialsListResponceBO.error_description
                    );
                    return certList;
                }
                if (credentialsListResponceBO.credentialInfos == null || credentialsListResponceBO.credentialInfos.Length == 0)
                {
                    logger.Error("ERROR: Not found Certificate of User");
                    return certList;
                }
                CredentialsInfoResponseBO[] itemList = credentialsListResponceBO.credentialInfos;
                foreach (CredentialsInfoResponseBO item in itemList)
                {
                    certList.Add(item.credentialId, item.cert);
                }
                return certList;
            }
            catch (Exception e)
            {
                logger.Error(e);
            }
            return certList;
        }


        public static String[] signHash(String[] hashList, int id, String dataDisplay, String clientId, String clientSecret, String credentialID, String BASE_URL, string accessToken)
        {
            try
            {
                //Step 4: Get SAD
                int numSignatures = hashList.Length;
                DocumentBO[] documents = new DocumentBO[hashList.Length];
                for (int i = 0; i < hashList.Length; i++)
                {
                    documents[i] = new DocumentBO(id, dataDisplay);
                }

                //Step 5: Sign hash
                String hashAlgo = OID_NIST_SHA1;
                String hash = hashList[0];
                if (hash != null && hash.Length != 28)
                {
                    hashAlgo = OID_NIST_SHA256;
                }

                String signAlgo = OID_RSA_RSA;
                SignHashResponseBO signHashResponceBO = signHash(clientId, clientSecret, credentialID, accessToken, documents.Length
                    , documents, hashList, hashAlgo, signAlgo, BASE_URL);
                
                if (signHashResponceBO == null)
                {
                    logger.Error("ERROR: Sign Hash");
                    return null;
                }
                else if (signHashResponceBO.error != null || signHashResponceBO.error_description != null)
                {
                    logger.Error("ERROR: Sign Hash: " + "\n"
                            + "Error: " + signHashResponceBO.error + "\n"
                            + "Error: " + signHashResponceBO.error_description
                    );
                    return null;
                }
                if (signHashResponceBO.signatures == null || signHashResponceBO.signatures.Length == 0)
                {
                    logger.Error("ERROR: Sign Hash");
                    return null;
                }

                var signatures = signHashResponceBO.signatures;
                return signatures;
            }
            catch (Exception e)
            {
                logger.Error(e);
            }
            return null;
        }

        public static LoginResponseBO login(String userId, String BASE_URL, String CLIENT_ID, String CLIENT_SECRET, String PROFILE_ID)
        {
            try
            {
                string url = BASE_URL + "/vtss/service/ras/v1/login";
                LoginRequestBO request = new LoginRequestBO();
                request.client_id = CLIENT_ID;
                request.user_id = userId;
                request.client_secret = CLIENT_SECRET;
                request.profile_id = PROFILE_ID;
                var response = APICall.PostAsync<LoginResponseBO>(url, request).GetAwaiter().GetResult();
                return response;
            }
            catch (Exception e)
            {
                logger.Error("Error: " + e.Message);
                return null;
            }
        }
        public static CredentialsListResponseBO getCredentialsList(String clientId, String clientSecret, String profileId, String userId, String accessToken, String BASE_URL)
        {
            try
            {
                string url = BASE_URL + "/vtss/service/certificates/info";
                CredentialsListRequestBO request = new CredentialsListRequestBO();
                request.clientId = clientId;
                request.clientSecret = clientSecret;
                request.profileId = profileId;
                request.userID = userId;
                request.certificates = "chain";
                request.certInfo = true;
                request.authInfo = true;
                var response = APICall.PostAsync<CredentialsInfoResponseBO[]>(url, request, accessToken).GetAwaiter().GetResult();
                CredentialsListResponseBO res = new CredentialsListResponseBO();
                res.credentialInfos = response;
                return res;
            }
            catch (Exception e)
            {
                logger.Error("Error: " + e.Message);
                return null;
            }
        }

        public static SignHashResponseBO signHash(String clientId, String clientSecret, String credentialID, String accessToken,
            int numSignatures, DocumentBO[] documents, String[] hashs, String hashAlgo, String signAlgo, String BASE_URL)
        {
            try
            {
                string url = BASE_URL + "/vtss/service/signHash";
                SignHashRequestBO request = new SignHashRequestBO();
                request.client_id = clientId;
                request.client_secret = clientSecret;
                request.credentialID = credentialID;
                request.numSignatures = numSignatures;
                request.documents = documents;
                request.hash = hashs;
                request.hashAlgo = hashAlgo;
                request.signAlgo = signAlgo;
                request.async = 0;

                var response = APICall.PostAsync<SignHashResponseBO>(url, request, accessToken).GetAwaiter().GetResult();
                return response;
            }
            catch (Exception e)
            {
                logger.Error("Error: " + e.Message);
                return null;
            }
        }
    }
}
