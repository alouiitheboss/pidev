using Data.Infrastructure;
using Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Service
{
    public class FormationService : IFormationService
    {
        IDatabaseFactory dbfactory = null;
        IUnitOfWork uow = null;
        public FormationService()
        {
            dbfactory = new DatabaseFactory();
            uow = new UnitOfWork(dbfactory);

        }
        public void AddFormation(Formation Formation)
        {
            uow.FormationRepository.Add(Formation);
        }

        public void DeleteFormation(int idFormation)
        {
            Formation p = uow.getRepository<Formation>().GetById(idFormation);
            uow.getRepository<Formation>().Delete(p);
            uow.Commit();
        }

        public void Dispose()
        {
            uow.Dispose();
        }

        public IEnumerable<Formation> GetAllFormation()
        {
            return uow.getRepository<Formation>().GetAll();
        }

        public IEnumerable<Formation> GetAllFormationFromNow()
        {
            DateTime current = DateTime.Now;
            return uow.getRepository<Formation>().GetMany(v => v.dateDebut > current);
        }

        /*   public IEnumerable<Formation> GetFormationBySwapper(string nom)
           {

               return uow.getRepository<Formation>().GetMany(x=> x.swapper.name.Equals(nom));

       }*/

        public Formation GetFormationById(int id)
        {
            return uow.getRepository<Formation>().GetById(id);
        }

        public int NbreFormation()

        {

            return uow.getRepository<Formation>().GetAll().Count();
        }

        public void SaveChange()
        {
            uow.Commit();
        }

        /*  public Dictionary<string, int> StatFormation()
          {
              {

                  var ss = from Formation u in uow.getRepository<Formation>().GetAll()
                           group u by u.type into g
                           select new { g.Key, Count = g.Count() };
                  Dictionary<string, int> typeFormation = new Dictionary<string, int>();
                  foreach (var t in ss)
                  {
                      typeFormation.Add(t.Key, t.Count);
                      Console.WriteLine(t.Key + "" + t.Count);
                  }
                  return typeFormation;
              }
          }*/

        public void Update(Formation h)
        {
            uow.getRepository<Formation>().Update(h);
            uow.Commit();

        }

        // public IEnumerable<Formation> getAllServBySwapper(Swapper swapper)
        //  {

        //  return uow.getRepository<Formation>().GetMany(c => c.swapper.idSwapper == swapper.idSwapper);



        // }
        //  public Swapper getSwapperByName(string name)
        //  {
        //     return uow.getRepository<Swapper>().Get(x => x.name == name);
        // }
    }
}
