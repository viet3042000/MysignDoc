﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;

namespace DEMO_CLOUD_CA_DOTNET.BO
{
    public class CredentialsInfoRequestBO
    {

        [JsonProperty("credentialID")]
        public string credentialID { get; set; }

        [JsonProperty("certificates")]
        public string certificates { get; set; }

        [JsonProperty("certInfo")]
        public bool certInfo { get; set; }

        [JsonProperty("authInfo")]
        public bool authInfo { get; set; }

    }
}
