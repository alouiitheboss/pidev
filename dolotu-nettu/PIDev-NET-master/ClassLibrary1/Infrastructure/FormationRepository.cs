using Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data.Infrastructure
{
    public class FormationRepository : RepositoryBase<Formation>, IFormationRepository
    {

        public FormationRepository(IDatabaseFactory dbFactory) : base(dbFactory)
        {

        }

    }
    public interface IFormationRepository : IRepositoryBase<Formation> { }
}
