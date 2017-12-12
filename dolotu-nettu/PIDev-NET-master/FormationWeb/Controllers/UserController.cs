using FormationWeb.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using System.Web.Script.Serialization;

namespace FormationWeb.Controllers
{
    public class UserController : Controller
    {
        private const string BASE_URL = "http://localhost:18080/pidev-web/api/users";

        public string LoginAsync(credentialsDto credentialsDto)
        {

            string retMsg = "KO";
            HttpWebRequest request = (HttpWebRequest)
                WebRequest.Create(BASE_URL + "/login");

            request.Method = "POST";
            request.ContentType = "application/json";
            using (var streamWriter = new StreamWriter(request.GetRequestStream()))
            {
                string json = new JavaScriptSerializer().Serialize(new
                {
                    username = credentialsDto.Username,
                    password = credentialsDto.Password
                });

                streamWriter.Write(json);
            }
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();

            string content = string.Empty;
            using (Stream stream = response.GetResponseStream())
            {
                using (StreamReader sr = new StreamReader(stream))
                {
                    content = sr.ReadToEnd();
                }
            }

            if (content != "")
            {
                HttpCookie myCookie = new HttpCookie("SESSION", content);
                Response.Cookies.Add(myCookie);
                retMsg = "OK";
            }

            return retMsg;
        }

        [Route("Login")]
        public ActionResult Login()
        {
            return View();
        }

    }
}