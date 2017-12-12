using Data.Infrastructure;
using Domain;
using Service.Pattern;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Service
{
    public class UserService : Service<user>, IUserService
    {
        static DatabaseFactory Dbf = new DatabaseFactory();
        static UnitOfWork utw = new UnitOfWork(Dbf);

        public UserService(IUnitOfWork utwk) : base(utwk)
        {
        }
    }

}
