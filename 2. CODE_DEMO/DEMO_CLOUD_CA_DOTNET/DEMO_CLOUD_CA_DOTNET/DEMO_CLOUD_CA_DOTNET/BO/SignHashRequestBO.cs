using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;

namespace DEMO_CLOUD_CA_DOTNET.BO
{
    public class SignHashRequestBO
    {
        [JsonProperty("client_id")]
        public string client_id { get; set; }

        [JsonProperty("client_secret")]
        public string client_secret { get; set; }

        [JsonProperty("credentialID")]
        public string credentialID { get; set; }

        [JsonProperty("numSignatures")]
        public int numSignatures { get; set; }

        [JsonProperty("documents")]
        public DocumentBO[] documents { get; set; }

        [JsonProperty("hash")]
        public string[] hash { get; set; }

        [JsonProperty("hashAlgo")]
        public string hashAlgo { get; set; }

        [JsonProperty("signAlgo")]
        public string signAlgo { get; set; }

        [JsonProperty("async")]
        public int async { get; set; }
    }
}
