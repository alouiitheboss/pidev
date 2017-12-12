using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Domain;

namespace FormationWeb.Models
{
    public class credentialsDto
    {
        public string Username { get; set; }
        public string Password { get; set; }

        public Credentials toCredentials()
        {
            Credentials credentials = new Credentials();
            credentials.Password = this.Password;
            credentials.Username = this.Username;

            return credentials;
        }
    }
}