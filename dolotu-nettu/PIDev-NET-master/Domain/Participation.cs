using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.Spatial;

namespace Domain
{
    public class Participation
    {
       
        public int Id { get; set; }

        public int? nombre { get; set; }

        public DateTime? date_participation { get; set; }

 
        [ForeignKey("user")]
        public Nullable<int> user_id { get; set; }
        public virtual user user { get; set; }
        [ForeignKey("Formation")]
        public Nullable<int> formation_id { get; set; }
        public virtual Formation Formation { get; set; }
    
    }
}
