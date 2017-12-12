using Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Service
{
     public interface IFormationService : IDisposable
    {

        void AddFormation(Formation Formation);
        void DeleteFormation(int id);
        IEnumerable<Formation> GetAllFormation();
        IEnumerable<Formation> GetAllFormationFromNow();
        Formation GetFormationById(int id);
        void Update(Formation h);

        void SaveChange();
        //  Dictionary<string, int> StatFormation();
        int NbreFormation();
    }
}
