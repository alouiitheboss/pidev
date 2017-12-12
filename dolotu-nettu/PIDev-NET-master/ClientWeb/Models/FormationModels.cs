using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Threading.Tasks;
using Domain;

namespace ClientWeb
{
   public class FormationModels
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Key]
        public int Id { get; set; }
        [StringLength(255)]
        public string type { get; set; }
        [StringLength(255)]
        public string adresse { get; set; }
        [DataType(DataType.DateTime)]
        public DateTime? dateDebut { get; set; }
        [DataType(DataType.DateTime)]
        public DateTime? dateFin { get; set; }
        public int NbrePlace { get; set; }
        public string description { get; set; }
        public float prix_unitaire { get; set; }
        public ICollection<Participation> participations { get; set; }


    }
}
