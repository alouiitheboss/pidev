using Domain;

namespace Data.Infrastructure
{
    public class ParticipationRepository : RepositoryBase<Participation>, IParticipationRepository
    {
        //private IDatabaseFactory dbFactory;

        public ParticipationRepository(IDatabaseFactory dbFactory) : base(dbFactory)
        {
            //this.dbFactory = dbFactory;
        }
    }
    public interface IParticipationRepository : IRepositoryBase<Participation> { }
}