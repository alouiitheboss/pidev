using System;
using System.ComponentModel.DataAnnotations;

namespace Domain
{
    public class Experience
    {
        [Key]
        [Display(Name = "experience_id")]
        public int id { get; set; }
        [Display(Name = "experience_from")]
        [DataType(DataType.Date)]
        public DateTime from { get; set; }
        [Display(Name = "experience_to")]
        [DataType(DataType.Date)]
        public DateTime to { get; set; }
        [Display(Name = "experience_poste")]
        public string poste { get; set; }
        public string company { get; set; }
    }
}