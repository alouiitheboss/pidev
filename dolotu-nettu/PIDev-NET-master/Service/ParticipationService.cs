using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain;
using Data.Infrastructure;

namespace Service
{
    public class ParticipationService : IParticipationService
    {

        IDatabaseFactory dbfactory = null;
        IUnitOfWork uow = null;

        public ParticipationService()
        {
            dbfactory = new DatabaseFactory();
            uow = new UnitOfWork(dbfactory);

        }
        public void AddParticipation(Participation r)
        {
            uow.getRepository<Participation>().Add(r);
        }

        public void DeleteParticipation(int id)
        {
            uow.getRepository<Participation>().Delete(uow.getRepository<Participation>().GetById(id));
            uow.Commit();
        }

        public void Dispose()
        {
            uow.Dispose();
        }

        public IEnumerable<Participation> GetAllParticipation()
        {
            return uow.getRepository<Participation>().GetAll();
        }
        // tache avancee 
        public IEnumerable<Participation> GetAllParticipationbyformation(int id)
        {
            return uow.getRepository<Participation>().GetMany(v => v.Formation.Id == id);
        }
        // tache avancee 
        public IEnumerable<Participation> GetAllParticipationByUser(int id)
        {
            return uow.getRepository<Participation>().GetMany(u => u.user.Id == id);
        }

        public Participation GetParticipationById(int id)
        {
            return uow.getRepository<Participation>().GetById(id);
        }
   
        public int NbreParticipation(int id )
        {
            return GetAllParticipationByUser(id).Count();
        }

        public int nbreParticipationbyformation(int id)
        {
            return GetAllParticipationbyformation(id).Count();
        }

        public void SaveChange()
        {
            uow.Commit();
        }

        public Dictionary<string, int> StatFormation(int id)
        {
            {

                var ss = from Participation u in GetAllParticipationbyformation(id)
                         group u by u.nombre into g
                         select new { g.Key, Count = g.Count() };
                Dictionary<string, int> depart = new Dictionary<string, int>();
                foreach (var t in ss)
                {
                    depart.Add(t.Key.ToString(), t.Count);
                    Console.WriteLine(t.Key + "" + t.Count);
                }
                return depart;
            }
        }
    }
}
