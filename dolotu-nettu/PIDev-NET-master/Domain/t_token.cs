namespace Domain
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;

    [Table("t_token")]
    public partial class t_token
    {
        [Key]
        public int id { get; set; }

        public DateTime? expiration { get; set; }

        [StringLength(255)]
        public string value { get; set; }

        public int? user_Id { get; set; }
    }
}
