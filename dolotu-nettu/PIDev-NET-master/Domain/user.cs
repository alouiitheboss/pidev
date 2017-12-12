namespace Domain
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;

    [Table("user")]
    public partial class user
    {
        [Key]
        public int Id { get; set; }

        [StringLength(255)]
        public string adresse { get; set; }
        
        [StringLength(255)]
        public string email { get; set; }

        [StringLength(255)]
        public string firstname { get; set; }

        [StringLength(255)]
        public string gender { get; set; }

        [StringLength(255)]
        public string lastname { get; set; }

        [StringLength(255)]
        public string password { get; set; }

        [DataType(DataType.EmailAddress)]
        public string phoneNumber { get; set; }

        public byte[] picture { get; set; }

        [StringLength(255)]
        public string role { get; set; }

        public bool? state { get; set; }

        [StringLength(255)]
        public string username { get; set; }

        public ICollection<Participation> participations { get; set; }
    }
}
