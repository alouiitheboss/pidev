using System;
using System.Collections.Generic;
using System.Linq;

using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;
using System.Threading.Tasks;
using Domain;

namespace ClientWeb
{
    public class ParticipationModel
    {
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [Key]
        public int Id { get; set; }

        public int? nombre { get; set; }
        [DataType(DataType.DateTime)]
        public DateTime? date_participation { get; set; }

        public virtual Formation Formation { get; set; }
        public virtual user user { get; set; }
      

    }
}
