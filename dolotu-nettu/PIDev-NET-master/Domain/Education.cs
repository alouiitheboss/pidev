using System;
using System.ComponentModel.DataAnnotations;

namespace Domain
{
    public class Education
    {
        [Display(Name = "education_id")]
        [Key]
        public int id { get; set; }
        [Display(Name = "education_from")]
        [DataType(DataType.Date)]
        public DateTime from { get; set; }
        [Display(Name = "education_to")]
        [DataType(DataType.Date)]
        public DateTime to { get; set; }
        [Display(Name = "education_title")]
        public string title { get; set; }
        public Organisation organisation { get; set; }


    }
}