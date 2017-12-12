using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(FormationWeb.Startup))]
namespace FormationWeb
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
