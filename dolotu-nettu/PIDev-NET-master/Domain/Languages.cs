using System.ComponentModel.DataAnnotations;

namespace Domain
{
    public class Languages
    {
        [Key]
        public int id { get; set; }
        [Display(Name ="language_name")]
        public string name { get; set; }
        [Display(Name = "language_level")]
        public string level { get; set; }
    }
}