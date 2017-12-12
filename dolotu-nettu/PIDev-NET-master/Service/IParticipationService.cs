using Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Service
{
  public  interface IParticipationService : IDisposable
    {



        void AddParticipation(Participation r);
        void DeleteParticipation(int id);
        IEnumerable<Participation> GetAllParticipation();
        IEnumerable<Participation> GetAllParticipationByUser(int id);
        Participation GetParticipationById(int id);

        void SaveChange();
        //  Dictionary<string, int> StatHouse();
        int NbreParticipation(int id);
        Dictionary<string, int> StatFormation(int id);
       
        IEnumerable<Participation> GetAllParticipationbyformation(int id);
        int nbreParticipationbyformation(int id);
    }
}
