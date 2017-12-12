using Data.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data.Infrastructure
{
    public class UnitOfWork : IUnitOfWork
    {

        private Context dataContext;

        IDatabaseFactory dbFactory;
        public UnitOfWork(IDatabaseFactory dbFactory)
        {
            this.dbFactory = dbFactory;
            dataContext = dbFactory.DataContext;
        }

        private IFormationRepository Formationrepository;
        private IParticipationRepository participationRepository;
        public IFormationRepository FormationRepository
        {
            get
            {
                return Formationrepository = new FormationRepository(dbFactory);
            }
        }
        public IParticipationRepository ParticipationRepository
        {
            get
            {
                return participationRepository = new ParticipationRepository(dbFactory);
            }
        }

        public void Commit()
        {
            dataContext.SaveChanges();
        }

        public void Dispose()
        {
            dataContext.Dispose();
        }
        public IRepositoryBase<T> getRepository<T>() where T : class
        {
            IRepositoryBase<T> repo = new RepositoryBase<T>(dbFactory);
            return repo;
        }

    }
}
