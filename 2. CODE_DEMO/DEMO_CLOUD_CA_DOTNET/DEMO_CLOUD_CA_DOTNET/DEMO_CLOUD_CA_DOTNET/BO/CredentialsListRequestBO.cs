using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;

namespace DEMO_CLOUD_CA_DOTNET.BO
{
    public class CredentialsListRequestBO
    {

        [JsonProperty("user_id")]
        public string userID { get; set; }

        [JsonProperty("client_id")]
        public string clientId { get; set; }

        [JsonProperty("client_secret")]
        public string clientSecret { get; set; }

        [JsonProperty("profile_id")]
        public string profileId { get; set; }

        [JsonProperty("certificates")]
        public string certificates { get; set; }

        [JsonProperty("certInfo")]
        public Boolean certInfo { get; set; }

        [JsonProperty("authInfo")]
        public Boolean authInfo { get; set; }
    }
}
