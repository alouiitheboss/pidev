using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain
{
   public class Formation
    {



        public int Id { get; set; }
        public string type { get; set; }
        public string adresse { get; set; }
        public DateTime? dateDebut { get; set; }
        public DateTime? dateFin { get; set; }
        public int NbrePlace { get; set; }
        public string description { get; set; }
        public float prix_unitaire { get; set; }
        public ICollection<Participation> participations { get; set; }


    }
}
