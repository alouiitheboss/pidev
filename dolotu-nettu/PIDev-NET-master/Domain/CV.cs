using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain
{
    public class CV
    {
        [Key]
        [Display(Name = "CV_ID")]
        public int id { get; set; }
        [ForeignKey("candidate")]
        public int candidateId { get; set; }
        
        [Display(Name = "CV_typeOfJob")]
        [Required]
        public string typeofjob { get; set; }
        [Display(Name = "CV_departement")]
        [Required]
        public string departement { get; set; }
        public virtual ICollection<Experience> experiences { get; set; }
        public virtual ICollection<Education> educations { get; set; }
        public virtual ICollection<Languages> languages { get; set; }
        [Display(Name = "CV_linkedIn")]
        public string linkedInLink { get; set; }
        [Display(Name = "CV_hobbies")]
        [DataType(DataType.MultilineText)]
        public string hobbies { get; set; }

        public virtual Candidate candidate { get; set; }
    }
}
